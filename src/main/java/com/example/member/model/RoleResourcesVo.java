package com.example.member.model;

import groovy.transform.builder.Builder;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResourcesVo{

    private Integer roleResourceId;
    private String resourceName;
    private String resourceType;
    private String roleName;
    private Integer roleDesc;

}
