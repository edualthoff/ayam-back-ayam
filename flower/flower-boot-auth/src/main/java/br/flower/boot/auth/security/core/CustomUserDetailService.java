package br.flower.boot.auth.security.core;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.flower.boot.auth.user.Usuario;
import br.flower.boot.auth.user.UsuarioServiceImp;

@Service("userDetailsService")
public class CustomUserDetailService implements UserDetailsService {


    @Autowired
    UsuarioServiceImp usuarioService;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = usuarioService.getByEmail(username);	
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return UserDatailsFactory.creater(user);
	}

}
