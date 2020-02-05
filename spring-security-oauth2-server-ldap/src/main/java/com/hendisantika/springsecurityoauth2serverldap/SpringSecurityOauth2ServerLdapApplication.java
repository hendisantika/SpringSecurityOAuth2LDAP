package com.hendisantika.springsecurityoauth2serverldap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringSecurityOauth2ServerLdapApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringSecurityOauth2ServerLdapApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityOauth2ServerLdapApplication.class, args);
    }

}
