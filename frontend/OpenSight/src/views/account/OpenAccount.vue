<script setup>
import { useRouter } from 'vue-router';
import { useAccountStore } from '@/stores/account';
import { onMounted } from 'vue';

const router = useRouter()
const AccountStore = useAccountStore();
onMounted(async () => {
  try {
    let res = await AccountStore.inqureBankAccountListType();
    console.log("res : ", res);
  } catch (error) {
    console.error("Error fetching bank account list:", error);
  }
});


// const express = require('express');
// const app = express();

// app.use((req, res, next) => {
//   res.header('Access-Control-Allow-Origin', '*'); // Allows all origins
//   res.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept, Authorization');
//   if (req.method === 'OPTIONS') {
//     res.header('Access-Control-Allow-Methods', 'PUT, POST, PATCH, DELETE, GET');
//     return res.status(200).json({});
//   }
//   next();
// });

</script>

<template>
  <div class="container">
    <div class="header">
      <p class="title2" @click="router.push('/main')">&lt;</p><p class="title2">비대면계좌 개설</p>
    </div>
    <div class="select-bank">
      <button class="button title2" >한국은행</button>
      <button class="button title2" >산업은행</button>
      <button class="button title2" >기업은행</button>
      <button class="button title2" >국민은행</button>
    </div>
  </div>
</template>

<style scoped>
.container {
  background-color: #ffffff;
}

.header {
  display: flex;
  flex-direction: row;
}

.title2 {
  margin: 30px 10px;
}

.select-bank {
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
  align-items: center;
  margin-top: 50px;
}

.button {
  border: 1px solid #EDEDED;
  border-radius: 15px;
  background-color: #ffffff;
  box-shadow: 0px 4px 30px rgba(54, 41, 183, 0.07);
  width: 300px;
  height: 60px;
}
</style>