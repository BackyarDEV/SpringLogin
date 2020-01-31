package com.backyardev.springlogin;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.filter.CompositeFilter;

@SpringBootApplication
@Controller
@EnableOAuth2Client
@EnableAuthorizationServer
@EnableJpaAuditing
@Order(6)
public class SpringLoginApplication extends WebSecurityConfigurerAdapter {

	@Autowired
	OAuth2ClientContext oauth2ClientContext;

	@SuppressWarnings("unchecked")
	@RequestMapping({ "/user", "/me" })
	@ResponseBody
	public Map<String, Object> user(Principal principal) {
		Map<String, Object> details = new LinkedHashMap<>();
		Map<String, Object> map = new LinkedHashMap<>();
		if(principal != null) {
			OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
	        Authentication authentication = oAuth2Authentication.getUserAuthentication();
	        details = (Map<String,Object>) authentication.getDetails();
        	map.put("name", details.get("name"));
        	map.put("email", details.get("email"));
	        if(details.get("avatar_url") != null) {
	        	map.put("propic", details.get("avatar_url"));
	        } else {
	        	map.put("propic", details.get("picture"));
	        }
	        
	        System.out.println(details);
		}
		return map;
	}
	
	@RequestMapping("/")
	public String home() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();  
		if (auth.getName().equals("anonymousUser")){
			return "redirect:/login";  
		} 
		return "home.jsp";
	}

	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();  
		if (!auth.getName().equals("anonymousUser")){
			return "redirect:/";  
		} 
		return "login.jsp";
	}
	
	@RequestMapping("/register")
	public String register() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();  
		if (!auth.getName().equals("anonymousUser")){
			return "redirect:/";  
		} 
		return "register.jsp";
	}
	
    @RequestMapping(value="/logout", method=RequestMethod.GET)  
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {  
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();  
        if (auth != null){      
           new SecurityContextLogoutHandler().logout(request, response, auth);  
        }  
         return "redirect:/login";  
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.authorizeRequests()
		.antMatchers("/register**", "/login**", "/webjars/**", "/error**").permitAll()
			.and().authorizeRequests().antMatchers(HttpMethod.POST, "/api/useraccounts**").permitAll()
			.and().authorizeRequests().antMatchers(HttpMethod.PUT, "/api/useraccounts/**").permitAll()
		.anyRequest().authenticated().and().exceptionHandling()
			.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
			.and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
		// @formatter:on
	}

	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
		@Override
		public void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http.antMatcher("/me").authorizeRequests().anyRequest().authenticated();
			// @formatter:on
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringLoginApplication.class, args);
	}

	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
	    return new CustomLogoutSuccessHandler();
	}
	
	@Bean
	public FilterRegistrationBean<OAuth2ClientContextFilter> oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
		FilterRegistrationBean<OAuth2ClientContextFilter> registration = new FilterRegistrationBean<OAuth2ClientContextFilter>();
		registration.setFilter(filter);
		registration.setOrder(-100);
		return registration;
	}

	@Bean
	@ConfigurationProperties("github")
	public ClientResources github() {
		return new ClientResources();
	}

	@Bean
	@ConfigurationProperties("google")
	public ClientResources google() {
		return new ClientResources();
	}

	private Filter ssoFilter() {
		CompositeFilter filter = new CompositeFilter();
		List<Filter> filters = new ArrayList<>();
		filters.add(ssoFilter(google(), "/login/google"));
		filters.add(ssoFilter(github(), "/login/github"));
		filter.setFilters(filters);
		return filter;
	}

	private Filter ssoFilter(ClientResources client, String path) {
		OAuth2ClientAuthenticationProcessingFilter oAuth2ClientAuthenticationFilter = new OAuth2ClientAuthenticationProcessingFilter(path);
		OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
		oAuth2ClientAuthenticationFilter.setRestTemplate(oAuth2RestTemplate);
		UserInfoTokenServices tokenServices = new UserInfoTokenServices(client.getResource().getUserInfoUri(),
				client.getClient().getClientId());
		tokenServices.setRestTemplate(oAuth2RestTemplate);
		oAuth2ClientAuthenticationFilter.setTokenServices(tokenServices);
		return oAuth2ClientAuthenticationFilter;
	}

}

class ClientResources {

	@NestedConfigurationProperty
	private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

	@NestedConfigurationProperty
	private ResourceServerProperties resource = new ResourceServerProperties();

	public AuthorizationCodeResourceDetails getClient() {
		return client;
	}

	public ResourceServerProperties getResource() {
		return resource;
	}
}
