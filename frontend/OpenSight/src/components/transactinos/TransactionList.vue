<script setup>
import { defineProps } from 'vue';
const props = defineProps({
  transactions: {
    type: Array,
    required: true,
    default: () => []
  }
});

</script>

<template>
  <div class="transaction-content">
    <div v-for="transaction in transactions" :key="transaction.id">
      <div class="content-box">
        <p>{{ transaction.date }}</p>
        <p>{{ transaction.receivename }}</p>
        <p>입금 {{ transaction.withdrawal }}</p>
        <p>잔액 {{ transaction.balance }}</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.transaction-content {
  border: 1px solid #ccc;
  border-radius: 5px;
}

.content-box {
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #ffffff;
  margin: 10px;
}
</style>