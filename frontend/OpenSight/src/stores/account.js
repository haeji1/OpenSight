import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import { useRouter } from 'vue-router';


export const useAccountStore = defineStore('AccountStore', () => {
    const accountBaseURL = `${import.meta.env.VITE_REST_API}/accounts`;
    
    const accountTypes = ref([]);
    const selectedAccountType = ref(null);
    const openAccountResult = ref(null);
    const myAccountList = ref([]);
    const myAccountBalance = ref(null);

    const selectedBank = ref();
    const selectedBankName = ref();
    const accountNumber = ref();
    const amount = ref();
    const recipientName = ref();
    const senderName = ref();

    const inqureBankAccountListType = async function (){
        await axios({
          method: 'post',
          url : `${accountBaseURL}/inquire-bank-account-list-type`
        })
        .then((res) => {  
           console.log(res);
          for(let i = 0; i < res.data.data.rec.length; i++){
            accountTypes.value.push(res.data.data.rec[i]);
          }
            console.log("accountType: ", accountTypes.value);

          })
        .catch((err) => {
          console.log(err)
        })
      }


      const openAccount = async function(){
        const accessToken = sessionStorage.getItem('accessToken');
        
        await axios({
          method: 'post',
          url : `${accountBaseURL}/open-account`,
          headers: {
            'Authorization': `${accessToken}` // access token을 Authorization 헤더에 포함
          },
          data : {
            accountTypeUniqueNo: selectedAccountType.value.accountTypeUniqueNo
          }
        }) .then((res) => {  
            openAccountResult.value = res.data.data;
            console.log(res);
        })
      }

      const inquireAccountList = async function() {
        const accessToken = sessionStorage.getItem('accessToken');
        console.log("inquireAccountList========================")
        await axios({
          method: 'post',
          url : `${accountBaseURL}/inquire-account-list`,
          headers: {
            'Authorization': `${accessToken}` // access token을 Authorization 헤더에 포함
          },
          data : {
          }
        }) .then((res) => {
            console.log(res);

            for(let i = 0; i < res.data.data.rec.length; i++){
                myAccountList.value.push(res.data.data.rec[i]);
            }
            console.log("+++++++++++"+myAccountList.value);
        }) .catch((error) =>  {
          console.log(error)
        })

      }

      const inquireBalance = async function(bankCode, accountNo) {
          const accessToken = sessionStorage.getItem('accessToken');
          console.log("=====================inquireAccountBalance========================")
          await axios({
              method: 'post',
              url : `${accountBaseURL}/inquire-account-balance`,
              headers: {
                  'Authorization': `${accessToken}` // access token을 Authorization 헤더에 포함
              },
              data : {
                  "bankCode" : bankCode,
                  "accountNo" : accountNo
              }
          }) .then((res) => {
              console.log(bankCode, accountNo)
              console.log(res);
              myAccountBalance.value = res.data.data.rec.accountBalance;
          }) .catch((error) =>  {
              console.log(error)
          })

      }



    const matchedBank = computed(() => matchBank(selectedBank.value));


    return {myAccountList, accountTypes,selectedAccountType,openAccountResult,selectedBank,myAccountBalance,accountNumber,amount,recipientName,senderName,selectedBankName, inqureBankAccountListType,openAccount,inquireAccountList, inquireBalance};
});