package tech.revolicise.crms_dev;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;

/**
 * @author uncle_yumo
 * @CreateDate 2024/5/22
 * @School 无锡学院
 * @StudentID 22344131
 * @Description
 */
public class InfoStatView_Controller {
    public TextArea textArea_allInfo;
    public Button button_getAllInfo;

    public void onAction_getAllInfo(ActionEvent actionEvent) {
        // 要求如下
        // 获取所有租借设备的用户信息<ChangeRecordTable>
        // 获取所有设备的状态信息<Device.getStatus()>
        // 获取所有用户的数量<User>
        // 获取所有设备的数量<Device>
        // 获取出租设备目前的总营收<ChangeRecordTable.getAmount()>
        // 显示信息到textArea_allInfo
        // 信息分门别类，用空行分隔，格式美观
        // 要求如上

        List<ChangeRecordTable> changeRecordTables = new SqlUtil().getAllChangeRecordTableList(MySQL_ConnUtils.getInstance().getConnection());
        List<Device> devices = new SqlUtil().getAllDeviceList(MySQL_ConnUtils.getInstance().getConnection());
        List<User> users = new SqlUtil().getAllUserList(MySQL_ConnUtils.getInstance().getConnection());
        int userCount = users.size();
        int deviceCount = devices.size();
        double totalRevenue = 0;
        for (ChangeRecordTable changeRecordTable : changeRecordTables) {
            totalRevenue += Double.parseDouble(changeRecordTable.getAmount());
        }
        String allInfo = "租借设备的用户信息：\n";
        for (ChangeRecordTable changeRecordTable : changeRecordTables) {
            allInfo += changeRecordTable.toString() + "\n";
        }
        allInfo += "\n设备的状态信息：\n";
        for (Device device : devices) {
            allInfo += device.toString() + "\n";
        }
        allInfo += "\n所有用户信息：\n";
        for (User user : users) {
            allInfo += user.toString() + "\n";
        }
        allInfo += "\n所有用户的数量：" + userCount + "\n";
        allInfo += "所有设备的数量：" + deviceCount + "\n";
        allInfo += "出租设备目前的总营收：" + totalRevenue + "\n";
        textArea_allInfo.setText(allInfo);
        System.out.println(allInfo);
    }
}
