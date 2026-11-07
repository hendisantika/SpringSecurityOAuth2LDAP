package com.hendisantika.springsecurityoauth2serverldap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(properties = "spring.ldap.embedded.port=0")
class SpringSecurityOauth2ServerLdapApplicationTests {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RegisteredClientRepository registeredClientRepository;

    @Test
    void contextLoads() {
        assertThat(registeredClientRepository.findByClientId("ClientId")).isNotNull();
    }

    @Test
    void ldapUserCanAuthenticate() {
        Authentication authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated("hendi", "password"));

        assertThat(authentication.isAuthenticated()).isTrue();
        assertThat(authentication.getName()).isEqualTo("hendi");
    }

    @Test
    void ldapUserWithWrongPasswordIsRejected() {
        assertThatThrownBy(() -> authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated("hendi", "wrong")))
                .isInstanceOf(BadCredentialsException.class);
    }

}
