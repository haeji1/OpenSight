<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useAccountStore } from '@/stores/account';
import { useSettingStore } from '@/stores/setting';

const router = useRouter()

const accountStore = useAccountStore();
const settingStore = useSettingStore();
const myAccountListCopy = ref([]);
const isDataLoaded = ref(false);
const currentIndex = ref(0);

const myAccountBalance = ref([]);


onMounted(async () => {
  try {
    console.log("=======시작======");
    await accountStore.inquireAccountList();
    isDataLoaded.value = true;
    // console.log("======="+accountStore.myAccountList);
  } catch (error) {
    console.error(error);
  }
});


// 이전 계정으로 이동
const prevAccount = () => {
  if (currentIndex.value > 0) {
    currentIndex.value--;
  }
};

// 다음 계정으로 이동
const nextAccount = () => {
  if (currentIndex.value < accountStore.myAccountList.length - 1) {
    currentIndex.value++;
  }
};

const selectaccount = function () {
  const account = {
    'accountNo': accountStore.myAccountList[currentIndex.value].accountNo,
    'bankCode': accountStore.myAccountList[currentIndex.value].bankCode
  }
  console.log(accountStore.myAccountList[currentIndex.value].accountNo);
  console.log(accountStore.myAccountList[currentIndex.value].bankCode);
  settingStore.AccountSetting(account)
}


</script>

<template>
  <div class="container">
    <div class="header">
      <p @click="router.push('/main')" class="title1">&lt;</p><p class="title1">주거래 계좌 설정</p>
    </div>
    <div class="content">
      <div class="change-account">
        <p class="prev-next" @click="prevAccount">&lt;</p>
        <div class="account-container">
          <div class="account">
            <div>
              <div class="account-content" v-if="isDataLoaded">
                <p class="title1 title1-account">{{accountStore.myAccountList[currentIndex].bankName}}</p>
                <p class="body3 body3-account">{{accountStore.myAccountList[currentIndex].accountName}}</p>
                <p class="body2 body2-account">계좌번호 {{accountStore.myAccountList[currentIndex].accountNo}}</p>
              </div>
            </div>
          </div>
          <button class="button" @click="selectaccount">이체하기</button>
        </div>
        <p class="prev-next" @click="nextAccount">&gt;</p>
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
  flex-direction: row;
  margin-left: 20px;
}

.content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
}

.button {
  margin-top: 100px;
  width: 327px;
  height: 50px; 
  font-size: 16px;
  font-weight: 500;
  background-color: #1B3C62;
  color: #ffffff;
  border-radius: 10px;
}

.account {
  background-image: url('src/assets/img/account.png');
  background-position: center;
  width: 360px;
  height: 250px;
  border-radius: 15px;
}

.account-content {
  display: flex;
  flex-direction: column;
  justify-content: left;
  text-align: left;
  margin-top: 10px;
  margin-left: 50px;
}

.account-container {
  border: 1px solid #EDEDED;
  border-radius: 15px;
  background-color: #ffffff;
  box-shadow: 0px 4px 30px rgba(54, 41, 183, 0.07);
}

.change-account {
  display: flex;
  flex-direction: row;
}

.prev-next {
  margin-top: 100px;
}
</style>