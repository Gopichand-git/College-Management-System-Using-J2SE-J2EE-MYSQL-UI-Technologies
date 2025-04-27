package com.gopichand.Attendance;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.sql.*;

import org.json.JSONArray;
import org.json.JSONObject;
import com.gopichand.factory.ConnectionFactory;


@WebServlet("/AttendanceReportServlet")
public class AttendanceReportServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String branch = request.getParameter("branch");
        String section = request.getParameter("section");
        String semester = request.getParameter("semester");
        String subject = request.getParameter("subject");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");

        JSONArray resultArray = new JSONArray();
        int totalClassSum = 0;
        int totalAttendedSum = 0;
        int totalAbsentSum = 0;
        
        // Get total number of classes conducted for this period regardless of attendance
        int totalClassesConducted = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = ConnectionFactory.getConnection();

            // First, get the total number of distinct classes conducted for this period
            String classCountQuery = "SELECT COUNT(DISTINCT date) AS total_classes " +
                                    "FROM attendance " +
                                    "WHERE branch = ? AND section = ? AND semester = ? " +
                                    "AND subject = ? AND date BETWEEN ? AND ?";
                                    
            PreparedStatement classCountPs = conn.prepareStatement(classCountQuery);
            classCountPs.setString(1, branch);
            classCountPs.setString(2, section);
            classCountPs.setString(3, semester);
            classCountPs.setString(4, subject);
            classCountPs.setString(5, fromDate);
            classCountPs.setString(6, toDate);
            
            ResultSet classCountRs = classCountPs.executeQuery();
            if (classCountRs.next()) {
                totalClassesConducted = classCountRs.getInt("total_classes");
            }
            classCountRs.close();
            classCountPs.close();

            // Now get student attendance data
            String query = "SELECT s.sid, CONCAT(s.firstname, ' ', s.lastname) AS fullname, s.branch, s.section, " +
                           "COUNT(DISTINCT a.date) AS total_recorded, " +
                           "SUM(CASE WHEN a.status = 'Present' THEN 1 ELSE 0 END) AS attended " +
                           "FROM student s " +
                           "LEFT JOIN attendance a ON s.sid = a.sid " +
                           "AND a.branch = ? AND a.section = ? AND a.semester = ? " +
                           "AND a.subject = ? AND a.date BETWEEN ? AND ? " +
                           "WHERE s.branch = ? AND s.section = ? " +
                           "GROUP BY s.sid, s.firstname, s.lastname, s.branch, s.section";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, branch);     // for JOIN
            ps.setString(2, section);
            ps.setString(3, semester);
            ps.setString(4, subject);
            ps.setString(5, fromDate);
            ps.setString(6, toDate);
            ps.setString(7, branch);     // for WHERE
            ps.setString(8, section);

            // Calculate working days
//            int workingDays = getWorkingDays(fromDate, toDate);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
//                int totalRecorded = rs.getInt("total_recorded");  // Classes recorded in the system
                int totalClasses = totalClassesConducted;         // Actual classes conducted
                int attended = rs.getInt("attended");
                int absent = totalClasses - attended;

                JSONObject obj = new JSONObject();
                obj.put("sid", rs.getString("sid"));
                obj.put("name", rs.getString("fullname"));
                obj.put("branch", rs.getString("branch"));
                obj.put("section", rs.getString("section"));
//                obj.put("workingDays", totalRecorded);              // Working days
                obj.put("total", totalClasses);                   // Total classes
                obj.put("attended", attended);                    // Classes attended
                obj.put("absent", absent);                        // Classes not attended
                resultArray.put(obj);

                totalClassSum += totalClasses;
                totalAttendedSum += attended;
                totalAbsentSum += absent;
            }

            // Summary row
            JSONObject summary = new JSONObject();
            summary.put("sid", "TOTAL");
            summary.put("name", "");
            summary.put("branch", branch);
            summary.put("section", section);
//            summary.put("workingDays", workingDays);
            summary.put("total", totalClassSum);
            summary.put("attended", totalAttendedSum);
            summary.put("absent", totalAbsentSum);
            summary.put("isSummary", true);
            resultArray.put(summary);

            response.setContentType("application/json");
            response.getWriter().print(resultArray.toString());
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            
            // Return error information
            JSONObject errorObj = new JSONObject();
            errorObj.put("error", true);
            errorObj.put("message", e.getMessage());
            
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print(errorObj.toString());
        }
    }
    
    // Calculate working days between two dates (excluding Sundays)
//    private int getWorkingDays(String startDateStr, String endDateStr) {
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            Calendar startDate = Calendar.getInstance();
//            startDate.setTime(sdf.parse(startDateStr));
//            
//            Calendar endDate = Calendar.getInstance();
//            endDate.setTime(sdf.parse(endDateStr));
//            
//            int workingDays = 0;
//            while (startDate.compareTo(endDate) <= 0) {
//                int dayOfWeek = startDate.get(Calendar.DAY_OF_WEEK);
//                if (dayOfWeek != Calendar.SUNDAY) {
//                    workingDays++;
//                }
//                startDate.add(Calendar.DAY_OF_MONTH, 1);
//            }
//            
//            return workingDays;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirect GET requests to the POST method or handle them separately if needed
        doPost(request, response);
    }
}