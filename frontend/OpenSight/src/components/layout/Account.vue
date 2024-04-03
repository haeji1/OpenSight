<script setup>
import { defineProps } from 'vue';
const props = defineProps({

})

</script>

<template>
  <div class="account">
    <div class="content">
      <p class="title1-white">은행명</p>
      <p class="body3-white">통장이름</p>
      <p class="body2-white">계좌번호</p>
      <p class="title2-white">잔액</p>
    </div>
  </div>
</template>

<style scoped>
.account {
  background-image: url('src/assets/img/account.png');
  background-position: center;
  width: 360px;
  height: 250px;
  border-radius: 15px;
}

.account-content {
  display: flex;
  flex-direction: column;
  justify-content: left;
  text-align: left;
  margin-top: 40px;
  margin-left: 50px;
}

.title1-white, .title2-white, .body2-white, .body3-white {
  color: #ffffff;
  margin: 5px 0px;
}
</style>