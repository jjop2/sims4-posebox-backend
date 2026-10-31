# Sims4 PoseBox - Backend

심즈4 포즈를 인원별·태그별로 검색할 수 있는 사이트입니다.
포즈 파일을 직접 배포하지 않고, 제작자의 원본 게시글로 연결하는 아카이브 서비스입니다.

개발 진행 중인 프로젝트입니다. 현재는 회원/인증 기능을 중심으로 구현했습니다.

> 🔗 [Frontend](https://github.com/jjop2/sims4-posebox-frontend.git)

## 기술 스택
- Java 17, Spring Boot 4, Spring Security, Spring Data JPA
- MySQL, JWT(JJWT), BCrypt

## 구현 기능
- 회원가입 (이메일/비밀번호, Validation, 이메일 정규화·중복 검사)
- 로그인 (Spring Security AuthenticationManager)
- JWT Access Token (1시간), Refresh Token (Opaque, 30일, httpOnly 쿠키)
- Refresh Token Rotation, 로그아웃
- 계정 모델 분리: User / LocalAccount / OauthAccount
- 통일된 에러 응답 (ErrorCode + ErrorResponse)

## 설계 결정
- **Refresh Token을 DB에 저장하는 Opaque Token으로**: 서버에서 즉시 폐기할 수 있도록
- **Access Token은 메모리, Refresh Token은 httpOnly 쿠키**: XSS 노출 최소화
- **LocalAccount/OauthAccount를 User와 1:1 식별관계로 분리**: 로그인 수단과 회원 정보의 분리
- **일반 가입과 OAuth 가입은 계정 연동 없음**: 이메일 인증이 없어 이메일 소유가 검증되지 않기 때문

## 실행 방법
1. 'src/main/resources/application.properties.example'을 'application.properties'로 복사
2. DB 접속 정보 입력 (MySQL에 빈 DB 생성)
3. './gradlew bootRun' (기본 포트 8888)

## 알려진 한계 / 로드맵
- JWT 서명 키가 서버 재시작 시 새로 생성됨 (배포 전 환경변수화 예정)
- 로그인/가입 요청 횟수 제한 없음
- Refresh Token 평문 저장, 재사용 탐지 미적용 (서비스 민감도를 고려한 선택)
- Refresh 동시 요청 경쟁 조건, 만료 토큰 정리 스케줄러 미구현
- 예정: Google OAuth, Creator 기능, 찜 기능
