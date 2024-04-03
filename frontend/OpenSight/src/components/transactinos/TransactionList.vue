<script setup>
import { defineProps } from 'vue';
const props = defineProps({
  transactions: {
    type: Array,
    required: true,
    default: () => []
  }
});

const formatDateTime = (date, time) => {
  const year = date.slice(0, 4);
  const month = date.slice(4, 6);
  const day = date.slice(6, 8);
  
  const hours = time.slice(0, 2);
  const minutes = time.slice(2, 4);
  const seconds = time.slice(4, 6);

  return `${year}.${month}.${day}  ${hours}:${minutes}:${seconds}`;
};
</script>


<template>
  <div class="transaction-content">
    <div v-for="transaction in transactions" :key="transaction.transactionUniqueNo">
      <div class="content-box">
        <p class="caption1 gray">{{ formatDateTime(transaction.transactionDate, transaction.transactionTime)}}</p>
        
        <div class="content-text">
          <div class="left-content">
            <p class="title3">{{ transaction.transactionSummary }}</p>
          </div>
          <div class="right-content">
            <div class="right-text">
              <p class="caption1 gray">{{ transaction.transactionTypeName }}</p>
              <p v-if="transaction.transactionTypeName == '입금'" class="caption1 color-blue">
                {{ transaction.transactionBalance }}원
              </p>
              <p v-else class="caption1 color-red">
                {{ transaction.transactionBalance }}원
              </p>
            </div>
            <div class="right-text">
              <p class="caption1 gray">잔액</p><p class="caption1">{{ transaction.transactionAfterBalance}}원</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.content-box {
  border: 1px solid #EDEDED;
  border-radius: 15px;
  background-color: #ffffff;
  box-shadow: 0px 4px 30px rgba(54, 41, 183, 0.07);
  width: 340px;
  height: 120px;
  display: flex;
  flex-direction: column;
  text-align: left;
  justify-content: center;
  margin: 15px 0px;
}

.content-text {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  padding: 0px 20px;  
}

.gray {
  color: #979797;
}

.color-red {
  color: #ff4267;
}

.color-blue {
  color: #416692;
}

.right-text {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

.caption1 {
  margin-left: 20px;
  margin-top: 5px;
}
.title3 {
  color: #000;
}

</style>