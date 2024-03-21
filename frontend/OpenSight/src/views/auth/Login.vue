<script setup>

function naverSocialLogin() {
  window.location.href = 'https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=4hSpDvA1GDlGXjqG6sac&redirect_uri=http://192.168.31.25:8080/login/oauth2/code/naver'
}
</script>

<template>
  <div class="screen-container">
    <div class="login-header">로그인</div>
    <div class="login-container">
      <div class="login-subheader">환영합니다</div>
      <div class="login-description">여기에 사이트 설명이나 그것과 관련된 내용</div>
      <input type="email" placeholder="ssafy@ssafy.com" class="login-input" />
      <input type="password" placeholder="**********" class="login-input" />
      <button type="submit" class="login-button">로그인</button>
      <div class="help-links">
        <img class="login-naver" src="../../assets/img/naver.png" alt="네이버 소셜로그인 하기" @click="naverSocialLogin">
        <a href="#" class="help-link">회원이 아니신가요? <span class="link-highlight">회원가입</span></a>
        <a href="#" class="help-link">이메일을 잊으셨나요?</a>
        <a href="#" class="help-link">비밀번호를 잊으셨나요?</a>
      </div>
    </div>
  </div>
</template>

<style scoped>
.screen-container {
  /* display: flex; */
  align-items: flex-start;
  justify-content: center;
  overflow: auto;
  top: 0;
  left: 0;
  position: fixed;
  width: 100vw;
  height: 100vh;
  background: #1B3C62;
  font-family: 'Poppins', sans-serif;
}

.login-container {
  background-color: #ffffff;
  border-radius: 20px 20px 0 0; /* 아래쪽 모서리는 둥글지 않음 */
  width: 320px; /* 가로 크기 유지 */
  padding: 20px;
  margin: 0 auto;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
  position: fixed; /* 화면에 고정 */
  top: 130px; /* 상단으로부터 130px 떨어진 위치에 배치 */
  left: 50%; /* 가운데 정렬을 위해 */
  transform: translateX(-50%); /* 가운데 정렬을 위해 */
  height: calc(100vh - 130px); /* 상단부터 130px 위치부터 하단까지의 높이 설정 */
  /* overflow: auto;  */
  /* 나머지 스타일은 기존에 설정한 대로 유지 */
}

.login-header {
  color: #ffffff;
  height: calc(770px - 100px);
  top: 100px;
}

.login-subheader {
  color: #1B3C62;
  font-weight: bold;
  font-size: 1.5em;
  margin-bottom: 0.5em;
}

.login-description {
  margin-bottom: 2em;
  color: #1B3C62;
  font-size: 0.9em;
}

.login-input {
  border: 1px solid #cbd5e0;
  border-radius: 4px;
  padding: 1em;
  margin-bottom: 1em;
  width: calc(100% - 2em);
  font-size: 0.9em;
}

.login-button {
  background-color: #87AAD3;
  color: white;
  border: none;
  border-radius: 4px;
  padding: 1em;
  width: 100%;
  font-size: 0.9em;
  margin-bottom: 1.5em;
  cursor: pointer;
  transition: background-color 0.2s;
}

.login-button:hover {
  background-color: #1B3C62;
}

.login-naver {
  width: 50px;
  height: 50px;
}

.help-links {
  font-size: 0.8em;
}

.help-link {
  color: #343434;
  display: block;
  margin-bottom: 0.5em;
}

.link-highlight {
  color: #1B3C62;
  font-weight: bold;
  text-decoration: none;
}

/* 각 요소의 사이즈 및 색상은 이미지를 기반으로 조정하였습니다. */
</style>
