package com.example.EMS.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
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
	@Autowired
    private AuthenticationManager authenticationManager;
	@Autowired
    private com.example.EMS.Service.jwtService jwtService;

	public final employeeService employeeService;
	public final securityConfig seConfig;
	//public final com.example.EMS.model.loginDto loginDto;
	public loginController(employeeService employeeService, securityConfig seConfig)
	{
		this.employeeService=employeeService;
		this.seConfig=seConfig;
	}
	
//	@PostMapping("/login")
//		public ResponseEntity<String> login(@Valid @RequestBody loginDto a)
//	{		
//			employee result= employeeService.loginService(a);
//			if(result == null)
//			{
//				return ResponseEntity.badRequest().body("Invalid input");
//			}
//			else
//			{
//				return ResponseEntity.ok().body("Welcome "+ result.getName());
//			}
//	}

	// login controller yuisng the authenticate method in authentication manager
			
	
	@GetMapping("/auth/dashboard")
	public ResponseEntity<employee> dashboard() {
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		var principal= authentication.getPrincipal();
		if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
			String name=((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();	
			
			employee user= employeeService.getEmployeeDetails(name);
			 return ResponseEntity.ok(user);
		}
		else
		{
			return ResponseEntity.ok().body(null);
		}
		
	   
	}

	    @PostMapping("/login")
	    public ResponseEntity<String> login(@RequestBody loginDto request) {
	    	

	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        request.getName(),
	                        request.getPassword()
	                )
	        );

	        // if we reach here → credentials valid
	        String token = jwtService.generateToken(request.getName());

	        return ResponseEntity.ok().body("welcome "+request.getName()+"!!!    token : "+ token);
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
	@PostMapping("/logout")
	public ResponseEntity<String> logout()
	{
		
		return ResponseEntity.ok().body("Thank you !!!  happy customer");
	}

}
