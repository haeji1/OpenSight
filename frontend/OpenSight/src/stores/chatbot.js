import { ref, computed } from "vue"
import { defineStore } from "pinia";
import axios from "axios";

export const useChatBotStore = defineStore('chatBotStore', () => {
  // ======== STATE ========

  const django = ref([])
  const spring = ref([])

  // ======== GETTER ========

  const getDjango = computed(() => {
    return django.value
  })

  const getSpring = computed(() => {
    return spring.value
  })

  // ======== ACTION ========

  const micCommand = function () {

    // 1. 음성 캡처를 위한 마이크 권한 요청
    navigator.mediaDevices.getUserMedia({ audio : true })

    // 2-1. 마이크 권한 요청에 성공했을 경우 
    .then((res) => {
      const mediaRecorder = new MediaRecorder(res)
      let audioChunks = []

      mediaRecorder.ondataavailable = event => {
        audioChunks.push(event.data)
      }

      // 3.  5초 간 음성 녹음
      mediaRecorder.start()

      setTimeout(() => {
        mediaRecorder.stop()
      }, 5000)

      mediaRecorder.onstop = function () {
        const audioBlob = new Blob(audioChunks, { type : 'audio/wav' })
        const formData = new FormData()
        formData.append('audioFile', audioBlob, 'audio.wav')

        // 4. 만들어진 오디오 파일을 axios 통신으로 전송
        axios({
          url: `http://127.0.0.1:8000/mic/`,
          method: "POST",
          data: formData,
        }
        )

        // 5-1. 성공했을 경우
        .then((res) => {
          django.value = res.data
          console.log(django.value)
        })


        // 5-2. 실패했을 경우
        .catch((err) => {
          alert('오디오 파일 전송 중 오류가 발생했습니다. 다시 시도해주세요.')
          console.log(err)
        })
      }
    })

    // 2-2. 마이크 권한 요청에 실패했을 경우
    .catch((err) => {
      alert('마이크 권한 요청에 실패했습니다. 다시 시도해주세요.')
      console.log(err)
    })
  }

  const textCommand = (text) => {
    axios({
      url: `http://127.0.0.1:8000/text/`,
      method: "POST",
      data: {
        text
      },
    }
    )

    .then((res) => {
      django.value = res.data
      console.log(django.value)
    })

    .catch((err) => {
      console.log(err)
    })
  }

  const micCheckCommand = function () {
    // micCommand랑 동일하나 axios 주소가 mic_check로 감
  }

  const textCheckCommand = (text) => {
    axios({
      url: `http://127.0.0.1:8000/text_check/`,
      method: "POST",
      data: {
        text
      },
    }
    )

    .then((res) => {
      django.value = res.data
      console.log(django.value)
    })

    .catch((err) => {
      console.log(err)
    })
  }

  const sendCommand = ({commandId, message, bank, account, money, isChatbot}) => {

    const accessToken = sessionStorage.getItem('accessToken')

    console.log('!@#', commandId, message, bank, account, money, isChatbot, '!@#')

    axios({
      url: `http://localhost:8080/api/chatbot/request`,
      method: "POST",
      headers : {
        'Authorization': `${accessToken}`
      },
      data: {
        command_id : commandId,
        message : message,
        bank : bank,
        account : account,
        money : money,
        ischatbot : isChatbot,
      },
    }
    )

    .then((res) => {
      spring.value = res.data
      console.log(spring.value)
    })

    .catch((err) => {
      console.log(err)
    })
  }
  
  // ======== RETURN ========

  return {
    django,
    spring,
    getDjango,
    getSpring,
    micCommand,
    textCommand,
    micCheckCommand,
    textCheckCommand,
    sendCommand,
  }
})