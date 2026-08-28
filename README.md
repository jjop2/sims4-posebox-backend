# Sims4 PoseBox - Backend
Spring Boot 기반으로 개발 중인 Sims4 PoseBox 백엔드 프로젝트입니다.
Sims4의 다양한 포즈들을 인원별, 태그별로 쉽게 검색할 수 있는 사이트로 계획하고 있습니다.
현재 인증(Authentication) 기능을 중심으로 구현하고 있으며, Spring Security + JWT로 진행하고 있습니다.

## 기술 스택
* Java 17
* Spring Boot
* Spring Data JPA
* MySQL
* JWT (JJWT)
* BCrypt

## 구현 기능
* 회원가입 (이메일/비밀번호 기반, Validation 적용)
* 로그인 (Spring Security AuthenticationManager 기반)
* BCrypt 비밀번호 암호화
* JWT 발급 및 검증 (Spring Security 필터 연동)
* Spring Security 필터 체인 구성
* 일반 회원 / 소셜 로그인 계정 테이블 분리 (User / LocalAccount / OauthAccount)

## 진행 예정
* Refresh Token
* OAuth (Google Login)

---

> 🚧 This project is under active development.
> Features and documentation will be continuously updated.
