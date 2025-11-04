package controller;

import dao.FeedBackDAO;
import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.Account;
import model.AccountUsers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.Feedback;
import model.Product;
import until.Pagination;

/**
 * ProductServlet
 *
 * Servlet này xử lý tất cả các yêu cầu liên quan đến sản phẩm: - Hiển thị trang
 * chủ với danh sách sản phẩm phân trang - Hiển thị sản phẩm theo danh mục
 * (category) - Hiển thị chi tiết sản phẩm (detail)
 *
 * URL mapping: /products
 */
@WebServlet(name = "ProductServlet", urlPatterns = {"/products"})
public class ProductServlet extends HttpServlet {

    /**
     * Phương thức xử lý yêu cầu GET từ client. Tùy theo tham số "action" hoặc
     * "category" mà servlet xử lý khác nhau.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Thiết lập mã hóa ký tự (đảm bảo tiếng Việt không bị lỗi font)
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // Khởi tạo DAO để truy xuất dữ liệu từ database
        ProductDAO dao = new ProductDAO();
        String action = request.getParameter("action");   // Ví dụ: action=detail
        String category = request.getParameter("category"); // Ví dụ: category=phone

        try {
            // ========== 1️⃣ TRƯỜNG HỢP: Không có action và category → hiển thị TRANG CHỦ ==========
            if (action == null && category == null) {

                // Xử lý phân trang
                String pageRaw = request.getParameter("page");
                int currentPage;
                try {
                    currentPage = Integer.parseInt(pageRaw);
                } catch (NumberFormatException ex) {
                    currentPage = 1; // Mặc định trang 1 nếu không truyền hoặc sai định dạng
                }

                Pagination pagination = new Pagination();
                int totalRow = dao.getTotalRow(); // Lấy tổng số sản phẩm trong DB
                pagination.handlePagintation(request, currentPage, totalRow, "products?"); // Gắn các thuộc tính phân trang

                // Lấy danh sách sản phẩm (12 sản phẩm / trang)
                List<Product> productList = dao.getAllProducts(currentPage);

                // Lấy 1 sản phẩm mới nhất (theo ngày tạo) cho từng danh mục
                ArrayList<Product> listPhone = (ArrayList<Product>) dao.getTop1(2);
                ArrayList<Product> listLaptop = (ArrayList<Product>) dao.getTop1(1);
                ArrayList<Product> listAccessory = (ArrayList<Product>) dao.getTop1(3);

                // Lấy 1 sản phẩm bán chạy nhất cho từng danh mục
                ArrayList<Product> listPhoneBest = (ArrayList<Product>) dao.getTop1ByCategory(2);
                ArrayList<Product> listLaptopBest = (ArrayList<Product>) dao.getTop1ByCategory(1);
                ArrayList<Product> listAccessoryBest = (ArrayList<Product>) dao.getTop1ByCategory(3);

                // Gán dữ liệu sang JSP
                request.setAttribute("list", productList);
                request.setAttribute("listPhone", listPhone);
                request.setAttribute("listLap", listLaptop);
                request.setAttribute("listAccessory", listAccessory);
                ArrayList<Product> listPhonefe = (ArrayList<Product>) dao.getTop1ByCategory(2);
                ArrayList<Product> listLapfe = (ArrayList<Product>) dao.getTop1ByCategory(1);
                ArrayList<Product> listAccessoryFe = (ArrayList<Product>) dao.getTop1(3);
                request.setAttribute("listPhonefe", listPhonefe);
                request.setAttribute("listLapfe", listLapfe);
                request.setAttribute("listAccessoryFe", listAccessoryFe);
                request.setAttribute("list", list);
                request.getRequestDispatcher("/WEB-INF/views/user/home.jsp").forward(request, response);
                return;
            }

            // ========== 2️⃣ TRƯỜNG HỢP: Có category → hiển thị DANH SÁCH SẢN PHẨM THEO DANH MỤC ==========
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
                        response.sendRedirect("products");
                        return;
                }

                // 🧩 Lấy toàn bộ sản phẩm theo danh mục
                allList = (ArrayList<Product>) dao.getProductsByCategory(categoryId);

                // 🔢 Xử lý phân trang
                String pageRaw = request.getParameter("page");
                int currentPage;
                try {
                    currentPage = Integer.parseInt(pageRaw);
                } catch (NumberFormatException ex) {
                    currentPage = 1; // mặc định trang 1
                }

                int pageSize = 9; // số sản phẩm mỗi trang
                int totalProducts = allList.size();
                int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

                int start = (currentPage - 1) * pageSize;
                int end = Math.min(start + pageSize, totalProducts);

                List<Product> paginatedList = allList.subList(start, end);

                // Gán dữ liệu sang JSP
                request.setAttribute("list", paginatedList);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("currentPage", currentPage);
                request.setAttribute("category", category);

                request.getRequestDispatcher(viewPath).forward(request, response);
                return;
            }

            // ========== 3️⃣ TRƯỜNG HỢP: action = "detail" → HIỂN THỊ CHI TIẾT SẢN PHẨM ==========
            if ("detail".equalsIgnoreCase(action)) {
                String idRaw = request.getParameter("id");

                // Nếu không có id → quay lại danh sách
                if (idRaw == null || idRaw.isEmpty()) {
                    response.sendRedirect("products");
                    return;
                }

                int productId;
                try {
                    productId = Integer.parseInt(idRaw);
                } catch (NumberFormatException e) {
                    // Nếu id không phải số → quay lại danh sách
                    response.sendRedirect("products");
                    return;
                }

                // Lấy sản phẩm theo ID
                Product product = dao.getProductById(productId);
                if (product == null) {
                    response.sendRedirect("products");
                    return;
                }

                // Lấy danh sách feedback của sản phẩm
                FeedBackDAO feedbackDAO = new FeedBackDAO();
                List<Feedback> feedbacks = feedbackDAO.getFeedbackByProductId(productId);
                request.setAttribute("feedbackList", feedbacks);

                // Đảm bảo Product có map attributes để JSP không lỗi
                if (product.getAttributesMap() == null) {
                    product.setAttributesMap(new HashMap<>());
                }

                // Xử lý thông báo từ session (nếu có)
                String msg = (String) request.getSession().getAttribute("msg");
                String msgError = (String) request.getSession().getAttribute("msgee");
                request.setAttribute("msg", msg);
                request.setAttribute("msgee", msgError);
                request.getSession().removeAttribute("msg");
                request.getSession().removeAttribute("msgee");

                    if (product == null) {
                        response.sendRedirect("products");
                        return;
                    }

                    // Đảm bảo có attributesMap (để tránh NullPointerException)
                    if (product.getAttributesMap() == null) {
                        product.setAttributesMap(new HashMap<>());
                    }

                    request.setAttribute("product", product);
                    request.getRequestDispatcher("/WEB-INF/views/user/product/product-detail/product-detail.jsp")
                            .forward(request, response);
                    return;

                } catch (NumberFormatException e) {
                    response.sendRedirect("products");
                    return;
                }
                // Gán sản phẩm vào request và forward sang trang chi tiết
                request.setAttribute("product", product);
                request.getRequestDispatcher("/WEB-INF/views/user/product/product-detail/product-detail.jsp")
                        .forward(request, response);
                return;
            }

            // ========== 4️⃣ Nếu không khớp bất kỳ trường hợp nào → quay về danh sách ==========
            response.sendRedirect("products");

        } catch (Exception e) {
            e.printStackTrace();

            // Nếu response chưa gửi, trả về mã lỗi 500
            if (!response.isCommitted()) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Lỗi khi tải sản phẩm: " + e.getMessage());
            }
        }
    }

    /**
     * Không sử dụng POST trong servlet này (tất cả logic dùng GET).
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FeedBackDAO dao = new FeedBackDAO();
        String productId_raw = request.getParameter("productId");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");
        String rating_raw = request.getParameter("rating");

        // --- 2. Kiểm tra dữ liệu hợp lệ ---
        if (productId_raw == null || rating_raw == null || productId_raw.isEmpty() || rating_raw.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu thông tin đánh giá.");
            return;
        }

        int productId = Integer.parseInt(productId_raw);
        int rating = Integer.parseInt(rating_raw);

        // --- 3. Lấy thông tin người dùng đang đăng nhập ---
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("login.jsp"); // Nếu chưa đăng nhập
            return;
        }

        AccountUsers acc = (AccountUsers) session.getAttribute("account");
        int accountId = acc.getId();

        // Nếu bạn có logic để lấy orderId thật, thay thế dòng này:
        Integer orderId = dao.getOrderIdByAccountAndProduct(accountId, productId);

        if (orderId == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn chỉ có thể đánh giá khi đã mua sản phẩm này.");
         
            return;
        }


        dao.addFeedback(accountId, productId, orderId, message, rating, subject);



        // --- 5. Quay lại trang chi tiết sản phẩm ---
        response.sendRedirect("products?action=detail&id=" + productId);
    }
}
