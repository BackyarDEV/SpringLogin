package com.backyardev.springlogin.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.backyardev.springlogin.exception.ResourceNotFoundException;
import com.backyardev.springlogin.model.MyUserAccounts;
import com.backyardev.springlogin.model.MyUserDetails;
import com.backyardev.springlogin.repository.UserAccountsRepository;
import com.backyardev.springlogin.repository.UserRolesRepository;

@Controller
public class MyController implements ErrorController{

	@Autowired
	UserAccountsRepository userRepo;
	UserRolesRepository userRolesRepo;
	
	@RequestMapping({ "/user", "/me" })
	@ResponseBody
	public Map<String, Object> user(Principal principal) {
		Map<String, Object> details = new LinkedHashMap<>();
		Map<String, Object> map = new LinkedHashMap<>();
		try {
			if(principal != null) {
		        details = getOAuthMap(principal);
	        	map.put("name", details.get("name"));
	        	map.put("email", details.get("email"));
		        if(details.get("avatar_url") != null) {
		        	map.put("propic", details.get("avatar_url"));
		        } else {
		        	map.put("propic", details.get("picture"));
		        }
			}
		} catch(ClassCastException e) {
			String name;
			String email;
			Object princi = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (princi instanceof MyUserDetails) {
				name = ((MyUserDetails)princi).getFirst_name() + " " + ((MyUserDetails)princi).getLast_name();
			   	email = ((MyUserDetails)princi).getEmail();
			} else {
				name = princi.toString();
				email = "email@DBUser.com";
			}
			map.put("name", name);
        	map.put("email", email);
        	map.put("propic", "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQoR3lxH768V306NfcbE7EcULhfnc8i1wv8Rb9tr2xBdyxDivZN");
        	System.out.println(map);
		}
		return map;
	}
	
	@RequestMapping("/")
	public String home(Principal principal) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		if (auth.getName().equals("anonymousUser")){
			return "redirect:/login";  
		} else {
			try {
				if(principal != null) {
					map = getOAuthMap(principal);
					System.out.println(map);
					Optional<MyUserAccounts> emailOptional = userRepo.findByEmail((String)map.get("email"));
					if(emailOptional.isPresent()) {
						System.out.println(emailOptional.get().getPassword());
						if(emailOptional.get().getPassword() != null) {
							if(emailOptional.get().getOauth_link() == 0) {
								return "redirect:/linkaccount";
							}
						}
					} else {
						
						MyUserAccounts insert = registerOAuthUser(map);
						if(insert!=null) {
							return "redirect:/home";
						}
					}
				}
			} catch(ClassCastException e) {
				System.out.println(e.getLocalizedMessage());
			}
		}
		return "redirect:/home";
	}
	
	
	@RequestMapping("/home")
	public String homePage() {
		return "home.jsp";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("errorMsg", "Your username and password are invalid.");

        if (logout != null)
            model.addAttribute("msg", "You have been logged out successfully.");
        
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
	
	@RequestMapping(value = "/linkaccount", method=RequestMethod.GET)
	public String linkAccounts() {
		return "linkoauth.jsp";
	}
	
	@RequestMapping(value="/linkaccount", method=RequestMethod.POST)
	public void linkPost(HttpServletRequest req, HttpServletResponse res, Principal principal) throws IOException {
		
		String link = req.getParameter("link");
		String pass = req.getParameter("password");
		
		if(principal != null) {
			
			Map<String, Object> map = getOAuthMap(principal);
			String email = (String) map.get("email");
			Optional<MyUserAccounts> emailOptional = userRepo.findByEmail(email);
			if(link != null && link.equalsIgnoreCase("yes")) {
				if(pass.equals(emailOptional.get().getPassword())) {
					
					MyUserAccounts updateUser = updateAccountOAuthLink(email, emailOptional, 1);
					
					if(updateUser != null) {
						System.out.println("OAuthLink: " + updateUser.getOauth_link());
						res.getWriter().write("true");
					}
				} else res.getWriter().write("wrong pass");
			} else if(link != null && link.equalsIgnoreCase("no")) {
				
				MyUserAccounts updateUser = updateAccountOAuthLink(email, emailOptional, -1);
				
				if(updateUser != null) {
					System.out.println("OAuthLink: " + updateUser.getOauth_link());
					res.getWriter().write("true");
				}
			}
		}
	}
	
    @RequestMapping(value="/logout", method=RequestMethod.GET)  
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {  
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();  
        if (auth != null){      
           new SecurityContextLogoutHandler().logout(request, response, auth);  
        }  
         return "redirect:/login";  
    }
    
    @RequestMapping("/error")
    public String errorPage() {
    	return "accessDenied.jsp";
    }
    
    @Override
    public String getErrorPath() {
    	return "/error";
    }
    
	@SuppressWarnings("unchecked")
    private Map<String, Object> getOAuthMap(Principal principal){
    	
    	OAuth2Authentication oauth = (OAuth2Authentication) principal; 
		Authentication auth = oauth.getUserAuthentication();
		Map<String, Object> map = (Map<String, Object>) auth.getDetails();
		return map;
    }
	
	private MyUserAccounts registerOAuthUser(Map<String, Object> map) {
		
		String email = (String) map.get("email");
		String[] name = ((String) map.get("name")).split(" ");
		String first_name = name[0];
		String last_name = name[name.length-1];
		MyUserAccounts myUserAccounts = new MyUserAccounts();
		myUserAccounts.setEmail(email);
		myUserAccounts.setFirst_name(first_name);
		myUserAccounts.setLast_name(last_name);
		myUserAccounts.setUser_name(email);
		
		MyUserAccounts insert = userRepo.save(myUserAccounts);
		return insert;
	}
	
	private MyUserAccounts updateAccountOAuthLink(String email, Optional<MyUserAccounts> emailOptional, Integer link) {
		MyUserAccounts myUserAccounts = userRepo.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
		
		myUserAccounts.setEmail(emailOptional.get().getEmail()); 
		myUserAccounts.setId(emailOptional.get().getId()); 
		myUserAccounts.setFirst_name(emailOptional.get().getFirst_name()); 
		myUserAccounts.setLast_name(emailOptional.get().getLast_name()); 
		myUserAccounts.setUser_name(emailOptional.get().getUser_name());
		myUserAccounts.setPassword(emailOptional.get().getPassword());
		myUserAccounts.setEnabled(emailOptional.get().isEnabled());
		myUserAccounts.setOauth_link(link);
		
		MyUserAccounts updateUser = userRepo.save(myUserAccounts);
		return updateUser;
	}
}
