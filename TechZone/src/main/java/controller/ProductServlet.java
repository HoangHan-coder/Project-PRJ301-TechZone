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
import model.FeedBack;
import model.Product;

@WebServlet(name = "ProductServlet", urlPatterns = {"/products"})
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        response.setContentType("text/html;charset=UTF-8");
        ProductDAO dao = new ProductDAO();
        String action = request.getParameter("action");
        String category = request.getParameter("category");

        try {

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

            if (category != null) {
                ArrayList<Product> list;
                String viewPath = "";

                switch (category) {
                    case "phone":
                        list = (ArrayList<Product>) dao.getProductsByCategory(2);
                        viewPath += "/WEB-INF/views/user/product/product-list/phone-list.jsp";
                        break;
                    case "laptop":
                        list = (ArrayList<Product>) dao.getProductsByCategory(1);
                        viewPath += "/WEB-INF/views/user/product/product-list/laptop-list.jsp";
                        break;
                    case "accessory":
                        list = (ArrayList<Product>) dao.getProductsByCategory(3);
                        viewPath += "/WEB-INF/views/user/product/product-list/accessory-list.jsp";
                        break;
                    default:
                        response.sendRedirect("products");
                        return;
                }

                request.setAttribute("list", list);
                request.getRequestDispatcher(viewPath).forward(request, response);
                return;
            }

            if ("detail".equalsIgnoreCase(action)) {
                String id = request.getParameter("id");
                FeedBackDAO feedbackDAO = new FeedBackDAO();

                List<FeedBack> feedbacks = feedbackDAO.getFeedbackByProductId(Integer.parseInt(id));
                request.setAttribute("feedbackList", feedbacks);

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

                    // Đảm bảo có attributesMap (để tránh NullPointerException)
                    if (product.getAttributesMap() == null) {
                        product.setAttributesMap(new HashMap<>());
                    }
                    String error = (String) request.getSession().getAttribute("msg");
                    request.setAttribute("msg", error);
                    request.getSession().removeAttribute("msg");
                    request.setAttribute("product", product);
                    request.getRequestDispatcher("/WEB-INF/views/user/product/product-detail/product-detail.jsp")
                            .forward(request, response);
                    return;

                } catch (NumberFormatException e) {
                    response.sendRedirect("products");
                    return;
                }
            }

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
    }   
}
