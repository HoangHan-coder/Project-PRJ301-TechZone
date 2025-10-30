/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package until;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 *
 * @author NgKaitou
 */
public class Pagination {

    /**
     * Xử lý phân trang cho servlet.
     *
     * @param request HttpServletRequest để setAttribute sang JSP
     * @param currentPage Trang hiện tại người dùng đang chọn (truyền từ param)
     * @param totalRow Tổng số dòng dữ liệu lấy từ DB
     * @param servletPath Đường dẫn servlet dùng để gọi lại khi chuyển trang
     * @throws ServletException ném khi có lỗi Servlet
     * @throws IOException ném khi có lỗi IO
     */
    public void handlePagintation(HttpServletRequest request, int currentPage, int totalRow, String servletPath) throws ServletException, IOException {
        int endPage;
        int startPage;
        int currentGroup;
        int pageGroup = 5;
        int rowInPage = 12;
        int totalPage;

        if (totalRow % rowInPage == 0) {
            totalPage = totalRow / rowInPage;
        } else {
            totalPage = (totalRow / rowInPage) + 1;
        }

        if (currentPage > totalPage) {
            currentPage = totalPage;
        }
        if (currentPage < 1) {
            currentPage = 1;
        }

        currentGroup = (currentPage - 1) / pageGroup;
        startPage = currentGroup * pageGroup + 1;
        endPage = startPage + pageGroup - 1;

        if (endPage > totalPage) {
            endPage = totalPage;
        }

        request.setAttribute("servletPath", servletPath);
        request.setAttribute("startPage", startPage);
        request.setAttribute("endPage", endPage);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPage", totalPage);
    }
}
