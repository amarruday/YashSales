package com.yashsales.restcontrollers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yashsales.service.impl.EmailServiceImpl;

@RestController
@RequestMapping("/password")
@CrossOrigin("*")
public class PasswordController {
	
	@Autowired
	private EmailServiceImpl emailService;
	
	@GetMapping("forgotpassword/{email}")
	public ResponseEntity<Map<String, String>> forgotPassword(@PathVariable("email") String email) {
		return new ResponseEntity<>(emailService.sendSimpleMessage(email), HttpStatus.OK);
	}

}