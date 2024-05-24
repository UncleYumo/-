package tech.revolicise.crms_dev;

import javafx.scene.control.Alert;

import java.sql.*;

/**
 * @author uncle_yumo
 * @CreateDate 2024/5/15
 * @School 无锡学院
 * @StudentID 22344131
 * @Description MySQL数据库连接类
 */
public class MySQL_Connection {
    public boolean isConnected = false; // 数据库连接状态
    Connection conn = null; // 数据库连接对象
    private String user;
    private String password;
    private String database_name;
    private String database_url;
    public static MySQL_Connection connection; // 连接数据库

    public MySQL_Connection() {
        this.user = "test"; // 数据库用户名
        this.password = "_Test141760"; // 数据库密码
        this.database_url = "47.120.34.243"; // 数据库地址
        this.database_name = "crms_database"; // 数据库名称
    }

    public MySQL_Connection(String user, String password, String database_url, String database_name) {
        this.user = user;
        this.password = password;
        this.database_name = database_name;
        this.database_url = database_url;
    }

    public Connection getConnection() {
        connection = new MySQL_Connection();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 加载JDBC驱动程序
            System.out.println("成功加载MySQL JDBC驱动！");
        } catch (ClassNotFoundException e) {
            e.fillInStackTrace(); // 异常处理,打印异常堆栈信息
            System.out.println("加载MySQL JDBC驱动失败！");
        }

        try { // 尝试连接数据库
            conn = DriverManager.getConnection("jdbc:mysql://"
                    + database_url + ":3306/"
                    + database_name +
                    "?useUnicode=true&characterEncoding=UTF-8", user, password);
            System.out.println("连接数据库成功");
            isConnected = true;
        } catch (SQLException e) { // 捕捉异常：如果连接失败
            e.fillInStackTrace(); // 输出具体的异常信息
            System.out.println("连接数据库失败！");
        }
        return conn;
    }

    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close(); // 关闭数据库连接
                System.out.println("关闭数据库连接成功！");
            }
        } catch (SQLException e) {
            e.fillInStackTrace(); // 输出异常信息
            System.out.println("关闭数据库连接失败！");
        }
    }

    public void outputTable(String table_name) {
        String sql = "SELECT * FROM " + table_name; // SQL查询语句
        try {
            if (conn != null) {
                ResultSet rs = conn.createStatement().executeQuery(sql); // 执行查询语句
                while (rs.next()) { // 遍历结果集
                    System.out.println(
                            rs.getString("id") + " "
                                    + rs.getString("name") + " "
                                    + rs.getString("birth")
                    ); // 输出结果
                }
                rs.close(); // 关闭结果集
            }
        } catch (SQLException e) {
            e.fillInStackTrace(); // 输出异常信息
            System.out.println("查询数据失败！");
        }
    }

    /**
     * @param username 用户名
     * @param password 密码
     * @return 0：验证失败，用户名或密码错误
     * 1：验证成功，为管理员权限（root）
     * 2：验证成功，为普通用户权限（user）
     */
    public int authentication(String username, String password) {
        try {
            if (conn != null) {
                String sql = "SELECT * FROM usertable WHERE UserName = '" + username + "' AND Password = '" + password + "'";
                ResultSet rs = conn.createStatement().executeQuery(sql); // 执行查询语句
                if (rs.next()) { // 验证成功
                    if (rs.getString("Role").equals("root")) { // 管理员权限
                        System.out.println("验证成功，为管理员权限！");
                        return 1;
                    } else { // 普通用户权限
                        System.out.println("验证成功，为普通用户权限！");
                        return 2;
                    }
                } else { // 验证失败
                    System.out.println("用户名或密码错误！");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("用户验证失败");
                    alert.setHeaderText("用户名或密码错误！");
                    alert.setContentText("请重新输入用户名和密码！");
                    alert.showAndWait(); // 弹出提示框
                    return 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("身份验证时出现数据库异常：" + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("用户验证失败");
            alert.setHeaderText("系统异常！");
            alert.setContentText("先别扣分！请先联系作者！");
            alert.showAndWait(); // 弹出提示框
            System.out.println("查询数据失败！");
        }
        return 0;
    }

    public String[] outputTable(String table_name, String field) {
        String sql = "SELECT " + field + " FROM " + table_name; // SQL查询语句
        String[] resultList = new String[100];
        int count = 0;
        try {
            if (conn != null) {
                ResultSet rs = conn.createStatement().executeQuery(sql); // 执行查询语句
                System.out.println("Table ：" + table_name + " Field ：" + field + " Result：");
                while (rs.next()) { // 遍历结果集
                    System.out.println(
                            rs.getString(field)
                    ); // 输出结果
                    resultList[count++] = rs.getString(field);
                }
                rs.close(); // 关闭结果集
            }
        } catch (SQLException e) {
            e.fillInStackTrace(); // 输出异常信息
            System.out.println("查询数据失败！");
        }
        return resultList;
    }
}