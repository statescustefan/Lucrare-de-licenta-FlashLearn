package com.info.uaic.licenta.security;

import java.util.Arrays;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

@Service
public class FacebookSignInAdapter implements SignInAdapter {

	@Override
	public String signIn(String localUserId, Connection<?> connection, NativeWebRequest request) {
		// TODO Auto-generated method stub
		System.out.println("Sign in adapter");
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(connection.getDisplayName(),null,Arrays.asList(new SimpleGrantedAuthority("ROLE_FACEBOOK"))));
		return null;
	}

	
}
