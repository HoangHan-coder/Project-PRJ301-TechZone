package controller;

import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
            // 🏠 1️⃣ Nếu không có action hoặc category → hiển thị tất cả sản phẩm
            if (action == null && category == null) {
                ArrayList<Product> list = (ArrayList<Product>) dao.getAllProducts();
                request.setAttribute("list", list);
                request.getRequestDispatcher("/WEB-INF/views/user/home.jsp").forward(request, response);
                return;
            }

            // 📱 2️⃣ Lọc sản phẩm theo danh mục
       if (category != null) {
    ArrayList<Product> list = new ArrayList<>();
    String viewPath = "/WEB-INF/views/user/product-list/";

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
}


            // 🔍 3️⃣ Xem chi tiết sản phẩm
            if ("detail".equals(action)) {
                String id = request.getParameter("id");
                if (id != null) {
                    try {
                        int productId = Integer.parseInt(id);
                        Product p = dao.getProductById(productId);
                        if (p != null) {
                            request.setAttribute("product", p);
                            request.getRequestDispatcher("/WEB-INF/views/user/product-detail.jsp").forward(request, response);
                            return;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Invalid product ID: " + id);
                    }
                }
                response.sendRedirect("products");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải sản phẩm");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // ❌ User không cần POST
    }
}
