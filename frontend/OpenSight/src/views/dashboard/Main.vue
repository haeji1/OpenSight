<script setup>
import { useRouter } from 'vue-router';
import { computed, onMounted } from 'vue';
import { useAccountStore } from '@/stores/account'
import { useAuthStore } from '@/stores/auth';
import { ref, watch } from 'vue';

const router = useRouter();
const authStore = useAuthStore();
const accountStore = useAccountStore();
const myAccountListCopy = ref([]);
const isDataLoaded = ref(false);
const currentIndex = ref(0);

const myAccountBalance = ref([]);
const checkNull = ref(false);
// 선택된 계정의 잔액을 조회하는 함수
async function fetchAccountBalance(index) {
  try {
    const account = accountStore.myAccountList[index];
    const isNull = accountStore.isNull;
    checkNull.value = accountStore.isNull;
    console.log(account)
    console.log("==================="+checkNull.value)
    if (checkNull.value === false) {
      await accountStore.inquireBalance(account.bankCode, account.accountNo);
    }
  } catch (error) {
    console.error("잔액 조회 중 오류 발생:", error);
  }
}

onMounted(async () => {
  try {
    console.log("=======시작======");
    await accountStore.inquireAccountList();
    await fetchAccountBalance(0); // 첫 번째 계정의 잔액 조회
    isDataLoaded.value = true;
    // console.log("======="+accountStore.myAccountList);
  } catch (error) {
    console.error(error);
  }
});


// currentIndex가 변경될 때마다 잔액을 다시 조회
watch(currentIndex, async (newIndex) => {
  accountStore.currentIndex.value = currentIndex.value;
  await fetchAccountBalance(newIndex);
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

function transferButtonClick() {
  
  console.log(accountStore.myAccountList[currentIndex.value].accountNo);
  console.log(accountStore.myAccountList[currentIndex.value].bankCode);

  accountStore.selectMyAccountNumber = accountStore.myAccountList[currentIndex.value].accountNo
  accountStore.selectedMyAccountBankCode = accountStore.myAccountList[currentIndex.value].bankCode

  router.push({name:'Transfer'})
}

function transactionButtonClick() {
  accountStore.currentAccount.value = accountStore.myAccountList[currentIndex.value]

  console.log('%$%$', accountStore.currentAccount.value, '%$%$')
  router.push({name:'Transactinos'})
}

function noContent() {
  alert('준비중입니다.')
}

const balanceString = ref('')

const getBalanceString = computed(() => {
  return balanceString.value
})

watch(() => accountStore.myAccountBalance, (balance) => {

  if (!balance) {
    const str = String(balance)
    let res = ''
    let count = 0

    for (let i = str.length - 1; i >= 0; i--) {
      res = str[i] + res
      count++

      if (count % 3 == 0 && i != 0) {
        res = ',' + res
      }
    }
    balanceString.value = res
  }
  
})

console.log('###', getBalanceString, '###')


const name = sessionStorage.getItem('username')
</script>

<template>
  <div class="container">
    <div class="header">
      <p class="title1-white">안녕하세요, {{name}}고객님.</p>
      <div class="img-container">
        <img class="img" src="../../assets/img/user.png" alt="프로필수정" @click="router.push('/profile/edit')">
        <img class="img" src="../../assets/img/setting.png" alt="간편비밀번호, 주거래계좌 설정" @click="router.push('/setting')">
        <img class="img" src="../../assets/img/signout.png" alt="로그아웃" @click="authStore.logout">
      </div>
    </div>
    <div class="content">
      <div class="change-account">
        <p class="prev-next" @click="prevAccount">&lt;</p>
        <div class="account-container">
          <div class="account">
            <div class="account-content" v-if = "checkNull">
              <p class="title1">계좌가 없습니다.</p>
            </div>
            <div v-else>
              <div class="account-content" v-if="isDataLoaded">
                <p class="title1 title1-account">{{accountStore.myAccountList[currentIndex].bankName}}</p>
                <p class="body3 body3-account">{{accountStore.myAccountList[currentIndex].accountName}}</p>
                <p class="body2 body2-account">계좌번호 {{accountStore.myAccountList[currentIndex].accountNo}}</p>
                <p class="title2 title2-account">잔액 {{getBalanceString}}원</p>
              </div>
            </div>
            </div>
          <button class="button" @click="transferButtonClick">이체하기</button>
          <button class="button" @click="transactionButtonClick">거래내역 조회하기</button>
        </div>
        <p class="prev-next" @click="nextAccount">&gt;</p>
      </div>
      <div class="account-open" @click="router.push('/account/open/select-bank')">
        <div class="account-text">
          <p class="title2">비대면 자유입출금</p>
          <p class="title2">계좌 개설</p>
        </div>
        <img class="content-img" src="../../assets/img/accountimage.png" alt="">
      </div>
      <div class="savings" @click="noContent">
        <p class="title2">예적금 상품추천</p>
        <img class="content-img" src="../../assets/img/savings.png" alt="">
      </div>
      <div class="chat-bot" @click="router.push('/chatbot')">
        <img class="chat-img" src="../../assets/img/whiterobot.png" alt="챗봇" @click="router.push('/chatbot2')">
      </div>
    </div>
  </div>

</template>

<style scoped>
.container {
  /* display: flex; */
  align-items: flex-start;
  justify-content: center;
  overflow: auto;
  top: 0;
  left: 0;
  position: fixed;
  width: 100vw;
  height: 100vh;
  background: #1B3C62;
  font-family: 'Poppins', sans-serif;
}

.content {
  background-color: #ffffff;
  border-radius: 20px 20px 0 0; /* 아래쪽 모서리는 둥글지 않음 */
  width: 370px; /* 가로 크기 유지 */
  padding: 20px;
  margin: 0 auto;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
  position: fixed; /* 화면에 고정 */
  top: 80px; /* 상단으로부터 130px 떨어진 위치에 배치 */
  left: 50%; /* 가운데 정렬을 위해 */
  transform: translateX(-50%); /* 가운데 정렬을 위해 */
  height: calc(100vh - 50px); /* 상단부터 130px 위치부터 하단까지의 높이 설정 */
  /* overflow: scroll;  */
  /* 나머지 스타일은 기존에 설정한 대로 유지 */
}

.header {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

.title1-white {
  font-size: 24px;
  font-weight: 600;
  color: #ffffff;
  margin-left: 20px;
}

.img {
  margin-top: 30px;
  margin-right: 30px;
  width: 24px;
  height: 24px;
}

.account-open {
  display: flex;
  flex-direction: row;
  justify-content: space-between;  
  text-align: center;
  align-items: center;
  border: 1px solid #EDEDED;
  border-radius: 15px;
  background-color: #ffffff;
  box-shadow: 0px 4px 30px rgba(54, 41, 183, 0.07);
  width: 270px;
  height: 80px;
  padding: 0px 50px;
  margin-bottom: 30px;
  margin-top: 30px;
}

.savings {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;  
  border: 1px solid #EDEDED;
  border-radius: 15px;
  background-color: #ffffff;
  box-shadow: 0px 4px 30px rgba(54, 41, 183, 0.07);
  width: 270px;
  height: 80px;
  padding: 0px 50px;
}

.account-text {
  display: flex;
  flex-direction: column;
  text-align: right;
}

.content-img {
  width: 50px;
  height: 50px;
}

.title2 {
  margin: 0;
}

.account {
  background-image: url('../../assets/img/account.png');
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

.title1-account, .title2-account, .body2-account, .body3-account {
  color: #ffffff;
  margin: 5px 0px;
}
.title1-account {
  margin-top: 50px;
}
.button {
  margin-bottom: 20px;
  width: 300px;
  height: 45px; 
  font-size: 16px;
  font-weight: 500;
  background-color: #1B3C62;
  color: #ffffff;
  border-radius: 10px;
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

.chat-bot {
  position: fixed;
  right: 40px;  /* 또는 원하는 간격으로 조정 */
  top: 580px; /* 또는 원하는 간격으로 조정 */
  width: 75px;  /* 아이콘의 크기에 맞게 조정 */
  height: 75px; /* 아이콘의 크기에 맞게 조정 */
  z-index: 1000; /* 다른 요소들 위에 떠 있게 하려면 충분히 큰 값 */
  background-color: #1B3C62;
  border-radius: 50%;
}

.chat-img {
  width: 65px;
  height: 65px;
}
</style>