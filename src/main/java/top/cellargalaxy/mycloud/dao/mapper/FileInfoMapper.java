package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import top.cellargalaxy.mycloud.dao.FileInfoDao;
import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;
import top.cellargalaxy.mycloud.util.StringUtils;
import top.cellargalaxy.mycloud.util.dao.IDao;
import top.cellargalaxy.mycloud.util.dao.ProviderUtils;
import top.cellargalaxy.mycloud.util.dao.SqlUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author cellargalaxy
 * @time 2018/10/25
 */
@Mapper
public interface FileInfoMapper extends IDao<FileInfoPo, FileInfoBo, FileInfoQuery> {
	@Options(useGeneratedKeys = true, keyProperty = "fileId")
	@InsertProvider(type = FileInfoProvider.class, method = "insert")
	int insert(FileInfoPo fileInfoPo);

	@DeleteProvider(type = FileInfoProvider.class, method = "delete")
	int delete(FileInfoPo fileInfoPo);

	@UpdateProvider(type = FileInfoProvider.class, method = "update")
	int update(FileInfoPo fileInfoPo);

	@Results(id = "fileInfoResults", value = {
			@Result(property = "fileId", column = "file_id"),
			@Result(property = "md5", column = "md5"),
			@Result(property = "fileLength", column = "file_length"),
			@Result(property = "contentType", column = "content_type"),
			@Result(property = "createTime", column = "create_time"),
	})
	@SelectProvider(type = FileInfoProvider.class, method = "selectOne")
	FileInfoBo selectOne(FileInfoPo fileInfoPo);

	@ResultMap(value = "fileInfoResults")
	@SelectProvider(type = FileInfoProvider.class, method = "selectPageSome")
	List<FileInfoBo> selectPageSome(FileInfoQuery fileInfoQuery);

	@ResultMap(value = "fileInfoResults")
	@SelectProvider(type = FileInfoProvider.class, method = "selectAllSome")
	List<FileInfoBo> selectAllSome(FileInfoQuery fileInfoQuery);

	@SelectProvider(type = FileInfoProvider.class, method = "selectCount")
	int selectCount(FileInfoQuery fileInfoQuery);

	@ResultMap(value = "fileInfoResults")
	@SelectProvider(type = FileInfoProvider.class, method = "selectAll")
	List<FileInfoBo> selectAll();

	@SelectProvider(type = FileInfoProvider.class, method = "selectAllContentType")
	List<String> selectAllContentType();

	class FileInfoProvider /*implements IProvider<FileInfoPo,FileInfoQuery>*/ {
		private final String tableName = FileInfoDao.TABLE_NAME;

		public Set<String> wheresKey(FileInfoPo fileinfoPo) {
			Set<String> wheres = new HashSet<>();
			if (fileinfoPo.getFileId() > 0) {
				wheres.add("fileId");
				return wheres;
			}
			if (!StringUtils.isBlank(fileinfoPo.getMd5())) {
				wheres.add("md5");
				return wheres;
			}
			wheres.add("fileId");
			return wheres;
		}

		public Set<String> wheresAll(FileInfoQuery fileinfoQuery) {
			Set<String> wheres = new HashSet<>();
			if (fileinfoQuery.getFileId() > 0) {
				wheres.add("fileId");
			}
			if (!StringUtils.isBlank(fileinfoQuery.getMd5())) {
				wheres.add("md5");
			}
			return wheres;
		}

		/**
		 * 只有删除，没有修改
		 *
		 * @param fileinfoPo
		 * @return
		 */
		public Set<String> sets(FileInfoPo fileinfoPo) {
			Set<String> sets = new HashSet<>();

			return sets;
		}

		public String insert(FileInfoPo fileinfoPo) {
			String string = ProviderUtils.insert(tableName, FileInfoPo.class).toString();
			return string;
		}

		public String delete(FileInfoPo fileinfoPo) {
			String string = ProviderUtils.delete(tableName, wheresKey(fileinfoPo)).toString();
			return string;
		}

		public String update(FileInfoPo fileinfoPo) {
			String string = null;//ProviderUtils.limitOne(ProviderUtils.update(tableName, sets(fileinfoPo), "defaultSet", wheresKey(fileinfoPo))).toString();
			return string;
		}

		public String selectOne(FileInfoPo fileinfoPo) {
			String string = ProviderUtils.limitOne(ProviderUtils.select(tableName, null, wheresKey(fileinfoPo))).toString();
			return string;
		}

		public String selectPageSome(FileInfoQuery fileinfoQuery) {
			SqlUtils.initPageQuery(fileinfoQuery);
			String string = ProviderUtils.limitSome(ProviderUtils.select(tableName, null, wheresAll(fileinfoQuery))).toString();
			return string;
		}

		public String selectAllSome(FileInfoQuery fileinfoQuery) {
			String string = ProviderUtils.select(tableName, null, wheresAll(fileinfoQuery)).toString();
			return string;
		}

		public String selectCount(FileInfoQuery fileinfoQuery) {
			String string = ProviderUtils.selectCount(tableName, wheresAll(fileinfoQuery)).toString();
			return string;
		}

		public String selectAll() {
			String string = ProviderUtils.selectAll(tableName, null).toString();
			return string;
		}

		public String selectAllContentType() {
			SQL sql = new SQL().SELECT("distinct " + ProviderUtils.column(tableName, "contentType")).FROM(tableName);
			String string = sql.toString();
			return string;
		}
	}
}
