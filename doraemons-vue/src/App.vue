<template>
  <RouterView/>
</template>

<script setup>

</script>

<style lang="scss">
* {
  margin: 0;
  padding: 0;
  border: 0;

  body {
    height: 100vh;
    background-image: url("@/assets/Doraemon.jpg");
    background-size: cover;
    font-family: 幼圆, sans-serif;
    overflow: hidden;
  }

  @font-face {
    font-family: "jam font";
    src: url("@/ttf/jamfont.ttf") format("truetype");
    font-weight: normal;
    font-style: normal;
  }
}

</style>
