package tech.revolicise.crms_dev;

/**
 * @author uncle_yumo
 * @CreateDate 2024/5/23
 * @School 无锡学院
 * @StudentID 22344131
 * @Description
 */
public class ChangeRecordTable {
    private String recordID;
    private String userID;
    private String deviceID;
    private String startTime;
    private String endTime;
    private String amount; // 金额 单位：元 保留两位小数

    public ChangeRecordTable(String recordID, String userID, String deviceID, String startTime, String endTime, String amount) {
        this.recordID = recordID;
        this.userID = userID;
        this.deviceID = deviceID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.amount = amount;
    }

    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    @Override

    public String toString() {
        String recordid;
        if(this.recordID != null) {
            recordid = this.recordID;
        }else {
            recordid = "null";
        }
        String userid;

        if(this.userID != null) {
            userid = this.userID;
        }else {
            userid = "null";
        }
        String deviceid;
        if(this.deviceID != null) {
            deviceid = this.deviceID;
        }else {
            deviceid = "null";
        }
        String starttime;
        if(this.startTime != null) {
            starttime = this.startTime;
        }else {
            starttime = "null";
        }
        String endtime;
        if(this.endTime != null) {
            endtime = this.endTime;
        }else {
            endtime = "null";
        }
        String amount;
        if(this.amount != null) {
            amount = this.amount;
        }else {
            amount = "null";
        }
        return "ChangeRecordTable{" +
                "recordID='" + recordid + '\'' +
                ", userID='" + userid + '\'' +
                ", deviceID='" + deviceid + '\'' +
                ", startTime='" + starttime + '\'' +
                ", endTime='" + endtime + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
