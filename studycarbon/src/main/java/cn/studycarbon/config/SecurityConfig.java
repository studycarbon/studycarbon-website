package cn.studycarbon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/css/**","/js/**/","/fonts/**","/index").permitAll()
                .antMatchers("/thymeleafUsers/**").hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login").failureUrl("/login-error");// 自定义登录页面
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // https://zhuanlan.zhihu.com/p/110599306
        // auth.inMemoryAuthentication().withUser("mowangshuying").password("123456").roles("ADMIN");

        // 当使用password标识标签时,使用上述创建mowangshuying时，出现There is no PasswordEncoder mapped for id “null”，参考上述文章内容
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("mowangshuying").password(new BCryptPasswordEncoder().encode("123456")).roles("ADMIN");
    }
}
