package top.cellargalaxy.mycloud.dao.mapper.provider;

import org.junit.Assert;
import org.junit.Test;
import top.cellargalaxy.mycloud.dao.mapper.UserMapper;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.UserQuery;

/**
 * @author cellargalaxy
 * @time 2018/12/14
 */
public class UserProviderTest /*implements UserDao*/ {
    private final UserMapper.UserProvider userProvider = new UserMapper.UserProvider();

    @Test
    public void insert() {
        UserPo userPo = new UserPo();
//        System.out.println("insert");
//        System.out.println(userProvider.insert(userPo));
//        System.out.println();
        Assert.assertEquals(userProvider.insert(userPo), "INSERT INTO user\n" +
                " (user_id, username, password, create_time, update_time)\n" +
                "VALUES (#{userId}, #{username}, #{password}, #{createTime}, #{updateTime})");
    }

    @Test
    public void delete() {
        UserPo userPo = new UserPo();
        userPo.setUsername("aaa");
//        System.out.println("delete");
//        System.out.println(userProvider.delete(userPo));
//        System.out.println();
        Assert.assertEquals(userProvider.delete(userPo), "DELETE FROM user\n" +
                "WHERE (username=#{username}) limit 1");
    }

    @Test
    public void update() {
        UserPo userPo = new UserPo();
        userPo.setUsername("aaa");
//        System.out.println("update");
//        System.out.println(userProvider.update(userPo));
//        System.out.println();
        Assert.assertEquals(userProvider.update(userPo), "UPDATE user\n" +
                "SET username=#{username}\n" +
                "WHERE (username=#{username}) limit 1");
    }

    @Test
    public void selectOne() {
        UserPo userPo = new UserPo();
        userPo.setUsername("aaa");
//        System.out.println("selectOne");
//        System.out.println(userProvider.selectOne(userPo));
//        System.out.println();
        Assert.assertEquals(userProvider.selectOne(userPo), "SELECT *\n" +
                "FROM user\n" +
                "WHERE (username=#{username}) limit 1");
    }

    @Test
    public void selectPageSome() {
        UserQuery userQuery = new UserQuery();
        userQuery.setUsername("aaa");
//        System.out.println("selectPageSome");
//        System.out.println(userProvider.selectPageSome(userQuery));
//        System.out.println();
        Assert.assertEquals(userProvider.selectPageSome(userQuery), "SELECT *\n" +
                "FROM user\n" +
                "WHERE (username=#{username}) limit #{off},#{len}");
    }

    @Test
    public void selectAllSome() {
        UserQuery userQuery = new UserQuery();
        userQuery.setUsername("aaa");
//        System.out.println("selectAllSome");
//        System.out.println(userProvider.selectAllSome(userQuery));
//        System.out.println();
        Assert.assertEquals(userProvider.selectAllSome(userQuery), "SELECT *\n" +
                "FROM user\n" +
                "WHERE (username=#{username})");
    }

    @Test
    public void selectCount() {
        UserQuery userQuery = new UserQuery();
        userQuery.setUsername("aaa");
//        System.out.println("selectCount");
//        System.out.println(userProvider.selectCount(userQuery));
//        System.out.println();
        Assert.assertEquals(userProvider.selectCount(userQuery), "SELECT count(*)\n" +
                "FROM user\n" +
                "WHERE (username=#{username})");
    }

    @Test
    public void selectAll() {
//        System.out.println("selectAll");
//        System.out.println(userProvider.selectAll());
//        System.out.println();
        Assert.assertEquals(userProvider.selectAll(), "SELECT *\n" +
                "FROM user");
    }
}
