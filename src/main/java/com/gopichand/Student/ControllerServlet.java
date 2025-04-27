package com.gopichand.Student;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import com.gopichand.beans.Faculty;
import com.gopichand.beans.Student;
import com.gopichand.factory.ConnectionFactory;
import com.gopichand.factory.FacultyServiceFactory;
import com.gopichand.factory.StudentDaoFactory;
import com.gopichand.factory.StudentServiceFactory;
import com.gopichand.service.FacultyService;
import com.gopichand.service.StudentService;

@WebServlet("*.do")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
		   public void init(){
		       ConnectionFactory.getConnection();
		       StudentServiceFactory.getStudentService();
		       StudentDaoFactory.getStudentDao();
		   }

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		
		   Student student = null;
	       String sid = "", sfname = "", slname="",saddr = "",sgender="",semail="",smobile="",sfather="",sfatherMobile="", smother="",smotherMobile="",section="",scourse="",sbranch="",sfee = "",scity="",spincode="",state="",sjoin="";
	       
	       String status = "";
	       StudentService studentService = StudentServiceFactory.getStudentService();

	       
	       //  Faculty Registration
	       Faculty faculty = null;
	       String fid = "",firstname="",lastname="",gender="",address="",email="",mobile="",course="",branch="",designation="",qualification="",salary="",city="",pincode="",fstate="",joining="";
	       String status1="";
	       FacultyService facultyService = FacultyServiceFactory.getFacultyService();
	       
	       
	       String requestURI = req.getRequestURI();
	       if (requestURI.endsWith("add.do")) {
	    	    sid = req.getParameter("sid");
	    	    Student std = studentService.searchStudent(sid);
	    	    if (std != null) {
	    	        resp.setStatus(HttpServletResponse.SC_CONFLICT); // 409 Conflict
	    	        resp.getWriter().write("exists");
	    	        return; // Exit the method
	    	    }
	    	    
	    	    // If student does not exist, add new student
	    	    if (std == null) {
	    	        sfname = req.getParameter("sfname");
	    	        slname = req.getParameter("slname");
	    	        saddr = req.getParameter("saddr");
	    	        sgender = req.getParameter("sgender");
	    	        semail = req.getParameter("semail");
	    	        smobile = req.getParameter("smobile");
	    	        sfather = req.getParameter("father_name");
	    	        sfatherMobile = req.getParameter("father_mobile");
	    	        smother = req.getParameter("mother_name");
	    	        smotherMobile = req.getParameter("mother_mobile");
	    	        scourse = req.getParameter("scourse");
	    	        sbranch = req.getParameter("sbranch");
	    	        section = req.getParameter("section");
	    	        sfee = req.getParameter("fee");
	    	        scity = req.getParameter("scity");
	    	        spincode = req.getParameter("spin");
	    	        state = req.getParameter("state");
	    	        sjoin = req.getParameter("joining");
	    	        
	    	        
	    	        // Validate email format
	    	        if (semail == null || !semail.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
	    	            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
	    	            resp.getWriter().write("Please enter a valid email address");
	    	            return;
	    	        }
	    	        
	    	        // Validate mobile number
	    	        if (smobile == null || !smobile.matches("^\\d{10}$")) {
	    	            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
	    	            resp.getWriter().write("Please enter a valid 10-digit mobile number");
	    	            return;
	    	        }
	    	        
	    	        // Validate father mobile number
	    	        if (sfatherMobile == null || !sfatherMobile.matches("^\\d{10}$")) {
	    	            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
	    	            resp.getWriter().write("Please enter a valid 10-digit mobile number for father");
	    	            return;
	    	        }
	    	        
	    	        // Validate mother mobile number
	    	        if (smotherMobile == null || !smotherMobile.matches("^\\d{10}$")) {
	    	            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
	    	            resp.getWriter().write("Please enter a valid 10-digit mobile number for mother");
	    	            return;
	    	        }
	    	        
	    	        // Validate date format
	    	        if (sjoin == null || !sjoin.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
	    	            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
	    	            resp.getWriter().write("Please enter date in YYYY-MM-DD format");
	    	            return;
	    	        }
	    	        
	    	        // Validate required fields
	    	        if (sfname == null || sfname.trim().isEmpty() ||
	    	            slname == null || slname.trim().isEmpty() ||
	    	            saddr == null || saddr.trim().isEmpty() ||
	    	            sgender == null || sgender.trim().isEmpty() ||
	    	            scourse == null || scourse.trim().isEmpty() ||
	    	            sbranch == null || sbranch.trim().isEmpty() ||
	    	            scity == null || scity.trim().isEmpty() ||
	    	            spincode == null || spincode.trim().isEmpty() ||
	    	            state == null || state.trim().isEmpty()) {
	    	            
	    	            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
	    	            resp.getWriter().write("Please fill all required fields");
	    	            return;
	    	        }
	    	        

	    	        student = new Student();
	    	        student.setSid(sid);
	    	        student.setSfname(sfname);
	    	        student.setSlname(slname);
	    	        student.setSaddr(saddr);
	    	        student.setSgender(sgender);
	    	        student.setSemail(semail);
	    	        student.setSmobile(smobile);
	    	        student.setSfather(sfather);
	    	        student.setSfatherMobile(sfatherMobile);
	    	        student.setSmother(smother);
	    	        student.setSmotherMobile(smotherMobile);
	    	        student.setScourse(scourse);
	    	        student.setSbranch(sbranch);
	    	        student.setSsection(section);
	    	        student.setSfee(sfee);
	    	        student.setScity(scity);
	    	        student.setSpincode(spincode);
	    	        student.setSstate(state);
	    	        student.setSjoin(sjoin);

	    	        status = studentService.addStudent(student);
	    	        if (status.equalsIgnoreCase("success")) {
	    	            resp.getWriter().write(status);
	    	        }
	    	        if(status.equalsIgnoreCase("failure")) {
	    	            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    	            resp.getWriter().write(status);
	    	        }
	    	    } 
	    	}

	       if (requestURI.endsWith("search.do")) {
	    	    sid = req.getParameter("sid");
	    	    student = studentService.searchStudent(sid);
	    	    
	    	    if(student == null) {
	    	        resp.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
	    	        resp.getWriter().write("Student not found");
	    	    } else {
	    	        resp.setContentType("text/html");
	    	        
	    	        out.println("<!DOCTYPE html>");
	    	        out.println("<html lang=\"en\">");
	    	        out.println("<head>");
	    	        out.println("    <meta charset=\"UTF-8\">");
	    	        out.println("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
	    	        out.println("    <title>Student Information</title>");
	    	        out.println("    <style>");
	    	        out.println("        body {");
	    	        out.println("            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;");
	    	        out.println("            background-color: #f5f5f5;");
	    	        out.println("            margin: 0;");
	    	        out.println("            padding: 20px;");
	    	        out.println("            color: #333;");
	    	        out.println("        }");
	    	        out.println("        .container {");
	    	        out.println("            max-width: 800px;");
	    	        out.println("            margin: 0 auto;");
	    	        out.println("            background-color: #fff;");
	    	        out.println("            border-radius: 8px;");
	    	        out.println("            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);");
	    	        out.println("            padding: 20px;");
	    	        out.println("        }");
	    	        out.println("        h2 {");
	    	        out.println("            color: #2c3e50;");
	    	        out.println("            text-align: center;");
	    	        out.println("            margin-bottom: 20px;");
	    	        out.println("            padding-bottom: 10px;");
	    	        out.println("            border-bottom: 2px solid #3498db;");
	    	        out.println("        }");
	    	        out.println("        .info-grid {");
	    	        out.println("            display: grid;");
	    	        out.println("            grid-template-columns: repeat(2, 1fr);");
	    	        out.println("            gap: 20px;");
	    	        out.println("        }");
	    	        out.println("        .info-section {");
	    	        out.println("            margin-bottom: 20px;");
	    	        out.println("            border: 1px solid #e0e0e0;");
	    	        out.println("            border-radius: 5px;");
	    	        out.println("            overflow: hidden;");
	    	        out.println("        }");
	    	        out.println("        .section-title {");
	    	        out.println("            background-color: #3498db;");
	    	        out.println("            color: white;");
	    	        out.println("            padding: 10px;");
	    	        out.println("            margin: 0;");
	    	        out.println("            font-size: 16px;");
	    	        out.println("        }");
	    	        out.println("        .info-table {");
	    	        out.println("            width: 100%;");
	    	        out.println("            border-collapse: collapse;");
	    	        out.println("        }");
	    	        out.println("        .info-table tr:nth-child(even) {");
	    	        out.println("            background-color: #f9f9f9;");
	    	        out.println("        }");
	    	        out.println("        .info-table td {");
	    	        out.println("            padding: 10px;");
	    	        out.println("            border-bottom: 1px solid #eee;");
	    	        out.println("        }");
	    	        out.println("        .info-table td:first-child {");
	    	        out.println("            font-weight: bold;");
	    	        out.println("            width: 40%;");
	    	        out.println("            color: #555;");
	    	        out.println("        }");
	    	        out.println("        .action-btn {");
	    	        out.println("            display: block;");
	    	        out.println("            width: 200px;");
	    	        out.println("            margin: 20px auto 0;");
	    	        out.println("            padding: 10px;");
	    	        out.println("            background-color: #2c3e50;");
	    	        out.println("            color: white;");
	    	        out.println("            text-align: center;");
	    	        out.println("            text-decoration: none;");
	    	        out.println("            border-radius: 5px;");
	    	        out.println("            font-size: 16px;");
	    	        out.println("            transition: background-color 0.3s;");
	    	        out.println("        }");
	    	        out.println("        .action-btn:hover {");
	    	        out.println("            background-color: #1a252f;");
	    	        out.println("        }");
	    	        out.println("    </style>");
	    	        out.println("</head>");
	    	        out.println("<body>");
	    	        out.println("    <div class=\"container\">");
	    	        out.println("        <h2>Student Information Data</h2>");
	    	        out.println("        ");
	    	        out.println("        <div class=\"info-grid\">");
	    	        out.println("            <div class=\"info-section\">");
	    	        out.println("                <h3 class=\"section-title\">Personal Details</h3>");
	    	        out.println("                <table class=\"info-table\">");
	    	        out.println("                    <tr><td>Student ID</td><td>" + student.getSid() + "</td></tr>");
	    	        out.println("                    <tr><td>First Name</td><td>" + student.getSfname() + "</td></tr>");
	    	        out.println("                    <tr><td>Last Name</td><td>" + student.getSlname() + "</td></tr>");
	    	        out.println("                    <tr><td>Gender</td><td>" + student.getSgender() + "</td></tr>");
	    	        out.println("                    <tr><td>Email</td><td>" + student.getSemail() + "</td></tr>");
	    	        out.println("                    <tr><td>Mobile Number</td><td>" + student.getSmobile() + "</td></tr>");
	    	        out.println("                </table>");
	    	        out.println("            </div>");
	    	        out.println("            ");
	    	        out.println("            <div class=\"info-section\">");
	    	        out.println("                <h3 class=\"section-title\">Family Information</h3>");
	    	        out.println("                <table class=\"info-table\">");
	    	        out.println("                    <tr><td>Father Name</td><td>" + student.getSfather() + "</td></tr>");
	    	        out.println("                    <tr><td>Father Mobile</td><td>" + student.getSfatherMobile() + "</td></tr>");
	    	        out.println("                    <tr><td>Mother Name</td><td>" + student.getSmother() + "</td></tr>");
	    	        out.println("                    <tr><td>Mother Number</td><td>" + student.getSmotherMobile() + "</td></tr>");
	    	        out.println("                </table>");
	    	        out.println("            </div>");
	    	        out.println("            ");
	    	        out.println("            <div class=\"info-section\">");
	    	        out.println("                <h3 class=\"section-title\">Educational Details</h3>");
	    	        out.println("                <table class=\"info-table\">");
	    	        out.println("                    <tr><td>Course</td><td>" + student.getScourse() + "</td></tr>");
	    	        out.println("                    <tr><td>Branch</td><td>" + student.getSbranch() + "</td></tr>");
	    	        out.println("                    <tr><td>Section</td><td>" + student.getSsection() + "</td></tr>");
	    	        out.println("                    <tr><td>Fee</td><td>" + student.getSfee() + "</td></tr>");
	    	        out.println("                    <tr><td>Joining Date</td><td>" + student.getSjoin() + "</td></tr>");
	    	        out.println("                </table>");
	    	        out.println("            </div>");
	    	        out.println("            ");
	    	        out.println("            <div class=\"info-section\">");
	    	        out.println("                <h3 class=\"section-title\">Address Information</h3>");
	    	        out.println("                <table class=\"info-table\">");
	    	        out.println("                    <tr><td>Address</td><td>" + student.getSaddr() + "</td></tr>");
	    	        out.println("                    <tr><td>City</td><td>" + student.getScity() + "</td></tr>");
	    	        out.println("                    <tr><td>Pin Code</td><td>" + student.getSpincode() + "</td></tr>");
	    	        out.println("                    <tr><td>Province</td><td>" + student.getSstate() + "</td></tr>");
	    	        out.println("                </table>");
	    	        out.println("            </div>");
	    	        out.println("        </div>");
	    	        out.println("        ");
	    	        out.println("        <a href=\"College.jsp\" class=\"action-btn\">Back to Menu</a>");
	    	        out.println("    </div>");
	    	        out.println("    ");
	    	        out.println("    <script>");
	    	        out.println("        document.addEventListener('DOMContentLoaded', function() {");
	    	        out.println("            // Add print functionality");
	    	        out.println("            const printBtn = document.createElement('button');");
	    	        out.println("            printBtn.className = 'action-btn';");
	    	        out.println("            printBtn.style.marginTop = '10px';");
	    	        out.println("            printBtn.textContent = 'Print Information';");
	    	        out.println("            printBtn.addEventListener('click', function() {");
	    	        out.println("                window.print();");
	    	        out.println("            });");
	    	        out.println("            ");
	    	        out.println("            document.querySelector('.container').appendChild(printBtn);");
	    	        out.println("        });");
	    	        out.println("    </script>");
	    	        out.println("</body>");
	    	        out.println("</html>");
	    	    }
	    	}
	       
	       if (requestURI.endsWith("edit.do")) {
	    	    sid = req.getParameter("sid");
	    	    student = studentService.searchStudent(sid);
	    	    
	    	    if(student == null) {
	    	        resp.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
	    	        resp.setContentType("application/json");
	    	        resp.getWriter().write("{\"error\": \"Student with ID " + sid + " not found\"}");
	    	    } else {
	    	        // Create a better styled HTML form for updating student
	    	    	StringBuilder htmlBuilder = new StringBuilder();

	    	    	htmlBuilder.append("<div class='container my-5'>"); // Added container
	    	    	htmlBuilder.append("<div class='card shadow-lg p-4'>");
	    	    	htmlBuilder.append("<h3 class='card-title text-center mb-4'>Update Student Details</h3>");
	    	    	htmlBuilder.append("<form id='studentUpdateForm' method='post' action='update.do'>");

	    	    	htmlBuilder.append("<div class='row g-3'>"); // Added Bootstrap 5 gutter spacing (g-3)

	    	    	String[][] fields = {
	    	    	    {"sid", "Student ID", "text", student.getSid(), "readonly"},
	    	    	    {"sfname", "First Name", "text", student.getSfname(), "required"},
	    	    	    {"slname", "Last Name", "text", student.getSlname(), "required"},
	    	    	    {"saddr", "Address", "text", student.getSaddr(), ""},
	    	    	    {"sgender", "Gender", "text", student.getSgender(), ""},
	    	    	    {"semail", "Email", "email", student.getSemail(), "required"},
	    	    	    {"smobile", "Mobile", "text", student.getSmobile(), ""},
	    	    	    {"sfather", "Father Name", "text", student.getSfather(), ""},
	    	    	    {"sfatherMobile", "Father Mobile", "text", student.getSfatherMobile(), ""},
	    	    	    {"smother", "Mother Name", "text", student.getSmother(), ""},
	    	    	    {"smotherMobile", "Mother Mobile", "text", student.getSmotherMobile(), ""},
	    	    	    {"scourse", "Course", "text", student.getScourse(), ""},
	    	    	    {"sbranch", "Branch", "text", student.getSbranch(), ""},
	    	    	    {"section", "Section", "text", student.getSsection(), ""},
	    	    	    {"fee", "Fee", "text", student.getSfee(), ""},
	    	    	    {"scity", "City", "text", student.getScity(), ""},
	    	    	    {"spin", "Pin Code", "text", student.getSpincode(), ""},
	    	    	    {"state", "Province/State", "text", student.getSstate(), ""},
	    	    	    {"sjoin", "Joining Date", "date", student.getSjoin(), ""}
	    	    	};

	    	    	for (String[] field : fields) {
	    	    	    htmlBuilder.append("<div class='col-md-6'>");
	    	    	    htmlBuilder.append("<label for='" + field[0] + "' class='form-label'>" + field[1] + "</label>");
	    	    	    htmlBuilder.append("<input type='" + field[2] + "' class='form-control' id='" + field[0] + "' name='" + field[0] + "' value='" + field[3] + "' " + field[4] + ">");
	    	    	    htmlBuilder.append("</div>");
	    	    	}

	    	    	htmlBuilder.append("</div>"); // Close row

	    	    	// Submit Button
	    	    	htmlBuilder.append("<div class='text-center mt-4'>");
	    	    	htmlBuilder.append("<button type='submit' class='btn btn-primary px-5'>");
	    	    	htmlBuilder.append("<span class='spinner-border spinner-border-sm d-none' id='updateLoadingSpinner' role='status'></span> ");
	    	    	htmlBuilder.append("Update Student");
	    	    	htmlBuilder.append("</button>");
	    	    	htmlBuilder.append("</div>");

	    	    	htmlBuilder.append("</form>");
	    	    	htmlBuilder.append("</div>");
	    	    	htmlBuilder.append("</div>");

	    	        
	    	        out.println(htmlBuilder.toString());
	    	    }
	    	}

	    	if (requestURI.endsWith("update.do")) {
	    	    // Get updated student details from the form
	    	    sid = req.getParameter("sid");
	    	    sfname = req.getParameter("sfname");
	    	    slname = req.getParameter("slname");
	    	    saddr = req.getParameter("saddr");
	    	    sgender = req.getParameter("sgender");
	    	    semail = req.getParameter("semail");
	    	    smobile = req.getParameter("smobile");
	    	    sfather = req.getParameter("sfather");
	    	    sfatherMobile = req.getParameter("sfatherMobile");
	    	    smother = req.getParameter("smother");
	    	    smotherMobile = req.getParameter("smotherMobile");
	    	    scourse = req.getParameter("scourse");
	    	    sbranch = req.getParameter("sbranch");
	    	    section = req.getParameter("section");
	    	    sfee = req.getParameter("fee");
	    	    scity = req.getParameter("scity");
	    	    spincode = req.getParameter("spin");
	    	    state = req.getParameter("state");
	    	    sjoin = req.getParameter("sjoin");

	    	    student = new Student();
	    	    student.setSid(sid);
	    	    student.setSfname(sfname);
	    	    student.setSlname(slname);
	    	    student.setSaddr(saddr);
	    	    student.setSgender(sgender);
	    	    student.setSemail(semail);
	    	    student.setSmobile(smobile);
	    	    student.setSfather(sfather);
	    	    student.setSfatherMobile(sfatherMobile);
	    	    student.setSmother(smother);
	    	    student.setSmotherMobile(smotherMobile);
	    	    student.setScourse(scourse);
	    	    student.setSbranch(sbranch);
	    	    student.setSsection(section);
	    	    student.setSfee(sfee);
	    	    student.setScity(scity);
	    	    student.setSpincode(spincode);
	    	    student.setSstate(state);
	    	    student.setSjoin(sjoin);

	    	    status = studentService.updateStudent(student);
	    	    
	    	    // Always set content type to JSON for consistent responses
	    	    resp.setContentType("application/json");

	    	    if (status.equalsIgnoreCase("success")) {
	    	        resp.setStatus(HttpServletResponse.SC_OK);
	    	        resp.getWriter().write("{\"message\": \"Student updated successfully!\"}");
	    	    } else {
	    	        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    	        resp.getWriter().write("{\"error\": \"Failed to update student details. Please try again.\"}");
	    	    }
	    	}
	       
	    	if (requestURI.endsWith("delete.do")) {
	    	    sid = req.getParameter("sid");
	    	    student = studentService.searchStudent(sid);
	    	    
	    	    if(student == null) {
	    	        // Student ID not found - redirect with notfound status
	    	        resp.sendRedirect("deleteform.html?status=notfound");
	    	    } else {
	    	         status = studentService.deleteStudent(sid);
	    	        if(status.equalsIgnoreCase("success")) {
	    	            // Deletion successful
	    	            resp.sendRedirect("College.jsp?status=success");
	    	        } else {
	    	            // Deletion failed
	    	            resp.sendRedirect("deleteform.html?status=error");
	    	        }
	    	    }
	    	}	
	       
	       //Faculty Registration
	       
	    // In your servlet doPost or service method
	       if (requestURI.endsWith("add1.do")) {
	           fid = req.getParameter("fid");
	           Faculty ftd = facultyService.searchFaculty(fid);
	           
	           if (ftd != null) {
	               resp.setStatus(HttpServletResponse.SC_CONFLICT); // 409 Conflict
	               resp.getWriter().write("exists");
	               return; // Exit the method
	           }

	           // Get parameters
	           firstname = req.getParameter("firstname");
	           lastname = req.getParameter("lastname");
	           address = req.getParameter("address");
	           gender = req.getParameter("gender");
	           email = req.getParameter("email");
	           mobile = req.getParameter("mobile");
	           course = req.getParameter("course");
	           branch = req.getParameter("branch");
	           designation = req.getParameter("designation");
	           qualification = req.getParameter("qualification");
	           salary = req.getParameter("salary");
	           city = req.getParameter("city");
	           pincode = req.getParameter("pin");
	           fstate = req.getParameter("state");
	           joining = req.getParameter("joining");
	           
	           // Validate email format
	           if (email == null || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
	               resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
	               resp.getWriter().write("Please enter a valid email address");
	               return;
	           }
	           
	           // Validate mobile number
	           if (mobile == null || !mobile.matches("^\\d{10}$")) {
	               resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
	               resp.getWriter().write("Please enter a valid 10-digit mobile number");
	               return;
	           }
	           
	           // Validate date format
	           if (joining == null || !joining.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
	               resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
	               resp.getWriter().write("Please enter date in YYYY-MM-DD format");
	               return;
	           }
	           
	           // Validate required fields
	           if (firstname == null || firstname.trim().isEmpty() ||
	               lastname == null || lastname.trim().isEmpty() ||
	               address == null || address.trim().isEmpty() ||
	               gender == null || gender.trim().isEmpty() ||
	               course == null || course.trim().isEmpty() ||
	               branch == null || branch.trim().isEmpty() ||
	               city == null || city.trim().isEmpty() ||
	               pincode == null || pincode.trim().isEmpty() ||
	               fstate == null || fstate.trim().isEmpty()) {
	               
	               resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
	               resp.getWriter().write("Please fill all required fields");
	               return;
	           }
	           
	           // If validation passes, create faculty object and add to database
	           faculty = new Faculty();
	           faculty.setFid(fid);
	           faculty.setFirstname(firstname);
	           faculty.setLastname(lastname);
	           faculty.setAddress(address);
	           faculty.setGender(gender);
	           faculty.setEmail(email);
	           faculty.setMobile(mobile);
	           faculty.setCourse(course);
	           faculty.setBranch(branch);
	           faculty.setDesignation(designation);
	           faculty.setQualification(qualification);
	           faculty.setSalary(salary);
	           faculty.setCity(city);
	           faculty.setPincode(pincode);
	           faculty.setState(fstate);
	           faculty.setJoin(joining);
	           
	           status1 = facultyService.addFaculty(faculty);
	           
	           if (status1.equalsIgnoreCase("success")) {
	               resp.getWriter().write("success");
	           } else {
	               resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
	               resp.getWriter().write("failure");
	           }
	       }
	       
	       
	       if (requestURI.endsWith("search1.do")) {
	    	    fid = req.getParameter("fid");
	    	    faculty = facultyService.searchFaculty(fid);
	    	    
	    	    if(faculty == null) {
	    	        resp.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
	    	        resp.getWriter().write("Faculty not found");
	    	    } else {

	    	        resp.setContentType("text/html");
	    	        
	    	        out.println("<!DOCTYPE html>");
	    	        out.println("<html lang=\"en\">");
	    	        out.println("<head>");
	    	        out.println("    <meta charset=\"UTF-8\">");
	    	        out.println("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
	    	        out.println("    <title>Faculty Information</title>");
	    	        out.println("    <style>");
	    	        out.println("        body {");
	    	        out.println("            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;");
	    	        out.println("            background-color: #f5f5f5;");
	    	        out.println("            margin: 0;");
	    	        out.println("            padding: 20px;");
	    	        out.println("            color: #333;");
	    	        out.println("        }");
	    	        out.println("        .container {");
	    	        out.println("            max-width: 800px;");
	    	        out.println("            margin: 0 auto;");
	    	        out.println("            background-color: #fff;");
	    	        out.println("            border-radius: 8px;");
	    	        out.println("            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);");
	    	        out.println("            padding: 20px;");
	    	        out.println("        }");
	    	        out.println("        h2 {");
	    	        out.println("            color: #2c3e50;");
	    	        out.println("            text-align: center;");
	    	        out.println("            margin-bottom: 20px;");
	    	        out.println("            padding-bottom: 10px;");
	    	        out.println("            border-bottom: 2px solid #3498db;");
	    	        out.println("        }");
	    	        out.println("        .info-grid {");
	    	        out.println("            display: grid;");
	    	        out.println("            grid-template-columns: repeat(2, 1fr);");
	    	        out.println("            gap: 20px;");
	    	        out.println("        }");
	    	        out.println("        .info-section {");
	    	        out.println("            margin-bottom: 20px;");
	    	        out.println("            border: 1px solid #e0e0e0;");
	    	        out.println("            border-radius: 5px;");
	    	        out.println("            overflow: hidden;");
	    	        out.println("        }");
	    	        out.println("        .section-title {");
	    	        out.println("            background-color: #3498db;");
	    	        out.println("            color: white;");
	    	        out.println("            padding: 10px;");
	    	        out.println("            margin: 0;");
	    	        out.println("            font-size: 16px;");
	    	        out.println("        }");
	    	        out.println("        .info-table {");
	    	        out.println("            width: 100%;");
	    	        out.println("            border-collapse: collapse;");
	    	        out.println("        }");
	    	        out.println("        .info-table tr:nth-child(even) {");
	    	        out.println("            background-color: #f9f9f9;");
	    	        out.println("        }");
	    	        out.println("        .info-table td {");
	    	        out.println("            padding: 10px;");
	    	        out.println("            border-bottom: 1px solid #eee;");
	    	        out.println("        }");
	    	        out.println("        .info-table td:first-child {");
	    	        out.println("            font-weight: bold;");
	    	        out.println("            width: 40%;");
	    	        out.println("            color: #555;");
	    	        out.println("        }");
	    	        out.println("        .action-btn {");
	    	        out.println("            display: inline-block;");
	    	        out.println("            min-width: 150px;");
	    	        out.println("            margin: 20px auto;");
	    	        out.println("            padding: 10px 15px;");
	    	        out.println("            background-color: #2c3e50;");
	    	        out.println("            color: white;");
	    	        out.println("            text-align: center;");
	    	        out.println("            text-decoration: none;");
	    	        out.println("            border-radius: 5px;");
	    	        out.println("            border: none;");
	    	        out.println("            cursor: pointer;");
	    	        out.println("            font-size: 16px;");
	    	        out.println("            transition: background-color 0.3s;");
	    	        out.println("        }");
	    	        out.println("        .action-btn:hover {");
	    	        out.println("            background-color: #1a252f;");
	    	        out.println("        }");
	    	        out.println("        .btn-container {");
	    	        out.println("            text-align: center;");
	    	        out.println("            margin-top: 20px;");
	    	        out.println("        }");
	    	        out.println("        .action-btn a {");
	    	        out.println("            color: white;");
	    	        out.println("            text-decoration: none;");
	    	        out.println("            display: block;");
	    	        out.println("        }");
	    	        out.println("    </style>");
	    	        out.println("</head>");
	    	        out.println("<body>");
	    	        out.println("    <div class=\"container\">");
	    	        out.println("        <h2>Faculty Information Data</h2>");
	    	        out.println("        ");
	    	        out.println("        <div class=\"info-grid\">");
	    	        out.println("            <div class=\"info-section\">");
	    	        out.println("                <h3 class=\"section-title\">Personal Information</h3>");
	    	        out.println("                <table class=\"info-table\">");
	    	        out.println("                    <tr><td>Faculty ID</td><td>" + faculty.getFid() + "</td></tr>");
	    	        out.println("                    <tr><td>First Name</td><td>" + faculty.getFirstname() + "</td></tr>");
	    	        out.println("                    <tr><td>Last Name</td><td>" + faculty.getLastname() + "</td></tr>");
	    	        out.println("                    <tr><td>Gender</td><td>" + faculty.getGender() + "</td></tr>");
	    	        out.println("                    <tr><td>Email</td><td>" + faculty.getEmail() + "</td></tr>");
	    	        out.println("                    <tr><td>Mobile Number</td><td>" + faculty.getMobile() + "</td></tr>");
	    	        out.println("                </table>");
	    	        out.println("            </div>");
	    	        out.println("            ");
	    	        out.println("            <div class=\"info-section\">");
	    	        out.println("                <h3 class=\"section-title\">Professional Details</h3>");
	    	        out.println("                <table class=\"info-table\">");
	    	        out.println("                    <tr><td>Designation</td><td>" + faculty.getDesignation() + "</td></tr>");
	    	        out.println("                    <tr><td>Qualification</td><td>" + faculty.getQualification() + "</td></tr>");
	    	        out.println("                    <tr><td>Course</td><td>" + faculty.getCourse() + "</td></tr>");
	    	        out.println("                    <tr><td>Branch</td><td>" + faculty.getBranch() + "</td></tr>");
	    	        out.println("                    <tr><td>Salary</td><td>" + faculty.getSalary() + "</td></tr>");
	    	        out.println("                    <tr><td>Joining Date</td><td>" + faculty.getJoin() + "</td></tr>");
	    	        out.println("                </table>");
	    	        out.println("            </div>");
	    	        out.println("            ");
	    	        out.println("            <div class=\"info-section\">");
	    	        out.println("                <h3 class=\"section-title\">Address Information</h3>");
	    	        out.println("                <table class=\"info-table\">");
	    	        out.println("                    <tr><td>Address</td><td>" + faculty.getAddress() + "</td></tr>");
	    	        out.println("                    <tr><td>City</td><td>" + faculty.getCity() + "</td></tr>");
	    	        out.println("                    <tr><td>Pin Code</td><td>" + faculty.getPincode() + "</td></tr>");
	    	        out.println("                    <tr><td>Province</td><td>" + faculty.getCity() + "</td></tr>");
	    	        out.println("                </table>");
	    	        out.println("            </div>");
	    	        out.println("        </div>");
	    	        out.println("        ");
	    	        out.println("        <div class=\"btn-container\">");
	    	        out.println("            <a href=\"College.jsp\" class=\"action-btn\">Back to Menu</a>");
	    	        out.println("            <button id=\"printBtn\" class=\"action-btn\">Print Information</button>");
	    	        out.println("        </div>");
	    	        out.println("    </div>");
	    	        out.println("    ");
	    	        out.println("    <script>");
	    	        out.println("        document.getElementById('printBtn').addEventListener('click', function() {");
	    	        out.println("            window.print();");
	    	        out.println("        });");
	    	        out.println("    </script>");
	    	        out.println("</body>");
	    	        out.println("</html>");
	    	    }
	    	}
	       
	       
	    // For edit1.do endpoint - fix the gender/address field swap
	       if (requestURI.endsWith("edit1.do")) {
	           fid = req.getParameter("fid");
	           faculty = facultyService.searchFaculty(fid);
	           if (faculty == null) {
	               resp.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
	               resp.setContentType("application/json");
	               resp.getWriter().write("{\"error\": \"Faculty not found\"}");
	           } else {
	               out.println("<html>");
	               out.println("<head>");
	               out.println("<meta charset=\"UTF-8\" />");
	               out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />");
	               out.println("<title>Update Faculty</title>");
	               out.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" />");
	               out.println("<style>");
	               out.println("body { background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%); min-height: 100vh; padding: 20px; }");
	               out.println(".card { max-width: 800px; margin: 0 auto; border-radius: 15px; box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1); }");
	               out.println(".form-label { font-weight: 600; }");
	               out.println(".btn-primary { background-color: #6a11cb; border: none; }");
	               out.println(".btn-primary:hover { background-color: #5900b3; }");
	               out.println(".toast-container { position: fixed; top: 1rem; right: 1rem; z-index: 9999; }");
	               out.println("</style>");
	               out.println("</head>");
	               out.println("<body>");
	               out.println("<div class=\"container py-4\">");
	               out.println("<div class=\"card p-4\">");
	               out.println("<h2 class=\"text-center mb-4\">Update Faculty Details</h2>");
	               out.println("<form method='post' action='update1.do' class=\"row g-3\">");
	               
	               // Begin form - corrected gender/address fields
	               out.println("<div class=\"col-md-6 mb-3\">");
	               out.println("<label for=\"fid\" class=\"form-label\">Faculty ID</label>");
	               out.println("<input type='text' class=\"form-control\" id=\"fid\" name='fid' value='" + faculty.getFid() + "' readonly='readonly'>");
	               out.println("</div>");
	               
	               out.println("<div class=\"col-md-6 mb-3\">");
	               out.println("<label for=\"firstname\" class=\"form-label\">First Name</label>");
	               out.println("<input type='text' class=\"form-control\" id=\"firstname\" name='firstname' value='" + faculty.getFirstname() + "'>");
	               out.println("</div>");
	               
	               out.println("<div class=\"col-md-6 mb-3\">");
	               out.println("<label for=\"lastname\" class=\"form-label\">Last Name</label>");
	               out.println("<input type='text' class=\"form-control\" id=\"lastname\" name='lastname' value='" + faculty.getLastname() + "'>");
	               out.println("</div>");
	               
	               out.println("<div class=\"col-md-6 mb-3\">");
	               out.println("<label for=\"gender\" class=\"form-label\">Gender</label>");
	               out.println("<input type='text' class=\"form-control\" id=\"gender\" name='gender' value='" + faculty.getGender() + "'>");
	               out.println("</div>");
	               
	               out.println("<div class=\"col-md-6 mb-3\">");
	               out.println("<label for=\"address\" class=\"form-label\">Address</label>");
	               out.println("<input type='text' class=\"form-control\" id=\"address\" name='address' value='" + faculty.getAddress() + "'>");
	               out.println("</div>");
	               
	               out.println("<div class=\"col-md-6 mb-3\">");
	               out.println("<label for=\"email\" class=\"form-label\">Email</label>");
	               out.println("<input type='email' class=\"form-control\" id=\"email\" name='email' value='" + faculty.getEmail() + "'>");
	               out.println("</div>");
	               
	               out.println("<div class=\"col-md-6 mb-3\">");
	               out.println("<label for=\"mobile\" class=\"form-label\">Mobile</label>");
	               out.println("<input type='text' class=\"form-control\" id=\"mobile\" name='mobile' value='" + faculty.getMobile() + "'>");
	               out.println("</div>");
	               
	               out.println("<div class=\"col-md-6 mb-3\">");
	               out.println("<label for=\"course\" class=\"form-label\">Course</label>");
	               out.println("<input type='text' class=\"form-control\" id=\"course\" name='course' value='" + faculty.getCourse() + "'>");
	               out.println("</div>");
	               
	               out.println("<div class=\"col-md-6 mb-3\">");
	               out.println("<label for=\"branch\" class=\"form-label\">Branch</label>");
	               out.println("<input type='text' class=\"form-control\" id=\"branch\" name='branch' value='" + faculty.getBranch() + "'>");
	               out.println("</div>");
	               
	               out.println("<div class=\"col-md-6 mb-3\">");
	               out.println("<label for=\"designation\" class=\"form-label\">Designation</label>");
	               out.println("<input type='text' class=\"form-control\" id=\"designation\" name='designation' value='" + faculty.getDesignation() + "'>");
	               out.println("</div>");
	               
	               out.println("<div class=\"col-md-6 mb-3\">");
	               out.println("<label for=\"qualification\" class=\"form-label\">Qualification</label>");
	               out.println("<input type='text' class=\"form-control\" id=\"qualification\" name='qualification' value='" + faculty.getQualification() + "'>");
	               out.println("</div>");
	               
	               out.println("<div class=\"col-md-6 mb-3\">");
	               out.println("<label for=\"salary\" class=\"form-label\">Salary</label>");
	               out.println("<input type='text' class=\"form-control\" id=\"salary\" name='salary' value='" + faculty.getSalary() + "'>");
	               out.println("</div>");
	               
	               out.println("<div class=\"col-md-6 mb-3\">");
	               out.println("<label for=\"city\" class=\"form-label\">City</label>");
	               out.println("<input type='text' class=\"form-control\" id=\"city\" name='city' value='" + faculty.getCity() + "'>");
	               out.println("</div>");
	               
	               out.println("<div class=\"col-md-6 mb-3\">");
	               out.println("<label for=\"pin\" class=\"form-label\">Pin Code</label>");
	               out.println("<input type='text' class=\"form-control\" id=\"pin\" name='pin' value='" + faculty.getPincode() + "'>");
	               out.println("</div>");
	               
	               out.println("<div class=\"col-md-6 mb-3\">");
	               out.println("<label for=\"state\" class=\"form-label\">Province</label>");
	               out.println("<input type='text' class=\"form-control\" id=\"state\" name='state' value='" + faculty.getState() + "'>");
	               out.println("</div>");
	               
	               out.println("<div class=\"col-md-6 mb-3\">");
	               out.println("<label for=\"joining\" class=\"form-label\">Joining Date</label>");
	               out.println("<input type='date' class=\"form-control\" id=\"joining\" name='joining' value='" + faculty.getJoin() + "'>");
	               out.println("</div>");
	               
	               out.println("<div class=\"col-12 mt-3\">");
	               out.println("<input type='submit' class=\"btn btn-primary\" value='UPDATE'>");
	               out.println("</div>");
	               
	               out.println("</form>");
	               out.println("</div>");
	               out.println("</div>");
	               
	               // Toast container for update notifications
	               out.println("<div class=\"toast-container\">");
	               out.println("<div id=\"msgToast\" class=\"toast align-items-center text-white bg-danger border-0\" role=\"alert\">");
	               out.println("<div class=\"d-flex\">");
	               out.println("<div class=\"toast-body\" id=\"toastMessage\"></div>");
	               out.println("<button type=\"button\" class=\"btn-close btn-close-white me-2 m-auto\" data-bs-dismiss=\"toast\"></button>");
	               out.println("</div>");
	               out.println("</div>");
	               out.println("</div>");
	               
	               // Scripts
	               out.println("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js\"></script>");
	               out.println("<script src=\"https://code.jquery.com/jquery-3.6.0.min.js\"></script>");
	               out.println("</body></html>");
	           }
	       }

	       // For update1.do endpoint - use update method instead of addFaculty
	       if (requestURI.endsWith("update1.do")) {
	           // Get updated faculty details from the form
	           fid = req.getParameter("fid");
	           firstname = req.getParameter("firstname");
	           lastname = req.getParameter("lastname");
	           gender = req.getParameter("gender"); // Note: These are now correctly mapped
	           address = req.getParameter("address"); // Note: These are now correctly mapped
	           email = req.getParameter("email");
	           mobile = req.getParameter("mobile");
	           course = req.getParameter("course");
	           branch = req.getParameter("branch");
	           designation = req.getParameter("designation");
	           qualification = req.getParameter("qualification");
	           salary = req.getParameter("salary");
	           city = req.getParameter("city");
	           pincode = req.getParameter("pin");
	           fstate = req.getParameter("state");
	           joining = req.getParameter("joining");

	           // Validate required fields and formats
	           boolean hasValidationErrors = false;
	           String errorMessage = "";

	           // Validate email format
	           if (email == null || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
	               hasValidationErrors = true;
	               errorMessage = "Please enter a valid email address";
	           }
	           
	           // Validate mobile number
	           else if (mobile == null || !mobile.matches("^\\d{10}$")) {
	               hasValidationErrors = true;
	               errorMessage = "Please enter a valid 10-digit mobile number";
	           }
	           
	           // Validate date format
	           else if (joining == null || !joining.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
	               hasValidationErrors = true;
	               errorMessage = "Please enter date in YYYY-MM-DD format";
	           }
	           
	           // Validate required fields
	           else if (firstname == null || firstname.trim().isEmpty() ||
	               lastname == null || lastname.trim().isEmpty() ||
	               address == null || address.trim().isEmpty() ||
	               gender == null || gender.trim().isEmpty() ||
	               course == null || course.trim().isEmpty() ||
	               branch == null || branch.trim().isEmpty() ||
	               city == null || city.trim().isEmpty() ||
	               pincode == null || pincode.trim().isEmpty() ||
	               fstate == null || fstate.trim().isEmpty()) {
	               
	               hasValidationErrors = true;
	               errorMessage = "Please fill all required fields";
	           }
	           
	           // If validation errors exist, return error response
	           if (hasValidationErrors) {
	               resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
	               resp.getWriter().write(errorMessage);
	               return;
	           }
	           
	           try {
	               // If validation passes, create faculty object and update in database
	               faculty = new Faculty();
	               faculty.setFid(fid);
	               faculty.setFirstname(firstname);
	               faculty.setLastname(lastname);
	               faculty.setGender(gender);
	               faculty.setAddress(address);
	               faculty.setEmail(email);
	               faculty.setMobile(mobile);
	               faculty.setCourse(course);
	               faculty.setBranch(branch);
	               faculty.setDesignation(designation);
	               faculty.setQualification(qualification);
	               faculty.setSalary(salary);
	               faculty.setCity(city);
	               faculty.setPincode(pincode);
	               faculty.setState(fstate);
	               faculty.setJoin(joining);
	               
	               // Call update method instead of addFaculty
	               status1 = facultyService.updateFaculty(faculty);
	               
	               if (status1.equalsIgnoreCase("success")) {
	                   resp.getWriter().write("success");
	               } else {
	                   resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
	                   resp.getWriter().write("failure");
	               }
	           } catch (Exception e) {
	               resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	               if (e.getMessage() != null && e.getMessage().contains("Data truncation")) {
	                   resp.getWriter().write("One or more fields contain data that is too long for the database");
	               } else {
	                   resp.getWriter().write("An error occurred: " + e.getMessage());
	               }
	               e.printStackTrace();
	           }
	       }
	   
	    // This is the updated controller code for handling faculty deletion

	       if (requestURI.endsWith("delete1.do")) {
	           fid = req.getParameter("fid");
	           
	           // Debug statement
	           System.out.println("Attempting to delete faculty with ID: " + fid);
	           
	            faculty = facultyService.searchFaculty(fid);
	           
	           if (faculty == null) {
	               // Debug statement
	               System.out.println("Faculty with ID " + fid + " not found");
	               resp.sendRedirect("FacultyDelete.html?status=notFound");
	           } else {
	               // Debug statement
	               System.out.println("Faculty with ID " + fid + " found, proceeding with deletion");
	               
	                status1 = facultyService.deleteFaculty(fid);
	               
	               // Debug statement
	               System.out.println("Deletion result: " + status1);
	               
	               if (status1.equalsIgnoreCase("success")) {
	                   resp.sendRedirect("FacultyDelete.html?status=success");
	                   // If you want to redirect to College.jsp instead, use:
	                   // resp.sendRedirect("College.jsp?status=success");
	               } else {
	                   resp.sendRedirect("FacultyDelete.html?status=failure");
	               }
	           }
	       }
	       
	       
	       
	       
	 
	   }
	
	}
