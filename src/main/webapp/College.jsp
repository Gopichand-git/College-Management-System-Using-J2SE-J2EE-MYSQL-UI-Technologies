<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Gopichand Engineering College</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet"/>
    <style>
        :root {
            --primary-color: #4b07f8;
            --hover-color: #3a05c5;
            --text-light: #f1f1f1;
            --text-dark: #212529;
            --transition-speed: 0.3s;
        }

        body {
            background-color: #f9f9f9;
            color: var(--text-dark);
            margin: 0;
            padding: 0;
            overflow-x: hidden;
            transition: background-color var(--transition-speed);
        }

        /* Dark Mode Styles */
        .dark-mode {
            background-color: #121212 !important;
            color: var(--text-light) !important;
        }

        .dark-mode .sidebar {
            background-color: #1f1f1f !important;
            color: var(--text-light) !important;
            border-right: 1px solid #333;
        }

        .dark-mode .nav-link {
            color: var(--text-light) !important;
        }

        .dark-mode .nav-link:hover {
            background-color: #333 !important;
        }

        .dark-mode .form-control {
            background-color: #2c2c2c !important;
            color: var(--text-light) !important;
            border: 1px solid #444 !important;
        }

        .dark-mode .main-content {
            background-color: #121212;
        }

        .dark-mode .mobile-menu-toggle {
            color: #fff;
        }

        .dark-mode .card {
            background-color: #1f1f1f !important;
            color: var(--text-light) !important;
            border: 1px solid #333;
        }

        .dark-mode .stats-card {
            background-color: #2c2c2c !important;
        }

        .dark-mode .dropdown-menu {
            background-color: #2c2c2c !important;
            border: 1px solid #444 !important;
        }

        .dark-mode .dropdown-item {
            color: var(--text-light) !important;
        }

        .dark-mode .dropdown-item:hover {
            background-color: #333 !important;
        }

        /* Header Styles */
        #div1 {
            background-color: var(--primary-color);
            text-align: center;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            position: relative;
            color: white;
            padding: 20px 10px;
            transition: all var(--transition-speed);
            margin-top: -180px;
        }

        #h1 {
            font-size: 50px;
            font-weight: bold;
            font-family: 'Times New Roman', Times, serif;
            color: var(--text-light);
            transition: font-size var(--transition-speed);
            text-shadow: red;
        }

        #h4 {
            color: red;
            font-size: 23px;
            font-weight: bold;
            margin-top: -10px;
            transition: font-size var(--transition-speed);
        }

        #h5 {
            font-size: 20px;
            color: var(--text-light);
            margin-top: -5px;
            transition: font-size var(--transition-speed);
        }

        /* Profile Header */
        .profile-header {
            position: absolute;
            top: 25px;
            right: 10px;
            display: flex;
            align-items: center;
            gap: 10px;
            transition: all var(--transition-speed);
        }

        .profile-header img {
            width: 45px;
            height: 45px;
            border-radius: 50%;
            object-fit: cover;
            border: 2px solid white;
            transition: all var(--transition-speed);
        }

        .profile-header img:hover {
            transform: scale(1.1);
        }

        .profile-header span {
            font-weight: bold;
            color: white;
        }

        .theme-toggle {
            background-color: #fff;
            border: none;
            font-size: 20px;
            cursor: pointer;
            border-radius: 50%;
            padding: 6px 10px;
            transition: all 0.3s ease;
        }

        .theme-toggle:hover {
            transform: rotate(45deg);
        }

        /* Sidebar Styles */
        .sidebar {
            background-color: #f8f9fa;
            color: var(--text-dark);
            height: 100vh;
            padding: 20px;
            position: fixed;
            width: 290px;
            border-right: 1px solid #dee2e6;
            z-index: 100;
            overflow-y: auto;
            transition: all var(--transition-speed);
            box-shadow: 2px 0 5px rgba(0,0,0,0.1);
        }

        .sidebar h4 {
            font-weight: bold;
            margin-bottom: 20px;
            display: flex;
            align-items: center;
        }

        .sidebar h4::before {
            content: "🎓";
            margin-right: 8px;
            font-size: 1.2rem;
        }

        .sidebar .form-control {
            border-radius: 25px;
            transition: all 0.3s;
        }

        .sidebar .form-control:focus {
            border-color: var(--primary-color);
            box-shadow: 0 0 0 0.25rem rgba(75, 7, 248, 0.25);
        }

        .nav-link {
            color: var(--text-dark);
            border-radius: 10px;
            padding: 12px 15px;
            margin-bottom: 5px;
            transition: all 0.3s ease;
            display: flex;
            align-items: center;
        }

        .nav-link i {
            margin-right: 10px;
            font-size: 1.1rem;
        }

        .nav-link:hover {
            background-color: rgba(75, 7, 248, 0.1);
            color: var(--primary-color);
            transform: translateX(5px);
        }

        .nav-link.active {
            background-color: var(--primary-color);
            color: white !important;
        }

        /* Main Content */
        .main-content {
            transition: margin-left var(--transition-speed);
            padding: 20px;
            margin-top: 180px;
        }

        /* Mobile Menu Toggle */
        .mobile-menu-toggle {
            display: none;
            background: transparent;
            border: none;
            font-size: 24px;
            cursor: pointer;
            padding: 10px;
            transition: all 0.3s;
            z-index: 101;
        }

        .mobile-menu-toggle:hover {
            transform: scale(1.1);
        }

        /* Sidebar Backdrop */
        .sidebar-backdrop {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            z-index: 99;
            transition: opacity 0.3s;
        }

        /* Dashboard Cards */
        .card {
            border-radius: 15px;
            overflow: hidden;
            box-shadow: 0 4px 12px rgba(0,0,0,0.08);
            transition: all 0.3s ease;
            border: none;
        }

        .card:hover {
            transform: translateY(-10px);
            box-shadow: 0 8px 24px rgba(0,0,0,0.15);
        }

        .card-title {
            font-weight: bold;
            color: var(--primary-color);
        }

        .card .btn-primary {
            background-color: var(--primary-color);
            border: none;
            border-radius: 30px;
            padding: 8px 20px;
            transition: all 0.3s ease;
        }

        .card .btn-primary:hover {
            background-color: var(--hover-color);
            transform: scale(1.05);
        }

        /* Stats Cards */
        .stats-card {
            background: linear-gradient(45deg, var(--primary-color), #6a3bf5);
            color: white;
            border-radius: 15px;
            padding: 20px;
            margin-bottom: 30px;
            position: relative;
            overflow: hidden;
            transition: all 0.3s ease;
        }

        .stats-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 24px rgba(75, 7, 248, 0.3);
        }

        .stats-card i {
            position: absolute;
            top: 20px;
            right: 20px;
            font-size: 3rem;
            opacity: 0.2;
        }

        .stats-card .count {
            font-size: 2.5rem;
            font-weight: bold;
        }

        .stats-card .label {
            font-size: 1rem;
            opacity: 0.8;
        }

        /* Animations */
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .animated-card {
            animation: fadeIn 0.5s ease-out forwards;
            opacity: 0;
        }

        .animated-card:nth-child(1) { animation-delay: 0.1s; }
        .animated-card:nth-child(2) { animation-delay: 0.2s; }
        .animated-card:nth-child(3) { animation-delay: 0.3s; }
        .animated-card:nth-child(4) { animation-delay: 0.4s; }

        /* Quick Access Menu */
        .quick-access {
            display: none;
            position: fixed;
            bottom: 20px;
            right: 20px;
            z-index: 99;
        }

        .quick-access-btn {
            width: 50px;
            height: 50px;
            background-color: var(--primary-color);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 1.5rem;
            cursor: pointer;
            box-shadow: 0 4px 12px rgba(0,0,0,0.2);
            transition: all 0.3s ease;
        }

        .quick-access-btn:hover {
            transform: scale(1.1);
            background-color: var(--hover-color);
        }

        .quick-access-menu {
            position: absolute;
            bottom: 60px;
            right: 0;
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.2);
            padding: 10px;
            width: 200px;
            display: none;
        }

        .dark-mode .quick-access-menu {
            background-color: #1f1f1f;
            border: 1px solid #333;
        }

        .quick-access-item {
            padding: 8px 12px;
            border-radius: 5px;
            transition: all 0.3s ease;
            cursor: pointer;
            display: flex;
            align-items: center;
        }

        .quick-access-item i {
            margin-right: 10px;
        }

        .quick-access-item:hover {
            background-color: rgba(75, 7, 248, 0.1);
            color: var(--primary-color);
        }

        .quick-access-menu.show {
            display: block;
            animation: fadeIn 0.3s ease-out forwards;
        }

        /* Recent Activities Section */
        .recent-activity {
            margin-top: 30px;
            padding: 20px;
            background-color: white;
            border-radius: 15px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.08);
        }

        .dark-mode .recent-activity {
            background-color: #1f1f1f;
        }

        .activity-item {
            display: flex;
            align-items: center;
            padding: 12px 0;
            border-bottom: 1px solid #eee;
        }

        .dark-mode .activity-item {
            border-bottom: 1px solid #333;
        }

        .activity-item:last-child {
            border-bottom: none;
        }

        .activity-icon {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            background-color: #e9ecef;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: 15px;
        }

        .dark-mode .activity-icon {
            background-color: #2c2c2c;
        }

        .activity-content {
            flex: 1;
        }

        .activity-time {
            color: #6c757d;
            font-size: 0.8rem;
        }

        /* Responsive Styles */
        /* Large devices (laptops/desktops, 992px and up) */
        @media (min-width: 992px) {
            .main-content {
                margin-left: 260px;
                margin-top: 180px;
            }
            
            .sidebar {
                margin-top: 180px;
                top: 0;
                left: 0;
            }
            
            #div1 {
                height: 180px;
                position: fixed;
                width: 100%;
                z-index: 99;
            }
            
            #h1 {
                font-size: 50px;
            }
            
            #h4 {
                font-size: 23px;
            }
            
            #h5 {
                font-size: 20px;
            }
        }

        /* Medium devices (tablets, 768px to 991px) */
        @media (min-width: 768px) and (max-width: 991px) {
            .sidebar {
                width: 220px;
                margin-top: 160px;
                top: 0;
                left: 0;
            }
            
            .main-content {
                margin-left: 220px;
                margin-top: 160px;
            }
            
            #div1 {
                height: 160px;
                position: fixed;
                width: 100%;
                z-index: 99;
            }
            
            #h1 {
                font-size: 38px;
            }
            
            #h4 {
                font-size: 18px;
            }
            
            #h5 {
                font-size: 16px;
            }
            
            .profile-header img {
                width: 40px;
                height: 40px;
            }
            
            .quick-access {
                display: block;
            }
        }

        /* Small devices (mobile phones, less than 768px) */
        @media (max-width: 767px) {
            .sidebar {
                width: 260px;
                transform: translateX(-100%);
                margin-top: 140px;
                top: 0;
                left: 0;
            }
            
            .sidebar.active {
                transform: translateX(0);
            }
            
            .main-content {
                margin-left: 0;
                padding: 15px;
                margin-top: 140px;
            }
            
            .mobile-menu-toggle {
                display: block;
                position: fixed;
                top: 15px;
                left: 15px;
                z-index: 101;
                color: #fff;
            }
            
            .sidebar-backdrop.active {
                display: block;
            }
            
            #div1 {
                height: 140px;
                padding: 20px 10px 15px;
                position: fixed;
                width: 100%;
                z-index: 98;
                margin-top: -150px;
            }
            
            #h1 {
                font-size: 24px;
                margin-top: 20px;
            }
            
            #h4 {
                font-size: 12px;
                margin-top: 5px;
            }
            
            #h5 {
                font-size: 10px;
                margin-top: 0;
            }
            
            .profile-header {
                position: absolute;
                top: 10px;
                right: 10px;
            }
            
            .profile-header img {
                width: 35px;
                height: 35px;
            }
            
            .profile-header span {
                font-size: 12px;
                display: none;
            }
            
            .quick-access {
                display: block;
            }
            
            .card {
                margin-bottom: 15px;
            }
            
            .stats-card .count {
                font-size: 2rem;
            }
        }
        
        /* Extra small devices (phones less than 576px) */
        @media (max-width: 575px) {
            #h1 {
                font-size: 20px;
            }
            
            #h4 {
                font-size: 10px;
            }
            
            #h5 {
                font-size: 9px;
            }
            
            .theme-toggle, .btn-sm {
                padding: 4px 8px;
                font-size: 12px;
            }
        }
    </style>
</head>
<body>

<!-- Mobile Menu Toggle -->
<button class="mobile-menu-toggle" id="sidebarToggle">
    <i class="bi bi-list"></i>
</button>

<!-- Backdrop for mobile sidebar -->
<div class="sidebar-backdrop" id="sidebarBackdrop"></div>

<!-- Header -->
<div id="div1">
    <h1 id="h1">GOPICHAND ENGINEERING COLLEGE</h1>
    <h4 id="h4">(Autonomous Institute with Permanent Affiliation to JNTUK, Kakinada)</h4>
    <h5 id="h5">Approved by AICTE New Delhi | Permitted by AP State Government</h5>

    <!-- Profile & Dark Mode Toggle -->
    <div class="profile-header">
        <img src="<%= session.getAttribute("imageUrl") != null ? session.getAttribute("imageUrl") : "chandu.jpg" %>" alt="Profile"/>
        <span><%= session.getAttribute("name") != null ? session.getAttribute("name") : "Admin" %></span>
        <button class="theme-toggle" id="themeToggle" title="Toggle Dark Mode">
            <i class="bi bi-moon-fill text-dark"></i>
        </button>
        <form action="logout" method="post" style="margin: 0;">
            <button type="submit" class="btn btn-sm btn-warning">
                <i class="bi bi-box-arrow-right text-dark fw-bold"></i>
            </button>
        </form>
    </div>
</div>

<!-- Sidebar -->
<div class="sidebar" id="sidebar">
    <h4>GEC</h4>
    <div class="input-group mb-3">
        <span class="input-group-text"><i class="bi bi-search"></i></span>
        <input type="text" class="form-control" placeholder="Search..." id="searchInput"/>
    </div>

    <ul class="nav nav-pills flex-column mb-auto">
        <li><a href="dashboardServlet" class="nav-link active"><i class="bi bi-speedometer2"></i> Dashboard</a></li>
        <li><a class="nav-link" data-bs-toggle="collapse" href="#studentMenu"><i class="bi bi-person-lines-fill"></i> Student Module</a></li>
        <div class="collapse" id="studentMenu">
            <a class="nav-link ps-4" href="addform.html"><i class="bi bi-plus-circle"></i> Add Student</a>
            <a class="nav-link ps-4" href="searchform.html"><i class="bi bi-search"></i> Search Student</a>
            <a class="nav-link ps-4" href="updateform.html"><i class="bi bi-pencil-square"></i> Update Student</a>
            <a class="nav-link ps-4" href="deleteform.html"><i class="bi bi-trash"></i> Delete Student</a>
            <a class="nav-link ps-4" href="Total-Students.html"><i class="bi bi-people"></i> Total Students</a>
        </div>
        <li><a class="nav-link" data-bs-toggle="collapse" href="#facultyMenu"><i class="bi bi-person-badge"></i> Faculty Module</a></li>
        <div class="collapse" id="facultyMenu">
            <a class="nav-link ps-4" href="FacultyForm.html"><i class="bi bi-plus-circle"></i> Add Faculty</a>
            <a class="nav-link ps-4" href="FacultySearch.html"><i class="bi bi-search"></i> Search Faculty</a>
            <a class="nav-link ps-4" href="FacultyUpdate.html"><i class="bi bi-pencil-square"></i> Update Faculty</a>
            <a class="nav-link ps-4" href="FacultyDelete.html"><i class="bi bi-trash"></i> Delete Faculty</a>
        </div>
        <li><a class="nav-link" data-bs-toggle="collapse" href="#feeMenu"><i class="bi bi-credit-card"></i> Fee Module</a></li>
        <div class="collapse" id="feeMenu">
            <a class="nav-link ps-4" href="FeePayment.html"><i class="bi bi-cash-coin"></i> Make Payment</a>
            <a class="nav-link ps-4" href="FeeSearch.html"><i class="bi bi-search"></i> Search Fee</a>
            <a class="nav-link ps-4" href="FeeStudentsHistory.html"><i class="bi bi-clock-history"></i> Students History</a>
            <a class="nav-link ps-4" href="FeeTransactionHistory.html"><i class="bi bi-receipt"></i> Payment History</a>
        </div>
        <li><a class="nav-link" data-bs-toggle="collapse" href="#marksMenu"><i class="bi bi-journal-text"></i> Marks Module</a></li>
        <div class="collapse" id="marksMenu">
            <a class="nav-link ps-4" href="Addmark.html"><i class="bi bi-plus-circle"></i> Add Marks</a>
            <a class="nav-link ps-4" href="Searchmark.html"><i class="bi bi-search"></i> Search Marks</a>
            <a class="nav-link ps-4" href="Updatemark.html"><i class="bi bi-pencil-square"></i> Update Marks</a>
            <a class="nav-link ps-4" href="Deletemark.html"><i class="bi bi-trash"></i> Delete Marks</a>
        </div>
        <li><a class="nav-link" data-bs-toggle="collapse" href="#attendanceMenu"><i class="bi bi-calendar-check"></i> Attendance</a></li>
        <div class="collapse" id="attendanceMenu">
            <a class="nav-link ps-4" href="Attendance.html"><i class="bi bi-check-circle"></i> Mark Attendance</a>
            <a class="nav-link ps-4" href="Attendance-Report.html"><i class="bi bi-file-text"></i> Attendance Report</a>
            <a class="nav-link ps-4" href="Attendance-Count.html"><i class="bi bi-calculator"></i> Attendance Count</a>
        </div>
        <li><a class="nav-link" href="UpdatePassword.html"><i class="bi bi-key"></i> Update Password</a></li>
        <li><a class="nav-link" href="#"><i class="bi bi-clipboard-data"></i> Admission Status</a></li>
    </ul>
</div>

<!-- Main Content -->
<div class="main-content">
    <div class="row mb-4">
        <div class="col-12">
            <div class="card">
                <div class="card-body">
                    <h2 class="text-center mb-4">Welcome to College Admin Dashboard</h2>
                    <p class="text-center">Today's date: <span id="currentDate"></span> | <span id="currentTime"></span></p>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Stats Cards -->
    <div class="row mb-4">
        <div class="col-md-3 col-sm-6 mb-3 animated-card">
            <div class="stats-card">
                <i class="bi bi-people"></i>
                <div class="count">1,245</div>
                <div class="label">Total Students</div>
            </div>
        </div>
        <div class="col-md-3 col-sm-6 mb-3 animated-card">
            <div class="stats-card">
                <i class="bi bi-person-badge"></i>
                <div class="count">78</div>
                <div class="label">Faculty Members</div>
            </div>
        </div>
        <div class="col-md-3 col-sm-6 mb-3 animated-card">
            <div class="stats-card">
                <i class="bi bi-cash-coin"></i>
                <div class="count">92%</div>
                <div class="label">Fee Collection</div>
            </div>
        </div>
        <div class="col-md-3 col-sm-6 mb-3 animated-card">
            <div class="stats-card">
                <i class="bi bi-award"></i>
                <div class="count">85%</div>
                <div class="label">Attendance Rate</div>
            </div>
        </div>
    </div>
    
    <!-- Module Cards -->
    <div class="row">
        <div class="col-lg-4 col-md-6 mb-4 animated-card">
            <div class="card h-100">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title"><i class="bi bi-people me-2"></i>Student Management</h5>
                    <p class="card-text">Access student-related functions: add, search, update, or delete student records.</p>
                    <div class="mt-auto">
                        <a href="#studentMenu" data-bs-toggle="collapse" class="btn btn-primary">
                            <i class="bi bi-arrow-right-circle me-2"></i>Student Module
                        </a>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="col-lg-4 col-md-6 mb-4 animated-card">
            <div class="card h-100">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title"><i class="bi bi-person-badge me-2"></i>Faculty Management</h5>
                    <p class="card-text">Manage faculty records: add new faculty, search, update information, or remove faculty.</p>
                    <div class="mt-auto">
                        <a href="#facultyMenu" data-bs-toggle="collapse" class="btn btn-primary">
                            <i class="bi bi-arrow-right-circle me-2"></i>Faculty Module
                        </a>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="col-lg-4 col-md-6 mb-4 animated-card">
            <div class="card h-100">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title"><i class="bi bi-credit-card me-2"></i>Fee Management</h5>
                    <p class="card-text">Handle fee-related operations: make payments, search fees, view payment history.</p>
                    <div class="mt-auto">
                        <a href="#feeMenu" data-bs-toggle="collapse" class="btn btn-primary">
                            <i class="bi bi-arrow-right-circle me-2"></i>Fee Module
                        </a>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="col-lg-6 col-md-6 mb-4 animated-card">
            <div class="card h-100">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title"><i class="bi bi-journal-text me-2"></i>Academic Management</h5>
                    <p class="card-text">Manage academic records including student marks, grades, and academic performance tracking.</p>
                    <div class="mt-auto">
                        <a href="#marksMenu" data-bs-toggle="collapse" class="btn btn-primary">
                            <i class="bi bi-arrow-right-circle me-2"></i>Marks Module
                        </a>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="col-lg-6 col-md-12 mb-4 animated-card">
            <div class="card h-100">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title"><i class="bi bi-calendar-check me-2"></i>Attendance Tracking</h5>
                    <p class="card-text">Record and monitor student attendance, generate reports, and track attendance statistics.</p>
                    <div class="mt-auto">
                        <a href="#attendanceMenu" data-bs-toggle="collapse" class="btn btn-primary">
                            <i class="bi bi-arrow-right-circle me-2"></i>Attendance Module
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Recent Activities Section -->
    <!-- Recent Activities Section -->
    <div class="recent-activity">
        <h4 class="mb-4"><i class="bi bi-activity me-2"></i>Recent Activities</h4>
        
        <div class="activity-item">
            <div class="activity-icon">
                <i class="bi bi-person-plus"></i>
            </div>
            <div class="activity-content">
                <div>New student registered - Rahul Sharma (CSE)</div>
                <div class="activity-time">Today, 10:45 AM</div>
            </div>
        </div>
        
        <div class="activity-item">
            <div class="activity-icon">
                <i class="bi bi-cash"></i>
            </div>
            <div class="activity-content">
                <div>Fee payment received - ₹45,000 (Priya Patel)</div>
                <div class="activity-time">Yesterday, 2:30 PM</div>
            </div>
        </div>
        
        <div class="activity-item">
            <div class="activity-icon">
                <i class="bi bi-file-earmark-text"></i>
            </div>
            <div class="activity-content">
                <div>Mid-semester exam results uploaded (ECE 3rd year)</div>
                <div class="activity-time">Yesterday, 11:15 AM</div>
            </div>
        </div>
        
        <div class="activity-item">
            <div class="activity-icon">
                <i class="bi bi-person-badge"></i>
            </div>
            <div class="activity-content">
                <div>New faculty joined - Dr. Anjali Verma (Physics Dept.)</div>
                <div class="activity-time">Apr 24, 2025</div>
            </div>
        </div>
    </div>
    
    <!-- Quick Stats Row -->
    <div class="row mt-4">
        <div class="col-md-4 mb-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Upcoming Events</h5>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            Annual Day Celebration
                            <span class="badge bg-primary rounded-pill">May 10</span>
                        </li>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            Tech Fest 2025
                            <span class="badge bg-primary rounded-pill">May 15-17</span>
                        </li>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            End Semester Exams
                            <span class="badge bg-danger rounded-pill">Jun 5</span>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        
        <div class="col-md-8 mb-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Department-wise Student Count</h5>
                    <canvas id="departmentChart" height="200"></canvas>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Quick Access Floating Menu -->
<div class="quick-access">
    <div class="quick-access-btn" id="quickAccessBtn">
        <i class="bi bi-lightning-charge-fill"></i>
    </div>
    <div class="quick-access-menu" id="quickAccessMenu">
        <div class="quick-access-item" onclick="window.location.href='addform.html'">
            <i class="bi bi-person-plus"></i> Add Student
        </div>
        <div class="quick-access-item" onclick="window.location.href='FacultyForm.html'">
            <i class="bi bi-person-plus-fill"></i> Add Faculty
        </div>
        <div class="quick-access-item" onclick="window.location.href='FeePayment.html'">
            <i class="bi bi-cash-coin"></i> Fee Payment
        </div>
        <div class="quick-access-item" onclick="window.location.href='Attendance.html'">
            <i class="bi bi-calendar-check"></i> Mark Attendance
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    // DOM Elements
    const body = document.body;
    const toggleBtn = document.getElementById("themeToggle");
    const sidebar = document.getElementById("sidebar");
    const sidebarToggle = document.getElementById("sidebarToggle");
    const sidebarBackdrop = document.getElementById("sidebarBackdrop");
    const searchInput = document.getElementById('searchInput');
    const navLinks = document.querySelectorAll('.sidebar .nav-link');
    const quickAccessBtn = document.getElementById('quickAccessBtn');
    const quickAccessMenu = document.getElementById('quickAccessMenu');
    const currentDateElement = document.getElementById('currentDate');
    const currentTimeElement = document.getElementById('currentTime');

    // Theme Toggle Functionality
    function setTheme(mode) {
        if (mode === "dark") {
            body.classList.add("dark-mode");
            toggleBtn.innerHTML = '<i class="bi bi-brightness-high-fill text-light"></i>';
        } else {
            body.classList.remove("dark-mode");
            toggleBtn.innerHTML = '<i class="bi bi-moon-fill text-dark"></i>';
        }
        localStorage.setItem("theme", mode);
    }

    toggleBtn.addEventListener("click", () => {
        const isDark = body.classList.contains("dark-mode");
        setTheme(isDark ? "light" : "dark");
    });

    // On load - set theme
    window.addEventListener("load", () => {
        const savedTheme = localStorage.getItem("theme") || "light";
        setTheme(savedTheme);
        
        // Initialize department chart
        initDepartmentChart();
        
        // Set current date and time
        updateDateTime();
        // Update time every second
        setInterval(updateDateTime, 1000);
    });

    // Update date and time function
    function updateDateTime() {
        const now = new Date();
        const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
        currentDateElement.textContent = now.toLocaleDateString('en-US', options);
        
        const timeOptions = { hour: '2-digit', minute: '2-digit', second: '2-digit', hour12: true };
        currentTimeElement.textContent = now.toLocaleTimeString('en-US', timeOptions);
    }

    // Mobile sidebar toggle
    sidebarToggle.addEventListener("click", () => {
        sidebar.classList.toggle("active");
        sidebarBackdrop.classList.toggle("active");
        
        // Change toggle icon based on sidebar state
        if (sidebar.classList.contains("active")) {
            sidebarToggle.innerHTML = '<i class="bi bi-x-lg"></i>';
        } else {
            sidebarToggle.innerHTML = '<i class="bi bi-list"></i>';
        }
    });

    // Close sidebar when clicking on backdrop
    sidebarBackdrop.addEventListener("click", () => {
        sidebar.classList.remove("active");
        sidebarBackdrop.classList.remove("active");
        sidebarToggle.innerHTML = '<i class="bi bi-list"></i>';
    });

    // Adjust sidebar on window resize
    window.addEventListener("resize", () => {
        if (window.innerWidth >= 768) {
            sidebar.classList.remove("active");
            sidebarBackdrop.classList.remove("active");
            sidebarToggle.innerHTML = '<i class="bi bi-list"></i>';
        }
    });

    // Search Bar Functionality
    searchInput.addEventListener('input', function () {
        const searchTerm = searchInput.value.toLowerCase();
        
        // Get all parent elements with class "collapse"
        const collapseElements = document.querySelectorAll('.collapse');
        
        // First hide all collapse elements
        collapseElements.forEach(collapseEl => {
            collapseEl.classList.remove('show');
        });
        
        let hasMatches = false;
        
        navLinks.forEach(link => {
            const linkText = link.textContent.toLowerCase();
            if (linkText.includes(searchTerm)) {
                link.style.display = '';
                hasMatches = true;
                
                // If this is a child link, show its parent collapse
                if (link.closest('.collapse')) {
                    link.closest('.collapse').classList.add('show');
                }
            } else {
                link.style.display = 'none';
            }
        });
        
        // Show a message if no matches found
        const noResultsEl = document.getElementById('noSearchResults');
        if (!hasMatches && searchTerm !== '') {
            if (!noResultsEl) {
                const noResults = document.createElement('div');
                noResults.id = 'noSearchResults';
                noResults.className = 'text-center my-3 text-muted';
                noResults.textContent = 'No matches found';
                searchInput.parentNode.after(noResults);
            }
        } else if (noResultsEl) {
            noResultsEl.remove();
        }
    });

    // Quick access menu toggle
    quickAccessBtn.addEventListener('click', function() {
        quickAccessMenu.classList.toggle('show');
    });

    // Close quick access menu when clicking outside
    document.addEventListener('click', function(event) {
        if (!quickAccessBtn.contains(event.target) && !quickAccessMenu.contains(event.target)) {
            quickAccessMenu.classList.remove('show');
        }
    });

    // Department Chart Initialization
    function initDepartmentChart() {
        const ctx = document.getElementById('departmentChart').getContext('2d');
        
        // Sample data for departments
        const departmentData = {
            labels: ['CSE', 'ECE', 'Mechanical', 'Civil', 'EEE', 'IT'],
            datasets: [{
                label: 'Number of Students',
                data: [320, 280, 210, 190, 225, 240],
                backgroundColor: [
                    'rgba(75, 7, 248, 0.7)',
                    'rgba(75, 192, 192, 0.7)',
                    'rgba(255, 159, 64, 0.7)',
                    'rgba(255, 99, 132, 0.7)',
                    'rgba(54, 162, 235, 0.7)',
                    'rgba(153, 102, 255, 0.7)'
                ],
                borderColor: [
                    'rgba(75, 7, 248, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(255, 159, 64, 1)',
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(153, 102, 255, 1)'
                ],
                borderWidth: 1
            }]
        };
        
        // Create chart
        new Chart(ctx, {
            type: 'bar',
            data: departmentData,
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    }
    
    // Add animation for card elements
    document.addEventListener('DOMContentLoaded', function() {
        const animatedCards = document.querySelectorAll('.animated-card');
        animatedCards.forEach((card, index) => {
            setTimeout(() => {
                card.style.opacity = '1';
            }, 100 * (index + 1));
        });
    });

    // Collapse other menus when one is opened
    const navMenus = document.querySelectorAll('.nav-link[data-bs-toggle="collapse"]');
    navMenus.forEach(menu => {
        menu.addEventListener('click', function() {
            const target = this.getAttribute('href');
            const currentCollapse = document.querySelector(target);
            
            // If menu already has show class, do nothing (let Bootstrap handle toggle)
            if (!currentCollapse.classList.contains('show')) {
                // Close all other open menus
                document.querySelectorAll('.collapse.show').forEach(openMenu => {
                    if (openMenu.id !== currentCollapse.id) {
                        openMenu.classList.remove('show');
                    }
                });
            }
        });
    });

    // Simulate loading state (for demonstration)
    function simulateLoading() {
        const loadingOverlay = document.createElement('div');
        loadingOverlay.style.position = 'fixed';
        loadingOverlay.style.top = '0';
        loadingOverlay.style.left = '0';
        loadingOverlay.style.width = '100%';
        loadingOverlay.style.height = '100%';
        loadingOverlay.style.backgroundColor = 'rgba(0,0,0,0.5)';
        loadingOverlay.style.display = 'flex';
        loadingOverlay.style.justifyContent = 'center';
        loadingOverlay.style.alignItems = 'center';
        loadingOverlay.style.zIndex = '9999';
        
        const spinner = document.createElement('div');
        spinner.className = 'spinner-border text-light';
        spinner.setAttribute('role', 'status');
        
        const srOnly = document.createElement('span');
        srOnly.className = 'visually-hidden';
        srOnly.textContent = 'Loading...';
        
        spinner.appendChild(srOnly);
        loadingOverlay.appendChild(spinner);
        document.body.appendChild(loadingOverlay);
        
        setTimeout(() => {
            loadingOverlay.remove();
        }, 1000);
    }

    // Add loading simulation to card links
    document.querySelectorAll('.card .btn-primary').forEach(btn => {
        btn.addEventListener('click', function() {
            simulateLoading();
        });
    });

    // Add notifications badge animation
    function createNotificationBadge() {
        const notificationBadge = document.createElement('span');
        notificationBadge.className = 'position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger';
        notificationBadge.style.fontSize = '0.6rem';
        notificationBadge.textContent = '3';
        
        const notificationIcon = document.querySelector('.nav-link i.bi-bell');
        if (notificationIcon) {
            notificationIcon.parentElement.style.position = 'relative';
            notificationIcon.parentElement.appendChild(notificationBadge);
        }
    }
    
    // Create a modal for showing system info
    function createSystemInfoModal() {
        const modal = document.createElement('div');
        modal.className = 'modal fade';
        modal.id = 'systemInfoModal';
        modal.tabIndex = '-1';
        modal.setAttribute('aria-labelledby', 'systemInfoModalLabel');
        modal.setAttribute('aria-hidden', 'true');
        
        modal.innerHTML = `
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="systemInfoModalLabel">System Information</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p><strong>System:</strong> College Management System v3.5</p>
                        <p><strong>Last Updated:</strong> April 24, 2025</p>
                        <p><strong>Browser:</strong> <span id="browserInfo"></span></p>
                        <p><strong>Screen Resolution:</strong> <span id="screenInfo"></span></p>
                        <p><strong>Current Theme:</strong> <span id="themeInfo"></span></p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        `;
        
        document.body.appendChild(modal);
        
        // Fill system info when modal is shown
        const systemInfoModal = document.getElementById('systemInfoModal');
        systemInfoModal.addEventListener('show.bs.modal', function (event) {
            document.getElementById('browserInfo').textContent = navigator.userAgent;
            document.getElementById('screenInfo').textContent = `${window.screen.width}x${window.screen.height}`;
            document.getElementById('themeInfo').textContent = body.classList.contains('dark-mode') ? 'Dark Mode' : 'Light Mode';
        });
    }
    
    // Initialize additional UI elements
    function initAdditionalUI() {
        createNotificationBadge();
        createSystemInfoModal();
        
        // Add info button to header
        const infoButton = document.createElement('button');
        infoButton.className = 'btn btn-sm btn-info ms-2';
        infoButton.setAttribute('data-bs-toggle', 'modal');
        infoButton.setAttribute('data-bs-target', '#systemInfoModal');
        infoButton.innerHTML = '<i class="bi bi-info-circle"></i>';
        document.querySelector('.profile-header').appendChild(infoButton);
    }
    
    // Initialize additional UI after page load
    window.addEventListener('load', initAdditionalUI);
</script>

</body>
</html>