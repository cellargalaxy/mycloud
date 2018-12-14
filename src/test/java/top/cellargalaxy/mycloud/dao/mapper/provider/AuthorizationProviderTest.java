package top.cellargalaxy.mycloud.dao.mapper.provider;

import org.junit.Assert;
import org.junit.Test;
import top.cellargalaxy.mycloud.dao.mapper.AuthorizationMapper;
import top.cellargalaxy.mycloud.model.po.AuthorizationPo;
import top.cellargalaxy.mycloud.model.po.Permission;
import top.cellargalaxy.mycloud.model.query.AuthorizationQuery;

/**
 * @author cellargalaxy
 * @time 2018/12/14
 */
public class AuthorizationProviderTest /*implements AuthorizationDao*/ {
    private final AuthorizationMapper.AuthorizationProvider authorizationProvider = new AuthorizationMapper.AuthorizationProvider();

    @Test
    public void insert() {
        AuthorizationPo authorizationPo = new AuthorizationPo();
//        System.out.println("insert");
//        System.out.println(authorizationProvider.insert(authorizationPo));
//        System.out.println();
        Assert.assertEquals(authorizationProvider.insert(authorizationPo), "INSERT INTO authorization\n" +
                " (authorization_id, user_id, permission, create_time, update_time)\n" +
                "VALUES (#{authorizationId}, #{userId}, #{permission}, #{createTime}, #{updateTime})");
    }

    @Test
    public void delete() {
        AuthorizationPo authorizationPo = new AuthorizationPo();
        authorizationPo.setAuthorizationId(3);
//        System.out.println("delete");
//        System.out.println(authorizationProvider.delete(authorizationPo));
//        System.out.println();
        Assert.assertEquals(authorizationProvider.delete(authorizationPo), "DELETE FROM authorization\n" +
                "WHERE (authorization_id=#{authorizationId}) limit 1");
    }

    @Test
    public void update() {
        AuthorizationPo authorizationPo = new AuthorizationPo();
        authorizationPo.setPermission(Permission.ADMIN);
//        System.out.println("update");
//        System.out.println(authorizationProvider.update(authorizationPo));
//        System.out.println();
        Assert.assertEquals(authorizationProvider.update(authorizationPo), "UPDATE authorization\n" +
                "SET permission=#{permission}\n" +
                "WHERE (false) limit 1");
    }

    @Test
    public void selectOne() {
        AuthorizationPo authorizationPo = new AuthorizationPo();
        authorizationPo.setUserId(5);
        authorizationPo.setPermission(Permission.ADMIN);
//        System.out.println("selectOne");
//        System.out.println(authorizationProvider.selectOne(authorizationPo));
//        System.out.println();
        Assert.assertEquals(authorizationProvider.selectOne(authorizationPo), "SELECT authorization.authorization_id, authorization.user_id, authorization.permission, authorization.create_time, authorization.update_time, user.username\n" +
                "FROM authorization, user\n" +
                "WHERE (authorization.user_id=user.user_id AND permission=#{permission} AND user_id=#{userId}) limit 1");
    }

    @Test
    public void selectPageSome() {
        AuthorizationQuery authorizationQuery = new AuthorizationQuery();
        authorizationQuery.setUserId(5);
//        System.out.println("selectPageSome");
//        System.out.println(authorizationProvider.selectPageSome(authorizationQuery));
//        System.out.println();
        Assert.assertEquals(authorizationProvider.selectPageSome(authorizationQuery), "SELECT authorization.authorization_id, authorization.user_id, authorization.permission, authorization.create_time, authorization.update_time, user.username\n" +
                "FROM authorization, user\n" +
                "WHERE (authorization.user_id=user.user_id AND user_id=#{userId}) limit #{off},#{len}");
    }

    @Test
    public void selectAllSome() {
        AuthorizationQuery authorizationQuery = new AuthorizationQuery();
        authorizationQuery.setUserId(5);
//        System.out.println("selectAllSome");
//        System.out.println(authorizationProvider.selectAllSome(authorizationQuery));
//        System.out.println();
        Assert.assertEquals(authorizationProvider.selectAllSome(authorizationQuery), "SELECT authorization.authorization_id, authorization.user_id, authorization.permission, authorization.create_time, authorization.update_time, user.username\n" +
                "FROM authorization, user\n" +
                "WHERE (authorization.user_id=user.user_id AND user_id=#{userId})");
    }

    @Test
    public void selectCount() {
        AuthorizationQuery authorizationQuery = new AuthorizationQuery();
        authorizationQuery.setUserId(5);
//        System.out.println("selectCount");
//        System.out.println(authorizationProvider.selectCount(authorizationQuery));
//        System.out.println();
        Assert.assertEquals(authorizationProvider.selectCount(authorizationQuery), "SELECT count(*)\n" +
                "FROM authorization\n" +
                "WHERE (user_id=#{userId})");
    }

    @Test
    public void selectAll() {
//        System.out.println("selectAll");
//        System.out.println(authorizationProvider.selectAll());
//        System.out.println();
        Assert.assertEquals(authorizationProvider.selectAll(), "SELECT authorization.authorization_id, authorization.user_id, authorization.permission, authorization.create_time, authorization.update_time, user.username\n" +
                "FROM authorization, user\n" +
                "WHERE (authorization.user_id=user.user_id)");
    }
}
