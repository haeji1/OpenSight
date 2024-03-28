<script setup>
import { useRouter } from 'vue-router';
import { useAccountStore } from '@/stores/account';

const router = useRouter()


const AccountStore = useAccountStore();


const openAccount = () => {
  

  router.push('/account/open/complete')
  //router.push('/account/open/terms');
}



</script>

<template>
  <header>
    <p>&lt; 비대면계좌 개설</p>
    <p>{{AccountStore.selectedAccountType }}</p>
  </header>
  <div>
    <div>
      <p>이용약관</p>
    </div>
    <div>
      <button @click="router.push('/main')">취소</button>
      <button @click="openAccount">계좌 개설</button>
    </div>
  </div>
</template>

<style scoped>

</style>