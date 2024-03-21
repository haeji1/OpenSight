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
        <p>회원정보를 수정했습니다</p>
        <p>회원정보 수정이 완료되었습니다</p>
      </div>
      <div>
        <button @click="router.push('/main')">메인 화면으로 돌아가기</button>
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