package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.cellargalaxy.mycloud.dao.FileBlockDao;
import top.cellargalaxy.mycloud.model.bo.FileBlockBo;
import top.cellargalaxy.mycloud.model.po.FileBlockPo;
import top.cellargalaxy.mycloud.model.query.FileBlockQuery;
import top.cellargalaxy.mycloud.util.SqlUtil;

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
			@Result(property = "fileId", column = "file_id", id = true),
			@Result(property = "blockId", column = "block_id")
	})
	@SelectProvider(type = FileBlockProvider.class, method = "selectOne")
	FileBlockBo selectOne(FileBlockQuery fileBlockQuery);

	@ResultMap(value = "fileBlockResult")
	@SelectProvider(type = FileBlockProvider.class, method = "selectSome")
	List<FileBlockBo> selectSome(FileBlockQuery fileBlockQuery);

	@SelectProvider(type = FileBlockProvider.class, method = "selectCount")
	int selectCount(FileBlockQuery fileBlockQuery);

	@UpdateProvider(type = FileBlockProvider.class, method = "update")
	int update(FileBlockPo fileBlockPo);

	class FileBlockProvider {
		private static final String TABLE_NAME = FileBlockDao.TABLE_NAME;
		private static final String fileId = TABLE_NAME + ".file_id=#{fileId}";
		private static final String blockId = TABLE_NAME + ".block_id=#{blockId}";

		public static final String insert(FileBlockPo fileBlockPo) {
			String string = "insert into " + TABLE_NAME + "(file_id,block_id) " +
					"values(#{fileId},#{blockId})";
			return string;
		}

		public static final String delete(FileBlockQuery fileBlockQuery) {
			List<String> wheres = new LinkedList<>();
			wheresAll(fileBlockQuery, wheres);
			StringBuilder sql = SqlUtil.createDeleteSql(TABLE_NAME, wheres);
			String string = sql.toString();
			return string;
		}

		public static final String selectOne(FileBlockQuery fileBlockQuery) {
			List<String> wheres = new LinkedList<>();
			wheresKey(fileBlockQuery, wheres);
			StringBuilder sql = SqlUtil.createSelectSql(null, TABLE_NAME, wheres);
			String string = sql.append(" limit 1").toString();
			return string;
		}

		public static final String selectSome(FileBlockQuery fileBlockQuery) {
			List<String> wheres = new LinkedList<>();
			wheresAll(fileBlockQuery, wheres);
			StringBuilder sql = SqlUtil.createSelectSql(null, TABLE_NAME, wheres);
			String string = sql.toString();
			return string;
		}

		public static final String selectCount(FileBlockQuery fileBlockQuery) {
			List<String> selects = new LinkedList<>();
			selects.add("count(*)");
			List<String> wheres = new LinkedList<>();
			wheresAll(fileBlockQuery, wheres);
			StringBuilder sql = SqlUtil.createSelectSql(selects, TABLE_NAME, wheres);
			String string = sql.toString();
			return string;
		}

		public static final String update(FileBlockPo fileBlockPo) {
			List<String> sets = new LinkedList<>();
			sets(fileBlockPo, sets);
			if (sets.size() == 0) {
				return "update " + TABLE_NAME + " set file_id=#{fileId} where false";
			}
			List<String> wheres = new LinkedList<>();
			wheresKey(fileBlockPo, wheres);
			String string = SqlUtil.createUpdateSql(TABLE_NAME, sets, wheres).append(" limit 1").toString();
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
			if (fileBlockPo.getBlockId() > 0) {
				wheres.add(blockId);
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
