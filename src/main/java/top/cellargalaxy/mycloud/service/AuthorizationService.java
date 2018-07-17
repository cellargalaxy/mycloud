package top.cellargalaxy.mycloud.service;

import top.cellargalaxy.mycloud.model.bo.AuthorizationBo;
import top.cellargalaxy.mycloud.model.po.AuthorizationPo;
import top.cellargalaxy.mycloud.model.query.AuthorizationQuery;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public interface AuthorizationService {
	void addAuthorization(AuthorizationPo authorizationPo);

	void removeAuthorization(AuthorizationQuery authorizationQuery);

	AuthorizationBo getAuthorization(AuthorizationQuery authorizationQuery);

	List<AuthorizationBo> listAuthorization(AuthorizationQuery authorizationQuery);

	void changeAuthorization(AuthorizationPo authorizationPo);

	String checkAddAuthorization(AuthorizationPo authorizationPo);

	String checkChangeAuthorization(AuthorizationPo authorizationPo);
}
