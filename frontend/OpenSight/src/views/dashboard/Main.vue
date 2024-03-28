<script setup>
import footer from '@/components/layout/Footer.vue'
import { useRouter } from 'vue-router';
const router = useRouter()


</script>

<template>
  <div class="container">
    <div class="header">
      <p class="title1-white">안녕하세요, 김싸피 고객님.</p>
      <img class="img" src="../../assets/img/signout.png" alt="로그아웃">
    </div>
    <div class="content">
      <div class="account-container">
        <div class="account">
          <div class="account-content">
            <p class="title1 title1-account">은행명</p>
            <p class="body3 body3-account">통장이름</p>
            <p class="body2 body2-account">계좌번호</p>
            <p class="title2 title2-account">잔액</p>
          </div>
        </div>
        <button class="button">이체하기</button>
        <button class="button">거래내역 조회하기</button>
      </div>
      <div class="account-open">
        <div class="account-text">
          <p class="title2">비대면 자유입출금</p>
          <p class="title2">계좌 개설</p>
        </div>
        <img class="content-img" src="../../assets/img/accountimage.png" alt="">
      </div>
      <div class="savings">
        <p class="title2">예적금 상품추천</p>
        <img class="content-img" src="../../assets/img/savings.png" alt="">
      </div>
    </div>
  </div>
  
</template>

<style scoped>
.container {
  /* display: flex; */
  align-items: flex-start;
  justify-content: center;
  overflow: auto;
  top: 0;
  left: 0;
  position: fixed;
  width: 100vw;
  height: 100vh;
  background: #1B3C62;
  font-family: 'Poppins', sans-serif;
}

.content {
  background-color: #ffffff;
  border-radius: 20px 20px 0 0; /* 아래쪽 모서리는 둥글지 않음 */
  width: 370px; /* 가로 크기 유지 */
  padding: 20px;
  margin: 0 auto;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
  position: fixed; /* 화면에 고정 */
  top: 80px; /* 상단으로부터 130px 떨어진 위치에 배치 */
  left: 50%; /* 가운데 정렬을 위해 */
  transform: translateX(-50%); /* 가운데 정렬을 위해 */
  height: calc(100vh - 50px); /* 상단부터 130px 위치부터 하단까지의 높이 설정 */
  overflow: scroll; 
  /* 나머지 스타일은 기존에 설정한 대로 유지 */
}

.header {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

.title1-white {
  font-size: 24px;
  font-weight: 600;
  color: #ffffff;
  margin-left: 20px;
}

.img {
  margin-top: 30px;
  margin-right: 30px;
  width: 24px;
  height: 24px;
}

.account-open {
  display: flex;
  flex-direction: row;
  justify-content: space-between;  
  text-align: center;
  align-items: center;
  border: 1px solid #EDEDED;
  border-radius: 15px;
  background-color: #ffffff;
  box-shadow: 0px 4px 30px rgba(54, 41, 183, 0.07);
  width: 270px;
  height: 80px;
  padding: 0px 50px;
  margin-bottom: 30px;
  margin-top: 30px;
}

.savings {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;  
  border: 1px solid #EDEDED;
  border-radius: 15px;
  background-color: #ffffff;
  box-shadow: 0px 4px 30px rgba(54, 41, 183, 0.07);
  width: 270px;
  height: 80px;
  padding: 0px 50px;
}

.account-text {
  display: flex;
  flex-direction: column;
  text-align: right;
}

.content-img {
  width: 50px;
  height: 50px;
}

.title2 {
  margin: 0;
}

.account {
  background-image: url('src/assets/img/account.png');
  background-position: center;
  width: 360px;
  height: 250px;
  border-radius: 15px;
}

.account-content {
  display: flex;
  flex-direction: column;
  justify-content: left;
  text-align: left;
  margin-top: 10px;
  margin-left: 50px;
}

.title1-account, .title2-account, .body2-account, .body3-account {
  color: #ffffff;
  margin: 5px 0px;
}
.title1-account {
  margin-top: 50px;
}
.button {
  margin-bottom: 20px;
  width: 300px;
  height: 45px; 
  font-size: 16px;
  font-weight: 500;
  background-color: #1B3C62;
  color: #ffffff;
  border-radius: 10px;
}

.account-container {
  border: 1px solid #EDEDED;
  border-radius: 15px;
  background-color: #ffffff;
  box-shadow: 0px 4px 30px rgba(54, 41, 183, 0.07);
}
</style>