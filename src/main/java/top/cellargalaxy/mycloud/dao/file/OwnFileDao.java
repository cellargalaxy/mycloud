package top.cellargalaxy.mycloud.dao.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.dao.mapper.OwnMapper;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.query.OwnQuery;
import top.cellargalaxy.mycloud.util.IOUtils;
import top.cellargalaxy.mycloud.util.dao.SqlUtils;
import top.cellargalaxy.mycloud.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cellargalaxy
 * @time 2018/10/30
 */
//@ConditionalOnMissingBean(DataSource.class)
//@Repository
public class OwnFileDao implements OwnMapper {
    private final ObjectMapper objectMapper;
    private final File file;
    private final LinkedList<OwnBo> ownBos;

    @Autowired
    public OwnFileDao(MycloudConfiguration mycloudConfiguration) throws IOException {
        objectMapper = new ObjectMapper();
        file = new File(mycloudConfiguration.getMycloudPath() + File.separator + "mycloud" + File.separator + "own.json");
        ownBos = read();
    }

    @Override
    public synchronized int insert(OwnPo ownPo) {
        try {
            OwnBo ownBo = new OwnBo();
            ownBo.setOwnId(ownPo.getOwnId());
            ownBo.setOwnUuid(ownPo.getOwnUuid());
            ownBo.setUserId(ownPo.getUserId());
            ownBo.setCreateTime(ownPo.getCreateTime());
            ownBo.setUpdateTime(ownPo.getUpdateTime());
            ownBo.setContentType(ownPo.getContentType());
            ownBo.setFileLength(ownPo.getFileLength());
            ownBo.setFileName(ownPo.getFileName());
            ownBo.setDescription(ownPo.getDescription());
            ownBo.setFileId(ownPo.getFileId());
            ownBo.setSort(ownPo.getSort());
            ownBos.addFirst(ownBo);
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
        try {
            ownBos.removeIf(ownBo -> filterKey(ownPo, ownBo));
            write();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    @Override
    public synchronized int update(OwnPo ownPo) {
        try {
            ownBos.stream().filter(ownBo -> filterKey(ownPo, ownBo)).forEach(ownBo -> set(ownPo, ownBo));
            write();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    @Override
    public synchronized OwnBo selectOne(OwnPo ownPo) {
        return ownBos.stream().filter(ownBo -> filterKey(ownPo, ownBo)).findFirst().orElse(null);
    }

    @Override
    public synchronized List<OwnBo> selectPageSome(OwnQuery ownQuery) {
        SqlUtils.initPageQuery(ownQuery);
        return ownBos.stream().filter(ownBo -> filterAll(ownQuery, ownBo)).skip(ownQuery.getOff()).limit(ownQuery.getLen()).collect(Collectors.toList());
    }

    @Override
    public synchronized List<OwnBo> selectAllSome(OwnQuery ownQuery) {
        return ownBos.stream().filter(ownBo -> filterAll(ownQuery, ownBo)).collect(Collectors.toList());
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

    private LinkedList<OwnBo> read() throws IOException {
        if (!file.exists()) {
            return new LinkedList<>();
        }
        try (BufferedReader reader = IOUtils.getReader(file)) {
            StringBuilder stringBuilder = new StringBuilder();
            String string;
            if ((string = reader.readLine()) != null) {
                stringBuilder.append(string);
            }
            return new LinkedList<>(Arrays.asList(objectMapper.readValue(stringBuilder.toString(), OwnBo[].class)));
        }
    }

    private void write() throws IOException {
        try (Writer writer = IOUtils.getWriter(file)) {
            writer.write(objectMapper.writeValueAsString(ownBos));
        }
    }

    private boolean filterKey(OwnPo ownPo, OwnBo ownBo) {
        if (ownPo == null || ownBo == null) {
            return false;
        }
        if (ownPo.getUserId() > 0 && ownPo.getUserId() != ownBo.getUserId()) {
            return false;
        }
        if (ownPo.getOwnId() > 0) {
            return ownPo.getOwnId() == ownBo.getOwnId();
        }
        if (!StringUtils.isBlank(ownPo.getOwnUuid())) {
            return ownPo.getOwnUuid().equals(ownBo.getOwnUuid());
        }
        return false;
    }

    private boolean filterAll(OwnQuery ownQuery, OwnBo ownBo) {
        if (ownQuery.getOwnId() > 0 && ownQuery.getOwnId() != ownBo.getOwnId()) {
            return false;
        }
        if (!StringUtils.isBlank(ownQuery.getOwnUuid()) && !ownQuery.getOwnUuid().equals(ownBo.getOwnUuid())) {
            return false;
        }
        if (ownQuery.getUserId() > 0 && ownQuery.getUserId() != ownBo.getUserId()) {
            return false;
        }
        if (ownQuery.getFileId() > 0 && ownQuery.getFileId() != ownBo.getFileId()) {
            return false;
        }
        if (!StringUtils.isBlank(ownQuery.getContentType()) && !ownQuery.getContentType().equals(ownBo.getContentType())) {
            return false;
        }
        if (!StringUtils.isBlank(ownQuery.getSort()) && !ownQuery.getSort().equals(ownBo.getSort())) {
            return false;
        }
        return true;
    }

    private void set(OwnPo ownPo, OwnBo ownBo) {
        if (ownPo.getFileId() > 0) {
            ownBo.setFileId(ownPo.getFileId());
        }
        if (!StringUtils.isBlank(ownPo.getFileName())) {
            ownBo.setFileName(ownPo.getFileName());
        }
        if (!StringUtils.isBlank(ownPo.getSort())) {
            ownBo.setSort(ownPo.getSort());
        }
        if (!StringUtils.isBlank(ownPo.getDescription())) {
            ownBo.setDescription(ownPo.getDescription());
        }
        if (ownPo.getUpdateTime() != null) {
            ownBo.setUpdateTime(ownPo.getUpdateTime());
        }
    }
}
