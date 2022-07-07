# LoginAPI

## #1. 요구사항 
**1. 회원가입**  
**2. 로그인**

## #2. 기술 Stack
**1. Spring-boot**  
**2. Java 11(open-jdk 11)**  
**3. Mysql**  
**4. Redis**  

## #3. Table 구성   
**Member Table.**  

<img width="1254" alt="스크린샷 2022-06-08 오후 5 20 01" src="https://user-images.githubusercontent.com/78134917/172568210-9bd4cc59-18ff-4540-8c38-5b1d87d24201.png">
 
**Login History Table.**  

<img width="1056" alt="스크린샷 2022-06-08 오후 5 21 40" src="https://user-images.githubusercontent.com/78134917/172568463-d27d79d6-cf31-4ed9-98d6-387123c7ec4f.png">
 
 
 ## #4. 설명 
 2022/04 ~ 2022/05
**#Spring #Java #Mybatis #Mysql #Redis**   
**#0. 개요**  
앞으로 진행할 프로젝트들에 적용할 Login&Join API를 개발했습니다.   

**#1. 다중 서버에 대비한 JWT token 사용**  

대규모 트래픽이 발생하는 scale-out된 서비스에 적용 가능한 loginAPI를 지향했습니다. 다중 서버에서 가벼운 사용자 인증과 인가를 위한 Jwt Token을 사용했습니다. 디비를 session 저장소로 이용할 경우 사용자 인증을 시도할때마다 I/O 부하가 발생합니다. 하지만 Jwt Access Token의 경우 Header hasCode. payload hasCode. signature hasCode 의 구성을 가지고 있습니다. 그중 Payload 부분에 사용자와 만료기간에 대한 정보가 json을 hashing한 값이 들어가 있습니다. 

이제 이 token payload 부분만 디코딩하면 요청한 사용자가 로그인 가능 시간 안에 있는지 어떤 권한을 가지고 있는지를 파악할 수 있습니다. 해당 토큰에 만료 기간만 지나지 않는다면 따로 DB에서 정보를 조회하고 비교할 필요가 없습니다. 따라서 디코딩말고는 추가적인 비용이 들지 않습니다. 이러한 이유로 jwt Token을 선택하였습니다.  
  
하지만 Access Token이 탈취 당하고나면 막을 방법이 없다고 생각했습니다. 구글링한 결과 Refresh Token을 사용할 수 있었습니다. access Token의 기한을 6시간으로 두고 Refresh Token의 만료기한을 일주일로 설정하였습니다.

**#2. Redis를 이용한 로그인 상태 유지**  
  
하지만 이 Refresh Token을    
  
sticky session을 사용할 경우 요청이 고정된 서버에 장애가 발생했을 때 사용자에게 불편함을 줄 수 있습니다. 또한 Session Clustering을 고려했지만 Session 데이터가 업데이트 될 때마다 모든 Session을 업데이트 시켜줘야하는 비용이 발생합니다. 이런 비용들을 상쇄시키도록 Session Storage를 선택했습니다.   

Session의 데이터는 현재 사용자가 로그인 상태인지 아닌지 체크할 수 있는 사용자 정보 데이터만 가지고 있을 것이기 때문에 데이터가 계속 유효할 필요가 없었습니다. 그래서 Session 저장소를 Disk DB 방식이 아닌 In-memory DB를 채택했고 In-memory DB시스템 중에 Redis를 선택하여 사용하였습니다. 
  

**#3. Global Exception 전략(Custom Exception으로 명확한 예외 발생, if-else문 줄이기)**  
  
자바에서 기본적으로 제공하는 Exception으로 예외를 처리하면 어느 로직에서 어떤 예외가 발생한건지 에러가 발생했는지 정확하게 파악하기 어려웠습니다. 또한 발생하는 예외를 모두 try-catch문이나 if문으로 처리한 경우 코드가 복잡해지고 가독성이 떨어져 유지보수의 어려움이 발생했습니다. 따라서 발생 가능한 예외 상황을 예상하고 CustomException을 만들었습니다. 이렇게 예외 발생시 원인을 정확히 파악할 수 있도록 하였고 간단한 코드로 쉽게 예외를 처리할 수 있게 설계하였습니다.
  
서비스에서 발생하는 모든 에러를 한곳에서 처리하기 위해 @ControllerAdvice를 사용하여 예외에 대한 모든 관심사를 한곳으로 집중 시켰습니다. 반환 타입을 통일 시키기위해 ErrorResponse를 반환해 주었습니다.   
  



 ## #5. 기능 테스트  
 ### 로그인 기능.  
case 1. 로그인 성공(액세스토큰, 리프레시토큰 반환) 
 <img width="1294" alt="스크린샷 2022-06-08 오후 5 32 28" src="https://user-images.githubusercontent.com/78134917/172570665-401a484c-c7de-4cf3-bf61-8525a38caafa.png">.  
case 2. 엑세스 토큰 입력 후 로그인 성공 
<img width="1295" alt="스크린샷 2022-06-08 오후 5 34 20" src="https://user-images.githubusercontent.com/78134917/172571191-d88df01d-cf0f-4fff-937d-88347832554e.png">.  

 ### 로그아웃 기능.  
 <img width="1295" alt="스크린샷 2022-06-08 오후 5 37 16" src="https://user-images.githubusercontent.com/78134917/172571771-52a49e83-021a-4dad-b978-d04116022b7d.png">.  
  
 
 ### 회원가입 기능.   
 <img width="1291" alt="스크린샷 2022-06-08 오후 5 30 22" src="https://user-images.githubusercontent.com/78134917/172570259-8054ba3f-4fce-42ff-9e3d-16c131e9fcf3.png">.  
<img width="460" alt="스크린샷 2022-06-08 오후 5 30 43" src="https://user-images.githubusercontent.com/78134917/172570308-1b9eba03-a02d-4306-876e-0c1b4919a9ef.png">.  

 
