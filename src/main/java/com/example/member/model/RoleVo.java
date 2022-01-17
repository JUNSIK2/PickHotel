package com.example.member.model;

import groovy.transform.builder.Builder;
import lombok.*;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleVo {

    private int roleId;
    private int roleDesc;
    private String roleName;

}
