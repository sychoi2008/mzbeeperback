## 프로젝트 구조
<img width=500 src="https://postfiles.pstatic.net/MjAyNDA4MjFfMjYg/MDAxNzI0MjM0NTUzNjc5.QIEfGgbysrA2cXmXPqGcYOzDAzZO84K-hc8vdLZ-1Fog.8qnvdEr1QAZmgj5Fz7kqv33JZUhghqgRdFbzspKfdnkg.JPEG/Untitled_Design-001.jpg?type=w966" />

## DB table
<img width=700 src="https://postfiles.pstatic.net/MjAyNDA4MjFfMTk5/MDAxNzI0MjI5MzI4NjE1.NapBYRYcBqqy7K6mr7OsRdsZlW-l9U1veqQQ1H_OIEIg.9R72hmgXI07svrTuGCcnDNLA1eU7QO-m4cCDHA8BJOIg.PNG/MZBeeper_(1).png?type=w966" />

## 노력한 곳
### 1. 스프링 시큐리티 없이 로그인 및 jwt 발급 기능
### 2. 


## 더 알아보기
### 1. UUID
https://www.baeldung.com/java-uuid

## 리팩토링 예정
1. JPA로 바꾸기
2. 객체지향스럽게 바꾸기 => 연관관계 사용
3. 테스트 코드 작성
4. 리다이렉션이 되지 않음을 발견 -> 스프링 시큐리티로 바꿔보기 


## 왜 jwt 로그인 방식을 사용했는가?
- 비교 대상은 <세션 로그인 방식>
  - 세션 id가 중요함 
- 1) 세션 로그인 방식은 서버의 부담이 크다
     - 세션은 stateful 기반으로 동작 -> 서버가 클라이언트의 상태도 모두 저장해야 함 
     - 세션 상태, 세션 id 모두 서버가 관리해야 함
     - 왜 서버 부담이 크냐?
       - 상태를 저장하므로 그 만큼 메모리를 잡아먹음
       - 로드 밸런싱이 되면은 다른 서버에서 현재 클라이언트의 상태를 공유해야 하므로 그만큼 관리 복잡성, 데이터 일관성 문제 발생 
       - 세션이 만료되면 또 서버에서 삭제해줘야 함
       - 하지만 jwt토큰은 서버가 그냥 토큰이 유효한지만 판단하면 되어서 굉장히 가벼움. 그리고 클라이언트 상태를 알 필요가 없으므로 서버 확장 쉬움, 
- 2) 보안성
     - 세션 id는 탈취 가능성이 있음(해커가 서버 id를 탈취해 클라이언트인 척을 할 수 있음)
    
이러한 이유로 JWT 로그인 방식을 선택하게 되었습니다. JWT는 stateless 하며, 서버가 클라이언트의 상태를 저장하지 않기 때문에 서버의 부담이 줄어들고 보안성을 강화할 수 있습니다. JWT의 구조상 만료 시간을 설정하여 유효성을 관리하고, 리프레시 토큰을 통해 사용자 경험을 개선할 수 있는 이점도 있습니다.
