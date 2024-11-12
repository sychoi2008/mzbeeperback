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
5. https를 사용해볼것
6. 웹페이지지만 알람 기능?


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

-> 세션 로그인의 단점 + jwt의 장점 + a(나의 프로젝트가 어떤 점에 있어서 jwt에 적합했는지! jwt의 **특성**을 언급하자)
-> JWT 로그인 방식을 잘 선택하신 것 같아요!

웹페이지에서 삐삐와 같은 기능은 모바일과 유사하게 빠르고 상태 비유지적인 연결을 요구할 가능성이 큽니다. 이 경우, JWT는 무상태(stateless)로 작동하여 서버가 세션을 관리할 필요가 없어 스케일링이 용이하고, 비용 절감에도 도움이 됩니다. 또한, 삐삐 기능이 알림을 많이 사용하거나 여러 서버 간 인증이 필요할 때, 서버 부담이 적고 확장성 높은 JWT 방식이 적합합니다.

다만, 보안에 신경을 쓰셔야 하므로 HTTPS 사용, 토큰 만료 시간 설정, 그리고 필요 시 리프레시 토큰 전략을 활용하면 보안성을 높일 수 있습니다.
(chat gpt의 말을 토대로 보안성을 유지하기 위해 https도 유의해야 한다)
