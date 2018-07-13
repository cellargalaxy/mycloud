package top.cellargalaxy.newcloud.dao.db;

import org.apache.ibatis.annotations.*;
import top.cellargalaxy.newcloud.model.po.FileDataPo;
import top.cellargalaxy.newcloud.model.query.FileDataQuery;

import static org.apache.ibatis.type.JdbcType.BLOB;

/**
 * @author cellargalaxy
 * @time 2018/7/3
 */
@Mapper
public interface FileDataMapper {
	@InsertProvider(type = FileDataDaoProvider.class, method = "insert")
	int insert(FileDataPo fileDataPo);

	@DeleteProvider(type = FileDataDaoProvider.class, method = "delete")
	int delete(FileDataQuery fileDataQuery);

	@Results(id = "fileDataResult", value = {
			@Result(property = "fileId", column = "file_id", id = true),
			@Result(property = "fileData", column = "file_data", javaType = byte[].class, jdbcType = BLOB),
	})
	@SelectProvider(type = FileDataDaoProvider.class, method = "select")
	FileDataPo select(FileDataQuery fileDataQuery);

	@UpdateProvider(type = FileDataDaoProvider.class, method = "update")
	int update(FileDataPo fileDataPo);

	class FileDataDaoProvider {
		public static final String TABLE_NAME = "file_data";

		public static final String insert(FileDataPo fileDataPo) {
			return "insert into " + TABLE_NAME + "(file_id,file_data) values(#{fileId},#{fileData,jdbcType=BLOB})";
		}

		public static final String delete(FileDataQuery fileDataQuery) {
			return "delete from " + TABLE_NAME + " where file_id=#{fileId} limit 1";
		}

		public static final String select(FileDataQuery fileDataQuery) {
			return "select * from " + TABLE_NAME + " where file_id=#{fileId} limit 1";
		}

		public static final String update(FileDataPo fileDataPo) {
			return "update " + TABLE_NAME + " set file_data=#{fileData,jdbcType=BLOB} where file_id=#{fileId} limit 1";
		}

		public static String checkInsert(FileDataPo fileDataPo) {
			if (fileDataPo.getFileId() < 1) {
				return "文件编号不得为空";
			}
			if (fileDataPo.getFileData() == null) {
				return "文件数据不得为空";
			}
			if (fileDataPo.getFileData().length > 16777215) {
				return "为数据不得大于16777215个字节";
			}
			return null;
		}
	}
}
