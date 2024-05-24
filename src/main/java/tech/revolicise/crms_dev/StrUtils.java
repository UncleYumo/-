package tech.revolicise.crms_dev;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author uncle_yumo
 * @CreateDate 2024/5/23
 * @School 无锡学院
 * @StudentID 22344131
 * @Description
 */
public class StrUtils {
    // 将java.time.LocalTime转换为"yyyy-MM-dd-HH:mm:ss"格式的字符串
    public String transJTimeToNumTime(String inputTime) {
        // 定义原始格式和目标格式
        DateTimeFormatter originFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
        DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");
        LocalDateTime outputTime = LocalDateTime.parse(inputTime, originFormatter);
        return outputTime.format(targetFormatter);
    }
    public String transSqlTimeToNumTime(String inputTime) {
        // 定义原始格式和目标格式
        DateTimeFormatter originFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");
        LocalDateTime outputTime = LocalDateTime.parse(inputTime, originFormatter);
        return outputTime.format(targetFormatter);
    }
    public String transNumTimeToSqlTime(String inputTime) {
        // 定义原始格式和目标格式
        DateTimeFormatter originFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");
        DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime outputTime = LocalDateTime.parse(inputTime, originFormatter);
        return outputTime.format(targetFormatter);
    }
    public double caluculateAmount(String startTime, String endTime) {
        // 计算借用时长
        // 计算开始时间和结束时间的差值（转换成小时数）
        // 提取开始时间的年月日
        String[] startArr = startTime.split("-");
        int startYear = Integer.parseInt(startArr[0]);
        int startMonth = Integer.parseInt(startArr[1]);
        int startDay = Integer.parseInt(startArr[2]);
        // 提取结束时间的年月日
        String[] endArr = endTime.split("-");
        int endYear = Integer.parseInt(endArr[0]);
        int endMonth = Integer.parseInt(endArr[1]);
        int endDay = Integer.parseInt(endArr[2]);
        // 计算天数总间隔：
        int dayNum = (endYear - startYear) * 365 + (endMonth - startMonth) * 30 + (endDay - startDay);
        // 计算小时数总间隔：
        int hourNum = dayNum * 24;
        System.out.println("借用时长：" + dayNum + "天；即：" + hourNum + "小时");
        // 计算借用金额
        return (hourNum * 2.5 + 2.5);
    }
}
