package com.thunisoft.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thunisoft.pojo.Task;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.Source;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangliujie
 * @Description
 * @date 2020/3/9.
 */
public class ETLInterfaceTest {

    public static void main(String[] args) {
        String taskId = "np_bzk_ql2.4.2";
        String taskGroup = "np到标准库_全量";
        String taskName = "2.5.2NP到标准库数据采集_全量";
        String url = "http://172.16.32.39:5080/sjcjgj/api/task/status?type={type}&id={id}&createTime={createTime}";

        String startTime = "21:00:00";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time = LocalTime.parse(startTime, dtf);
        LocalDate day = LocalDate.now().plusDays(-1);
        LocalDateTime localDateTime = LocalDateTime.of(day, time);
        String createTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


        Map<String, Object> params = new HashMap<>();
        params.put("type", 1);
        params.put("id", taskId);
        params.put("createTime", createTime);

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(params, headers);
        String fhxx = rest.getForObject(url, String.class, params);
        System.out.println(fhxx);

        Map taskGroupDetail = (Map)JSONObject.parse(fhxx);
        Object data = taskGroupDetail.get("data");
        JSONArray array = JSONArray.parseArray(JSONObject.toJSONString(data));
        List<Task> taskDetails = JSONObject.parseArray(array.toJSONString(), Task.class);
        System.out.println(taskDetails);

        int hour = time.getHour();
        int minute = time.getMinute();
        int second = time.getSecond();
        String cron = second + " " + minute + " " + hour + " * * ?";

        System.out.println(cron);

        List<Map<String, Object>> exSjbs = new ArrayList<>();
        Map<String, Object> exSjb1 = new HashMap<>();
        Map<String, Object> exSjb2 = new HashMap<>();
        exSjb1.put("c_name", "zip1");
        exSjb1.put("dt_rksj", "2020-03-24 15:00:00");
        exSjb2.put("c_name", "zip2");
        exSjb2.put("dt_rksj", "2020-03-24 16:00:00");
        exSjbs.add(exSjb1);
        exSjbs.add(exSjb2);


        String sql = "insert into db_sjb.temp_sjb_export values ";
        StringBuffer sqlStrBuf = new StringBuffer(sql);
        for (Map<String, Object> exSjb : exSjbs) {
            String zipName = (String)exSjb.get("c_name");
            String rksj = (String)exSjb.get("dt_rksj");
            sqlStrBuf.append("('" + zipName + "','" + rksj + "'),");
        }
        sql = sqlStrBuf.toString();
        //截取最后一个逗号
        sql = sql.substring(0, sql.length() - 1);
        System.out.println(sql);

        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task();
        task1.setStatus("1");
        task1.setTaskId("taskId1");
        task1.setTaskName("taskName1");
        Task task2 = new Task();
        task2.setStatus("2");
        task2.setTaskId("taskId2");
        task2.setTaskName("taskName2");
        Task task3 = new Task();
        task3.setStatus("3");
        task3.setTaskId("taskId3");
        task3.setTaskName("taskName3");
        Task task4 = new Task();
        task4.setStatus("4");
        task4.setTaskId("taskId4");
        task4.setTaskName("taskName4");

        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        tasks.add(task4);

        Integer pageNum = 2;
        Integer pageSize = 2;

        System.out.println(tasks.subList((pageNum - 1) * pageSize, pageNum * pageSize));



    }

}
