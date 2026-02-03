package com.example.EMS.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.Service.employeeService;
import com.example.EMS.config.securityConfig;
import com.example.EMS.model.employee;
import com.example.EMS.model.loginDto;

import jakarta.validation.Valid;

@RestController
public class loginController {
	public final employeeService employeeService;
	public final securityConfig seConfig;
	//public final com.example.EMS.model.loginDto loginDto;
	public loginController(employeeService employeeService, securityConfig seConfig)
	{
		this.employeeService=employeeService;
		this.seConfig=seConfig;
	}
	
	@PostMapping("/login")
		public ResponseEntity<String> login(@Valid @RequestBody loginDto a)
	{		
			employee result= employeeService.loginService(a);
			if(result == null)
			{
				return ResponseEntity.badRequest().body("Invalid input");
			}
			else
			{
				return ResponseEntity.ok().body("Welcome "+ result.getName());
			}
	}
	
	@PostMapping("/register")
	public ResponseEntity<employee> registEmployee(@RequestBody employee a)
	{
		System.out.println("in regasitwer");
		String passw=a.getPassword();
		a.setPassword(seConfig.passwordEncoder().encode(passw));
		employee b = employeeService.saveEmployee(a);
		
		return ResponseEntity.ok(b);
	}
	

}
