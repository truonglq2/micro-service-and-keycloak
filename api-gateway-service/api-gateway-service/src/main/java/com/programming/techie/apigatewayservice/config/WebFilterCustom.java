package com.programming.techie.apigatewayservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

@Slf4j
public class WebFilterCustom implements WebFilter {

  private String url;
  private final List<String> allowedOrigins = Arrays.asList(url);
  private static final String REQUEST_ID = "request_id";

  @Override
  public Mono<Void> filter(ServerWebExchange serverWebExchange,
                           WebFilterChain webFilterChain) {

    /*serverWebExchange.getResponse()
        .getHeaders().add("web-filter", "web-filter-test");*/

    String origin = serverWebExchange.getRequest().getHeaders().getOrigin();
    /*serverWebExchange.getResponse()
        .getHeaders().add("Access-Control-Allow-Methods", "*");
    serverWebExchange.getResponse()
        .getHeaders().add("Access-Control-Max-Age", "3600");
    serverWebExchange.getResponse()
        .getHeaders().add("Access-Control-Allow-Headers", "*");
    serverWebExchange.getResponse()
        .getHeaders().add("Access-Control-Allow-Origin",
        allowedOrigins.contains(origin) ? origin : "*");
    serverWebExchange.getResponse()
        .getHeaders().add("Access-Control-Allow-Credentials", "true");*/
    String requestId = UUID.randomUUID().toString();
    serverWebExchange.getAttributes().put(REQUEST_ID, requestId);
    logRequest( serverWebExchange, requestId);
    return webFilterChain.filter(serverWebExchange);
  }

  private void logRequest(ServerWebExchange serverWebExchange, String requestId) {
    if (serverWebExchange != null) {
      StringBuilder data = new StringBuilder();
      data.append("\nLOGGING REQUEST-----------------------------------\n")
          .append("[REQUEST-ID]: ").append(requestId).append("\n")
          .append("[PATH]: ").append(serverWebExchange.getRequest().getURI()).append("\n")
          .append("[HEADERS]: ").append("\n");

      data.append("LOGGING REQUEST-----------------------------------\n");

      log.info(data.toString());
    }
  }
}
