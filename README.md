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

<img width="1260" alt="스크린샷 2022-07-07 오후 2 11 55" src="https://user-images.githubusercontent.com/78134917/177696412-52fbc1e9-3cd8-4548-8453-f5ea9f1ec9e6.png">

 
**Login History Table.**  

<img width="1268" alt="스크린샷 2022-07-07 오후 2 12 24" src="https://user-images.githubusercontent.com/78134917/177696456-501c723d-e07a-42d7-aacb-492dc20f2333.png">

 ## #4. 설명 
 2022/04 ~ 2022/05
**#Spring #Java #Mybatis #Mysql #Redis**   
**#0. 개요**  
앞으로 진행할 프로젝트들에 적용할 Login&Join API를 개발했습니다.   

**#1. 다중 서버에 대비한 JWT token 사용**  

대규모 트래픽이 발생하는 scale-out된 서비스에 적용 가능한 loginAPI를 지향했습니다. 다중 서버에서 가벼운 사용자 인증과 인가를 위한 Jwt Token을 사용했습니다. 디비를 session 저장소로 이용할 경우 사용자 인증을 시도할때마다 I/O 부하가 발생합니다. 하지만 Jwt Access Token의 경우 Header hasCode. payload hasCode. signature hasCode 의 구성을 가지고 있습니다. 그중 Payload 부분에 사용자와 만료기간에 대한 정보가 json을 hashing한 값이 들어가 있습니다. 

이제 이 token payload 부분만 디코딩하면 요청한 사용자가 로그인 가능 시간 안에 있는지 어떤 권한을 가지고 있는지를 파악할 수 있습니다. 해당 토큰에 만료 기간만 지나지 않는다면 따로 DB에서 정보를 조회하고 비교할 필요가 없습니다. 따라서 디코딩말고는 추가적인 비용이 들지 않습니다. 이러한 이유로 jwt Token을 선택하였습니다.  
  
하지만 Access Token이 탈취 당하고나면 막을 방법이 없다고 생각했습니다. 구글링한 결과 Refresh Token을 사용할 수 있었습니다. Access Token에 기한을 6시간으로 두고 인증에 대해 확인하고 Refresh Token의 만료기한을 일주일로 설정하여 로그인 유지 기한을 확인하기 위한 토큰으로 사용했습니다.

**#2. Redis를 이용한 로그인 상태 유지**  
  
Refresh Token을 어디에 저장할 것인가를 결정해야했습니다. 결국 서버에 상태를 유지하기위해 session을 사용해야 했습니다. AccessToken을 사용하는 이유가 IO부하를 줄이기 위해 사용했는데 DB Session에 저장하는 것은 논리에 맞지 않았습니다. 

in-memory Db를 사용하여 Session 저장소를 구축했습니다. In-memory Db는 렘메모리를 사용하는 방식의 디비입니다. 하드웨어보다 전기신호를 통해 조회하는게 훨씬 빠릅니다. 또한 Session에 저장되는 데이터들은 영구적일 필요가 없는 휘발성 데이터들이었기 때문에  In-memory DB를 사용하기로 했습니다. 여러가지 in-memoryDb들이 존재했습니다. MemCached와 Redis사이에서 고민했습니다. 조금 더 다양한 기능을 제공해주고 여러가지 자료구조를 제공해주기 때문에 Redis를 사용하기로 했습니다.  
  
이렇게 Redis Session에 Token을 조회할 수 있는 key(Id)와 Refresh Token을 value 값으로 저장하는 방식으로 구현했습니다.
  
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

 
