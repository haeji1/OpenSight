<script setup>
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

// Extract tokens
var accessToken = getQueryVariable('accessToken');
var refreshToken = getQueryVariable('refreshToken');

// Check if tokens are found before saving
if (accessToken && refreshToken) {
    // Save tokens to localStorage
    localStorage.setItem('accessToken', accessToken);
    localStorage.setItem('refreshToken', refreshToken);
} else {
    console.log('Tokens not found in query string');
}
// function parseJwt(token) {
//     var base64Url = token.split('.')[1];
//     var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
//     var jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
//       return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
//     }).join(''));
//     return JSON.parse(jsonPayload);
// };

// const token = localStorage.getItem('accessToken')

// const headers = {
//   'Authorization': `Bearer ${token}`,
//   'Content-Type': 'application/json'
// }

// console.log(headers)

// console.log(parseJwt(localStorage.getItem('accessToken')))
</script>

<template>
  <h1>hi</h1>
</template>