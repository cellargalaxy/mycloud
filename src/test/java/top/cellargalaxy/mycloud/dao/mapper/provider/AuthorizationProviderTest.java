package top.cellargalaxy.mycloud.dao.mapper.provider;

import org.junit.Test;
import top.cellargalaxy.mycloud.dao.mapper.AuthorizationMapper;
import top.cellargalaxy.mycloud.model.po.AuthorizationPo;
import top.cellargalaxy.mycloud.model.po.Permission;
import top.cellargalaxy.mycloud.model.query.AuthorizationQuery;

/**
 * @author cellargalaxy
 * @time 2018/12/14
 */
public class AuthorizationProviderTest /*implements IProvider<AuthorizationPo, AuthorizationQuery>*/ {

    @Test
    public void insert() {
        AuthorizationPo authorizationPo = new AuthorizationPo();
        AuthorizationMapper.AuthorizationProvider authorizationProvider = new AuthorizationMapper.AuthorizationProvider();
        System.out.println("insert");
        System.out.println(authorizationProvider.insert(authorizationPo));
        System.out.println();
    }

    @Test
    public void delete() {
        AuthorizationPo authorizationPo = new AuthorizationPo();
        authorizationPo.setAuthorizationId(3);
        AuthorizationMapper.AuthorizationProvider authorizationProvider = new AuthorizationMapper.AuthorizationProvider();
        System.out.println("delete");
        System.out.println(authorizationProvider.delete(authorizationPo));
        System.out.println();
    }

    @Test
    public void update() {
        AuthorizationPo authorizationPo = new AuthorizationPo();
        authorizationPo.setPermission(Permission.ADMIN);
        AuthorizationMapper.AuthorizationProvider authorizationProvider = new AuthorizationMapper.AuthorizationProvider();
        System.out.println("update");
        System.out.println(authorizationProvider.update(authorizationPo));
        System.out.println();
    }

    @Test
    public void selectOne() {
        AuthorizationPo authorizationPo = new AuthorizationPo();
        authorizationPo.setUserId(5);
        authorizationPo.setPermission(Permission.ADMIN);
        AuthorizationMapper.AuthorizationProvider authorizationProvider = new AuthorizationMapper.AuthorizationProvider();
        System.out.println("selectOne");
        System.out.println(authorizationProvider.selectOne(authorizationPo));
        System.out.println();
    }

    @Test
    public void selectPageSome() {
        AuthorizationQuery authorizationQuery = new AuthorizationQuery();
        authorizationQuery.setUserId(5);
        AuthorizationMapper.AuthorizationProvider authorizationProvider = new AuthorizationMapper.AuthorizationProvider();
        System.out.println("selectPageSome");
        System.out.println(authorizationProvider.selectPageSome(authorizationQuery));
        System.out.println();
    }

    @Test
    public void selectAllSome() {
        AuthorizationQuery authorizationQuery = new AuthorizationQuery();
        authorizationQuery.setUserId(5);
        AuthorizationMapper.AuthorizationProvider authorizationProvider = new AuthorizationMapper.AuthorizationProvider();
        System.out.println("selectAllSome");
        System.out.println(authorizationProvider.selectAllSome(authorizationQuery));
        System.out.println();
    }

    @Test
    public void selectCount() {
        AuthorizationQuery authorizationQuery = new AuthorizationQuery();
        authorizationQuery.setUserId(5);
        AuthorizationMapper.AuthorizationProvider authorizationProvider = new AuthorizationMapper.AuthorizationProvider();
        System.out.println("selectCount");
        System.out.println(authorizationProvider.selectCount(authorizationQuery));
        System.out.println();
    }

    @Test
    public void selectAll() {
        AuthorizationMapper.AuthorizationProvider authorizationProvider = new AuthorizationMapper.AuthorizationProvider();
        System.out.println("selectAll");
        System.out.println(authorizationProvider.selectAll());
        System.out.println();
    }
}
