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
        <p>회원가입이 완료되었습니다</p>
        <p>OpenSight의 회원이 되신 것을 환영합니다!</p>
        <p>여기에 사이트를 소개할 수 있는 설명 작성하기</p>
      </div>
      <div>
        <button @click="router.push('/login')">OpenSight 시작하기</button>
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