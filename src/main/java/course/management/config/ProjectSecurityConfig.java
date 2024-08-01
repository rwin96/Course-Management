package course.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain mainConfig(HttpSecurity http) throws Exception {
        http.csrf(hcc -> hcc.disable());
        http.cors(hcoc -> hcoc.disable());
        http.authorizeHttpRequests((req) -> req

                // Course APIs
                .antMatchers(HttpMethod.GET, "/api/course/**").hasAnyRole("ADMIN", "HEAD", "TEACHER")
                .antMatchers(HttpMethod.POST, "/api/course/**").hasAnyRole("ADMIN", "HEAD")
                .antMatchers(HttpMethod.PUT, "/api/course/**").hasAnyRole("ADMIN", "HEAD")
                .antMatchers(HttpMethod.DELETE, "/api/course/**").hasAnyRole("ADMIN", "HEAD")

                // Department APIs
                .antMatchers(HttpMethod.GET, "/api/department").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/department/{id}").hasAnyRole("ADMIN", "HEAD")
                .antMatchers(HttpMethod.GET, "/api/department/{id}/avg").hasAnyRole("ADMIN", "HEAD")
                .antMatchers(HttpMethod.POST, "/api/department/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/department/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/department/**").hasRole("ADMIN")

                // Provided Course APIs
                .antMatchers(HttpMethod.GET, "/api/provided-course/{id}/avg").hasAnyRole("ADMIN", "HEAD", "TEACHER")
                .antMatchers(HttpMethod.GET, "/api/provided-course/**").hasAnyRole("ADMIN", "HEAD", "TEACHER", "STUDENT")
                .antMatchers(HttpMethod.POST, "/api/provided-course/**").hasAnyRole("ADMIN", "HEAD", "TEACHER")
                .antMatchers(HttpMethod.PUT, "/api/provided-course/**").hasAnyRole("ADMIN", "HEAD", "TEACHER")
                .antMatchers(HttpMethod.DELETE, "/api/provided-course/**").hasAnyRole("ADMIN", "HEAD", "TEACHER")

                // Selected Course APIs
                .antMatchers(HttpMethod.GET, "/api/selected-course/**").hasAnyRole("ADMIN", "HEAD", "TEACHER", "STUDENT")
                .antMatchers(HttpMethod.POST, "/api/selected-course/**").hasAnyRole("ADMIN", "HEAD", "STUDENT")
                .antMatchers(HttpMethod.PUT, "/api/selected-course/**").hasAnyRole("ADMIN", "HEAD", "TEACHER")
                .antMatchers(HttpMethod.DELETE, "/api/selected-course/**").hasAnyRole("ADMIN", "HEAD", "STUDENT")

                // Student APIs
                .antMatchers(HttpMethod.GET, "/api/student").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/student/{id}").hasAnyRole("ADMIN", "HEAD", "STUDENT")
                .antMatchers(HttpMethod.GET, "/api/student/{id}/avg").hasAnyRole("ADMIN", "HEAD", "STUDENT")
                .antMatchers(HttpMethod.PUT, "/api/student/{id}").hasAnyRole("ADMIN", "HEAD", "STUDENT")
                .antMatchers(HttpMethod.POST, "/api/student/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/student/**").hasRole("ADMIN")

                // Teacher APIs
                .antMatchers(HttpMethod.GET, "/api/teacher").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/teacher/{id}").hasAnyRole("ADMIN", "HEAD", "TEACHER")
                .antMatchers(HttpMethod.PUT, "/api/teacher/{id}").hasAnyRole("ADMIN", "HEAD", "TEACHER")
                .antMatchers(HttpMethod.POST, "/api/teacher/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/teacher/**").hasRole("ADMIN")

                .anyRequest().authenticated());

        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
