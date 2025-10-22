package controller;

import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import model.Product;

@WebServlet(name = "ProductServlet", urlPatterns = {"/products"})
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProductDAO dao = new ProductDAO();
        String action = request.getParameter("action");
        String category = request.getParameter("category");

        try {
            // 🏠 1️⃣ Không có action/category → hiển thị trang chủ
            if (action == null && category == null) {
                // mat hang them moi nhat
                ArrayList<Product> listPhone = (ArrayList<Product>) dao.getTop1(2);
                ArrayList<Product> listLap = (ArrayList<Product>) dao.getTop1(1);

                request.setAttribute("listPhone", listPhone);
                request.setAttribute("listLap", listLap);
                // mat hang ban chay nhat
                ArrayList<Product> listPhonefe = (ArrayList<Product>) dao.getTop1ByCategory(2);
                ArrayList<Product> listLapfe = (ArrayList<Product>) dao.getTop1ByCategory(1);

                request.setAttribute("listPhonefe", listPhonefe);
                request.setAttribute("listLapfe", listLapfe);

                request.getRequestDispatcher("/WEB-INF/views/user/home.jsp").forward(request, response);
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
                        product.setAttributesMap(new HashMap<>());
                    }

                    request.setAttribute("product", product);
                    request.getRequestDispatcher("/WEB-INF/views/user/product/product-detail/product-detail.jsp")
                            .forward(request, response);
                    return;

                } catch (NumberFormatException e) {
                    System.out.println("❌ ID không hợp lệ: " + id);
                    response.sendRedirect("products");
                    return;
                }
            }

            // 🌀 Nếu action không hợp lệ → quay lại home
            response.sendRedirect("products");

        } catch (Exception e) {
            e.printStackTrace();
            if (!response.isCommitted()) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Lỗi khi tải sản phẩm: " + e.getMessage());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // POST không dùng trong servlet này
    }
}
