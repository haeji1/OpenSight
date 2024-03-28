import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import { useRouter } from 'vue-router';


export const useAccountStore = defineStore('AccountStore', () => {
    const accountBaseURL = "http://127.0.0.1:8080/api/accounts";

    /*

    성욱이 알아?

     */
    
    const accountTypes = ref([]);
    const selectedAccountType = ref(null);


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
        await axios({
          method: 'post',
          url : `${accountBaseURL}/open-account`,
          data : {
            accountType: selectedAccountType.value
          }
        })
      }



    return {accountTypes,selectedAccountType, inqureBankAccountListType};
});