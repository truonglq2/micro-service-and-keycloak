package com.programming.techie.apigatewayservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {
  private String url;
  private final List<String> allowedOrigins = Arrays.asList(url);
  private static final String REQUEST_ID = "request_id";
  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                  HttpServletResponse httpServletResponse, FilterChain filterChain)
      throws ServletException, IOException {
    String origin = httpServletRequest.getHeader("Origin");
    httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
    httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
    httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
    httpServletResponse.setHeader("Access-Control-Allow-Origin",
        allowedOrigins.contains(origin) ? origin : "*");
    httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
    String requestId = UUID.randomUUID().toString();
    httpServletRequest.setAttribute(REQUEST_ID, requestId);
    logRequest((HttpServletRequest) httpServletRequest, requestId);
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
  private void logRequest(HttpServletRequest request, String requestId) {
    if (request != null) {
      StringBuilder data = new StringBuilder();
      data.append("\nLOGGING REQUEST-----------------------------------\n")
          .append("[REQUEST-ID]: ").append(requestId).append("\n")
          .append("[PATH]: ").append(request.getRequestURI()).append("\n")
          .append("[QUERIES]: ").append(request.getQueryString()).append("\n")
          .append("[HEADERS]: ").append("\n");

      Enumeration headerNames = request.getHeaderNames();
      while (headerNames.hasMoreElements()) {
        String key = (String) headerNames.nextElement();
        String value = request.getHeader(key);
        data.append("---").append(key).append(" : ").append(value).append("\n");
      }
      data.append("LOGGING REQUEST-----------------------------------\n");

      log.info(data.toString());
    }
  }
}
