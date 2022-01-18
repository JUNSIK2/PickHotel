package com.example.member.service;

import com.example.member.model.RoleVo;
import com.example.member.persistent.RoleResourceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleHierarchyService {

//    @Autowired
//    private RoleResourceDao resourceDao;
//
//    public String findAllHierarchy(){
//        List<RoleVo> roleHierarchies = resourceDao.findRoleAll();
//        StringBuilder concatedRoles = new StringBuilder();
//
//        for (int i = 0; i < roleHierarchies.size(); i++) {
//            concatedRoles.append(roleHierarchies.get(i).getRoleName());
//            if (i  < roleHierarchies.size() - 1) {
//                concatedRoles.append(" > ");
////                concatedRoles.append("\n");
//            }
//        }
//
//        System.out.println("Role Hierarchy : " + concatedRoles.toString());
//        return concatedRoles.toString();
//    }

}
