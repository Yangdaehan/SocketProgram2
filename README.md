# socketServer2

가상의 local DNS 서버와 root DNS 서버, 두 단계 DNS 서버를 구현

* 클라이언트에서는 도메인 이름을 입력하면, 로컬 DNS 서버에서는 해당 IP 주소를 반환
* Local DNS 서버는 DB 또는 테이블로 도메인 이름과 IP 주소를 미리 저장
* 만약 Local DNS 서버에 해당 IP주소가 없다면 상위(root) DNS 서버에 문의 함
* 상위 DNS 서버에도 그 해당 주소가 없다면 에러 메시지 발생
* 클라이언트에서는 도메인 이름과 ip 주소를 등록할 수도 있음
&nbsp;

&nbsp;
## 구현 설명

클라이언트 => local DNS 서버 => root DNS 서버 => local DNS 서버 => 클라이언트

위와 같은 flow로 진행되게 시스템을 구현하였습니다.

```c
local DNS 서버 = localhost:8080
Root DNS 서버  = localhost:8081
```

포트 번호를 다르게 하여 로컬 DNS 서버에 도메인이 없을 경우 루트 DNS 서버에 문의하여 해당 도메인을 찾고 이를 반환하게 하였습니다.
&nbsp;
&nbsp;

## 구현 예시


로컬 DNS 서버 데이터베이스에 있는 도메인은 아래와 같습니다. 
<img width="1044" alt="스크린샷 2024-05-27 오후 8 12 57" src="https://github.com/Yangdaehan/socketServer3/assets/68599095/f2ded604-5f81-4511-b0d2-f6b8645512f1">

루트 DNS 서버 데이터베이스에 있는 도메인은 아래와 같습니다.
<img width="1040" alt="스크린샷 2024-05-27 오후 8 15 48" src="https://github.com/Yangdaehan/socketServer3/assets/68599095/ba8f3fe9-ba43-4b99-b068-891666458872">


클라이언트가 "google.com" 에 해당하는 ip 주소를 DNS 서버에 요청하면 로컬 DNS 서버에는 해당 도메인이 없으므로, 루트 DNS 서버에 해당 도메인을 문의 하고 이를 찾아 반환하는 모습을 볼 수 있습니다.
<img width="1039" alt="스크린샷 2024-05-27 오후 8 18 56" src="https://github.com/Yangdaehan/socketServer3/assets/68599095/777a40ae-26f0-40c3-83ab-2b5d1429c76c">


만약 루트 DNS 서버에도 없는 도메인을 요청한다면 에러 메시지가 발생합니다.
<img width="1045" alt="스크린샷 2024-05-27 오후 8 20 05" src="https://github.com/Yangdaehan/socketServer3/assets/68599095/ffdd5e43-018f-49e5-bba8-d6e1a0d29cc7">


클라이언트가 로컬 DNS 서버 데이터베이스에 직접 도메인과 ip 주소를 저장할 수 있는 기능도 구현하였습니다.
{"localhost:8080/dns/register"}에 아래와 같이 Requset body를 json 형태로 채워주고 POST 요청을 보내면 됩니다.
```c
{
    "domain": "yahoo.com",
    "ipAddress": "171.01.23"
}
```
POST 요청을 보낸 후 list를 확인해보면 저장 된 것을 확인할 수 있습니다. "google.com"의 경우 루트 DNS 서버에서 가져 온 후 저장하였기 때문에 출력되어집니다.
<img width="1046" alt="스크린샷 2024-05-27 오후 8 25 22" src="https://github.com/Yangdaehan/socketServer3/assets/68599095/8a9e8235-d0be-4786-bca4-27fd1878557f">

&nbsp;
&nbsp;
## API 명세서


[API 명세서](https://documenter.getpostman.com/view/31445434/2sA3XSBMC5)

