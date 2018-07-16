package top.cellargalaxy.newcloud.dao.db;

import org.apache.ibatis.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.cellargalaxy.newcloud.dao.FileBlockDao;
import top.cellargalaxy.newcloud.model.bo.FileBlockBo;
import top.cellargalaxy.newcloud.model.po.FileBlockPo;
import top.cellargalaxy.newcloud.model.query.FileBlockQuery;
import top.cellargalaxy.newcloud.util.SqlUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
@Mapper
public interface FileBlockMapper {
	@InsertProvider(type = FileBlockProvider.class, method = "insert")
	int insert(FileBlockPo fileBlockPo);

	@DeleteProvider(type = FileBlockProvider.class, method = "delete")
	int delete(FileBlockQuery fileBlockQuery);

	@Results(id = "fileBlockResult", value = {
			@Result(property = "file_id", column = "fileId", id = true),
			@Result(property = "block_id", column = "blockId")
	})
	@SelectProvider(type = FileBlockProvider.class, method = "selectOne")
	FileBlockBo selectOne(FileBlockQuery fileBlockQuery);

	@ResultMap(value = "fileBlockResult")
	@SelectProvider(type = FileBlockProvider.class, method = "selectSome")
	List<FileBlockBo> selectSome(FileBlockQuery fileBlockQuery);

	@UpdateProvider(type = FileBlockProvider.class, method = "update")
	int update(FileBlockPo fileBlockPo);

	class FileBlockProvider {
		private static final Logger logger = LoggerFactory.getLogger(FileBlockProvider.class);
		private static final String TABLE_NAME = FileBlockDao.TABLE_NAME;
		private static final String fileId = "file_id=#{fileId}";
		private static final String blockId = "block_id=#{blockId}";

		public static final String insert(FileBlockPo fileBlockPo) {
			String string = "insert into " + TABLE_NAME + "(file_id,block_id) " +
					"values(#{fileId},#{blockId})";
			logger.debug("insert:{}, sql:{}", fileBlockPo, string);
			return string;
		}

		public static final String delete(FileBlockQuery fileBlockQuery) {
			List<String> wheres = new LinkedList<>();
			wheresAll(fileBlockQuery, wheres);
			StringBuilder sql = SqlUtil.createDeleteSql(TABLE_NAME, wheres);
			String string = sql.toString();
			logger.debug("delete:{}, sql:{}", fileBlockQuery, string);
			return string;
		}

		public static final String selectOne(FileBlockQuery fileBlockQuery) {
			List<String> wheres = new LinkedList<>();
			wheresKey(fileBlockQuery, wheres);
			StringBuilder sql = SqlUtil.createSelectSql(null, TABLE_NAME, wheres);
			String string = sql.append(" limit 1").toString();
			logger.debug("selectOne:{}, sql:{}", fileBlockQuery, string);
			return string;
		}

		public static final String selectSome(FileBlockQuery fileBlockQuery) {
			List<String> wheres = new LinkedList<>();
			wheresAll(fileBlockQuery, wheres);
			StringBuilder sql = SqlUtil.createSelectSql(null, TABLE_NAME, wheres);
			String string = sql.toString();
			logger.debug("selectSome:{}, sql:{}", fileBlockQuery, string);
			return string;
		}

		public static final String update(FileBlockPo fileBlockPo) {
			if (FileBlockDao.checkUpdate(fileBlockPo) != null) {
				return "update " + TABLE_NAME + " set file_id=#{fileId} where false";
			}
			List<String> sets = new LinkedList<>();
			sets(fileBlockPo, sets);
			List<String> wheres = new LinkedList<>();
			wheresKey(fileBlockPo, wheres);
			String string = SqlUtil.createUpdateSql(TABLE_NAME, sets, wheres).append(" limit 1").toString();
			logger.debug("update:{}, sql:{}", fileBlockPo, string);
			return string;
		}

		private static final void wheresAll(FileBlockQuery fileBlockQuery, List<String> wheres) {
			if (fileBlockQuery.getFileId() > 0) {
				wheres.add(fileId);
			}
			if (fileBlockQuery.getBlockId() > 0) {
				wheres.add(blockId);
			}
		}

		private static final void wheresKey(FileBlockPo fileBlockPo, List<String> wheres) {
			if (fileBlockPo.getFileId() > 0) {
				wheres.add(fileId);
			}
		}

		private static final void sets(FileBlockPo fileBlockPo, List<String> sets) {
			if (fileBlockPo.getFileId() > 0) {
				sets.add(fileId);
			}
			if (fileBlockPo.getBlockId() > 0) {
				sets.add(blockId);
			}
		}
	}
}
