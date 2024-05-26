# socketServer3

가상의 local DNS 서버와 root DNS 서버, 두 단계 DNS 서버를 구현

* 클라이언트에서는 도메인 이름을 입력하면, 로컬 DNS 서버에서는 해당 IP 주소를 반환
* Local DNS 서버는 DB 또는 테이블로 도메인 이름과 IP 주소를 미리 저장
* 만약 Local DNS 서버에 해당 IP주소가 없다면 상위(root) DNS 서버에 문의 함
* 상위 DNS 서버에도 그 해당 주소가 없다면 에러 메시지 발생
* 클라이언트에서는 도메인 이름과 ip 주소를 등록할 수도 있음
