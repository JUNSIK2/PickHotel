package com.example.member.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class UserAccount extends org.springframework.security.core.userdetails.User {

    private MemberVo member;

    public UserAccount(MemberVo member, boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        super(member.getId(), member.getPwd(), enabled, true, true, true, authorities);
        this.member = member;
    }
}
