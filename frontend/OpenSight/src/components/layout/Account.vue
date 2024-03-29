<script setup>

</script>

<template>
  <div class="account">
    <div class="content">
      <p class="title1">은행명</p>
      <p class="body3">통장이름</p>
      <p class="body2">계좌번호</p>
      <p class="title2">잔액</p>
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

.content {
  display: flex;
  flex-direction: column;
  justify-content: left;
  text-align: left;
  margin-top: 40px;
  margin-left: 50px;
}

.title1, .title2, .body2, .body3 {
  color: #ffffff;
  margin: 5px 0px;
}
</style>