/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import dao.AdminProductDAO;
import dao.ProductDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.util.List;
import model.FileUpload;
import model.Product;

/**
 *
 * @author acer
 */
@WebServlet(name = "AdminProductServlet", urlPatterns = {"/admin/product"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class AdminProductServlet extends HttpServlet {

   

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String view = request.getParameter("view");
        AdminProductDAO daoProductAd = new AdminProductDAO();
        ProductDAO daoProduct = new ProductDAO();
        String idProduct = request.getParameter("Id");
         if (view == null || view.equals("product")) {
            List<Product> listpro = daoProductAd.getAllProducts();
            request.setAttribute("listproductadmin", listpro);
            request.getRequestDispatcher("/WEB-INF/views/admin/product/admin-product.jsp").forward(request, response);
        } else if (view.equals("edit") && idProduct != null) {
            Product pro = daoProductAd.getProductById(Integer.parseInt(idProduct));
            request.setAttribute("productedit", pro);
            request.getRequestDispatcher("/WEB-INF/views/admin/product/edit-product.jsp").forward(request, response);
        } else if (view.equals("createproduct")) {

            request.getRequestDispatcher("/WEB-INF/views/admin/product/create-product.jsp").forward(request, response);
        } else if (view.equals("detail") && idProduct != null){
            Product productdetail = daoProductAd.getProductById(Integer.parseInt(idProduct));
            request.setAttribute("productdetail", productdetail);
            request.getRequestDispatcher("/WEB-INF/views/admin/product/product-detail.jsp").forward(request, response);
        } else if(view.equals("delete") && idProduct != null){
            Product productdetail = daoProductAd.getProductById(Integer.parseInt(idProduct));
            request.setAttribute("productdelete", productdetail);
            request.getRequestDispatcher("/WEB-INF/views/admin/product/product-delete.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       String action = request.getParameter("action");
        
        AdminProductDAO daoProductAd = new AdminProductDAO();
        if (action.equals("filter")) {
            String category = request.getParameter("category");
            String brand = request.getParameter("brand");
            String sort = request.getParameter("sort");

            List<Product> products = daoProductAd.filterProducts(category, brand, sort);

            response.setContentType("application/json;charset=UTF-8");
            Gson gson = new Gson();
            String json = gson.toJson(products);
            response.getWriter().write(json);

        } else if (action.equals("search")) {
            String keyword = request.getParameter("keyword");

            List<Product> productSearch = daoProductAd.getAllProductsSearch(keyword);

            response.setContentType("application/json;charset=UTF-8");
            Gson gson = new Gson();
            String json = gson.toJson(productSearch);
            response.getWriter().write(json);
        } else if (action.equals("createproduct")) {
            String productname = request.getParameter("productname");
            String CategoryID = request.getParameter("CategoryID");
            String brand = request.getParameter("brand");
            String price = request.getParameter("price");
            String descriptionproduct = request.getParameter("descriptionproduct");
            String cpu = request.getParameter("cpu");
            String ram = request.getParameter("ram");
            String storage = request.getParameter("storage");
            String os = request.getParameter("os");
            String weight = request.getParameter("weight");
            String stock = request.getParameter("stock");
            String model = request.getParameter("model");
            String cam = request.getParameter("cam");
            String type = request.getParameter("type");
            String connectivity = request.getParameter("connectivity");
            String color = request.getParameter("color");
            String compatibility = request.getParameter("compatibility");
            String uploadFolder = FileUpload.UPLOAD_DIR;
            if(CategoryID.equals("1")){
                uploadFolder += "\\laptops";
            } else if (CategoryID.equals("2")){
                uploadFolder += "\\phones";
            } else if (CategoryID.equals("3")){
                uploadFolder += "\\accessories";
            } 
            
            File folder = new File(uploadFolder);
            if (!folder.exists()) {
                folder.mkdir();
            }
            Part imgPart = request.getPart("img");
            String imgName = imgPart.getSubmittedFileName();
            if (imgName != null && imgName.length() > 0) {
                File outFile = new File(folder, imgName);
                FileUpload.saveFile(imgPart, outFile);
            }
            
            int success = daoProductAd.createProduct(imgName,model, productname, CategoryID, brand, price, descriptionproduct, cpu, ram, storage, os, weight, stock, cam, type, connectivity, color, compatibility);
            if(success == 1){
                response.sendRedirect(request.getContextPath() + "/admin/product?view=product");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/product?view=createproduct");
            }
        } else if(action.equals("updateproduct")){
            String productname = request.getParameter("productname");
            String CategoryID = request.getParameter("CategoryID");
            String brand = request.getParameter("brand");
            String price = request.getParameter("price");
            String descriptionproduct = request.getParameter("descriptionproduct");
            String cpu = request.getParameter("cpu");
            String ram = request.getParameter("ram");
            String storage = request.getParameter("storage");
            String os = request.getParameter("os");
            String weight = request.getParameter("weight");
            String stock = request.getParameter("stock");
            String model = request.getParameter("model");
            String cam = request.getParameter("cam");
            String type = request.getParameter("type");
            String connectivity = request.getParameter("connectivity");
            String color = request.getParameter("color");
            String compatibility = request.getParameter("compatibility");
            String id = request.getParameter("productID");
            int idparse = Integer.parseInt(id);
            String uploadFolder = FileUpload.UPLOAD_DIR;
            if(CategoryID.equals("1")){
                uploadFolder += "\\laptops";
            } else if (CategoryID.equals("2")){
                uploadFolder += "\\phones";
            } else if (CategoryID.equals("3")){
                uploadFolder += "\\accessories";
            } 
            
            File folder = new File(uploadFolder);
            if (!folder.exists()) {
                folder.mkdir();
            }
            Part imgPart = request.getPart("img");
            String imgName = imgPart.getSubmittedFileName();
            if (imgName != null && imgName.length() > 0) {
                File outFile = new File(folder, imgName);
                FileUpload.saveFile(imgPart, outFile);
            }
            
            int success = daoProductAd.updateProduct(idparse, imgName, model, productname, CategoryID, brand, price, descriptionproduct, cpu, ram, storage, os, weight, stock, cam, type, connectivity, color, compatibility);
            if(success == 1){
                response.sendRedirect(request.getContextPath() + "/admin/product?view=detail&Id=" + id);
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/product?view=edit&Id=" + id);
            }
        } else if(action.equals("deleteproduct")){
            String id = request.getParameter("productID");
            int deleted = daoProductAd.deleteProduct(Integer.parseInt(id));
            if(deleted == 1){
                response.sendRedirect(request.getContextPath() + "/admin/product?view=product");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/product?view=delete&Id=" + id);
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
