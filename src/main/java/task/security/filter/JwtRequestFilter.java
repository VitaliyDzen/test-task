package task.security.filter;

import static task.constants.AppConstant.AUTHORIZATION;
import static task.constants.AppConstant.UNABLE_TO_GET_JWT_TOKEN;

import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import task.security.jwt.JwtTool;
import task.security.service.SecurityService;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final SecurityService securityService;
    private final JwtTool jwtTool;

    @Autowired
    public JwtRequestFilter(
        SecurityService securityService, JwtTool jwtTool) {
        this.securityService = securityService;
        this.jwtTool = jwtTool;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain chain) throws IOException, ServletException {
        final String requestTokenHeader = request.getHeader(AUTHORIZATION);
        String username = null;
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTool.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException | ExpiredJwtException e) {
                throw new IllegalArgumentException(UNABLE_TO_GET_JWT_TOKEN, e);
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.securityService.loadUserByUsername(username);

            if (jwtTool.isTokenValid(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext()
                    .setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}