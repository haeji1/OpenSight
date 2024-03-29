import { ref, computed } from 'vue';
import { defineStore } from 'pinia';import axios from 'axios';
import { useRouter } from 'vue-router';

export const useAccountStore = defineStore('AccountStore', () => {
    const accountBaseURL = "http://192.168.31.168:8080/api/accounts";


    const inqureBankAccountListType = function (){
        axios({
          method: 'post',
          url: `${accountBaseURL}/inquire-bank-account-list-type`,
          data: {
            }
        })
        .then((res) => {
          console.log(res)
          return res;
          })
        .catch((err) => {
          console.log(err)
        })
      }


    return {inqureBankAccountListType};
});