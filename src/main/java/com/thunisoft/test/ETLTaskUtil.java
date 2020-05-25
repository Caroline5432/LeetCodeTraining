package com.thunisoft.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thunisoft.pojo.Task;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author zhangliujie
 * @Description 数据集成与调度工具接口调用类
 * @date 2020/3/26.
 */
public final class ETLTaskUtil {

    //获取任务状态接口
    public static Map<String, Object> status(String url, Map<String, Object> params) {
        url += "/api/task/status?type={type}&id={id}&createTime={createTime}";
        return httpGet(url, params);
    }

    //触发任务接口
    public static Map<String, Object> insert(String url, Map<String, Object> params) {
        url += "/api/task/insert?type={type}&id={id}&params={params}";
        return httpGet(url, params);
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

    //触发增量任务接口
    public static Map<String, Object> insertZl(String url, Map<String, Object> params) {
        //url += "/api/task/insert?type={type}&id={id}&startTime={startTime}&endTime={endTime}";
        url += "/api/task/insert?type={type}&id={id}&startTime={startTime}";
        return httpGet(url, params);
    }

    private static Map<String, Object> httpGet(String url, Map<String, Object> params) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        String result = rest.getForObject(url, String.class, params);
        Map resultMap = (Map) JSONObject.parse(result);
        return resultMap;
    }

    //获取任务状态接口，返回解析后的状态
    public static String etlStatus(String url, Map<String, Object> params) {
        url += "/api/task/status?type={type}&id={id}&createTime={createTime}";
        Map<String, Object> statusMap = httpGet(url, params);
        Object statusData = statusMap.get("data");
        JSONArray array = JSONArray.parseArray(JSONObject.toJSONString(statusData));
        List<Task> statusList = JSONObject.parseArray(array.toJSONString(), Task.class);
        String etlStatus = statusList.get(0).getStatus();
        return etlStatus;
    }

    private ETLTaskUtil(){}

}
