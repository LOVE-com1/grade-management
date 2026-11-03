<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.grade.dao.ScoreDAO" %>
<%@ page import="java.util.*" %>
<%
    // 首页：加载所有成绩，转发到教师端展示
    ScoreDAO dao = new ScoreDAO();
    request.setAttribute("scoreList", dao.findAll());
    request.getRequestDispatcher("/teacher.jsp").forward(request, response);
%>
