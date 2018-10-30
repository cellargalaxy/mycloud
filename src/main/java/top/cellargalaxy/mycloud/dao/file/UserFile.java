package top.cellargalaxy.mycloud.dao.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Repository;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.dao.mapper.UserMapper;
import top.cellargalaxy.mycloud.model.bo.UserBo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.UserQuery;
import top.cellargalaxy.mycloud.util.StringUtil;

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
public class UserFile implements UserMapper {
	private final UserBo userBo;

	@Autowired
	public UserFile(MycloudConfiguration mycloudConfiguration) {
		String username = mycloudConfiguration.getMycloudUsername();
		String password = mycloudConfiguration.getMycloudPassword();
		userBo = new UserBo();
		userBo.setUserId(1);
		userBo.setUsername(username);
		userBo.setPassword(StringUtil.PASSWORD_HEAD + StringUtil.encoderPassword(password));
		Date date = new Date();
		userBo.setCreateTime(date);
		userBo.setUpdateTime(date);
	}

	@Override
	public int insert(UserPo userPo) {
		return 0;
	}

	@Override
	public int delete(UserPo userPo) {
		return 0;
	}

	@Override
	public int update(UserPo userPo) {
		return 0;
	}

	@Override
	public UserBo selectOne(UserPo userPo) {
		if (userPo == null) {
			return null;
		}
		if (userBo.getUserId() == userPo.getUserId() || userBo.getUsername().equals(userPo.getUsername())) {
			return userBo;
		}
		return null;
	}

	@Override
	public List<UserBo> selectPageSome(UserQuery userQuery) {
		return Arrays.asList(userBo);
	}

	@Override
	public List<UserBo> selectAllSome(UserQuery userQuery) {
		return Arrays.asList(userBo);
	}

	@Override
	public int selectCount(UserQuery userQuery) {
		return 1;
	}

	@Override
	public List<UserBo> selectAll() {
		return Arrays.asList(userBo);
	}

	public UserBo getUserBo() {
		return userBo;
	}
}
