import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import { useRouter } from 'vue-router';

export const useAuthStore = defineStore('authStore', () => {
  // const API_URL = 'https://j10b104.p.ssafy.io:8080'
   const API_URL = 'http://192.168.31.168:8080'
  const router = useRouter()
  let id = ref(null)
  let name = ref(null)
  let email = ref(null)
  let accessToken = ref(null)
  let refreshToken = ref(null)

  const isLogin = computed(() => {
    if (accessToken.value === null && refreshToken.value === null) {
      return false
    } else {
      return true
    }
  })

  const setLogin = function (payload) {
    alert('로그인 되었습니다.');
    sessionStorage.setItem('userId', payload.data.id)
    sessionStorage.setItem('accessToken', payload.data.accessToken)
    sessionStorage.setItem('refreshToken', payload.data.refreshToken)
  }

  const register = function (payload) {
    const { email, password, username, phonenumber, userImage } = payload
    axios({
      method: 'post',
      url: `${API_URL}/api/users/register`,
      data: {
        code: 'register',
        email,
        password,
        username,
        phonenumber,
        userImage
      }
    })
    .then((res) => {
      console.log(res)
      router.push('/register/complete')
    })
    .catch((err) => {
      console.log(err)
    })
  }

  const login = function (payload) {
    axios({
      method: 'post', 
      url: `${API_URL}/api/users/login`,
      data: {
        code: 'login',
        userEmail : payload.username,
        userPassword : payload.password
      }
    })
    .then((res) => {
      console.log(res)

      
      sessionStorage.setItem('userId', res.data.data.id)
      sessionStorage.setItem('accessToken', res.data.data.accessToken)
      sessionStorage.setItem('refreshToken', res.data.data.refreshToken)
      router.push('/main')
    })
    .catch((err) => {console.log(err)})
  }

  const logout = function () {
    axios({
      method: 'post',
      url: `${API_URL}/api/users/logout`
    })
    .then((res) => {
      accessToken.value = null
      refreshToken.value = null
      sessionStorage.removeItem('userId')
      sessionStorage.removeItem('accessToken')
      sessionStorage.removeItem('refreshToken')
      alert('로그아웃 되었습니다.')
      router.push('/login')
    })
    .catch((err) => {console.log(err)})
  }

  const findEmail = function (payload) {
    const { phonenumber } = payload
    axios({
      method: 'post',
      url: `${API_URL}/api/users/find-id`,
      data: {
        code: 'findId',
        phonenumber
      }
      .then((res) => {
        console.log(res)
      })
      .catch((err) => {
        console.log(err)
      })
    })
  }

  const findPassword = function (payload) {
    const { email, phonenumber } = payload
    axios({
      method: 'post',
      url: `${API_URL}/api/users/find-pw`,
      data: {
        code: 'findpw',
        email,
        phonenumber
      }
    })
    .then((res) => {
      console.log(res)
    })
    .catch((err) => {
      console.log(err)
    })
  }

  return {
    accessToken, refreshToken, isLogin, register, login, logout, findEmail, findPassword
  }
})