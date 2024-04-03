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
    <p class="title1">환영합니다</p>
    <button class="button" @click="router.push('/main')">시작하기</button>
  </div>
</template>

<style scoped>
/* 컨테이너 스타일링 */
.container {
  display: flex; /* Flexbox 레이아웃 사용 */
  flex-direction: column; /* 자식 요소들을 세로로 배열 */
  justify-content: center; /* 중앙 정렬 (세로 방향) */
  align-items: center; /* 중앙 정렬 (가로 방향) */
  height: 100vh; /* 전체 뷰포트 높이 */
  text-align: center; /* 텍스트 중앙 정렬 */
}

/* 버튼 스타일링 */
.button {
  padding: 10px 20px; /* 버튼 안쪽 여백 */
  margin-top: 20px; /* 버튼 상단 여백 */
  border: none; /* 테두리 없음 */
  background-color: #ffffff; /* 배경 색상 */
  color: #1b3c62; /* 글자 색상 */
  font-size: 16px;
  font-weight: 600;
  cursor: pointer; /* 마우스 오버 시 커서 변경 */
  border-radius: 5px; /* 모서리 둥글게 */
}


.title1 {
  font-size: 24px;
  font-weight: 600;
  color: #ffffff;
  margin: 0;
}
</style>