package com.grade.servlet;

import com.grade.dao.ScoreDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 统计：班级平均分 / 及格率 / 各科统计
 */
@WebServlet(name = "StatServlet", urlPatterns = "/stat")
public class StatServlet extends HttpServlet {

    private ScoreDAO dao = new ScoreDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        double classAvg = dao.classAverage();
        double classPassRate = dao.classPassRate();
        List<Map<String, Object>> subjectStat = dao.subjectStat();
        long totalCount = dao.findAll().size();

        req.setAttribute("classAvg", classAvg);
        req.setAttribute("classPassRate", classPassRate);
        req.setAttribute("subjectStat", subjectStat);
        req.setAttribute("totalCount", totalCount);

        req.getRequestDispatcher("/stat.jsp").forward(req, resp);
    }
}
