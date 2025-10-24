package controller;

import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
            // 🏠 1️⃣ Trang chủ (nếu không có action hoặc category)
            if (action == null && category == null) {
                List<Product> list = dao.getAllProducts();
                ArrayList<Product> listPhone = (ArrayList<Product>) dao.getTop1(2);
                ArrayList<Product> listLap = (ArrayList<Product>) dao.getTop1(1);
                ArrayList<Product> listAccessory = (ArrayList<Product>) dao.getTop1(3);
                request.setAttribute("listPhone", listPhone);
                request.setAttribute("listLap", listLap);
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
                    response.sendRedirect("products");
                    return;
                }

                try {
                    int productId = Integer.parseInt(id);
                    Product product = dao.getProductById(productId);

                    if (product == null) {
                        response.sendRedirect("products");
                        return;
                    }

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
            }

            // 🌀 Mặc định quay lại trang chủ
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

        String action = request.getParameter("action");

        if ("filter".equals(action)) {
            ProductDAO dao = new ProductDAO();

            int cateid = Integer.parseInt(request.getParameter("cateid"));
            String brand = request.getParameter("brand");

            List<Product> listFilter;

            // ✅ Nếu brand null hoặc rỗng thì lấy tất cả sản phẩm trong category
            if (brand == null || brand.trim().isEmpty()) {
                listFilter = dao.getProductsByCategory(cateid);
            } else {
                listFilter = dao.getFilterBrand(cateid, brand);
            }

            request.setAttribute("list", listFilter);

            // ✅ Trả về HTML fragment để AJAX cập nhật phần sản phẩm
            request.getRequestDispatcher("/WEB-INF/views/user/product/product-list/filter-result.jsp")
                    .forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/product");
        }
    }

}
