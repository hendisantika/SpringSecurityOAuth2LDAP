package com.hendisantika.springsecurityoauth2serverldap.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-security-oauth2-server-ldap
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Link : s.id/hendisantika
 * Date: 05/02/20
 * Time: 07.07
 */
@RestController
@RequestMapping("/rest/hello")
public class HelloController {

    @GetMapping("/principal")
    public Map<String, Object> user(Principal principal, @AuthenticationPrincipal Jwt jwt) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("name", principal.getName());
        if (principal instanceof org.springframework.security.core.Authentication authentication) {
            result.put("authorities", authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList());
        }
        if (jwt != null) {
            result.put("claims", jwt.getClaims());
        }
        return result;
    }

    @GetMapping
    public String hello() {
        return "Hello World";
    }

}
