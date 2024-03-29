import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import { useRouter } from 'vue-router';


export const useAccountStore = defineStore('AccountStore', () => {
    const accountBaseURL = `${import.meta.env.VITE_REST_API}/accounts`;
    
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
});