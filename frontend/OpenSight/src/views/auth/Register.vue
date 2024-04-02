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


  if (isCameraReady.value === true){//사진 있을때
    canvasRef.value.toBlob(async (blob) => {
    const formData = new FormData();
    formData.append('email', email.value);
    formData.append('password', password.value);
    formData.append('username', name.value);
    formData.append('phone', phoneNumber.value);
    // Append the image with a unique filename
    formData.append('profileImage', blob, uniqueFilename);


      try {//http://52.78.27.70:8080/api/users/register
        const requestUrl = `${import.meta.env.VITE_REST_API}/users/register`;
        const response = await axios.post(requestUrl, formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });
        console.log('Registration successful:', response.data);
        router.push('/register/complete')
        // Handle success, e.g., redirect or show a success message
      } catch (error) {
        alert('회원가입이 실패했습니다. 다시 시도해주세요.')
        console.error('Registration failed:', error);
        // Handle error, e.g., show an error message
      }
    }, 'image/jpeg');
  }
  else {//사진 없을때
    const formData = new FormData();
    formData.append('email', email.value);
    formData.append('password', password.value);
    formData.append('username', name.value);
    formData.append('phone', phoneNumber.value);

    try {
        const requestUrl = `${import.meta.env.VITE_REST_API}/users/register`;
        const response = await axios.post(requestUrl, formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });
        console.log('Registration successful:', response.data);
        router.push('/register/complete')
        // Handle success, e.g., redirect or show a success message
      } catch (error) {
        alert('회원가입이 실패했습니다. 다시 시도해주세요.')
        console.error('Registration failed:', error);
      }
  }
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
    <div class="header">
      <p class="title2 white" @click="router.push('/login')">&lt;</p><p class="title2 white">회원가입</p>
    </div>
    <div class="content">
      <p class="title1">환영합니다</p>
      <form @submit.prevent="register" class="form">
        <label class="caption1" for="email">이메일(필수)</label>
        <input type="email" name="email" id="email" placeholder="이메일" v-model.trim="email" required>

        <label class="caption1" for="password">비밀번호(필수)</label>
        <input type="password" name="password" id="password" placeholder="비밀번호" v-model.trim="password" required>

        <p class="caption1">비밀번호는 다음과 같은 조건을 만족해야 합니다</p>
        <div class="pwd-check1">
          <p class="caption2">비밀번호는 공백없이 10자리 이상 30자 이하여야 합니다</p>
        </div>
        <div class="pwd-check2">
          <p class="caption2">영어 대문자, 영어 소문자, 특수문자를 최소 한 글자 이상 포함한 문자 조합이어야 합니다.</p>
        </div>
        <label class="caption1" for="passwordConfirm">비밀번호 확인(필수)</label>
        <input type="password" name="passwordConfirm" id="passwordConfirm" placeholder="비밀번호 확인" v-model.trim="passwordConfirm" required>

        <label class="caption1" for="name">이름(필수)</label>
        <input type="text" name="name" id="name" placeholder="이름" v-model.trim="name" required>

        <label class="caption1" for="phoneNumber">전화번호(필수)</label>
        <input type="tel" name="phoneNumber" id="phoneNumber" placeholder="전화번호" v-model.trim="phoneNumber" required>

        <video ref="videoRef" autoplay style="display:none;"></video>
        <canvas ref="canvasRef" style="display:none;"></canvas>
        <div class="button-container">
          <button class="button" type="button" @click="captureImageFilter">사진 찍기</button>
          <button class="button" type="submit" @click.prevent="register">가입 하기</button>
        </div>


      </form>
    </div>
  </div>
</template>


<style scoped>
.container {
  display: flex;
  flex-direction: column;
}

.header {
  display: flex;
  flex-direction: row;
}

.content {
  background-color: #ffffff;
  border-radius: 20px 20px 0 0; /* 아래쪽 모서리는 둥글지 않음 */
  width: 370px; /* 가로 크기 유지 */
  padding: 20px;
  margin: 0 auto;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  /* text-align: center; */
  position: fixed; /* 화면에 고정 */
  top: 70px; /* 상단으로부터 130px 떨어진 위치에 배치 */
  left: 50%; /* 가운데 정렬을 위해 */
  transform: translateX(-50%); /* 가운데 정렬을 위해 */
  height: calc(100vh - 50px); 
  overflow: auto; 
  /* 나머지 스타일은 기존에 설정한 대로 유지 */
}

.form {
  display: flex;
  flex-direction: column;
  padding-bottom: 100px;
}

.form label {
  margin-top: 10px;
}

.form input {
  padding: 10px;
  margin-top: 5px;
  border: 1px solid #ccc;
  border-radius: 10px;
}




.button-container {
  display: flex;
  flex-direction: column; /* 버튼을 수직으로 쌓음 */
  align-items: center; /* 버튼을 가로축 중앙에 배치 */
  margin-top: auto; /* 컨테이너 상단의 여백을 자동으로 설정하여 내용을 위로 밀어냄 */
  margin-bottom: 20px; /* 하단 여백도 자동으로 설정 */
}

.button {
  margin-top: 50px;
  width: 327px;
  height: 45px; 
  font-size: 16px;
  font-weight: 500;
  background-color: #1B3C62;
  color: #ffffff;
  border-radius: 10px;
}

.checkmark {
  color: green;
  margin-right: 5px;
  margin-top: 5px;
}
.pwd-check1 {
  display: flex;
  flex-direction: row;
}

.white {
  color: #ffffff;
  margin-left: 15px;
}
</style>