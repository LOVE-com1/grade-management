<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>统计报表 - 平均分 / 及格率</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<nav class="navbar">
    <div class="container">
        <a class="brand" href="${pageContext.request.contextPath}/">📊 班级成绩管理系统</a>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/teacher.jsp">教师录入</a>
            <a href="${pageContext.request.contextPath}/student.jsp">学生查询</a>
            <a href="${pageContext.request.contextPath}/stat" class="active">统计报表</a>
        </div>
    </div>
</nav>

<div class="container">

    <!-- 班级总览 -->
    <div class="card">
        <h2>📈 班级总览</h2>
        <div class="stat-row">
            <div class="stat-box">
                <div class="stat-value">${totalCount}</div>
                <div class="stat-label">成绩总条数</div>
            </div>
            <div class="stat-box">
                <div class="stat-value">${classAvg}</div>
                <div class="stat-label">班级平均分</div>
            </div>
            <div class="stat-box">
                <div class="stat-value">${classPassRate}%</div>
                <div class="stat-label">班级及格率</div>
            </div>
        </div>
    </div>

    <!-- 各科统计 -->
    <div class="card">
        <h2>📊 各科平均分与及格率</h2>
        <table class="table">
            <thead>
                <tr>
                    <th>科目</th>
                    <th>参考人数</th>
                    <th>平均分</th>
                    <th>及格率</th>
                    <th>及格率可视化</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="s" items="${subjectStat}">
                    <tr>
                        <td><strong>${s.subject}</strong></td>
                        <td>${s.count}</td>
                        <td>${s.avg}</td>
                        <td>${s.passRate}%</td>
                        <td style="min-width:180px;">
                            <div class="progress-bar">
                                <div class="progress-fill ${s.passRate >= 60 ? 'bg-success' : 'bg-danger'}"
                                     style="width:${s.passRate}%"></div>
                            </div>
                            <span class="progress-text">${s.passRate}%</span>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty subjectStat}">
                    <tr><td colspan="5" class="text-center text-muted">暂无统计数据</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>

</div>
</body>
</html>
