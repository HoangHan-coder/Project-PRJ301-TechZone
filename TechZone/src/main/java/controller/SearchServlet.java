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

/**
 * Handles searching and filtering of products with pagination.
 *
 * <p>GET serves the search page and executes search when action=search.
 * POST handles filter and search actions with pagination.</p>
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {
    
      @Override
    /**
     * Handles GET requests to show search results or the filter page.
     *
     * @param request HTTP request containing action and pagination parameters
     * @param response HTTP response for forwarding results
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // ensure request encoding
        response.setCharacterEncoding("UTF-8"); // ensure response encoding
        response.setContentType("text/html;charset=UTF-8"); // set content type

        String action = request.getParameter("action"); // expected: "search"
        if ("search".equals(action)) {
            SearchDAO dao = new SearchDAO(); // DAO for search operations

            int page = 1; // default page index
            int pageSize = 8; // items per page
            if (request.getParameter("page") != null) {
                try {
                    page = Integer.parseInt(request.getParameter("page"));
                } catch (NumberFormatException e) {
                    page = 1; // fallback to page 1
                }
            }

            String txtSearch = request.getParameter("txtSearch"); // search keyword

            List<Product> list = dao.searchProducts(txtSearch, page, pageSize); // current page results
            int totalProducts = dao.countProductsByKeyword(txtSearch); // count all matches
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize); // compute total pages

            request.setAttribute("list", list); // result list
            request.setAttribute("txtSearch", txtSearch); // echo keyword
            request.setAttribute("totalPages", totalPages); // pagination info
            request.setAttribute("currentPage", page); // current page index

            request.getRequestDispatcher("/WEB-INF/views/user/product/product-list/product-filter.jsp")
                    .forward(request, response);
            return;
        }

        // Show product-filter.jsp page
        request.getRequestDispatcher("/WEB-INF/views/user/product/product-list/product-filter.jsp")
                .forward(request, response);
    }
    
    @Override
    /**
     * Handles POST requests for product filtering and searching with pagination.
     *
     * @param request HTTP request containing action, page, and filter params
     * @param response HTTP response for forwarding or redirecting
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // ensure request encoding
        response.setCharacterEncoding("UTF-8"); // ensure response encoding
        response.setContentType("text/html;charset=UTF-8"); // set content type

        String action = request.getParameter("action"); // "filter" or "search"
        SearchDAO dao = new SearchDAO(); // search DAO
        ProductDAO daopd = new ProductDAO(); // product DAO

        int page = 1; // default page
        int pageSize = 8; // items per page
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1; // fallback page
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

            int cateid = Integer.parseInt(cateidStr); // parse category id
            List<Product> fullList = (brand == null || brand.trim().isEmpty())
                    ? daopd.getProductsByCategory(cateid) // fetch by category only
                    : dao.getFilterBrand(cateid, brand); // fetch by category and brand

            int totalProducts = fullList.size(); // count total results
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize); // compute pages
            int start = (page - 1) * pageSize; // start index
            int end = Math.min(start + pageSize, totalProducts); // end index
            List<Product> list = fullList.subList(start, end); // page slice

            request.setAttribute("list", list); // results for current page
            request.setAttribute("totalPages", totalPages); // pagination info
            request.setAttribute("currentPage", page); // current page index

            request.getRequestDispatcher("/WEB-INF/views/user/product/product-list/filter-result.jsp")
                    .forward(request, response);
            return;
        } // ==================== SEARCH ====================
        else if ("search".equals(action)) {
            String txtSearch = request.getParameter("txtSearch");

            // Fetch paginated product list from DB
            List<Product> list = dao.searchProducts(txtSearch, page, pageSize); // search paginated results

            // Count total results to compute total pages
            int totalProducts = dao.countProductsByKeyword(txtSearch); // total matches
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize); // pages

            request.setAttribute("list", list);
            request.setAttribute("txtSearch", txtSearch);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", page);

            request.getRequestDispatcher("/WEB-INF/views/user/product/product-list/product-filter.jsp")
                    .forward(request, response);
            return;
        }

        // ==================== DEFAULT ====================
        response.sendRedirect(request.getContextPath() + "/error"); // fallback route
    }
}
