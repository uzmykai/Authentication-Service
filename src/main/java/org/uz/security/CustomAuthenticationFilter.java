package org.uz.security;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.uz.service.UserService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    private final UserService userService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        try{
            if(StringUtils.isEmpty(authHeader) || !authHeader.startsWith("Bearer")){
                filterChain.doFilter(request,response);
            }
            else{
                jwt = authHeader.substring(7);
                username = jwtService.extractUsername(jwt);
                if (StringUtils.isNotEmpty(username) &&
                        SecurityContextHolder.getContext().getAuthentication() == null){
                    SecurityContextHolder.getContext().setAuthentication(getAuthentication(
                            username,jwt,request
                    ));

                }
                filterChain.doFilter(request, response);
            }
        }catch(Exception e){
            if (!response.isCommitted()) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
            } else {
                e.printStackTrace();
            }
        }
    }

    private Authentication getAuthentication(String username, String jwt,
                                             HttpServletRequest request){
        UserDetails userDetails =
                userService.userDetailsService().loadUserByUsername(
                        username);
        if (jwtService.isTokenValid(jwt, userDetails)) {

            UsernamePasswordAuthenticationToken token = new  UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            return token;

        }
        return null;
    }
}

