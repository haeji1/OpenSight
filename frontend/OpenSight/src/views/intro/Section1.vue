<script setup>
import { useRouter } from 'vue-router';

const router = useRouter()

</script>

<template>
  <div class="container">
    <p class="title1">모두를 위한 은행 서비스</p>
    <p class="title1">OpenSight</p>
    <button class="button" @click="router.push('/login')">시작하기</button>
  </div>
</template>

<style scoped>
/* 컨테이너 스타일링 */
.container {
  display: flex; /* Flexbox 레이아웃 사용 */
  flex-direction: column; /* 자식 요소들을 세로로 배열 */
  justify-content: center; /* 중앙 정렬 (세로 방향) */
  align-items: center; /* 중앙 정렬 (가로 방향) */
  height: 100vh; /* 전체 뷰포트 높이 */
  text-align: center; /* 텍스트 중앙 정렬 */
}

/* 버튼 스타일링 */
.button {
  padding: 10px 20px; /* 버튼 안쪽 여백 */
  margin-top: 20px; /* 버튼 상단 여백 */
  border: none; /* 테두리 없음 */
  background-color: #ffffff; /* 배경 색상 */
  color: #1b3c62; /* 글자 색상 */
  font-size: 16px;
  font-weight: 600;
  cursor: pointer; /* 마우스 오버 시 커서 변경 */
  border-radius: 5px; /* 모서리 둥글게 */
}

/* .button:hover {
  background-color: #0056b3;
} */

.title1 {
  font-size: 24px;
  font-weight: 600;
  color: #ffffff;
  margin: 0;
}

</style>