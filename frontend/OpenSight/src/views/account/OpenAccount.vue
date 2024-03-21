<script setup>
import { useRouter } from 'vue-router';

const router = useRouter()
</script>

<template>
  <div>
    <p>&lt; 비대면계좌 개설</p>
    <button @click="router.push('/account/open/terms')">한국은행</button>
    <button @click="router.push('/account/open/terms')">산업은행</button>
    <button @click="router.push('/account/open/terms')">기업은행</button>
    <button @click="router.push('/account/open/terms')">국민은행</button>
  </div>
</template>

<style scoped>

</style>