package com.poly.ductr.app.config.userpricle;

import com.poly.ductr.app.model.Customer;
import com.poly.ductr.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    CustomerService customerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerService.findById(Integer.parseInt(username)).get();
        UserPrinciple userPrinciple = UserPrinciple.build(customer);
        return userPrinciple;
    }

}
