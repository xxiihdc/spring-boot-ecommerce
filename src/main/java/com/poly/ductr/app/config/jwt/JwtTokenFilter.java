package com.poly.ductr.app.config.jwt;

import com.nimbusds.jwt.JWTClaimsSet;
import com.poly.ductr.app.config.userpricle.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getJwt(request);
            token= token+"";
            if (!token.equals("null")) {
                if (jwtProvider.validateTokenLogin(token)) {
                    String username = jwtProvider.getUsernameFromToken(token);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null,
                                    userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (Exception e) {
        }
        filterChain.doFilter(request, response);
    }

    private String getJwt(HttpServletRequest req) {
        String authHeader = req.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer")) {
            return authHeader.replace("Bearer ", "");
        }
        return null;
    }
//    private UserDetails getUserDetails(String token){
//        UserPrinciple userPrinciple = new UserPrinciple();
//        JWTClaimsSet jwtClaimsSet= jwtProvider.getClaimsSetFromToken(token);
//        List<GrantedAuthority> lst = new ArrayList<>();
//        try {
//            String roles = jwtClaimsSet.getStringClaim("roles");
//            roles = roles.replace("[","").replace("]","");
//            String [] roleArr = roles.split(",");
//            for (String role : roleArr){
//                lst.add(new SimpleGrantedAuthority(role));
//            }
//            userPrinciple.setRoles(lst);
//            return  userPrinciple;
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
