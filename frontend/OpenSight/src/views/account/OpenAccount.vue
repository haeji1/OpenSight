<script setup>
import { useRouter } from 'vue-router';

const router = useRouter()
</script>

<template>
  <div class="container">
    <div class="header">
      <p class="title2" @click="router.push('/main')">&lt;</p><p class="title2">비대면계좌 개설</p>
    </div>
    <div class="select-bank">
      <button class="button title2" >한국은행</button>
      <button class="button title2" >산업은행</button>
      <button class="button title2" >기업은행</button>
      <button class="button title2" >국민은행</button>
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
}

.title2 {
  margin: 30px 10px;
}

.select-bank {
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
  align-items: center;
  margin-top: 50px;
}

.button {
  border: 1px solid #EDEDED;
  border-radius: 15px;
  background-color: #ffffff;
  box-shadow: 0px 4px 30px rgba(54, 41, 183, 0.07);
  width: 300px;
  height: 60px;
}
</style>