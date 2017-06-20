package io.arukas.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by Administrator on 20/06/2017.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 配置Spring Security的Filter链
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    /**
     * 设置http验证规则, 如何通过拦截器保护请求
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                // 对请求认证
                .authorizeRequests()
                // 所有的 /webjars 请求都放行(静态资源)
                .antMatchers("/webjars/**").permitAll()
//                .antMatchers(HttpMethod.POST, "/account/login").permitAll()
                .antMatchers("/account/login").permitAll()
                // 权限检测
                .antMatchers("/api/hello").hasAuthority("AUTH_WRITE")
                // 角色检测
                .antMatchers("/api/message").hasRole("ADMIN")
                // 所有的请求需要身份认证
                .anyRequest().authenticated();
    }

    /**
     * 配置user-detail服务
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password("admin").roles("ADMIN");
//        super.configure(auth);
    }


}
