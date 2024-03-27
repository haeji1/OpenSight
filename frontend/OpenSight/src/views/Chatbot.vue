<script setup>
import { ref } from "vue"

import ChatMessage from '@/components/ChatMessage.vue'
import { useChatBotStore } from '@/stores/chatbot'

const store = useChatBotStore()

const textMessage = ref(null)

// test code
const messages = ref([
  { id: 1, text: '안녕하세요, 어떻게 도와드릴까요?', isUser: false },
  { id: 2, text: '안녕하세요, 어떻게 도와드릴까요?', isUser: true },
  { id: 3, text: '안녕하세요, 어떻게 도와드릴까요?', isUser: false },
  { id: 4, text: '안녕하세요, 어떻게 도와드릴까요?', isUser: true },
])

</script>

<template>

  <header>
    <p>&lt; 챗봇</p>
  </header>
  <div class="content">

    <div class="chat-container">
      <ChatMessage v-for="msg in messages"
      :key="msg.id"
      :message="msg"
      :isUser="msg.isUser" />
    </div>

    <div class="input-text">
      <input type="text" name="textMessage" id="textMessage"
      v-model="textMessage" placeholder="옆 버튼을 눌러 음성으로 말하거나 보실 은행 업무를 입력해주세요.">

      <div v-if="textMessage">
        <div class="button" @click="store.textCommand(textMessage)">
          <img class="speech" src="../assets/img/send.png" alt=""> 
        </div>
      </div>

      <div v-else>
        <div class="button" @click="store.micCommand">
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