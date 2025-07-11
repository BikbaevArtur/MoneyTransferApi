package ru.bikbaev.moneytransferapi.security;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handler;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            @Qualifier("handlerExceptionResolver")
            HandlerExceptionResolver handler,

            JwtService jwtService,
            UserDetailsService userDetailsService
    ) {

        this.handler = handler;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");


        if (authHeader == null) {
            log.trace("No Authorization header provided");
            filterChain.doFilter(request, response);
            return;
        }

        if (!authHeader.startsWith("Bearer ")) {
            log.trace("Malformed Authorization header: {}", authHeader);
            filterChain.doFilter(request, response);
            return;
        }

        try {

            final String token = authHeader.substring(7);

            final String login = jwtService.extractLogin(token);

            log.debug("Extracted login from token: {}", login);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (login != null && authentication == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(login);

                if (jwtService.isTokenValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities()
                            );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    log.debug("User authenticated: {}", login);
                }else {
                    log.warn("Invalid token for user: {}", login);
                }
            } else if (authentication != null) {
                log.trace("SecurityContext already contains authentication: {}", authentication.getName());
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.warn("Authentication exception: {}", e.getMessage());
            handler.resolveException(request, response, null, e);
        }

    }
}
