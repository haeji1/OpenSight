<script setup>
import { useRouter } from 'vue-router';
import {useAccountStore} from "@/stores/account.js";

const router = useRouter()
const accountStore = useAccountStore();

</script>

<template>
  <div class="container">
    <div class="content">
      <div class="check-mark">
        <img class="check-img" src="../../assets/img/check-mark.png" alt="회원가입이 완료되었다는 이미지">
      </div>
      <div class="title">
        <p class="title1">이체가 완료되었습니다</p>
        <div class="transfer-content">
          <div class="content-text">
            <p class="caption1">받은 계좌</p><p class="caption1">{{accountStore.recipientName}} {{ accountStore.accountNumber }}</p>
          </div>
          <div class="content-text">
            <p class="caption1">이체 금액</p><p class="caption1">{{ accountStore.amount }}</p>
          </div>
          <div class="content-text">
            <p class="caption1">거래 일자</p><p class="caption1">{{ accountStore.transactionDate }}</p>
          </div>
          <div class="content-text">
            <p class="caption1">출금 계좌</p><p class="caption1">{{ accountStore.selectedBankName }} {{accountStore.accountNumber}}</p>
          </div>
        </div>
        <div>
          <button class="button" @click="router.push('/main')">메인 화면으로 돌아가기</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  background-color: #1B3C62;
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
  top: 60px; /* 상단으로부터 130px 떨어진 위치에 배치 */
  left: 50%; /* 가운데 정렬을 위해 */
  transform: translateX(-50%); /* 가운데 정렬을 위해 */
  height: calc(100vh - 50px); /* 상단부터 130px 위치부터 하단까지의 높이 설정 */
}

.check-img {
  margin-top: 30px;
  width: 150px;
  height: 150px;
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

.content-text {
  display: flex;
  flex-direction: row; /* 가로로 배열 */
  justify-content: space-between; /* 양 끝으로 정렬 */
  width: 100%; /* 전체 너비 사용 */
  padding: 5px;
} 

.caption1 {
  margin: 10px 20px;
}

.button {
  margin-top: 70px;
  width: 327px;
  height: 45px; 
  font-size: 16px;
  font-weight: 500;
  background-color: #1B3C62;
  color: #ffffff;
  border-radius: 10px;
}
</style>