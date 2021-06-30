# HanshinShop (쇼핑몰)

### 기술
- SpringBoot
- MyBatis
- JPA
- Mysql
- Git
- JWT
- Spring-Security
- 진행 중

### 현재 구현 사항

- JWT 로그인
  - Spring Security 사용 후 ArgumentResolver 사용하여 로그인 유저 정보 전달
  - 각 사용자별 권한 검증 후 페이지 별 권한 체크
  - RestApi로 구현
- 관리자 상품 등록
  - Form Data 형식으로 데이터 전달 후 Mustache 문법 사용
- 메인화면
  - 관리자 상품 등록 후 RestApi로 카테고리, 상품들 요청
  - main.js 동적 데이터
- 장바구니 
- 주문 구현
- 페이징 처리 
- 주문
- Mybatis to JPA(~ing)

