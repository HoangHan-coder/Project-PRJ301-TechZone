package controller;

import dao.AccountDAO;
import dao.AccountDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Account;

@WebServlet(name = "AdminServlet", urlPatterns = {"/admin"})
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String view = request.getParameter("view");
        AccountDAO dao = new AccountDAO();

        if (view == null || view.equals("list")) {
            String pageParam = request.getParameter("page");
            int page = 1;
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

            List<Account> list = dao.getAccounts(page);
            request.setAttribute("accounts", list);
            request.setAttribute("currentPage", page);
            request.getRequestDispatcher("/WEB-INF/Views/admin/account-management.jsp").forward(request, response);

        } else if (view.equals("update")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Account acc = dao.getById(id);
            
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        AccountDAO dao = new AccountDAO();

        if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String roleName = request.getParameter("role");

            Account account = new Account();
            account.setAccountId(id);
            account.setFullName(fullName);
            account.setEmail(email);
            account.setPhone(phone);
            account.setRoleName(roleName);

            int result = dao.update(account);
            if (result == 1) {
                response.sendRedirect(request.getContextPath() + "/admin?view=list");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin?view=update&id=" + account.getAccountId());
            }

        } else if ("create".equals(action)) {

            String userName = request.getParameter("userName");
            String passWord = request.getParameter("passWordHarh");
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String roleName = request.getParameter("role");

            // Validate
            if (userName == null || userName.trim().isEmpty()
                    || passWord == null || passWord.trim().isEmpty()
                    || fullName == null || fullName.trim().isEmpty()) {

                request.setAttribute("error", "Username, Password và Họ & tên không được để trống!");
                request.getRequestDispatcher("/WEB-INF/Views/admin/create-user.jsp").forward(request, response);
                return;
            }

            Account account = new Account();
            account.setUserName(userName);
            account.setPassWordHarh(passWord); // Hash trước khi lưu nếu muốn
            account.setFullName(fullName);
            account.setEmail(email);
            account.setPhone(phone);
            account.setRoleName(roleName);

            int result = dao.create(account);
            if (result == 1) {
                response.sendRedirect(request.getContextPath() + "/admin?view=list");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin?view=create");
            }

        }
    }

    @Override
    public String getServletInfo() {
        return "Admin management servlet";
    }
}
