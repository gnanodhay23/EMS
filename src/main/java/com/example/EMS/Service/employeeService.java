package com.example.EMS.Service;


import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.EMS.Repository.employeeRepo;
import com.example.EMS.config.securityConfig;
import com.example.EMS.model.employee;
import com.example.EMS.model.loginDto;

@Service
public class employeeService {
	
	public final employeeRepo employeeRepo;
	public final securityConfig seConfig;
	
	public employeeService(employeeRepo employeeRepo, securityConfig seConfig)
	{
		this.employeeRepo=employeeRepo;
		this.seConfig=seConfig;
	}
	
	public employee saveEmployee(employee a)
	{
		employee b = employeeRepo.save(a);
		return b;
	}
	
	public employee loginService(loginDto a)
	{
		String name=a.getName();
		Optional<employee> x = employeeRepo.findByName(name);
	
		boolean isvalid = seConfig.passwordEncoder().matches(a.getPassword() ,x.get().getPassword());
		if(isvalid)
		{
			return x.get();
		}
		else
		{
			System.out.println("login entered encoded password"+seConfig.passwordEncoder().encode(a.getPassword()));
			System.out.println("password inserted in the db"+x.get().getPassword());
			return null;
		}
		
		}
}
