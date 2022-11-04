package com.alibaba.nacos;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.AbstractListener;
import com.alibaba.nacos.api.exception.NacosException;

import java.util.Properties;
import java.util.Scanner;

/**
 * @author DELL
 *
 * ConfigServer > Namespace/Tenant > Group > dataId
 *
 * Namespace(Tenant):命名空间(租户)，默认命名空间是public,一个命名空间可以包含多个Group,在nacos源码里有些变量是tenant租户，和命名空间是一个东西
 * Group:组，默认分组是DEFAULT_GROUP，一个组可以包含多个dataId
 * DataId:数据ID，在nacos中DataId代表一整个配置文件，是配置的最小单位。和apollo不同，apollo的最小单位是一个配置项key。
 */
public class MyConfigExample {
    public static void main(String[] args) throws NacosException {
        String serverAddr = "localhost";
        String dataId = "test"; // dataId
        String group = "DEFAULT_GROUP";// group
        Properties properties = new Properties();
        // nacos-server地址
        properties.put("serverAddr", serverAddr);
        // namespace/tenant
        properties.put("namespace", "fd624b17-2709-486a-b9fe-56a1ebfa2ca4");
        ConfigService configService = NacosFactory.createConfigService(properties);
        //1.注册监听
        configService.addListener(dataId, group, new AbstractListener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println("receive configInfo = " + configInfo);
            }
        });

        //2.查询初始配置
        String config = configService.getConfig(dataId, group, 3000);
        System.out.println("init config:" + config);
        //3.修改配置
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String next = scanner.next();
            if ("exit".equals(next)) {
                break;
            }

            configService.publishConfig(dataId, group, next);
        }

    }
}
