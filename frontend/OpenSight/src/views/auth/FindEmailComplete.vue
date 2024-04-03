<script setup>
import router from '@/router';

</script>
<template>
  <div class="container">
    <div class="content">
      <div class="check-mark">
        <img class="check-img" src="../../assets/img/check-mark.png" alt="이메일을 찾았다는 이미지">
      </div>
      <div class="title">
        <p class="title2">이메일 정보는 다음과 같습니다</p>
        <p class="title1">email</p>
        <div class="body-message">
          <p class="body2">이메일을 찾았습니다</p>
          <p class="body2">로그인을 다시 해주세요</p>
        </div>
      </div>
      <div>
        <button class="button" @click="router.push('/login')">로그인 화면으로 돌아가기</button>
      </div>
    </div>
  </div>
</template>
<style scoped>
.container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
  background-color: #ffffff;
}
.content {
  background-color: #ffffff;
}
.check-img {
  width: 150px;
  height: 150px;
}

.body2 {
  margin: 0;
}

.body-message {
  margin-bottom: 80px;
}

.button {
  margin-top: 100px;
  width: 327px;
  height: 45px; 
  font-size: 16px;
  font-weight: 500;
  background-color: #1B3C62;
  color: #ffffff;
  border-radius: 10px;
}
</style>