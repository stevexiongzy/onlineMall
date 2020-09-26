package ${basepackage}.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * spring boot的入口
 * @author: ${author!"Felix Woo"}
 * @email: ${email!"foruforo@msn.com"}
 */
@EnableSwagger2
/*
apollo不启用
@EnableApolloConfig({"application","mybatisplus", "jmtop.zipkin"})*/
@SpringBootApplication(scanBasePackages = {"${basepackage}"},exclude={DataSourceAutoConfiguration.class})
//@EnableHystrix
//@EnableFeignClients
@EnableDiscoveryClient
public class ${projectNameCamel}Application {
    public static void main(String[] args) {
        SpringApplication.run(${projectNameCamel}Application.class, args);
    }

}
