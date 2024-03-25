
package com.jbk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.jbk.security.CustomUserDetailService;



@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public CustomUserDetailService customUserDetilService;

	@Autowired
	private JwtAuthenticationFilter jwtFilter;
	
	public static final String[] PUBLIC_URLS=
		{
				"/api/v1/auth/**",
				"/v3/api-docs",
				"/v2/api-docs",
				"/swagger-resources/**",
				"/swagger-ui/**",
				"/webjars/**"				
		};

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetilService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
		http
		.csrf().disable().authorizeRequests()
		.antMatchers("/auth/**").permitAll()
		.and()
		.authorizeRequests()
		.antMatchers(PUBLIC_URLS).permitAll()
		.antMatchers("/product/**").hasRole("ADMIN") // Product USER
		.antMatchers("/category/**").hasRole("USER") // USER
		.antMatchers("/supplier/**").hasRole("USER") // Supplier USER
        .anyRequest().authenticated().and().httpBasic();
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity web) throws Exception 
	{
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
				"/configuration/security", "/webjars/**");
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() 
	{
		return new BCryptPasswordEncoder(10);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManager() throws Exception 
	{
		return super.authenticationManager();
	}

}
