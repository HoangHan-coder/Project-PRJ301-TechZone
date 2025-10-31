package controller;

import com.google.gson.Gson;
import dao.AccountDAO;
import dao.AdminProductDAO;
import dao.ProductDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.util.ArrayList;
import java.util.stream.Collectors;
import model.Account;
import java.util.Arrays;
import model.Product;

@WebServlet(name = "AdminServlet", urlPatterns = {"/admin"})
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String view = request.getParameter("view");
        AccountDAO dao = new AccountDAO();
        AdminProductDAO daoProductAd = new AdminProductDAO();
        ProductDAO daoProduct = new ProductDAO();

        if (view == null || view.equals("list")) {
            String keyword = request.getParameter("keyword");
            String role = request.getParameter("role");

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

            List<Account> list = dao.filterAccounts(keyword, role);
            if (list == null) {
                list = new ArrayList<>();
            }

            // Gán attribute cho JSP
            request.setAttribute("accounts", list);
            request.setAttribute("currentPage", page);
            request.setAttribute("keyword", keyword != null ? keyword : "");
            request.setAttribute("role", role != null ? role : "");

            // Forward sang JSP
            request.getRequestDispatcher("/WEB-INF/views/admin/account-management.jsp")
                    .forward(request, response);
            return;
        } else if (view.equals("update")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Account acc = dao.getById(id);

            request.setAttribute("account", acc);
            request.getRequestDispatcher("/WEB-INF/views/admin/update-profile.jsp").forward(request, response);
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
            request.getRequestDispatcher("/WEB-INF/views/admin/create-user.jsp").forward(request, response);
        } else if (view.equals("product")) {
            List<Product> listpro = daoProductAd.getAllProducts();
            request.setAttribute("listproductadmin", listpro);
            request.getRequestDispatcher("/WEB-INF/views/admin/product/admin-product.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        AccountDAO dao = new AccountDAO();
        AdminProductDAO daoProductAd = new AdminProductDAO();

        if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String roleName = request.getParameter("role");
            boolean hasError = false;

            // fullName
            if (fullName == null || fullName.trim().isEmpty()) {
                request.setAttribute("fullNameError", "Họ tên không được để trống");
                hasError = true;
            }

            // email
            if (email == null || email.trim().isEmpty()) {
                request.setAttribute("emailError", "Email không được để trống");
                hasError = true;
            } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                request.setAttribute("emailError", "Email không đúng định dạng");
                hasError = true;
            }

            // phone
            if (phone == null || phone.trim().isEmpty()) {
                request.setAttribute("phoneError", "Số điện thoại không được để trống");
                hasError = true;
            } else if (!phone.matches("^[0-9]{10}$")) {
                request.setAttribute("phoneError", "Số điện thoại phải gồm 10 chữ số");
                hasError = true;
            }

            // Nếu có lỗi, giữ data và forward lại
            if (hasError) {
                Account temp = new Account();
                temp.setAccountId(id);
                temp.setFullName(fullName);
                temp.setEmail(email);
                temp.setPhone(phone);
                temp.setRoleName(roleName);
                request.setAttribute("account", temp);
                request.getRequestDispatcher("/WEB-INF/views/admin/update-profile.jsp").forward(request, response);
                return;
            }
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
                response.sendRedirect(request.getContextPath() + "/admin?view=update&id=" + id);
            }

        } else if ("create".equals(action)) {
            // 1. Lấy dữ liệu từ form (chú ý tên 'name' của input trong JSP)
            String userName = request.getParameter("userName");
            String password = request.getParameter("passWordHarh"); // input trong form
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String roleName = request.getParameter("role");

            boolean hasError = false;

            // 2. Validate dữ liệu
            if (userName == null || userName.trim().isEmpty()) {
                request.setAttribute("usernameError", "Username không được để trống");
                hasError = true;
            } else if (dao.existsUsername(userName)) {
                request.setAttribute("usernameError", "Username đã tồn tại, vui lòng chọn tên khác");
                hasError = true;
            } else {
                userName = Arrays.stream(userName.trim().toLowerCase().split("\\s+"))
                        .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                        .collect(Collectors.joining(" "));
            }

            if (password == null || password.trim().isEmpty()) {
                request.setAttribute("passwordError", "Password không được để trống");
                hasError = true;
            } else if (!password.matches("^(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$")) {
                request.setAttribute("passwordError", "Password phải ≥ 8 ký tự, có số và ký tự đặc biệt");
                hasError = true;
            }

            if (fullName == null || fullName.trim().isEmpty()) {
                request.setAttribute("fullNameError", "Họ tên không được để trống");
                hasError = true;
            } else if (!fullName.matches("^[A-Za-zÀ-ỹ\\s]+$")) {
                request.setAttribute("fullNameError", "Họ và tên chỉ chứa chữ cái và khoảng trống");
            } else {
                fullName = Arrays.stream(fullName.trim().toLowerCase().split("\\s+"))
                        .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                        .collect(Collectors.joining(" "));
            }

            if (email != null && !email.isEmpty() && !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                request.setAttribute("emailError", "Email không đúng định dạng");
                hasError = true;
            }

            if (phone != null && !phone.isEmpty() && !phone.matches("^[0-9]{10}$")) {
                request.setAttribute("phoneError", "Số điện thoại phải gồm 10 chữ số");
                hasError = true;
            }

            // 3. Giữ lại dữ liệu đã nhập
            request.setAttribute("userName", userName);
            request.setAttribute("fullName", fullName);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.setAttribute("roleName", roleName);
            request.setAttribute("accountId", dao.getNextId());

            // 4. Nếu có lỗi, quay lại form
            if (hasError) {
                request.getRequestDispatcher("/WEB-INF/views/admin/create-user.jsp").forward(request, response);
                return;
            }

            // 5. Hash mật khẩu trước khi lưu
            String hashedPassword = dao.hashMd5(password);

            // 6. Tạo Account và lưu
            Account account = new Account();
            account.setUserName(userName);
            account.setPassWordHarh(hashedPassword); // lưu hash
            account.setFullName(fullName);
            account.setEmail(email);
            account.setPhone(phone);
            account.setRoleName(roleName);

            try {
                dao.create(account);
                // 7. Redirect về list sau khi tạo thành công
                response.sendRedirect(request.getContextPath() + "/admin?view=list");
            } catch (Exception ex) {
                // log lỗi
                ex.printStackTrace();
                request.setAttribute("usernameError", "Lỗi server khi tạo tài khoản, thử lại sau");
                request.getRequestDispatcher("/WEB-INF/views/admin/create-user.jsp").forward(request, response);
            }
        } else if (action.equals("filter")) {
            String category = request.getParameter("category");
            String brand = request.getParameter("brand");
            String sort = request.getParameter("sort");

            List<Product> products = daoProductAd.filterProducts(category, brand, sort);

            response.setContentType("application/json;charset=UTF-8");
            Gson gson = new Gson();
            String json = gson.toJson(products);
            response.getWriter().write(json);

        }
    }

    @Override
    public String getServletInfo() {
        return "Admin management servlet";
    }
}
