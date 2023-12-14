<template>
  <div class="home">
    <a v-for="(item, index) in ballList" :key="index" :href="item.href" class="ball">
      <div :class="'ball-' + (index + 1)">
        <img :alt="'旋转图片' + (index + 1)" :src="imagePaths.paths[index]" class="ball-img"/>
        <span class="ball-text">{{ item.text }}</span>
      </div>
    </a>
    <div id="hello">
      <img alt="" src="../assets/cloud.png"/>
      <span>
        欢迎用户
        <router-link style="color: white" to="/login">{{ username }}</router-link>
        <br>来到Doraemons
        <br>个人学习站
      </span>
    </div>
  </div>
</template>

<script setup>
import {onMounted, reactive, ref} from "vue";

const username = ref('游客001')

if (localStorage.getItem('username')) {
  username.value = localStorage.getItem('username')
}

const ballList = [
  {text: "点我去登录", href: "/login"},
  {text: "点我去搜索", href: ""},
  {text: "点我去学习", href: "/charactintro"},
  {text: "点我去睡觉", href: ""},
  {text: "点我去打游戏", href: ""},
  {text: "点我去看电视", href: ""},
];

const imagePaths = reactive({
  paths: [],
});

onMounted(async () => {
  for (let i = 1; i <= ballList.length; i++) {
    const imagePath = await import(`@/assets/home/${i}.jpeg`);
    imagePaths.paths.push(imagePath.default);
  }
});

//设置组件碰撞反弹
onMounted(() => {
  const ball = document.querySelectorAll(".ball");
  let divPositions = [];

  // 初始化div的位置和速度
  ball.forEach((div) => {
    let overlapping = true;
    let x, y;

    while (overlapping) {
      overlapping = false;
      //给每个ball组件在窗口内生成随机位置
      x = Math.random() * (window.innerWidth - div.offsetWidth);
      y = Math.random() * (window.innerHeight - div.offsetHeight);
      //检查是否有ball位置重叠
      for (let i = 0; i < divPositions.length; i++) {
        if (
            Math.sqrt(
                Math.pow(x - divPositions[i].x, 2) +
                Math.pow(y - divPositions[i].y, 2),
            ) < div.offsetWidth
        ) {
          overlapping = true;
          break;
        }
      }
    }
    //设置ball在水平和竖直方向上的的初始速度
    let vx = Math.random() * 4 - 2;
    let vy = Math.random() * 3 - 2;
    divPositions.push({div, x, y, vx, vy});
  });

  function updatePositions() {
    // 更新每个div的位置
    divPositions.forEach((pos1, speed) => {
      pos1.x += pos1.vx;
      pos1.y += pos1.vy;

      // 碰撞检测和反弹
      divPositions.forEach((pos2, speed2) => {
        if (speed !== speed2) {
          const dx = pos1.x - pos2.x;
          const dy = pos1.y - pos2.y;
          const distance = Math.sqrt(dx * dx + dy * dy);
          const minDistance =
              pos1.div.offsetWidth / 2 + pos2.div.offsetWidth / 2;
          if (distance < minDistance) {
            // 发生碰撞，计算法向量
            const nx = dx / distance;
            const ny = dy / distance;
            const overlap = minDistance - distance;
            // 调整位置和速度
            pos1.x += overlap * nx * 0.5;
            pos1.y += overlap * ny * 0.5;
            pos2.x -= overlap * nx * 0.5;
            pos2.y -= overlap * ny * 0.5;
            const dvx = pos1.vx - pos2.vx;
            const dvy = pos1.vy - pos2.vy;
            const dotProduct = dvx * nx + dvy * ny;
            // 更新速度
            pos1.vx -= dotProduct * nx;
            pos1.vy -= dotProduct * ny;
            pos2.vx += dotProduct * nx;
            pos2.vy += dotProduct * ny;
          }
        }
      });

      // 碰到窗口边框时进行反弹
      if (pos1.x < 0 || pos1.x + pos1.div.offsetWidth > window.innerWidth) {
        pos1.vx = -pos1.vx;
      }
      if (pos1.y < 0 || pos1.y + pos1.div.offsetHeight > window.innerHeight) {
        pos1.vy = -pos1.vy;
      }

      // 设置div的新位置
      pos1.div.style.left = pos1.x + "px";
      pos1.div.style.top = pos1.y + "px";
    });

    // 在下一帧更新位置
    requestAnimationFrame(updatePositions);
  }

  // 开始更新位置
  updatePositions();

  const windowWidth = window.innerWidth;
  let hello = document.querySelector("#hello img");
  let text = document.querySelector("#hello span");
  hello.style.width = (500 * windowWidth) / 1440 + "px";
  text.style.fontSize = (45 * windowWidth) / 1440 + "px";
});
</script>

<style lang="scss" scoped>
body {
  overflow: hidden;

  .home {
    position: absolute;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 2;

    .ball {
      position: absolute;
      display: flex;
      justify-content: center;
      align-items: center;
      z-index: 999;

      div {
        width: 120px;
        height: 120px;

        .ball-img {
          position: absolute;
          width: 120px;
          height: 120px;
          border-radius: 50%;
        }

        span {
          position: absolute;
          width: 100%;
          height: 100%;
          text-align: center;
          left: 60px;
          top: -20px;
          color: white;
          font-size: 15px;
          font-weight: bold;
          text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
          transform: translateX(-50%);
        }
      }
    }
  }
}


@font-face {
  font-family: "jam font";
  src: url("@/ttf/jamfont.ttf") format("truetype");
  font-weight: normal;
  font-style: normal;
}

#hello {
  position: absolute;
  z-index: 1;
  top: 20px;
  left: 20px;
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  img {
    width: 525px !important;
  }

  span {
    position: absolute;
    top: 55%;
    left: 45%;
    transform: translate(-50%, -50%);
    font-weight: bold;
    font-family: "jam font", serif;
    font-size: 37px !important;
    color: rgba(243, 247, 245, 1);
  }
}

</style>

