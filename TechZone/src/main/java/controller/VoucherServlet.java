/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.VoucherDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Voucher;
import until.Pagination;

/**
 * Admin voucher management servlet for listing, creating, updating, removing,
 * and searching vouchers with pagination.
 *
 * <p>GET routes by 'view' to list/create/update/remove/search pages. POST
 * handles create/update/remove/search actions.</p>
 *
 * @author NgKaitou
 */
@WebServlet(name = "VoucherServlet", urlPatterns = {"/admin/voucher", "/admin/voucher/search"})
public class VoucherServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Routes GET requests to the appropriate admin voucher view based on the
     * 'view' parameter.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String view = request.getParameter("view"); // which admin view to show
        if (view == null) {
            view = "list"; // default to list view
        }

        switch (view) {
            case "list":
                getAllVoucher(request, response);
                break;
            case "create":
                getCreateVoucher(request, response);
                break;
            case "update":
                getUpdateVoucher(request, response);
                break;
            case "remove":
                getRemoveVoucher(request, response);
                break;
            case "search":
                searchVoucher(request, response);
                break;
            default:
                getAllVoucher(request, response);
        }

    }

    /**
     * Handles POST actions for creating, updating, removing, and searching vouchers.
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
        if (action != null) {
            switch (action) {
                case "create":
                    createVoucher(request, response); // create voucher
                    break;
                case "update":
                    updateVoucher(request, response); // update voucher
                    break;
                case "remove":
                    removeVoucher(request, response); // delete voucher
                    break;
                case "search":
                    searchVoucher(request, response); // search vouchers
                    break;
                default:
                    throw new AssertionError();
            }
        } else {
            response.sendRedirect(getServletContext().getContextPath() + "/admin/voucher"); // fallback
        }

    }

    /**
     * Loads paginated list of vouchers for admin listing page.
     */
    private void getAllVoucher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageRaw = request.getParameter("page"); // current page
        String success = request.getParameter("success"); // success message
        int currentPage;
        try {
            currentPage = Integer.parseInt(pageRaw);
        } catch (NumberFormatException ex) {
            currentPage = 1; // default page
        }
        VoucherDAO db = new VoucherDAO(); // DAO for vouchers
        Pagination p = new Pagination(); // pagination helper
        int totalRow = db.getTotalRow(); // total vouchers
         int totalPage;

        if (totalRow % 12 == 0) {
            totalPage = totalRow / 12;
        } else {
            totalPage = (totalRow / 12) + 1;
        }

        if (currentPage > totalPage) {
            currentPage = totalPage; // clamp to max
        }
        if (currentPage < 1) {
            currentPage = 1; // clamp to min
        }
        List<Voucher> listVoucher = db.getVoucherList(currentPage); // fetch current page
        p.handlePagintation(request, currentPage, totalRow, "/admin/voucher?"); // attach pagination attrs
        request.setAttribute("listVoucher", listVoucher); // attach list
        request.setAttribute("success", success); // attach message
        request.getRequestDispatcher("/WEB-INF/views/admin/voucher/list-voucher.jsp").forward(request, response); // forward
    }

    /**
     * Forwards to the create voucher page with any validation errors.
     */
    private void getCreateVoucher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errString = (String) request.getSession().getAttribute("errors"); // aggregated errors
        Map<String, String> errMap = splitError(errString); // split to map
        request.setAttribute("errorMap", errMap); // expose to JSP
        request.getSession().removeAttribute("errors"); // clear once
        request.getRequestDispatcher("/WEB-INF/views/admin/voucher/create-voucher.jsp").forward(request, response); // forward
    }

    /**
     * Forwards to update voucher page pre-filled with selected voucher and errors if any.
     */
    private void getUpdateVoucher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String voucherCode = request.getParameter("voucherCode");
        String errors = (String) request.getSession().getAttribute("errors"); // aggregated errors
        System.out.println(errors);
        if (errors != null) {
            Map<String, String> errorMap = new HashMap<>();
            String[] error = errors.split(",");
            String[] errorDetail;
            for (String err : error) {
                errorDetail = err.split("-");
                errorMap.put(errorDetail[0], errorDetail[1]);
                System.out.println(errorDetail[0] + ": " + errorDetail[1]);
            }
            request.setAttribute("errorMap", errorMap); // expose to JSP
            System.out.println("Đã set errorMap");
        }
        VoucherDAO db = new VoucherDAO(); // DAO for vouchers
        Voucher v = db.getByVoucherCode(voucherCode); // fetch voucher
        request.setAttribute("voucher", v); // attach voucher
        System.out.println(v.getCode());
        request.getSession().removeAttribute("errors"); // clear once
        request.getRequestDispatcher("/WEB-INF/views/admin/voucher/update-voucher.jsp").forward(request, response); // forward
    }

    /**
     * Forwards to remove voucher confirmation/list view with feedback messages.
     */
    private void getRemoveVoucher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String success = request.getParameter("success");
        String removeError = request.getParameter("removeError");

        request.setAttribute("success", success); // success message
        request.setAttribute("removeError", removeError); // error message
        getAllVoucher(request, response); // reload list
    }

    /**
     * Searches vouchers by keyword with pagination.
     */
    private void searchVoucher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String success = request.getParameter("success");
        String keyword = request.getParameter("keyword").trim(); // search term
        String pageRaw = request.getParameter("page");

        int currentPage;
        try {
            currentPage = Integer.parseInt(pageRaw);
        } catch (NumberFormatException ex) {
            currentPage = 1; // default page
        }

        Pagination p = new Pagination(); // pagination helper
        VoucherDAO db = new VoucherDAO(); // DAO
        List<Voucher> listVoucher = db.getByVouCode(keyword, currentPage); // search results
        int totalPage = db.getTotalRow(keyword); // total rows for search
        request.setAttribute("listVoucher", listVoucher); // attach results
        request.setAttribute("success", success); // attach message
        p.handlePagintation(request, currentPage, totalPage,  "admin/voucher?view=search&keyword=" + keyword + "&"); // pagination attrs
        request.getRequestDispatcher("/WEB-INF/views/admin/voucher/list-voucher.jsp").forward(request, response); // forward
    }

    /**
     * Validates inputs and creates a new voucher, redirecting with messages.
     */
    private void createVoucher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String vocherCodeRaw = request.getParameter("vocherCode");
        String discountValueRaw = request.getParameter("discountValue");
        String discountTypeRaw = request.getParameter("discountType");
        String minOrderValueRaw = request.getParameter("minOrderValue");

        String startDateRaw = request.getParameter("startDate");
        LocalDateTime startDate = LocalDateTime.parse(startDateRaw);
        String endDateRaw = request.getParameter("endDate");
        LocalDateTime endDate = LocalDateTime.parse(endDateRaw);
        String maxUsageRaw = request.getParameter("maxUsage");

        VoucherDAO voucherDAO = new VoucherDAO(); // DAO
        String errors = checkInput(voucherDAO, vocherCodeRaw, discountValueRaw, discountTypeRaw, minOrderValueRaw, startDate, endDate, maxUsageRaw).trim(); // validate
        if (!errors.isEmpty() && errors.startsWith(",")) {
            request.getSession().setAttribute("errors", errors.substring(1)); // store errors without leading comma
            response.sendRedirect(getServletContext().getContextPath() + "/admin/voucher?view=create"); // back to create page
            return;
        }

        int maxUsage = Integer.parseInt(maxUsageRaw); // maximum uses
        double discountValue = Double.parseDouble(discountValueRaw); // numeric discount
        double minOrderValue = Double.parseDouble(minOrderValueRaw); // minimum order amount
        Voucher v = new Voucher(vocherCodeRaw, BigDecimal.valueOf(discountValue), discountTypeRaw, Timestamp.valueOf(startDate), Timestamp.valueOf(endDate), BigDecimal.valueOf(minOrderValue), maxUsage); // new voucher
        int result = voucherDAO.createVoucher(v); // persist
        if (result == 1) {
            String success = "Create successfully!";
            response.sendRedirect(getServletContext().getContextPath() + "/admin/voucher?success=" + success); // redirect to list with message

        } else {
            request.getSession().setAttribute("createErr", "Create failded!");
            response.sendRedirect(getServletContext().getContextPath() + "/admin/voucher?view=create"); // back to create page
        }

    }

    /**
     * Deletes a voucher by id and redirects back with a success/error message.
     */
    private void removeVoucher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int voucherId = Integer.parseInt(request.getParameter("voucherId"));
        VoucherDAO voucherDAO = new VoucherDAO(); // DAO
        int result = voucherDAO.deleteVoucher(voucherId); // perform delete

        if (result == 1) {
            String success = "Delete successfully!";
            System.out.println(success);
            response.sendRedirect(getServletContext().getContextPath() + "/admin/voucher?view=remove&success=" + success); // back with success
        } else {
            String removeError = "Delete failded!";
            response.sendRedirect(getServletContext().getContextPath() + "/admin/voucher?view=remove&removeError=" + removeError); // back with error
        }

    }

    /**
     * Validates and updates an existing voucher by id, redirecting with status.
     */
    private void updateVoucher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int voucherId = Integer.parseInt(request.getParameter("voucherId"));
        String voucherCode = request.getParameter("voucherCode");
        String discountValueRaw = request.getParameter("discountValue");
        String discountType = request.getParameter("discountType");
        String minOrderValueRaw = request.getParameter("minOrderValue");
        String startDateRaw = request.getParameter("startDate");
        LocalDateTime startDate = LocalDateTime.parse(startDateRaw);
        String endDateRaw = request.getParameter("endDate");
        LocalDateTime endDate = LocalDateTime.parse(endDateRaw);
        String maxUsageRaw = request.getParameter("maxUsage");

        VoucherDAO voucherDAO = new VoucherDAO(); // DAO
        BigDecimal discountValue = BigDecimal.ONE;
        BigDecimal minOrderValue = BigDecimal.ONE;
        String errors = "";
        int maxUsage = 0;
        if (voucherDAO.voucherCodeExist(voucherCode) && !voucherCode.equals(voucherCode)) { // check duplicate
            errors += ",vouCodExist-Voucher code đã tồn tại!";
        }

        try {
            discountValue = BigDecimal.valueOf(Double.parseDouble(discountValueRaw));
            if (discountValue.doubleValue() <= 0) {
                errors += ",disValErrPos-Giá trị giảm giá phải lớn hơn 0!";
            } else if (discountType.equalsIgnoreCase("PERCENT") && discountValue.doubleValue() > 100) {
                errors += ",disValErrPos-Giá trị giảm giá dạng phần trăm không được vượt quá 100%!";
            }
        } catch (NumberFormatException ex) {
            errors += ",disValErrNumFmt-Giá trị giảm giá phải đúng định dạng số!";
        }

        try {
            minOrderValue = BigDecimal.valueOf(Double.parseDouble(minOrderValueRaw));
            if (minOrderValue.doubleValue() <= 0) {
                errors += ",minOrdErrPos-Giá trị tối thiểu phải lớn hơn 0!";
            }
        } catch (NumberFormatException ex) {
            errors += ",minOrdErrNumFmt-Giá trị tối thiểu phải đúng định dạng số!";
        }

        LocalDateTime now = LocalDateTime.now(); // current time
        if (now.isAfter(endDate)) {
            errors += ",endDateErr-Ngày kết thúc voucher không hợp lệ!";
        } else if (endDate.isBefore(startDate)) {
            errors += ",endDateErr-Ngày kết thúc voucher không hợp phải sau ngày bắt đầu voucher";
        }

        try {
            maxUsage = Integer.parseInt(maxUsageRaw);
            if (maxUsage <= 0) {
                errors += ",maxUsageErrPos-Số lượng voucher phải lớn hơn 0!";
            }
        } catch (NumberFormatException ex) {
            errors += ",maxUsageErrNumFmt-Số lượng voucher phải đúng định dạng số!";
        }

        if (!errors.isEmpty() && errors.startsWith(",")) { // if any validation error
            errors = errors.substring(1);
            request.getSession().setAttribute("errors", errors);
            System.out.println(voucherCode);
            response.sendRedirect(getServletContext().getContextPath() + "/admin/voucher?view=update&voucherCode=" + voucherCode); // back to update
            return;
        }

        Voucher voucher = new Voucher(voucherCode, discountValue, discountType, Timestamp.valueOf(startDate), Timestamp.valueOf(endDate), minOrderValue, maxUsage); // updated voucher
        int result = voucherDAO.updateVoucher(voucher, voucherId); // persist update

        if (result == 1) {

            String success = "Update successfully!";
            System.out.println(success);
            response.sendRedirect(getServletContext().getContextPath() + "/admin/voucher?success=" + success); // back to list with success
        } else {
            System.out.println("---------------------------------------------------------------------------------------------------->failded");
            String updateError = "Update failded!";
            response.sendRedirect(getServletContext().getContextPath() + "/admin/voucher?updateError=" + updateError); // back with error
        }
    }

    /**
     * Validates input fields for creating a voucher, returning a comma-separated error string.
     */
    private String checkInput(VoucherDAO voucherDAO, String voucherCode, String discountValueRaw, String discountType, String minOrderValueRaw, LocalDateTime startDate, LocalDateTime endDate, String maxUsageRaw) {
        String errors = "";
        BigDecimal discountValue, minOrderValue;
        int maxUsage;
        if (voucherDAO.voucherCodeExist(voucherCode)) { // duplicate code check
            errors += ",vouCodExist-Voucher code đã tồn tại!";
        }

        try {
            discountValue = BigDecimal.valueOf(Double.parseDouble(discountValueRaw));
            if (discountValue.doubleValue() <= 0) {
                errors += ",disValErrPos-Giá trị giảm giá phải lớn hơn 0!";
            } else if (discountType.equalsIgnoreCase("PERCENT") && discountValue.doubleValue() > 100) {
                errors += ",disValErrPos-Giá trị giảm giá dạng phần trăm không được vượt quá 100%!";
            }
        } catch (NumberFormatException ex) {
            errors += ",disValErrNumFmt-Giá trị giảm giá phải đúng định dạng số!";
        }

        try {
            minOrderValue = BigDecimal.valueOf(Double.parseDouble(minOrderValueRaw));
            if (minOrderValue.doubleValue() <= 0) {
                errors += ",minOrdErrPos-Giá trị tối thiểu phải lớn hơn 0!";
            }
        } catch (NumberFormatException ex) {
            errors += ",minOrdErrNumFmt-Giá trị tối thiểu phải đúng định dạng số!";
        }

        LocalDateTime now = LocalDateTime.now(); // current time
        if (now.isAfter(startDate)) {
            errors += ",startDateErr-Ngày bắt đầu không hợp lệ!";
        }

        if (now.isAfter(endDate)) {
            errors += ",endDateErr-Ngày kết thúc voucher không hợp lệ!";
        } else if (endDate.isBefore(startDate)) {
            errors += ",endDateErr-Ngày kết thúc voucher không hợp phải sau ngày bắt đầu voucher";
        }

        try {
            maxUsage = Integer.parseInt(maxUsageRaw);
            if (maxUsage <= 0) {
                errors += ",maxUsageErrPos-Số lượng voucher phải lớn hơn 0!";
            }
        } catch (NumberFormatException ex) {
            errors += ",maxUsageErrNumFmt-Số lượng voucher phải đúng định dạng số!";
        }
        return errors;
    }

    /**
     * Converts an aggregated error string into a map for JSP display.
     */
    private Map<String, String> splitError(String errors) {
        Map<String, String> errorMap = new HashMap<>();
        if (errors != null) {

            String[] error = errors.split(",");
            String[] errorDetail;
            for (String err : error) {
                errorDetail = err.split("-");
                errorMap.put(errorDetail[0], errorDetail[1]); // key-value of error code and message
                System.out.println(errorDetail[0] + ": " + errorDetail[1]);
            }
            return errorMap;
        }
        return null;
    }

}
