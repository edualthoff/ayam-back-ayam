package br.flower.boot.auth.teste;

import java.util.Base64;
import java.util.UUID;

import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/teste2")
public class TestController2 {

	
	@PostMapping(path = "")
	public String test() {
		System.out.println("aq "+UUID.randomUUID()+" "
	+Base64.getEncoder().encodeToString("8e28fa6b-fe94-4508-8c87-546ada92d041".getBytes()));
		return "oi";
	}
}
