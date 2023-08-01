package com.michael.socialmedia.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter  extends OncePerRequestFilter {

    private final JwtFilter jwtFilter;

    private  final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(
            @NotNull  HttpServletRequest request, @NotNull HttpServletResponse response,
            @NotNull  FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String username = null;
        String accessToken = null ;

       if(authHeader!= null && authHeader.startsWith("Bearer ")){
            accessToken =authHeader.substring(7);
           username = jwtFilter.extractUsername(authHeader);
       }

       if(username!= null && SecurityContextHolder.getContext()== null){

           UserDetails userDetails = userDetailsService.loadUserByUsername(username);
               if(jwtFilter.isTokenValid(accessToken,userDetails)){
                   UsernamePasswordAuthenticationToken usernamePassword=
                           new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                   usernamePassword.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                   SecurityContextHolder.getContext().setAuthentication(usernamePassword);

               }


       }
           filterChain.doFilter(request,response);


    }
}
