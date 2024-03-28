<script setup>
import { useRouter } from 'vue-router';

const router = useRouter()
</script>

<template>
  <div class="footer">
    <button class="menu-button" @click="router.push('/transaction')">조회</button>
    <button class="home-button" @click="router.push('/main')">메인</button>
    <button class="profile-button" @click="router.push('/profile/edit')">내정보수정</button>
  </div>
</template>

<style scoped>
.footer {
  display: flex;
  justify-content: space-around;
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  background-color: #ffffff;
  box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.1);
  padding: 10px 0;
}

.menu-button, .home-button, .profile-button {
  flex: 1;
  text-align: center;
  padding: 10px;
  border-radius: 5px;
  background-color: #1b3c62;
  color: #ffffff;
  border: none;
  font-size: 16px;
}


</style>