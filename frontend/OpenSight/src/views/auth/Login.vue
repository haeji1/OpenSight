<template>
  <div>
    <button @click="redirectToOAuth">Login with OAuth Provider</button>
  </div>
</template>

<script setup>

function redirectToOAuth() {
  window.location.href = 'https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=4hSpDvA1GDlGXjqG6sac&redirect_uri=http://192.168.31.25:8080/login/oauth2/code/naver'
}
</script>
