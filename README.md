# 班级成绩管理系统（Java EE）

基于 **Servlet + JSP + JDBC + MySQL** 的经典 Java EE 架构，实现教师录入、学生查询、平均分与及格率统计。

---

## 一、技术栈

| 层级 | 技术 |
|------|------|
| 视图层 | JSP + JSTL + HTML/CSS |
| 控制层 | Servlet 4.0（`@WebServlet` 注解） |
| 模型层 | JavaBean（POJO） |
| 持久层 | JDBC（原生，无框架） |
| 数据库 | MySQL 8.x |
| 构建工具 | Maven |
| 服务器 | Tomcat 8.5 / 9.x / 10.x |
| JDK | 1.8 |

---

## 二、项目结构

```
grade-management-javaee
├── pom.xml                                    # Maven 配置
├── sql/
│   └── grade_db.sql                            # 建库建表脚本（含示例数据）
└── src/main/
    ├── java/com/grade/
    │   ├── model/Score.java                    # 实体类
    │   ├── util/DBUtil.java                    # 数据库连接工具
    │   ├── dao/ScoreDAO.java                   # 数据访问层（CRUD + 统计）
    │   └── servlet/
    │       ├── TeacherServlet.java             # 教师录入/删除
    │       ├── StudentServlet.java             # 学生查询
    │       └── StatServlet.java                # 统计（平均分/及格率）
    ├── resources/                              # 配置文件目录（预留）
    └── webapp/
        ├── WEB-INF/web.xml                     # 部署描述符（编码过滤器）
        ├── css/style.css                       # 样式
        ├── index.jsp                           # 首页（转发到教师端）
        ├── teacher.jsp                          # 教师端页面
        ├── student.jsp                          # 学生端页面
        └── stat.jsp                             # 统计报表页面
```

---

## 三、快速部署

### 1. 建库建表
```bash
mysql -u root -p < sql/grade_db.sql
```

### 2. 修改数据库连接信息
编辑 `src/main/java/com/grade/util/DBUtil.java`：
```java
private static final String URL = "jdbc:mysql://localhost:3306/grade_db?...";
private static final String USER = "root";
private static final String PASSWORD = "你的密码";
```

### 3. 打包
```bash
mvn clean package
```
生成 `target/grade-management.war`。

### 4. 部署
将 `grade-management.war` 复制到 Tomcat 的 `webapps/` 目录，启动 Tomcat。

### 5. 访问
```
http://localhost:8080/grade-management/
```

---

## 四、功能说明

| 模块 | URL | 功能 |
|------|-----|------|
| 教师录入 | `/` 或 `/teacher.jsp` | 录入/更新/删除成绩，同一学生同科目自动更新 |
| 学生查询 | `/student.jsp` | 输入学号查询个人成绩、平均分、及格率，与班级平均对比 |
| 统计报表 | `/stat` | 班级平均分、班级及格率、各科平均分与及格率（含进度条可视化） |

---

## 五、关键设计

- **DAO 层**：所有 SQL 操作集中到 `ScoreDAO`，Servlet 只负责请求调度与页面跳转。
- **防止重复录入**：`UNIQUE KEY (student_id, subject)` + `saveOrUpdate` 逻辑，同人同科目自动更新。
- **统计 SQL**：用 `AVG()` / `CASE WHEN` 聚合函数一次算出平均分与及格率，无内存遍历。
- **字符编码**：`SetCharacterEncodingFilter` 统一 UTF-8，中文不乱码。
- **JSTL 标签**：页面用 `<c:forEach>`、`<c:choose>` 等，逻辑与展示分离。
