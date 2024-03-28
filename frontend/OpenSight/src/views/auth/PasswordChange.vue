<script setup>
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
import { ref } from 'vue';

const newPassword = ref("");

const store = useAuthStore();

const updatePassword = function() {
  console.log("=======시작=========")
  store.updatePassword(newPassword.value);
}
</script>

<template>
<div class="container">
<<<<<<< HEAD
  <div class="header">
    <p class="title2">&lt;</p><p class="title2">비밀번호 찾기</p>
=======
    
    <div class="content">
      <div>&lt; 비밀번호 찾기</div>
      <form class="form" @submit.prevent="updatePassword">

        <label for="password">새로운 비밀번호를 입력하세요</label>
        <input type="password" id="password" placeholder="비밀번호" required>
        <p>비밀번호는 다음과 같은 조건을 만족해야 합니다</p>
        <div class="pwd-check1">
          <span v-if="isLengthValid" class="checkmark">✔</span>
          <span v-else>✔</span>
          <p>비밀번호는 공백없이 10자리 이상 30자 이하여야 합니다</p>
        </div>
        <div class="pwd-check2">
          <span v-if="hasAllRequiredCharacters" class="checkmark">✔</span>
          <span v-else>✔</span>
          <p>영어 대문자, 영어 소문자, 특수문자를 최소 한 글자 이상 포함한 문자 조합이어야 합니다.</p>
        </div>
        <label for="passwordConfirm">비밀번호를 확인해주세요</label>
        <input type="password" id="passwordConfirm" v-model="newPassword" placeholder="비밀번호 확인" required>
        <button type="submit" >비밀번호 변경</button>
      </form>
    </div>
>>>>>>> fad3ccaa1b703cf77565da2a9d9852e8308ee35b
  </div>
  <div class="content">
    <form class="form">
      <label for="password"><p class="caption1">새로운 비밀번호를 입력하세요</p></label>
      <input class="input" type="password" id="password" placeholder="비밀번호" >
      <p class="caption1">비밀번호는 다음과 같은 조건을 만족해야 합니다</p>
      <p class="caption2">비밀번호는 공백없이 10자리 이상 30자 이하여야 합니다</p>
      <p class="caption2">영어 대문자, 영어 소문자, 특수문자를 최소 한 글자 이상 포함한 문자 조합이어야 합니다.</p>
      <label for="passwordConfirm"><p class="caption1">비밀번호를 확인해주세요</p></label>
      <input class="input" type="password" id="passwordConfirm" placeholder="비밀번호 확인" >
    </form>
    <button class="button" type="submit" @click="goComplete">비밀번호 변경</button>
  </div>
</div>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  background-color: #ffffff;
}
.header {
  display: flex;
  flex-direction: row;
  padding-left: 30px;
  justify-content: left;
  text-align: left;
}
.content {
  padding: 20px;
  background-color: #ffffff;
  border-radius: 10px 10px 0 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.form {
  display: flex;
  flex-direction: column;
  justify-content: left;
  text-align: left;
}
.form label {
  margin-top: 20px;
}
.input {
  width: 340px;
  height: 30px;
  padding: 10px;
  margin-bottom: 5px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
}
.button {
  margin-top: 200px;
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
}
.pwd-check1 {
  flex-direction: row;
}
.title2 {
  margin-right: 10px;
}

.caption2 {
  margin: 0;
}
.caption1 {
  margin-top: 5px;
  margin-bottom: 15px;
}
</style>