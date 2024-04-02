<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import TransactionList from '@/components/transactinos/TransactionList.vue'
import Account from '@/components/layout/Account.vue'
import axios from 'axios';

const router = useRouter()

const loading = ref(false);
const error = ref(null);
const transactions = ref([]) 
const apiUrl = 'http://localhost:8080/api/accounts/inquire-account-transaction-history';
const accessToken = sessionStorage.getItem('accessToken');

const requestData = {
    bankCode: "004",
    accountNo: "0048656398367274",
    startDate: "20240101",
    endDate: "20241231",
    transactionType: "A",
    orderByType: "DESC",
  };

  onMounted( async () => {
    const aaa = await requestTransactionData(requestData);
    console.log("transactions", aaa);
})

const requestTransactionData = async (requestData) =>{
  try {
    await axios({
      url: apiUrl,
      method: "POST",
      headers : {
        'Authorization': `${accessToken}`
      },
      data: requestData,
    })
    .then((res) => {
      console.log("Transaction data requested successfully:", res.data);
      transactions.value = res.data.data.rec.list;
    })
    
    
  } catch (err) {
    console.error("Failed to request transaction data:", err);
    throw err; // Rethrow the error to handle it where this function is called
  }
}



</script>



<template>
  <div class="container">
    <div class="header">
      <p class="title2" @click="router.push('/main')">&lt;</p><p class="title2">거래 내역 조회</p>
    </div>
    <div class="content">
      <Account class="account" />
      <button class="button" @click="">이체하기</button>
      <TransactionList :transactions="transactions" />
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