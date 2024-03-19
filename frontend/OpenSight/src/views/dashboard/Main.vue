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

</script>

<template>
  <h1>hi</h1>
</template>