<script setup>
import { ref, computed } from 'vue';
import router from '@/router';
import { useAuthStore } from '@/stores/auth';
import axios from 'axios';

const authStore = useAuthStore()
const email = ref(null)
const password = ref(null)
const passwordConfirm = ref(null)
const name = ref(null)
const phoneNumber = ref(null)
const videoRef = ref(null);
const canvasRef = ref(null);

const isCameraReady = ref(false);

const captureImageFilter = () =>{
  if (isCameraReady.value === false){
    setupCamera();
    alert("카메라 준비 완료");
    isCameraReady.value = true;
    return;
  }
  else{
    captureImage();
    alert("사진 캡쳐 완료");
  }
}

const setupCamera = async () => {
  try {
    const stream = await navigator.mediaDevices.getUserMedia({ video: true });
    videoRef.value.srcObject = stream;
    console.error("Setup camera successful");
  } catch (error) {
    console.error("Error accessing the camera", error);
  }
};


const captureImage = () => {
  canvasRef.value.width = videoRef.value.videoWidth;
  canvasRef.value.height = videoRef.value.videoHeight;
  canvasRef.value.getContext('2d').drawImage(videoRef.value, 0, 0);
};



const register = async () => {


  const now = new Date();
  const dateTimeStr = now.toISOString().replace(/[^0-9]/g, '').slice(0, 14);
  const uniqueFilename = `profileImage_${dateTimeStr}.jpg`;

  canvasRef.value.toBlob(async (blob) => {
  const formData = new FormData();
  formData.append('email', email.value);
  formData.append('password', password.value);
  formData.append('username', name.value);
  formData.append('phone', phoneNumber.value);
  // Append the image with a unique filename
  formData.append('profileImage', blob, uniqueFilename);


    try {
      const requestUrl = 'http://backend:8080/api/users/register';
      const response = await axios.post(requestUrl, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });
      console.log('Registration successful:', response.data);
      // Handle success, e.g., redirect or show a success message
    } catch (error) {
      console.error('Registration failed:', error);
      // Handle error, e.g., show an error message
    }
  }, 'image/jpeg');
};

// Check if `userImage` is a file input and has a file selected
/*if (userImage.files && userImage.files[0]) {
  formData.append('userImage', userImage.files[0]);
}*/

// Calling the register method with FormData
// authStore.register(formData);

  //if (password === passwordConfirm) {
    
  // alert('register');
  //   const payload = {
  //     email: email.value,
  //     password: password.value,
  //     name: name.value,
  //     phoneNumber: phoneNumber.value,
  //     userImage: userImage.value
  //   }
  //   authStore.register(payload)
 // }



</script>

<template>
  <div class="container">
    <div class="back-button">&lt; 회원가입</div>
    <div class="content">
      <p>환영합니다</p>
      <form @submit.prevent="register" class="form">
        <label for="email">이메일(필수)</label>
        <input type="email" name="email" id="email" placeholder="이메일" v-model.trim="email" required>

        <label for="password">비밀번호(필수)</label>
        <input type="password" name="password" id="password" placeholder="비밀번호" v-model.trim="password" required>

        <p>비밀번호는 다음과 같은 조건을 만족해야 합니다</p>
        <div class="pwd-check1">
          <span v-if="isLengthValid" class="checkmark">✔</span>
          <span v-else>✔</span>
          <p>비밀번호는 공백없이 10자리 이상 30자 이하여야 합니다</p>
        </div>
        <div class="pwd-check2">
          <span v-if="hasAllRequiredCharacters" class="checkmark">✔</span>
          <span v-else>✔</span>
          <p>영어 대문자, 영어 소문자, 특수문자를 최소 한 글자 이상 포함한 문자 조합이어야 합니다.</p>
        </div>
        <label for="passwordConfirm">비밀번호 확인(필수)</label>
        <input type="password" name="passwordConfirm" id="passwordConfirm" placeholder="비밀번호 확인" v-model.trim="passwordConfirm" required>

        <label for="name">이름(필수)</label>
        <input type="text" name="name" id="name" placeholder="이름" v-model.trim="name" required>

        <label for="phoneNumber">전화번호(필수)</label>
        <input type="tel" name="phoneNumber" id="phoneNumber" placeholder="전화번호" v-model.trim="phoneNumber" required>

        <video ref="videoRef" autoplay style="display:none;"></video>
        <canvas ref="canvasRef" style="display:none;"></canvas>
        <button type="button" @click="captureImageFilter">사진 찍기</button>
        <button type="submit" @click.prevent="register">가입 하기</button>


        

        <!--<button type="submit">회원가입</button>-->
      </form>
    </div>
  </div>
</template>


<style scoped>
.container {
  font-family: 'Arial', sans-serif;
  max-width: 360px;
  margin: 0 auto;
  overflow: auto;
  display: flex;
  flex-direction: column;
}

.content {
  padding: 20px;
  background-color: #ffffff;
  border-radius: 10px 10px 0 0;
}

.form {
  display: flex;
  flex-direction: column;
}

.form label {
  margin-top: 20px;
}

.form input {
  padding: 10px;
  margin-top: 5px;
  border: 1px solid #ccc;
  border-radius: 10px;
}

.form small {
  color: #888;
  margin-top: 5px;
}

.form ul {
  color: #555;
  font-size: 0.9em;
  margin-top: 5px;
  padding-left: 20px;
}

.form button {
  background-color: #007aff;
  color: white;
  padding: 10px;
  margin-top: 30px;
  border: none;
  border-radius: 10px;
  font-size: 1em;
}

.back-button {
  font-size: 1em;
  color: #007aff;
  margin-bottom: 30px;
  cursor: pointer;
}
.checkmark {
  color: green;
  margin-right: 5px;
}
.pwd-check1 {
  flex-direction: row;
}
</style>