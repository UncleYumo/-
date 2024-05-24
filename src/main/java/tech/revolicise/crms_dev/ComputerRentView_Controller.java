package tech.revolicise.crms_dev;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

/**
 * @author uncle_yumo
 * @CreateDate 2024/5/22
 * @School 无锡学院
 * @StudentID 22344131
 * @Description
 */
public class ComputerRentView_Controller {

    public TableView<Device> tableView_device;
    public TableColumn<Device, String> column_deviceID;
    public TableColumn<Device, String> column_deviceType;
    public TableColumn<Device, String> column_status;
    public TableColumn<Device, String> column_location;
    public TextArea textArea_operationInfo;
    public Button button_rent;
    public TextField textField_deviceID;
    public Button button_return;
    public TextArea textArea_currentUserInfo;
    public Button button_refresh;
    public TextArea textArea_myRecord;
    public Button button_myRecord;
    public Button button_clearLog;
    private AnchorPane anchorPane;

    private User currentUser;
    private String currentRole = "";

    private boolean isRefreshButtonClicked = false;

    List<String> textList = new ArrayList<>();
    List<String> textRecordList = new ArrayList<>();

    public void initialize() {
        System.out.println("电脑租赁界面initialize()方法被调用");
        textArea_currentUserInfo.setEditable(false); // 设置textArea不可编辑
        textArea_operationInfo.setEditable(false); // 设置textArea不可编辑
        textArea_myRecord.setEditable(false); // 设置textArea不可编辑
        // TODO Auto-generated method stub
        column_deviceID.setCellValueFactory(new PropertyValueFactory<>("deviceID")); // 设置列名与属性名的映射关系
        column_deviceType.setCellValueFactory(new PropertyValueFactory<>("deviceType"));
        column_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        column_location.setCellValueFactory(new PropertyValueFactory<>("location"));
    }

    public void onAction_refreshTable(ActionEvent actionEvent) {
        textList.add("正在刷新设备列表...\n");
        textArea_operationInfo.setText(textList.toString());
        List<Device> deviceList = new SqlUtil().getAllDeviceList(MySQL_ConnUtils.getInstance().getConnection());
        tableView_device.setItems(FXCollections.observableList(deviceList));
        String currentUserName = MySQL_ConnUtils.getInstance().userName;
        String tempRole = MySQL_ConnUtils.getInstance().userRole;
        if (tempRole.equals("root")) {
            currentRole = "管理员";
        }
        if (tempRole.equals("user")){
            currentRole = "普通用户";
        }
        currentUser = new SqlUtil().getUserByUserName(currentUserName, MySQL_ConnUtils.getInstance().getConnection());
        String currentUserInfo = "当前用户：" + currentUser.getUserName() + "  " + "当前用户ID：" + currentUser.getUserID() + "  " + "当前用户权限：" + currentRole + "\n";
        textArea_currentUserInfo.setText(currentUserInfo);
        textList.add("欢迎使用电脑租赁系统！\n");
        textList.add(currentUser.toString() + "\n");
        textArea_operationInfo.setText(textList.toString());
        if(!isRefreshButtonClicked){
            isRefreshButtonClicked = true;
        }
    }

    public void onAction_rentDevice(ActionEvent actionEvent) {
        if(!isRefreshButtonClicked){
            textList.add("请先刷新设备列表！\n");
            textArea_operationInfo.setText(textList.toString());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText("请先刷新设备列表！");
            alert.setContentText("请刷新设备列表后再进行借出操作！");
            alert.showAndWait();
            return;
        }

        String deviceID = textField_deviceID.getText();
        if (deviceID.isEmpty()) {
            textList.add("请输入设备ID！\n");
            textArea_operationInfo.setText(textList.toString());
            return;
        }
        Device device = new SqlUtil().getDeviceByID(deviceID, MySQL_ConnUtils.getInstance().getConnection());
        if(device == null) {
            textList.add("正在判断设备状态...\n");
            textList.add("设备ID不存在！\n");
            textArea_operationInfo.setText(textList.toString());
            return;
        }
        String currentDeviceInfo = device.toString() + "\n";
        textList.add("正在判断设备状态...\n");
        textList.add("当前设备信息：" + currentDeviceInfo + "\n");
        textArea_operationInfo.setText(textList.toString());
        if(device.getStatus().equals("闲置")) {
            System.out.println("设备状态为闲置，可以借出！");
            textList.add("设备状态为闲置，可以借出！\n");
            // 判断可以借出时，记录当前时间startTime，传入设备ID、所借用户ID、借出时间，返回成功或失败信息
            // 获取当前实时时间
            String borrowTime = java.time.LocalDateTime.now().toString();
            System.out.println("借出时间：" + borrowTime);
            textList.add("借出时间：" + borrowTime + "\n");
            textArea_operationInfo.setText(textList.toString());
            // 借出设备
            boolean result = new SqlUtil().borrowDevice(deviceID, currentUser.getUserID(), borrowTime, MySQL_ConnUtils.getInstance().getConnection());
            if (result) {
                textList.add("借出成功！\n");
                textArea_operationInfo.setText(textList.toString());
            } else {
                textList.add("借出失败！\n");
                textArea_operationInfo.setText(textList.toString());
            }
        } else {
            System.out.println("设备状态不为闲置，不能借出！");
            textList.add("设备状态不为闲置，不能借出！\n");
            textArea_operationInfo.setText(textList.toString());
        }
    }

    public void onAction_returnDevice(ActionEvent actionEvent) {
        if(!isRefreshButtonClicked){
            textList.add("请先刷新设备列表！\n");
            textArea_operationInfo.setText(textList.toString());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText("请先刷新设备列表！");
            alert.setContentText("请刷新设备列表后再进行归还操作！");
            alert.showAndWait();
            return;
        }
        textList.add("正在读取设备状态...\n");
        textArea_operationInfo.setText(textList.toString());
        String deviceID = textField_deviceID.getText();
        if (deviceID.isEmpty()) {
            textList.add("请输入设备ID！\n");
            textArea_operationInfo.setText(textList.toString());
            return;
        }
        Device device = new SqlUtil().getDeviceByID(deviceID, MySQL_ConnUtils.getInstance().getConnection());
        System.out.println("RentView获取到的设备信息：" + device.toString());
        if(device == null) {
            textList.add("设备ID不存在！归还失败！\n");
            textArea_operationInfo.setText(textList.toString());
            return;
        }
        String currentDeviceInfo = device.toString() + "\n";
        textList.add("正在判断设备状态...\n");
        textArea_operationInfo.setText(textList.toString());
        if(device.getStatus().equals("借用中")) {
            System.out.println("设备状态为借用中，可以归还！");
            textList.add("设备状态为借用中，可以归还！\n");
            textArea_operationInfo.setText(textList.toString());
            // 判断可以归还时，记录当前时间endTime，传入设备ID、所归还用户ID、归还时间，返回成功或失败信息
            // 获取当前实时时间
            String returnTime = java.time.LocalDateTime.now().toString();
            System.out.println("归还时间：" + returnTime);
            textList.add("归还时间：" + returnTime + "\n");
            textArea_operationInfo.setText(textList.toString());
            // 归还设备
            ChangeRecordTable changeRecordTable = new SqlUtil().returnDevice(deviceID, currentUser.getUserID(), returnTime, MySQL_ConnUtils.getInstance().getConnection());
            if (changeRecordTable != null) {
                textList.add("归还成功！\n");
            } else {
                textList.add("归还失败！请检查您的设备ID是否正确！\n");
                textArea_operationInfo.setText(textList.toString());
            }
        } else {
            System.out.println("设备状态不为借用中，不能归还！");
            textList.add("设备状态不为借用中，不能归还！\n");
            textArea_operationInfo.setText(textList.toString());
        }
    }

    public void onAction_getMyRecord(ActionEvent actionEvent) {
        if(!isRefreshButtonClicked){
            textList.add("请先刷新设备列表！\n");
            textArea_operationInfo.setText(textList.toString());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText("请先刷新设备列表！");
            alert.setContentText("请刷新设备列表后再查看借用记录！");
            alert.showAndWait();
            return;
        }
        textArea_myRecord.clear();
        textRecordList.add("当前用户的借用记录如下：\n");
        textArea_operationInfo.setText(textList.toString());
        ArrayList<ChangeRecordTable> changeRecordTables = new SqlUtil().getChangeRecordTableListByUserID(currentUser.getUserID(), MySQL_ConnUtils.getInstance().getConnection());
        if(changeRecordTables == null) {
            textRecordList.add("当前用户暂无借用记录！\n\n");
            textArea_myRecord.setText(textRecordList.toString());
            return;
        }
        for (ChangeRecordTable changeRecordTable : changeRecordTables) {
            String recordID = changeRecordTable.getRecordID();
            String deviceID = changeRecordTable.getDeviceID();
            Device device = new SqlUtil().getDeviceByID(deviceID, MySQL_ConnUtils.getInstance().getConnection());
            String startTime = changeRecordTable.getStartTime();
            String endTime = changeRecordTable.getEndTime();
            String amount = changeRecordTable.getAmount();
            String recordInfo = "\n借用记录ID：" + recordID + "  " + "设备ID：" + deviceID + "  " + "设备类型：" + device.getDeviceType() + "\n" + "借用时间：" + startTime + "  " + "归还时间：" + endTime + "  " + "借用金额：" + amount + "\n";
            textRecordList.add(recordInfo);
        }
        textRecordList.add("以上为当前用户的借用记录！\n\n");
        textArea_myRecord.setText(textRecordList.toString());
    }

    public void onAction_ClearLog(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("警告");
        alert.setHeaderText("确认清空操作日志？");
        alert.setContentText("操作日志将被清空，确认继续？");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            textList.clear();
            textRecordList.clear();
            textArea_operationInfo.clear();
            textArea_myRecord.clear();
        }
    }
}