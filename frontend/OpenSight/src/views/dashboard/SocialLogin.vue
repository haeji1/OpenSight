<script setup>
import { useRouter} from "vue-router";
const router  = useRouter()

// URL에서 쿼리 스트링 파싱
const queryParams = new URLSearchParams(window.location.search);

// 필요한 값(예: accessToken) 추출
const accessToken = queryParams.get('accessToken');

if (accessToken) {
  sessionStorage.setItem('accessToken', accessToken);
}

</script>

<template>
  <div class="container">
    <p>환영합니다</p>
    <button class="button" @click="router.push('/main')">시작하기</button>
  </div>
</template>

<style scoped>

</style>