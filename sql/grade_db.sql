-- ============================================================
-- 班级成绩管理系统  数据库初始化脚本
-- MySQL 5.7+ / 8.0+
-- 执行方式：mysql -u root -p < grade_db.sql
-- ============================================================

CREATE DATABASE IF NOT EXISTS grade_db
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;

USE grade_db;

-- ---------- 成绩表 ----------
DROP TABLE IF EXISTS score;
CREATE TABLE score (
    id          INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    student_id  VARCHAR(20)  NOT NULL COMMENT '学号',
    student_name VARCHAR(50) NOT NULL COMMENT '姓名',
    subject     VARCHAR(50)  NOT NULL COMMENT '科目',
    score       DECIMAL(5,2) NOT NULL COMMENT '成绩(0~100)',
    input_time  DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '录入时间',
    UNIQUE KEY uk_student_subject (student_id, subject)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生成绩表';

-- ---------- 示例数据（可选） ----------
INSERT INTO score (student_id, student_name, subject, score) VALUES
('2024001', '张三', '语文', 88),
('2024001', '张三', '数学', 92),
('2024001', '张三', '英语', 76),
('2024002', '李四', '语文', 65),
('2024002', '李四', '数学', 58),
('2024002', '李四', '英语', 82),
('2024003', '王五', '语文', 90),
('2024003', '王五', '数学', 85),
('2024003', '王五', '英语', 88),
('2024004', '赵六', '语文', 52),
('2024004', '赵六', '数学', 61),
('2024004', '赵六', '英语', 45);
