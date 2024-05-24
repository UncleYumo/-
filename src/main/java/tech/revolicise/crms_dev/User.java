package tech.revolicise.crms_dev;

/**
 * @author uncle_yumo
 * @CreateDate 2024/5/23
 * @School 无锡学院
 * @StudentID 22344131
 * @Description
 */
public class User {
    private String userID;
    private String userName;
    private String password;
    private String role;

    public User(String userID, String userName, String password, String role) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    @Override
    public String toString() {
        if(role.equals("root")) {
            role = "管理员";
        }else {
            role = "普通用户";
        }
        return "当前获取的用户信息为：" + "  用户ID：" + userID + "  用户名：" + userName + "  密码：" + password.charAt(0) + "******" + "  权限：" + role;
    }

}
