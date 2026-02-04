package com.example.EMS.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.EMS.Repository.employeeRepo;
import com.example.EMS.model.employee;

@Service
public class employeeService implements UserDetailsService {

    private final employeeRepo employeeRepo;

    public employeeService(employeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public employee saveEmployee(employee a) {
        return employeeRepo.save(a);
    }
    public employee getEmployeeDetails(String name)
    {
    	return employeeRepo.findByName(name).get();
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        employee user = employeeRepo.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + name));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getName())
                .password(user.getPassword())
                .roles(user.getRole())   // role stored in DB
                .build();
    }
}

//	public employee loginService(loginDto a)
//	{
//		String name=a.getName();
//		Optional<employee> x = employeeRepo.findByName(name);
//	
//		boolean isvalid = seConfig.passwordEncoder().matches(a.getPassword() ,x.get().getPassword());
//		if(isvalid)
//		{
//			return x.get();
//		}
//		else
//		{
//			System.out.println("login entered encoded password"+seConfig.passwordEncoder().encode(a.getPassword()));
//			System.out.println("password inserted in the db"+x.get().getPassword());
//			return null;
//		}
//		}
