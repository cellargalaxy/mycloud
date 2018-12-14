package top.cellargalaxy.mycloud.dao.mapper.provider;

import org.junit.Assert;
import org.junit.Test;
import top.cellargalaxy.mycloud.dao.mapper.OwnExpireMapper;
import top.cellargalaxy.mycloud.model.po.OwnExpirePo;
import top.cellargalaxy.mycloud.model.query.OwnExpireQuery;

import java.util.Date;

/**
 * @author cellargalaxy
 * @time 2018/12/14
 */
public class OwnExpireProviderTest /*implements OwnExpireDao*/ {
    private final OwnExpireMapper.OwnExpireProvider ownExpireProvider = new OwnExpireMapper.OwnExpireProvider();

    @Test
    public void insert() {
        OwnExpirePo ownExpirePo = new OwnExpirePo();
//        System.out.println("insert");
//        System.out.println(ownExpireProvider.insert(ownExpirePo));
//        System.out.println();
        Assert.assertEquals(ownExpireProvider.insert(ownExpirePo), "INSERT INTO own_expire\n" +
                " (own_id, expire_time)\n" +
                "VALUES (#{ownId}, #{expireTime})");
    }

    @Test
    public void delete() {
        OwnExpirePo ownExpirePo = new OwnExpirePo();
        ownExpirePo.setOwnId(1);
//        System.out.println("delete");
//        System.out.println(ownExpireProvider.delete(ownExpirePo));
//        System.out.println();
        Assert.assertEquals(ownExpireProvider.delete(ownExpirePo), "DELETE FROM own_expire\n" +
                "WHERE (own_id=#{ownId}) limit 1");
    }

    @Test
    public void update() {
        OwnExpirePo ownExpirePo = new OwnExpirePo();
        ownExpirePo.setOwnId(4);
        ownExpirePo.setExpireTime(new Date());
//        System.out.println("update");
//        System.out.println(ownExpireProvider.update(ownExpirePo));
//        System.out.println();
        Assert.assertEquals(ownExpireProvider.update(ownExpirePo), "UPDATE own_expire\n" +
                "SET own_id=#{ownId}\n" +
                "WHERE (own_id=#{ownId}) limit 1");
    }

    @Test
    public void selectOne() {
        OwnExpirePo ownExpirePo = new OwnExpirePo();
        ownExpirePo.setOwnId(2);
//        System.out.println("selectOne");
//        System.out.println(ownExpireProvider.selectOne(ownExpirePo));
//        System.out.println();
        Assert.assertEquals(ownExpireProvider.selectOne(ownExpirePo), "SELECT *\n" +
                "FROM own_expire\n" +
                "WHERE (own_id=#{ownId}) limit 1");
    }

    @Test
    public void selectPageSome() {
        OwnExpireQuery ownExpireQuery = new OwnExpireQuery();
        ownExpireQuery.setOwnId(2);
//        System.out.println("selectPageSome");
//        System.out.println(ownExpireProvider.selectPageSome(ownExpireQuery));
//        System.out.println();
        Assert.assertEquals(ownExpireProvider.selectPageSome(ownExpireQuery), "SELECT *\n" +
                "FROM own_expire\n" +
                "WHERE (own_id=#{ownId}) limit #{off},#{len}");
    }

    @Test
    public void selectAllSome() {
        OwnExpireQuery ownExpireQuery = new OwnExpireQuery();
        ownExpireQuery.setOwnId(2);
//        System.out.println("selectAllSome");
//        System.out.println(ownExpireProvider.selectAllSome(ownExpireQuery));
//        System.out.println();
        Assert.assertEquals(ownExpireProvider.selectAllSome(ownExpireQuery), "SELECT *\n" +
                "FROM own_expire\n" +
                "WHERE (own_id=#{ownId})");
    }

    @Test
    public void selectCount() {
        OwnExpireQuery ownExpireQuery = new OwnExpireQuery();
        ownExpireQuery.setOwnId(2);
//        System.out.println("selectCount");
//        System.out.println(ownExpireProvider.selectCount(ownExpireQuery));
//        System.out.println();
        Assert.assertEquals(ownExpireProvider.selectCount(ownExpireQuery), "SELECT count(*)\n" +
                "FROM own_expire\n" +
                "WHERE (own_id=#{ownId})");
    }

    @Test
    public void selectAll() {
//        System.out.println("selectAll");
//        System.out.println(ownExpireProvider.selectAll());
//        System.out.println();
        Assert.assertEquals(ownExpireProvider.selectAll(), "SELECT *\n" +
                "FROM own_expire");
    }

    @Test
    public void selectExpireOwnExpire() {
        OwnExpireQuery ownExpireQuery = new OwnExpireQuery();
        ownExpireQuery.setOwnId(2);
        ownExpireQuery.setExpireTime(new Date());
//        System.out.println("selectExpireOwnExpire");
//        System.out.println(ownExpireProvider.selectExpireOwnExpire(ownExpireQuery));
//        System.out.println();
        Assert.assertEquals(ownExpireProvider.selectExpireOwnExpire(ownExpireQuery), "SELECT *\n" +
                "FROM own_expire\n" +
                "WHERE (expire_time<=#{expireTime})");
    }

    @Test
    public void selectRecentExpireOwn() {
        OwnExpireQuery ownExpireQuery = new OwnExpireQuery();
        ownExpireQuery.setOwnId(2);
//        System.out.println("selectAll");
//        System.out.println(ownExpireProvider.selectRecentExpireOwn(ownExpireQuery));
//        System.out.println();
        Assert.assertEquals(ownExpireProvider.selectRecentExpireOwn(ownExpireQuery), "SELECT *\n" +
                "FROM own_expire, own\n" +
                "WHERE (own_expire.own_id=own.own_id)\n" +
                "ORDER BY expire_time desc limit #{off},#{len}");
    }
}
