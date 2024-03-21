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
        <p>비대면 계좌가 개설되었습니다.</p>
      </div>
      <div class="content">
        <p>은행명</p>
        <p>한국은행</p>
        <p>계좌번호</p>
        <p>00151029380298</p>
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
  border: 1px solid #ccc;
  border-radius: 10px;
}

.check-img {
  width: 150px;
  height: 150px;
}
</style>