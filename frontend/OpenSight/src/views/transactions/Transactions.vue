<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import TransactionList from '@/components/transactinos/TransactionList.vue'
// import PeriodSettingModal from '@/components/modal/PeriodSettingModal.vue';
import Account from '@/components/layout/Account.vue'
import axios from 'axios';

import { useAccountStore } from '@/stores/account'

const store = useAccountStore()

const router = useRouter()

const transactions = ref([])
// const apiUrl = 'http://localhost:8080/api/accounts/inquire-account-transaction-history';
const apiUrl = 'https://j10b104.p.ssafy.io/api/accounts/inquire-account-transaction-history'
const accessToken = sessionStorage.getItem('accessToken');

const requestData = ref(store.currentAccount)

console.log('****', store.currentAccount, '****')
console.log('++++', requestData._value, '++++')

// 최근 거래내역조회를 가져오는 함수
const requestTransactionData = async (requestData) =>{

  console.log('은행코드:', requestData._value.bankCode)
  console.log('계좌번호:', requestData._value.accountNo)

  try {
    await axios({
      url: apiUrl,
      method: "POST",
      headers : {
        'Authorization': `${accessToken}`
      },
      data: {
        "bankCode": requestData._value.bankCode,
        "accountNo": requestData._value.accountNo,
        "startDate": "20240101",
        "endDate": "20241231",
        "transactionType": "A",
        "orderByType": "DESC"
      },
    })
    .then((res) => {
      if (res.data.data.result === 'success') {
        console.log("Transaction data requested successfully:", res.data)
        transactions.value = res.data.data.rec.list
      }

      else {
        console.log(res.data)
      }
    })


  } catch (err) {
    console.error("Failed to request transaction data:", err);
    throw err; // Rethrow the error to handle it where this function is called
  }
}


// 페이지를 불러오면서 해당 계좌에 있는 거래내역 가져오기
onMounted( async () => {
  const aaa = await requestTransactionData(requestData);
  console.log("transactions", aaa);
})
</script>



<template>
  <div class="container">
    <div class="header">
      <p class="title2" @click="router.push('/main')">&lt;</p><p class="title2">거래 내역 조회</p>
    </div>
    <div class="content">
      <Account class="account" />
      <button class="button" @click="">이체하기</button>
      <!-- <button class="">조회기간설정</button> -->
      <TransactionList :transactions="transactions" />
      <!-- <DateRangeModal v-if="isModalOpen" @update="updateDateRange" @close="closeModal" /> -->
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  background-color: #ffffff;
}


.content {
  display: flex;
  flex-direction: column;
  background-color: #ffffff;
  justify-content: center;
  text-align: center;
  align-items: center;
}

.header {
  display: flex;
  flex-direction: row;
  padding-left: 30px;
  justify-content: left;
  text-align: left;
}

.button {
  margin-top: 10px;
  width: 327px;
  height: 45px; 
  font-size: 16px;
  font-weight: 500;
  background-color: #1B3C62;
  color: #ffffff;
  border-radius: 10px;
}

.title2 {
  margin-right: 10px;
}
</style>