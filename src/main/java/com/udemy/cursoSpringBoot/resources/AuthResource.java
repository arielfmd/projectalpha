package com.udemy.cursoSpringBoot.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.udemy.cursoSpringBoot.dto.EmailDTO;
import com.udemy.cursoSpringBoot.security.JWTUtil;
import com.udemy.cursoSpringBoot.security.UserSS;
import com.udemy.cursoSpringBoot.services.AuthService;
import com.udemy.cursoSpringBoot.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;

    @Autowired
	private AuthService service;

	@PostMapping(value = "/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		return ResponseEntity.noContent().build();
	}

    @PostMapping(value = "/forgot")
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
		service.sendNewPassword(objDto.getEmail());
		return ResponseEntity.noContent().build();
	}
}