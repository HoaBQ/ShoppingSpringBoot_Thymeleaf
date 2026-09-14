package com.shopping.config;

import com.shopping.entity.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Chặn truy cập /admin/* nếu chưa đăng nhập hoặc không có quyền Admin (role == 1).
 */
public class AdminAccessFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        User loggedUser = session != null ? (User) session.getAttribute("loggedUser") : null;

        if (loggedUser != null && loggedUser.getRole() == 1) {
            chain.doFilter(req, res);
        } else {
            response.sendRedirect(request.getContextPath() + "/login?error=access_denied");
        }
    }
}
