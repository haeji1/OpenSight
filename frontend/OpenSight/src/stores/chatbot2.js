import { ref, computed } from "vue"
import { defineStore } from "pinia";
import axios from "axios";

export const useChatBotStore2 = defineStore('chatBotStore2', () => {
  // ======== STATE ========

  const springResponse = ref(null)

  // ======== GETTER ========

  const getSpringResponse = computed(() => {
    return springResponse.value
  })

  // ======== ACTION ========


  // const micCommand2 = async function () {
  //   // 1-1. 음성 인식 권한이 있을 경우 실행
  //   if ("webkitSpeechRecognition" in window) {
  //
  //     // 2. 10초 간 녹음 후 자동으로 종료
  //     const speech = new webkitSpeechRecognition
  //
  //     speech.start()
  //     console.log('녹음 시작')
  //
  //     setTimeout(() => {
  //       speech.stop()
  //       console.log('녹음 종료')
  //     }, 10000)
  //
  //     // 3. 녹음된 음성을 텍스트로 변환
  //      await speech.addEventListener('result', (event) => {
  //
  //       const text = event['results'][0][0]['transcript']
  //
  //        console.log(text);
  //       // 4-1. 녹음이 정상적으로 되었을 경우 변환된 텍스트를 django로 전달
  //       if (text) {
  //         return text;
  //       }
  //       // 4-2. 녹음이 제대로 되지 않았을 경우 다시 시도해달 라는 메세지 전달
  //       else {
  //         console.log('녹음 실패')
  //         alert('음성 인식이 제대로 되지 않았습니다.\n조용한 환경에서 다시 시도해주시거나 큰소리로 말해주세요.')
  //         return null;
  //       }
  //     })
  //   }
  //   // 1-2. 만약 권한이 없을 경우 음성 인식 종료
  //   else {
  //     alert('음성 인식 기능이 지원되지 않은 브라우저입니다.\n다른 브라우저로 접근해주세요.')
  //   }
  //   return null;
  // }

  const micCommand2 =  ()=> {
    return new Promise((resolve, reject) => {
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

          console.log(text);
          if (text) {
            resolve(text);
          }
          else {
            console.log('녹음 실패')
            alert('음성 인식이 제대로 되지 않았습니다.\n조용한 환경에서 다시 시도해주시거나 큰소리로 말해주세요.')
            reject('녹음 실패');
          }
        })
      }
      // 1-2. 만약 권한이 없을 경우 음성 인식 종료
      else {
        alert('음성 인식 기능이 지원되지 않은 브라우저입니다.\n다른 브라우저로 접근해주세요.')
        reject('음성 인식 기능이 지원되지 않음');
      }
    });
  }



  /**
   * 첫번쨰로 명령 받아오기
   * @param {*} param0 
   */
  const sendTextToCommand = (text) => {
    axios({
        url: `${import.meta.env.VITE_REST_API}/chatbot/gpt/request`,
        method: "POST",
        data: {
          text : text
        },
      })
      .then((res) => {
        springResponse.value = res.data
        console.log(res)
        console.log(springResponse.value)
      })
      .catch((err) => {
        console.log(err)
        console.log(text)
      })
    }

  const sendTextToTransferInfo = (text) => {
    axios({
      url: `${import.meta.env.VITE_REST_API}/chatbot/gpt/transfer-info/request`,
      method: "POST",
      data: {
        text : text
      },
    })
        .then((res) => {
          springResponse.value = res.data
          console.log(res)
          console.log(springResponse.value)
        })
        .catch((err) => {
          console.log(err)
          console.log(text)
        })
  }

  const sendIsPositiveAnswer = (text) => {
    axios({
      url: `${import.meta.env.VITE_REST_API}/chatbot/gpt/check/answer`,
      method: "POST",
      data: {
        text : text
      },
    })
        .then((res) => {
          springResponse.value = res.data
          console.log(res)
          console.log(springResponse.value)
        })
        .catch((err) => {
          console.log(err)
          console.log(text)
        })
  }

  // ======== RETURN ========

    return {
    sendTextToCommand,
    getSpringResponse, sendTextToTransferInfo,sendIsPositiveAnswer,micCommand2,
    }
})