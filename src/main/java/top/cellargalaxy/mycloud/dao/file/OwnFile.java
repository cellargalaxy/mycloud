package top.cellargalaxy.mycloud.dao.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Repository;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.dao.mapper.OwnMapper;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.query.OwnQuery;
import top.cellargalaxy.mycloud.util.IOUtil;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cellargalaxy
 * @time 2018/10/30
 */
@ConditionalOnMissingBean(DataSource.class)
@Repository
public class OwnFile implements OwnMapper {
	private final String separator = "\t";
	private final File file;
	private final List<OwnBo> ownBos;

	@Autowired
	public OwnFile(MycloudConfiguration mycloudConfiguration) throws IOException {
		file = null;
		ownBos = read();
	}

	@Override
	public synchronized int insert(OwnPo ownPo) {
		try {
			OwnBo ownBo = new OwnBo();
			ownBo.setOwnId(ownPo.getOwnId());
			ownBo.setOwnUuid(ownPo.getOwnUuid());
			ownPo.setUserId(ownPo.getUserId());
			ownBo.setCreateTime(ownPo.getCreateTime());
			ownBo.setUpdateTime(ownPo.getUpdateTime());
			ownBo.setContentType(ownPo.getContentType());
			ownBo.setFileLength(ownPo.getFileLength());
			ownBo.setFileName(ownPo.getFileName());
			ownBo.setDescription(ownPo.getDescription());
			ownBo.setFileId(ownPo.getFileId());
			ownBo.setSort(ownPo.getSort());
			ownBos.add(ownBo);
			write();
		} catch (IOException e) {
			e.printStackTrace();
			ownBos.remove(ownBos.size());
			return 0;
		}
		return 1;
	}

	@Override
	public synchronized int delete(OwnPo ownPo) {
		return 0;
	}

	@Override
	public synchronized int update(OwnPo ownPo) {
		return 0;
	}

	@Override
	public synchronized OwnBo selectOne(OwnPo ownPo) {
		return null;
	}

	@Override
	public synchronized List<OwnBo> selectPageSome(OwnQuery ownQuery) {
		List<OwnBo> owns = ownBos.stream().filter(ownBo -> query(ownQuery, ownBo)).collect(Collectors.toList());
		int fromIndex = ownQuery.getOff();
		int toIndex = ownQuery.getOff() + ownQuery.getLen();
		if (fromIndex < 0) {
			fromIndex = 0;
		}
		if (toIndex < 0) {
			toIndex = 0;
		}
		if (fromIndex > owns.size() - 1) {
			fromIndex = owns.size() - 1;
		}
		if (toIndex > owns.size()) {
			toIndex = owns.size();
		}
		return owns.subList(fromIndex, toIndex);
	}

	@Override
	public synchronized List<OwnBo> selectAllSome(OwnQuery ownQuery) {
		return ownBos.stream().filter(ownBo -> query(ownQuery, ownBo)).collect(Collectors.toList());
	}

	@Override
	public synchronized int selectCount(OwnQuery ownQuery) {
		return ownBos.size();
	}

	@Override
	public synchronized List<OwnBo> selectAll() {
		return ownBos;
	}

	@Override
	public synchronized List<String> selectAllSort(OwnQuery ownQuery) {
		return ownBos.stream().map(ownBo -> ownBo.getSort()).distinct().collect(Collectors.toList());
	}

	private List<OwnBo> read() throws IOException {
		return null;
	}

	private void write() throws IOException {
		try (Writer writer = IOUtil.getWriter(file)) {
			for (OwnBo ownBo : ownBos) {

			}
		}
	}

	private boolean query(OwnQuery ownQuery, OwnBo ownBo) {
		return true;
	}
}
