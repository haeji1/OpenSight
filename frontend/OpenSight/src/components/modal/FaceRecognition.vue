<script setup>
import { ref } from "vue"
import { useAccountStore }  from "@/stores/account.js";
import { cameraActivate } from '@/stores/chatbot'
import axios from 'axios'

const accountStore = useAccountStore();

const videoRef = ref(null);
const isCameraReady = ref(false);
const canvasRef = ref(null);

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

    const accessToken = sessionStorage.getItem('accessToken');

    canvasRef.value.toBlob(async (blob) => {
      const formData = new FormData();
      formData.append('requestImage', blob, uniqueFilename);

      try {
        const response = await axios.post(
            `${import.meta.env.VITE_REST_API}/users/face-auth`,
            formData,
            {
              headers: {
                'Content-Type': 'multipart/form-data', // Content-Type을 올바르게 설정해야 합니다.
                'Authorization': `${accessToken}`
              }
            }
        );
        console.log(response.data);
        if (response.data.data.result === "success") {
          alert("얼굴인식 성공");

          await accountStore.inquireAccountList();

          console.log(accountStore.myAccountList[0].bankCode);
          console.log(accountStore.myAccountList[0].accountNo);
          console.log ("balance : " , transferBalance.value);
          console.log ("receiverBankCode : " , receiverBankCode.value);
          console.log ("receiverAccountNo : " , receiverAccountNo.value);


          accountStore.selectedBank = receiverBankCode.value;
          accountStore.accountNumber = receiverAccountNo.value;
          accountStore.amount = transferBalance.value;
          accountStore.selectedMyAccountBankCode = accountStore.myAccountList[0].bankCode;
          accountStore.selectMyAccountNumber = accountStore.myAccountList[0].accountNo;
          accountStore.accountTransfer();
          messages.value.push(
              {
                message : "입금 완료.",
                isChatbot : 1
              }
          )
          cameraActivate.value = false;

        } else {
          alert("얼굴인식 실패");
          cameraActivate.value = false;
        }
      }
      catch (error) {
        console.error(error);
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

</script>

<template>
  <div class="modal">
    <video ref="videoRef" autoplay style="display:none;"></video>
    <canvas ref="canvasRef" style="display:none;"></canvas>
    <div @click="captureImageFilter">
      <img class="faceid" src="../assets/img/faceid.png" alt="얼굴 인식 버튼">
    </div>
  </div>
</template>

<style scoped>
.modal {
  background-color: rgba(255, 255, 255, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 100vh;
  z-index: 99;
}

.faceid {
  width: 100px;
  height: 100px;
}
</style>