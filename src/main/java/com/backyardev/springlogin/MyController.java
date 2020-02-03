package com.backyardev.springlogin;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.backyardev.springlogin.model.MyUserDetails;

@Controller
public class MyController {

	@SuppressWarnings("unchecked")
	@RequestMapping({ "/user", "/me" })
	@ResponseBody
	public Map<String, Object> user(Principal principal) {
		Map<String, Object> details = new LinkedHashMap<>();
		Map<String, Object> map = new LinkedHashMap<>();
		try {
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
		} catch(ClassCastException e) {
			System.out.println("DB login");
			String username;
			String email;
			Object principal1 = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal1 instanceof MyUserDetails) {
			   username = ((MyUserDetails)principal1).getUsername();
			   email = ((MyUserDetails)principal1).getEmail();
			} else {
			  username = principal1.toString();
			  email = "email@DBUser.com";
			}
			map.put("name", username);
        	map.put("email", email);
        	map.put("propic", "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQoR3lxH768V306NfcbE7EcULhfnc8i1wv8Rb9tr2xBdyxDivZN");
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

	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("errorMsg", "Your username and password are invalid.");

        if (logout != null)
            model.addAttribute("msg", "You have been logged out successfully.");

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
}
