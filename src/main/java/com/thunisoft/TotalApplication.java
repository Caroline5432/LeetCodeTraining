package com.thunisoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author zhangliujie
 * @Description
 * @date 2019/9/10.
 */
@EnableCaching
@SpringBootApplication
public class TotalApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TotalApplication.class, args);
    }

}
