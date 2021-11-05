package br.flower.boot.auth.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioServiceImp usuarioService;
	
	
	@PostMapping
	public void creatUser() {
		
		
	}
}
