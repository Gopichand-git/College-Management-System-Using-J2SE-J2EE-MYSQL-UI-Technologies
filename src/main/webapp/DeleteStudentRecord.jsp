<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Delete Student Record</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet"/>

    <style>
        body {
            background-color: #f8f9fa;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .card {
            border-radius: 15px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            padding: 30px;
            width: 100%;
            max-width: 400px;
            animation: fadeIn 0.6s ease-in-out;
            background-color: #ffffff;
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .btn-danger {
            width: 100%;
            transition: background-color 0.3s ease;
        }

        .btn-danger:hover {
            background-color: #c82333;
        }

        .form-label {
            font-weight: 500;
        }

        .card h4 {
            font-weight: 600;
        }
    </style>
</head>
<body>
    <div class="card">
        <h4 class="text-center mb-4">
            <i class="bi bi-trash-fill text-danger"></i> Delete Student Record
        </h4>

        <%-- Displaying success or error message --%>
        <%
            String message = (String) request.getAttribute("message");
            String messageType = (String) request.getAttribute("messageType");

            if (message != null && !message.isEmpty()) {
                String alertClass = "alert-info"; // Default is info
                if ("error".equals(messageType)) {
                    alertClass = "alert-danger";
                } else if ("success".equals(messageType)) {
                    alertClass = "alert-success";
                }
        %>
            <div class="alert <%= alertClass %> text-center">
                <%= message %>
            </div>
        <%
            }
        %>

        <form action="./deleteServlet" method="post">
            <div class="mb-3">
                <label for="studentId" class="form-label">Student ID</label>
                <input type="text" class="form-control" id="sid" name="sid" placeholder="Enter Student ID" required />
            </div>
            <button type="submit" class="btn btn-danger">
                Delete
            </button>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
