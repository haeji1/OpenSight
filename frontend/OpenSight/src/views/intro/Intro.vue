<script setup>
import { ref } from 'vue';
import Section1 from './Section1.vue';
import Section2 from './Section2.vue';
import Section3 from './Section3.vue';
import Section4 from './Section4.vue';
import Section5 from './Section5.vue';


// 각 섹션 컴포넌트를 배열에 객체 형태로 참조합니다.
const sections = ref([
  { name: 'Section1', component: Section1 },
  { name: 'Section2', component: Section2 },
  { name: 'Section3', component: Section3 },
  { name: 'Section4', component: Section4 },
  { name: 'Section5', component: Section5 },
]);
</script>

<template>
  <div class="scroll-container">
    <component class="scroll-section" v-for="section in sections" :key="section.name" :is="section.component" />
  </div>
</template>


<style>
.scroll-container {
  height: 100vh;
  overflow-y: scroll;
  scroll-snap-type: y mandatory;
}


.scroll-section {
  height: 100vh;
  scroll-snap-align: start; /* 스크롤 섹션의 시작 부분에서 스냅 */
  display: flex;
  justify-content: center;
  align-items: center;
  /* 필요한 추가 스타일링 */
}
/* 스크롤 섹션의 시작 부분에서 스냅할 컴포넌트 스타일링 */
/* 컴포넌트별로 스타일을 설정할 경우, 각 컴포넌트 내부의 <style>에 작성합니다. */
</style>