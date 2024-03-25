<script setup>
import { useRouter } from 'vue-router';

const router = useRouter()

</script>

<template>
  <div class="container">
    <div class="content">
      <div class="check-mark">
        <img class="check-img" src="../../assets/img/check-mark.png" alt="회원가입이 완료되었다는 이미지">
      </div>
      <div class="title">
        <p>이체가 완료되었습니다</p>
        <div class="transfer-content">
          <p>받은 계좌</p><p>싸피 987654-32-109876</p>
          <p>이체 금액</p><p>10,000원</p>
          <p>거래 일자</p><p>2024.03.11.</p>
          <p>출금 계좌</p><p>싸피 123456-78-901234</p>
        </div>
        <div>
          <button @click="router.push('/main')">메인 화면으로 돌아가기</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
}

.content {
  background-color: #ffffff;

}

.check-img {
  width: 150px;
  height: 150px;
}

.transfer-content {
  border: 1px solid #ccc;
  border-radius: 10px;
  margin-bottom: 50px;
  
}
</style>