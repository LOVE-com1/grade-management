package com.grade.servlet;

import com.grade.dao.ScoreDAO;
import com.grade.model.Score;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 教师端：录入 / 删除成绩
 */
@WebServlet(name = "TeacherServlet", urlPatterns = "/teacher")
public class TeacherServlet extends HttpServlet {

    private ScoreDAO dao = new ScoreDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("delete".equals(action)) {
            // 删除
            int id = Integer.parseInt(req.getParameter("id"));
            dao.delete(id);
            req.getSession().setAttribute("msg", "删除成功！");
        } else {
            // 录入 / 更新
            String studentId = req.getParameter("studentId").trim();
            String studentName = req.getParameter("studentName").trim();
            String subject = req.getParameter("subject").trim();
            double score = Double.parseDouble(req.getParameter("score"));

            if (studentId.isEmpty() || studentName.isEmpty() || subject.isEmpty()) {
                req.getSession().setAttribute("msg", "学号、姓名、科目不能为空！");
            } else if (score < 0 || score > 100) {
                req.getSession().setAttribute("msg", "成绩必须在 0~100 之间！");
            } else {
                Score s = new Score(studentId, studentName, subject, score);
                if (dao.saveOrUpdate(s)) {
                    req.getSession().setAttribute("msg", "成绩保存成功！");
                } else {
                    req.getSession().setAttribute("msg", "保存失败，请重试！");
                }
            }
        }
        // 重定向回教师页面
        resp.sendRedirect(req.getContextPath() + "/teacher.jsp");
    }
}
