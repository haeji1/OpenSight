<script setup>
import { useRouter } from 'vue-router';
const router = useRouter()


</script>

<template>
  <div>
    
    <button @click="router.push('/account/open/select-bank')">비대면계좌 개설하기</button>
  </div>
</template>