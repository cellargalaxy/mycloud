package top.cellargalaxy.mycloud.dao.mapper.provider;

import org.junit.Assert;
import org.junit.Test;
import top.cellargalaxy.mycloud.dao.mapper.FileInfoMapper;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;

/**
 * @author cellargalaxy
 * @time 2018/12/14
 */
public class FileInfoProviderTest /*implements FileInfoDao*/ {
    FileInfoMapper.FileInfoProvider fileInfoProvider = new FileInfoMapper.FileInfoProvider();

    @Test
    public void insert() {
        FileInfoPo fileInfoPo = new FileInfoPo();
//        System.out.println("insert");
//        System.out.println(fileInfoProvider.insert(fileInfoPo));
//        System.out.println();
        Assert.assertEquals(fileInfoProvider.insert(fileInfoPo), "INSERT INTO file_info\n" +
                " (file_id, md5, file_length, content_type, create_time)\n" +
                "VALUES (#{fileId}, #{md5}, #{fileLength}, #{contentType}, #{createTime})");
    }


    @Test
    public void delete() {
        FileInfoPo fileInfoPo = new FileInfoPo();
        fileInfoPo.setMd5("md5");
//        System.out.println("delete");
//        System.out.println(fileInfoProvider.delete(fileInfoPo));
//        System.out.println();
        Assert.assertEquals(fileInfoProvider.delete(fileInfoPo), "DELETE FROM file_info\n" +
                "WHERE (md5=#{md5}) limit 1");
    }

    @Test
    public void update() {
        FileInfoPo fileInfoPo = new FileInfoPo();
        fileInfoPo.setFileId(1);
        fileInfoPo.setContentType("setContentType");
//        System.out.println("update");
//        System.out.println(fileInfoProvider.update(fileInfoPo));
//        System.out.println();
        Assert.assertEquals(fileInfoProvider.update(fileInfoPo), "UPDATE file_info\n" +
                "SET file_id=#{fileId}\n" +
                "WHERE (file_id=#{fileId}) limit 1");
    }

    @Test
    public void selectOne() {
        FileInfoPo fileInfoPo = new FileInfoPo();
        fileInfoPo.setMd5("md5");
//        System.out.println("selectOne");
//        System.out.println(fileInfoProvider.selectOne(fileInfoPo));
//        System.out.println();
        Assert.assertEquals(fileInfoProvider.selectOne(fileInfoPo), "SELECT *\n" +
                "FROM file_info\n" +
                "WHERE (md5=#{md5}) limit 1");
    }

    @Test
    public void selectPageSome() {
        FileInfoQuery fileInfoQuery = new FileInfoQuery();
        fileInfoQuery.setMd5("md5");
//        System.out.println("selectPageSome");
//        System.out.println(fileInfoProvider.selectPageSome(fileInfoQuery));
//        System.out.println();
        Assert.assertEquals(fileInfoProvider.selectPageSome(fileInfoQuery), "SELECT *\n" +
                "FROM file_info\n" +
                "WHERE (md5=#{md5}) limit #{off},#{len}");
    }

    @Test
    public void selectAllSome() {
        FileInfoQuery fileInfoQuery = new FileInfoQuery();
        fileInfoQuery.setMd5("md5");
//        System.out.println("selectAllSome");
//        System.out.println(fileInfoProvider.selectAllSome(fileInfoQuery));
//        System.out.println();
        Assert.assertEquals(fileInfoProvider.selectAllSome(fileInfoQuery), "SELECT *\n" +
                "FROM file_info\n" +
                "WHERE (md5=#{md5})");
    }

    @Test
    public void selectCount() {
        FileInfoQuery fileInfoQuery = new FileInfoQuery();
        fileInfoQuery.setMd5("md5");
//        System.out.println("selectCount");
//        System.out.println(fileInfoProvider.selectCount(fileInfoQuery));
//        System.out.println();
        Assert.assertEquals(fileInfoProvider.selectCount(fileInfoQuery), "SELECT count(*)\n" +
                "FROM file_info\n" +
                "WHERE (md5=#{md5})");
    }

    @Test
    public void selectAll() {
//        System.out.println("selectAll");
//        System.out.println(fileInfoProvider.selectAll());
//        System.out.println();
        Assert.assertEquals(fileInfoProvider.selectAll(), "SELECT *\n" +
                "FROM file_info");
    }

    @Test
    public void selectAllContentType() {
//        System.out.println("selectAllContentType");
//        System.out.println(fileInfoProvider.selectAllContentType());
//        System.out.println();
        Assert.assertEquals(fileInfoProvider.selectAllContentType(), "SELECT distinct file_info.content_type\n" +
                "FROM file_info");
    }
}
