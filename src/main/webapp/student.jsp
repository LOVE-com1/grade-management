<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>学生端 - 成绩查询</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<nav class="navbar">
    <div class="container">
        <a class="brand" href="${pageContext.request.contextPath}/">📊 班级成绩管理系统</a>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/teacher.jsp">教师录入</a>
            <a href="${pageContext.request.contextPath}/student.jsp" class="active">学生查询</a>
            <a href="${pageContext.request.contextPath}/stat">统计报表</a>
        </div>
    </div>
</nav>

<div class="container">

    <!-- 查询表单 -->
    <div class="card">
        <h2>🔍 成绩查询</h2>
        <form action="${pageContext.request.contextPath}/student" method="post" class="form-row">
            <div class="form-group flex-2">
                <label>输入学号</label>
                <input type="text" name="studentId" value="${studentId}" placeholder="请输入你的学号" required>
            </div>
            <div class="form-group btn-group">
                <button type="submit" class="btn btn-primary">🔎 查询</button>
            </div>
        </form>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
    </div>

    <!-- 查询结果 -->
    <c:if test="${not empty scoreList}">
        <div class="card">
            <h2>👤 ${studentName}（学号：${studentId}）的成绩</h2>

            <!-- 个人统计卡片 -->
            <div class="stat-row">
                <div class="stat-box">
                    <div class="stat-value">${avg}</div>
                    <div class="stat-label">个人平均分</div>
                </div>
                <div class="stat-box">
                    <div class="stat-value">${passRate}%</div>
                    <div class="stat-label">个人及格率</div>
                </div>
                <div class="stat-box">
                    <div class="stat-value">${classAvg}</div>
                    <div class="stat-label">班级平均分</div>
                </div>
            </div>

            <!-- 成绩明细 -->
            <table class="table">
                <thead>
                    <tr>
                        <th>科目</th>
                        <th>成绩</th>
                        <th>状态</th>
                        <th>与班级平均对比</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="s" items="${scoreList}">
                        <c:set var="diff" value="${s.score - classAvg}" />
                        <tr>
                            <td>${s.subject}</td>
                            <td>
                                <span class="badge ${s.pass ? 'badge-success' : 'badge-danger'}">
                                    ${s.score}
                                </span>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${s.pass}">✅ 及格</c:when>
                                    <c:otherwise>❌ 不及格</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${diff > 0}">
                                        <span class="text-success">▲ +${diff}</span>
                                    </c:when>
                                    <c:when test="${diff < 0}">
                                        <span class="text-danger">▼ ${diff}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-muted">— 持平</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>

</div>
</body>
</html>
