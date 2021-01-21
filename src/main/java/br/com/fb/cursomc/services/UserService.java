package br.com.fb.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.fb.cursomc.security.UserSS;

public class UserService {

	public static UserSS authenticated() {
		try {
			// pode não ter um usuário logado e dar erro de casting
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
		} catch (Exception e) {
			return null;
		}
	}
	
}
