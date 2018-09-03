package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import top.cellargalaxy.mycloud.dao.AbstractDao;
import top.cellargalaxy.mycloud.dao.FileInfoDao;
import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;
import top.cellargalaxy.mycloud.util.ProviderUtil;
import top.cellargalaxy.mycloud.util.StringUtil;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.apache.ibatis.type.JdbcType.BIGINT;
import static org.apache.ibatis.type.JdbcType.TIMESTAMP;

/**
 * @author cellargalaxy
 * @time 2018/7/3
 */
@Mapper
public interface FileInfoMapper extends AbstractDao<FileInfoPo, FileInfoBo, FileInfoQuery> {
	@InsertProvider(type = FileInfoProvider.class, method = "insert")
	@Options(useGeneratedKeys = true, keyProperty = "fileId")
	int insert(FileInfoPo fileInfoPo);

	@DeleteProvider(type = FileInfoProvider.class, method = "delete")
	int delete(FileInfoPo fileInfoPo);

	@Results(id = "fileInfoResult", value = {
			@Result(property = "fileId", column = "file_id", id = true),
			@Result(property = "md5", column = "md5"),
			@Result(property = "fileLength", column = "file_length", javaType = long.class, jdbcType = BIGINT),
			@Result(property = "contentType", column = "content_type"),
			@Result(property = "createTime", column = "create_time", javaType = Date.class, jdbcType = TIMESTAMP)
	})
	@SelectProvider(type = FileInfoProvider.class, method = "selectOne")
	FileInfoBo selectOne(FileInfoPo fileInfoPo);

	@ResultMap(value = "fileInfoResult")
	@SelectProvider(type = FileInfoProvider.class, method = "selectSome")
	List<FileInfoBo> selectSome(FileInfoQuery fileInfoQuery);

	@SelectProvider(type = FileInfoProvider.class, method = "selectCount")
	int selectCount(FileInfoQuery fileInfoQuery);

	@ResultMap(value = "fileInfoResult")
	@SelectProvider(type = FileInfoProvider.class, method = "selectAll")
	List<FileInfoBo> selectAll();

	@SelectProvider(type = FileInfoProvider.class, method = "selectContentType")
	List<String> selectContentType();

	@UpdateProvider(type = FileInfoProvider.class, method = "update")
	int update(FileInfoPo fileInfoPo);

	class FileInfoProvider /*implements AbstractProvider<FileInfoPo, FileInfoQuery>*/ {
		private String tableName = FileInfoDao.TABLE_NAME;
		private String fileId = tableName + ".file_id=#{fileId}";
		private String md5 = tableName + ".md5=#{md5}";
		private String fileLength = tableName + ".file_length=#{fileLength}";
		private String contentType = tableName + ".content_type=#{contentType}";
		private String createTime = tableName + ".create_time=#{createTime,jdbcType=TIMESTAMP}";

		public String insert(FileInfoPo fileInfoPo) {
			init(fileInfoPo);
			fileInfoPo.setCreateTime(new Date());

			String string = "insert into " + tableName + "(md5,file_length,content_type,create_time) " +
					"values(#{md5},#{fileLength},#{contentType},#{createTime,jdbcType=TIMESTAMP})";
			return string;
		}

		public String delete(FileInfoPo fileInfoPo) {
			return ProviderUtil.delete(tableName, fileInfoPo, this::wheresKey).append(" limit 1").toString();
		}

		public String selectOne(FileInfoPo fileInfoPo) {
			return ProviderUtil.selectOne(tableName, fileInfoPo, this::wheresKey).append(" limit 1").toString();
		}

		public String selectSome(FileInfoQuery fileInfoQuery) {
			return ProviderUtil.selectSome(tableName, fileInfoQuery, this::wheresAll).append(" order by create_time desc limit #{off},#{len}").toString();
		}

		public String selectCount(FileInfoQuery fileInfoQuery) {
			return ProviderUtil.selectCount(tableName, fileInfoQuery, this::wheresAll).toString();
		}

		public String selectAll() {
			return ProviderUtil.selectAll(tableName).toString();
		}

		public String selectContentType() {
			return "select distinct content_type from " + tableName;
		}

		public String update(FileInfoPo fileInfoPo) {
			init(fileInfoPo);
			fileInfoPo.setCreateTime(null);

			return ProviderUtil.update(tableName, fileInfoPo, fileId, this::sets, this::wheresKey).append(" limit 1").toString();
		}

		private void wheresAll(FileInfoQuery fileInfoQuery, Set<String> wheres) {
			if (fileInfoQuery.getFileId() > 0) {
				wheres.add(fileId);
			}
			if (!StringUtil.isBlank(fileInfoQuery.getMd5())) {
				wheres.add(md5);
			}
			if (fileInfoQuery.getFileLength() > 0) {
				wheres.add(fileLength);
			}
			if (!StringUtil.isBlank(fileInfoQuery.getContentType())) {
				wheres.add(contentType);
			}
			if (fileInfoQuery.getCreateTime() != null) {
				wheres.add(createTime);
			}
		}

		private void wheresKey(FileInfoPo fileInfoPo, Set<String> wheres) {
			if (fileInfoPo.getFileId() > 0) {
				wheres.add(fileId);
			} else if (!StringUtil.isBlank(fileInfoPo.getMd5())) {
				wheres.add(md5);
			}
		}

		private void sets(FileInfoPo fileInfoPo, Set<String> sets) {
			if (!StringUtil.isBlank(fileInfoPo.getMd5())) {
				sets.add(md5);
			}
			if (fileInfoPo.getFileLength() > 0) {
				sets.add(fileLength);
			}
			if (!StringUtil.isBlank(fileInfoPo.getContentType())) {
				sets.add(contentType);
			}
		}

		private void init(FileInfoPo fileInfoPo) {
			if (fileInfoPo.getMd5() != null) {
				fileInfoPo.setMd5(fileInfoPo.getMd5().trim());
			}
			if (fileInfoPo.getContentType() != null) {
				fileInfoPo.setContentType(fileInfoPo.getContentType().trim());
			}
		}
	}
}
