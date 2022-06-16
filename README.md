# 항해99 2주차 과제

<br>

# 1. 공통 질문

## 전체 공통
### [프레임워크와 라이브러리의 차이점](https://planet-punishment-427.notion.site/df2138aca6384f22b197792c401097f8)


프레임워크와 라이브러리의 차이는 **제어의 흐름에 대한 주도성이 누구 / 어디에 있는가**로 구분할 수 있다.

즉, 어플리케이션의 흐름(flow)를 누가 쥐고 있느냐가 둘을 구분하는 가장 큰 특징이다.

프레임워크는 **전체적인 흐름을 스스로가 쥐고 있으며 그 안에서 필요한 코드를 짜 넣는** 반면, 라이브러리는 **사용자가 전체적인 흐름을 만들면서 라이브러리를 가져다 쓰는 것**이라고 할 수 있다.

<br>
<br>

### [코드를 구현할 때 예외 처리를 위해 무엇을 했나요?](https://planet-punishment-427.notion.site/c3153169becc45a3897d2ba66ff1cd16)

예외 처리 방식으로 <code>@RestControllerAdvice</code>를 사용하였다.
예외 처리를 하기 위한 다양한 방법 중 해당 방식을 사용한 이유는 다음과 같다.

1. Spring이 제공하는 기본 예외처리 방식은 일련의 설정을 거치더라도 노출하면 안되는 정보를 반환해주거나, 클라이언트에게 유의미한 정보를 제공해주지 못하기 때문이다.
  
2. 다른 방식에 비해 @ExceptionHandler와 함께 사용되는 @RestControllerAdvice를 사용하는 것이 전역적으로, 또 유연하게 에러를 처리할 수 있기 때문이다.

<br>
<br>

## 백엔드 공통

[REST API 정리 글 BY 진유진](https://annajin.tistory.com/70)

<br>
<br>

### Restful이란?

Rest는 HTTP 통신에서 어떤 자원에 대한 CRUD 요청을 Resource와 Method로 표현하여 특정한 형태로 전달하는 방식을 말하고, Restful이란 Rest API의 설계가이드를 '올바르게' 따라 설계하는 것을 말한다.

<br>
<br>

### [왜 Restful하게 짜야하나요? 참고글](https://shyvana.tistory.com/7)

분산 시스템을 위해서. Restful API를 서비스하기만 하면 어떤 다른 모듈 또는 애플리케이션들이라도 Restful API를 통해 상호간에 통신을 할 수 있다.

WEB브라우저 외의 클라이언트를 위해서(멀티 플랫폼). Restful API를 사용하면 데이터만 주고 받기 때문에 여러 클라이언트가 자유롭고 부담없이 데이터를 이용할 수 있다.

<br>
<br>

### [Restful의 장/단점](https://planet-punishment-427.notion.site/REST-API-1e7104d710fb462a8654d1ceb397aefe)

장점 - 서버와 클라이언트의 역할을 명확하게 분리한다.

단점 - 표준이 자체가 존재하지 않아 정의가 필요하다.

<br>
<br>

### [Restful의 대안은?](https://planet-punishment-427.notion.site/Restful-952b45317a124e2a866cbcca68c16bc5)

GraphQl - GraphQL은 Server API를 구성하기 위해 Facebook에서 만든 쿼리 언어. 하나의 앤드포인트만을 가지며, 원하는 응답값만을 받아올 수 있다는 REST와의 차이점이 존재한다.

<br>
<br>

### Restful하게 짜기 위해 무엇을 고려했나요?

[참고](https://sanghaklee.tistory.com/57)

<br>

- 고려한 점
1. CRUD별로 적절한 메소드(POST, GET, PUT, DELETE)를 사용함. -> 새롭게 알게 된 사실. update에 적절한 메소드는 PATCH라고 한다.
2. Restful API의 URI 설계 규칙을 최대한 반영함.
3. 적절한 HTTP status 코드를 응답함. (2xx, 4xx)

<br>

- 고려하지 못한 점
1. Header에 적절한 내용을 담는 것.


<br>
<br>

## Spring

### Entity 설계를 위해 무엇을 하였나요? 연관관계에 근거하여 설명해주세요. **\<help wanted\>**

- User <-> Post

  <code>1:N</code> <-> <code>N:1</code> 양방향 매핑
  
- Post <-> Comment

  <code>1:N</code> <-> <code>N:1</code> 양방향 매핑
  
<br>

> 양방향 매핑을 사용한 이유?
>> 각 엔티티의 FK값은 N부분(Post, Comment)가 관리한다. 즉, 연관관계의 주인은 N이라는 말이다. 
>> 만약 다대일 단방향 매핑 전략을 사용했을 경우, N의 insert 쿼리가 날아갔을 때 1 부분은 연관관계가 존재하지 않기 때문에 해당 부분에서 변경이 일어났을 때 다른 테이블(N) 부분에도 update 쿼리가 날아가게 된다.
>>
>>
>>  **이 부분은 확실하게 이해가 되지 않아서 더 공부가 필요할 것 같다. Post는 Comment Entity를 조회해야하므로 양방향 관계를 맺어야한다는게 이해가 되는데 User Entity는 단방향 매핑을 해도 되는게 아닐까?**

<br>
  
- User, Post -> Likes

  -> <code>N:1</code> 단방향 매핑
  
  
<br>
<br>

# 2. Lv.2 Spring
## 인증 / 인가 
- **인증 (Authentication)**: 사용자 신원을 확인하는 행위
- **인가 (Authorization)**: 사용자 권한을 확인하는 행위

<br>

### Token vs. Session

- [Token과 Session 중 어느 방식을 사용할까?](https://planet-punishment-427.notion.site/Token-Session-e3a82b8395df42d59cff885d612026ab)

  Token : 확장성 용이. 토큰을 사용하는 다른 인증시스템 이용 가능. 보안에 상대적으로 취약
  
  Session : 보안에 상대적으로 효과적. 서버에 부담이 갈 수 있음
  
  <br>
  
  토큰과 세션을 공부하면서 내린 결론은 대규모 서비스를 목표로 하고 있고, 다양한 인증 시스템을 사용하려고 한다면 토큰을, 서버의 부담을 감수하더라도 보안이 중요한 서비스를 운영한다면 세션을 사용하는게 좋을 것 같다.


<br>
<br>

## CORS(Cross-Origin Resource Sharing)

[CORS란? 작성 중](https://planet-punishment-427.notion.site/CORS-Cross-Origin-Resource-Sharing-74fc7e640084455996213822c9a6d703)


<br>
<br>

# 3. Trouble Shooting

## [Trouble Shoooting](https://planet-punishment-427.notion.site/0c4e6bd4ae194ede89b0d20825a5b1ec)
