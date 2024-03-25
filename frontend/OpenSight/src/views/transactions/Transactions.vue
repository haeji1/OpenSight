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
  <div>
    <Account />
    <button @click="">이체하기</button>
    <TransactionList :transactions="transactions" />
  </div>
</template>

<style scoped>

</style>