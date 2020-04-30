package com.leaf.jobs.context;

import com.google.common.base.Strings;
import com.leaf.common.concurrent.ConcurrentSet;
import com.leaf.common.utils.Maps;
import com.leaf.jobs.model.RegisterServiceVo;
import com.leaf.register.api.model.RegisterMeta;
import com.leaf.rpc.consumer.invoke.GenericInvoke;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * 上下文信息
 *
 * @author yefei
 */
@Component
public class JobsContext implements ApplicationContextAware {

    /**
     * 组与服务信息
     */
    private static final ConcurrentMap<String, ConcurrentSet<RegisterMeta>> GROUP_MAP = Maps.newConcurrentMap();

    /**
     * 服务执行器
     */
    private static final ConcurrentMap<Long, GenericInvoke> invokeMap = Maps.newConcurrentMap();

    /**
     * spring context
     */
    private static ApplicationContext applicationContext;

    public static ConcurrentMap<String, ConcurrentSet<RegisterMeta>> getGroupMap() {
        return GROUP_MAP;
    }

    public static ConcurrentMap<Long, GenericInvoke> getInvokeMap() {
        return invokeMap;
    }

    /**
     * 获取泛化调用执行器
     *
     * @param taskId
     * @return
     */
    public static GenericInvoke getInvoke(Long taskId) {
        GenericInvoke genericInvoke = invokeMap.get(taskId);
        return genericInvoke;
    }

    public static Set<RegisterServiceVo> getRegisterServiceVo(String group, String serviceName) {
        Set<RegisterServiceVo> set = new HashSet<>();
        if (Strings.isNullOrEmpty(group)) {
            GROUP_MAP.get(group).stream()
                    .filter(registerMeta -> Strings.isNullOrEmpty(serviceName) ? serviceName.equals(registerMeta.getServiceMeta().getServiceProviderName()) : true)
                    .forEach(registerMeta -> Arrays.stream(registerMeta.getMethods()).forEach(method -> {
                                set.add(RegisterServiceVo.builder()
                                        .group(registerMeta.getServiceMeta().getGroup())
                                        .serviceName(registerMeta.getServiceMeta().getServiceProviderName())
                                        .method(method)
                                        .build());
                            }
                    ));
        } else {
            GROUP_MAP.forEach((s, registerMetas) -> registerMetas.stream()
                    .filter(registerMeta -> Strings.isNullOrEmpty(serviceName) ? serviceName.equals(registerMeta.getServiceMeta().getServiceProviderName()) : true)
                    .forEach(registerMeta -> Arrays.stream(registerMeta.getMethods()).forEach(method -> {
                        set.add(RegisterServiceVo.builder()
                                .group(registerMeta.getServiceMeta().getGroup())
                                .serviceName(registerMeta.getServiceMeta().getServiceProviderName())
                                .method(method)
                                .build());
                            }
                    )));
        }
        return set;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        JobsContext.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
