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

/**
 * AdminServlet
 *
 * <p>Manages admin operations for user accounts including listing with
 * pagination and filters, creating, updating, and deleting accounts.</p>
 *
 * <p>URL mapping: <code>/admin/account</code></p>
 */
@WebServlet(name = "AdminServlet", urlPatterns = {"/admin/account"})
public class AdminServlet extends HttpServlet {

    private AccountDAO dao = new AccountDAO(); // DAO for account-related database operations

    /**
     * Handles GET requests to determine which view to render (list/create/update).
     *
     * @param request HTTP request containing optional parameter 'view'
     * @param response HTTP response used for forwarding
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Determine which view to show; default to 'list'
        String view = request.getParameter("view");
        if (view == null) {
            view = "list";
        }

        switch (view) { // route to corresponding view handler
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

    /**
     * Handles POST requests to perform actions (create/update/delete) then redirect or forward.
     *
     * @param request HTTP request containing parameter 'action'
     * @param response HTTP response used for forwarding/redirecting
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Determine action to perform; default to list view if missing
        String action = request.getParameter("action");
        if (action == null) {
            handleListView(request, response);
            return;
        }

        switch (action) { // route to corresponding action handler
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

    /**
     * Prepares data for the create account view.
     *
     * @param request HTTP request used to set nextId
     * @param response HTTP response used to forward to JSP
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    private void handleCreateView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int nextId = dao.getNextId(); // compute next available account id
        request.setAttribute("nextId", nextId); // expose to JSP
        request.getRequestDispatcher("/WEB-INF/views/admin/create-user.jsp").forward(request, response); // forward to create form
    }

    /**
     * Loads account by id and forwards to update profile view.
     *
     * @param request HTTP request containing parameter 'id'
     * @param response HTTP response used to forward
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    private void handleUpdateView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id")); // parse account id
            Account acc = dao.getById(id); // fetch account
            request.setAttribute("account", acc); // attach to request
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID không hợp lệ"); // invalid id format
        }
        request.getRequestDispatcher("/WEB-INF/views/admin/update-profile.jsp").forward(request, response); // forward regardless (error or data)
    }

    /**
     * Lists accounts with optional keyword/role filters and pagination.
     *
     * @param request HTTP request containing optional 'keyword','role','page'
     * @param response HTTP response used to forward to list view
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    private void handleListView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword"); // search term for username/fullname/etc.
        String role = request.getParameter("role"); // role filter
        int page; // current page number
        int pageSize = 12; // items per page

        try {
            page = Integer.parseInt(request.getParameter("page")); // parse page
            if (page < 1) {
                page = 1; // normalize to first page
            }
        } catch (NumberFormatException ex) {
            page = 1; // default when missing/invalid
        }

        int totalRows = dao.getTotalPages(keyword, role); // total items
        int totalPage = (int) Math.ceil((double) totalRows / pageSize); // total pages

        if (page > totalPage && totalPage > 0) { // clamp page to max
            page = totalPage;
        }
        List<Account> accountsPage = dao.filterAccounts(page, keyword, role, pageSize); // fetch page data

        if (accountsPage.isEmpty() && keyword != null && !keyword.trim().isEmpty()) { // show not-found message
            request.setAttribute("message", "Không tìm thấy tài khoản nào phù hợp với từ khóa \"" + keyword + "\"");
        }
        Pagination pagination = new Pagination(); // helper to attach pagination attributes
        if (keyword == null) {
            pagination.handlePagintation(request, page, totalRows,
                    "admin/account?"); // base URL without keyword
        } else {
            pagination.handlePagintation(request, page, totalRows,
                    "admin/account?keyword=" + keyword + "&"); // include keyword param
        }
        request.setAttribute("accounts", accountsPage); // list for JSP
        request.setAttribute("keyword", keyword != null ? keyword : ""); // echo filters
        request.setAttribute("role", role != null ? role : "");

        request.getRequestDispatcher("/WEB-INF/views/admin/account-management.jsp").forward(request, response); // forward to list view
    }

    /**
     * Validates inputs and creates a new account when valid; otherwise returns to form.
     *
     * @param request HTTP request with form fields
     * @param response HTTP response used for forwarding/redirecting
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    private void handleCreateAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id")); // requested id (may be displayed)
        String userName = request.getParameter("userName"); // username input
        String password = request.getParameter("passWordHarh"); // plain password from form
        String fullName = request.getParameter("fullName"); // full name input
        String email = request.getParameter("email"); // email
        String phone = request.getParameter("phone"); // phone number
        String roleName = request.getParameter("role"); // role selection

        boolean hasError = false; // track validation errors

        if (userName == null || userName.trim().isEmpty()) { // username required
            request.setAttribute("usernameError", "Username không được để trống");
            hasError = true;
        } else if (dao.existsUsername(userName)) { // must be unique
            request.setAttribute("usernameError", "Username đã tồn tại");
            hasError = true;
        }

        if (password == null || password.trim().isEmpty()) { // password required
            request.setAttribute("passwordError", "Password không được để trống");
            hasError = true;
        } else if (!password.matches("^(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$")) { // enforce strength
            request.setAttribute("passwordError", "Password ≥ 8 ký tự, có số và ký tự đặc biệt");
            hasError = true;
        }

        if (fullName == null || fullName.trim().isEmpty()) { // full name required
            request.setAttribute("fullNameError", "Họ tên không được để trống");
            hasError = true;
        } else if (!fullName.matches("^[A-Za-zÀ-ỹ\\s]+$")) { // only letters and spaces
            request.setAttribute("fullNameError", "Họ và tên chỉ chứa chữ cái và khoảng trống");
            hasError = true;
        } else {
            fullName = normalizeName(fullName); // title-case normalization
        }

        if (email == null || email.trim().isEmpty()
                || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) { // basic email format
            request.setAttribute("emailError", "Email không hợp lệ");
            hasError = true;
        } else if (dao.existsGmail(email, id)) { // unique email
            request.setAttribute("emailError", "Email đã tồn tại");
            hasError = true;
        }

        if (phone != null && !phone.isEmpty() && !phone.matches("^[0-9]{10}$")) { // optional but must be 10 digits if provided
            request.setAttribute("phoneError", "Số điện thoại phải gồm 10 chữ số");
            hasError = true;
        } else if (dao.existsPhone(phone, id)) { // unique phone
            request.setAttribute("phoneError", "Số điện thoại đã tồn tại");
            hasError = true;
        }

        // 3. Preserve entered form data
        request.setAttribute("userName", userName);
        request.setAttribute("fullName", fullName);
        request.setAttribute("email", email);
        request.setAttribute("phone", phone);
        request.setAttribute("roleName", roleName);
        request.setAttribute("accountId", dao.getNextId()); // display next id for the form

        // 4. If there are validation errors, return to the form
        if (hasError) {
            request.setAttribute("nextId", dao.getNextId()); // repopulate id
            request.getRequestDispatcher("/WEB-INF/views/admin/create-user.jsp").forward(request, response); // forward back to create page
            return;
        }

        Account account = new Account(); // build account model
        account.setUserName(userName);
        account.setPassWordHarh(dao.hashMd5(password)); // hash password before persisting
        account.setFullName(fullName);
        account.setEmail(email);
        account.setPhone(phone);
        account.setRoleName(roleName);

        dao.create(account); // persist new account
        response.sendRedirect(request.getContextPath() + "/admin/account?view=list"); // redirect to list view
    }

    /**
     * Validates and updates an existing account; on validation errors, re-renders the update form.
     *
     * @param request HTTP request with update fields
     * @param response HTTP response used for forwarding/redirecting
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    private void handleUpdateAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id")); // account id
            String username = request.getParameter("userName"); // username
            String fullName = request.getParameter("fullName"); // name
            String email = request.getParameter("email"); // email
            String phone = request.getParameter("phone"); // phone
            String roleName = request.getParameter("role"); // role

            boolean hasError = false; // validation flag

            if (fullName == null || fullName.trim().isEmpty()) { // name required
                request.setAttribute("fullNameError", "Họ tên không được để trống");
                hasError = true;
            } else if (!fullName.matches("^[A-Za-zÀ-ỹ\\s]+$")) { // only letters
                request.setAttribute("fullNameError", "Họ và tên chỉ chứa chữ cái");
                hasError = true;
            } else {
                fullName = normalizeName(fullName); // title-case
            }

            if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) { // basic email format
                request.setAttribute("emailError", "Email không hợp lệ");
                hasError = true;
            } else if (dao.existsGmail(email, id)) { // check uniqueness except current id
                request.setAttribute("emailError", "Email đã tồn tại");
                hasError = true;
            }

            if (phone == null || !phone.matches("^[0-9]{10}$")) { // phone required and 10 digits
                request.setAttribute("phoneError", "Số điện thoại không hợp lệ");
                hasError = true;
            } else if (dao.existsPhone(phone, id)) { // unique phone
                request.setAttribute("phoneError", "Số điện thoại đã tồn tại");
                hasError = true;
            }

            if (hasError) {
                Account temp = new Account(id, username, null, fullName, email, phone, roleName); // temporary holder to repopulate form
                request.setAttribute("account", temp); // set back to request
                System.out.println("--------------------------------------------------------------------------------->" + temp.getUserName()); // debug log
                request.getRequestDispatcher("/WEB-INF/views/admin/update-profile.jsp").forward(request, response); // forward back to update page
                return;
            }

            Account account = new Account(); // construct updated account
            account.setAccountId(id);
            account.setUserName(username);
            account.setFullName(fullName);
            account.setEmail(email);
            account.setPhone(phone);
            account.setRoleName(roleName);
            System.out.println("<<<---------------------------------------------------------------------------------"); // debug log
            dao.update(account); // persist update
            response.sendRedirect(request.getContextPath() + "/admin/account?view=list"); // redirect to list

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/account?view=list"); // invalid id → back to list
        }
    }

    /**
     * Deletes an account by id and redirects to the list view.
     *
     * @param request HTTP request containing parameter 'id'
     * @param response HTTP response used for redirection
     * @throws IOException if an I/O error occurs
     */
    private void handleDeleteAction(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id")); // parse id
            dao.delete(id); // perform delete
        } catch (NumberFormatException e) {
            e.printStackTrace(); // log parse error
        }
        response.sendRedirect(request.getContextPath() + "/admin/account?view=list"); // redirect after delete
    }

    /**
     * Normalizes a full name into title case and trims extra spaces.
     *
     * @param input raw full name
     * @return normalized name in Title Case
     */
    private String normalizeName(String input) {
        return Arrays.stream(input.trim().toLowerCase().split("\\s+")) // split by whitespace
                .map(w -> w.substring(0, 1).toUpperCase() + w.substring(1)) // capitalize each word
                .collect(Collectors.joining(" ")); // join back with single spaces
    }
}
