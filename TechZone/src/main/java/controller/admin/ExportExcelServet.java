/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dao.StatisticalDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.AllCategory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author letan
 */
@WebServlet(name = "ExportExcelServett", urlPatterns = {"/admin/export"})
public class ExportExcelServet extends HttpServlet {

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
            out.println("<title>Servlet ExportExcelServet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ExportExcelServet at " + request.getContextPath() + "</h1>");
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

        List<AllCategory> category = order.getAll();
        request.setAttribute("listall", category);
        request.getRequestDispatcher("/WEB-INF/views/admin/export-excel.jsp").forward(request, response);

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

        // Create an instance of StatisticalDAO to retrieve data from the database
        StatisticalDAO order = new StatisticalDAO();

        // 🧾 Create a new Excel workbook using Apache POI
        Workbook workbook = new XSSFWorkbook();

        // Retrieve all product category statistics from the DAO
        List<AllCategory> category = order.getAll();

        // Store the list in the request scope (for reference if needed)
        request.setAttribute("listall", category);

        // Create a new sheet in the workbook named "ProductCategory"
        Sheet sheet = workbook.createSheet("ProductCategory");

        // Create header row (column titles)
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Sản Phẩm");   // Product Name
        header.createCell(1).setCellValue("Số Lượng");   // Quantity
        header.createCell(2).setCellValue("Lượt Bán");   // Sales Count
        header.createCell(3).setCellValue("Doanh Thu");  // Revenue

        // Fill data rows from the list of categories
        int rowIndex = 1; // Start from the second row (after header)
        for (AllCategory u : category) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(u.getName());         // Product name
            row.createCell(1).setCellValue(u.getSales());        // Quantity
            row.createCell(2).setCellValue(u.getSumquantity());  // Total sales count
            row.createCell(3).setCellValue(u.getAllprice());     // Revenue
        }

        // Create and apply a bold font style for the header row
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);
        for (Cell cell : header) {
            cell.setCellStyle(headerStyle); // Apply style to each header cell
        }

        // Automatically resize all columns to fit their content
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }

        // ⚙️ Set response headers to indicate an Excel file download
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"product.xlsx\"");

        // Write the workbook to the HTTP response output stream
        workbook.write(response.getOutputStream());

        // Close the workbook to free system resources
        workbook.close();
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
