<script setup>
banks = ['산업은행','기업은행', '한국은행', '국민은행']
</script>

<template>
  <div>
    <div v-for="bank in banks">
      <button>{{ bank }}</button>
    </div>
  </div>
</template>

<style scoped>

</style>