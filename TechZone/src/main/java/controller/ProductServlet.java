package controller;

import dao.CustomerBehaviorDAO;
import dao.FeedBackDAO;
import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.AccountUsers;
import model.Feedback;
import model.Product;
import until.Pagination;

/**
 * ProductServlet handles product-related requests:
 * - Home page with paginated products
 * - Listing products by category
 * - Product detail page
 * 
 * URL mapping: /products
 */
@WebServlet(name = "ProductServlet", urlPatterns = {"/products"})
public class ProductServlet extends HttpServlet {

    /**
     * Handles GET requests for product listing and details.
     * 
     * @param request HTTP request with optional parameters: action, category, page, id
     * @param response HTTP response for forwarding or redirecting
     * @throws ServletException on servlet errors
     * @throws IOException on I/O errors
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set character encoding to ensure UTF-8 rendering
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // Initialize DAO to query data from database
        ProductDAO dao = new ProductDAO();
        String action = request.getParameter("action");   // e.g., action=detail
        String category = request.getParameter("category"); // e.g., category=phone
        HttpSession session = request.getSession(false); // get existing session if present

        // Check if session is present and get current user info
        if (session != null) {
            AccountUsers account = (AccountUsers) session.getAttribute("account"); // current user info
            CustomerBehaviorDAO customer = new CustomerBehaviorDAO(); // customer behavior DAO

            // Get recommended products by behavior if account is present
            if (account != null) {
                List<Product> listcustomer = customer.getBehavior(account.getId()); // recommended products by behavior
                request.setAttribute("listcustomer", listcustomer); // attach to request
            }
        }

        try {
            // Case 1: No action and no category → SHOW HOME PAGE
            if (action == null && category == null) {

                // Handle pagination
                String pageRaw = request.getParameter("page");
                int currentPage;
                try {
                    currentPage = Integer.parseInt(pageRaw);
                } catch (NumberFormatException ex) {
                    currentPage = 1; // Default to page 1 if missing or invalid
                }

                Pagination pagination = new Pagination(); // pagination helper
                int totalRow = dao.getTotalRow(); // total products in DB
                pagination.handlePagintation(request, currentPage, totalRow, "products?"); // attach pagination attributes

                // Get paginated products (12 per page)
                List<Product> productList = dao.getAllProducts(currentPage);

                // Get newest 1 product (by created time) for each category
                ArrayList<Product> listPhone = (ArrayList<Product>) dao.getTop1(2);
                ArrayList<Product> listLaptop = (ArrayList<Product>) dao.getTop1(1);
                ArrayList<Product> listAccessory = (ArrayList<Product>) dao.getTop1(3);

                // Get best-selling 1 product for each category
                ArrayList<Product> listPhoneBest = (ArrayList<Product>) dao.getTop1ByCategory(2);
                ArrayList<Product> listLaptopBest = (ArrayList<Product>) dao.getTop1ByCategory(1);
                ArrayList<Product> listAccessoryBest = (ArrayList<Product>) dao.getTop1ByCategory(3);

                // Set data to JSP
                request.setAttribute("list", productList);
                request.setAttribute("listPhone", listPhone);
                request.setAttribute("listLap", listLaptop);
                request.setAttribute("listAccessory", listAccessory);
                request.setAttribute("listPhonefe", listPhoneBest);
                request.setAttribute("listLapfe", listLaptopBest);
                request.setAttribute("listAccessoryFe", listAccessoryBest);

                // Forward to home page to display products
                request.getRequestDispatcher("/WEB-INF/views/user/home.jsp")
                        .forward(request, response);
                return;
            }

            // Case 2: Has category → SHOW PRODUCT LIST BY CATEGORY
            if (category != null) {
                ArrayList<Product> allList;
                String viewPath;
                int categoryId;

                switch (category) {
                    case "phone":
                        categoryId = 2;
                        viewPath = "/WEB-INF/views/user/product/product-list/phone-list.jsp";
                        break;
                    case "laptop":
                        categoryId = 1;
                        viewPath = "/WEB-INF/views/user/product/product-list/laptop-list.jsp";
                        break;
                    case "accessory":
                        categoryId = 3;
                        viewPath = "/WEB-INF/views/user/product/product-list/accessory-list.jsp";
                        break;
                    default:
                        response.sendRedirect("products"); // invalid parameter → back to list
                        return;
                }

                // Get all products in the selected category
                allList = (ArrayList<Product>) dao.getProductsByCategory(categoryId);

                // Handle pagination
                String pageRaw = request.getParameter("page");
                int currentPage;
                try {
                    currentPage = Integer.parseInt(pageRaw);
                } catch (NumberFormatException ex) {
                    currentPage = 1; // default to page 1
                }

                int pageSize = 9; // items per page
                int totalProducts = allList.size();
                int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

                int start = (currentPage - 1) * pageSize;
                int end = Math.min(start + pageSize, totalProducts);
                List<Product> paginatedList = allList.subList(start, end); // slice list by page

                // Set data to JSP
                request.setAttribute("list", paginatedList);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("currentPage", currentPage);
                request.setAttribute("category", category);

                request.getRequestDispatcher(viewPath).forward(request, response); // forward to category view
                return;
            }

            // Case 3: action = "detail" → SHOW PRODUCT DETAIL
            if ("detail".equalsIgnoreCase(action)) {
                String idRaw = request.getParameter("id");

                // If no id → back to list
                if (idRaw == null || idRaw.isEmpty()) {
                    response.sendRedirect("products");
                    return;
                }

                int productId;
                try {
                    productId = Integer.parseInt(idRaw);
                } catch (NumberFormatException e) {
                    // If id is not numeric → back to list
                    response.sendRedirect("products");
                    return;
                }

                // Get product by ID
                Product product = dao.getProductById(productId);
                if (product == null) {
                    response.sendRedirect("products"); // not found → back to list
                    return;
                }

                // Update customer behavior if session is present
                if (session != null) {
                    AccountUsers account = (AccountUsers) session.getAttribute("account"); // current user
                    CustomerBehaviorDAO customer = new CustomerBehaviorDAO(); // customer behavior DAO
                    if (account != null) {
                        if (customer.selectBehaviorDAO(productId, account.getId()) == null) { // if no record, create
                            customer.createBehavior(productId, 1, account.getId()); // record first view
                        } else {
                            int count = customer.selectQuantity(productId, account.getId()); // get view count
                            count = count + 1; // increase count
                            customer.updateQuantity(productId, count, account.getId()); // update count
                        }
                    }
                }

                // Get feedback list for this product
                FeedBackDAO feedbackDAO = new FeedBackDAO();
                List<Feedback> feedbacks = feedbackDAO.getFeedbackByProductId(productId);
                request.setAttribute("feedbackList", feedbacks);
                System.out.println(feedbacks.size());
                // Ensure Product has attributes map to avoid JSP errors
                if (product.getAttributesMap() == null) {
                    product.setAttributesMap(new HashMap<>());
                }

                // Handle messages from session (if any)
                String msg = (String) request.getSession().getAttribute("msg");
                String msgError = (String) request.getSession().getAttribute("msgee");
                request.setAttribute("msg", msg);
                request.setAttribute("msgee", msgError);
                request.getSession().removeAttribute("msg");
                request.getSession().removeAttribute("msgee");

                // Attach product to request and forward to detail page
                request.setAttribute("product", product);
                request.getRequestDispatcher("/WEB-INF/views/user/product/product-detail/product-detail.jsp")
                        .forward(request, response);
                return;
            }

            // Default: if none matched → back to list
            response.sendRedirect("products"); // default back to list

        } catch (Exception e) {
            e.printStackTrace();

            // If response not committed, return 500 error
            if (!response.isCommitted()) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Error loading products: " + e.getMessage()); // server error message
            }
        }
    }
}
