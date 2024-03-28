<script setup>
import { ref } from 'vue';

const input = ref('');

const appendNumber = (number) => {
  input.value += number;
};

const clearInput = () => {
  input.value = '';
};

const backspace = () => {
  input.value = input.value.slice(0, -1);
};

const confirmInput = () => {
  // 입력된 값을 확인하는 로직
  console.log(input.value);
};
</script>

<template>
  <div class="container">
    <div class="numpad-container">
      <div class="input-display">
        {{ input }}
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
        <button class="confirm" @click="confirmInput">이제</button>
      </div>
    </div>
  </div>
</template>

<style scoped>

.container {
  background-color: #ffffff;
}

.numpad-container {
  width: 100%;
  max-width: 300px;
  margin: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 15px;
}

.input-display {
  width: 100%;
  border-bottom: 1px solid #ccc;
  padding: 10px;
  text-align: center;
  font-size: 24px;
}

.numpad {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 5px;
}

.numpad button {
  padding: 15px;
  font-size: 18px;
  background-color: #f7f7f7;
  border: 1px solid #ddd;
  flex: 1;
}

.numpad button.special {
  background-color: #dedede; /* 특별한 기능을 가진 버튼의 배경색 */
}

.actions {
  width: 100%;
  display: flex;
  justify-content: space-between;
}

.actions button {
  padding: 10px 30px;
  font-size: 16px;
  border: none;
  border-radius: 5px;
}

.cancel {
  background-color: #e0e0e0;
}

.confirm {
  background-color: #1B3C62;
  color: white;
}
</style>