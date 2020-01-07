<%@page import="Entity.*"%>
<%
Object user = session.getAttribute("user");
if(user == null) {
    response.sendRedirect("Login.jsp");
    return;
}
if(user instanceof User) {
    User currentUser = (User)user;
    if(currentUser.getAccountType().equals("User")){
        response.sendRedirect("MainMenu.jsp");
    } 
}
%>