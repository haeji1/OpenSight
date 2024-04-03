<script setup>
import router from '@/router';

import { useAccountStore } from '@/stores/account';

const AccountStore = useAccountStore();

const bankName = computed(() => {
  const code = AccountStore.openAccountResult.bankCode;
  const bankMap = {
    '001': '한국은행',
    '002': '산업은행',
    '003': '기업은행',
    '004': '국민은행',
  }
  return bankMap[code];
})


</script>

<template>
  <div class="container">
    <div class="content">
      <div class="check-mark">
        <img class="check-img" src="../../assets/img/check-mark.png" alt="회원가입이 완료되었다는 이미지">
      </div>
      <div class="title">
        <p class="title2">비대면 계좌가 개설되었습니다.</p>
      </div>
      <div class="content-box">
        <div class="bank-name">
          <p class="title3">은행명</p>
          <p class="title3">{{bankName}}</p>
        </div>
        <div class="bank-number">
          <p class="title3">계좌번호</p>
          <p class="title3">{{AccountStore.openAccountResult.accountNo}}</p>
        </div>
        
      </div>
      <div>
        <button class="button" @click="router.push('/main')">메인 화면으로 돌아가기</button>
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
  top: 100px; /* 상단으로부터 130px 떨어진 위치에 배치 */
  left: 50%; /* 가운데 정렬을 위해 */
  transform: translateX(-50%); /* 가운데 정렬을 위해 */
  height: calc(100vh - 50px); /* 상단부터 130px 위치부터 하단까지의 높이 설정 */
  /* overflow: auto;  */
  /* 나머지 스타일은 기존에 설정한 대로 유지 */
}

.content-box {
  border: 1px solid #EDEDED;
  border-radius: 15px;
  background-color: #ffffff;
  box-shadow: 0px 4px 30px rgba(54, 41, 183, 0.07);
}

.bank-name, .bank-number {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin: 0px 30px;
}

.check-img {
  margin-top: 80px;
  width: 150px;
  height: 150px;
}

.button {
  margin-top: 80px;
  width: 327px;
  height: 45px; 
  font-size: 16px;
  font-weight: 500;
  background-color: #1B3C62;
  color: #ffffff;
  border-radius: 10px;
}
</style>