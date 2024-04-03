import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import { useRouter } from 'vue-router';

export const useSettingStore = defineStore('SettingStore', () => {
  const settingBaseURL = `${import.meta.env.VITE_REST_API}`
  const router = useRouter()

  const PasswordSetting = async function (simplePassword) {
    try {
      const accessToken = sessionStorage.getItem('accessToken')
      await axios({
        method: 'post',
        url: `${settingBaseURL}/register/account`,
        headers: {
          'Authorization': `${accessToken}`
        },
        data: {
          'simplePassword': simplePassword
        }
      })
      console.log(res)
      alert('완료되었습니다.')
      router.push('/main')
    } catch (error) {
      console.log(error)
    }
  }

  const AccountSetting = async function (account) {
    try {
      const accessToken = sessionStorage.getItem('accessToken')
      await axios({
        method: 'post',
        url: `${settingBaseURL}/register/account`,
        headers: {
          'Authorization': `${accessToken}`
        },
        data: {
          'accountNo': account.accountNo,
          'bankCode': account.bankCode
        }
      })
      console.log(res)
      alert('완료되었습니다.')
      router.push('/main')
    } catch (error) {
      console.log(error)
    }
  }

  return {PasswordSetting, AccountSetting}
})

