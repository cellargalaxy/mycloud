package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.cellargalaxy.mycloud.dao.BlockDao;
import top.cellargalaxy.mycloud.model.bo.BlockBo;
import top.cellargalaxy.mycloud.model.po.BlockPo;
import top.cellargalaxy.mycloud.model.query.BlockQuery;
import top.cellargalaxy.mycloud.util.SqlUtil;

import java.util.LinkedList;
import java.util.List;

import static org.apache.ibatis.type.JdbcType.BLOB;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
@Mapper
public interface BlockMapper {
	@SelectProvider(type = BlockProvider.class, method = "insert")
	@Options(useGeneratedKeys = true, keyProperty = "blockId")
	int insert(BlockPo blockPo);

	@DeleteProvider(type = BlockProvider.class, method = "delete")
	int delete(BlockQuery blockQuery);

	@Results(id = "blockResult", value = {
			@Result(property = "blockId", column = "block_id", id = true),
			@Result(property = "block", column = "block", javaType = byte[].class, jdbcType = BLOB)
	})
	@SelectProvider(type = BlockProvider.class, method = "selectOne")
	BlockBo selectOne(BlockQuery blockQuery);

	@UpdateProvider(type = BlockProvider.class, method = "update")
	int update(BlockPo blockPo);

	class BlockProvider {
		private static final String TABLE_NAME = BlockDao.TABLE_NAME;
		private static final String blockId = TABLE_NAME + ".block_id=#{blockId}";
		private static final String block = TABLE_NAME + ".block=#{block}";

		public static final String insert(BlockPo blockPo) {
			String string = "insert into " + TABLE_NAME + "(block) " +
					"values(#{block,jdbcType=BLOB})";
			return string;
		}

		public static final String delete(BlockQuery blockQuery) {
			List<String> wheres = new LinkedList<>();
			wheresAll(blockQuery, wheres);
			StringBuilder sql = SqlUtil.createDeleteSql(TABLE_NAME, wheres);
			String string = sql.toString();
			return string;
		}

		public static final String selectOne(BlockQuery blockQuery) {
			List<String> wheres = new LinkedList<>();
			wheresKey(blockQuery, wheres);
			StringBuilder sql = SqlUtil.createSelectSql(null, TABLE_NAME, wheres);
			String string = sql.append(" limit 1").toString();
			return string;
		}

		public static final String update(BlockPo blockPo) {
			List<String> sets = new LinkedList<>();
			sets(blockPo, sets);
			if (sets.size() == 0) {
				return "update " + TABLE_NAME + " set block_id=#{blockId} where false";
			}
			List<String> wheres = new LinkedList<>();
			wheresKey(blockPo, wheres);
			String string = SqlUtil.createUpdateSql(TABLE_NAME, sets, wheres).append(" limit 1").toString();
			return string;
		}

		private static final void wheresAll(BlockQuery blockQuery, List<String> wheres) {
			if (blockQuery.getBlockId() > 0) {
				wheres.add(blockId);
			}
		}

		private static final void wheresKey(BlockPo blockPo, List<String> wheres) {
			if (blockPo.getBlockId() > 0) {
				wheres.add(blockId);
			}
		}

		private static final void sets(BlockPo blockPo, List<String> sets) {
			if (blockPo.getBlock() != null) {
				sets.add(block);
			}
		}
	}
}
