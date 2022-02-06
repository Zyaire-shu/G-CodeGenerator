package top.zyaire.webview.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Author ZyaireShu
 * @Date 2022/1/12 14:43
 * @Version 1.0
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    /**
     * SpringBoot 默认开启了CSRF来防止跨站脚本攻击，可以关闭
     * CSRF原理：用户应用网站，并通过了鉴权，本地Cookie缓存了鉴权信息，此时如果用户再访问不安全网站时，
     * 利用Cookie伪造了用户的请求，达到攻击应用网站的目的
     *
     * SpringBoot 如何防CSRF攻击？
     * 用户访问表单时，Spring Security会生成CSRF参数放入表单，并且该参数不会缓存到Cookie中，请求提交到
     * 服务器时，Spring Security会校验CSRF参数，达到防CSRF攻击目的
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/images/**").permitAll();
        http.csrf().disable();
    }
// ...
}
