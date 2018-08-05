package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import top.cellargalaxy.mycloud.dao.BlockDao;
import top.cellargalaxy.mycloud.model.bo.BlockBo;
import top.cellargalaxy.mycloud.model.po.BlockPo;
import top.cellargalaxy.mycloud.model.query.BlockQuery;
import top.cellargalaxy.mycloud.util.ProviderUtil;

import java.util.Set;

import static org.apache.ibatis.type.JdbcType.BLOB;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
@Mapper
public interface BlockMapper {
	@Options(useGeneratedKeys = true, keyProperty = "blockId")
	@SelectProvider(type = BlockProviderUtil.class, method = "insert")
	int insert(BlockPo blockPo);

	@DeleteProvider(type = BlockProviderUtil.class, method = "delete")
	int delete(BlockPo blockPo);

	@Results(id = "blockResult", value = {
			@Result(property = "blockId", column = "block_id", id = true),
			@Result(property = "block", column = "block", javaType = byte[].class, jdbcType = BLOB)
	})
	@SelectProvider(type = BlockProviderUtil.class, method = "selectOne")
	BlockBo selectOne(BlockPo blockPo);

	@UpdateProvider(type = BlockProviderUtil.class, method = "update")
	int update(BlockPo blockPo);

	class BlockProviderUtil {
		private String tableName = BlockDao.TABLE_NAME;
		private String blockId = tableName + ".block_id=#{blockId}";
		private String block = tableName + ".block=#{block}";

		public String insert(BlockPo blockPo) {
			String string = "insert into " + tableName + "(block) " +
					"values(#{block,jdbcType=BLOB})";
			return string;
		}

		public String delete(BlockPo blockPo) {
			return ProviderUtil.delete(tableName, blockPo, this::wheresKey).append(" limit 1").toString();
		}

		public String selectOne(BlockPo blockPo) {
			return ProviderUtil.selectOne(tableName, blockPo, this::wheresKey).append(" limit 1").toString();
		}

		public String update(BlockPo blockPo) {
			return ProviderUtil.update(tableName, blockPo, blockId, this::sets, this::wheresKey).append(" limit 1").toString();
		}

		private void wheresAll(BlockQuery blockQuery, Set<String> wheres) {
			if (blockQuery.getBlockId() > 0) {
				wheres.add(blockId);
			}
		}

		private void wheresKey(BlockPo blockPo, Set<String> wheres) {
			if (blockPo.getBlockId() > 0) {
				wheres.add(blockId);
			}
		}

		private void sets(BlockPo blockPo, Set<String> sets) {
			if (blockPo.getBlock() != null) {
				sets.add(block);
			}
		}

	}
}
