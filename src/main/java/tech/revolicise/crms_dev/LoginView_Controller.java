package tech.revolicise.crms_dev;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Optional;

/**
 * @author uncle_yumo·
 * @CreateDate 2024/5/21
 * @School 无锡学院
 * @StudentID 22344131
 * @Description 这是LoginViewController的Java类，负责处理登录界面的逻辑。
 */
public class LoginView_Controller {
    @FXML
    public TextField textField_user;
    @FXML
    public PasswordField passwordField_password;
    @FXML
    public Button button_connect;
    @FXML
    public Button button_login;
    @FXML
    public Circle circle_flag;
    @FXML
    public Label label_isConnected;
    @FXML
    private AnchorPane anchorPane_login;
    private boolean isConnected = false;
    private boolean isLogin = false;

    private boolean isCirecleGreen = false;
    public String userRole = "";

    public void initialize() {

        // 连接数据库按钮事件处理
        button_connect.setOnAction(event -> {
            isConnected = MySQL_ConnUtils.getInstance().isConnected;
            System.out.println("数据库连接按钮被调用");
            if (isConnected) {
                System.out.println("正在连接数据库...");
                refreshInfo();
                isConnected = true;
            } else {
                System.out.println("数据库已连接，请勿重复连接！");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("数据库连接通知");
                alert.setHeaderText("数据库已连接");
                alert.setContentText("请勿重复连接数据库！");
                alert.showAndWait();
            }
        });

        // 登录按钮事件处理
        button_login.setOnAction(event -> {
            if(!isConnected) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("数据库连接错误");
                alert.setHeaderText("请先连接数据库！");
                alert.setContentText("请先连接数据库，再进行登录操作！");
                alert.showAndWait();
                return;
            }
            String user = "";
            String password = "";
            try {
                user = textField_user.getText();
                System.out.println("用户名：" + user);
                password = passwordField_password.getText();
                System.out.println("密码：" + password);
                int role = MySQL_ConnUtils.getInstance().authentication(user, password);
                if (role == 1) {
                    userRole = "管理员";
                } else if (role == 2) {
                    userRole = "普通用户";
                } else {
                    return;
                }
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // 创建确认对话框
                alert.setTitle("身份验证成功"); // 设置标题
                isLogin = true;
                alert.setHeaderText("您当前登录的身份是：" + userRole); // 设置头部文本
                alert.setContentText("进入系统"); // 设置内容文本
                Optional<ButtonType> isOK = alert.showAndWait(); // 显示确认对话框
                if (isOK.get() == ButtonType.OK) {
                    anchorPane_login.getScene().getWindow().hide(); // 关闭登录界面
                    System.out.println("登录成功！");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("捕获异常，原因: " + e.getMessage());
            }
        });
    }
    // anchorPane_login.requestLayout(); // 刷新界面
    public void refreshInfo() { // 刷新数据库连接信息
        if (isConnected) {
            label_isConnected.setText("数据库已连接");
            circle_flag.setFill(Color.GREEN);
            isCirecleGreen = true;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("数据库连接通知");
            alert.setHeaderText("连接成功");
            alert.setContentText("连接数据库成功！");
            alert.showAndWait();
        }
    }
    public boolean isConnected() {
        return isConnected;
    }

    public boolean isLogin() {
        return isLogin;
    }
}
