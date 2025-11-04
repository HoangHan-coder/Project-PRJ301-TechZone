package controller;

import dao.AccountDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import model.Account;
import until.Pagination;

@WebServlet(name = "AdminServlet", urlPatterns = {"/admin/account"})
public class AdminServlet extends HttpServlet {

    private AccountDAO dao = new AccountDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String view = request.getParameter("view");
        if (view == null) {
            view = "list";
        }

        switch (view) {
            case "create":
                handleCreateView(request, response);
                break;
            case "update":
                handleUpdateView(request, response);
                break;
            default:
                handleListView(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            handleListView(request, response);
            return;
        }

        switch (action) {
            case "create":
                handleCreateAction(request, response);
                break;
            case "update":
                handleUpdateAction(request, response);
                break;
            case "delete":
                handleDeleteAction(request, response);
                break;
            default:
                handleListView(request, response);
                break;
        }
    }

    private void handleCreateView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int nextId = dao.getNextId();
        request.setAttribute("nextId", nextId);
        request.getRequestDispatcher("/WEB-INF/views/admin/create-user.jsp").forward(request, response);
    }

    private void handleUpdateView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Account acc = dao.getById(id);
            request.setAttribute("account", acc);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID không hợp lệ");
        }
        request.getRequestDispatcher("/WEB-INF/views/admin/update-profile.jsp").forward(request, response);
    }

    private void handleListView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        String role = request.getParameter("role");
        int page = 1;
        int pageSize = 5;

        try {
            page = Integer.parseInt(request.getParameter("page"));
            if (page < 1) {
                page = 1;
            }
        } catch (Exception ignored) {
        }

        int totalRows = dao.getTotalPages(keyword, role);
        int totalPage = (int) Math.ceil((double) totalRows / pageSize);

        if (page > totalPage && totalPage > 0) {
            page = totalPage;
        }

        Pagination pagination = new Pagination();
        pagination.handlePagintation(request, page, totalRows,
                "admin/account?");

        List<Account> accountsPage = dao.filterAccounts(page, keyword, role, pageSize);

        if (accountsPage.isEmpty() && keyword != null && !keyword.trim().isEmpty()) {
            request.setAttribute("message", "Không tìm thấy tài khoản nào phù hợp với từ khóa \"" + keyword + "\"");
        }

        request.setAttribute("accounts", accountsPage);
        request.setAttribute("totalPages", totalPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("keyword", keyword != null ? keyword : "");
        request.setAttribute("role", role != null ? role : "");

        request.getRequestDispatcher("/WEB-INF/views/admin/account-management.jsp").forward(request, response);
    }

    private void handleCreateAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("passWordHarh");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String roleName = request.getParameter("role");

        boolean hasError = false;

        if (userName == null || userName.trim().isEmpty()) {
            request.setAttribute("usernameError", "Username không được để trống");
            hasError = true;
        } else if (dao.existsUsername(userName)) {
            request.setAttribute("usernameError", "Username đã tồn tại");
            hasError = true;
        }

        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("passwordError", "Password không được để trống");
            hasError = true;
        } else if (!password.matches("^(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$")) {
            request.setAttribute("passwordError", "Password ≥ 8 ký tự, có số và ký tự đặc biệt");
            hasError = true;
        }

        if (fullName == null || fullName.trim().isEmpty()) {
            request.setAttribute("fullNameError", "Họ tên không được để trống");
            hasError = true;
        } else if (!fullName.matches("^[A-Za-zÀ-ỹ\\s]+$")) {
            request.setAttribute("fullNameError", "Họ và tên chỉ chứa chữ cái và khoảng trống");
            hasError = true;
        } else {
            fullName = normalizeName(fullName);
        }

        if (email == null || email.trim().isEmpty()
                || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            request.setAttribute("emailError", "Email không hợp lệ");
            hasError = true;
        }

        if (phone != null && !phone.isEmpty() && !phone.matches("^[0-9]{10}$")) {
            request.setAttribute("phoneError", "Số điện thoại phải gồm 10 chữ số");
            hasError = true;
        }

        if (hasError) {
            request.setAttribute("nextId", dao.getNextId());
            request.getRequestDispatcher("/WEB-INF/views/admin/create-user.jsp").forward(request, response);
            return;
        }

        Account account = new Account();
        account.setUserName(userName);
        account.setPassWordHarh(dao.hashMd5(password));
        account.setFullName(fullName);
        account.setEmail(email);
        account.setPhone(phone);
        account.setRoleName(roleName);

        dao.create(account);
        response.sendRedirect(request.getContextPath() + "/admin/account?view=list");
    }

    private void handleUpdateAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String username = request.getParameter("username");
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String roleName = request.getParameter("role");

            boolean hasError = false;

            if (fullName == null || fullName.trim().isEmpty()) {
                request.setAttribute("fullNameError", "Họ tên không được để trống");
                hasError = true;
            } else if (!fullName.matches("^[A-Za-zÀ-ỹ\\s]+$")) {
                request.setAttribute("fullNameError", "Họ và tên chỉ chứa chữ cái");
                hasError = true;
            } else {
                fullName = normalizeName(fullName);
            }

            if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                request.setAttribute("emailError", "Email không hợp lệ");
                hasError = true;
            }

            if (phone == null || !phone.matches("^[0-9]{10}$")) {
                request.setAttribute("phoneError", "Số điện thoại không hợp lệ");
                hasError = true;
            }

            if (hasError) {
                Account temp = new Account(id, username, null, fullName, email, phone, roleName);
                request.setAttribute("account", temp);
                request.getRequestDispatcher("/WEB-INF/views/admin/update-profile.jsp").forward(request, response);
                return;
            }

            Account account = new Account(id, username, null, fullName, email, phone, roleName);
            dao.update(account);
            response.sendRedirect(request.getContextPath() + "/admin/account?view=list");

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/account?view=list");
        }
    }

    private void handleDeleteAction(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.delete(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/admin/account?view=list");
    }

    private String normalizeName(String input) {
        return Arrays.stream(input.trim().toLowerCase().split("\\s+"))
                .map(w -> w.substring(0, 1).toUpperCase() + w.substring(1))
                .collect(Collectors.joining(" "));
    }

    @Override
    public String getServletInfo() {
        return "AdminServlet - Quản lý tài khoản (MVC đơn giản & tối ưu)";
    }
}
