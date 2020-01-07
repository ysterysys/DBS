<%
    session.invalidate();
    out.println("You have successfully logged out of the system!");
    request.getRequestDispatcher("Login.jsp").forward(request, response);
%>
