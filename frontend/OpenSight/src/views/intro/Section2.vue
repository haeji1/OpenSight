<script setup>
</script>

<template>
  <div class="container">
    <div class="title-top">
      <p class="title1">은행 서비스 사용하는데</p>
      <p class="title1">그동안 불편한 점은 없었나요?</p>
    </div>
    <div class="title-middle">
      <p class="title3">하지만 이러한 화면으로</p>
      <p class="title3">보시는 분들도 있습니다.</p>
    </div>
    <div class="img-section">
      <div class="left-section">
        <img class="img" src="@/assets/img/intro-1.png" alt="전맹시각장애인이 보는 화면 이미지">
        <div class="intro-ment-left">
          <p class="body3">저시력 시각장애인이</p>
          <p class="body3">보는 스마트폰 화면</p>
        </div>
      </div>
      <div class="right-section">
        <div class="intro-ment-right">
          <p class="body3">전맹 시각장애인이</p>
          <p class="body3">보는 스마트폰 화면</p>
        </div>
        <img class="img" src="@/assets/img/intro-2.png" alt="저시력 시각장애인이 보는 화면 이미지">
      </div>
    </div>
    <div class="title-bottom">
      <p class="title1">이러한 불편함을</p>
    </div>   
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

.img {
  width: 150px;
}

.intro-ment-left {
  margin: 30px 0px;
  text-align: right;
}

.intro-ment-right {
  margin: 30px 0px;
  text-align: left;
}


.title1, .title2, .title3, .body3 {
  color: #ffffff;
  margin: 0;
}

.title-top {
  margin-top: 30px;
}

.title-middle {
  margin-top: 40px;
}

.title-bottom {
  margin-bottom: 110px;
}
</style>