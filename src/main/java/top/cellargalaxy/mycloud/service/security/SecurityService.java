package top.cellargalaxy.mycloud.service.security;

import top.cellargalaxy.mycloud.model.security.SecurityUser;

/**
 * @author cellargalaxy
 * @time 2018/7/31
 */
public interface SecurityService {
	String USER_KEY = "user";

	/**
	 * 验证账号密码是否正确
	 *
	 * @param username
	 * @param password
	 * @return 账号密码正确就返回SecurityUser对象，否则返回null
	 */
	SecurityUser checkSecurityUser(String username, String password);

	/**
	 * 查询账号
	 *
	 * @param username
	 * @return 有则返之，无则返null
	 */
	SecurityUser getSecurityUser(String username);

	/**
	 * 制作该账号的token
	 *
	 * @param username 账号
	 * @return 账号的token
	 */
	String createToken(String username);

	/**
	 * 制作该账号的token
	 *
	 * @param securityUser 账号
	 * @return 账号的token
	 */
	String createToken(SecurityUser securityUser);

	/**
	 * 检查token是否合法
	 *
	 * @param token
	 * @return 合法则返回该token的SecurityUser对象
	 */
	SecurityUser checkToken(String token);
}
