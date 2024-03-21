<script setup>

</script>

<template>
<div class="container">
    
    <div class="content">
      <div>&lt; 비밀번호 찾기</div>
      <form class="form">
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
        <input type="password" id="passwordConfirm" placeholder="비밀번호 확인" required>
        <button type="submit" @click="goComplete">비밀번호 변경</button>
      </form>
    </div>
  </div>
</template>

<style scoped>
container {
  font-family: 'Arial', sans-serif;
  max-width: 360px;
  margin: 0 auto;
  overflow: auto;
  display: flex;
  flex-direction: column;
}
.content {
  padding: 20px;
  background-color: #ffffff;
  border-radius: 10px 10px 0 0;
}
.form {
  display: flex;
  flex-direction: column;
}
.form label {
  margin-top: 20px;
}
.form input {
  padding: 10px;
  margin-top: 5px;
  border: 1px solid #ccc;
  border-radius: 10px;
}
.form small {
  color: #888;
  margin-top: 5px;
}
.form ul {
  color: #555;
  font-size: 0.9em;
  margin-top: 5px;
  padding-left: 20px;
}
.form button {
  background-color: #007aff;
  color: white;
  padding: 10px;
  margin-top: 30px;
  border: none;
  border-radius: 10px;
  font-size: 1em;
}
.back-button {
  font-size: 1em;
  color: #007aff;
  margin-bottom: 30px;
  cursor: pointer;
}
.checkmark {
  color: green;
  margin-right: 5px;
}
.pwd-check1 {
  flex-direction: row;
}
</style>