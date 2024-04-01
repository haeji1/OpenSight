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
        <p class="caption1 gray">{{ transaction.date }}</p>
        <div class="content-text">
          <div class="left-content">
            <p class="title3">{{ transaction.receivename }}</p>
          </div>
          <div class="right-content">
            <div class="right-text">
              <p class="caption1 gray">입금</p><p class="caption1">{{ transaction.withdrawal }}</p>
            </div>
            <div class="right-text">
              <p class="caption1 gray">잔액</p><p class="caption1">{{ transaction.balance }}</p>
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
  width: 300px;
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

.right-text {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

.caption1 {
  margin-left: 20px;
  margin-top: 5px;
}
</style>