/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author acer
 */
@WebServlet(name = "SendCodePasswordServlet", urlPatterns = {"/sendcodepassword"})
public class SendCodePasswordServlet extends HttpServlet {

    

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("error", "not");
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String confirm = request.getParameter("code");
        if (confirm != null) {
            String codee = request.getParameter("confirm");
            String email = request.getParameter("email");
            if (confirm.equals(codee)) {
                request.setAttribute("email", email);
                request.setAttribute("confirm", codee);
                request.setAttribute("status", "ok");
                request.setAttribute("error", "not");
                request.getRequestDispatcher("/WEB-INF/views/user/forgetpassword.jsp").forward(request, response);
                return;
            } else {
                request.setAttribute("error", "error");
                request.setAttribute("status", "false");
                request.getRequestDispatcher("/WEB-INF/views/user/forgetpassword.jsp").forward(request, response);
                return;
            }

        }
        String to = request.getParameter("email");
        String from = "loctramcam12@gmail.com"; 
        String password = "ficcgrgzxnxyguwu";     
        // Tạo mã OTP ngẫu nhiên 6 chữ số
        int code = (int) (Math.random() * 900000) + 100000;
        
        
        // Cấu hình SMTP Gmail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Tạo session gửi mail
        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // ======= Gửi mail =======
            Message message = new MimeMessage(session);
            try {
                message.setFrom(new InternetAddress(from));
            } catch (MessagingException ex) {
                Logger.getLogger(SendCodeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Mã xác minh của bạn");
            message.setText("Mã xác minh của bạn là: " + code);

            Transport.send(message);
            HttpSession sess = request.getSession();
            sess.setAttribute("otp", code);
            request.setAttribute("email", to);
            request.setAttribute("error", "not");
            sess.setAttribute("status", "success");
            request.setAttribute("message", "Đã gửi mã xác minh đến " + to);
        } catch (AddressException e) {
            request.setAttribute("message", "Lỗi địa chỉ email không hợp lệ: " + e.getMessage());
        } catch (MessagingException e) {
            request.setAttribute("message", "Không thể gửi mail: " + e.getMessage());
        }
        request.getRequestDispatcher("/WEB-INF/views/user/forgetpassword.jsp").forward(request, response);

    }

}
