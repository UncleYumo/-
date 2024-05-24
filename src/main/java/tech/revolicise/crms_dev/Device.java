package tech.revolicise.crms_dev;

/**
 * @author uncle_yumo
 * @CreateDate 2024/5/22
 * @School 无锡学院
 * @StudentID 22344131
 * @Description
 */
public class Device {
     private String deviceID;
     private String deviceType;
     private String status;
     private String location;

    public Device() {
    }

    public Device(String deviceID, String deviceType, String status, String location) {
        this.deviceID = deviceID;
        this.deviceType = deviceType;
        this.status = status;
        this.location = location;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        if(deviceID == null || deviceType == null || status == null || location == null) {
            return "当前获取的设备信息为空";
        }
        return "当前获取的设备信息为：设备ID：" + deviceID + "  设备类型：" + deviceType + "  设备状态：" + status + "  设备位置：" + location;
    }
}
