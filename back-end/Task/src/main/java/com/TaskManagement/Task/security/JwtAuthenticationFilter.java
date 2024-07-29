package com.TaskManagement.Task.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private  JWTGenerator tokenGerator;
    @Autowired
    private  CustomUserDetailsService customUserDetailsService;

    public  JwtAuthenticationFilter(){}
    @Autowired
    public JwtAuthenticationFilter(JWTGenerator tokenGerator, CustomUserDetailsService customUserDetailsService) {
        this.tokenGerator = tokenGerator;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token=getJWTFromRequest(request);
        if(StringUtils.hasText(token)&&tokenGerator.validdateToken(token)){
            String username=tokenGerator.getUsernameFromJwt(token);
            UserDetails userDetails= customUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }
        filterChain.doFilter(request,response);
    }
    private String getJWTFromRequest(HttpServletRequest request){
        String bearertoken= request.getHeader("Authorization");
        if(StringUtils.hasText(bearertoken)&&bearertoken.startsWith("Bearer ")){
            return  bearertoken.substring(7,bearertoken.length());
        }
        return null;
    }
}
