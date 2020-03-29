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
        return invokeMap.get(taskId);
    }

    public static Set<RegisterServiceVo> getRegisterServiceVo(String group, String serviceName) {
        Set<RegisterServiceVo> set = new HashSet<>();
        if (Strings.isNullOrEmpty(group) && Strings.isNullOrEmpty(serviceName)) {
            for (String s : GROUP_MAP.keySet()) {
                ConcurrentSet<RegisterMeta> registerMetas = GROUP_MAP.get(s);
                for (RegisterMeta registerMeta : registerMetas) {
                    for (String method : registerMeta.getMethods()) {
                        RegisterServiceVo registerServiceVo = new RegisterServiceVo();
                        registerServiceVo.setGroup(registerMeta.getServiceMeta().getGroup());
                        registerServiceVo.setServiceName(registerMeta.getServiceMeta().getServiceProviderName());
                        registerServiceVo.setMethod(method);
                        set.add(registerServiceVo);
                    }
                }
            }
        } else if (Strings.isNullOrEmpty(group) && !Strings.isNullOrEmpty(serviceName)) {
            ConcurrentSet<RegisterMeta> registerMetas = GROUP_MAP.get(group);
            for (RegisterMeta registerMeta : registerMetas) {

                for (String method : registerMeta.getMethods()) {
                    RegisterServiceVo registerServiceVo = new RegisterServiceVo();
                    registerServiceVo.setGroup(registerMeta.getServiceMeta().getGroup());
                    registerServiceVo.setServiceName(registerMeta.getServiceMeta().getServiceProviderName());
                    registerServiceVo.setMethod(method);
                    set.add(registerServiceVo);
                }
            }
        } else {
            ConcurrentSet<RegisterMeta> registerMetas = GROUP_MAP.get(group);
            for (RegisterMeta registerMeta : registerMetas) {
                if (serviceName.equals(registerMeta.getServiceMeta().getServiceProviderName())) {
                    for (String method : registerMeta.getMethods()) {
                        RegisterServiceVo registerServiceVo = new RegisterServiceVo();
                        registerServiceVo.setGroup(registerMeta.getServiceMeta().getGroup());
                        registerServiceVo.setServiceName(registerMeta.getServiceMeta().getServiceProviderName());
                        registerServiceVo.setMethod(method);
                        set.add(registerServiceVo);
                    }
                }
            }
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
