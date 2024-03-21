<script setup>

</script>

<template>
  <div class="container">
    <div class="img-section">
      <div class="left-section">
        <p>음성을 통한</p>
        <p>쉬운 업무 처리</p>
        <!-- <img src="@/assets/img/intro-1.png" alt="전맹시각장애인이 보는 화면 이미지"> -->
      </div>
      <div class="right-section">
        <!-- <img src="@/assets/img/intro-2.png" alt="저시력 시각장애인이 보는 화면 이미지"> -->
        <p>비밀번호 입력 없이</p>
        <p>얼굴인식을 통한</p>
        <p>간편한 인증</p>
      </div>
    </div>
    <div class="title-bottom">
      <p>편리한 기능을</p>
    </div>   
  </div>
</template>

<style scoped>
.container {
  display: flex; /* Flexbox 레이아웃 사용 */
  flex-direction: column; /* 자식 요소들을 세로로 배열 */
  justify-content: center; /* 중앙 정렬 (세로 방향) */
  align-items: center; /* 중앙 정렬 (가로 방향) */
  height: 100vh; /* 전체 뷰포트 높이 */
  text-align: center; /* 텍스트 중앙 정렬 */
}

.img-section {
  display: flex;
  height: 100vh; /* 전체 화면 높이를 채우도록 설정 */
}

.left-section {
  flex: 1; /* 부모 컨테이너에 대해 1의 flex 비율을 가짐 */
  flex-direction: column; /* 자식 요소들을 세로로 배열 */
  display: flex;
  justify-content: center; /* 가운데 정렬 */
  align-items: center; /* 세로 중앙 정렬 */
  padding: 10px;
}

.right-section {
  flex: 1; /* 부모 컨테이너에 대해 1의 flex 비율을 가짐 */
  flex-direction: column; /* 자식 요소들을 세로로 배열 */
  display: flex;
  justify-content: center; /* 가운데 정렬 */
  align-items: center; /* 세로 중앙 정렬 */
  padding: 10px;
}
</style>