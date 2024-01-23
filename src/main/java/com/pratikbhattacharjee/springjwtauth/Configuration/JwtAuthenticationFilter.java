package com.pratikbhattacharjee.springjwtauth.Configuration;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.ServletException;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtService jwtService;

    //Helps fetch user from the database
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request , @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        // Implement the logic for validating the token and setting the authentication on the SecurityContext

        //Getting the bearer token
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        if (authHeader != null  authHeader.startsWith("Bearer ")) {
            //Valid bearer format
            //Removing the Bearer prefix
            jwt = authHeader.substring(7);

            //Check the UserDetails service to check if we have the user registered or not
            //Get tuse user email
            username = jwtService.extractUsername(jwt);

            //Check if the user is registered or not
            if (username!= null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                //check if the token is valid
                if (jwtService.isTokenValid(jwt, userDetails)){

                    //Set the authentication on the SecurityContext
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                    );
                authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
                );

                //Update the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } else {
            throw new ServletException("Missing or invalid Authorization header");
        }
        
    }
    
}
