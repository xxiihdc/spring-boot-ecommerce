package com.poly.ductr.app.config.userpricle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poly.ductr.app.model.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrinciple implements UserDetails {
    public Collection<? extends GrantedAuthority> getRoles() {
		return roles;
	}

	public void setRoles(Collection<? extends GrantedAuthority> roles) {
		this.roles = roles;
	}

	public UserPrinciple(Integer id, String username, String password, Collection<? extends GrantedAuthority> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;

//        roleArr = (String[]) this.getAuthorities().toArray();
    }
    public UserPrinciple(){
    }

    private String viewName;


    private Integer id;
    private String username;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> roles;

    private String [] roleArr;

    public String getViewName(){
        return  this.viewName;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.id + "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static UserPrinciple build(Customer customer) {
        List<GrantedAuthority> lst = new ArrayList<>();
        if (customer.getAdmin()) {
            lst.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            lst.add(new SimpleGrantedAuthority("ROlE_USER"));
            UserPrinciple u = new UserPrinciple(customer.getId(), customer.getCustomerName(),
                    "Hide", lst);
            u.viewName = "ADMIN";
            System.out.println("Role admin");
            return u;
        } else {
            lst.add(new SimpleGrantedAuthority("ROLE_USER"));
            System.out.println("Here");
            UserPrinciple u = new UserPrinciple(customer.getId(), customer.getCustomerName(),
                    "Hide", lst);
            u.viewName =customer.getCustomerName();
            System.out.println("Role user");

            return u;

        }

    }


}
