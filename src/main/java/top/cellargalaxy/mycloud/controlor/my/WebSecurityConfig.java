package top.cellargalaxy.mycloud.controlor.my;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author cellargalaxy
 * @time 2018/7/27
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder
				//设置UserDetailsService
				.userDetailsService(userDetailsService)
				//使用BCrypt进行密码的hash
				.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				// 由于使用的是JWT，我们这里不需要csrf
				.csrf().disable()

				// 基于token，所以不需要session
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

				.authorizeRequests()

				// 允许对于网站静态资源的无授权访问
				.antMatchers(
						HttpMethod.GET,
						"/",
						"/*.html",
						"/favicon.ico",
						"/**/*.html",
						"/**/*.css",
						"/**/*.js"
				).permitAll()

				// 对于获取token的rest api要允许匿名访问
				.antMatchers(/*HttpMethod.POST, */"/login").permitAll()

				// 除上面外的所有请求全部需要鉴权认证
				.anyRequest().authenticated()

				.and()
				//登录
				.addFilterBefore(new LoginFilter("/login",authenticationManager()), UsernamePasswordAuthenticationFilter.class)
				// 添加一个过滤器验证其他请求的Token是否合法
				.addFilterBefore(new CheckFilter(), UsernamePasswordAuthenticationFilter.class);

		// 禁用缓存
		httpSecurity.headers().cacheControl();
	}
}
