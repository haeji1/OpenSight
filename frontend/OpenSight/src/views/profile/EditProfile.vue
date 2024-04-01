<script setup>
import { ref, computed } from 'vue';
import router from '@/router';
const password = ref('');
const isLengthValid = computed(() => {
  return password.value.length >= 10 && password.value.length <= 30;
});
const hasAllRequiredCharacters = computed(() => {
  const lowerCase = /[a-z]/.test(password.value);
  const upperCase = /[A-Z]/.test(password.value);
  const numbers = /[0-9]/.test(password.value);
  return lowerCase && upperCase && numbers;
});
function goComplete () {
  router.push('/register/complete')
}
</script>
<template>
  <div class="container">
    <div class="header">
      <p @click="router.push('/main')" class="title2 white">&lt;</p><p class="title2 white">회원정보 수정</p>
    </div>
    <div class="content">
      <form class="form">
        <label for="email">이메일(필수)</label>
        <input class="block-input" type="email" id="email" placeholder="이메일" required readonly>
        
        <label class="caption1" for="password">비밀번호(필수)</label>
        <input type="password" id="password" placeholder="비밀번호" required>

        <p class="caption1">비밀번호는 다음과 같은 조건을 만족해야 합니다</p>
        <div class="pwd-check1">
          <p class="caption2">비밀번호는 공백없이 10자리 이상 30자 이하여야 합니다</p>
        </div>
        <div class="pwd-check2">
          <p class="caption2">영어 대문자, 영어 소문자, 특수문자를 최소 한 글자 이상 포함한 문자 조합이어야 합니다.</p>
        </div>
        
        <label class="caption1" for="passwordConfirm">비밀번호 확인(필수)</label>
        <input type="password" id="passwordConfirm" placeholder="비밀번호 확인" required>
        
        <label class="caption1" for="name">이름(필수)</label>
        <input type="text" id="name" placeholder="이름" required>
        
        <label class="caption1" for="phoneNumber">전화번호(필수)</label>
        <input class="block-input" type="tel" id="phoneNumber" placeholder="전화번호" required readonly>
        
        <div class="button-container">
          <button class="button" type="submit" @click="goComplete">회원정보 수정하기</button>
        </div>
      </form>
    </div>
  </div>
</template>
<style scoped>
.container {
  display: flex;
  flex-direction: column;
}

.header {
  display: flex;
  flex-direction: row;
}

.content {
  background-color: #ffffff;
  border-radius: 20px 20px 0 0; /* 아래쪽 모서리는 둥글지 않음 */
  width: 370px; /* 가로 크기 유지 */
  padding: 20px;
  margin: 0 auto;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  /* text-align: center; */
  position: fixed; /* 화면에 고정 */
  top: 70px; /* 상단으로부터 130px 떨어진 위치에 배치 */
  left: 50%; /* 가운데 정렬을 위해 */
  transform: translateX(-50%); /* 가운데 정렬을 위해 */
  height: calc(100vh - 50px); 
  overflow: auto; 
  /* 나머지 스타일은 기존에 설정한 대로 유지 */
}
.form {
  display: flex;
  flex-direction: column;
  padding-bottom: 100px;
}

.form label {
  margin-top: 10px;
}

.form input {
  padding: 10px;
  margin-top: 5px;
  border: 1px solid #ccc;
  border-radius: 10px;
}

.button-container {
  display: flex;
  flex-direction: column; /* 버튼을 수직으로 쌓음 */
  align-items: center; /* 버튼을 가로축 중앙에 배치 */
  margin-top: auto; /* 컨테이너 상단의 여백을 자동으로 설정하여 내용을 위로 밀어냄 */
  margin-bottom: 20px; /* 하단 여백도 자동으로 설정 */
}

.button {
  margin-top: 50px;
  width: 327px;
  height: 45px; 
  font-size: 16px;
  font-weight: 500;
  background-color: #1B3C62;
  color: #ffffff;
  border-radius: 10px;
}

.checkmark {
  color: green;
  margin-right: 5px;
  margin-top: 5px;
}
.pwd-check1 {
  display: flex;
  flex-direction: row;
}

.white {
  color: #ffffff;
  margin-left: 15px;
}
</style>