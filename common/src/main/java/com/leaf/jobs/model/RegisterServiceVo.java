package com.leaf.jobs.model;

import lombok.*;

@Setter
@Getter
@EqualsAndHashCode
@Builder
public class RegisterServiceVo {

    private String group;

    private String serviceName;

    private String method;

}
