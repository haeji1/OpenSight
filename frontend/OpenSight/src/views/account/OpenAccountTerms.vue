<script setup>
import { useRouter } from 'vue-router';

const router = useRouter()
</script>

<template>
  <header>
    <p>&lt; 비대면계좌 개설</p>
  </header>
  <div>
    <div>
      <p>이용약관</p>
    </div>
    <div>
      <button @click="router.push('/main')">취소</button>
      <button @click="router.push('/account/open/complete')">계좌 개설</button>
    </div>
  </div>
</template>

<style scoped>

</style>