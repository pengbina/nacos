/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Nacos starter.
 *
 * @author nacos
 *
 * nacos服务端的入口
 *
 * 首先最外层是一个Map,结构为：Map<String,Map<String,Service>>:
 *   key:是namespace_id,起到环境隔离的作用。namespace下可以有多个group
 *   value:又是一个Map<String,Service>,代表分组及组内的服务。一个组内可以有多个服务
 *      key:代表group分组，不过作为key时格式是group_name:service_name
 *      value:分组下的某一个服务，例如userservice，用户服务.类型为Service,内部也包含一个Map<String,Cluster>,
 *      一个服务下可以有多个集群：
 *          key：集群名称
 *          value:Cluster类型，包含集群的具体信息。一个集群中可能包含多个实例，也就是具体的节点信息，其中包含一个Set<Instance>,
 *          就是该集群下的实例的集合
 *             Instance:实例信息，包含实例的IP，Port，健康状态，权重等信息
 *  每一个服务去注册到Nacos时，就会把信息组织并存入这个Map中。
 */
@SpringBootApplication(scanBasePackages = "com.alibaba.nacos")
@ServletComponentScan
@EnableScheduling
public class Nacos {
    
    public static void main(String[] args) {
        SpringApplication.run(Nacos.class, args);
    }
}

