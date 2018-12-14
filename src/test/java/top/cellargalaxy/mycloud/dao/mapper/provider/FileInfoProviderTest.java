package top.cellargalaxy.mycloud.dao.mapper.provider;

import org.junit.Test;
import top.cellargalaxy.mycloud.dao.mapper.FileInfoMapper;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;

/**
 * @author cellargalaxy
 * @time 2018/12/14
 */
public class FileInfoProviderTest /*implements IProvider<FileInfoPo, FileInfoQuery>*/ {

    @Test
    public void insert() {
        FileInfoPo fileInfoPo = new FileInfoPo();
        FileInfoMapper.FileInfoProvider fileInfoProvider = new FileInfoMapper.FileInfoProvider();
        System.out.println("insert");
        System.out.println(fileInfoProvider.insert(fileInfoPo));
        System.out.println();
    }

    @Test
    public void delete() {
        FileInfoPo fileInfoPo = new FileInfoPo();
        FileInfoMapper.FileInfoProvider fileInfoProvider = new FileInfoMapper.FileInfoProvider();
        System.out.println("delete");
        System.out.println(fileInfoProvider.delete(fileInfoPo));
        System.out.println();
    }

    @Test
    public void update() {
        FileInfoPo fileInfoPo = new FileInfoPo();
        FileInfoMapper.FileInfoProvider fileInfoProvider = new FileInfoMapper.FileInfoProvider();
        System.out.println("update");
        System.out.println(fileInfoProvider.update(fileInfoPo));
        System.out.println();

        
    }

    @Test
    public void selectOne() {
        FileInfoPo fileInfoPo = new FileInfoPo();
        FileInfoMapper.FileInfoProvider fileInfoProvider = new FileInfoMapper.FileInfoProvider();
        System.out.println("selectOne");
        System.out.println(fileInfoProvider.selectOne(fileInfoPo));
        System.out.println();
    }

    @Test
    public void selectPageSome() {
        FileInfoQuery fileInfoQuery = new FileInfoQuery();
        FileInfoMapper.FileInfoProvider fileInfoProvider = new FileInfoMapper.FileInfoProvider();
        System.out.println("selectPageSome");
        System.out.println(fileInfoProvider.selectPageSome(fileInfoQuery));
        System.out.println();
    }

    @Test
    public void selectAllSome() {
        FileInfoQuery fileInfoQuery = new FileInfoQuery();
        FileInfoMapper.FileInfoProvider fileInfoProvider = new FileInfoMapper.FileInfoProvider();
        System.out.println("selectAllSome");
        System.out.println(fileInfoProvider.selectAllSome(fileInfoQuery));
        System.out.println();
    }

    @Test
    public void selectCount() {
        FileInfoQuery fileInfoQuery = new FileInfoQuery();
        FileInfoMapper.FileInfoProvider fileInfoProvider = new FileInfoMapper.FileInfoProvider();
        System.out.println("selectCount");
        System.out.println(fileInfoProvider.selectCount(fileInfoQuery));
        System.out.println();
    }

    @Test
    public void selectAll() {
        FileInfoQuery fileInfoQuery = new FileInfoQuery();
        FileInfoMapper.FileInfoProvider fileInfoProvider = new FileInfoMapper.FileInfoProvider();
        System.out.println("selectAll");
        System.out.println(fileInfoProvider.selectAll());
        System.out.println();
    }
}
