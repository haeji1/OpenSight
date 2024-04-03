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
const apiUrl = `${import.meta.env.VITE_REST_API}/accounts/inquire-account-transaction-history`;
const accessToken = sessionStorage.getItem('accessToken');


const showModal = ref(false);
const selectedPeriod = ref('1');
const selectedType = ref('A');

const openModal = () => {
  showModal.value = true;
};

const closeModal = () => {
  showModal.value = false;
};


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
  loading.value = true; // Indicate loading process has started
  try {
    const response = await axios({
      url: apiUrl,
      method: "POST",
      headers : {
        'Authorization': `${accessToken}` // It's common to use 'Bearer' in Authorization headers
      },
      data: requestData,
    });

    if (response.data && response.data.data && response.data.data.rec && response.data.data.rec.list) {
      transactions.value = response.data.data.rec.list.map(transaction => ({
        ...transaction,
        transactionBalance: Number(transaction.transactionBalance).toLocaleString('en-US'),
        transactionAfterBalance: Number(transaction.transactionAfterBalance).toLocaleString('en-US'),
      }));
    } else {
      // If 'rec' or 'list' does not exist, log an error or set transactions to an empty array
      console.error("No transactions list found in the response.");
      transactions.value = [];
    }
  } catch (err) {
    console.error("Failed to request transaction data:", err);
    error.value = err.message; // Set the error message for display
  }
  loading.value = false; // Indicate loading process has ended
}

const applyFilters = async () => {
  const endDate = new Date();
  const startDate = new Date();
  startDate.setMonth(startDate.getMonth() - parseInt(selectedPeriod.value));
  
  // Adjust the start and end dates in the requestData object
  requestData.startDate = startDate.toISOString().split('T')[0].replace(/-/g, '');
  requestData.endDate = endDate.toISOString().split('T')[0].replace(/-/g, '');

  // Map the selected type to the API's expected values and adjust the requestData object
  const typeMap = {
    'A': 'A', // Assuming 'A' is acceptable for the API to fetch all transactions
    'D': '1', // Deposit
    'W': '2'  // Withdrawal
  };

  requestData.transactionType = typeMap[selectedType.value];

  // Call the API with the updated requestData object
  await requestTransactionData(requestData);

  closeModal();
};

</script>



<template>
  <div class="container">
    <div class="header">
      <p class="title2" @click="router.push('/main')">&lt;</p><p class="title2">거래 내역 조회</p>
    </div>
    <div class="content">
      <Account class="account" />
      <button class="button" @click="transferButtonClick">이체하기</button>
      <button class="button" @click="openModal">조회 기간 설정</button>
      <div v-if="showModal" class="modal">
    <div class="modal-content">
      <h3>조회 기간 설정</h3>
      <label for="period">기간선택</label>
      <select id="period" v-model="selectedPeriod">
        <option value="1">1개월</option>
        <option value="3">3개월</option>
        <option value="6">6개월</option>
      </select>

      <label for="transactionType">거래내역</label>
      <select id="transactionType" v-model="selectedType">
        <option value="A">전체</option>
        <option value="D">입금</option>
        <option value="W">출금</option>
      </select>

      <button class="modal-button" @click="applyFilters">적용</button>
      <button class="modal-button" @click="closeModal">닫기</button>
    </div>
  </div>
      <TransactionList :transactions="transactions" />
      <div v-for="transaction in transactions" :key="transaction.transactionUniqueNo">
</div>
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
.modal {
  display: flex;
  position: fixed;
  z-index: 1000;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  align-items: center;
  justify-content: center;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 15px;
  text-align: center;
}
</style>