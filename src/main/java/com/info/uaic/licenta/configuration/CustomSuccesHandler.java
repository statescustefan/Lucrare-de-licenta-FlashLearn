package com.info.uaic.licenta.configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomSuccesHandler extends SimpleUrlAuthenticationSuccessHandler{

	private RedirectStrategy rs = new DefaultRedirectStrategy();
	
	@Override
	protected void handle (HttpServletRequest req, HttpServletResponse resp, Authentication auth) throws IOException {
		
		String targetUrl = determineTargetUrl(auth);
		
		if ( resp.isCommitted() ) {
			System.out.println("Can't redirect");
			return ;
		}
		
		rs.sendRedirect(req, resp, targetUrl);
	}
	
	protected String determineTargetUrl (Authentication auth) {
		String url = "";
		
		Collection <? extends GrantedAuthority> authorities = auth.getAuthorities();
		
		List<String> roles = new ArrayList<String>();
		
		for (GrantedAuthority ga : authorities) {
			roles.add(ga.getAuthority());
		}
		
		if (isUser(roles)) {
			url = "/FlashLearn/student/dashboard";
		}
		return url;
	}
		
    private boolean isUser(List<String> roles) {
    	if (roles.contains("ROLE_STUDENT")) {
    		return true;
    	}
    	return false;
    }
}
