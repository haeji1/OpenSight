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

    // 1-1. 음성 인식 권한이 있을 경우 실행
    if ("webkitSpeechRecognition" in window) {

      // 2. 10초 간 녹음 후 자동으로 종료
      const speech = new webkitSpeechRecognition

      alert('10초 동안 음성 인식 기능이 활성화됩니다. 원하시는 업무를 말씀해주세요. 음성은 10초 후 자동으로 종료됩니다.')
      speech.start()
      console.log('녹음 시작')
      
      setTimeout(() => {
        speech.stop()
        alert('10초가 경과하여 음성 인식 기능이 종료되었습니다. 알림을 닫고 화면으로 돌아가주세요.')
        console.log('녹음 종료')
      }, 10000)

      // 3. 녹음된 음성을 텍스트로 변환
      speech.addEventListener('result', (event) => {

        const text = event['results'][0][0]['transcript']

        // 4-1. 녹음이 정상적으로 되었을 경우 변환된 텍스트를 django로 전달
        if (text) {
          axios({
            url: `http://175.209.203.185:7979/command/`,
            method: "POST",
            data: {
              text
            },
          })

          .then((res) => {
            django.value = res.data
            console.log(django.value)
          })

          .catch((err) => {
            console.log(err)
          })
        }

        // 4-2. 녹음이 제대로 되지 않았을 경우 다시 시도해달라는 메세지 전달
        else {
          console.log('녹음 실패')
          alert('음성 인식이 제대로 되지 않았습니다.\n조용한 환경에서 다시 시도해주시거나 큰소리로 말해주세요.')
        }

      })
    }

    // 1-2. 만약 권한이 없을 경우 음성 인식 종료    
    else {
      alert('음성 인식 기능이 지원되지 않은 브라우저입니다.\n다른 브라우저로 접근해주세요.')
    }
  }

  const textCommand = (text) => {
    axios({
      url: `http://175.209.203.185:7979/text/`,
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

    // 1-1. 음성 인식 권한이 있을 경우 실행
    if ("webkitSpeechRecognition" in window) {

      // 2. 10초 간 녹음 후 자동으로 종료
      const speech = new webkitSpeechRecognition

      speech.start()
      console.log('녹음 시작')
      
      setTimeout(() => {
        speech.stop()
        console.log('녹음 종료')
      }, 10000)

      // 3. 녹음된 음성을 텍스트로 변환
      speech.addEventListener('result', (event) => {

        const text = event['results'][0][0]['transcript']

        // 4-1. 녹음이 정상적으로 되었을 경우 변환된 텍스트를 django로 전달
        if (text) {
          axios({
            url: `http://175.209.203.185:7979/check/`,
            method: "POST",
            data: {
              text
            },
          })

          .then((res) => {
            django.value = res.data
            console.log(django.value)
          })

          .catch((err) => {
            console.log(err)
          })

        }

        // 4-2. 녹음이 제대로 되지 않았을 경우 다시 시도해달라는 메세지 전달
        else {
          console.log('녹음 실패')
          alert('음성 인식이 제대로 되지 않았습니다.\n조용한 환경에서 다시 시도해주시거나 큰소리로 말해주세요.')
        }

      })
    }

    // 1-2. 만약 권한이 없을 경우 음성 인식 종료    
    else {
      alert('음성 인식 기능이 지원되지 않은 브라우저입니다.\n다른 브라우저로 접근해주세요.')
    }
  }

  const textCheckCommand = (text) => {
    axios({
      url: `http://175.209.203.185:7979/check/`,
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