package com.gopichand.Validations;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.gopichand.factory.ConnectionFactory;

@WebServlet("/SendOtpServlet")
public class SendOtpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String API_KEY = "ac872c9e-191c-11f0-8b17-0200cd936042";
    
    public void init() {
        ConnectionFactory.getConnection();
    }
    
    @SuppressWarnings("deprecation")
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String email = req.getParameter("email");
        PrintWriter out = res.getWriter();
        
        if (email == null || email.isEmpty()) {
            out.print("error");
            return;
        }
        
        // Get mobile number from database using email
        String mobile = getMobileFromEmail(email);
        
        if (mobile == null || mobile.isEmpty()) {
            out.print("error");
            return;
        }
        
        try {
            String url = "https://2factor.in/API/V1/" + API_KEY + "/SMS/" + mobile + "/AUTOGEN";
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
            
            if (conn.getResponseCode() == 200) {
                // Extract session_id from response
                String json = result.toString();
                String sessionId = json.split("\"Details\":\"")[1].split("\"")[0];
                // Store in session
                HttpSession session = req.getSession();
                session.setAttribute("otpSessionId", sessionId);
                
                // Set the response
                out.print("success");
            } else {
                out.print("error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.print("error");
        }
    }
    
    private String getMobileFromEmail(String email) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String mobile = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            String sql = "SELECT mobile FROM users WHERE email = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                mobile = rs.getString("mobile");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                // Don't close the connection here as it's managed by ConnectionFactory
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return mobile;
    }
}