<script setup>
import router from '@/router';
</script>
<template>
  <div class="container">
    <div class="content">
      <div>
        <img class="caution-img" src="../../assets/img/caution.png" alt="">
      </div>
      <div>
        <div class="title-message">
          <p class="title2">입력하신 전화번호와 이메일로</p>
          <p class="title2">가입된 회원정보를 찾을 수 없습니다</p>
        </div>
        <div class="body-message">
          <p class="body2">회원정보와 일치하는지</p>
          <p class="body2">확인 후 다시 시도해주세요</p>
        </div>
      </div>
      <div class="route-button">
        <button @click="router.push('/find-password')">회원정보 다시 입력하기</button>
        <button @click="router.push('/login')">로그인 화면으로 돌아가기</button>
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
.caution-img {
  width: 150px;
  height: 150px;
}

.title2, .body2 {
  margin: 0;
  color: #1B3C62;
}

.title-message {
  margin-top: 50px;
}

.body-message {
  margin-top: 40px;
  margin-bottom: 60px;
}

.route-button {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.route-button button {
  margin: 15px;
  width: 327px;
  height: 45px; 
  font-size: 16px;
  font-weight: 500;
  background-color: #1B3C62;
  color: #ffffff;
  border-radius: 10px;
}
</style>