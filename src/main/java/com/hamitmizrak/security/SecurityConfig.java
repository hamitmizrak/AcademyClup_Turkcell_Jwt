package com.hamitmizrak.security;

import com.hamitmizrak.jwt._7_JwtUtils;
import com.hamitmizrak.jwt._9_JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

// LOMBOK
@RequiredArgsConstructor


// SECURITY
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    // Injection
    private final _7_JwtUtils jwtUtils;

    /// ////////////////////////////////////////////////////////////////////
    // for JWT
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Static Web Dosyalarımız
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() throws Exception {
        return (web) -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/favicon.ico"))
                .requestMatchers(new AntPathRequestMatcher("/html/**"))
                .requestMatchers(new AntPathRequestMatcher("/css/**"))
                .requestMatchers(new AntPathRequestMatcher("/js/**"))
                .requestMatchers(new AntPathRequestMatcher("/img/**"))
                .requestMatchers(new AntPathRequestMatcher("/lib/**"));
    } // end Web


    // Filter: SecurityFilterChain
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector, _9_JwtFilter customizeMyJwtFilter) throws Exception {

        // CSRF(Cross-Site Request Forgery) korumasını devre dışı bıraktık,
        // Kötü niyetli kişiler tarafından kullanıcı bilgileri çalarak sistemeye girmesi
        // Bu genellikle API'larda kullanmaktayız
        httpSecurity.csrf(AbstractHttpConfigurer::disable); // Web sayfalarında Yetkilendirme ayarlarını yapıyoruz.
        httpSecurity.authorizeHttpRequests(
                        authorize -> authorize
                                // Anasayfaya izin ver
                                .requestMatchers(new MvcRequestMatcher(introspector, "/")).permitAll()
                                .requestMatchers(new MvcRequestMatcher(introspector, "/index")).permitAll()
                                .requestMatchers(new MvcRequestMatcher(introspector, "/index.html")).permitAll()
                                .requestMatchers(new MvcRequestMatcher(introspector, "/index.htm")).permitAll()

                                // Genel public'lere izin ver
                                //.requestMatchers(new MvcRequestMatcher(introspector, "/public/**")).permitAll()
                                // Login, Register
                                .requestMatchers(new MvcRequestMatcher(introspector, "/login")).permitAll()
                                .requestMatchers(new MvcRequestMatcher(introspector, "/register")).permitAll()
                                .requestMatchers(new MvcRequestMatcher(introspector, "/logout")).permitAll()
                                .requestMatchers(new MvcRequestMatcher(introspector, "/admin")).permitAll()

                                // Third part Libraries
                                .requestMatchers(new MvcRequestMatcher(introspector, "/swagger-ui/**")).permitAll()
                                //.requestMatchers(new AntPathRequestMatcher("/h2-console/**", "GET"))
                                .requestMatchers(new MvcRequestMatcher(introspector, "/h2-console/**")).permitAll()

                                // Jwt Url yani login, register izin vermek
                                .requestMatchers(new MvcRequestMatcher(introspector, "/api/auth/**")).permitAll()

                                // Yukarıki url'lerin hariçinde bütün url izin verilmedikçe(Authorization) erişilmesin
                                // 401: Yerkisiz giriş
                                .anyRequest().authenticated()) // end httpSecurity.authorizeHttpRequests

                // For Jwt
                .addFilterBefore(customizeMyJwtFilter, UsernamePasswordAuthenticationFilter.class)

                // POSTMAN AUTHENTICATION
                // Http Temel Kimlik doğrulaması (Basic Auth) kullanılır.
                // Bu yapı Postman gibi client uygulamalarda mutlaka açmalıyız.
                .httpBasic(Customizer.withDefaults());

                // WEB SAYFA AUTHENTICATION
                // Form tabanlı girişler içindir(Spring MVC)
                // .formLogin(Customizer.withDefaults())

                // Logout işlemler için bu ayarları kullanıyoruz.
                //Ancak Çıkış yaptığımızda oturum(session) geçersiz olur
                //.logout().logoutUrl("/logout").invalidateHttpSession(true);

        return httpSecurity.build();
    }  // end SecurityFilterChain

}  // end SecurityConfig
