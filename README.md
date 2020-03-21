# leaf-jobs
集群任务调度中心

###服务端配置
````
@Configuration
@ComponentScan("com.leaf.jobs.spi")
@JobsScanner(basePackages = "com.leaf.jobs")
public class Config {

    @Bean
    public JobsProperties jobsProperties() {
        JobsProperties jobsProperties = new JobsProperties();
        jobsProperties.setRegisterAddress("zookeeper.dev.xianglin.com:2181");
        jobsProperties.setSystemName("test");
        return jobsProperties;
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
        return "sb: " + name;
    }

    @Override
    public String sayHello2(String name, String age) {
        log.info("hi service" + name);
        return "sb: " + name + age;
    }
}
````



