package cn.studycarbon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)  // 处理@PreAuthorize 不起作用 参考：https://blog.csdn.net/weixin_41195786/article/details/84439384
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String KEY = "studycarbon.cn";

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public SecurityConfig() {
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
//        PasswordEncoder passwordEncoder = getApplicationContext().getBean("PasswordEncoder", PasswordEncoder.class);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/css/**","/js/**/","/fonts/**","/index").permitAll()
                .antMatchers("/admins/**").hasRole("ADMIN") // admins需要登录
                .and()
                .formLogin()
                .loginPage("/login").failureUrl("/login-error")// 自定义登录页面
                .and().exceptionHandling().accessDeniedPage("/403"); // 处理异常，拒绝范文就重定向到 403 页面
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // https://zhuanlan.zhihu.com/p/110599306
        // auth.inMemoryAuthentication().withUser("mowangshuying").password("123456").roles("ADMIN");
        // 当使用password标识标签时,使用上述创建mowangshuying时，出现There is no PasswordEncoder mapped for id “null”，参考上述文章内容
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("mowangshuying").password(new BCryptPasswordEncoder().encode("123456")).roles("ADMIN");
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());
    }
}
