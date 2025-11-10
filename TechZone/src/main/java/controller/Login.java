/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AuthDAO;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import model.AccountUsers;
import org.json.JSONObject;

/**
 * Handles user authentication (login) requests and session initialization.
 *
 * <p>This servlet serves the login page on GET and processes credentials on
 * POST. If authentication succeeds, a session attribute named
 * <code>account</code> is set and the user is redirected based on role.</p>
 *
 * @author acer
 */
@WebServlet(name = "Login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

/**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(request, response); // forward to login JSP
    }

    /**
     * Handles the HTTP <code>POST</code> method for authenticating a user.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        boolean verify = verifyRecaptcha(gRecaptchaResponse);
        if (!verify) {
            request.setAttribute("error", "error");
            request.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(request, response);
            return;
        }
        String username = request.getParameter("username"); // read username from form
        String password = request.getParameter("password"); // read password from form
        AuthDAO userdao = new AuthDAO(); // create DAO responsible for authentication
        AccountUsers account = userdao.login(username, password); // validate credentials and fetch account
        if (account != null) { // if authentication successful
            HttpSession session = request.getSession(); // retrieve or create HTTP session
            session.setAttribute("account", account); // store account in session
            if (account.getAccountroles().equals("Admin")) { // check if user has Admin role

                response.sendRedirect(getServletContext().getContextPath() + "/admin/report"); // redirect admin to dashboard/report
            } else { // non-admin user

                response.sendRedirect(getServletContext().getContextPath() + "/products"); // redirect user to product listing
            }

        } else { // authentication failed
            response.sendRedirect(getServletContext().getContextPath() + "/login?error=true"); // redirect back to login with error flag
        }

    }
    
    private boolean verifyRecaptcha(String gRecaptchaResponse) {
        try {
            String url = "https://www.google.com/recaptcha/api/siteverify"; // Google reCAPTCHA verify URL

            // Build request parameters (secret key + user response)
            String params = "secret=" + URLEncoder.encode("6Lc7rgcsAAAAAHd5k3efCGT_d2H8Zd4S8V9FSi12", "UTF-8")
                    + "&response=" + URLEncoder.encode(gRecaptchaResponse, "UTF-8");

            URL obj = new URL(url); // Create URL object
            HttpURLConnection con = (HttpURLConnection) obj.openConnection(); // Open connection
            con.setRequestMethod("POST"); // Use POST method
            con.setDoOutput(true); // Allow sending data
            con.getOutputStream().write(params.getBytes("UTF-8")); // Send parameters

            // Read response from Google server
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse JSON response
            JSONObject json = new JSONObject(response.toString());
            return json.getBoolean("success"); // Return true if verification passed
        } catch (Exception e) {
            e.printStackTrace(); // Print error if something goes wrong
            return false; // Return false on failure
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description"; // default description string
    }// </editor-fold>

}
