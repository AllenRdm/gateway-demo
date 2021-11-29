/*
 *
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.sttc.gateway.nacos;

import com.sttc.gateway.nacos.action.GreetingServiceConsumer;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.demo.Helloworld;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

public class ConsumerBootstrap {

    public static void main(String[] args) {
        Helloworld.HelloRequest request1 = Helloworld.HelloRequest.newBuilder()
                .setName("nacos").build();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        //AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //context.getEnvironment().addActiveProfile("classpath:/spring/dubbo-consumer.properties");
        //springboot可以用EnvironmentPostProcessor扩展点
        //http://www.soolco.com/post/111098_1_1.html
/*        MutablePropertySources propertySources = context.getEnvironment().getPropertySources();
        Properties p = new Properties();
        p.put("dubbo.application.name", "nacos-registry-demo-provider");
        p.put("dubbo.registry.address", "nacos://localhost:8848?username=nacos&password=nacos");
        p.put("dubbo.consumer.timeout", "3000");
        p.put("dubbo.application.qosPort", "33333");
        propertySources.addFirst(new PropertiesPropertySource("defaultProperties", p));
        context.register(ConsumerConfiguration.class);*/
        context.start();
        GreetingServiceConsumer greetingServiceConsumer = context.getBean(GreetingServiceConsumer.class);
        Helloworld.HelloRequest request = Helloworld.HelloRequest.newBuilder()
                .setName("pika").build();
        Helloworld.User hello = greetingServiceConsumer.doSayHello(request);
        System.out.println("result: " + hello);
    }

    @Configuration
    @EnableDubbo(scanBasePackages = "com.sttc.gateway.nacos.action")
    @PropertySource("classpath:/spring/dubbo-consumer.properties")
    @ComponentScan(value = {"com.sttc.gateway.nacos.action"})
    static class ConsumerConfiguration {

    }
}
