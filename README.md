# leaf-jobs
#### 集群任务调度中心
- rpc模块                 netty
- quartz-support模块      quartz集群模式
- core模块                核心逻辑，springboot控制台     
- spi模块                 服务端需要依赖
- test模块                spi的test模块
                  

#### 数据库脚本
leaf-jobs/db/customer.sql
leaf-jobs/db/tables_mysql_innodb.sql

#### 服务端配置
可参考test模块

依赖
````xml
    <dependency>
        <groupId>com.leaf.jobs</groupId>
        <artifactId>leaf-jobs-spi</artifactId>
    </dependency>
````

支持springboot autoconfig
````java
@SpringBootApplication
@JobsScanner(basePackages = "com.leaf.jobs")
public class SpiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpiApplication.class, args);
    }
}

public interface HelloService {

    String sayHello(String name);

    String sayHello2(String name, String age);
}

@JobsProvider(group = "rjb")
@Slf4j
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        log.info("hi service" + name);
        return "ss: " + name;
    }

    @Override
    public String sayHello2(String name, String age) {
        log.info("hi service" + name);
        return "ss: " + name + age;
    }
}
````

````properties
    leaf:
        jobs:
          registerAddress: 172.16.2.203:2181
          systemName: rjb
          port: 9000
````

#### 调度中心控制台
启动 com.leaf.jobs.JobsApplication 即可

#### 后续
- 日志回显
- 脚本 shell groovy 执行
- 子任务
- no channel bug



