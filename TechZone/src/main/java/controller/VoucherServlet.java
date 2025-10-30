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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Voucher;
import until.Pagination;

/**
 *
 * @author NgKaitou
 */
@WebServlet(name = "VoucherServlet", urlPatterns = {"/voucher"})
public class VoucherServlet extends HttpServlet {

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
        if (view == null) {
            view = "list";
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
            default:
                throw new AssertionError();
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
        if (action != null) {
            switch (action) {
                case "create":
                    createVoucher(request, response);
                    break;
                case "update":
                    updateVoucher(request, response);
                    break;
                case "remove":
                    removeVoucher(request, response);
                    break;
                default:
                    throw new AssertionError();
            }
        } else {
            response.sendRedirect(getServletContext().getContextPath() + "/voucher");
        }

    }

    private void getAllVoucher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageRaw = request.getParameter("page");
        int currentPage;
        try {
            currentPage = Integer.parseInt(pageRaw);
        } catch (NumberFormatException ex) {
            currentPage = 1;
        }
        VoucherDAO db = new VoucherDAO();
        Pagination p = new Pagination();
        int totalRow = db.getTotalRow();
        List<Voucher> listVoucher = db.getVoucherList(currentPage);
        p.handlePagintation(request, currentPage, totalRow, "voucher");
        request.setAttribute("listVoucher", listVoucher);

        request.getRequestDispatcher("/WEB-INF/views/admin/voucher/list-voucher.jsp").forward(request, response);
    }

    private void getCreateVoucher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/admin/voucher/create-voucher.jsp").forward(request, response);
    }

    private void getUpdateVoucher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String voucherCode = request.getParameter("voucherCode");
        VoucherDAO db = new VoucherDAO();
        Voucher v = db.getByVoucherCode(voucherCode);

        request.setAttribute("voucher", v);
        request.getRequestDispatcher("/WEB-INF/views/admin/voucher/update-voucher.jsp").forward(request, response);
    }

    private void getRemoveVoucher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String success = request.getParameter("success");
        String removeError = request.getParameter("removeError");

        request.setAttribute("success", success);
        request.setAttribute("removeError", removeError);
        getAllVoucher(request, response);
    }

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
        int maxUsage = 0;
        double discountValue = 0;
        double minOrderValue = 0;

        Map<String, String> errors = new HashMap<>();
        VoucherDAO voucherDAO = new VoucherDAO();

        try {
            discountValue = Double.parseDouble(discountValueRaw);
            if (discountValue <= 0) {
                errors.put("discountValueError", "Giá trị giảm giá phải lớn hơn 0!");
            }
        } catch (NumberFormatException ex) {
            errors.put("discountValueError", "Giá trị giảm giá không hợp lệ!");
        }

        try {
            minOrderValue = Double.parseDouble(minOrderValueRaw);
            if (minOrderValue <= 0) {
                errors.put("minOrderValueError", "Giá trị tối thiểu phải lớn hơn 0!");
            }
        } catch (NumberFormatException ex) {
            errors.put("minOrderValueError", "Giá trị tối thiểu không hợp lệ!");
        }

        try {
            maxUsage = Integer.parseInt(maxUsageRaw);
            if (maxUsage <= 0) {
                errors.put("maxUsage", "Số lượng voucher phải lớn hơn 0!");
            }
        } catch (NumberFormatException ex) {
            errors.put("maxUsage", "Số lượng voucher không hợp lệ!");
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(startDate)) {
            errors.put("startDateError", "Ngày bắt đầu không hợp lệ!");
        }

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/views/admin/voucher/create-voucher.jsp").forward(request, response);
            return;
        }

        Voucher v = new Voucher(vocherCodeRaw, BigDecimal.valueOf(discountValue), discountTypeRaw, Timestamp.valueOf(startDate), Timestamp.valueOf(endDate), BigDecimal.valueOf(minOrderValue), maxUsage);
        int result = voucherDAO.createVoucher(v);
        if (result == 1) {
            System.out.println("------------------------>pass");
            response.sendRedirect(getServletContext().getContextPath() + "/voucher");

        } else {
            request.getRequestDispatcher("/WEB-INF/views/admin/voucher/create-voucher.jsp").forward(request, response);
        }

    }

    private void removeVoucher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int voucherId = Integer.parseInt(request.getParameter("voucherId"));
        VoucherDAO voucherDAO = new VoucherDAO();
        int result = voucherDAO.deleteVoucher(voucherId);
        System.out.println(voucherId);
        if (result == 1) {
            String success = "Xóa thành công!";
            response.sendRedirect(getServletContext().getContextPath() + "/voucher?view=remove&success=" + success);
        } else {
            String removeError = "Xóa không thành công!";
            response.sendRedirect(getServletContext().getContextPath() + "/voucher?view=remove&removeError=" + removeError);
        }

    }

    private void updateVoucher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
