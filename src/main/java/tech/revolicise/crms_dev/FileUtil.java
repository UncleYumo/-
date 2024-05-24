package tech.revolicise.crms_dev;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author uncle_yumo
 * @CreateDate 2024/5/22
 * @School 无锡学院
 * @StudentID 22344131
 * @Description
 */
public class FileUtil {
    public List<Device> readDeviceData() {
        String filePath = "src/main/tableData/deviceData.txt";
        List<Device> result = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            while(line != null) {
                String[] dataArray = line.split(",");
                String deviceID = dataArray[0];
                String deviceType = dataArray[1];
                String status = dataArray[2];
                String location = dataArray[3];
                result.add(new Device(deviceID, deviceType, status, location));
                line = reader.readLine();
            }
            return result;
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public List<Device> addDeviceData(Device device) {
        String filePath = "src/main/tableData/deviceData.txt";
        List<Device> result = new ArrayList<>();
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true))) { //true表示追加
            writer.write(device.toString());
            writer.newLine(); //换行
            writer.flush(); //刷新缓冲区
            return result;
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
