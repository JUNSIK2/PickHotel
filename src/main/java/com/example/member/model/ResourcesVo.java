package com.example.member.model;

import groovy.transform.builder.Builder;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourcesVo {

    private Integer resourceId;
    private String resourceName;
    private String resourceType;

}
