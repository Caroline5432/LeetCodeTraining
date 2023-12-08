package com.training.test;

/**
 * @author zhangliujie
 * @Description
 * @date 2020/4/23.
 */
public class SingletonTest {
    public static void main(String[] args) {
        Singleton singleton = Singleton.INSTANCE;
        singleton.whateverMethod();
        singleton.changeProp("张三");
        System.out.println(singleton.getProps());
    }
}
