package io.spring.api.security;

import static java.util.Arrays.asList;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * custom Security filter Security filter chain에는 9개가 내장돼있음.
 **/
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(asList("*"));
    configuration.setAllowedMethods(asList("HEAD",
        "GET", "POST", "PUT", "DELETE", "PATCH"));
    // setAllowCredentials(true) is important, otherwise:
    // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
    configuration.setAllowCredentials(false);
    // setAllowedHeaders is important! Without it, OPTIONS preflight request
    // will fail with 403 Invalid CORS request
    configuration.setAllowedHeaders(asList("Authorization", "Cache-Control", "Content-Type"));
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public JwtTokenFilter jwtTokenFilter() {
    return new JwtTokenFilter();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

//.httpbasic() //rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
    /**csrf**/
    http.csrf().disable()// rest api이므로 csrf 보안이 필요없으므로 disable처리.
        /**cors**/
        .cors()
        .and()
        /**Exception**/
        .exceptionHandling()
        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
        .and()
        /**Session**/
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()// jwt token으로 인증하므로 세션은 필요없으므로 생성안함.
        /**authorization**/
        .authorizeRequests()// ★다음 리퀘스트에 대한 사용권한 체크
        .antMatchers(HttpMethod.OPTIONS).permitAll()
//            .antMatchers("/**").permitAll()
        .antMatchers(HttpMethod.GET, "/articles/feed").authenticated()
        .antMatchers(HttpMethod.POST, "/users", "/users/login").permitAll()
        .antMatchers(HttpMethod.GET, "/articles/**", "/profiles/**", "/tags").permitAll()
        .antMatchers(HttpMethod.GET, "/**").permitAll()//지워도 됨.
        .antMatchers(HttpMethod.GET, "/*").permitAll()//지워도 됨.
//        .antMatchers(HttpMethod.POST, "/profiles/**").permitAll()//try
//        .antMatchers(HttpMethod.POST, "/profiles/**").permitAll()//try
//        .antMatchers(HttpMethod.POST, "customize").hasAuthority("SCOPE_read")//역할 시도해봐도 됨 .
        .anyRequest().authenticated();
    http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
  }
}
