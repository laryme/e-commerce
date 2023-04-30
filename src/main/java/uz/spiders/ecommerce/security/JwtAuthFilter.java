package uz.spiders.ecommerce.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.spiders.ecommerce.utils.Constants;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(Constants.AUTH_HEADER);
        final String jwt;
        final String username;
        System.out.println(header);

        if(header == null || !header.startsWith(Constants.AUTH_TYPE_BEARER)){
            filterChain.doFilter(request, response);
            return;
        }

        jwt = header.substring(Constants.AUTH_TYPE_BEARER.length());
        username = jwtService.extractUsernameFromAccessToken(jwt);

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            //todo handle user not found exception
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if(jwtService.isAccessTokenValid(jwt, userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
