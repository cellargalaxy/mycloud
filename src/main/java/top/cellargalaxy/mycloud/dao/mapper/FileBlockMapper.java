package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import top.cellargalaxy.mycloud.dao.AbstractDao;
import top.cellargalaxy.mycloud.dao.FileBlockDao;
import top.cellargalaxy.mycloud.model.bo.FileBlockBo;
import top.cellargalaxy.mycloud.model.po.FileBlockPo;
import top.cellargalaxy.mycloud.model.query.FileBlockQuery;
import top.cellargalaxy.mycloud.util.ProviderUtil;

import java.util.List;
import java.util.Set;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
@Mapper
public interface FileBlockMapper extends AbstractDao<FileBlockPo, FileBlockBo, FileBlockQuery> {
	@InsertProvider(type = FileBlockProvider.class, method = "insert")
	int insert(FileBlockPo fileBlockPo);

	@DeleteProvider(type = FileBlockProvider.class, method = "delete")
	int delete(FileBlockPo fileBlockPo);

	@Results(id = "fileBlockResult", value = {
			@Result(property = "fileId", column = "file_id", id = true),
			@Result(property = "blockId", column = "block_id")
	})
	@SelectProvider(type = FileBlockProvider.class, method = "selectOne")
	FileBlockBo selectOne(FileBlockPo fileBlockPo);

	@ResultMap(value = "fileBlockResult")
	@SelectProvider(type = FileBlockProvider.class, method = "selectSome")
	List<FileBlockBo> selectSome(FileBlockQuery fileBlockQuery);

	@SelectProvider(type = FileBlockProvider.class, method = "selectCount")
	int selectCount(FileBlockQuery fileBlockQuery);

	@ResultMap(value = "fileBlockResult")
	@SelectProvider(type = FileBlockProvider.class, method = "selectAll")
	List<FileBlockBo> selectAll();

	@UpdateProvider(type = FileBlockProvider.class, method = "update")
	int update(FileBlockPo fileBlockPo);

	class FileBlockProvider /*implements AbstractProvider<FileBlockPo, FileBlockQuery>*/ {
		private String tableName = FileBlockDao.TABLE_NAME;
		private String fileId = tableName + ".file_id=#{fileId}";
		private String blockId = tableName + ".block_id=#{blockId}";

		public String insert(FileBlockPo fileBlockPo) {
			String string = "insert into " + tableName + "(file_id,block_id) " +
					"values(#{fileId},#{blockId})";
			return string;
		}

		public String delete(FileBlockPo fileBlockPo) {
			return ProviderUtil.delete(tableName, fileBlockPo, this::wheresKey).append(" limit 1").toString();
		}

		public String selectOne(FileBlockPo fileBlockPo) {
			return ProviderUtil.selectOne(tableName, fileBlockPo, this::wheresKey).append(" limit 1").toString();
		}

		public String selectSome(FileBlockQuery fileBlockQuery) {
			return ProviderUtil.selectSome(tableName, fileBlockQuery, this::wheresAll).toString();
		}

		public String selectCount(FileBlockQuery fileBlockQuery) {
			return ProviderUtil.selectCount(tableName, fileBlockQuery, this::wheresAll).toString();
		}

		public String selectAll() {
			return ProviderUtil.selectAll(tableName).toString();
		}

		public String update(FileBlockPo fileBlockPo) {
			return ProviderUtil.update(tableName, fileBlockPo, fileId, this::sets, this::wheresKey).append(" limit 1").toString();
		}

		private void wheresAll(FileBlockQuery fileBlockQuery, Set<String> wheres) {
			if (fileBlockQuery.getFileId() > 0) {
				wheres.add(fileId);
			}
			if (fileBlockQuery.getBlockId() > 0) {
				wheres.add(blockId);
			}
		}

		private void wheresKey(FileBlockPo fileBlockPo, Set<String> wheres) {
			if (fileBlockPo.getFileId() > 0) {
				wheres.add(fileId);
			}
			if (fileBlockPo.getBlockId() > 0) {
				wheres.add(blockId);
			}
		}

		private void sets(FileBlockPo fileBlockPo, Set<String> sets) {
			if (fileBlockPo.getFileId() > 0) {
				sets.add(fileId);
			}
			if (fileBlockPo.getBlockId() > 0) {
				sets.add(blockId);
			}
		}

	}
}
