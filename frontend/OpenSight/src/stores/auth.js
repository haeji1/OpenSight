import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import { useRouter } from 'vue-router';

export const useAuthStore = defineStore('authStore', () => {
  // const API_URL = 'https://j10b104.p.ssafy.io:8080'
  // const API_URL = 'http://192.168.31.168:8080'
  const API_URL = 'https://j10b104.p.ssafy.io/api'
  const router = useRouter()
  const id = ref(null)
  const name = ref(null)
  const email = ref(null)
  const accessToken = ref(null)
  const refreshToken = ref(null)

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

  const updatePassword = function (payload) {
    console.log("in auth.js");
    console.log(payload);

    const { password }= payload

    const accessToken = sessionStorage.getItem('accessToken');
    console.log("================"+accessToken)

    // access token이 존재하는지 확인하고, 존재하면 HTTP 요청 헤더에 포함하여 요청 보냄
    if (accessToken) {
      axios({
        method:'post',
        url:`${API_URL}/api/users/update-pw`,
        headers: {
          'Authorization': `${accessToken}` // access token을 Authorization 헤더에 포함
        },
        data: {
          code: 'updatepw',
          password
        }
      })
          .then((res) => {
            console.log("========비밀번호 변경 성공======="+res)
          })
          .catch((err) => {
            console.log(err)
          });
    } else {
      console.log(sessionStorage.getItem("accessToken"))
      console.log('Access token이 없습니다.');
      // access token이 없는 경우에 대한 처리
    }
  }

  return {
    accessToken, refreshToken, isLogin, register, login, logout, findEmail, findPassword,updatePassword,name
  }
})