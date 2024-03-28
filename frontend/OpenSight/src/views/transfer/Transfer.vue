<script setup>
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter()

const banks = ref([
  { bankCode: '001', bankName: '한국은행'},
  { bankCode: '002', bankName: '산업은행'},
  { bankCode: '003', bankName: '기업은행'},
  { bankCode: '004', bankName: '국민은행'},
])

</script>

<template>
  <div class="container">
    <div class="header">
      <p class="title2" @click="router.push('/main')">&lt;</p><p class="title2">이체</p>
    </div>
    <div class="content">
      <div class="account-info">
        <p class="body3">자유입출금통장</p>
        <p class="title2">123456-78-901234</p>
        <div class="current-balance">
          <p class="body3">잔액</p><p class="body1">1,316,000원</p>
        </div>
        <div class="available-balance">
          <p class="body3">1일 출금한도</p><p class="body1">5,000,000원</p>
        </div>
      </div>

      <form class="transfer-form">
        <section class="input-group">
          <select class="select-bank" name="bank" id="bank">
            <option value="">은행선택</option>
            <option v-for="bank in banks" :key="bank.bankCode" :value="bank.bankCode">
              {{ bank.bankName }}
            </option>
          </select>
        </section>

        <section class="input-group">
          <label>계좌번호</label>
          <input type="text"/>
        </section>

        <section class="input-group">
          <label>보낼금액</label>
          <input type="text"  />
        </section>

        <section class="input-group">
          <label>받는계좌에 표시</label>
          <input type="text" placeholder="미입력시 보낸 분 이름 표시" />
        </section>

        <section class="input-group">
          <label>내계좌에 표시</label>
          <input type="text" placeholder="미입력시 받는 분 이름 표시" />
        </section>

        <div class="button-group">
          <button type="button" @click="cancel">취소</button>
          <button type="submit" @click="submit">다음</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>

</script>

<style scoped>
.container {
  background-color: #ffffff;
}

.header {
  display: flex;
  flex-direction: row;
  margin-left: 20px;
}


/* 여기에 추가적인 CSS 스타일을 정의하시면 됩니다 */
/* 예시 */
.account-info {
  margin: 10px;
  border: 1px solid #ccc;
  padding: 16px;
  margin-bottom: 16px;
}

.input-group label {
  display: block;
}

.input-group input,
.input-group select {
  width: 100%;
}

.button-group button {
  width: 48%;
  margin-right: 4%;
}

.button-group button:last-child {
  margin-right: 0;
}



.body1, .body3 {
  margin: 0;
}
</style>
