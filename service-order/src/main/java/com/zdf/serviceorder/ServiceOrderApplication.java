package com.zdf.serviceorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@MapperScan("com.zdf.serviceorder.mapper")
@EnableDiscoveryClient
public class ServiceOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceOrderApplication.class, args);
    }

}
//http://service-map/terminal/trsearch?tid=280&startTime=1668674375000&endTime=1668675805851
//http://service-map/terminal/trsearch?tid=594222569&starttime=1668851455000&endtime=1669032454506