package br.flower.boot.auth.security.core;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.flower.boot.auth.user.Usuario;
import br.flower.boot.auth.user.role.UserAuthRole;

public class UserDatailsFactory {
	
	private UserDatailsFactory() {}
	
	
	public static UserDetails creater(Usuario customer) {
		return CustomUserDetails.withUsername(customer.getUsername())
                .password(customer.getPassword())
                .disabled(customer.isDisabled())
                .authorities(mapToGrantedAuthorities(customer)).build();
		
	}
	
	private static Collection<GrantedAuthority> mapToGrantedAuthorities(Usuario authRoles) {
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for(UserAuthRole role: authRoles.getUserAuthRole() ) {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName().toString()));
		}
		return authorities;
	}
}
