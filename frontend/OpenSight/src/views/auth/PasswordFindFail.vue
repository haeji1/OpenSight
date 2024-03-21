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
        <p>입력하신 전화번호와 이메일로</p>
        <p>가입된 회원정보를 찾을 수 없습니다</p>
        <p>맞는 정보인지</p>
        <p>확인 후 다시 시도해주세요</p>
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
}
.content {
  background-color: #ffffff;
}
.caution-img {
  width: 150px;
  height: 150px;
}
</style>