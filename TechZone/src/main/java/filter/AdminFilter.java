/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.AccountUsers;

/**
 * Authorization filter for admin-only routes.
 *
 * <p>Intercepts all requests matching <code>/admin/*</code> and verifies the
 * current session contains an authenticated user whose role is Admin. If no
 * session or non-admin user is found, redirects accordingly.</p>
 */
/**
 *
 * @author acer
 */
@WebFilter(filterName = "AdminFilter", urlPatterns = {"/admin/*"})
public class AdminFilter implements Filter {
    
   
    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request; // cast to HTTP request
        HttpServletResponse resp = (HttpServletResponse) response; // cast to HTTP response
        HttpSession session = req.getSession(false); // retrieve existing session, do not create
        
        if (session == null || session.getAttribute("account") == null) { // not logged in
            resp.sendRedirect(req.getContextPath() + "/login"); // redirect to login page
        } else {
            AccountUsers admin = (AccountUsers) session.getAttribute("account"); // get user from session
            if(admin.getAccountroles().equals("Admin")){
                chain.doFilter(request, response); // allow request to proceed
            } else {
                resp.sendRedirect(req.getContextPath() + "/error"); // non-admin redirected to error page
            }
            
        }
        
    }
    
}
