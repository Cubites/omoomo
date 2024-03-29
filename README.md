# omoomo

# [종료] - [미니] 오목 웹 프로젝트

# 🎯 최종 목표

### 기능적 목표

- 소켓 통신을 이용한 웹 오목 게임
- 각자 판단하에 추가 기능(내용) 및 안해본 기술 스택 적용

### 기능외 목표

- 1차 프로젝트 전 조별 사전 팀워크 확인
- 지금까지 배운 내용 복습/응용

### 사용 기술스택

- HTML, CSS, JS, JSP/SERVLET, DB(제한없음)
- 웹소켓

# 🗓️ 일정

- 2023.10.27(금) ~ 2023.11.10(금)

# 👪 조원

- 강태연, 김이슬, 송진주, 장원, 주희철

<details>
<summary>
    
# 📋 회의록
</summary>

## 2023.10.27 (금)

### 목표

- 다음 회의까지 해야할 일 결정

### 회의 결과

- 각자 오목 판별 로직 완성
- 다음 회의 전까지 넣을 만한 기능 취합

## 2023.10.30 (월)

### 목표

- 넣을 기능 결정
- 다음 회의까지 할 일 결정

### 회의 결과

- 넣을 기능을 다음 목록들로 결정
    - 오목 게임 - 33금지, 44금지 기능 추가
    - 방 - 최대 8개까지 생성, 한 방에는 2명까지만 접속 가능
    - 회원가입 및 로그인 기능
    - 경기 승패 저장
    - 랭킹 표시 및 검색
- 11/03(금)까지 피그마에 각자 생각하는 화면 디자인이나 구도 작성
    - 링크: [Omoomo 화면디자인](https://www.figma.com/file/6sm7MYRBKKIIccXnclDk76/Omok?type=design&mode=design&t=RTWyakm4AsoYa2MG-1)

## 2023.11.01 (수)

### 목표

- 역할 분담

### 회의 진행

- 만들어야할 기능 및 인원 배분
    - 2명 - [소켓] 오목 게임 및 로직, 채팅
    - 1명 - 방 생성 및 대기 화면
    - 1명 - 경기 승패 저장 및 랭킹 표시
    - 1명 - 회원가입 및 로그인
- 각자 하고싶은 기능 선택
    
    
    | 이름 | 1지망 | 2지망 |
    | --- | --- | --- |
    | 김이슬 | 오목 게임 | 경기 승패 저장 및 랭킹 |
    | 강태연 | 오목 게임 및 로직, 채팅 |  |
    | 송진주 | 오목 게임 | 회원가입 및 로그인 |
    | 장원 | 회원가입 및 로그인 | 오목 게임 |
    | 주희철 | 오목 게임 |  |

### 회의 결과

- 역할
    - 강태연 - 방 생성 기능 및 대기방 페이지
    - 김이슬 - 오목 승리, 33금지, 44금지 로직
    - 송진주 - 회원가입 및 로그인 기능
    - 장원 - 경기 승패 저장 및 랭킹 표시 기능
    - 주희철 - 오목 게임 및 채팅 프로그램

## 2023.11.03 (금)

### 목표

- 각자 할 작업을 명확히 결정
- 화면 디자인 결정

### 회의 결과

- 화면 디자인 확정
- 장원 - 서버 작업 분리 구조 구축
- 주희철 - 웹소켓 부분 작업 분리 구조 구축
- 분리 구조 구축 후, 각자 작업 진행

## 2023.11.06 (월)

### 목표

- 각자 진행할 작업 확정

### 회의 진행

- 방 생성은 소켓과 연관이 없음을 확인

### 회의 결과

- 각자 작업 시작

## 2023.11.09 (목)

### 목표

- 통합 작업 완료

### 회의 결과

- 통합 작업 미완료
- 회의 전까지 계속 진행

## 2023.11.10 (금) - 발표 당일

### 회의 결과

- 각자 맡은 부분에서 불필요한 소스 정리 및 주석 추가

## 2023.11.13 (월)

### 목표

- 각자 수정한 소스 병합

### 회의 결과

- 소스 병합 완료
</details>

# 🎲 최종 결과

### 페이지 디자인

- 순서대로 처음 화면, 대기방 화면, 게임 화면

![home](https://github.com/Cubites/omoomo/assets/75084369/a29892e8-6ffb-458c-ae8f-1cd82711dfdc)

![waitingroom](https://github.com/Cubites/omoomo/assets/75084369/2868e21c-ea38-4cb5-b847-4de00401ca46)

![gamepage](https://github.com/Cubites/omoomo/assets/75084369/3754679b-70d3-4635-9fe7-353795cfe689)

## 필요한 라이브러리
* .jar
    * json: https://mvnrepository.com/artifact/org.json/json/20231013
    * javax.json: https://mvnrepository.com/artifact/org.glassfish/javax.json/1.1.4
    * Java WebSocket: https://mvnrepository.com/artifact/org.java-websocket/Java-WebSocket/1.5.4
    * jstl: https://mvnrepository.com/artifact/javax.servlet/jstl/1.2
    * gson(2.8.5):https://mvnrepository.com/artifact/com.google.code.gson/gson/2.8.5
    * ojdbc8-23.2 : https://mvnrepository.com/artifact/com.oracle.database.jdbc/ojdbc8/23.2.0.0 
    * lombok : https://mvnrepository.com/artifact/org.projectlombok/lombok/1.18.30
