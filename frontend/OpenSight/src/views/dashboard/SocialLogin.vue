<script setup>
import { useRouter} from "vue-router";
import { onMounted } from 'vue';
const router  = useRouter()

function getQueryVariable(variable) {
  var query = window.location.search.substring(1);
  var vars = query.split('&');
  for (var i = 0; i < vars.length; i++) {
    var pair = vars[i].split('=');
    if (decodeURIComponent(pair[0]) == variable) {
      return decodeURIComponent(pair[1]);
    }
  }
  console.log('Query variable %s not found', variable);
}


//Extract tokens
var accessToken = getQueryVariable('accessToken');
var refreshToken = getQueryVariable('refreshToken');

// Check if tokens are found before saving
if (accessToken && refreshToken) {
  // Save tokens to localStorage
  sessionStorage.setItem('accessToken', accessToken);
  sessionStorage.setItem('refreshToken', refreshToken);
} else {
  console.log('Tokens not found in query string');
}
router.push('/main')

</script>

<template>
  <div class="container">
    <p>환영합니다</p>
    <button class="button" @click="router.push('/main')">시작하기</button>
  </div>
</template>

<style scoped>

</style>