package top.cellargalaxy.mycloud.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.model.bo.AuthorizationBo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.UserQuery;
import top.cellargalaxy.mycloud.model.security.SecurityUser;
import top.cellargalaxy.mycloud.model.vo.UserAuthorizationVo;
import top.cellargalaxy.mycloud.service.UserService;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author cellargalaxy
 * @time 2018/7/31
 */
@Service
public class SecurityServiceImpl implements SecurityService {
	private Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);
	public static final int EXPIRATION_TIME = 1000 * 60 * 60 * 6;
	public static final String USER_ID_KEY = "userId";
	public static final String CREATE_TIME_KEY = "createTime";
	public static final String UPDATE_TIME_KEY = "updateTime";
	public static final String PERMISSIONS_KEY = "permissions";
	@Autowired
	private MycloudConfiguration mycloudConfiguration;
	@Autowired
	private UserService userService;

	@Override
	public SecurityUser checkSecurityUser(String username, String password) {
		logger.info("checkSecurityUser:{}", username);
		SecurityUser securityUser = getSecurityUser(username);
		if (securityUser != null && securityUser.getPassword().equals(password)) {
			return securityUser;
		} else {
			return null;
		}
	}

	@Override
	public SecurityUser getSecurityUser(String username) {
		logger.info("getSecurityUser:{}", username);
		UserAuthorizationVo userAuthorizationVo = userService.getUserAuthorization(new UserQuery() {{
			setUsername(username);
		}});
		if (userAuthorizationVo != null) {
			return new SecurityUserImpl() {{
				setUserId(userAuthorizationVo.getUser().getUserId());
				setUsername(userAuthorizationVo.getUser().getUsername());
				setUserPassword(userAuthorizationVo.getUser().getUserPassword());
				setCreateTime(userAuthorizationVo.getUser().getCreateTime());
				setUpdateTime(userAuthorizationVo.getUser().getUpdateTime());
				for (AuthorizationBo authorization : userAuthorizationVo.getAuthorizations()) {
					getPermissions().add(authorization.getPermissionMark());
				}
			}};
		}
		return null;
	}

	@Override
	public String createToken(String username) {
		logger.info("createToken:{}", username);
		return createToken(getSecurityUser(username));
	}

	@Override
	public String createToken(SecurityUser securityUser) {
		logger.info("createToken:{}", securityUser);
		if (securityUser == null) {
			return null;
		}
		SecurityUserImpl securityUserImpl = (SecurityUserImpl) securityUser;
		//获取账号的权限，然后变成用逗号相间隔的字符串
		StringBuilder stringBuilder = new StringBuilder();
		Iterator<String> iterator = securityUserImpl.getPermissions().iterator();
		if (iterator.hasNext()) {
			stringBuilder.append(iterator.next());
		}
		while (iterator.hasNext()) {
			stringBuilder.append("," + iterator.next());
		}

		String jwt = Jwts.builder()
				.claim(USER_ID_KEY, securityUserImpl.getUserId())
				.claim(CREATE_TIME_KEY, securityUserImpl.getCreateTime().getTime())
				.claim(UPDATE_TIME_KEY, securityUserImpl.getUpdateTime().getTime())
				//保存权限/角色
				.claim(PERMISSIONS_KEY, stringBuilder.toString())
				//用户名写入标题
				.setSubject(securityUserImpl.getUsername())
				//有效期设置
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				//签名设置
				.signWith(SignatureAlgorithm.HS512, mycloudConfiguration.getSecret())
				.compact();
		return jwt;
	}

	@Override
	public SecurityUser checkToken(String token) {
		logger.info("checkToken:{}", token);
		if (token == null) {
			return null;
		}
		try {
			Claims claims = Jwts.parser()
					.setSigningKey(mycloudConfiguration.getSecret())
					.parseClaimsJws(token)
					.getBody();

			int userId = claims.get(USER_ID_KEY, Integer.class);
			String username = claims.getSubject();
			Date createTime = new Date(claims.get(CREATE_TIME_KEY, Long.class));
			Date updateTime = new Date(claims.get(UPDATE_TIME_KEY, Long.class));
			final String[] permissions = claims.get(PERMISSIONS_KEY, String.class).split(",");

			return new SecurityUserImpl() {{
				setUserId(userId);
				setUsername(username);
				setCreateTime(createTime);
				setUpdateTime(updateTime);
				for (String permission : permissions) {
					getPermissions().add(permission);
				}
			}};
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("checkToken:token解析失败");
			return null;
		}
	}

	class SecurityUserImpl extends UserPo implements SecurityUser {
		private final Set<String> permissions;

		public SecurityUserImpl() {
			permissions = new HashSet<>();
		}

		@Override
		public String getUsername() {
			return super.getUsername();
		}

		@Override
		public String getPassword() {
			return super.getUserPassword();
		}

		@Override
		public Set<String> getPermissions() {
			return permissions;
		}

		@Override
		public String toString() {
			return "SecurityUserImpl{" +
					"username=" + getUsername() +
					", permissions=" + permissions +
					'}';
		}
	}
}
