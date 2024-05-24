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
public class ComputerManageView_Controller {
    public TableView<Device> tableView_deviceList;
    public TableColumn<Device, String> column_deviceType;
    public TableColumn<Device, String> column_deviceID;
    public TableColumn<Device, String> column_status;
    public TableColumn<Device, String> column_location;
    public Button button_refreshInfo;
    public TextArea textArea_Log;
    public TextField textField_deviceIDID;
    public TextField textField_deviceType;
    public TextField textField_deviceStatus;
    public Button button_deleteDevice;
    public Button button_changeDeviceInfo;
    public Button button_addDevice;
    public TextField textField_location;
    public Button button_clearLog;

    public ArrayList<String> logList = new ArrayList<>();


    public void initialize() {
        System.out.println("计算机管理界面initialize()方法被调用");
        textArea_Log.setEditable(false);//日志区域不可编辑
        button_addDevice.setDisable(true);//默认不允许添加设备
        button_changeDeviceInfo.setDisable(true);//默认不允许修改设备信息
        button_deleteDevice.setDisable(true);//默认不允许删除设备
        column_deviceType.setCellValueFactory(new PropertyValueFactory<>("deviceType"));
        column_deviceID.setCellValueFactory(new PropertyValueFactory<>("deviceID"));
        column_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        column_location.setCellValueFactory(new PropertyValueFactory<>("location"));
    }

    public void onAction_refreshInfo(ActionEvent actionEvent) {
        System.out.println("刷新设备信息");
        logList.add("刷新设备信息\n");
        textArea_Log.setText(logList.toString());
        List<Device> deviceList = new SqlUtil().getAllDeviceList(MySQL_ConnUtils.getInstance().getConnection());

        if(deviceList == null) {
            logList.add("获取设备列表失败\n");
            textArea_Log.setText(logList.toString());
            return;
        }
        tableView_deviceList.getItems().clear();
        tableView_deviceList.setItems((FXCollections.observableArrayList(deviceList)));
        logList.add("获取设备列表成功\n");
        textArea_Log.setText(logList.toString());
        button_addDevice.setDisable(false);//允许添加设备
        button_changeDeviceInfo.setDisable(false);//允许修改设备信息
        button_deleteDevice.setDisable(false);//允许删除设备
    }

    public void onAction_deleteDevice(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("警告");
        alert.setHeaderText("确认删除?");
        alert.setContentText("删除后不可恢复，请确认是否要删除?");
        alert.showAndWait();
        if(alert.getResult() == ButtonType.NO) {
            System.out.println("取消删除");
            return;
        }
        System.out.println("删除设备");
        Device device = tableView_deviceList.getSelectionModel().getSelectedItem();
        if (device == null) {
            logList.add("请先选择要删除的设备\n");
            textArea_Log.setText(logList.toString());
            return;
        }
        int result = new SqlUtil().deleteDeviceByDeviceID(MySQL_ConnUtils.getInstance().getConnection(), device.getDeviceID());
        if (result == 1) {
            logList.add("删除设备成功\n");
            textArea_Log.setText(logList.toString());
            onAction_refreshInfo(null);
        } else {
            logList.add("删除设备失败\n");
            textArea_Log.setText(logList.toString());
        }
    }

    public void onAction_changeDeviceInfo(ActionEvent actionEvent) {
        System.out.println("修改设备信息");
        Device device = tableView_deviceList.getSelectionModel().getSelectedItem();
        if (device == null) {
            logList.add("请先选择要修改的设备\n");
            textArea_Log.setText(logList.toString());
            return;
        }
        String deviceID = textField_deviceIDID.getText();
        String deviceType = textField_deviceType.getText();
        String deviceStatus = textField_deviceStatus.getText();
        String location = textField_location.getText();
        if(deviceID.isEmpty() || deviceType.isEmpty() || deviceStatus.isEmpty() || location.isEmpty()) {
            logList.add("设备信息不能为空\n");
            textArea_Log.setText(logList.toString());
            return;
        }
        device.setDeviceID(deviceID);
        device.setDeviceType(deviceType);
        device.setStatus(deviceStatus);
        device.setLocation(location);
        int result = new SqlUtil().updateDevice(MySQL_ConnUtils.getInstance().getConnection(), device);
        if (result == 1) {
            logList.add("修改设备信息成功\n");
            textArea_Log.setText(logList.toString());
            onAction_refreshInfo(null);
            textField_deviceIDID.clear();
            textField_deviceType.clear();
            textField_deviceStatus.clear();
            textField_location.clear();
        } else {
            logList.add("修改设备信息失败\n");
            textArea_Log.setText(logList.toString());
        }
    }

    public void onAction_addDevice(ActionEvent actionEvent) {
        System.out.println("添加设备");
        String deviceID = textField_deviceIDID.getText();
        String deviceType = textField_deviceType.getText();
        String Status = textField_deviceStatus.getText();
        String location = textField_location.getText();
        if(deviceID.isEmpty() || deviceType.isEmpty() || Status.isEmpty() || location.isEmpty()) {
            logList.add("添加失败，请填写完整信息\n");
            textArea_Log.setText(logList.toString());
            return;
        }
        Device device = new Device(deviceID, deviceType, Status, location);
        boolean result = new SqlUtil().addDevice(device, MySQL_ConnUtils.getInstance().getConnection());
        if(result) {
            logList.add("添加设备成功\n");
            textArea_Log.setText(logList.toString());
            onAction_refreshInfo(null);
            textField_deviceIDID.clear();
            textField_deviceType.clear();
            textField_deviceStatus.clear();
            textField_location.clear();
        } else {
            logList.add("添加设备失败\n");
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
