/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dao.StatisticalDAO;
import model.AllCategory;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Category;

/**
 *
 * @author letan
 */
@WebServlet(name = "Admin", urlPatterns = {"/admin/report"})
public class Dashboard extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Dashboard</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Dashboard at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        StatisticalDAO order = new StatisticalDAO();

        double alltotal = order.getTotal();
        int allbill = order.getTotalBill();
        int allproduct = order.getTotalProduct();
        int allaccount = order.getTotalAccount();
        request.setAttribute("allprice", alltotal);
        request.setAttribute("allbill", allbill);
        request.setAttribute("allproduct", allproduct);
        request.setAttribute("allaccount", allaccount);
        List<Category> list = order.getTotalCategory();
        String[] colors = {"#3b82f6", "#60a5fa", "#93c5fd", "#2563eb", "#1e40af"};
        StringBuilder gradient = new StringBuilder("conic-gradient(");
        double current = 0;
        for (int i = 0; i < list.size(); i++) {
            double next = current + list.get(i).getTotal();
            gradient.append(colors[i % colors.length])
                    .append(" ")
                    .append(current)
                    .append("% ")
                    .append(next)
                    .append("%");
            if (i < list.size() - 1) {
                gradient.append(", ");
            }
            current = next;
            request.setAttribute("per" + i + "", list.get(i).getTotal());
        }
        // Gửi sang JSP
        request.setAttribute("listtotal", list);
        request.setAttribute("pieGradient", gradient.toString() + ")");
        request.setAttribute("completed", order.getCompleted());
        request.setAttribute("processing", order.getProcessing());
        request.setAttribute("cancel", order.getCancel());
        request.setAttribute("pending", order.getPending());
        List<AllCategory> category = order.getAll();
        request.setAttribute("listall", category);
        request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);

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
        processRequest(request, response);
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
