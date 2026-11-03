package com.grade.servlet;

import com.grade.dao.ScoreDAO;
import com.grade.model.Score;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 学生端：按学号查询成绩
 */
@WebServlet(name = "StudentServlet", urlPatterns = "/student")
public class StudentServlet extends HttpServlet {

    private ScoreDAO dao = new ScoreDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String studentId = req.getParameter("studentId").trim();

        if (studentId.isEmpty()) {
            req.setAttribute("error", "请输入学号！");
            req.getRequestDispatcher("/student.jsp").forward(req, resp);
            return;
        }

        boolean exists = dao.existsStudent(studentId);
        if (!exists) {
            req.setAttribute("error", "该学号不存在，请检查！");
            req.getRequestDispatcher("/student.jsp").forward(req, resp);
            return;
        }

        List<Score> list = dao.findByStudentId(studentId);
        double avg = dao.studentAverage(studentId);
        double passRate = dao.studentPassRate(studentId);
        double classAvg = dao.classAverage();

        // 找到第一条记录拿到姓名
        String name = list.isEmpty() ? "" : list.get(0).getStudentName();

        req.setAttribute("studentId", studentId);
        req.setAttribute("studentName", name);
        req.setAttribute("scoreList", list);
        req.setAttribute("avg", avg);
        req.setAttribute("passRate", passRate);
        req.setAttribute("classAvg", classAvg);

        req.getRequestDispatcher("/student.jsp").forward(req, resp);
    }
}
