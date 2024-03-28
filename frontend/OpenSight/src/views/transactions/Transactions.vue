<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import TransactionList from '@/components/transactinos/TransactionList.vue'
import Account from '@/components/layout/Account.vue'

const router = useRouter()


const transactions = ref([
  { id: 1, date: '2024.03.11 10:20:20', receivename: '이싸피', withdrawal: '10,000원', balance: '1,100,000원'},
  { id: 2, date: '2024.03.09 10:20:20', receivename: '김싸피', withdrawal: '10,000원', balance: '1,110,000원'},
  { id: 3, date: '2024.03.08 10:20:20', receivename: '박싸피', withdrawal: '10,000원', balance: '1,120,000원'},
])


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