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
    const selectMyAccountNumber = ref(null);
    const selectedMyAccountBankCode = ref(null);
    const transactionDate = ref(null);
    const currentAccount = ref({})
    
    const isNull = ref(false)

    const selectedBank = ref("asdf");
    const selectedBankName = ref("fdas");
    const accountNumber = ref("qwer");
    const amount = ref("sdfg");
    const recipientName = ref("ncg");
    const senderName = ref("fdgr");
    const currentIndex = ref(0);

    
    
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
            if (res.data.data == null) {
                isNull.value = true
                console.log("res.data.data의 값"+isNull.value)
            }
            else {
                for(let i = 0; i < res.data.data.rec.length; i++){
                    myAccountList.value.push(res.data.data.rec[i]);
                }
                console.log("+++++++++++"+myAccountList.value);
            }


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

    const accountTransfer = async function() {
        const depositBankCode = selectedBank.value;
        const depositAccountNo = accountNumber.value;
        const transactionBalance = amount.value;
        const withdrawalBankCode = selectedMyAccountBankCode.value;
        const withdrawalAccountNo = selectMyAccountNumber.value;
        const depositTransactionSummary = "출금합니다.";
        const withdrawalTransactionSummary = "입금합니다.";

        const accessToken = sessionStorage.getItem('accessToken');
        console.log("=====================accountTransfer========================");
        try {
            // axios 요청을 await를 사용하여 호출하고 결과를 res 변수에 저장
            const res = await axios({
                method: 'post',
                url: `${accountBaseURL}/account-transfer`,
                headers: {
                    'Authorization': accessToken // access token을 Authorization 헤더에 포함
                },
                data: {
                    "depositBankCode": depositBankCode,
                    "depositAccountNo": depositAccountNo,
                    "transactionBalance": transactionBalance,
                    "withdrawalBankCode": withdrawalBankCode,
                    "withdrawalAccountNo": withdrawalAccountNo,
                    "depositTransactionSummary": depositTransactionSummary,
                    "withdrawalTransactionSummary": withdrawalTransactionSummary
                }
            });
            console.log(res);
            return res; // 여기에서 axios 요청의 결과를 반환
        } catch (error) {
            console.log(error);
            return error; // 오류가 발생했다면 오류를 반환
        }
    }


    const checkSimplePassword = async function(simplePassword) {
        try {
            const accessToken = sessionStorage.getItem('accessToken');
            console.log("=====================checkSimplePassword========================")
            const response = await axios({
                method: 'post',
                url : `${import.meta.env.VITE_REST_API}/users/check/simple-password`,
                headers: {
                    'Authorization': `${accessToken}` // access token을 Authorization 헤더에 포함
                },
                data : {
                    "simplePassword" : simplePassword
                }
            });

            console.log("간편비밀번호는"+simplePassword)
            console.log(response)
            console.log(response.data.data.matched)
            return response.data.data.matched === true;
        } catch (error) {
            console.log(error)
            return false;
        }
    }


    const matchedBank = computed(() => matchBank(selectedBank.value));


    return {myAccountList, accountTypes,selectedAccountType,transactionDate,currentAccount,currentIndex,
      openAccountResult,selectedBank,myAccountBalance,accountNumber,selectMyAccountNumber,selectedMyAccountBankCode,
      amount,recipientName,senderName,selectedBankName, checkSimplePassword,
      inqureBankAccountListType,openAccount,inquireAccountList, inquireBalance,accountTransfer,isNull}
        ;
});