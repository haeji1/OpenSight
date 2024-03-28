<script setup>

</script>

<template>
  <div class="container">
    <div class="header">
      <p class="title1">이체 확인</p>
    </div>
    <div class="main-text">
        <p class="title2">김싸피님께 10,000원을</p>
        <p class="title2">이체하시겠습니까?</p>
      </div>
    <div class="content">
      <div class="transfer-content">
        <div class="content-text">
          <p class="caption1">받은 계좌</p><p class="caption1">싸피 987654-32-109876</p>
        </div>
        <div class="content-text">
         <p class="caption1">이체 금액</p><p class="caption1">10,000원</p>
        </div>
        <div class="content-text">
          <p class="caption1">출금 계좌</p><p class="caption1">싸피 123456-78-901234</p>
        </div>
        <div class="content-text">
          <p class="caption1">받는계좌에 표시</p><p class="caption1">김싸피</p>
        </div>
        <div class="content-text">
          <p class="caption1">내계좌에 표시</p><p class="caption1">이싸피</p>
        </div>
      </div>
      <div class="button-group">
          <button class="button-cancel">취소</button>
          <button class="button-confirm">얼굴인식확인</button>
          <button class="button-confirm">비밀번호확인</button>
      </div>
    </div>
  </div>

</template>

<style scoped>
.container {
  background-color: #ffffff;
}
.header {
  display: flex;
  justify-content: left;
  text-align: left;
  margin-left: 30px;
}
.content {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.main-text {
  display: flex;
  flex-direction: column;
  text-align: left;
  justify-content: left;
  margin-left: 30px;
  margin-top: 50px;
}

.content-text {
  display: flex;
  flex-direction: row; /* 가로로 배열 */
  justify-content: space-between; /* 양 끝으로 정렬 */
  width: 100%; /* 전체 너비 사용 */
  padding: 5px;
} 

.button-group {
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
  width: 100%;
  margin-top: 100px;
}

.button-cancel {
  margin-top: 50px;
  width: 120px;
  height: 45px; 
  font-size: 16px;
  font-weight: 500;
  background-color: #e0e0e0;
  color: #000000;
  border-radius: 10px;
  border: none;
}

.button-confirm {
  margin-top: 50px;
  width: 120px;
  height: 45px; 
  font-size: 16px;
  font-weight: 500;
  background-color: #1B3C62;
  color: #ffffff;
  border: none;
  border-radius: 10px;
}

.transfer-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
  align-items: center;
  border: 1px solid #EDEDED;
  border-radius: 15px;
  background-color: #ffffff;
  box-shadow: 0px 4px 30px rgba(54, 41, 183, 0.07);
  width: 360px;
  margin-top: 70px;
}

.title2 {
  margin: 0
}

.caption1 {
  margin: 10px 20px;
}

</style>