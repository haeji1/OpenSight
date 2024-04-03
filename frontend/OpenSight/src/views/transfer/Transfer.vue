<script setup>
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import {useAccountStore} from "@/stores/account.js";

const router = useRouter()
const accountStore = useAccountStore();
const isDataLoaded = ref(false);
const selectedBank = ref("");
const accountNumber = ref("");
const amount = ref("");
const recipientName = ref("");
const senderName = ref("");

const banks = ref([
  { bankCode: '001', bankName: '한국은행'},
  { bankCode: '002', bankName: '산업은행'},
  { bankCode: '003', bankName: '기업은행'},
  { bankCode: '004', bankName: '국민은행'},
])

onMounted (async () => {
  try {
    console.log("=======시작======")
    await accountStore.inquireAccountList();
    await accountStore.inquireBalance(accountStore.myAccountList[accountStore.currentIndex].bankCode, accountStore.myAccountList[accountStore.currentIndex].accountNo);
    isDataLoaded.value = true;
    //myAccountListCopy.value = accountStore.myAccountList.value;
    // myAccountList = accountStore.myAccountList;
    // console.log(myAccountList.value)

    console.log(accountStore.myAccountList)

  } catch (error) {
    console.log(error)
  }
});

function onClickNextButton() {
  accountStore.amount = amount.value;
  accountStore.selectedBank = selectedBank.value;
  accountStore.recipientName = recipientName.value;
  accountStore.senderName = senderName.value;
  accountStore.accountNumber = accountNumber.value;
  console.log("**************************"+accountStore.amount.value+"****************")

  if (selectedBank.value === "001")
    accountStore.selectedBankName = ref('한국은행');
  else if (selectedBank.value === "002")
    accountStore.selectedBankName = ref('산업은행');
  else if (selectedBank.value === "003")
    accountStore.selectedBankName = ref('기업은행');
  else if (selectedBank.value === "004")
    accountStore.selectedBankName = ref('국민은행');

  router.push({ name: "TransferConfirm" });
}

function test() {
  console.log(selectedBank.value)
  console.log(amount.value)
  console.log(recipientName.value)
  console.log(senderName.value)
  console.log(accountNumber.value)
}

</script>

<template>
  <div class="container">
    <div class="header">
      <p class="title2" @click="router.push('/main')">&lt;</p><p class="title2">이체</p>
    </div>
    <div class="content">
      <div class="account-info" v-if="isDataLoaded">
        <p class="body3">{{ accountStore.myAccountList[accountStore.currentIndex].accountName}}</p>
        <p class="title2">{{accountStore.myAccountList[accountStore.currentIndex].accountNo}}</p>
        <div class="current-balance">
          <p class="body3">잔액</p><p class="body1">{{accountStore.myAccountBalance}}원</p>
        </div>
        <div class="available-balance">
          <p class="body3">1일 출금한도</p><p class="body1">{{accountStore.myAccountList[accountStore.currentIndex].dailyTransferLimit}}원</p>
        </div>
      </div>

      <div class="transfer-form">
        <select class="select-bank" name="bank" id="bank" v-model="selectedBank">
          <option value="">은행선택</option>
          <option v-for="bank in banks" :key="bank.bankCode" :value="bank.bankCode">
            {{ bank.bankName }}
          </option>
        </select>
        
        <div class="input-text">
          <p class="caption1">계좌번호</p>
        </div>
        <input class="input" type="string" v-model="accountNumber"/>

        <div class="input-text">
          <p class="caption1">보낼금액</p>
        </div>
        <input class="input" type="number" v-model="amount" />

        <div class="input-text">
          <p class="caption1">받는 계좌에 표시</p>
        </div>
        <input class="input" type="text" placeholder="미입력시 보낸 분 이름 표시" v-model="recipientName"/>

        <div class="input-text">
          <p class="caption1">내 계좌에 표시</p>
        </div>
        <input class="input" type="text" placeholder="미입력시 받는 분 이름 표시" v-model="senderName"/>

        <div class="button-group">
          <button class="button-cancel" type="button" @click="router.push('/main')">취소</button>
          <button class="button-confirm" type="button" @click="onClickNextButton">다음</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

</script>

<style scoped>
.container {
  background-color: #ffffff;
}

.header {
  display: flex;
  flex-direction: row;
  margin-left: 20px;
}

.account-info {
  border: 1px solid #EDEDED;
  border-radius: 15px;
  background-color: #ffffff;
  box-shadow: 0px 4px 30px rgba(54, 41, 183, 0.07);
}

.current-balance, .available-balance {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

/* 여기에 추가적인 CSS 스타일을 정의하시면 됩니다 */
/* 예시 */
.account-info {
  margin: 10px;
  border: 1px solid #ccc;
  padding: 16px;
  margin-bottom: 16px;
}

.transfer-form {
  display: flex;
  flex-direction: column;

}

.input-text {
  display: flex;
  justify-content: left;
  align-items: center;
  margin-left: 30px;
}

.input {
  display: block;
  margin-left: auto;
  margin-right: auto;
  width: 340px;
  height: 30px;
  padding: 10px;
  margin-bottom: 5px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
}

.select-bank {
  display: block;
  margin-left: auto;
  margin-right: auto;
  width: 340px;
  height: 50px;
  padding: 10px;
  margin-bottom: 5px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
}



.title2 {
  margin-right: 10px;
}

.body1, .body3 {
  margin: 0;
}

.button-group {
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
}

.button-cancel {
  margin-top: 30px;
  width: 160px;
  height: 45px; 
  font-size: 16px;
  font-weight: 500;
  background-color: #e0e0e0;
  color: #000000;
  border-radius: 10px;
  border: none;
  margin-right: 10px;
}

.button-confirm {
  margin-top: 30px;
  width: 160px;
  height: 45px; 
  font-size: 16px;
  font-weight: 500;
  background-color: #1B3C62;
  color: #ffffff;
  border: none;
  border-radius: 10px;
  margin-left: 10px;
}

.caption1 {
  margin: 5px 0px;
}
</style>
