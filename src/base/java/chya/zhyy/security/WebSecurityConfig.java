package chya.zhyy.security;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	final static String[] authenticatedUrls = {"/main/**","/"};
	
	final static String[] permitAllUrl = { "/login**","/login/**","/common/**","/api/**"
			,"/getMenuTree","/toUpdatePwd","/updatePwd","/noAcess"
	};
	
	public final static String[] excludeUrl = {"/css/**", "/js/**", "/image/**","/img/**", "/font/", "/favicon.ico", 
			"**/favicon.ico", "/lib/**", "/static/**","/fonts/**" };
	
	
	@Autowired UserDetailsService userDetailsService;
	
	@Autowired
	PasswordEncoder userPasswordEncoder;
	
	@Autowired(required = false) 
	RoleResourceVoter roleReVoter;
	
	
	/**
	 * 
	 */
	public WebSecurityConfig() {
		super();
	}

	/**
	 * @param disableDefaults
	 */
	public WebSecurityConfig(boolean disableDefaults) {
		super(disableDefaults);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
//		List<String> ignored = SpringBootWebSecurityConfiguration.getIgnored(this.props);
		String[] paths = excludeUrl;
		if (!ObjectUtils.isEmpty(paths)) {
			web.ignoring().antMatchers(paths);
		}
	}
	
	public AccessDecisionManager accessDecisionManager() {
		List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<AccessDecisionVoter<? extends Object>>();
		ExpressionVoter webExpressionVoter = new ExpressionVoter();
		decisionVoters.add(webExpressionVoter);
		decisionVoters.add(roleReVoter);
		AffirmativeBased accessDecisionManager = new AffirmativeBased(decisionVoters);
		accessDecisionManager.setAllowIfAllAbstainDecisions(false);
		return accessDecisionManager;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.addFilterBefore(new CaptchaValidateFilter(imageCaptchaService), UsernamePasswordAuthenticationFilter.class)
//		.addFilterBefore(new BusiDateValidateFilter(), CaptchaValidateFilter.class)
		http.authorizeRequests()
		.accessDecisionManager(accessDecisionManager())
		.antMatchers(permitAllUrl).permitAll()
			.antMatchers(authenticatedUrls).authenticated()
			.anyRequest().denyAll()
			.and().formLogin().permitAll()
			.successHandler(new LoginSuccessHandler())
			.loginPage("/login").failureUrl("/login?failure").permitAll()
			.and().logout().logoutUrl("/logout").logoutSuccessUrl("/login").permitAll()
			.and().exceptionHandling().accessDeniedPage("/noAcess")
			.and().anonymous().principal("anonymousUser")
			.and().csrf().disable().headers().frameOptions().disable();
	}
	
	@Bean
	PasswordEncoder userPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		    provider.setHideUserNotFoundExceptions(false);
		    provider.setUserDetailsService(userDetailsService);
		    provider.setPasswordEncoder(userPasswordEncoder());
		auth.authenticationProvider(provider);
	}
}
