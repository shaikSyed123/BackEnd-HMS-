package com.tsarit.form_1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tsarit.form_1.service.LoginSuccessListener;

@Configuration
@EnableWebSecurity
public class MyConfigur {
	@Autowired
    private LoginSuccessListener  loginSuccessListener;
	   
	 @SuppressWarnings({ "removal" })
	@Bean
	   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////		   http.csrf().disable()
////		   .authorizeHttpRequests()
////		   .requestMatchers("/user/**")
////		   .hasRole("USER")
////		   .requestMatchers("/**")
////		   .permitAll()
////		   .anyRequest().authenticated()
////		   .and().formLogin()
////		   .defaultSuccessUrl("/dashboard")
//////		   .defaultSuccessUrl("/home")
////		   .loginProcessingUrl("/dologin").loginPage("/login");
	  
////		  http.csrf().disable()
////          .authorizeHttpRequests(authorizeRequests ->
////              authorizeRequests
////                  .requestMatchers("/user/**").hasRole("USER")
////                  .requestMatchers("/**").permitAll()
////                  .anyRequest().authenticated()
////          )
////          .formLogin(form ->
////              form
////                  .loginPage("/login")
////                  .loginProcessingUrl("/dologin")
//////                  .defaultSuccessUrl("/dashboard")
////                  .defaultSuccessUrl("/count")
////          );
////		   return http.build();
//		 
		 
		   http.csrf().disable()
           .authorizeHttpRequests(authorizeRequests ->
               authorizeRequests
                   .requestMatchers("/user/**").hasRole("USER")
                   .requestMatchers("/**").permitAll()
                   .requestMatchers("/auth/login", "/dashboard","/Dashboard").authenticated()
                   .anyRequest().permitAll()
           );
//           .formLogin(form ->
//               form
//                   .loginPage("/login")  // The login page to redirect to
//                   .loginProcessingUrl("/dologin")  // The endpoint that processes the login
//                   .defaultSuccessUrl("/dashboard")  // Redirect to dashboard after successful login
//                   .successHandler(loginSuccessListener)
//           )
//           .logout(logout -> 
//               logout
//                   .logoutUrl("/logout")
//                   .logoutSuccessUrl("/login?logout")
//           );
       return http.build();
	   }
//	    @Autowired
//	    private  JwtRequestFilter jwtRequestFilter;
//
//	    private coustomUserDetailService  coustomUserDetailService;
//	    
////	    @Autowired
////		private AuthenticationEntryPoint jwtAuthenticationEntryPoint;
//	    public MyConfigur(JwtRequestFilter jwtRequestFilter, coustomUserDetailService coustomUserDetailService) {
//	        this.jwtRequestFilter = jwtRequestFilter;
//	        this.coustomUserDetailService = coustomUserDetailService;
//	    }
	  
//	  @Bean
//	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//	        http.csrf().disable()
//	            .authorizeHttpRequests(authorizeRequests ->
//	                authorizeRequests
//	                    .requestMatchers("/authenticate", "/register").permitAll()  // Allow unauthenticated access to /authenticate and /register
//	                    .requestMatchers("/user/**").hasRole("USER")  // Protect /user/** endpoints, requiring the USER role
//	                    .requestMatchers("/**").permitAll()  // Allow access to all other endpoints
//	                    .anyRequest().authenticated()  // All other requests need to be authenticated
//	            )
//	            .formLogin(form -> 
//	                form
//	                    .loginPage("/login")  // Custom login page
//	                    .loginProcessingUrl("/dologin")  // Login form submit URL
//	                    .defaultSuccessUrl("/dashboard")  // Redirect after successful login
//	            )
////	            .exceptionHandling(exception -> 
//////	                exception.authenticationEntryPoint(new JwtAuthenticationEntryPoint())  // Handle authentication errors
////	            )
//	            .sessionManagement(session -> 
//	                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Stateless session management for JWT
//	            );
//
////	        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);  // Add JWT filter
//
//	        return http.build();
//	    }
//	   
	   @Bean
      public UserDetailsService getUserDetailsService() {
		   return new coustomUserDetailService();
      }
	   
	   @Bean
	   public BCryptPasswordEncoder passwordEncoder() {
		   return new BCryptPasswordEncoder();
	   }
	   
	   @Bean
	   public DaoAuthenticationProvider daoProvider() {
		   DaoAuthenticationProvider dao=new DaoAuthenticationProvider();
		   dao.setUserDetailsService(getUserDetailsService());
		   dao.setPasswordEncoder(passwordEncoder());
		   return dao;
	   }
	
//	   @Bean
//	    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//	        return http.getSharedObject(AuthenticationManagerBuilder.class)
//	                   .userDetailsService(coustomUserDetailService)
//	                   .passwordEncoder(new BCryptPasswordEncoder())
//	                   .and()
//	                   .build();
//	    }
	  
//	   protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//		   auth.authenticationProvider(daoProvider());
//	   }
//	   
	   
	   @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	        return authenticationConfiguration.getAuthenticationManager();
//	        .userDetailsService(userDetailsService)
//            .passwordEncoder(new BCryptPasswordEncoder())
//            .and()
//            .build();
	    }
	  
}
