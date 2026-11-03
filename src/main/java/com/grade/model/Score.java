package com.grade.model;

/**
 * 成绩实体类，对应数据库表 score
 */
public class Score {

    private Integer id;
    private String studentId;   // 学号
    private String studentName; // 姓名
    private String subject;     // 科目
    private Double score;       // 成绩
    private String inputTime;   // 录入时间

    public Score() {}

    public Score(String studentId, String studentName, String subject, Double score) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.subject = subject;
        this.score = score;
    }

    // getter / setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }

    public String getInputTime() { return inputTime; }
    public void setInputTime(String inputTime) { this.inputTime = inputTime; }

    /** 是否及格 */
    public boolean isPass() {
        return score != null && score >= 60;
    }
}
