package controller;

import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import model.Product;

@WebServlet(name = "ProductServlet", urlPatterns = {"/products"})
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            ProductDAO dao = new ProductDAO();
            String action = request.getParameter("action");
            String category = request.getParameter("category");
            String view = request.getParameter("view");
            if (view == null || view.equals("home")) {
                request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
            }
            try {
                // 🏠 1️⃣ Không có action/category → hiển thị tất cả sản phẩm
                if (action == null && category == null) {
                    ArrayList<Product> list = (ArrayList<Product>) dao.getAllProducts();
                    request.setAttribute("list", list);
                    request.getRequestDispatcher("home.jsp").forward(request, response);
                    return;
                }

                // 📱 2️⃣ Lọc theo danh mục
                if (category != null) {
                    ArrayList<Product> list;
                    String viewPath = "/WEB-INF/views/user/product/product-list/";

                    switch (category) {
                        case "phone":
                            list = (ArrayList<Product>) dao.getProductsByCategory(2);
                            viewPath += "phone-list.jsp";
                            break;

                        case "laptop":
                            list = (ArrayList<Product>) dao.getProductsByCategory(1);
                            viewPath += "laptop-list.jsp";
                            break;

                        case "accessory":
                            list = (ArrayList<Product>) dao.getProductsByCategory(3);
                            viewPath += "accessory-list.jsp";
                            break;

                        default:
                            response.sendRedirect("products");
                            return;
                    }

                    request.setAttribute("list", list);
                    request.getRequestDispatcher(viewPath).forward(request, response);
                    return;
                }

                // 🔍 3️⃣ Xem chi tiết sản phẩm
                if ("detail".equalsIgnoreCase(action)) {
                    String id = request.getParameter("id");

                    if (id == null || id.isEmpty()) {
                        System.out.println("⚠️ Không có ID trong request!");
                        response.sendRedirect("products");
                        return;
                    }

                    try {
                        int productId = Integer.parseInt(id);
                        Product product = dao.getProductById(productId);

                        if (product == null) {
                            System.out.println("❌ Không tìm thấy sản phẩm với ID = " + id);

                            response.sendRedirect("products");
                            return;
                        }

                        // Kiểm tra map null để tránh lỗi JSP
                        if (product.getAttributesMap() == null) {
                            System.out.println("⚠️ attributesMap null → khởi tạo rỗng.");
                            product.setAttributesMap(new java.util.HashMap<>());
                        }

                        request.setAttribute("product", product);
                        request.getRequestDispatcher("/WEB-INF/views/user/product/product-detail/product-detail.jsp").forward(request, response);
                        return;

                    } catch (NumberFormatException e) {
                        System.out.println("❌ ID không hợp lệ: " + id);
                        response.sendRedirect("products");
                        return;
                    }
                }

                // Nếu action không hợp lệ → quay lại home
                response.sendRedirect("products");

            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải sản phẩm: " + e.getMessage());
            }
        

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Không dùng POST ở đây
    }
}
