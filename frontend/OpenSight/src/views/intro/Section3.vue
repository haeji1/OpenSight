<script setup>

</script>

<template>
  <div class="container">
    <img class="img" src="@/assets/img/intro-3.png" alt="쉬운 UI를 설명하는 이미지">
    <p class="title1">쉬운 UI와</p>
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

.img {
  height: 70vh;
  display: flex;
  margin-top: 50px;
}

.title1 {
  color: #ffffff;
  margin-bottom: 100px;
}
</style>