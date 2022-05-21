package board.myboard.global.config;

import board.myboard.global.login.filter.JsonUsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ObjectMapper objectMapper;

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
    public PasswordEncoder passwordEncoder(){ // 1 - PasswordEncoder 등록
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(){ // 2 - AuthenticationManager 등록
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); // DaoAuthenticationProvider 사용
        provider.setPasswordEncoder(passwordEncoder()); // PasswordEncoder로는 PasswordEncoderFactories.createDelegationPasswordEncoder() 사용
        return new ProviderManager(provider);
    }

    @Bean
    public JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter(){
        JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordLoginFilter = new JsonUsernamePasswordAuthenticationFilter(objectMapper);
        return jsonUsernamePasswordLoginFilter;
    }

}
