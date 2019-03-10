package com.example.mvcbackend.sys.config;

import com.example.mvcbackend.sys.jwt.JWTAuthenticationFilter;
import com.example.mvcbackend.sys.jwt.JWTLoginFilter;
import com.example.mvcbackend.sys.jwt.JwtUtils;
import com.example.mvcbackend.sys.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class AppConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    private Environment env;

    @Autowired
    private UserService userService;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedHeaders("token")
                .exposedHeaders("token")
                .allowCredentials(true).maxAge(3600);
    }



    /**
     * 读取配置文件入静态类
     * @return
     */
    @Bean
    @Lazy(false)
    public int readConf() {
        JwtUtils.setSECRECT(env.getProperty("JWT.secret"));
        JwtUtils.setEXPIRE(env.getProperty("JWT.expire",Long.class, 600000L));
        return 1;
    }

    /**
     * 配置加密算法
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().httpBasic().disable().csrf().disable().authorizeRequests()
                .antMatchers("/api/signUp").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTLoginFilter(authenticationManager()))
                .addFilter(new JWTAuthenticationFilter(authenticationManager()));
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }



}
