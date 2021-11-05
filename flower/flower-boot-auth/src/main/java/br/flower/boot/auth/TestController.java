package br.flower.boot.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/teste")
public class TestController {

	
	@PostMapping
	public String test() {
		return "oi";
	}
}
