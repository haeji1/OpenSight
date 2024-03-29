<script setup>
import { defineProps } from 'vue';

const props = defineProps({
  message : Object
})
</script>

<template>
  <div class="chat-message" 
  :class="{'user-message': !message.isChatbot,
  'bot-message': message.isChatbot}">
    {{ message.message }}
  </div>
</template>

<style scoped>
.chat-message {
  padding: 10px;
  margin: 5px;
  border-radius: 10px;
  max-width: 80%;
}

.user-message {
  background-color: rgb(80, 80, 226);
  align-self: flex-end;
}

.bot-message {
  background-color: #ccc;
  align-self: flex-start;
}

/* 이 부분은 ChatMessage 컴포넌트가 포함된 부모 컴포넌트의 스타일에 포함시켜야 합니다. */
.chat-container {
  display: flex;
  flex-direction: column;
}
</style>
