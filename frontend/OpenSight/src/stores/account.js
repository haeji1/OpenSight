import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import { useRouter } from 'vue-router';


export const useAccountStore = defineStore('AccountStore', () => {
    const accountBaseURL = "http://127.0.0.1:8080/api/accounts";
    
    const accountTypes = ref([]);
    const selectedAccountType = ref(null);
    const openAccountResult = ref(null);

    const inqureBankAccountListType = async function (){
        await axios({
          method: 'post',
          url : `${accountBaseURL}/inquire-bank-account-list-type`
        })
        .then((res) => {  
        //   console.log(res);
          for(let i = 0; i < res.data.data.rec.length; i++){
            accountTypes.value.push(res.data.data.rec[i]);
          }
          console.log("acciountType: ", accountTypes.value);
          })
        .catch((err) => {
          console.log(err)
        })
      }

<<<<<<< HEAD

    return {inqureBankAccountListType};
=======
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



    return {accountTypes,selectedAccountType,openAccountResult, inqureBankAccountListType,openAccount};
>>>>>>> 46796473d9bf6f29a60300546d3eb58f03c2d646
});