package com.hendisantika.springsecurityoauth2serverldap.controller;

import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-security-oauth2-server-ldap
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 05/02/20
 * Time: 07.07
 */
@EnableResourceServer
@RestController
@RequestMapping("/rest/api/")
public class HelloController {

}
