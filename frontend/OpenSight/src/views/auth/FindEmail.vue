<script setup>
import { useRouter } from 'vue-router';
import { ref } from 'vue';
import { useAuthStore } from '@/stores/auth';

const authStore = useAuthStore()
const router = useRouter()
const phonenumber = ref(null)

const findEmail = function () {
  const payload = {
    phonenumber: phonenumber.value
  }
  authStore.findEmail(payload)
}
</script>

<template>
  <div class="container">
    <div class="header">
      <p class="title2" @click="router.push('/login')">&lt;</p><p class="title2">이메일 찾기</p>
    </div>
    <p class="caption1">회원가입 시 입력한 전화번호를 입력해주세요</p>
    <div class="verification-container">
      
      <div class="input-group">
        <input
          class="input"
          type="text"
          placeholder="전화번호"
          v-model="phonenumber"
        />
      </div>
      <button class="button" @click="findEmail">이메일 찾기</button>
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
  padding-left: 30px;
  justify-content: left;
  text-align: left;
}


.verification-container {
  justify-content: center;
  align-items: center;
  text-align: center;
}

.input-group {
  margin: 20px 0;
  text-align: center;
}

.input {
  width: 340px;
  height: 30px;
  padding: 10px;
  margin-bottom: 5px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
}

.button {
  margin-top: 400px;
  width: 327px;
  height: 45px; 
  font-size: 16px;
  font-weight: 500;
  background-color: #1B3C62;
  color: #ffffff;
  border-radius: 10px;
}

.title2 {
  margin-right: 10px;
}

.caption1 {
  margin-left: 30px;
  margin-top: 80px;
}
</style>