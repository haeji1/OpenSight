<script setup>
import { ref, watch } from "vue"
import { useRouter } from "vue-router";
import ChatMessage from '@/components/ChatMessage.vue'
import { useChatBotStore2 } from '@/stores/chatbot2'
import {useAccountStore}  from "@/stores/account.js";

const accountStore = useAccountStore();

const router = useRouter();

const store = useChatBotStore2()

const textMessage = ref(null)

const messages = ref([
  {
    message: '안녕하세요! 원하시는 업무를 말씀해주시거나 입력해주세요.',
    isChatbot: 1 },
])


const isNextCommandTrasnferInfoRequest = ref(0);

const receiverBankCode = ref("");
const receiverAccountNo = ref("");
const transferBalance = ref("");

const messageToSend = ref("");

const cameraActivate = ref(false);
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
async function sendVoiceToCommand() {
  alert("sendVoiceToCommand");
  try {
    const result = await store.micCommand2();
    console.log(result);
    messageToSend.value = result;


  } catch (error) {
    console.error(error);
    return;
  }

  messages.value.push(
      {
        message : messageToSend.value,
        isChatbot : 0
      })

  if (isNextCommandTrasnferInfoRequest.value === 0)
    store.sendTextToCommand(messageToSend.value);
  else if (isNextCommandTrasnferInfoRequest.value === 1){
    store.sendTextToTransferInfo(messageToSend.value);
    isNextCommandTrasnferInfoRequest.value = 2;
  }
  else if (isNextCommandTrasnferInfoRequest.value === 2){
    store.sendIsPositiveAnswer(messageToSend.value);
    isNextCommandTrasnferInfoRequest.value = 0;
  }
  messageToSend.value = "";
}


const sendButtonClick = () => {
  messages.value.push(
      {
        message : textMessage.value,
        isChatbot : 0
      })

  if (isNextCommandTrasnferInfoRequest.value === 0)
    store.sendTextToCommand(textMessage.value);
  else if (isNextCommandTrasnferInfoRequest.value === 1){
    store.sendTextToTransferInfo(textMessage.value);
    isNextCommandTrasnferInfoRequest.value = 2;
  }
  else if (isNextCommandTrasnferInfoRequest.value === 2){
    store.sendIsPositiveAnswer(textMessage.value);
    isNextCommandTrasnferInfoRequest.value = 0;
  }
  textMessage.value = "";
}




watch(()=> store.getSpringResponse, async (command) => {
  switch (command.data.commandId) {
    case "1":
      messages.value.push(
          {
            message : "은행 이름과 계좌번호, 보낼 금액을 알려주세요.",
            isChatbot : 1
          }
      )
      isNextCommandTrasnferInfoRequest.value = 1;
      break;
    case "2"://잔액조회
      console.log("잔액조회로 이동합니다.");
      messages.value.push(
          {
            message : "잔액조회로 이동합니다.",
            isChatbot : 1
          }
      )
      setTimeout(() => {
        router.push({name : "Main"})
      }, 3000);
      break;
    case "3"://거래 내역 조회
      messages.value.push(
          {
            message : "거래 내역 조회로 이동합니다.",
            isChatbot : 1
          }
      )
      setTimeout(() => {
        router.push({name : "Transactinos"})
      }, 3000);
      break;
    case "4"://비대면 계좌 개설
      messages.value.push(
          {
            message : "비대면 계좌 개설로 이동합니다.",
            isChatbot : 1
          }

      )

      setTimeout(() => {
        router.push({name : "OpenAccount"})
      }, 3000);
      break;
    case "101":
      receiverBankCode.value = command.data.bankCode;
      receiverAccountNo.value = command.data.accountNumber;
      transferBalance.value = command.data.transferBalance;

      messages.value.push(
          {
            message :`${command.data.bankName} 은행의 ${command.data.accountNumber} 계좌로 ${command.data.transferBalance}원을 보내겠습니다. 맞나요?`,

            isChatbot : 1
          }
      )
      messages.value.push(
          {
            message : "계좌 입력 정보가 맞나요?",
            isChatbot : 1
          }
      )
      break;case "102":

      if (command.data.answer === "Yes") {
        messages.value.push(
            {
              message : "입금 중 . . .",
              isChatbot : 1
            }
        )
        cameraActivate.value = true;
      }
      else {
        messages.value.push(
            {
              message : "계좌이체가 취소되었습니다.",
              isChatbot : 1
            }
        );
      }
      break;
    default:
      messages.value.push(
        {
          message : "잘 모르겠어요 ㅠㅠ 다시 명령해주세요.",
          isChatbot : 1
        }
      )
      break;
  }
});
</script>

<template>
<div class="container">
  <div class="header">
    <p class="title1" @click="router.push('/main')">&lt;</p><p class="title1">챗봇</p>
  </div>

  <div v-if="cameraActivate" class="modal">
    <video ref="videoRef" autoplay style="display:none;"></video>
    <canvas ref="canvasRef" style="display:none;"></canvas>
    <!-- 여기 밑 클릭 이벤트에 카메라 활성화하는 함수 작성 -->
    <!-- 카메라 활성화하는 함수를 작성했을 때 cameraActivate.value = ture로 바꾸고 -->
    <!-- 얼굴 인식이 끝나면 cameraActivate.value = false로 바꿔야 함 -->
    <div @click="captureImageFilter">
      <img class="faceid" src="../assets/img/faceid.png" alt="얼굴 인식 버튼">
    </div>
  </div>

  <div class="content">
    <div class="chat-container">
      <ChatMessage v-for="message in messages"
      :key="message"
      :message="message"
      :isChatbot="message.isChatbot" />
    </div>

    <div class="input-text">
      <input class="input" type="text" name="textMessage" id="textMessage"
      v-model="textMessage" placeholder="옆 버튼을 눌러 음성으로 말하거나 보실 은행 업무를 입력해주세요.">

      <div v-if="textMessage">
        <div class="button" @click="sendButtonClick()">
          <img class="speech" src="../assets/img/send.png" alt="입력한 글자를 챗봇에게 보내는 버튼"> 
        </div>
      </div>
      <div v-else>
        <div class="button" @click="sendVoiceToCommand">
          <img class="speech" src="../assets/img/waves.png" alt="음성으로 말하기 위한 시작 버튼"> 
        </div>
      </div>
    </div>
  </div>
</div>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  background-color: #ffffff;
}

.header {
  display: flex;
  flex-direction: row;
}

.title1 {
  margin-left: 20px;
}

.content {
  display: flex;
  flex-direction: column;
  justify-content: flex-end; /* 이를 통해 chat-container가 아래쪽으로 정렬됩니다 */
  height: 100%; /* 콘텐츠 영역이 화면 전체 높이를 차지하도록 설정합니다 */
  background-color: #ffffff;
}

.chat-container {
  display: flex;
  flex-direction: column;
  overflow-y: auto;
  max-height: 100%; /* 입력란의 높이를 빼고 나머지 높이를 사용합니다 */
  width: 100%; /* 채팅 컨테이너의 너비를 화면 전체로 설정합니다 */
  padding: 10px; /* 내부 여백을 추가합니다 */
}

.button {
  background-color: #1B3C62;
  height: 35px;
  width: 35px;
  border-radius: 50%;
  text-align: center;
}

.speech {
  margin-top: 5px;
  width: 25px;
  height: 25px;
}

.content {
  background-color: #ffffff;
}

.input-text {
  position: relative; /* 입력란을 화면 하단에 고정합니다 */
  bottom: 0; /* 하단에서부터의 위치를 0으로 설정하여 바닥에 붙습니다 */
  left: 0; /* 왼쪽에서부터의 위치를 0으로 설정하여 왼쪽에 붙습니다 */
  width: 100%; /* 입력란의 너비를 화면 전체로 설정합니다 */
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
  padding: 10px; /* 내부 여백을 추가합니다 */
  background-color: #ffffff; /* 입력란의 배경색을 설정합니다 */
}


.input {
  width: 300px;
  height: 40px;
  border-radius: 10px;
  border: 1px solid #cbcbcb;
}

.modal {
  background-color: rgba(255, 255, 255, 0.5);
  width: 100vw;
  height: 100vh;
  z-index: 99;
}

.faceid {
  width: 100px;
  height: 100px;
}

</style>