package controller;

import dao.AccountsDAO;
import dao.RoleDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Accounts;

@WebServlet(name = "AdminServlet", urlPatterns = {"/admin"})
public class AdminServlet extends HttpServlet {

    private AccountsDAO accountsDAO;
    private RoleDAO roleDAO;

    @Override
    public void init() throws ServletException {
        accountsDAO = new AccountsDAO();
        roleDAO = new RoleDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String view = request.getParameter("view");

        if (view == null || view.equals("list")) {
            String pageParam = request.getParameter("page");
            int page = 1;
            if (pageParam != null) {
                try {
                    page = Integer.parseInt(pageParam);
                    if (page < 1) page = 1;
                } catch (NumberFormatException e) {
                    page = 1;
                }
            }

            List<Accounts> list = accountsDAO.getAccounts(page);
            request.setAttribute("accounts", list);
            request.setAttribute("currentPage", page);
            request.getRequestDispatcher("/WEB-INF/Views/admin/account-management.jsp").forward(request, response);

        } else if (view.equals("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            int result = accountsDAO.delete(id);
            if (result == 1) {
                response.sendRedirect(request.getContextPath() + "/admin?view=list&delete=1");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin?view=list&delete=0");
            }

        } else if (view.equals("create")) {
            int nextId = accountsDAO.getNextId();
            request.setAttribute("nextId", nextId);
            request.getRequestDispatcher("/WEB-INF/Views/admin/create-user.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");

            Accounts account = new Accounts();
            account.setId(id);
            account.setFullName(fullName);
            account.setEmail(email);
            account.setPhone(phone);

            int result = accountsDAO.update(account);
            if (result == 1) {
                response.sendRedirect(request.getContextPath() + "/admin?view=list");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin?view=update&id=" + account.getId());
            }

        } else if ("create".equals(action)) {

            String userName = request.getParameter("userName");
            String passWord = request.getParameter("passWord");
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

            Accounts account = new Accounts();
            account.setUserName(userName);
            account.setPassWord(passWord); // Hash trước khi lưu nếu muốn
            account.setFullName(fullName);
            account.setEmail(email);
            account.setPhone(phone);

            // Lấy roleId và tạo account
            int roleId = roleDAO.getRoleIdByName(roleName);
            int newId = accountsDAO.create(account, roleId);

            if (newId > 0) {
                response.sendRedirect(request.getContextPath() + "/admin?view=list&created=1");
            } else {
                request.setAttribute("error", "Không thể tạo Account!");
                request.getRequestDispatcher("/WEB-INF/Views/admin/create-user.jsp").forward(request, response);
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Admin management servlet";
    }
}
