<template>
  <div class="header">
    <div id="links">
      <svg aria-hidden="true" class="icon">
        <use xlink:href="#icon-duolaAmeng"></use>
      </svg>
      <RouterLink :class="['link', { active: isActive('/') }]" to="/">
        <span class="link">Doraemons(个人学习)</span>
      </RouterLink>
      <RouterLink :class="['link', { active: isActive('/charactintro') }]" to="/charactintro">人物介绍</RouterLink>
      <RouterLink :class="['link', { active: isActive('/authorintro') }]" to="/authorintro">作者简介</RouterLink>
      <RouterLink :class="['link', { active: isActive('/page2') }]" to="/page2">页面2</RouterLink>
      <RouterLink :class="['link', { active: isActive('/page3') }]" to="/page3">页面3</RouterLink>
    </div>

    <div id="user">
      <RouterLink :to="{ path: '/login' }" class="router-link">当前用户：{{ username }}</RouterLink>
      <svg aria-hidden="true" class="icon">
        <use xlink:href="#icon-duolaAmeng"></use>
      </svg>
    </div>
  </div>
</template>

<script setup>
import {ref} from "vue";
import {useRoute} from 'vue-router';

const route = useRoute();

// 判断当前路由是否匹配指定路径
function isActive(path) {
  return route.path === path;
}

const username = ref('游客001')

if (localStorage.getItem('username')) {
  username.value = localStorage.getItem('username')
}
</script>

<style lang="scss" scoped>
.header {
  position: fixed;
  width: 100%;
  display: flex;
  height: 30px;
  background-color: #ffffff90;
  padding: 2px;
  justify-content: space-between;
  border-bottom: 1px solid #000;
  z-index: 99999999;

  .icon {
    font-size: 2em;
  }

  #links {
    display: flex;
    align-items: center;
    margin-left: 20px;

    .link {
      text-decoration: none;
      color: #333;
      margin-right: 15px;
    }

    .active {
      padding: 7px;
      border-radius: 15px;
      background-color: white; /* 设置背景颜色为白色 */
    }
  }

  #user {
    display: flex;
    align-items: center;
    margin-right: 25px;

    .router-link {
      text-decoration: none;
      color: inherit;
      margin-right: 15px;
    }
  }
}
</style>

