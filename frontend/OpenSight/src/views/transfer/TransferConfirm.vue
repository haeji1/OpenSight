  <script setup>
  import { onMounted, ref } from 'vue';
  import { useRouter } from 'vue-router';
  import {useAccountStore} from "@/stores/account.js";
  import axios from 'axios'

  const router = useRouter()
  const accountStore = useAccountStore();

  function faceButtonClick() {
    captureImageFilter();
  }

  function simplePwButtonClick() {
    router.push({name : "TransferVerify"})
  }

  const isCameraReady = ref(false);
const canvasRef = ref(null);
const videoRef = ref(null);

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
          const transferResult = await accountStore.accountTransfer();
          console.log("========transferResult============"+transferResult);
          // transactionDate를 변수에 저장합니다.
          const transactionDate = transferResult.data.data.rec[0].transactionDate;
          console.log(transactionDate);
          accountStore.transactionDate = transactionDate;
          router.push({name : "TransferComplete"})
        } else {
          alert("얼굴인식 실패");
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
    <div class="container">
      <div class="header">
        <p class="title1">이체 확인</p>
      </div>
      <div class="main-text">
          <p class="title2">{{accountStore.senderName}}님께 {{accountStore.amount}}원을</p>
          <p class="title2">이체하시겠습니까?</p>
        </div>
      <div class="content">
        <div class="transfer-content">
          <div class="content-text">
            <p class="caption1">받는 계좌</p><p class="caption1">{{ accountStore.selectedBankName }} {{ accountStore.accountNumber }}</p>
          </div>
          <div class="content-text">
           <p class="caption1">이체 금액</p><p class="caption1">{{ accountStore.amount }}</p>
          </div>
          <div class="content-text">
            <p class="caption1">출금 계좌</p><p class="caption1">{{accountStore.recipientName}} {{accountStore.myAccountList[0].accountNo}}</p>
          </div>
          <div class="content-text">
            <p class="caption1">받는계좌에 표시</p><p class="caption1">{{ accountStore.recipientName }}</p>
          </div>
          <div class="content-text">
            <p class="caption1">내계좌에 표시</p><p class="caption1">{{accountStore.senderName}}</p>
          </div>
        </div>
        <div class="button-group">
          <video ref="videoRef" autoplay style="display:none;"></video>
          <canvas ref="canvasRef" style="display:none;"></canvas>
            <button class="button-cancel">취소</button>
            <button class="button-confirm" @click="faceButtonClick">얼굴인식확인</button>
            <button class="button-confirm" @click="simplePwButtonClick">비밀번호확인</button>
        </div>
      </div>
    </div>

  </template>

  <style scoped>
  .container {
    background-color: #ffffff;
  }
  .header {
    display: flex;
    justify-content: left;
    text-align: left;
    margin-left: 30px;
  }
  .content {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  .main-text {
    display: flex;
    flex-direction: column;
    text-align: left;
    justify-content: left;
    margin-left: 30px;
    margin-top: 50px;
  }

  .content-text {
    display: flex;
    flex-direction: row; /* 가로로 배열 */
    justify-content: space-between; /* 양 끝으로 정렬 */
    width: 100%; /* 전체 너비 사용 */
    padding: 5px;
  }

  .button-group {
    display: flex;
    flex-direction: row;
    justify-content: space-evenly;
    width: 100%;
    margin-top: 100px;
  }

  .button-cancel {
    margin-top: 50px;
    width: 120px;
    height: 45px;
    font-size: 16px;
    font-weight: 500;
    background-color: #e0e0e0;
    color: #000000;
    border-radius: 10px;
    border: none;
  }

  .button-confirm {
    margin-top: 50px;
    width: 120px;
    height: 45px;
    font-size: 16px;
    font-weight: 500;
    background-color: #1B3C62;
    color: #ffffff;
    border: none;
    border-radius: 10px;
  }

  .transfer-content {
    display: flex;
    flex-direction: column;
    justify-content: center;
    text-align: center;
    align-items: center;
    border: 1px solid #EDEDED;
    border-radius: 15px;
    background-color: #ffffff;
    box-shadow: 0px 4px 30px rgba(54, 41, 183, 0.07);
    width: 360px;
    margin-top: 70px;
  }

  .title2 {
    margin: 0
  }

  .caption1 {
    margin: 10px 20px;
  }

  </style>