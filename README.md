# SpringSecurityOAuth2LDAP

[![Java CI with Maven](https://github.com/hendisantika/SpringSecurityOAuth2LDAP/actions/workflows/maven.yml/badge.svg)](https://github.com/hendisantika/SpringSecurityOAuth2LDAP/actions/workflows/maven.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.2-brightgreen?logo=springboot)
![Dependabot](https://img.shields.io/badge/Dependabot-enabled-blue?logo=dependabot)

Single Sign-On demo with **Spring Security OAuth2** where user credentials are stored in **LDAP**.

The project contains two independent Spring Boot applications:

| Module                                                                     | Port   | Context path | Description                                                                                                                       |
|----------------------------------------------------------------------------|--------|--------------|-----------------------------------------------------------------------------------------------------------------------------------|
| [`spring-security-oauth2-server-ldap`](./spring-security-oauth2-server-ldap) | `8081` | `/auth`      | OAuth2 / OpenID Connect **Authorization Server** (Spring Authorization Server). Users log in with credentials checked against an embedded LDAP server. Also exposes a JWT-protected REST API. |
| [`spring-security-client`](./spring-security-client)                       | `8080` | `/ui`        | Thymeleaf web app acting as an **OAuth2 Client** (`oauth2Login`). Protected pages redirect to the Authorization Server for login. |

## Architecture

```text
 Browser                 spring-security-client (:8080/ui)        spring-security-oauth2-server-ldap (:8081/auth)
    |  GET /ui/secure              |                                             |
    |----------------------------->|  302 /oauth2/authorize                      |
    |<-----------------------------|                                             |
    |  GET /auth/oauth2/authorize  ------------------------------------------->  |
    |  302 /auth/login  <-------------------------------------------------------  |
    |  POST /auth/login (username/password)  --------------------------------->  |  bind to embedded LDAP
    |  302 /ui/login/oauth2/code/ldap-auth?code=...  <--------------------------  |  (uid={0},ou=people)
    |----------------------------->|  POST /oauth2/token (code -> tokens)  ----> |
    |                              |  GET  /userinfo                       ----> |
    |  200 /ui/secure "Welcome, hendi!"                                          |
    |<-----------------------------|                                             |
```

## Tech Stack

- Java **25**
- Spring Boot **4.1.2** (Spring Framework 7, Spring Security 7)
- Spring Authorization Server (OAuth2 + OpenID Connect 1.0)
- Spring Security OAuth2 Client & Resource Server (JWT)
- Spring LDAP + embedded UnboundID LDAP server
- Thymeleaf + `thymeleaf-extras-springsecurity6`
- Maven Wrapper (Maven 3.9.16)
- GitHub Actions + Dependabot

## Prerequisites

- JDK 25 (e.g. Temurin, Corretto or Oracle JDK)
- No Maven installation needed — each module ships with the Maven Wrapper (`./mvnw`)

## How to Run

1. Clone the repository

   ```shell
   git clone https://github.com/hendisantika/SpringSecurityOAuth2LDAP.git
   cd SpringSecurityOAuth2LDAP
   ```

2. Start the Authorization Server (terminal #1)

   ```shell
   cd spring-security-oauth2-server-ldap
   ./mvnw spring-boot:run
   ```

3. Start the Client UI (terminal #2)

   ```shell
   cd spring-security-client
   ./mvnw spring-boot:run
   ```

4. Open http://localhost:8080/ui and click **Login to OAuth here**. You will be redirected to
   http://localhost:8081/auth/login. Log in with one of the LDAP users below and you will land on the secured page.

### Demo LDAP Users

Users are loaded from [`users.ldif`](./spring-security-oauth2-server-ldap/src/main/resources/users.ldif)
into an embedded LDAP server (`ldap://localhost:8389`, base DN `dc=hendisantika,dc=com`).

| Username | Password   |
|----------|------------|
| `hendi`  | `password` |
| `naruto` | `password` |

### OAuth2 Client Registration

| Property      | Value                                                  |
|---------------|--------------------------------------------------------|
| Client ID     | `ClientId`                                             |
| Client Secret | `secret`                                               |
| Grant types   | `authorization_code`, `refresh_token`                  |
| Scopes        | `openid`, `profile`, `user_info`                       |
| Redirect URI  | `http://localhost:8080/ui/login/oauth2/code/ldap-auth` |

## Useful Endpoints (Authorization Server)

| Endpoint                                                   | Description                                    |
|------------------------------------------------------------|------------------------------------------------|
| `GET  /auth/.well-known/openid-configuration`              | OpenID Connect discovery document              |
| `GET  /auth/oauth2/authorize`                              | Authorization endpoint                         |
| `POST /auth/oauth2/token`                                  | Token endpoint                                 |
| `GET  /auth/oauth2/jwks`                                   | JSON Web Key Set                               |
| `GET  /auth/userinfo`                                      | OIDC UserInfo endpoint                         |
| `GET  /auth/rest/hello`                                    | Sample API — requires a Bearer access token    |
| `GET  /auth/rest/hello/principal`                          | Returns the authenticated principal and claims |

Example call to the protected API:

```shell
curl -H "Authorization: Bearer <access_token>" http://localhost:8081/auth/rest/hello/principal
```

## Build & Test

```shell
cd spring-security-oauth2-server-ldap && ./mvnw verify
cd ../spring-security-client && ./mvnw verify
```

## CI/CD & Dependency Management

- **GitHub Actions** ([`maven.yml`](.github/workflows/maven.yml)) builds and tests both modules with JDK 25 using a
  matrix strategy, and submits the Maven dependency graph to GitHub on every push to `master`.
- **Dependabot** ([`dependabot.yml`](.github/dependabot.yml)) checks Maven dependencies of each module daily and
  GitHub Actions weekly (05:00 Asia/Jakarta). Updates are grouped (Spring Boot, Spring, minor/patch, actions),
  labelled, and use conventional commit prefixes (`chore(deps)`, `ci(deps)`).

## Author

**Hendi Santika**

- Email: hendisantika@gmail.com
- Telegram: [@hendisantika34](https://t.me/hendisantika34)
- Link: [s.id/hendisantika](https://s.id/hendisantika)
