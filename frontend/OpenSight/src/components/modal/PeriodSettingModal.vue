<script setup>
import { ref } from 'vue';
import { defineEmits } from 'vue';

const emit = defineEmits(['close', 'update'])

const startDate = ref('');
const endDate = ref('');

const submit = () => {
  emit('update', { startDate: startDate.value, endDate: endDate.value });
};

const close = () => {
  emit('close');
};
</script>

<template>
  <div class="modal">
    <div class="modal-content">
      <h3>조회기간 설정</h3>
      <input type="date" v-model="startDate" />
      <input type="date" v-model="endDate" />
      <button @click="submit">확인</button>
      <button @click="close">취소</button>
    </div>
  </div>
</template>

<style scoped>

</style>
