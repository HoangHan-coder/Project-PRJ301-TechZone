/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AccountsDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Accounts;

/**
 *
 * @author pc
 */
@WebServlet(name = "AdminServlet", urlPatterns = {"/admin"})
public class AdminServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        String view = request.getParameter("view");
        AccountsDAO dao = new AccountsDAO();

        if (view == null || view.equals("list")) {
            String pageParam = request.getParameter("page");
            int page = 1; // mặc định là trang 1
            if (pageParam != null) {
                try {
                    page = Integer.parseInt(pageParam);
                    if (page < 1) {
                        page = 1;
                    }
                } catch (NumberFormatException e) {
                    page = 1;
                }
            }

            List<Accounts> list = dao.getAccounts(page);
            int totalPages = dao.getNextId();
            request.setAttribute("accounts", list);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.getRequestDispatcher("/WEB-INF/Views/admin/account-management.jsp").forward(request, response);

        } else if (view.equals("update")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Accounts acc = dao.getById(id);
            request.setAttribute("account", acc);
            request.getRequestDispatcher("/WEB-INF/Views/admin/update-profile.jsp").forward(request, response);
        } else if (view.equals("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            int result = dao.delete(id);

            if (result == 1) {
                response.sendRedirect(request.getContextPath() + "/admin?view=list&delete=1");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin?view=list&delete=0");
            }
        } else if (view.equals("create")) {
            int nextId = dao.getNextId();
            request.setAttribute("nextId", nextId);
            request.getRequestDispatcher("/WEB-INF/Views/admin/create-user.jsp").forward(request, response);
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        AccountsDAO dao = new AccountsDAO();

        if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("fullName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");

            Accounts account = new Accounts();
            account.setId(id);
            account.setFullName(name);
            account.setEmail(email);
            account.setPhone(phone);

            int result = dao.update(account);
            if (result == 1) {
                response.sendRedirect(request.getContextPath() + "/admin?action=list");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin?view=update&id=" + account.getId());
            }
        } else if ("create".equals(action)) {
            String userName = request.getParameter("userName");
            String passWord = request.getParameter("passWord");
            String name = request.getParameter("fullName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");

            // Kiểm tra rỗng
            if (name == null || name.trim().isEmpty()) {
                request.setAttribute("error", "Tên Account không được để trống!");
                request.getRequestDispatcher("/WEB-INF/Views/admin/create-user.jsp").forward(request, response);
                return;
            }
            // Tạo object và set dữ liệu
            Accounts account = new Accounts();
            account.setUserName(userName);
            account.setPassWord(passWord);
            account.setFullName(name);
            account.setEmail(email);
            account.setPhone(phone);

//            int id = dao.getNextId();
            int re = dao.create(account);

            if (re == 1) {
                response.sendRedirect(request.getContextPath() + "/admin?view=list&created=1");
            } else {
                request.setAttribute("error", "Không thể tạo Account!");
                request.getRequestDispatcher("/WEB-INF/Views/admin/create-user.jsp").forward(request, response);
            }
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
