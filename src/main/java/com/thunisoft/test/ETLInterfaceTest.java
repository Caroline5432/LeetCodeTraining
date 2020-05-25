package com.thunisoft.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thunisoft.pojo.Task;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import sun.rmi.runtime.Log;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        // insertETLTask();
        // String ajbs = "1,2,3,";
        // ajbs = ajbs.substring(0, ajbs.length() - 1);
        // System.out.println(ajbs);
        // insertExport();
        // statusTask();
        /*
        String zxExKssj = "10 0 21 * * ?";
        String[] zxExkssjList = zxExKssj.split(" ");
        for (int i = 0; i < 3; i++) {
            if (zxExkssjList[i].length() < 2) {
                zxExkssjList[i] = "0" + zxExkssjList[i];
            }
        }
        String time = zxExkssjList[2] + ":" + zxExkssjList[1] + ":" + zxExkssjList[0];
        System.out.println(getDate(-1, time));*/
        //Integer importFormat = getImportFormat(4);
        //System.out.println(importFormat);

       // insertLkbd();
        /*String date1 = getDate1(1, "00:00:00");
        System.out.println(date1);*/
        //listSub();

        //testSjcjgjStatus();

        String url = "http://172.16.32.41:8080/sjcjgj";
        //获取触发任务所需的参数
        Map<String, Object> taskParams = new HashMap<>();
        taskParams.put("#{task.CId}", "1122");
        taskParams.put("#{kssj}", "'2020-01-01'");
        taskParams.put("#{jssj}", "'2020-12-31'");
        taskParams.put("#{type}", "sp");
        taskParams.put("#{gyid}", "100");

        Map<String, Object> params = new HashMap<>();
        params.put("type", 2);
        params.put("id", "sjfwjk_lkbd");
        params.put("params", JSONObject.toJSON(taskParams));

        //触发任务，返回cteateTime
        Map<String, Object> resultMap = ETLTaskUtil.insert(url, params);
        Object data = resultMap.get("data");
        Map<String, String> dataMap = (Map)JSONObject.parse(JSONObject.toJSONString(data));
        String createTime = dataMap.get("createTime");

        System.out.println("createTime:" + createTime);


        //监控采集任务状态
        boolean isStop = true;
        Map<String, Object> ztParams = new HashMap<>();
        ztParams.put("type", 2);
        ztParams.put("id", "sjfwjk_lkbd");
        ztParams.put("createTime", createTime);
        String etlStatus = null;
        int i = 1;
        while (isStop) {
            etlStatus = ETLTaskUtil.etlStatus(url, ztParams);
            //比对任务成功或者失败，循环结束
            if (!(JkConstants.ETL_STATUS_WAIT.equals(etlStatus) || JkConstants.ETL_STATUS_RUNNING.equals(etlStatus))) {
                isStop = false;
            }
            System.out.println("第" + i + "次：" + etlStatus);
            i++;
        }

    }

    public static void testSjcjgjStatus() {
        String url = "http://172.16.32.41:8080/sjcjgj/api/task/insert?type={type}&id={id}&startTime={startTime}&endTime={endTime}";
        Map<String, Object> params = new HashMap<>();
        params.put("type", 1);
        params.put("id", "np_bzk_zl");
        params.put("startTime", "2020-04-02 15:00:00");
        params.put("endTime", "2020-04-03 18:00:00");
        //触发任务，返回cteateTime
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        String result = rest.getForObject(url, String.class, params);
        Map resultMap = (Map) JSONObject.parse(result);
        System.out.println(resultMap);

        //获取任务状态
        /*Map<String, Object> statusParams = new HashMap<>();
        statusParams.put("type", 1);
        statusParams.put("id", "np_bzk_zl");
        String statusEtl = "http://172.16.32.41:8080/sjcjgj/api/task/status?type={type}&id={id}";
        RestTemplate rest2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();
        MediaType type2 = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers2.setContentType(type);
        headers2.add("Accept", MediaType.APPLICATION_JSON.toString());
        String result2 = rest2.getForObject(statusEtl, String.class, statusParams);
        Map taskGroupDetail = (Map) JSONObject.parse(result2);

        System.out.println(taskGroupDetail);

        if (404 == (int)taskGroupDetail.get("code")) {
            System.out.println("任务未运行");
        }
        Object data = taskGroupDetail.get("data");
        JSONArray array = JSONArray.parseArray(JSONObject.toJSONString(data));
        List<Task> taskDetails = JSONObject.parseArray(array.toJSONString(), Task.class);
        System.out.println("np增量任务状态:" + taskDetails);
        //获取任务列表为空，说明任务未运行
        if (taskDetails == null || taskDetails.size() == 0) {
            System.out.println("任务未运行");
        }
        for (Task task : taskDetails) {
            //存在任务失败或者关键任务失败即为失败
            if (JkConstants.ETL_STATUS_FAIL.equals(task.getStatus()) || JkConstants.ETL_STATUS_IMFAIL.equals(task.getStatus())) {
                System.out.println("任务失败");
            }
        }
        //没有返回即为没有任务失败
        for (Task task : taskDetails) {
            //存在任务运行中
            if (JkConstants.ETL_STATUS_RUNNING.equals(task.getStatus()) || JkConstants.ETL_STATUS_WAIT.equals(task.getStatus())) {
                System.out.println("任务正在运行中");
            }
        }
        System.out.println("任务执行成功");*/
    }

    public static void listSub() {
        List<String> cyAjbs = new ArrayList<>();
        cyAjbs.add("1");
        cyAjbs.add("2");
        cyAjbs.add("3");
        cyAjbs.add("4");
        cyAjbs.add("5");
        cyAjbs.add("6");
        cyAjbs.add("7");
        cyAjbs.add("8");
        cyAjbs.add("9");
        cyAjbs.add("10");
        cyAjbs.add("11");
        cyAjbs.add("12");
        cyAjbs.add("13");
        int size = cyAjbs.size();
        List<String> tmp;
        for (int i = 0; i <= (size / 2); i++) {
            if ( i == size / 2) {
                tmp = cyAjbs.subList(i * 2, size);
                System.out.println(tmp);
                break;
            }
            tmp = cyAjbs.subList(i * 2, (i + 1) * 2);
            System.out.println(tmp);
        }
    }

    public static String getDate1(int day, String time) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, day);
        Date date = cal.getTime();
        String dateStr = format(date, JkConstants.STR_FORMAT_PATTERN1);
        return dateStr + " " +time;
    }

    public static String format(Date d, String pattern) {
        return (new SimpleDateFormat(pattern)).format(d);
    }

    public static void insertLkbd() {
        String url = "http://172.16.32.41:8080/sjcjgj";
        String aj = "1234";
        Map<String, Object> taskParams = new HashMap<>();
        taskParams.put("#{task.CId}", "123456");
        taskParams.put("#{kssj}", "'2020-03-26'");
        taskParams.put("#{jssj}", "now()");
        Integer sjlx = 1;
        if (JkConstants.LCMB_SP.equals(sjlx + "")) {
            taskParams.put("#{type}", "sp");
        } else if (JkConstants.LCMB_ZX.equals(sjlx + "")) {
            taskParams.put("#{type}", "zx");
        }

        Map<String, Object> params = new HashMap<>();
        params.put("type", 2);
        params.put("id", "sjfwjk_lkbd");
        params.put("#{gyid}", 100);
        params.put("params", JSONObject.toJSON(taskParams));

        Map<String, Object> result = insertPost(url, params);
        System.out.println(result);
    }

    public static Integer getImportFormat(Integer importStatus) {
        Map<Integer, Integer> importStatusMap = new HashMap<>();
        importStatusMap.put(JkConstants.IMPORT_STATUS_FAIL, JkConstants.TASK_FAIL);
        importStatusMap.put(JkConstants.IMPORT_STATUS_SUCCESS, JkConstants.TASK_SUCCESS);
        importStatusMap.put(JkConstants.IMPORT_STATUS_RUNNING, JkConstants.TASK_RUNNING);
        importStatusMap.put(JkConstants.IMPORT_STATUS_WAIT, JkConstants.TASK_NORUN);
        System.out.println(importStatusMap);
        System.out.println(importStatusMap.get(importStatus));
        return importStatusMap.get(importStatus);
    }

    public static String getDate(int day, String time) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, day);
        Date date = cal.getTime();
        String dateStr = DateUtils.format(date, "yyyy-MM-dd");
        return dateStr + " " +time;
    }

    public static void insertExport() {
        String url = "http://172.16.32.41:8080/sjcjgj";
        String aj = "1234";
        Map<String, Object> taskparams = new HashMap<>();
        taskparams.put("#{exportid}", "3344");
        taskparams.put("#{kssj}", "'2020-01-01 17:00:00'");
        taskparams.put("#{jssj}", "'2020-03-30 18:00:00'");
        taskparams.put("#{ajbs}", "'" + aj + "'");
        taskparams.put("#{type}", "sp");
        taskparams.put("#{type.type}", "'手动'");

        Map<String, Object> params = new HashMap<>();
        params.put("type", 2);
        params.put("id", "start_export");
        params.put("params", JSONObject.toJSON(taskparams));

        Map<String, Object> result = insertPost(url, params);
        System.out.println(result);
    }

    //post请求触发任务接口
    public static Map<String, Object> insertPost(String url, Map<String, Object> params) {
        Integer type = (Integer)params.get("type");
        String id = (String)params.get("id");
        url += "/api/task/insert?type="+ type +"&id=" + id;
        Map<String, Object> result = httpPost(url, (Map<String, Object>) params.get("params"));
        return result;
    }

    private static Map<String, Object> httpPost(String url, Map<String, Object> params) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> responseEntity = rest.postForEntity(url, requestEntity, String.class);
        String result = responseEntity.getBody();
        Map resultMap = (Map) JSONObject.parse(result);
        return resultMap;
    }

    public static void insertETLTask() {
        /*String taskId = "DEMO";
        String url = "http://172.16.32.41:8080/sjcjgj/api/task/insert?type={type}&id={id}&params={params}";
        Map<String, Object> taskParams = new HashMap<>();
        taskParams.put("#{task.CId}", "20200331001");
        taskParams.put("#{kssj}", "'2020-03-21 05:03:00'");
        taskParams.put("#{jssj}", "'2020-03-30 06:03:00'");
        taskParams.put("#{type}", "sp");

        Map<String, Object> params = new HashMap<>();
        params.put("type", 2);
        params.put("id", "sjfwjk_lkbd");
        params.put("params", JSONObject.toJSON(taskParams));*/

        String zlTaskId = "etl_np_bzk_zl2.5";
        String url = "http://172.16.32.41:8080/sjcjgj/api/task/insert?type={type}&id={id}&startTime={startTime}&endTime={endTime}&params={params}";
        Map<String, Object> taskParams = new HashMap<>();
        taskParams.put("{中院部署}", "--");
        Map<String, Object> params = new HashMap<>();
        params.put("type", 2);
        params.put("id", zlTaskId);
        params.put("startTime", "2020-01-01 17:00:00");
        params.put("endTime", "2020-03-31 17:00:00");
        params.put("params", JSONObject.toJSON(taskParams));

        System.out.println(taskParams);

        //触发任务，返回cteateTime
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(params, headers);
        String result = rest.getForObject(url, String.class, params);
        Map resultMap = (Map) JSONObject.parse(result);
        System.out.println(resultMap);

        Object data = resultMap.get("data");
        Map<String, String> dataMap = (Map)JSONObject.parse(JSONObject.toJSONString(data));
        String createTime = dataMap.get("createTime");
        System.out.println(createTime);
    }

    public static void statusTask() {
        String taskId = "DEMO";
        String taskGroup = "np到标准库_全量";
        String taskName = "2.5.2NP到标准库数据采集_全量";
        String url = "http://172.16.32.41:8080/sjcjgj/api/task/status?type={type}&id={id}&createTime={createTime}";

        String startTime = "15:25:55";
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
        if (404 != (int)taskGroupDetail.get("code")) {
            Object data = taskGroupDetail.get("data");
            JSONArray array = JSONArray.parseArray(JSONObject.toJSONString(data));
            List<Task> taskDetails = JSONObject.parseArray(array.toJSONString(), Task.class);
            System.out.println(taskDetails);
        }
    }

    public static void jsonTest() {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        data.put("createTime", "2020-03-26 21:45:00");
        result.put("code", 200);
        result.put("message", "成功");
        result.put("data", data);

        Object json = JSONObject.toJSON(result);
        System.out.println(json);
    }

    public static void lDTformat() {
        LocalDateTime localDateTime1 = LocalDateTime.now();
        String s = localDateTime1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString();
        System.out.println(s);
    }

    public static void subListTest() {
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

    public static void sqlTest() {
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
    }

}
