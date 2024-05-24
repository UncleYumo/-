package tech.revolicise.crms_dev;

import javafx.scene.control.Alert;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQL_ConnUtils {
    public boolean isConnected = false; // 数据库连接状态

    public String userRole = "未知";
    public String userName = "未知";
    public String userID = "未知"; // 当前用户ID
    Connection conn = null; // 数据库连接
    private String user;
    private String password;
    private String database_name;
    private String database_url;
    private static MySQL_ConnUtils instance; // 单例模式

    private MySQL_ConnUtils() { // 构造函数私有化
        this.user = "test";
        this.password = "_Test141760";
        this.database_url = "47.120.34.243";
        this.database_name = "crms_database";
    }

    // 单例设计模式，获取实例
    public static MySQL_ConnUtils getInstance() { // 静态方法
        System.out.println("正在获取数据库连接实例...");
        if (instance == null) { // 第一次调用getInstance方法时，创建实例
            System.out.println("正在创建数据库连接实例...");
            instance = new MySQL_ConnUtils();
            System.out.println("数据库连接实例创建成功！");
            instance.connectToDatabase();
            System.out.println("数据库连接成功！");
        }
        return instance; // 返回实例
    }

    private void connectToDatabase() {
        System.out.println("正在连接数据库...");
        try {
            System.out.println("正在加载MySQL JDBC驱动...");
            Class.forName("com.mysql.cj.jdbc.Driver"); // 加载JDBC驱动程序
            System.out.println("成功加载MySQL JDBC驱动！");
            conn = DriverManager.getConnection("jdbc:mysql://"
                    + database_url + ":3306/"
                    + database_name +
                    "?useUnicode=true&characterEncoding=UTF-8", user, password);
            System.out.println("成功连接到数据库！");
            isConnected = true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // 输出异常信息
            System.out.println("加载MySQL JDBC驱动失败！");
            System.out.println("连接数据库失败！");
            System.out.println(e.getMessage());
        }
    }

    public Connection getConnection() { // 获取数据库连接
        return conn;
    }

    /**
     * 获取设备列表
     * @param conn 数据库连接
     * @Description 获取devicetable表中的设备列表（DeviceID、DeviceType、Status、Location），查询失败返回null
     * @return 设备列表
     */
    public List<Device> getAllDeviceList(Connection conn) {
        List<Device> deviceList = new ArrayList<>();
        String sql = "SELECT * FROM devicetable"; // 定义查询语句
        try {
            if (conn != null) {
                ResultSet rs = conn.createStatement().executeQuery(sql); // 执行查询语句
                while (rs.next()) { // 遍历结果集
                    String deviceID = rs.getString("DeviceID");
                    String deviceType = rs.getString("DeviceType");
                    String status = rs.getString("Status");
                    String location = rs.getString("Location");
                    System.out.println("当前获取的设备信息：" + deviceID + " " + deviceType + " " + status + " " + location);
                    deviceList.add(new Device(deviceID, deviceType, status, location)); // 将查询到的设备信息添加到设备列表中
                }
                rs.close(); // 关闭结果集
                return deviceList; // 返回设备列表
            } else {
                System.out.println("数据库连接失败！");
                return null;
            }
        } catch (SQLException e) {
            e.fillInStackTrace(); // 输出异常信息
            System.out.println("查询数据失败！");
            return null;
        }
    }
    public void closeConnection() { // 关闭数据库连接
        System.out.println("正在关闭数据库连接...");
        try {
            if (conn != null) {
                conn.close();
                System.out.println("关闭数据库连接成功！");
            }
        } catch (SQLException e) {
            System.out.println("关闭数据库连接失败！");
            System.out.println(e.getMessage());
        }
    }

    // 其他方法...

    // 存储当前用户信息
    public void setCurrentUserInfo(String userRole, String userName) {
        this.userRole = userRole;
        this.userName = userName;
        System.out.println("当前用户：" + userName + " 角色：" + userRole);
    }

    public int authentication(String username, String password) {
        try {
            if (conn != null) {
                String sql = "SELECT * FROM usertable WHERE UserName = '" + username + "' AND Password = '" + password + "'";
                ResultSet rs = conn.createStatement().executeQuery(sql); // 执行查询语句
                if (rs.next()) { // 验证成功
                    if (rs.getString("Role").equals("root")) { // 管理员权限
                        System.out.println("验证成功，为管理员权限！");
                        setCurrentUserInfo("root", username);
                        return 1;
                    } else { // 普通用户权限
                        System.out.println("验证成功，为普通用户权限！");
                        setCurrentUserInfo("user", username);
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
}

// 在这个重写后的类中，构造函数变为私有，新增了一个静态方法 getInstance 来获取唯一的实例，
// 并在内部调用 connectToDatabase 方法来连接数据库。在其他部分的实现逻辑没有变化。
//在你的应用程序中，可以使用 MySQL_ConnUtils.getInstance().getConnUtils()
// 来获取数据库连接实例，而无需创建多个实例。