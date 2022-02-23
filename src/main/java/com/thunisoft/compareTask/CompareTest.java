package com.thunisoft.compareTask;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author zhangliujie
 * @Description
 * @date 2020/6/16.
 */
@Slf4j
@Controller
@RequestMapping("compare")
public class CompareTest {

    public static void main(String[] args) {
        test3();
    }

    @GetMapping(path = "/test3")
    public static void test3() {
        final CountDownLatch countDownLatch = new CountDownLatch(10);
        ExecutorService execCompare = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        List<String> columns = new ArrayList<>();
        columns.add("c_stm");
        columns.add("c_baah");
        columns.add("n_ajbs");
        columns.add("n_jbfy");
        columns.add("d_xgsj");
        Long start = System.currentTimeMillis();
        //where abs(mod(hashtext(c_stm),1)) =" + i
        for (int i = 0; i < 10; i++) {
            String sqlLeft = "select c_stm,c_baah,n_ajbs,n_jbfy,d_xgsj,md5(concat(c_stm,c_baah,n_ajbs,n_jbfy,d_xgsj)) " +
                    "from db_msys.t_msys where abs(mod(hashtext(c_stm),10)) =" + i + " order by c_stm";
            String sqlRight = "select c_stm,c_baah,n_ajbs,n_jbfy,d_xgsj,md5(concat(c_stm,c_baah,n_ajbs,n_jbfy,d_xgsj)) " +
                    "from db_msys.t_msys where abs(mod(hashtext(c_stm),10)) =" + i + " order by c_stm";
            execCompare.submit(new CompareFetchExecutor(sqlLeft, sqlRight, columns, countDownLatch));
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("异常:{}" + e);
        }
        Long end = System.currentTimeMillis();
        log.info("耗时:" + (end - start));
    }

    private static void compareMethodTest() {
        String pkKey = "c_stm";
        List<Map<String, Object>> leftDatas = new ArrayList<>();
        Map<String, Object> leftData1 = new HashMap<>();
        leftData1.put(pkKey, "000000000000000000000318600000000628");
        leftData1.put("md5", "e7c3d450527de04aec6ab318e47486cc");
        Map<String, Object> leftData2 = new HashMap<>();
        leftData2.put(pkKey, "000000000000000000000318600000001027");
        leftData2.put("md5", "54e725c9afea676080a7ccf6801bc903");
        Map<String, Object> leftData3 = new HashMap<>();
        leftData3.put(pkKey, "000000000000000000000318600000002299");
        leftData3.put("md5", "9bff1c8cd9b64e38739921e4adbfb4f1");
        Map<String, Object> leftData4 = new HashMap<>();
        leftData4.put(pkKey, "000000000000000000000318600000003163");
        leftData4.put("md5", "c5042ecf8df5993d87a7637a3bdc4100");
        Map<String, Object> leftData5 = new HashMap<>();
        leftData5.put(pkKey, "000000000000000000000318600000003303");
        leftData5.put("md5", "31626531ef7c0e7d02690b73900da45e");
        leftDatas.add(leftData1);
        leftDatas.add(leftData2);
        leftDatas.add(leftData3);
        leftDatas.add(leftData4);
        leftDatas.add(leftData5);

        List<Map<String, Object>> rightDatas = new ArrayList<>();
        Map<String, Object> rightData1 = new HashMap<>();
        rightData1.put(pkKey, "000000000000000000000318600000000629");
        rightData1.put("md5", "e7c3d450527de04aec6ab318e47486cc");
        Map<String, Object> rightData2 = new HashMap<>();
        rightData2.put(pkKey, "000000000000000000000318600000001027");
        rightData2.put("md5", "54e725c9afea676080a7ccf6801bc903");
        Map<String, Object> rightData3 = new HashMap<>();
        rightData3.put(pkKey, "000000000000000000000318600000002299");
        rightData3.put("md5", "9bff1c8cd9b64e38739921e4adbfb4f2");
        Map<String, Object> rightData4 = new HashMap<>();
        rightData4.put(pkKey, "000000000000000000000318600000003163");
        rightData4.put("md5", "c5042ecf8df5993d87a7637a3bdc4100");
        Map<String, Object> rightData5 = new HashMap<>();
        rightData5.put(pkKey, "000000000000000000000318600000003303");
        rightData5.put("md5", "31626531ef7c0e7d02690b73900da45e");
        rightDatas.add(rightData1);
        rightDatas.add(rightData2);
        rightDatas.add(rightData3);
        rightDatas.add(rightData4);
        rightDatas.add(rightData5);

        List<Map<String, Object>> more = new ArrayList<>();
        List<Map<String, Object>> less = new ArrayList<>();
        List<Map<String, Object>> different = new ArrayList<>();
        for (int i = 0; i < leftDatas.size();) {
            for (int j = 0; j < rightDatas.size();) {
                Map<String, Object> leftData = leftDatas.get(i);
                Map<String, Object> rightData = rightDatas.get(j);
                String pkValueLeft = (String) leftData.get(pkKey);
                String pkValueRight = (String) rightData.get(pkKey);
                if (pkValueLeft.equals(pkValueRight)) {
                    leftDatas.remove(leftData);
                    rightDatas.remove(rightData);
                    if (!leftData.get("md5").equals(rightData.get("md5"))) {
                        different.add(leftData);
                        different.add(rightData);
                    }
                } else if (pkValueLeft.compareTo(pkValueRight) < 0) {
                    leftDatas.remove(leftData);
                    more.add(leftData);
                    i++;
                    if (i == leftDatas.size()) {
                        //如果左表已经走到最后一位，右表其余的数不用查了
                        break;
                    }
                } else {
                    rightDatas.remove(rightData);
                    less.add(rightData);
                    j++;
                }
            }
        }

        System.out.println(more);
        System.out.println(less);
        System.out.println(different);
    }


    @GetMapping(path = "/test1")
    public void test() {
        final CountDownLatch countDownLatch = new CountDownLatch(10);
        ExecutorService execCompare = new ThreadPoolExecutor(20, 20, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        List<String> columns = new ArrayList<>();
        columns.add("c_stm");
        columns.add("c_baah");
        columns.add("n_ajbs");
        columns.add("n_jbfy");
        columns.add("d_xgsj");
        Long start = System.currentTimeMillis();
        //where abs(mod(hashtext(c_stm),1)) =" + i
        for (int i = 0; i < 10; i++) {
            String sqlLeft = "select c_stm,c_baah,n_ajbs,n_jbfy,d_xgsj,md5(concat(c_stm,c_baah,n_ajbs,n_jbfy,d_xgsj)) " +
                    "from db_msys.t_msys where abs(mod(hashtext(c_stm),10)) =" + i;
            String sqlRight = "select c_stm,c_baah,n_ajbs,n_jbfy,d_xgsj,md5(concat(c_stm,c_baah,n_ajbs,n_jbfy,d_xgsj)) " +
                    "from db_msys.t_msys where abs(mod(hashtext(c_stm),10)) =" + i;
            execCompare.submit(new CompareExecutor(sqlLeft, sqlRight, columns, countDownLatch));
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("异常:{}" + e);
        }
        Long end = System.currentTimeMillis();
        log.info("耗时:" + (end - start));
    }

    @GetMapping(path = "/test2")
    public void test2() {
        final CountDownLatch countDownLatch = new CountDownLatch(20);
        ExecutorService execCompare = new ThreadPoolExecutor(20, 20, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        List<String> columns = new ArrayList<>();
        columns.add("c_stm");
        columns.add("c_baah");
        columns.add("n_ajbs");
        columns.add("n_jbfy");
        columns.add("d_xgsj");
        Long start = System.currentTimeMillis();
        //where abs(mod(hashtext(c_stm),1)) =" + i
        for (int i = 0; i < 20; i++) {
            String sqlLeft = "select c_stm,c_baah,n_ajbs,n_jbfy,d_xgsj,md5(concat(c_stm,c_baah,n_ajbs,n_jbfy,d_xgsj)) from db_msys.t_msys where abs(mod(hashtext(c_stm),20)) =" + i;
            String sqlRight = "select c_stm,c_baah,n_ajbs,n_jbfy,d_xgsj,md5(concat(c_stm,c_baah,n_ajbs,n_jbfy,d_xgsj)) from db_msys.t_msys where abs(mod(hashtext(c_stm),20)) =" + i;
            execCompare.submit(new BloomCompareExecutor(sqlLeft, sqlRight, columns, countDownLatch));
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("异常:{}" + e);
        }
        Long end = System.currentTimeMillis();
        log.info("耗时:" + (end - start));
    }

    private static void bloomFilterTest() {
        long expectedInsertions = 10000000;
        double fpp = 0.00001;
        BloomFilter<CharSequence> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), expectedInsertions, fpp);

        bloomFilter.put("aaa");
        bloomFilter.put("bbb");
        boolean containsString = bloomFilter.mightContain("aaa");
        System.out.println(containsString);
    }


}
