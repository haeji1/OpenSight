import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import { useRouter } from 'vue-router';

export const useAuthStore = defineStore('authStore', () => {
  const router = useRouter()
  let accessToken = ref(null)
  let refreshToken = ref(null)

  const isLogin = computed(() => {
    if (accessToken.value === null && refreshToken.value === null) {
      return false
    } else {
      return true
    }
  })

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
    const { email, password } = payload
    axios({
      method: 'post',
      url: `${API_URL}/api/users/login`,
      data: {
        code: 'login',
        email,
        password
      }
    })
    .then((res) => {
      if (res.data.result === "success") {
        accessToken = res.data.acctoken
        refreshToken = res.data.responsetoken
        sessionStorage.setItem('userId', res.data.userId)
        sessionStorage.setItem('accessToken', res.data.acctoken)
        sessionStorage.setItem('refreshToken', res.data.responsetoken)
        router.push('/main')
      } else {
        console.log('Login failed:', res.data)
      }
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