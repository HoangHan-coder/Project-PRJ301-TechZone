/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Authentication filter for user-protected endpoints.
 *
 * <p>Ensures the user is authenticated before accessing the following paths:
 * <code>/profile</code>, <code>/order</code>, <code>/cartitem</code>,
 * and <code>/feedback-user</code>. Unauthenticated users are redirected to
 * the login page.</p>
 */
/**
 *
 * @author acer
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"/profile","/order","/cartitem","/feedback-user"})
public class AuthFilter implements Filter {
    
    

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request; // cast to HTTP request
        HttpServletResponse resp = (HttpServletResponse) response; // cast to HTTP response
        HttpSession session = req.getSession(false); // get existing session, do not create
        if (session == null || session.getAttribute("account") == null) { // unauthenticated user
            resp.sendRedirect(req.getContextPath() + "/login"); // redirect to login
        } else {
            chain.doFilter(request, response); // proceed if authenticated
        }
        
    }
    
    

    
}

