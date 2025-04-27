package com.gopichand.Student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import com.gopichand.factory.ConnectionFactory;

@WebServlet("/dashboardServlet")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void init() {
        ConnectionFactory.getConnection();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Connection connection = ConnectionFactory.getConnection();

        try {
            Statement statement = connection.createStatement();

            int totalStudents = getCount(statement, "SELECT COUNT(sid) FROM student");
            int btechCount = getCount(statement, "SELECT COUNT(*) FROM student WHERE UPPER(course) = 'BTECH'");
            int mtechCount = getCount(statement, "SELECT COUNT(*) FROM student WHERE course = 'M.TECH'");
            int cseCount = getCount(statement, "SELECT COUNT(*) FROM student WHERE branch = 'CSE'");
            int aidsCount = getCount(statement, "SELECT COUNT(*) FROM student WHERE branch = 'AIDS'");
            int aimlCount = getCount(statement, "SELECT COUNT(*) FROM student WHERE branch = 'AIML'");
            int itCount = getCount(statement, "SELECT COUNT(*) FROM student WHERE branch = 'IT'");
            int eceCount = getCount(statement, "SELECT COUNT(*) FROM student WHERE branch = 'ECE'");
            int eeeCount = getCount(statement, "SELECT COUNT(*) FROM student WHERE branch = 'EEE'");
            int mecCount = getCount(statement, "SELECT COUNT(*) FROM student WHERE branch = 'MEC'");
            int civilCount = getCount(statement, "SELECT COUNT(*) FROM student WHERE branch = 'CIVIL'");
            int facultyCount = getCount(statement, "SELECT COUNT(fid) FROM faculty");

            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Dashboard</title>");
            out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");
            out.println("<style>");
            out.println("body { background-color: #f0f2f5; }");
            out.println(".card { border-radius: 15px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); transition: 0.3s ease-in-out; min-height: 180px; }");
            out.println(".card:hover { transform: translateY(-5px); }");
            out.println(".dashboard-title { text-align: center; font-size: 28px; margin-top: 30px; margin-bottom: 40px; font-weight: 600; }");
            out.println(".card-icon { width: 40px; height: 40px; }");
            out.println(".card-body h5 { font-size: 20px; font-weight: 600; }");
            out.println(".card-body p { font-size: 22px; font-weight: bold; margin: 0; }");
            out.println("@media (max-width: 576px) { .dashboard-title { font-size: 22px; margin-top: 20px; margin-bottom: 30px; }");
            out.println(".card-body h5 { font-size: 18px; } .card-body p { font-size: 20px; } }");
            out.println("</style></head><body>");

            out.println("<div class='container'>");
            out.println("<div class='dashboard-title'>📊 College Dashboard Summary</div>");
            out.println("<div class='row g-4 justify-content-center'>");

            // Responsive Dashboard Cards in Custom Order
            card(out, "Faculty Members", facultyCount + "", "https://img.icons8.com/color/48/teacher.png", "warning");
            card(out, "Total Students", totalStudents + "+", "https://img.icons8.com/color/48/classroom.png", "primary");
            card(out, "B.TECH Students", btechCount + "", "https://img.icons8.com/fluency/48/graduation-cap.png", "info");
            card(out, "M.TECH Students", mtechCount + "", "https://img.icons8.com/color/48/student-male.png", "info");
            card(out, "CSE", cseCount + "", "https://img.icons8.com/color/48/laptop.png", "secondary");
            card(out, "AIDS", aidsCount + "", "https://img.icons8.com/color/48/data-configuration.png", "secondary");
            card(out, "AIML", aimlCount + "", "https://img.icons8.com/color/48/artificial-intelligence.png", "secondary");
            card(out, "IT", itCount + "", "https://img.icons8.com/color/48/monitor--v1.png", "secondary");
            card(out, "ECE", eceCount + "", "https://img.icons8.com/color/48/electronics.png", "secondary");
            card(out, "EEE", eeeCount + "", "https://img.icons8.com/color/48/electrical.png", "secondary");
            card(out, "MEC", mecCount + "", "https://img.icons8.com/color/48/maintenance.png", "secondary");
            card(out, "CIVIL", civilCount + "", "https://img.icons8.com/color/48/road.png", "secondary");

            out.println("</div></div></body></html>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getCount(Statement stmt, String query) throws Exception {
        ResultSet rs = stmt.executeQuery(query);
        return rs.next() ? rs.getInt(1) : 0;
    }

    private void card(PrintWriter out, String title, String value, String iconUrl, String color) {
        out.println("<div class='col-12 col-sm-6 col-md-4 col-lg-3'>");
        out.println("<div class='card text-white bg-" + color + "'>");
        out.println("<div class='card-body text-center'>");
        out.println("<img src='" + iconUrl + "' class='card-icon mb-2 img-fluid' alt='" + title + "'>");
        out.println("<h5 class='card-title'>" + title + "</h5>");
        out.println("<p class='card-text'>" + value + "</p>");
        out.println("</div></div></div>");
    }
}
