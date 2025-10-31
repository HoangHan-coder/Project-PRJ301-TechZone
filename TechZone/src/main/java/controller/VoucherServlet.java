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
                case "search":
                    searchVoucher(request, response);
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
        String success = request.getParameter("success");
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
        request.setAttribute("success", success);
        request.getRequestDispatcher("/WEB-INF/views/admin/voucher/list-voucher.jsp").forward(request, response);
    }

    private void getCreateVoucher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errString = (String) request.getSession().getAttribute("errors");
        Map<String, String> errMap = splitError(errString);
        request.setAttribute("errorMap", errMap);
        request.getSession().removeAttribute("errors");
        request.getRequestDispatcher("/WEB-INF/views/admin/voucher/create-voucher.jsp").forward(request, response);
    }

    private void getUpdateVoucher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String voucherCode = request.getParameter("voucherCode");
        String errors = (String) request.getSession().getAttribute("errors");
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
            request.setAttribute("errorMap", errorMap);
            System.out.println("Đã set errorMap");
        }
        VoucherDAO db = new VoucherDAO();
        Voucher v = db.getByVoucherCode(voucherCode);
        request.setAttribute("voucher", v);
        System.out.println(v.getCode());
        request.getSession().removeAttribute("errors");
        request.getRequestDispatcher("/WEB-INF/views/admin/voucher/update-voucher.jsp").forward(request, response);
    }

    private void getRemoveVoucher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String success = request.getParameter("success");
        String removeError = request.getParameter("removeError");

        request.setAttribute("success", success);
        request.setAttribute("removeError", removeError);
        getAllVoucher(request, response);
    }

    private void searchVoucher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String success = request.getParameter("success");
        String keyword = request.getParameter("keyword").trim();
        String pageRaw = request.getParameter("page");

        int currentPage;
        try {
            currentPage = Integer.parseInt(pageRaw);
        } catch (NumberFormatException ex) {
            currentPage = 1;
        }
         
        Pagination p = new Pagination();
        VoucherDAO db = new VoucherDAO();
        List<Voucher> listVoucher = db.getByVouCode(keyword, currentPage);
        int totalPage = db.getTotalRow(keyword);
        request.setAttribute("listVoucher", listVoucher);
        request.setAttribute("success", success);
        p.handlePagintation(request, currentPage, totalPage, "voucher");
        request.getRequestDispatcher("/WEB-INF/views/admin/voucher/list-voucher.jsp").forward(request, response);
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

        VoucherDAO voucherDAO = new VoucherDAO();
        String errors = checkInput(voucherDAO, vocherCodeRaw, discountValueRaw, discountTypeRaw, minOrderValueRaw, startDate, endDate, maxUsageRaw).trim();
        System.out.println(errors + "<-------------");
        if (!errors.isEmpty() && errors.startsWith(",")) {
            request.getSession().setAttribute("errors", errors.substring(1));
            response.sendRedirect(getServletContext().getContextPath() + "/voucher?view=create");
            return;
        }

        int maxUsage = Integer.parseInt(maxUsageRaw);
        double discountValue = Double.parseDouble(discountValueRaw);
        double minOrderValue = Double.parseDouble(minOrderValueRaw);
        Voucher v = new Voucher(vocherCodeRaw, BigDecimal.valueOf(discountValue), discountTypeRaw, Timestamp.valueOf(startDate), Timestamp.valueOf(endDate), BigDecimal.valueOf(minOrderValue), maxUsage);
        int result = voucherDAO.createVoucher(v);
        if (result == 1) {
            String success = "Create successfully!";
            response.sendRedirect(getServletContext().getContextPath() + "/voucher?success=" + success);

        } else {
            request.getSession().setAttribute("createErr", "Create failded!");
            response.sendRedirect(getServletContext().getContextPath() + "/voucher?view=create");
        }

    }

    private void removeVoucher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int voucherId = Integer.parseInt(request.getParameter("voucherId"));
        VoucherDAO voucherDAO = new VoucherDAO();
        int result = voucherDAO.deleteVoucher(voucherId);

        if (result == 1) {
            String success = "Delete successfully!";
            System.out.println(success);
            response.sendRedirect(getServletContext().getContextPath() + "/voucher?view=remove&success=" + success);
        } else {
            String removeError = "Delete failded!";
            response.sendRedirect(getServletContext().getContextPath() + "/voucher?view=remove&removeError=" + removeError);
        }

    }

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

        VoucherDAO voucherDAO = new VoucherDAO();
        BigDecimal discountValue = BigDecimal.ONE;
        BigDecimal minOrderValue = BigDecimal.ONE;
        String errors = "";
        int maxUsage = 0;
        if (voucherDAO.voucherCodeExist(voucherCode) && !voucherCode.equals(voucherCode)) {
            errors += ",vouCodExist-Voucher code đã tồn tại!";
        }

        try {
            discountValue = BigDecimal.valueOf(Double.parseDouble(discountValueRaw));
            if (discountValue.doubleValue() <= 0) {
                errors += ",disValErrPos-Giá trị giảm giá phải lớn hơn 0!";
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

        LocalDateTime now = LocalDateTime.now();
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

        if (!errors.isEmpty() && errors.startsWith(",")) {
            errors = errors.substring(1);
            request.getSession().setAttribute("errors", errors);
            System.out.println(voucherCode);
            response.sendRedirect(getServletContext().getContextPath() + "/voucher?view=update&voucherCode=" + voucherCode);
            return;
        }

        Voucher voucher = new Voucher(voucherCode, discountValue, discountType, Timestamp.valueOf(startDate), Timestamp.valueOf(endDate), minOrderValue, maxUsage);
        int result = voucherDAO.updateVoucher(voucher, voucherId);

        if (result == 1) {

            String success = "Update successfully!";
            System.out.println(success);
            response.sendRedirect(getServletContext().getContextPath() + "/voucher?success=" + success);
        } else {
            System.out.println("---------------------------------------------------------------------------------------------------->failded");
            String updateError = "Update failded!";
            response.sendRedirect(getServletContext().getContextPath() + "/voucher?updateError=" + updateError);
        }
    }

    private String checkInput(VoucherDAO voucherDAO, String voucherCode, String discountValueRaw, String discountType, String minOrderValueRaw, LocalDateTime startDate, LocalDateTime endDate, String maxUsageRaw) {
        String errors = "";
        BigDecimal discountValue, minOrderValue;
        int maxUsage;
        if (voucherDAO.voucherCodeExist(voucherCode)) {
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

        LocalDateTime now = LocalDateTime.now();
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

    private Map<String, String> splitError(String errors) {
        Map<String, String> errorMap = new HashMap<>();
        if (errors != null) {

            String[] error = errors.split(",");
            String[] errorDetail;
            for (String err : error) {
                errorDetail = err.split("-");
                errorMap.put(errorDetail[0], errorDetail[1]);
                System.out.println(errorDetail[0] + ": " + errorDetail[1]);
            }
            return errorMap;
        }
        return null;
    }

}
