package com.training.test;

/**
 * @author zhangliujie
 * @Description
 * @date 2020/4/23.
 */
public enum Singleton {

    INSTANCE;

    private String props;

    public void whateverMethod() {
        System.out.println("do whateverMethod");
    }

    public void changeProp(String prop) {
        props= prop;
    }

    public String getProps() {
        return props;
    }
}

