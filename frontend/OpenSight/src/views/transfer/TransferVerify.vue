<script setup>
import { ref,computed } from 'vue';
import { useAccountStore } from '@/stores/account';
import router from '@/router';

const input = ref('');

const displayInput = computed(() => '*'.repeat(input.value.length));

const accountStore = useAccountStore();

const appendNumber = (number) => {
  input.value += number;
};

const clearInput = () => {
  input.value = '';
};

const backspace = () => {
  input.value = input.value.slice(0, -1);
};

const confirmInput = async () => {
  // 입력된 값을 확인하는 로직
  console.log("=======inputvalue======="+input.value);
  const result = await accountStore.checkSimplePassword(input.value);
  console.log(result)
  if (result === true) {
    const transferResult = await accountStore.accountTransfer();
    console.log("========transferResult============"+transferResult);
    // transactionDate를 변수에 저장합니다.
    const transactionDate = transferResult.data.data.rec[0].transactionDate;
    console.log(transactionDate);
    accountStore.transactionDate = transactionDate;
    router.push({name:"TransferComplete"});
  }
  else alert("비밀번호를 다시 입력해주세요");
};


</script>

<template>
  <div class="container">
    <div class="hedaer">
      <p class="title1">비밀번호 입력</p>
    </div>
    <div class="numpad-container">
      <div class="input-display">
        {{ displayInput }}
      </div>
      <div class="numpad">
        <button v-for="number in 9" :key="number" @click="appendNumber(number)">
          {{ number }}
        </button>
        <button @click="clearInput">C</button> <!-- 전체 삭제 -->
        <button @click="appendNumber(0)">0</button>
        <button @click="backspace">&#9003;</button> <!-- 단일 삭제 -->
      </div>
      <div class="actions">
        <button class="cancel" @click="clearInput">취소</button>
        <button class="confirm" @click="confirmInput">이체</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  background-color: #ffffff;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; /* 현대적인 느낌의 폰트 적용 */
}

.numpad-container {
  width: 100%;
  max-width: 360px; /* 이미지의 가로 너비에 맞게 조정 */
  margin: 20px auto; /* 상단 여백 추가 */
  display: flex;
  flex-direction: column;
  align-items: stretch; /* 가로로 쭉 늘어나게 조정 */
}

.input-display {
  height: 100px;
  width: 100%;
  border-bottom: 2px solid #000; /* 선을 더 도드라지게 */
  padding: 20px; /* 여유로운 패딩으로 가독성 향상 */
  text-align: right; /* 오른쪽 정렬로 변경 */
  font-size: 32px; /* 입력된 숫자가 더 크게 보이도록 */
  box-sizing: border-box; /* 패딩이 너비에 포함되도록 */
}

.numpad {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 10px; /* 버튼 사이의 간격 늘림 */
  padding: 0 20px; /* 좌우 여백 추가 */
  margin-top: 100px;
}

.numpad button {
  padding: 20px 0; /* 버튼의 세로 패딩을 늘려 높이 증가 */
  font-size: 22px; /* 숫자 크기 증가 */
  background-color: #f7f7f7;
  border: 2px solid #ddd; /* 경계선 더 도드라지게 */
  border-radius: 10px; /* 둥근 모서리 적용 */
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.1); /* 약간의 그림자 효과 */
}

.numpad button:active {
  background-color: #dedede; /* 클릭 시 배경색 변경 */
}

.actions {
  display: flex;
  justify-content: space-between;
  margin-top: 20px; /* 버튼들 사이의 상단 여백 추가 */
  padding: 0 20px; /* 좌우 여백 추가 */
}

.actions button {
  flex: 1; /* 버튼을 균등하게 분할 */
  margin: 0 10px; /* 버튼 사이의 간격 추가 */
  padding: 15px 0; /* 세로 패딩 늘림 */
  font-size: 18px; /* 글씨 크기 증가 */
  border: none;
  border-radius: 10px; /* 둥근 모서리 적용 */
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.1); /* 약간의 그림자 효과 */
}

.cancel {
  background-color: #ffffff;
  color: #1b3c62;
}

.confirm {
  background-color: #1b3c62; /* iOS 기본 블루 컬러로 변경 */
  color: white;
}

.title1 {
  margin-left: 20px;
}

</style>