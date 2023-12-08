package com.training.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangliujie
 * @Description
 * @date 2020/1/14.
 */
public class StreamGroupingBy {

    public static void main(String[] args) {
        List<SlResult> slList = new ArrayList<>();
        SlResult slResult11 = new SlResult("aaa",100,0,0,0);
        SlResult slResult12 = new SlResult("aaa",0,110,0,0);
        SlResult slResult13 = new SlResult("aaa",0,0,120,0);
        SlResult slResult14 = new SlResult("aaa",0,0,0,130);

        SlResult slResult21 = new SlResult("bbb",200,0,0,0);
        SlResult slResult22 = new SlResult("bbb",0,210,0,0);
        SlResult slResult23 = new SlResult("bbb",0,0,220,0);
        SlResult slResult24 = new SlResult("bbb",0,0,0,230);

        slList.add(slResult11);
        slList.add(slResult12);
        slList.add(slResult13);
        slList.add(slResult14);
        slList.add(slResult21);
        slList.add(slResult22);
        slList.add(slResult23);
        slList.add(slResult24);

        Map<String, List<SlResult>> slMap = slList.stream().collect(Collectors.groupingBy(SlResult::getJbfyid));

        System.out.println(slMap);

        Long id = Long.valueOf("205020190103308272");
        System.out.println(id % 1024);
    }

}
