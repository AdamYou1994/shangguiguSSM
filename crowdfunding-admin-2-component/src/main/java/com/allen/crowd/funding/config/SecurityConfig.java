package com.allen.crowd.funding.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.allen.crowd.funding.exception.CrowdAccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private UserDetailsService userDetailsService;
	

	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		/*builder.inMemoryAuthentication()
			.passwordEncoder(new BCryptPasswordEncoder())
			.withUser("xulujiang")
			.password(new BCryptPasswordEncoder().encode("123456"))
			.roles("king");
		System.out.println(new BCryptPasswordEncoder().encode("11111"));*/
		builder.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
		
	}

	@Override
	protected void configure(HttpSecurity security) throws Exception {
		security.authorizeRequests()
			.antMatchers("/index.html","/bootstrap/**","/css/**","/fonts/**","/img/**","jquery/**","/layer/**","/script/**","/ztree/**")
			.permitAll()
			.antMatchers("/admin/query/for/search.html")
			.hasRole("董事长")
			.anyRequest()
			.authenticated()
			.and()
			.exceptionHandling()
			.accessDeniedHandler(new CrowdAccessDeniedHandler())
			.and()
			.formLogin()
			.loginPage("/admin/to/login/page.html")
			.permitAll()
			.loginProcessingUrl("/admin/security/do/login.html")
			.permitAll()
			.usernameParameter("loginAcct")
			.passwordParameter("userPswd")
			.defaultSuccessUrl("/admin/to/main/page.html")
			.and()
			.logout()
			.logoutUrl("/admin/security/do/logout.html")
			.logoutSuccessUrl("/index.html")
			.and()
			.csrf().disable()
			;
	}
}
