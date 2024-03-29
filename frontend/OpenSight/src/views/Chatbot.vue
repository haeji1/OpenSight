<script setup>
import { ref, watch } from "vue"

import ChatMessage from '@/components/ChatMessage.vue'
import { useChatBotStore } from '@/stores/chatbot'

const store = useChatBotStore()

const textMessage = ref(null)

const messages = ref([
  {
    command_id : 999,
    message: '안녕하세요! 원하시는 업무를 말씀해주시거나 입력해주세요.',
    isChatbot: 1 },
])

const check = ref(null)

// 사용자가 요청할 때마다 messages에 새로운 메세지 push
watch(() => store.getDjango, (newMessage) => {

  // 계좌이체일 경우 분기처리를 위해 별도로 추가 정보 저장
  messages.value.push(
    {
      command_id : newMessage.command_id,
      message : newMessage.message,
      bank : newMessage.bank,
      account : newMessage.account,
      money : newMessage.money,
      isChatbot : 0
    }
  )

  // command_id에 따른 분기처리
  if (newMessage.command_id == 1) {

    // 모든 값이 존재한다면 이 정보가 맞는지 확인하는 메세지 전달
    if (newMessage.bank && newMessage.account && newMessage.money) {
      messages.value.push(
        {
          command_id : 999,
          message : '은행: ' + newMessage.bank + '\n' +
          '계좌번호: ' + newMessage.account + '\n' +
          '금액: ' + newMessage.money + '\n' +
          '다음과 같은 정보로 계좌이체를 하려고 합니다. 정보가 맞다면 "네", 아니라면 "아니오" 라고 말씀해주시거나 입력해주세요.',
          isChatbot : 1
        }
      )

      // 네, 아니오로 대답을 받도록 요청 유무를 구분
      check.value = 1
    }

    // 아니라면 다시 정보를 요청한다는 메세지 전달
    else {
      messages.value.push(
        {
          command_id : 999,
          message : '계좌이체를 할 정보가 부족합니다. 다시 한번 말씀해주세요.',
          isChatbot : 1
        }
      )
    }
  }

  else if (newMessage.command_id == 2 || newMessage.command_id == 3 || newMessage.command_id == 4) {

    const commandId = newMessage.command_id
    const message = newMessage.message
    const bank = newMessage.bank
    const account = newMessage.account
    const money = newMessage.money
    const isChatbot = 1

    store.sendCommand({
      commandId,
      message,
      bank,
      account,
      money,
      isChatbot,
    })

  }

  // 계좌이체 답변에 대한 분기처리
  // 만약 정보가 맞다면 이전 메세지에 접근하여 그 값을 spring에 전달
  else if (newMessage.command_id == 200) {

    // 이전 메세지에 접근하여 그 값을 spring으로 전달
    const lastMessage = messages.value[messages.value.length - 3]

    const commandId = 1
    const message = lastMessage.message
    const bank = lastMessage.bank
    const account = lastMessage.account
    const money = lastMessage.money
    const isChatbot = lastMessage.isChatbot

    store.sendCommand({
      commandId,
      message,
      bank,
      account,
      money,
      isChatbot,
    })
  }

  // 만약 정보가 다르다면 다시 계좌이체 정보를 요청
  else if (newMessage.command_id == 400) {
    messages.value.push(
      {
        command_id : 999,
        message : '처음 화면으로 다시 돌아갑니다. 원하시는 업무를 말씀해주시거나 입력해주세요.',
        isChatbot : 1
      }
    )
  }
})

// 챗봇이 답변할 때마다 messages에 새로운 메세지 push
watch(() => store.getSpring, (newMessage) => {

  // 요청에 따라 오는 응답이 다르므로 분기처리
  if (newMessage.command_id == 1) {
    messages.value.push(
      {
        // 완료된 계좌이체 페이지로 이동
      }
    )
  }

  else if (newMessage.command_id == 2) {
    messages.value.push(
      {
        command_id : newMessage.command_id,
        message : newMessage.text1 + newMessage.money + newMessage.text2,
        isChatbot : 1
      }
    )
  }

  else if (newMessage.command_id == 3) {

    // 가지고 오는 길이에 따라 최근 거래내역을 출력
    const historyNum = newMessage.len
    let history = ''

    for (let i = 1; i <= historyNum; i++) {

      if (i == historyNum) {
        history += `newMessage.history${i}`
      } else {
        history += `newMessage.history${i}` + '\n'
      }
    }

    messages.value.push(
      {
        command_id : newMessage.command_id,
        message : newMessage.text1 + '\n' + history + '\n' + newMessage.text2,
        isChatbot : 1
      }
    )
  }

  else if (newMessage.command_id == 4) {
    messages.value.push(
      {
        command_id : newMessage.command_id,
        message :
        newMessage.text1 + '\n' +
        newMessage.url + '\n' +
        newMessage.text2,
        isChatbot : 1
      }
    )
  }

  else {
    messages.value.push(
      {
        command_id : 0,
        message : newMessage.text,
        isChatbot : 1
      }
    )
  }
  }
)
</script>

<template>
  <header>
    <p>&lt; 챗봇</p>
  </header>
  <div class="content">
    <div class="chat-container">
      <ChatMessage v-for="message in messages"
      :key="message"
      :message="message"
      :isChatbot="message.isChatbot" />
    </div>

    <div class="input-text">
      <input type="text" name="textMessage" id="textMessage"
      v-model="textMessage" placeholder="옆 버튼을 눌러 음성으로 말하거나 보실 은행 업무를 입력해주세요.">

      <div v-if="textMessage">
        <div v-if="check" class="button" @click="store.textCheckCommand(textMessage)">
          <img class="speech" src="../assets/img/send.png" alt=""> 
        </div>

        <div v-else class="button" @click="store.textCommand(textMessage)">
          <img class="speech" src="../assets/img/send.png" alt=""> 
        </div>
      </div>

      <div v-else>
        <div v-if="check" class="button" @click="store.micCheckCommand">
          <img class="speech" src="../assets/img/waves.png" alt=""> 
        </div>

        <div v-else class="button" @click="store.micCommand">
          <img class="speech" src="../assets/img/waves.png" alt=""> 
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.chat-container {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  overflow-y: auto;
  max-height: 600px; /* 원하는 높이에 맞추어 조정 */
}

.button {
  background-color: #1B3C62;
  height: 35px;
  width: 35px;
  border-radius: 50%;
}

.speech {
  width: 30px;
  height: 30px;
}

.content {
  background-color: #ffffff;
}
</style>