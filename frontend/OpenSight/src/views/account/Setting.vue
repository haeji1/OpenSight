<script setup>
import { useRouter } from 'vue-router';

const router = useRouter()

</script>

<template>
  <div class="container">
    <div class="header">
      <p class="title2" @click="router.push('/main')">&lt;</p><p class="title2">설정</p>
    </div>
    <div class="content">
      <button class="button" @click="router.push('/account/setting')">주거래계좌 설정</button>
      <button class="button" @click="router.push('/password/setting')">간편비밀번호 설정</button>
    </div>
  </div>
</template>

<style scoped>
.container {
  background-color: #ffffff;
}

.header {
  display: flex;
  flex-direction: row;
  margin-left: 20px;
}

.content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
}

.button {
  margin-top: 100px;
  width: 327px;
  height: 50px; 
  font-size: 16px;
  font-weight: 500;
  background-color: #1B3C62;
  color: #ffffff;
  border-radius: 10px;
}
</style>