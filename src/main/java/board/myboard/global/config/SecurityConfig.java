package board.myboard.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http
                // 로그인 이후 refreshToken이 만료되기 전까지는 토큰을 통해 인증을 진행할 것이다.
                .formLogin().disable()//1 - formLogin 인증방법 비활성화
                .httpBasic().disable()//2 - httpBasic 인증방법 비활성화(특정 리소스에 접근할 때 username과 password 물어봄)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션은 Stateless(상태를 유지하지 않음)

                .and()
                .authorizeRequests()
                .antMatchers("/login", "/signUp","/").permitAll() // 로그인(/login), 회원가입(/signup), 메인페이지(/)에 대해서는 인증 없이도 접근 가능
                .anyRequest().authenticated();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
