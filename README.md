# TodoList

### 1. 앱에 대한 설명
저는 Todolist를 만들었습니다. 사용자가 해야 할 작업과 계획을 간단하고 효율적으로 관리할 수 있습니다. 
팀 단위 Todolist 기능도 넣어 팀 프로젝트에서 공동으로 해야할 작업이나 계획을 팀 단위로 관리할 수 있습니다. 그리고 실시간 채팅 기능을 통해 웹 상에서 팀원들끼리 접속해 있으면 소통이 가능합니다. 
프론트엔드는 테스트를 기능 테스트를 위해 만들었고 css 및 구조가 부실합니다..

### 2. 소스 빌드 및 실행 방법
1. 폴더에 첨부된 todo.sql 파일을 실행해 DB 구현
2. git clone https://github.com/kmsjkh12/todo_back.git 다운로드
3. dependecy 다운로드
4. 실행
5. 테스트를 위해 폴더에 첨부된 todo_front 파일 npm install
6. npm run start
7. test id : test1@test.com password : 1234 로그인 
8. 기능 테스트
9. 웹 소켓 테스트를 하려면 다른 브라우저를 킨 후 회원가입 및 로그인 후 teamid : 1 으로 팀 가입 후 웹 소켓 테스트 진행 팀( Development Team )으로 테스트 진행

### 3. 주력으로 사용한 컴포넌트에 대한 설명 및 사용 이유 기입
우선 기본적인 투두의 기능은 모두 넣었고 중점적으로 설계한 부분은 팀 투두 기능입니다. 팀 프로젝트 시 팀원과 같이 작업이나 계획을 관리하는 투두 기능이 있었으면 좋겠다고 생각했고, 그 안에 채팅 기능까지 넣으면 페이지 안에서 소통하며 계획과 작업을 할 수 있고, 효율적이라고 생각했습니다.
그리고 로그인 기능은 처음에는 넣으려고 생각하지 않았지만, 팀 투두 기능이 들어가며 자연스럽게 유저에 대한 정보가 필요했고, Spring security와 jwt 기능을 사용하여 토큰을 쿠키에 넣고 클라이언트에게서 요청이 오면 쿠키에서 토큰을 꺼내 사용자에 대한 검증을 했습니다. 


### 4. API 명세서 
API 명세서는 노션을 사용하여 작성했습니다.
https://stream-swamp-a58.notion.site/api-f4e6b1cb7e7f48fe91af6da4e276465f?pvs=25

### 5. 추가적인 기능
추가적인 기능은 웹소켓을 통한 팀 단위 실시간 채팅 기능을 구현했습니다. 팀 투두리스트에서 팀원이 다같이 계획을 짜는데 소통 기능이 꼭 필요하다고 생각되어서 넣었습니다. 