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
  background-color: #1b3c62;
  color: #ffffff;
  align-self: flex-end;
}

.bot-message {
  background-color: #DEEDFF;
  align-self: flex-start;
}


</style>
