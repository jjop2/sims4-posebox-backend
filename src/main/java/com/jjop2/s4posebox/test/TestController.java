package com.jjop2.s4posebox.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jjop2.s4posebox.security.details.CustomUserDetails;

@RestController
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@GetMapping("/test")
	public String test(
		@AuthenticationPrincipal CustomUserDetails principal
	) {
		System.out.println("=== Test Controller 진입 ===");
		return principal.getUsername();
	}
	
	@GetMapping("/beans")
	public String beans() {
//	    System.out.println(applicationContext.getBeansOfType(JsonMapper.class));
	    return "ok";
	}
}
