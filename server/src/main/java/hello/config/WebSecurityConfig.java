package hello.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
					.anyRequest().authenticated()
                .and()
					.formLogin()
					.loginPage("/login")
					.permitAll()
                .and()
					.logout()
					.permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .usersByUsernameQuery("select name, password from users.user where name=?");
    }

    @Configuration
    @Component
    public class DataSourceBean {

        @ConfigurationProperties(prefix = "spring.datasource")
        @Bean
        @Primary
        public DataSource getDataSource() {
            return DataSourceBuilder
                    .create()
                    .url("jdbc:mysql://localhost:3306/users"+"?serverTimezone=UTC")
                    .username("root")
                    .password("1004")
                    .driverClassName("org.mysql.Driver")
                    .build();
        }
    }
}