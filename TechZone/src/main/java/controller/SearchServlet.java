package controller;

import dao.ProductDAO;
import dao.SearchDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Product;

@WebServlet(name = "SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {
    
      @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        if ("search".equals(action)) {
            SearchDAO dao = new SearchDAO();

            int page = 1;
            int pageSize = 8;
            if (request.getParameter("page") != null) {
                try {
                    page = Integer.parseInt(request.getParameter("page"));
                } catch (NumberFormatException e) {
                    page = 1;
                }
            }

            String txtSearch = request.getParameter("txtSearch");

            List<Product> list = dao.searchProducts(txtSearch, page, pageSize);
            int totalProducts = dao.countProductsByKeyword(txtSearch);
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

            request.setAttribute("list", list);
            request.setAttribute("txtSearch", txtSearch);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", page);

            request.getRequestDispatcher("/WEB-INF/views/user/product/product-list/product-filter.jsp")
                    .forward(request, response);
            return;
        }

        // Hiển thị trang product-filter.jsp
        request.getRequestDispatcher("/WEB-INF/views/user/product/product-list/product-filter.jsp")
                .forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        SearchDAO dao = new SearchDAO();
        ProductDAO daopd = new ProductDAO();

        int page = 1;
        int pageSize = 8;
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        // ==================== FILTER ====================
        if ("filter".equals(action)) {
            String cateidStr = request.getParameter("cateid");
            String brand = request.getParameter("brand");

            if (cateidStr == null || cateidStr.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Category ID missing");
                return;
            }

            int cateid = Integer.parseInt(cateidStr);
            List<Product> fullList = (brand == null || brand.trim().isEmpty())
                    ? daopd.getProductsByCategory(cateid)
                    : dao.getFilterBrand(cateid, brand);

            int totalProducts = fullList.size();
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize);
            int start = (page - 1) * pageSize;
            int end = Math.min(start + pageSize, totalProducts);
            List<Product> list = fullList.subList(start, end);

            request.setAttribute("list", list);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", page);

            request.getRequestDispatcher("/WEB-INF/views/user/product/product-list/filter-result.jsp")
                    .forward(request, response);
            return;
        } // ==================== SEARCH ====================
        else if ("search".equals(action)) {
            String txtSearch = request.getParameter("txtSearch");

            // Lấy danh sách sản phẩm có phân trang từ DB
            List<Product> list = dao.searchProducts(txtSearch, page, pageSize);

            // Đếm tổng số kết quả để tính số trang
            int totalProducts = dao.countProductsByKeyword(txtSearch);
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

            request.setAttribute("list", list);
            request.setAttribute("txtSearch", txtSearch);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", page);

            request.getRequestDispatcher("/WEB-INF/views/user/product/product-list/product-filter.jsp")
                    .forward(request, response);
            return;
        }

        // ==================== DEFAULT ====================
        response.sendRedirect(request.getContextPath() + "/error");
    }
}
