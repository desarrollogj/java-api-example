package com.desarrollogj.exampleapi.commons.helper.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.SERVLET;
import static org.springframework.util.StringUtils.hasLength;

@Component
@Order(25)
@ConditionalOnWebApplication(type = SERVLET)
public class GlobalTransactionFilter implements Filter {
    private static final String GLOBAL_TRANSACTION_ID = "X-Global-Transaction-Id";
    private static final String LOG_TRANSACTION_ID = "txid";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) servletRequest;
        var httpResponse = (HttpServletResponse) servletResponse;

        var transactionId = httpRequest.getHeader(GLOBAL_TRANSACTION_ID);
        if (!hasLength(transactionId)) {
            transactionId = generateTransactionId();
        }

        httpRequest.setAttribute(GLOBAL_TRANSACTION_ID, transactionId);
        httpResponse.addHeader(GLOBAL_TRANSACTION_ID, transactionId);

        ThreadContext.put(LOG_TRANSACTION_ID, transactionId);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String generateTransactionId() {
        return UUID.randomUUID().toString();
    }
}
