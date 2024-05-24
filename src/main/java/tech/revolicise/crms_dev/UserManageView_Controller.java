package tech.revolicise.crms_dev;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author uncle_yumo
 * @CreateDate 2024/5/22
 * @School 无锡学院
 * @StudentID 22344131
 * @Description
 */
public class UserManageView_Controller {
    public TableView<User> tableView_UserList;
    public TableColumn<User, String> column_userID;
    public TableColumn<User, String> column_userName;
    public TableColumn<User, String> column_passWorld;
    public TableColumn<User, String> column_userRole;
    public Button button_refreshInfo;
    public TextArea textArea_Log;
    public TextField textField_userID;
    public TextField textField_userName;
    public TextField textField_passWord;
    public Button button_deleteUser;
    public Button button_changeUserInfo;
    public Button button_addUser;
    public TextField textField_userRole;

    public ArrayList<String> logList = new ArrayList<>(); // 日志列表
    public Button button_clearLog;

    public void initialize() {
        // TODO: initialize the view here
        System.out.println("用户管理界面initialize()方法被调用");
        textArea_Log.setEditable(false); // 日志区域不可编辑
        button_deleteUser.setDisable(true); // 删除按钮不可用
        button_changeUserInfo.setDisable(true); // 修改按钮不可用
        button_addUser.setDisable(true); // 添加按钮不可用
        column_userID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        column_userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        column_passWorld.setCellValueFactory(new PropertyValueFactory<>("password"));
        column_userRole.setCellValueFactory(new PropertyValueFactory<>("role"));
    }

    public void onAction_refreshInfo(ActionEvent actionEvent) {
        System.out.println("刷新用户信息");
        logList.add("刷新用户信息\n");
        textArea_Log.setText(logList.toString());
        List<User> userList = new SqlUtil().getAllUserList(MySQL_ConnUtils.getInstance().getConnection());

        if (userList == null) {
            logList.add("刷新失败\n");
            textArea_Log.setText(logList.toString());
            return;
        }
        tableView_UserList.getItems().clear();
        tableView_UserList.setItems(FXCollections.observableArrayList(userList));
        logList.add("刷新成功\n");
        textArea_Log.setText(logList.toString());
        button_deleteUser.setDisable(false); // 删除按钮可用
        button_changeUserInfo.setDisable(false); // 修改按钮可用
        button_addUser.setDisable(false); // 添加按钮可用
    }

    public void onAction_deleteUser(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("警告");
        alert.setHeaderText("确认删除?");
        alert.setContentText("删除后无法恢复，请确认是否删除?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.NO) {
            System.out.println("取消删除");
            return;
        }
        System.out.println("删除用户");
        User user = tableView_UserList.getSelectionModel().getSelectedItem();
        if (user == null) {
            logList.add("请先选择用户\n");
            textArea_Log.setText(logList.toString());
            return;
        }
        boolean result = new SqlUtil().deleteUserByUserID(user.getUserID(), MySQL_ConnUtils.getInstance().getConnection());
        if (result) {
            logList.add("删除成功\n");
            textArea_Log.setText(logList.toString());
            onAction_refreshInfo(null);
        } else {
            logList.add("删除失败\n");
            textArea_Log.setText(logList.toString());
        }
    }
    public void onAction_changeUserInfo(ActionEvent actionEvent) {
        System.out.println("修改用户信息");
        User user = tableView_UserList.getSelectionModel().getSelectedItem(); // 获取选中的用户
        if (user == null) {
            logList.add("请先选择用户\n");
            textArea_Log.setText(logList.toString());
            return;
        }
        String userID = textField_userID.getText();
        String userName = textField_userName.getText();
        String password = textField_passWord.getText();
        String role = textField_userRole.getText();
        if (userID.isEmpty() || userName.isEmpty() || password.isEmpty() || role.isEmpty()) {
            logList.add("修改失败，请填写完整信息\n");
            textArea_Log.setText(logList.toString());
            return;
        }
        user.setUserID(userID);
        user.setUserName(userName);
        user.setPassword(password);
        user.setRole(role);
        boolean result = new SqlUtil().updateUser(user, MySQL_ConnUtils.getInstance().getConnection());
        if (result) {
            logList.add("修改成功\n");
            textArea_Log.setText(logList.toString());
            onAction_refreshInfo(null);
            textField_userID.clear();
            textField_userName.clear();
            textField_passWord.clear();
        }
    }
    public void onAction_addUser(ActionEvent actionEvent) {
        System.out.println("添加用户");
        String userID = textField_userID.getText();
        String userName = textField_userName.getText();
        String password = textField_passWord.getText();
        String role = textField_userRole.getText();
        if (userID.isEmpty() || userName.isEmpty() || password.isEmpty() || role.isEmpty()) {
            logList.add("添加失败，请填写完整信息\n");
            textArea_Log.setText(logList.toString());
            return;
        }
        User user = new User(userID, userName, password, role);
        boolean result = new SqlUtil().addUser(user, MySQL_ConnUtils.getInstance().getConnection());
        if (result) {
            logList.add("添加成功\n");
            textArea_Log.setText(logList.toString());
            onAction_refreshInfo(null);
        } else {
            logList.add("添加失败\n");
            textArea_Log.setText(logList.toString());
        }
    }

    public void onAction_clearLog(ActionEvent actionEvent) {
        System.out.println("清空日志");
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("警告");
        alert.setHeaderText("确认清空日志?");
        alert.setContentText("清空日志后将无法恢复，请确认是否清空?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            logList.clear();
            textArea_Log.clear();
        } else {
            System.out.println("取消清空日志");
        }
    }
}