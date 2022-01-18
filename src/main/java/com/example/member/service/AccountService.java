package com.example.member.service;

import com.example.member.model.MemberRoleVo;
import com.example.member.model.MemberVo;
import com.example.member.model.UserAccount;
import com.example.member.persistent.RoleResourceDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private RoleResourceDao roleResourceDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("1. AccountService loadUserByUsername {}", username);
        return getLoginUser(username);
    }

    protected UserDetails getLoginUser(String id) throws UsernameNotFoundException {

        MemberVo member = new MemberVo();
        List<GrantedAuthority> authorityList = null;

        try {
            member = roleResourceDao.getUserById(id);
            boolean enabled = true;

            if (1 != Integer.parseInt(member.getState())) {
                enabled = false;
            }

            log.info("2. AccountService getLoginUser User {}", member);

            if (member == null) {
                throw new UsernameNotFoundException("no user found [email=" + id + "]");
            }

            List<MemberRoleVo> memberRoles = roleResourceDao.getRolesById(id);

            List<String> roles = new ArrayList<>();
            List<String> roleHierarchy = new ArrayList<>();

            for (MemberRoleVo memberRole : memberRoles) {
                roleHierarchy = roleResourceDao.roleHierarchy(memberRole.getRoleDesc());
            }

            for (String myRoles : roleHierarchy) {
                roles.add(myRoles.replace("ROLE_", ""));
            }

            List<GrantedAuthority> authorities = new ArrayList(roles.size());

            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }

            log.info("=======================================================");
            log.info("1. Select id : {}", id);
            log.info("2. User id  : {}", member.getId());
            log.info("3. User pwd : {}", member.getPwd());
            log.info("4. User Roles : {}", roles);
            log.info("4. User enabled : {}", enabled);
            log.info("=======================================================");

            return new UserAccount(member, enabled, authorities);

        } catch (Exception ex) {
            log.error("failed to get LoginUser.", ex);

            if (ex instanceof UsernameNotFoundException) {
                log.error("failed to get LoginUser.", ex);
                UsernameNotFoundException e = (UsernameNotFoundException) ex;
                throw e;
            }
            throw new UsernameNotFoundException("could not select user.", ex);
        }
    }
}
