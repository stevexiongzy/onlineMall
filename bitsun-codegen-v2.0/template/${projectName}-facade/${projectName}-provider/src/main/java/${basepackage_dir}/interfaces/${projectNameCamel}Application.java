package ${basepackage}.interfaces;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * spring boot的入口
 * @author: ${author!"Felix Woo"}
 * @email: ${email!"foruforo@msn.com"}
 */
@EnableSwagger2
/*
apollo不启用
@EnableApolloConfig({"application", "jmtop.actuator", "jmtop.datasource", "jmtop.eureka", "jmtop.feign", "jmtop.logging",
    "jmtop.mybatisplus", "jmtop.zipkin"})*/
@SpringBootApplication(scanBasePackages = {"${basepackage}"})
//@EnableHystrix
//@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("${basepackage}.infrastructure.persistence.dao*")
public class ${projectNameCamel}Application {
    public static void main(String[] args) {
        SpringApplication.run(${projectNameCamel}Application.class, args);
    }

}
