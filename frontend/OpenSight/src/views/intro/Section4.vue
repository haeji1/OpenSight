<script setup>

</script>

<template>
  <div class="container">
    <div class="img-section">
      <div class="left-section">
        <div class="intro-ment-left">
          <p class="body1">음성을 통한</p>
          <p class="body1">쉬운 업무 처리</p>
        </div>
        <img class="img" src="@/assets/img/intro-4.png" alt="전맹시각장애인이 보는 화면 이미지">
      </div>
      <div class="right-section">
        <img class="img" src="@/assets/img/intro-5.png" alt="저시력 시각장애인이 보는 화면 이미지">
        <div class="intro-ment-right">
          <p class="body1">비밀번호 입력 없이</p>
          <p class="body1">얼굴인식을 통한</p>
          <p class="body1">간편한 인증</p>
        </div>
      </div>
    </div>
    <div class="title-bottom">
      <p class="title1">편리한 기능을</p>
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

.img {
  width: 170px;
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
  padding: 10px;
}

.right-section {
  flex: 1; /* 부모 컨테이너에 대해 1의 flex 비율을 가짐 */
  flex-direction: column; /* 자식 요소들을 세로로 배열 */
  display: flex;
  justify-content: center; /* 가운데 정렬 */
  padding: 10px;
}

.intro-ment-left {
  margin: 30px 0px;
  text-align: right;
}

.intro-ment-right {
  margin: 30px 0px;
  text-align: left;
}

.title1, .body1 {
  color: #ffffff;
  margin: 0;
}

.title-bottom {
  margin-bottom: 150px;
}
</style>