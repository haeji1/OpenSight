<script setup>
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
import { ref } from 'vue';
import axios from 'axios'

const router = useRouter()
const authStore = useAuthStore()
const username = ref(null)
const password = ref(null)


const videoRef = ref(null);
const canvasRef = ref(null);

const isCameraReady = ref(false);

const captureImageFilter = () =>{
  if (isCameraReady.value === false){
    setupCamera();
    alert("카메라 준비 완료 다시 버튼을 눌러주세요");
    isCameraReady.value = true;
    return;
  }
  else{
    captureImage();
    alert("사진 캡쳐 완료");

    const now = new Date();
    const dateTimeStr = now.toISOString().replace(/[^0-9]/g, '').slice(0, 14);
    const uniqueFilename = `capturedImage_${dateTimeStr}.jpg`;

    canvasRef.value.toBlob(async (blob) => {
      const formData = new FormData();
      formData.append('requestImage', blob, uniqueFilename);

      try {
        const response = await axios.post('backend:8080/api/users/face-login', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });
        console.log('face login successful:', response.data);
        // Handle success, e.g., redirect or show a success message

  

        sessionStorage.setItem('userId', response.data.data.id);
        sessionStorage.setItem('accessToken', response.data.data.accessToken);
        sessionStorage.setItem('refreshToken', response.data.data.refreshToken);
      } catch (error) {
        console.error('face login failed:', error);
        // Handle error, e.g., show an error message
      }
    }, 'image/jpeg');
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



const logIn = function () {
  const payload = {
    username: username.value,
    password: password.value
  } 
  authStore.login(payload)
}




function naverSocialLogin() {
  window.location.href = 'https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=4hSpDvA1GDlGXjqG6sac&redirect_uri=http://192.168.31.25:8080/login/oauth2/code/naver'
}
</script>

<template>
  <div class="screen-container">
    <div class="login-header">
      <p class="title1">로그인</p>
    </div>
    <div class="login-container">
      <div class="login-subheader">
        <p class="title1-blue">환영합니다</p>
        <p class="caption2">모두를 위한 금융서비스 OpenSight</p>
      </div>
      <form @click.prevent="logIn" class="form">
        <input type="email" v-model.trim="username" placeholder="이메일" class="login-input" />
        <input type="password" v-model.trim="password" placeholder="비밀번호" class="login-input" />
        <button type="submit" class="login-button">로그인</button>
      </form>
      <div class="help-links">
        <div class="other-login">
          <video ref="video" class="video-feed" style="display:none;"></video>
          <canvas ref="canvas" style="display:none;"></canvas>

          <video ref="videoRef" autoplay style="display:none;"></video>
          <canvas ref="canvasRef" style="display:none;"></canvas>

          <img class="login-face" src="../../assets/img/faceid.png" alt="얼굴인식 로그인하기" @click="captureImageFilter">
          <img class="login-naver" src="../../assets/img/naver.png" alt="네이버 소셜로그인 하기" @click="naverSocialLogin">
        </div>
        <a href="#" class="help-link" @click="router.push('/register')">회원이 아니신가요? <span class="link-highlight" @click="router.push('/register')">회원가입</span></a>
        <a href="#" class="help-link" @click="router.push('/find-email')">이메일을 잊으셨나요?</a>
        <a href="#" class="help-link" @click="router.push('/find-password')">비밀번호를 잊으셨나요?</a>
      </div>
    </div>
  </div>
</template>

<style scoped>
.screen-container {
  /* display: flex; */
  align-items: flex-start;
  justify-content: center;
  overflow: auto;
  top: 0;
  left: 0;
  position: fixed;
  width: 100vw;
  height: 100vh;
  background: #1B3C62;
  font-family: 'Poppins', sans-serif;
}

.login-container {
  background-color: #ffffff;
  border-radius: 20px 20px 0 0; /* 아래쪽 모서리는 둥글지 않음 */
  width: 360px; /* 가로 크기 유지 */
  padding: 20px;
  margin: 0 auto;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
  position: fixed; /* 화면에 고정 */
  top: 100px; /* 상단으로부터 130px 떨어진 위치에 배치 */
  left: 50%; /* 가운데 정렬을 위해 */
  transform: translateX(-50%); /* 가운데 정렬을 위해 */
  height: calc(100vh - 50px); /* 상단부터 130px 위치부터 하단까지의 높이 설정 */
  /* overflow: auto;  */
  /* 나머지 스타일은 기존에 설정한 대로 유지 */
}

.form {
  margin: 50px 0px;
}

.login-header {
  color: #ffffff;
  margin-left: 30px;
}

.login-subheader {
  color: #1B3C62;
  text-align: left;
}

.login-description {
  margin-bottom: 2em;
  color: #1B3C62;
  font-size: 0.9em;
}

.login-input {
  border: 1px solid #cbd5e0;
  border-radius: 4px;
  padding: 1em;
  margin-bottom: 1em;
  width: calc(100% - 2em);
  font-size: 0.9em;
}

.login-button {
  height: 45px; 
  font-size: 16px;
  font-weight: 500;
  background-color: #1B3C62;
  color: #ffffff;
  border-radius: 10px;
  padding: 1em;
  width: 100%;
  margin-bottom: 1.5em;
  cursor: pointer;
  text-align: center;
}


.login-naver {
  width: 50px;
  height: 50px;
  margin-left: 25px;
}

.login-face {
  width: 50px;
  height: 50px;
  margin-right: 25px;
}

.other-login {
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin-bottom: 1em;
}

.help-links {
  font-size: 0.8em;
}

.help-link {
  color: #343434;
  display: block;
  margin-bottom: 0.5em;
  margin-top: 5px;
}

.link-highlight {
  color: #1B3C62;
  font-weight: bold;
  text-decoration: none;
}

.title1-blue {
  font-weight: 600;
  font-size: 24px;
  margin-bottom: 5px;
}
/* 각 요소의 사이즈 및 색상은 이미지를 기반으로 조정하였습니다. */
</style>
