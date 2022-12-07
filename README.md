# MyBlog-RESTFulAPI

## 22-11-03
 - OpenApi 신청 후 단기 예보 JSON형식으로 받음.
 - WebClient 사용.
 - 받은 값 파싱 후 DTO 매핑, DB 저장 예정

## 22-11-22
 - numofrows를 290을 설정 => 24시간동안 시간당 12개의 데이터와 최저기온, 최고기온을 받으므로 290으로 설정
 - base_date를 전 날 23시로 설정
 - 그외 fixed 설정값 제외하고 postmapping + requestbody로 요청받고 responseentity로 응답(http상태코드, json)
 
## 22-12-07
 - 단기 예보 시간당 기온과 최고/최저 기온 JSON response
 
## 22-12-08
 - 중기 예보(3~10일 최저/최고 ) JSON response
