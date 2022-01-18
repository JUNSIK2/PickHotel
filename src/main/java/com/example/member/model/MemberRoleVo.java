package com.example.member.model;


import groovy.transform.builder.Builder;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRoleVo {

    private String memberRoleId;
    private Integer roleDesc;
    private String roleName;
    private String resourceName;

}
