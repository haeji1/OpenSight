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
    axios({
      url: `http://127.0.0.1:8000/mic/`,
      method: "POST",
    })

    .then((res) => {
      django.value = res.data
    })

    .catch((err) => {
      console.log(err)
    })
  }

  const textCommand = (text) => {
    axios({
      url: `http://192.168.31.203:8000/text/`,
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

  const sendCommand = ({command_id, message, name, account, money, ischatbot}) => {
    axios({
      url: `/api/chatbot/request`,
      method: "POST",
      data: {
        command_id,
        message,
        name,
        account,
        money,
        ischatbot,
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
    sendCommand,
  }
})