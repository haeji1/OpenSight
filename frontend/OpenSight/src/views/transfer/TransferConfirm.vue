<script setup>

</script>

<template>
  <head>
    <h1>이체 확인</h1>
  </head>
  <div class="container">
    <p>김싸피님께 10,000원을</p>
    <p>이체하시겠습니까?</p>
    <div class="transfer-content">
      <p>받은 계좌</p><p>싸피 987654-32-109876</p>
      <p>이체 금액</p><p>10,000원</p>
      <p>출금 계좌</p><p>싸피 123456-78-901234</p>
      <p>받는계좌에 표시</p><p>김싸피</p>
      <p>내계좌에 표시</p><p>이싸피</p>
    </div>
    <div class="button-group">
        <button class="button">취소</button>
        <button class="button highlight">확인</button>
    </div>
  </div>

</template>

<style scoped>
.container {
  max-width: 320px;
  margin: 0 auto;
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 20px;
  box-sizing: border-box;
}
h1 {
  font-size: 18px;
  color: #333;
  margin-bottom: 30px;
}
.button-group {
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
}
.button {
  background-color: #f2f2f2;
  border: none;
  padding: 15px;
  border-radius: 4px;
  margin-bottom: 10px;
  font-size: 16px;
  color: #333;
}
.highlight {
  background-color: #4e8df5;
  color: white;
  font-weight: bold;
}
.divider {
  background-color: #ddd;
  height: 1px;
  border: none;
  margin: 20px 0;
}

.transfer-content {
  border: 1px solid #ccc;
  border-radius: 10px;
  margin-bottom: 50px;
  
}
</style>