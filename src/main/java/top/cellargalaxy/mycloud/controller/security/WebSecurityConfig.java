package top.cellargalaxy.mycloud.controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.cellargalaxy.mycloud.service.security.SecurityService;

/**
 * @author cellargalaxy
 * @time 2018/7/30
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SecurityService securityService;

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(new UserDetailsServiceImpl(securityService));
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                //由于使用的是JWT，这里不需要csrf
                .csrf().disable()

                //跨域
                .cors().and()

                //基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests()

                //允许对于网站静态资源的无授权访问
                .antMatchers(
                        "/",
                        "/*",
                        "/guest/**",
                        "/favicon.ico"
                ).permitAll()

                //除上面外的，所有请求全部需要登录与鉴权
                .anyRequest().authenticated()

                .and()

                //登录
                .addFilterBefore(
                        new LoginFilter("/login", authenticationManager(), securityService),
                        UsernamePasswordAuthenticationFilter.class)

                //检验token
                .addFilterBefore(new AuthenticationFilter(securityService),
                        UsernamePasswordAuthenticationFilter.class);

        //禁用缓存
        httpSecurity.headers().cacheControl();
    }
}
