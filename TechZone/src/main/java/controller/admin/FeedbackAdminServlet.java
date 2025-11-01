/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dao.FeedBackDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Feedback;

import until.Pagination;

/**
 *
 * @author letan
 */
@WebServlet(name = "FeedbackServlet", urlPatterns = {"/admin/feedback"})
public class FeedbackAdminServlet extends HttpServlet {

   
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
        FeedBackDAO feedback = new FeedBackDAO();
        Pagination pag = new Pagination();
        String rating = request.getParameter("rating");
        String text = request.getParameter("keyword");
        String id = request.getParameter("id");
        if (id != null) {
            Feedback data = feedback.getId(Integer.parseInt(id));
            request.setAttribute("data", data);
            request.getRequestDispatcher("/WEB-INF/views/admin/feedback/feedback-detail.jsp").forward(request, response);
            return;
        }
        String page1 = request.getParameter("page");
        if (page1 == null) {
            page1 = "1";
        }
        pag.handlePagintation(request, Integer.parseInt(page1), feedback.getAll(), "/admin/feedback");
        int totalpage = Integer.parseInt(request.getAttribute("totalPage") + "");
        if (rating == null && text == null || rating.isEmpty() && text.isEmpty()) {
            List<Feedback> list = feedback.getAllPage(Integer.parseInt(page1), 10);
            request.setAttribute("list", list);
        } else if (text.isEmpty()) {
            List<Feedback> list = feedback.getRating(Integer.parseInt(page1), totalpage, Integer.parseInt(rating));
            request.setAttribute("list", list);
        } else if (rating.isEmpty()) {
            List<Feedback> list = feedback.getByKeyword(Integer.parseInt(page1), totalpage, text);
            request.setAttribute("list", list);
        } else if (text != null && rating != null) {
            List<Feedback> list = feedback.searchFeedback(Integer.parseInt(page1), totalpage, text, Integer.parseInt(rating));
            request.setAttribute("list", list);
        }
        request.getRequestDispatcher("/WEB-INF/views/admin/feedback/feedback.jsp").forward(request, response);
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
        FeedBackDAO feedback = new FeedBackDAO();
        String type = request.getParameter("type");
        String id = request.getParameter("id");
        String text = request.getParameter("adminReply");
        if (id != null && text != null) {
            if (feedback.updateFeedBack(Integer.parseInt(id), text) == 1) {
                response.sendRedirect(request.getContextPath() + "/admin/feedback?page=1");
            }
        }
        if (type != null && id != null) {
            String iddelete = request.getParameter("id");
            if (feedback.getUpdateDelete(Integer.parseInt(id)) == 1) {
                response.sendRedirect(request.getContextPath() + "/admin/feedback?page=1");
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
