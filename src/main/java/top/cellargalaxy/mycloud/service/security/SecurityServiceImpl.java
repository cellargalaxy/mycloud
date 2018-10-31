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
import top.cellargalaxy.mycloud.model.bo.UserBo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.security.SecurityUser;
import top.cellargalaxy.mycloud.model.vo.UserVo;
import top.cellargalaxy.mycloud.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author cellargalaxy
 * @time 2018/7/31
 */
@Service
public class SecurityServiceImpl implements SecurityService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	public static final int EXPIRATION_TIME = 1000 * 60 * 60 * 6;
	public static final String USER_ID_KEY = "userId";
	public static final String CREATE_TIME_KEY = "createTime";
	public static final String UPDATE_TIME_KEY = "updateTime";
	public static final String PERMISSIONS_KEY = "permissions";
	@Autowired
	private UserService userService;

	private final String secret;

	@Autowired
	public SecurityServiceImpl(MycloudConfiguration mycloudConfiguration) {
		secret = mycloudConfiguration.getSecret();
	}

	@Override
	public SecurityUser checkSecurityUser(String username, String password) {
		logger.debug("checkSecurityUser: {}", username);
		SecurityUser securityUser = getSecurityUser(username);
		if (securityUser != null && securityUser.getPassword().equals(password)) {
			return securityUser;
		} else {
			return null;
		}
	}

	@Override
	public SecurityUser getSecurityUser(String username) {
		logger.debug("getSecurityUser: {}", username);
		UserPo userPo = new UserPo();
		userPo.setUsername(username);
		UserVo userVo = userService.getUserVo(userPo);
		if (userVo != null) {
			SecurityUserImpl securityUser = new SecurityUserImpl();

			UserBo userBo = userVo.getUser();
			securityUser.setUserId(userBo.getUserId());
			securityUser.setUsername(userBo.getUsername());
			securityUser.setPassword(userBo.getPassword());
			securityUser.setCreateTime(userBo.getCreateTime());
			securityUser.setUpdateTime(userBo.getUpdateTime());

			List<AuthorizationBo> authorizationBos = userVo.getAuthorizations();
			authorizationBos.stream().forEach(authorizationBo -> securityUser.getPermissions().add(authorizationBo.getPermission().toString()));

			return securityUser;
		}
		return null;
	}

	@Override
	public String createToken(String username) {
		logger.debug("createToken:{}", username);
		return createToken(getSecurityUser(username));
	}

	@Override
	public String createToken(SecurityUser securityUser) {
		logger.debug("createToken:{}", securityUser);
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
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
		return jwt;
	}

	@Override
	public SecurityUser checkToken(String token) {
		logger.debug("checkToken:{}", token);
		if (token == null) {
			return null;
		}
		try {
			Claims claims = Jwts.parser()
					.setSigningKey(secret)
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
//			e.printStackTrace();
			logger.info("checkToken:token解析失败:" + e);
			return null;
		}
	}

	public static final SecurityUserImpl getSecurityUser(HttpServletRequest request) {
		return (SecurityUserImpl) request.getAttribute(USER_KEY);
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
			return super.getPassword();
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
