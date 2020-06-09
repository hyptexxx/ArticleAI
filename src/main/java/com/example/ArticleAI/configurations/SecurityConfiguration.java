package com.example.ArticleAI.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
//        http.addFilterBefore(filter, CsrfFilter.class);
//        http.authorizeRequests()
//                .antMatchers("/main-test").authenticated()
//                .antMatchers("/main-test/**").authenticated()
//                .antMatchers("/isUserExists").authenticated()
//                .antMatchers("/sendTestForm").authenticated();
//        http.exceptionHandling()
//                .accessDeniedPage("/");
//        http.formLogin()
//                // указываем страницу с формой логина
//                .loginPage("/")
//                // указываем action с формы логина
//                .loginProcessingUrl("/login")
//                // указываем URL при неудачном логине
//                .failureUrl("/login?error")
//                // Указываем параметры логина и пароля с формы логина
//                .usernameParameter("login")
//                .passwordParameter("password")
//                .defaultSuccessUrl("/")
//                // даем доступ к форме логина всем
//                .permitAll();
//        http.rememberMe()
//                .rememberMeParameter("remember-me")
//                .key("AppKey")
//                .rememberMeCookieName("javasampleapproach-remember-me")
//                .tokenValiditySeconds(24 * 60 * 60)
//                .alwaysRemember(true);
    }


}
