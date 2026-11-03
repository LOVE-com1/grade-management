<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>教师端 - 成绩录入</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<!-- 顶部导航 -->
<nav class="navbar">
    <div class="container">
        <a class="brand" href="${pageContext.request.contextPath}/">📊 班级成绩管理系统</a>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/teacher.jsp" class="active">教师录入</a>
            <a href="${pageContext.request.contextPath}/student.jsp">学生查询</a>
            <a href="${pageContext.request.contextPath}/stat">统计报表</a>
        </div>
    </div>
</nav>

<div class="container">

    <!-- 提示消息 -->
    <c:if test="${not empty sessionScope.msg}">
        <div class="alert alert-success">
            ${sessionScope.msg}
            <% session.removeAttribute("msg"); %>
        </div>
    </c:if>

    <!-- 录入表单 -->
    <div class="card">
        <h2>📝 录入成绩</h2>
        <form action="${pageContext.request.contextPath}/teacher" method="post" class="form-row">
            <div class="form-group">
                <label>学号</label>
                <input type="text" name="studentId" placeholder="如：2024001" required>
            </div>
            <div class="form-group">
                <label>姓名</label>
                <input type="text" name="studentName" placeholder="如：张三" required>
            </div>
            <div class="form-group">
                <label>科目</label>
                <input type="text" name="subject" placeholder="如：数学" required>
            </div>
            <div class="form-group">
                <label>成绩</label>
                <input type="number" name="score" step="0.5" min="0" max="100" placeholder="0~100" required>
            </div>
            <div class="form-group btn-group">
                <button type="submit" class="btn btn-primary">💾 保存</button>
            </div>
        </form>
        <p class="hint">💡 同一学生同一科目再次提交将自动更新分数。</p>
    </div>

    <!-- 成绩列表 -->
    <div class="card">
        <h2>📋 成绩列表</h2>
        <table class="table">
            <thead>
                <tr>
                    <th>学号</th>
                    <th>姓名</th>
                    <th>科目</th>
                    <th>成绩</th>
                    <th>录入时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="s" items="${scoreList}">
                    <tr>
                        <td>${s.studentId}</td>
                        <td>${s.studentName}</td>
                        <td>${s.subject}</td>
                        <td>
                            <span class="badge ${s.pass ? 'badge-success' : 'badge-danger'}">
                                ${s.score}
                            </span>
                        </td>
                        <td>${s.inputTime}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/teacher" method="post"
                                  onsubmit="return confirm('确认删除这条记录？');" style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="${s.id}">
                                <button type="submit" class="btn btn-sm btn-danger">删除</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty scoreList}">
                    <tr><td colspan="6" class="text-center text-muted">暂无成绩数据</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>

</div>
</body>
</html>
