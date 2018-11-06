package top.cellargalaxy.mycloud.dao.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Repository;
import top.cellargalaxy.mycloud.dao.mapper.AuthorizationMapper;
import top.cellargalaxy.mycloud.model.bo.AuthorizationBo;
import top.cellargalaxy.mycloud.model.bo.UserBo;
import top.cellargalaxy.mycloud.model.po.AuthorizationPo;
import top.cellargalaxy.mycloud.model.po.Permission;
import top.cellargalaxy.mycloud.model.query.AuthorizationQuery;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/10/30
 */
@ConditionalOnMissingBean(DataSource.class)
@Repository
public class AuthorizationFileDao implements AuthorizationMapper {
	private final AuthorizationBo admin;
	private final AuthorizationBo user;

	@Autowired
	public AuthorizationFileDao(UserFileDao userFileDao) {
		this.admin = new AuthorizationBo();
		this.user = new AuthorizationBo();
		UserBo userBo = userFileDao.getUserBo();
		Date date = new Date();

		admin.setAuthorizationId(1);
		admin.setUserId(userBo.getUserId());
		admin.setUsername(userBo.getUsername());
		admin.setPermission(Permission.ADMIN);
		admin.setCreateTime(date);
		admin.setUpdateTime(date);

		user.setAuthorizationId(2);
		user.setUserId(userBo.getUserId());
		user.setUsername(userBo.getUsername());
		user.setPermission(Permission.USER);
		user.setCreateTime(date);
		user.setUpdateTime(date);
	}

	@Override
	public int insert(AuthorizationPo authorizationPo) {
		return 0;
	}

	@Override
	public int delete(AuthorizationPo authorizationPo) {
		return 0;
	}

	@Override
	public int update(AuthorizationPo authorizationPo) {
		return 0;
	}

	@Override
	public AuthorizationBo selectOne(AuthorizationPo authorizationPo) {
		if (authorizationPo == null) {
			return null;
		}
		if (admin.getAuthorizationId() == authorizationPo.getAuthorizationId()
				|| (admin.getUserId() == authorizationPo.getUserId() && admin.getPermission() == authorizationPo.getPermission())) {
			return admin;
		}
		if (user.getAuthorizationId() == authorizationPo.getAuthorizationId()
				|| (user.getUserId() == authorizationPo.getUserId() && user.getPermission() == authorizationPo.getPermission())) {
			return user;
		}
		return null;
	}

	@Override
	public List<AuthorizationBo> selectPageSome(AuthorizationQuery authorizationQuery) {
		return Arrays.asList(admin, user);
	}

	@Override
	public List<AuthorizationBo> selectAllSome(AuthorizationQuery authorizationQuery) {
		return Arrays.asList(admin, user);
	}

	@Override
	public int selectCount(AuthorizationQuery authorizationQuery) {
		return 2;
	}

	@Override
	public List<AuthorizationBo> selectAll() {
		return Arrays.asList(admin, user);
	}

	public AuthorizationBo getAdmin() {
		return admin;
	}

	public AuthorizationBo getUser() {
		return user;
	}
}
