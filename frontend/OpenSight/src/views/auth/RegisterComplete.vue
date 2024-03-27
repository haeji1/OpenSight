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
        <p class="title2">회원가입이 완료되었습니다</p>
        <p class="body3">OpenSight의 회원이 되신 것을 환영합니다!</p>
        <p class="body3">여기에 사이트를 소개할 수 있는 설명 작성하기</p>
      </div>
      <div>
        <button class="button" @click="router.push('/login')">OpenSight 시작하기</button>
      </div>
    </div>
  </div>
</template>

<style scoped>

.container {
  /* display: flex; */
  align-items: flex-start;
  justify-content: center;
  overflow: auto;
  top: 0;
  left: 0;
  position: fixed;
  width: 100vw;
  height: 100vh;
  background: #1B3C62;
  font-family: 'Poppins', sans-serif;
}

.content {
  background-color: #ffffff;
  border-radius: 20px 20px 0 0; /* 아래쪽 모서리는 둥글지 않음 */
  width: 360px; /* 가로 크기 유지 */
  padding: 20px;
  margin: 0 auto;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
  position: fixed; /* 화면에 고정 */
  top: 100px; /* 상단으로부터 130px 떨어진 위치에 배치 */
  left: 50%; /* 가운데 정렬을 위해 */
  transform: translateX(-50%); /* 가운데 정렬을 위해 */
  height: calc(100vh - 50px); /* 상단부터 130px 위치부터 하단까지의 높이 설정 */
  /* overflow: auto;  */
  /* 나머지 스타일은 기존에 설정한 대로 유지 */
}

.check-img {
  width: 150px;
  height: 150px;
  margin: 50px 0px;
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