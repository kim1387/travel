# 기능 요구사항
## 여행지(도시) 관리
### 핵심 모델
도시
- [ ] 필수 속성 : 도시 이름
추가 속성은 자유롭게 정의
### API
- [X] 도시 등록 API
    - [X] test
    - [ ] request에 대한 검증

- [X] 도시 수정 API
    - [X] 기존 도시 속성(도시 이름, 추가 속성) 수정
    - [ ] test
    - [X] 예외 처리
      - [X] 존재하지 않는 city
    - [ ] request 에 대한 검증
- [X] 도시 삭제 API
    - [X] 단, 해당 도시가 지정된 여행이 없을 경우만 삭제 가능
    - [ ] test
  - [ ] 예외 처리
    - [X] 존재하지 않는 city
- [X] 단일 도시 조회 API
  - [X] 도시 조회시 조회 수 +1
  - [X] 최근 조회 날짜 업데이트
- [ ] 사용자별 도시 목록 조회 API
  - [ ] test
  - [X] 기본적으로 중복 없이 상위 10개 도시만 노출 (Pagination 없음)
  - [ ] request에 대한 검증
## 여행 관리
여행지(도시) 관리를 완전히 동작시켜 보기 위해, 아래 요구사항으로 여행 관리 최소 스펙을 구현합니다. 
### 핵심 모델
여행
- [ ] 필수 속성 : 도시, 여행 기간(여행 종료일은 미래만 허용)
추가 속성은 자유롭게 정의
### API
- [X] 여행 등록 API
  - [ ] test
  - [ ] request에 대한 검증

- [X] 여행 수정 API
  - [X] 기존 여행 속성(도시, 여행 기간, 추가 속성) 수정
  - [ ] test
  - [ ] 예외 처리
    - [X] 존재하지 않는 city를 찾을 때
    - [X] 존재하지 않는 travel을 찾을 때 (이미 삭제된 travel에 접근할 경우)
  - [ ] request에 대한 검증

- [X] 여행 삭제 API
    - [ ] test
    - [ ] 예외 처리
      - [X] 존재하지 않는 travel을 찾을 때 (이미 삭제된 travel에 접근할 경우)
- [X] 단일 여행 조회 API
    - [X] test
    - [X] 예외 처리
      - [X] 존재하지 않는 travel을 찾을 때 (이미 삭제된 travel에 접근할 경우)
## global 예외 상황
- [X] 존재하지 않는 Entity 조회
- [ ] 400 클라이언트에서 비정상적인 값 입력
- [ ] 405 허용되지 않은 Http method 요청
- [ ] request 에 대한 검증 BusinessException

## 기술 요구사항
- 프로그래밍 언어는 Java 혹은 Kotlin 사용 -> Java
- 빌드 도구는 Gradle 혹은 Maven 사용 -> Gradle
프레임워크, 라이브러리는 자유롭게 선택
- [X] 환경별로 데이터베이스 구분
  - development, test : H2
  - production : MySQL
- 테스트 작성
  - 필수 : API 검증 테스트 (Acceptance Test)
  - 권장 : 단위 테스트 (Unit Test)
- [X] README 파일에 로컬 실행 방법 기술

