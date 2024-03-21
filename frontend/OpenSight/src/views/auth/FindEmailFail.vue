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
        <p>입력하신 전화번호로</p>
        <p>가입된 회원정보를 찾을 수 없습니다</p>
        <p>맞는 전화번호인지</p>
        <p>확인 후 다시 시도해주세요</p>
      </div>
      <div class="route-button">
        <button @click="router.push('/find-email')">전화번호 다시 입력하기</button>
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