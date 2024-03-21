<script setup>
import { ref } from 'vue';

const verificationCode = ref('');
const invalidCode = ref(false);

// const checkVerificationCode = () => {
//   // 인증번호 유효성 검사 로직
//   // 예시로, 만약 verificationCode의 길이가 6 미만이면 invalidCode를 true로 설정
//   invalidCode.value = verificationCode.value.length < 6;
// };

// const submitVerificationCode = () => {
//   // 인증번호 제출 로직
//   // 여기에 서버로 데이터를 보내고 결과를 처리하는 코드를 작성하면 됩니다.
//   if (!invalidCode.value) {
//     // 서버로 verificationCode.value를 전송하는 로직
//   }
// };
</script>

<template>
  <div>
    <div class="verification-container">
      <p>이메일 찾기</p>
      <div class="input-group">
        <p>회원가입 시 입력한 전화번호를 입력해주세요</p>
        <input
          v-model="verificationCode"
          type="text"
          placeholder="인증번호"
          @input="checkVerificationCode"
        />
        <!-- <p v-if="invalidCode">유효하지 않은 인증번호입니다.</p> -->
      </div>
      <button @click="submitVerificationCode">이메일 찾기</button>
    </div>
  </div>
</template>

<style scoped>
.verification-container {
  background-color: #f5f7fa;
  padding: 20px;
  text-align: center;
}

.input-group {
  margin: 20px 0;
}

input {
  width: 100%;
  padding: 10px;
  margin-bottom: 5px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
}

button {
  width: 100%;
  padding: 10px;
  border: none;
  border-radius: 4px;
  background-color: #409eff;
  color: white;
  font-size: 16px;
  cursor: pointer;
}

button:hover {
  background-color: #66b1ff;
}


</style>