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
	String addAuthorization(AuthorizationPo authorizationPo);

	String removeAuthorization(AuthorizationPo authorizationPo);

	List<AuthorizationBo> listAuthorizationByUserId(AuthorizationQuery authorizationQuery);
}
