<script setup>
import BookMarkModal from'@/components/modal/BookMarkModal.vue';
import SelectBankModal from '@/components/modal/SelectBankModal.vue';

const openBookMarkModal = () => {
  isModalVisible.value = true;
}

const closeBookMarkModal = () => {
  isModalVisible.value = false;
}


</script>

<template>
  <div class="transfer-container">
    <header>
      <h1>이체</h1>
    </header>
    <div class="content">
      <div class="account-info">
        <label>자유입출금통장</label>
        <div class="account-number">123456-78-901234</div>
        <div class="balance-info">
          <div class="current-balance">잔액 1,316,000원</div>
          <div class="available-balance">1일 출금한도 5,000,000원</div>
        </div>
      </div>

      <form class="transfer-form">
        <section class="input-group">
          <label>은행명</label>
          <button @click="openBookMarkModal()">즐겨찾기</button>
          <input type="text" @click="" readonly>
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
.transfer-container {
  max-width: 400px;
  margin: auto;
}

.content {
  background-color: #ffffff;
  padding: 10px;
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
h1 {
  color: #ffffff;
}
</style>
