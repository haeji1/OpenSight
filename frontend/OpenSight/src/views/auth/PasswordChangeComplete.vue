<script setup>
import router from '@/router';
</script>
<template>
  <div class="container">
    <div class="content">
      <div class="check-mark">
        <img class="check-img" src="../../assets/img/check-mark.png" alt="회원가입이 완료되었다는 이미지">
      </div>
      <div class="title">
        <p>비밀번호가 새롭게 설정되었습니다</p>
        <p>새롭게 설정한 비밀번호로 로그인 해주세요.</p>
      </div>
      <div>
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
}
.content {
  background-color: #ffffff;
}
.check-img {
  width: 150px;
  height: 150px;
}
</style>