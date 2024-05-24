package tech.revolicise.crms_dev;

import javafx.scene.control.Alert;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author uncle_yumo
 * @CreateDate 2024/5/22
 * @School 无锡学院
 * @StudentID 22344131
 * @Description 用于处理数据库相关操作的工具类，包括增删改查等操作
 */
public class SqlUtil {

    StrUtils strUtils = new StrUtils(); // 实例化字符串工具类

    /**
     * 获取设备列表
     * @param conn // 外部建立的数据库连接
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

    public Device getDeviceByID(String deviceID, Connection connection) {
        String sql = "SELECT * FROM devicetable WHERE DeviceID = '" + deviceID + "'";
        try {
            if (connection != null) {
                ResultSet rs = connection.createStatement().executeQuery(sql);
                if (rs.next()) {
                    String deviceType = rs.getString("DeviceType");
                    String status = rs.getString("Status");
                    String location = rs.getString("Location");
                    System.out.println("getDeviceByID()获取的设备信息：" + deviceID + " " + deviceType + " " + status + " " + location);
                    return new Device(deviceID, deviceType, status, location);
                } else {
                    System.out.println("设备ID不存在！");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("错误");
                    alert.setHeaderText("设备ID不存在！");
                    alert.showAndWait();
                    return null;
                }
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

    public User getUserByUserName(String userName, Connection connection) {
        String sql = "SELECT * FROM usertable WHERE UserName = '" + userName + "'";
        try {
            if (connection != null) {
                ResultSet rs = connection.createStatement().executeQuery(sql);
                if (rs.next()) {
                    String userID = rs.getString("UserID");
                    String password = rs.getString("Password");
                    String role = rs.getString("Role");
                    System.out.println("当前获取的用户信息：" + userID + " " + userName + " " + password + " " + role);
                    return new User(userID, userName, password, role);
                } else {
                    System.out.println("用户名不存在！");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("错误");
                    alert.setHeaderText("用户名不存在！");
                    alert.showAndWait();
                    return null;
                }
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

    public boolean changeDeviceStatusByDeviceID(String deviceID, String status, Connection connection) {
        String sql = "UPDATE devicetable SET Status = '" + status + "' WHERE DeviceID = '" + deviceID + "'";
        try {
            if (connection != null) {
                Statement statement = connection.createStatement();
                int result = statement.executeUpdate(sql);
                if (result == 1) {
                    System.out.println("设备状态修改成功！");
                    return true;
                } else {
                    System.out.println("设备状态修改失败！");
                    return false;
                }
            } else {
                System.out.println("数据库连接失败！");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("123：操作数据库失败！");
            return false;
        }
    }

    public ChangeRecordTable getChangeRecordTableByDeviceIDAndUserID(String deviceID,String userID, Connection connection) {
        String sql = "SELECT * FROM changerecordtable WHERE DeviceID = '" + deviceID + "' AND UserID = '" + userID + "'";
        try {
            if (connection != null) {
                ResultSet rs = connection.createStatement().executeQuery(sql);
                if (rs.next()) {
                    String recordID = rs.getString("RecordID");
                    String startTime = rs.getString("StartTime");
                    String endTime = rs.getString("EndTime");
                    double amount = rs.getDouble("Amount");
                    String amount_String = String.format("%.2f", amount);
                    System.out.println("当前获取的借用记录信息：" + recordID + " " + userID + " " + deviceID + " " + startTime + " " + endTime + " " + amount_String);
                    System.out.println("借用开始时间：" + startTime);
                    System.out.println("借用结束时间：" + endTime);
                    System.out.println("借用金额：" + amount_String);
                    return new ChangeRecordTable(recordID, userID, deviceID, startTime, endTime, amount_String);
                } else {
                    System.out.println("未检索到与您匹配的借用记录！");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("错误");
                    alert.setHeaderText("未检索到与您匹配的借用记录！");
                    alert.showAndWait();
                    return null;
                }
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

    public boolean borrowDevice(String deviceID, String userID, String borrowTime, Connection connection) {
        String sql = "INSERT INTO changerecordtable (UserID, DeviceID, StartTime, EndTime) VALUES ('" + userID + "', '" + deviceID + "', '" + borrowTime + "', NULL)";
        try {
            if (connection != null) {
                Statement statement = connection.createStatement();
                int result = statement.executeUpdate(sql);
                if (result == 1) {
                    System.out.println("设备借用成功！");
                    // 更新对应设备的状态
                    return changeDeviceStatusByDeviceID(deviceID, "借用中", MySQL_ConnUtils.getInstance().getConnection());
                } else {
                    System.out.println("设备借用失败！");
                    return false;
                }
            } else {
                System.out.println("数据库连接失败！");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public ChangeRecordTable returnDevice(String deviceID, String userID, String endTime, Connection connection) {
        ChangeRecordTable changeRecordTable = getChangeRecordTableByDeviceIDAndUserID(deviceID, userID, connection); // 获取借用记录
        if (changeRecordTable == null) {
            System.out.println("借用记录不匹配！无法归还设备。");
            return null;
        }
        // 计算amount
        String startTime = changeRecordTable.getStartTime();
        System.out.println("借用时间（转换前）：" + startTime);
        System.out.println("归还时间（转换前）：" + endTime);
        startTime = strUtils.transSqlTimeToNumTime(startTime);
        endTime = strUtils.transJTimeToNumTime(endTime);
        System.out.println("借用时间（转换后）：" + startTime);
        System.out.println("归还时间（转换后）：" + endTime);
        double amount = strUtils.caluculateAmount(startTime, endTime);
        // amount保留小数点后两位
        System.out.println("本次借用的租金：" + amount);
        String amount_String = String.format("%.2f", amount);
        amount = Double.parseDouble(amount_String);
        // 更新借用记录表changerecordtable（Endtime、Amount
        // 更新设备表devicetable（Status）
        // 重新格式化endTime为yyyy-MM-dd HH:mm:ss
        endTime = strUtils.transNumTimeToSqlTime(endTime);
        String updateSql = "UPDATE changerecordtable SET EndTime = '" + endTime + "', Amount = " + amount + " WHERE RecordID = '" + changeRecordTable.getRecordID() + "'";
        System.out.println("插入更新记录表的sql语句：" + updateSql);
        try  {
            if (connection != null) {
                Statement statement = connection.createStatement();
                int result = statement.executeUpdate(updateSql);
                System.out.print("更新借用记录表的结果：(deviceID）：" + deviceID + "(recordID）：" + changeRecordTable.getRecordID() + " ");
                if (result == 1) {
                    System.out.println("借用记录更新成功！");
                    // 更新对应设备的状态
                    boolean isSuccess = changeDeviceStatusByDeviceID(deviceID, "闲置", MySQL_ConnUtils.getInstance().getConnection());
                    if (isSuccess) {
                        System.out.println("设备表状态更新成功！(借用中 -> 闲置)");
                        return changeRecordTable;
                    } else {
                        System.out.println("设备状态更新失败！");
                        return null;
                    }
                } else {
                    System.out.println("借用记录更新失败！");
                    return null;
                }
            } else {
                System.out.println("数据库连接失败！");
                return null;
            }
        } catch (SQLException e) {
            e.fillInStackTrace(); // 输出异常信息
            System.out.println("操作数据库失败！");
            return null;
        }
    }

    private boolean updateChangeRecordTableByRecordID(String recordID, String endTime, String status, double amount, Connection connection) {
        String sql = "UPDATE changerecordtable SET EndTime = '" + endTime + "', Status = '" + status + "', Amount = " + amount + " WHERE RecordID = '" + recordID + "'";
        try {
            if (connection != null) {
                Statement statement = connection.createStatement();
                int result = statement.executeUpdate(sql);
                if (result == 1) {
                    System.out.println("借用记录更新成功！");
                    return true;
                } else {
                    System.out.println("借用记录更新失败！");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace(); // 输出异常信息
            System.out.println("操作数据库失败！");
        }
        return false;
    }


    private String getStartTimeByDeviceIDAndUserID(String deviceID, String userID, Connection connection) {
        String sql = "SELECT StartTime FROM changerecordtable WHERE DeviceID = '" + deviceID + "' AND UserID = '" + userID + "' AND EndTime IS NULL";
        try {
            if (connection != null) {
                ResultSet rs = connection.createStatement().executeQuery(sql);
                if (rs.next()) {
                    String startTime = rs.getString("StartTime");
                    return startTime;
                } else {
                    System.out.println("获取借用时间失败！");
                    return null;
                }
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
    // 获取用户的所有借用记录
    public ArrayList<ChangeRecordTable> getChangeRecordTableListByUserID(String userID, Connection connection) {
        ArrayList<ChangeRecordTable> changeRecordTableList = new ArrayList<>();
        String sql = "SELECT * FROM changerecordtable WHERE UserID = '" + userID + "'";
        try {
            if (connection != null) {
                ResultSet rs = connection.createStatement().executeQuery(sql);
                while (rs.next()) {
                    String recordID = rs.getString("RecordID");
                    String deviceID = rs.getString("DeviceID");
                    String startTime = rs.getString("StartTime");
                    String endTime = rs.getString("EndTime");
                    double amount = rs.getDouble("Amount");
                    String amount_String = String.format("%.2f", amount);
                    ChangeRecordTable tempChangeRecordTable = new ChangeRecordTable(recordID, userID, deviceID, startTime, endTime, amount_String);
                    changeRecordTableList.add(tempChangeRecordTable);
                    System.out.println("当前获取的借用记录信息：" + tempChangeRecordTable.toString());
                    tempChangeRecordTable = null;
            }
                rs.close(); // 关闭结果集
                return changeRecordTableList; // 返回设备列表
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

    public List<User> getAllUserList(Connection connection) {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM usertable"; // 定义查询语句
        try {
            if (connection != null) {
                ResultSet rs = connection.createStatement().executeQuery(sql); // 执行查询语句
                while (rs.next()) { // 遍历结果集
                    String userID = rs.getString("UserID");
                    String userName = rs.getString("UserName");
                    String password = rs.getString("Password");
                    String role = rs.getString("Role");
                    System.out.println("当前获取的用户信息：" + userID + " " + userName + " " + password + " " + role);
                    userList.add(new User(userID, userName, password, role)); // 将查询到的用户信息添加到用户列表中
                }
                rs.close(); // 关闭结果集
                return userList; // 返回用户列表
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

    public User getUserByUserID(String userID, Connection connection) {
        String sql = "SELECT * FROM usertable WHERE UserID = '" + userID + "'";
        try {
            if (connection != null) {
                ResultSet rs = connection.createStatement().executeQuery(sql);
                if (rs.next()) {
                    String userName = rs.getString("UserName");
                    String password = rs.getString("Password");
                    String role = rs.getString("Role");
                    System.out.println("当前获取的用户信息：" + userID + " " + userName + " " + password + " " + role);
                    return new User(userID, userName, password, role);
                } else {
                    System.out.println("用户ID不存在！");
                    return null;
                }
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

    public boolean deleteUserByUserID(String userID, Connection connection) {
        String sql = "DELETE FROM usertable WHERE UserID = '" + userID + "'";
        try {
            if (connection != null) {
                Statement statement = connection.createStatement();
                int result = statement.executeUpdate(sql);
                if (result == 1) {
                    System.out.println("用户删除成功！");
                    return true;
                } else {
                    System.out.println("用户删除失败！");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("错误");
                    alert.setHeaderText("该用户有未归还记录，请先归还设备！");
                    alert.showAndWait();
                    return false;
                }
            } else {
                System.out.println("数据库连接失败！");
                return false;
            }
        } catch (SQLException e) {
            e.fillInStackTrace(); // 输出异常信息
            System.out.println("操作数据库失败！");
            return false;
        }
    }

    public boolean addUser(User user, Connection connection) {
        // 判断用户名与userID是否已存在，如果存在，则返回false
        if (getUserByUserName(user.getUserName(), connection) != null) {
            System.out.println("用户名已存在！");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("用户名已存在！");
            alert.showAndWait();
            return false;
        }
        if (getUserByUserID(user.getUserID(), connection) != null) {
            System.out.println("用户ID已存在！");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("用户ID已存在！");
            alert.showAndWait();
            return false;
        }

        // 添加用户到数据库
        String sql = "INSERT INTO usertable (UserID, UserName, Password, Role) VALUES ('" + user.getUserID() + "', '" + user.getUserName() + "', '" + user.getPassword() + "', '" + user.getRole() + "')";
        try {
            if (connection != null) {
                Statement statement = connection.createStatement();
                int result = statement.executeUpdate(sql);
                if (result == 1) {
                    System.out.println("用户添加成功！");
                    return true;
                } else {
                    System.out.println("用户添加失败！");
                    return false;
                }
            } else {
                System.out.println("数据库连接失败！");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(User user, Connection connection) {
        // 更新用户信息
        String sql = "UPDATE usertable SET UserName = '" + user.getUserName() + "', Password = '" + user.getPassword() + "', Role = '" + user.getRole() + "' WHERE UserID = '" + user.getUserID() + "'";
        try {
            if (connection != null) {
                Statement statement = connection.createStatement();
                int result = statement.executeUpdate(sql);
                if (result == 1) {
                    System.out.println("用户信息更新成功！");
                    return true;
                } else {
                    System.out.println("用户信息更新失败！");
                    return false;
                }
            } else {
                System.out.println("数据库连接失败！");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int deleteDeviceByDeviceID(Connection connection, String deviceID) {
        String sql = "DELETE FROM devicetable WHERE DeviceID = '" + deviceID + "'";
        try {
            if (connection != null) {
                Statement statement = connection.createStatement();
                int result = statement.executeUpdate(sql);
                if (result == 1) {
                    System.out.println("设备删除成功！");
                    return result;
                } else {
                    System.out.println("设备删除失败！");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("错误");
                    alert.setHeaderText("该设备仍有借出记录，请先归还设备！");
                    alert.showAndWait();
                    return 0;
                }
            } else {
                System.out.println("数据库连接失败！");
                return 0;
            }
        } catch (SQLException e) {
            e.fillInStackTrace(); // 输出异常信息
            System.out.println("操作数据库失败！");
            return 0;
        }
    }

    public int updateDevice(Connection connection, Device device) {
        String sql = "UPDATE devicetable SET DeviceID = '" + device.getDeviceID() + "', DeviceType = '" + device.getDeviceType() + "', Status = '" + device.getStatus() + "', Location = '" + device.getLocation() + "' WHERE DeviceID = '" + device.getDeviceID() + "'";
        try {
            if (connection != null) {
                Statement statement = connection.createStatement();
                int result = statement.executeUpdate(sql);
                if (result == 1) {
                    System.out.println("设备信息更新成功！");
                    return result;
                } else {
                    System.out.println("设备信息更新失败！");
                    return 0;
                }
            } else {
                System.out.println("数据库连接失败！");
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean addDevice(Device device, Connection connection) {
        // 判断设备ID是否已存在，如果存在，则返回false
        if (getDeviceByDeviceID(device.getDeviceID(), connection) != null) {
            System.out.println("设备ID已存在！");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("设备ID已存在！");
            alert.showAndWait();
            return false;
        }
        // 添加设备到数据库
        String sql = "INSERT INTO devicetable (DeviceID, DeviceType, Status, Location) VALUES ('" + device.getDeviceID() + "', '" + device.getDeviceType() + "', '" + device.getStatus() + "', '" + device.getLocation() + "')";
        try {
            if (connection != null) {
                Statement statement = connection.createStatement();
                int result = statement.executeUpdate(sql);
                if (result == 1) {
                    System.out.println("设备添加成功！");
                    return true;
                } else {
                    System.out.println("设备添加失败！");
                    return false;
                }
            } else {
                System.out.println("数据库连接失败！");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Device getDeviceByDeviceID(String deviceID, Connection connection) {
        String sql = "SELECT * FROM devicetable WHERE DeviceID = '" + deviceID + "'";
        try {
            if (connection != null) {
                ResultSet rs = connection.createStatement().executeQuery(sql);
                if (rs.next()) {
                    String deviceType = rs.getString("DeviceType");
                    String status = rs.getString("Status");
                    String location = rs.getString("Location");
                    System.out.println("当前获取的设备信息：" + deviceID + " " + deviceType + " " + status + " " + location);
                    return new Device(deviceID, deviceType, status, location);
                } else {
                    System.out.println("设备ID不存在！");
                    return null;
                }
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

    public List<ChangeRecordTable> getAllChangeRecordTableList(Connection connection) {
        List<ChangeRecordTable> changeRecordTableList = new ArrayList<>();
        String sql = "SELECT * FROM changerecordtable"; // 定义查询语句
        try {
            if (connection != null) {
                ResultSet rs = connection.createStatement().executeQuery(sql); // 执行查询语句 遍历结果集
                while (rs.next()) {
                    String recordID = rs.getString("RecordID");
                    String userID = rs.getString("UserID");
                    String deviceID = rs.getString("DeviceID");
                    String startTime = rs.getString("StartTime");
                    String endTime = rs.getString("EndTime");
                    double amount = rs.getDouble("Amount");
                    String amount_String = String.format("%.2f", amount);
                    ChangeRecordTable tempChangeRecordTable = new ChangeRecordTable(recordID, userID, deviceID, startTime, endTime, amount_String);
                    changeRecordTableList.add(tempChangeRecordTable);
                    System.out.println("当前获取的借用记录信息：" + tempChangeRecordTable.toString());
                    tempChangeRecordTable = null;
                }
                rs.close(); // 关闭结果集
                return changeRecordTableList; // 返回设备列表
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
}
