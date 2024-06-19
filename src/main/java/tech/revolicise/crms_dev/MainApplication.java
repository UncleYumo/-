package tech.revolicise.crms_dev;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

import java.sql.Connection; // 用于处理数据库连接
import java.sql.DriverManager; // 用于加载驱动程序
import java.sql.SQLException; // 用于处理SQL异常
import java.sql.ResultSet; // 用于处理结果集，即所查询到的记录
import java.util.Optional;

public class MainApplication extends Application {
    public String userName;
    public String userID;
    public String userRole;

    @Override
    public void start(Stage primaryStage) throws IOException {
        System.out.println("start方法调用成功");
        System.out.println(System.getProperty("java.class.path")); // 打印类路径

        // 加载登录界面
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login-view.fxml"));
        Scene login_scene = new Scene(fxmlLoader.load(), 670, 400);
        LoginView_Controller loginView_Controller = fxmlLoader.getController();

        primaryStage.setTitle("登录");
        primaryStage.setScene(login_scene);
        primaryStage.show();

        // 创建BorderPane作为根节点
        BorderPane root = new BorderPane();
        // 创建顶级菜单栏
        MenuBar menuBar = new MenuBar();
        Button button_computerRent = new Button("电脑租借");
        Button button_infoStat = new Button("信息统计");
        Button button_userManage = new Button("用户管理");
        Button button_computerManage = new Button("电脑管理");
        Button button_sysExit = new Button("退出系统");
        HBox hbox = new HBox(menuBar, button_computerRent, button_infoStat, button_userManage, button_computerManage, button_sysExit);
        root.setTop(hbox);
        Stage mainStage = new Stage();
        mainStage.setTitle("计算机机房管理系统");
        mainStage.setScene(new Scene(root, 1200, 800));

        // 当前场景隐藏后触发事件（开启主界面）
        primaryStage.setOnHidden(event -> {
            if (loginView_Controller.isConnected() && loginView_Controller.isLogin()) {
                mainStage.show();
            }
        });

        // 初始化电脑租借、信息统计、用户管理、电脑管理界面
        FXMLLoader fxmlLoader_computerRent = new FXMLLoader();
        fxmlLoader_computerRent.setLocation(MainApplication.class.getResource("computerRent-view.fxml"));
        AnchorPane computerRent_view = (AnchorPane) fxmlLoader_computerRent.load();

        FXMLLoader fxmlLoader_infoStat = new FXMLLoader();
        fxmlLoader_infoStat.setLocation(MainApplication.class.getResource("infoStat-view.fxml"));
        AnchorPane infoStat_view = (AnchorPane) fxmlLoader_infoStat.load();

        FXMLLoader fxmlLoader_userManage = new FXMLLoader();
        fxmlLoader_userManage.setLocation(MainApplication.class.getResource("userManage-view.fxml"));
        AnchorPane userManage_view = (AnchorPane) fxmlLoader_userManage.load();

        FXMLLoader fxmlLoader_computerManage = new FXMLLoader();
        fxmlLoader_computerManage.setLocation(MainApplication.class.getResource("computerManage-view.fxml"));
        AnchorPane computerManage_view = (AnchorPane) fxmlLoader_computerManage.load();

        // 监听全局键盘事件
        primaryStage.getScene().setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == javafx.scene.input.KeyCode.ESCAPE) {
                try {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("提示");
                    alert.setHeaderText("确认退出系统?");
                    alert.setContentText("您将退出系统，是否确认?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        System.out.println("程序已退出");
                        stop();
                        System.exit(0);
                    } else {
                        System.out.println("取消退出系统");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("退出系统菜单点击事件异常：" + e.getMessage());
                    return;
                }
            }
        }); // ESC键退出程序

        mainStage.getScene().setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == javafx.scene.input.KeyCode.ESCAPE) {
                try {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("提示");
                    alert.setHeaderText("确认退出系统?");
                    alert.setContentText("您将退出系统，是否确认?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        System.out.println("程序已退出");
                        stop();
                    } else {
                        System.out.println("取消退出系统");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("退出系统菜单点击事件异常：" + e.getMessage());
                    return;
                }
            }
        }); // ESC键退出程序

        // 设置菜单按钮样式（简约美观，富有层次与设计感，素雅清新，风格灵动统一，
        button_computerRent.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #d9d9d9; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-font-size: 16px; -fx-text-fill: #000000; -fx-padding: 10px 20px 10px 20px; -fx-shadow-offset-x: 2px; -fx-shadow-offset-y: 2px; -fx-shadow-radius: 5px; -fx-shadow-color: #d9d9d9;");
        button_infoStat.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #d9d9d9; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-font-size: 16px; -fx-text-fill: #000000; -fx-padding: 10px 20px 10px 20px; -fx-shadow-offset-x: 2px; -fx-shadow-offset-y: 2px; -fx-shadow-radius: 5px; -fx-shadow-color: #d9d9d9;");
        button_userManage.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #d9d9d9; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-font-size: 16px; -fx-text-fill: #000000; -fx-padding: 10px 20px 10px 20px; -fx-shadow-offset-x: 2px; -fx-shadow-offset-y: 2px; -fx-shadow-radius: 5px; -fx-shadow-color: #d9d9d9;");
        button_computerManage.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #d9d9d9; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-font-size: 16px; -fx-text-fill: #000000; -fx-padding: 10px 20px 10px 20px; -fx-shadow-offset-x: 2px; -fx-shadow-offset-y: 2px; -fx-shadow-radius: 5px; -fx-shadow-color: #d9d9d9;");
        button_sysExit.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #d9d9d9; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-font-size: 16px; -fx-text-fill: #000000; -fx-padding: 10px 20px 10px 20px; -fx-shadow-offset-x: 2px; -fx-shadow-offset-y: 2px; -fx-shadow-radius: 5px; -fx-shadow-color: #d9d9d9;");

        // 监听菜单按钮点击事件
        button_computerRent.setOnAction(event -> {
            System.out.println("电脑租借菜单点击事件");
            root.setCenter(computerRent_view);
        });
        button_infoStat.setOnAction(event -> {
            System.out.println("信息统计菜单点击事件");
            if(!loginView_Controller.userRole.equals("管理员")){
                System.out.println("非管理员用户无权访问信息统计页面");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("错误");
                alert.setHeaderText("无权访问");
                alert.setContentText("您没有权限访问该页面，如有需要请联系管理员获得权限！");
                alert.showAndWait();
                return;
            }
            root.setCenter(infoStat_view);
        });
        button_userManage.setOnAction(event -> {
            System.out.println("用户管理菜单点击事件");
            if(!loginView_Controller.userRole.equals("管理员")){
                System.out.println("非管理员用户无权访问信息统计页面");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("错误");
                alert.setHeaderText("无权访问");
                alert.setContentText("您没有权限访问该页面，如有需要请联系管理员获得权限！");
                alert.showAndWait();
                return;
            }
            root.setCenter(userManage_view);
        });
        button_computerManage.setOnAction(event -> {
            System.out.println("电脑管理菜单点击事件");
            if(!loginView_Controller.userRole.equals("管理员")){
                System.out.println("非管理员用户无权访问信息统计页面");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("错误");
                alert.setHeaderText("无权访问");
                alert.setContentText("您没有权限访问该页面，如有需要请联系管理员获得权限！");
                alert.showAndWait();
                return;
            }
            root.setCenter(computerManage_view);
        });
        button_sysExit.setOnAction(event -> {
            System.out.println("退出系统菜单点击事件");
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("提示");
                alert.setHeaderText("确认退出系统?");
                alert.setContentText("您将退出系统，是否确认?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    System.out.println("程序已退出");
                    stop();
                } else {
                    System.out.println("取消退出系统");
                }
            } catch (Exception e) {
                System.out.println("退出系统菜单点击事件异常：" + e.getMessage());
                return;
            }
        });
    }

    public static void main(String[] args) {
        launch(); // 启动JavaFX程序
    }

    public void init() {
        System.out.println("init方法调用成功");
        try {
            super.init();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("初始化异常：" + e.getMessage());
        }
    }

    public void stop() {
        System.out.println("stop方法调用成功");
        // 关闭数据库连接
        try {
            MySQL_ConnUtils.getInstance().closeConnection();
            System.exit(0);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
