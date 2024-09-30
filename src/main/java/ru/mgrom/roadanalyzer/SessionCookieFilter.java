package ru.mgrom.roadanalyzer;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SessionCookieFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Получаем текущую сессию
        String sessionId = httpRequest.getSession().getId();

        // Устанавливаем параметры куки
        httpResponse.addHeader("Set-Cookie", "JSESSIONID=" + sessionId + "; Max-Age=31536000; Path=/; HttpOnly");

        // Продолжаем выполнение цепочки фильтров
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Инициализация фильтра (при необходимости)
    }

    @Override
    public void destroy() {
        // Освобождение ресурсов (при необходимости)
    }
}