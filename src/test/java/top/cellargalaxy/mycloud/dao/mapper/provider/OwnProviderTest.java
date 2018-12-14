package top.cellargalaxy.mycloud.dao.mapper.provider;

import org.junit.Assert;
import org.junit.Test;
import top.cellargalaxy.mycloud.dao.mapper.OwnMapper;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.query.OwnQuery;


/**
 * @author cellargalaxy
 * @time 2018/12/14
 */
public class OwnProviderTest /*implements OwnDao*/ {
    private final OwnMapper.OwnProvider ownProvider = new OwnMapper.OwnProvider();

    @Test
    public void selectAllSort() {
        OwnQuery ownQuery = new OwnQuery();
        ownQuery.setUserId(1);
//        System.out.println("selectAllSort");
//        System.out.println(ownProvider.selectAllSort(ownQuery));
//        System.out.println();
        Assert.assertEquals(ownProvider.selectAllSort(ownQuery), "SELECT distinct own.sort\n" +
                "FROM own\n" +
                "WHERE (user_id=#{userId})");
    }

    @Test
    public void insert() {
        OwnPo ownPo = new OwnPo();
//        System.out.println("insert");
//        System.out.println(ownProvider.insert(ownPo));
//        System.out.println();
        Assert.assertEquals(ownProvider.insert(ownPo), "INSERT INTO own\n" +
                " (own_id, own_uuid, user_id, file_length, content_type, file_name, file_id, sort, description, create_time, update_time)\n" +
                "VALUES (#{ownId}, #{ownUuid}, #{userId}, #{fileLength}, #{contentType}, #{fileName}, #{fileId}, #{sort}, #{description}, #{createTime}, #{updateTime})");
    }

    @Test
    public void delete() {
        OwnPo ownPo = new OwnPo();
        ownPo.setOwnUuid("aaa");
//        System.out.println("delete");
//        System.out.println(ownProvider.delete(ownPo));
//        System.out.println();
        Assert.assertEquals(ownProvider.delete(ownPo), "DELETE FROM own\n" +
                "WHERE (own_uuid=#{ownUuid}) limit 1");
    }

    @Test
    public void update() {
        OwnPo ownPo = new OwnPo();
        ownPo.setOwnUuid("aaa");
//        System.out.println("update");
//        System.out.println(ownProvider.update(ownPo));
//        System.out.println();
        Assert.assertEquals(ownProvider.update(ownPo), "UPDATE own\n" +
                "SET own_id=#{ownId}\n" +
                "WHERE (own_uuid=#{ownUuid}) limit 1");
    }

    @Test
    public void selectOne() {
        OwnPo ownPo = new OwnPo();
        ownPo.setOwnUuid("aaa");
//        System.out.println("selectOne");
//        System.out.println(ownProvider.selectOne(ownPo));
//        System.out.println();
        Assert.assertEquals(ownProvider.selectOne(ownPo), "SELECT own.own_id, own.own_uuid, own.user_id, own.file_id, own.file_length, own.content_type, own.file_name, own.sort, own.description, own.create_time, own.update_time, user.username, file_info.md5\n" +
                "FROM own left join user on own.user_id=user.user_id left join file_info on own.file_id=file_info.file_id\n" +
                "WHERE (own_uuid=#{ownUuid}) limit 1");
    }

    @Test
    public void selectPageSome() {
        OwnQuery ownQuery = new OwnQuery();
        ownQuery.setOwnUuid("aaa");
//        System.out.println("selectPageSome");
//        System.out.println(ownProvider.selectPageSome(ownQuery));
//        System.out.println();
        Assert.assertEquals(ownProvider.selectPageSome(ownQuery), "SELECT own.own_id, own.own_uuid, own.user_id, own.file_id, own.file_length, own.content_type, own.file_name, own.sort, own.description, own.create_time, own.update_time, user.username, file_info.md5\n" +
                "FROM own left join user on own.user_id=user.user_id left join file_info on own.file_id=file_info.file_id\n" +
                "WHERE (own_uuid=#{ownUuid}) limit #{off},#{len}");
    }

    @Test
    public void selectAllSome() {
        OwnQuery ownQuery = new OwnQuery();
        ownQuery.setOwnUuid("aaa");
//        System.out.println("selectAllSome");
//        System.out.println(ownProvider.selectAllSome(ownQuery));
//        System.out.println();
        Assert.assertEquals(ownProvider.selectAllSome(ownQuery), "SELECT own.own_id, own.own_uuid, own.user_id, own.file_id, own.file_length, own.content_type, own.file_name, own.sort, own.description, own.create_time, own.update_time, user.username, file_info.md5\n" +
                "FROM own left join user on own.user_id=user.user_id left join file_info on own.file_id=file_info.file_id\n" +
                "WHERE (own_uuid=#{ownUuid})");
    }

    @Test
    public void selectCount() {
        OwnQuery ownQuery = new OwnQuery();
        ownQuery.setOwnUuid("aaa");
//        System.out.println("selectCount");
//        System.out.println(ownProvider.selectCount(ownQuery));
//        System.out.println();
        Assert.assertEquals(ownProvider.selectCount(ownQuery), "SELECT count(*)\n" +
                "FROM own\n" +
                "WHERE (own_uuid=#{ownUuid})");
    }

    @Test
    public void selectAll() {
//        System.out.println("selectAll");
//        System.out.println(ownProvider.selectAll());
//        System.out.println();
        Assert.assertEquals(ownProvider.selectAll(), "SELECT own.own_id, own.own_uuid, own.user_id, own.file_id, own.file_length, own.content_type, own.file_name, own.sort, own.description, own.create_time, own.update_time, user.username, file_info.md5\n" +
                "FROM own left join user on own.user_id=user.user_id left join file_info on own.file_id=file_info.file_id");
    }
}
