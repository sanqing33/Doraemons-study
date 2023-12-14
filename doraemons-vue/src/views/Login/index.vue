<template>
  <div id="index">
    <img id="head" alt="Hello Doraemon" src="@/assets/Doraemon.png">

    <div id="login" :style="{ zIndex: alert.loginzIndex }">
      <div :class="{ loginflip: alert.isFlipped }" :style="{ zIndex: alert.loginzIndex }" class="login">
        <h2>登录</h2>
        <form @submit.prevent="submitFormLogin">
          <div class="form-group">
            <label for="username">用户名:</label>
            <input id="username" v-model="input.username" autocomplete="off" name="username" type="text">
          </div>
          <div class="form-group">
            <label for="password">密码:</label>
            <input id="password" v-model="input.password" name="password" type="password">
          </div>
          <div class="form-group">
            <label for="captcha">验证码:</label>
            <input id="captcha" ref="Captcha_width" v-model="input.captcha" type="text">
            <div>
              <canvas id="Captcha" ref="captchaCanvas" height="50" width="150" @click="refreshCaptcha"></canvas>
            </div>
          </div>
          <div class="form-group button-group">
            <button type="submit">登录</button>
            <button id="loginBtn" type="button" @click="register">注册</button>
          </div>
          <router-link style="text-decoration: none;font-size: 15px" to="/">暂不登录\注册，返回...</router-link>
        </form>
      </div>
      <div :class="{ loginimgflip: alert.isFlipped }" class="login-img">
        <img alt="Login Image" src="@/assets/Doraemon/login.png">
      </div>
    </div>

    <div id="register" :style="{ zIndex: alert.registerzIndex }">
      <div :class="{ registerimgflip: alert.isFlipped2 }" class="register-img">
        <img alt="Register Image" src="@/assets/Doraemon/register.png">
      </div>
      <div :class="{ registerflip: alert.isFlipped2 }" :style="{ zIndex: alert.registerzIndex }" class="register">
        <h2>注册</h2>
        <form @submit.prevent="submitFormRegister">
          <div class="form-group">
            <label for="username">用户名:</label>
            <input id="username" v-model="input.username" name="username" type="text">
          </div>
          <div class="form-group">
            <label for="password">密码:</label>
            <input id="password" v-model="input.password" name="password" type="password">
          </div>
          <div class="form-group">
            <label for="confirmPassword">确认密码:</label>
            <input id="confirmPassword" v-model="input.confirmPassword" name="confirmPassword" type="password">
          </div>
          <div class="form-group button-group">
            <button type="submit">注册</button>
            <button id="registerBtn" type="button" @click="back">返回</button>
          </div>
          <router-link style="text-decoration: none;font-size: 15px" to="/">暂不登录\注册，返回...</router-link>
        </form>
      </div>
    </div>

    <div v-if="alert.input_alert || alert.error_alert || alert.login_alert || alert.register_alert" class="alert">
      <h1 v-if="alert.input_alert">请输入<br>{{ alert.message }}</h1>
      <h1 v-else-if="alert.error_alert">{{ alert.message }}错误<br>请重新输入</h1>
      <h1 v-else-if="alert.login_alert">登录成功<br>欢迎用户{{ alert.message }}</h1>
      <h1 v-else-if="alert.register_alert">注册成功</h1>
      <button @click="close">返回</button>
    </div>

  </div>
</template>

<script setup>
import {onMounted, reactive, ref} from 'vue';
import router from '@/router/index.js';

const input = {
  username: 'sqing',
  password: 'sqing',
  captcha: '',
  confirmPassword: ''
};

const user = {
  name: '三青',
  username: 'sqing',
  password: 'sqing',
};

const alert = reactive({
  loginzIndex: 2,
  registerzIndex: 1,
  input_alert: false,
  error_alert: false,
  login_alert: false,
  register_alert: false,
  message: '',
  isFlipped: false,
  isFlipped2: true,
})

//生成验证码
const captchaCanvas = ref(null);
let captchaValue = '';

function refreshCaptcha() {
  const canvas = captchaCanvas.value;
  const ctx = canvas.getContext('2d');
  // 清空画布
  ctx.clearRect(0, 0, canvas.width, canvas.height);
  // 生成新的验证码
  captchaValue = generateCaptcha();
  // 绘制背景
  ctx.fillStyle = '#f5f5f5';
  ctx.fillRect(0, 0, canvas.width, canvas.height);
  // 绘制干扰线
  for (let i = 0; i < 6; i++) {
    ctx.strokeStyle = randomColor();
    ctx.beginPath();
    ctx.moveTo(randomNum(0, canvas.width), randomNum(0, canvas.height));
    ctx.lineTo(randomNum(0, canvas.width), randomNum(0, canvas.height));
    ctx.stroke();
  }
  // 绘制干扰点
  for (let i = 0; i < 50; i++) {
    ctx.fillStyle = randomColor();
    ctx.beginPath();
    ctx.arc(randomNum(0, canvas.width), randomNum(0, canvas.height), 1, 0, 2 * Math.PI);
    ctx.fill();
  }
  // 绘制验证码文字
  ctx.font = 'italic 24px Arial';
  ctx.fillStyle = '#333';
  const textWidth = captchaValue.length * 20; // 计算文字的总宽度
  const startX = (canvas.width - textWidth) / 2; // 计算文字起始位置的 x 坐标
  const startY = canvas.height / 2 + 10; // 计算文字起始位置的 y 坐标
  for (let i = 0; i < captchaValue.length; i++) {
    const x = startX + i * 20;
    const deg = randomNum(-30, 30);
    ctx.save();
    ctx.translate(x, startY);
    ctx.rotate(deg * Math.PI / 180);
    ctx.fillText(captchaValue[i], 0, 0);
    ctx.restore();
  }

  function generateCaptcha() {
    const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    let captcha = '';
    for (let i = 0; i < 4; i++) {
      captcha += chars[Math.floor(Math.random() * chars.length)];
    }
    return captcha;
  }

  function randomNum(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
  }

  function randomColor() {
    const r = randomNum(0, 255);
    const g = randomNum(0, 255);
    const b = randomNum(0, 255);
    return `rgb(${r},${g},${b})`;
  }
}

onMounted(() => {
  refreshCaptcha();
});

function submitFormLogin() {
  if (input.username === '') {
    alert.message = '用户名';
    alert.input_alert = true;
  } else if (input.password === '') {
    alert.message = '密码';
    alert.input_alert = true;
  } else if (input.captcha === '') {
    alert.message = '验证码';
    alert.input_alert = true;
  } else {
    if (input.captcha.toLowerCase() === captchaValue.toLowerCase()) {
      if (input.username === user.username || input.password === user.password) {
        alert.message = user.name;
        alert.login_alert = true;
        localStorage.setItem('username', user.name);
        setTimeout(() => {
          router.push({path: '/'})
        }, 2000);
      } else {
        alert.message = '用户名或密码';
        alert.error_alert = true;
      }
    } else {
      alert.message = '验证码';
      alert.error_alert = true;
    }
  }
}

function submitFormRegister() {
  if (input.username === '') {
    alert.message = '用户名';
    alert.input_alert = true;
  } else if (input.password === '') {
    alert.message = '密码';
    alert.input_alert = true;
  } else if (input.confirmPassword === '') {
    alert.message = '密码';
    alert.input_alert = true;
  } else if (input.password === input.confirmPassword) {
    alert.register_alert = true;
    back();
  }
}

function close() {
  alert.input_alert = false;
  alert.error_alert = false;
  alert.login_alert = false;
  alert.register_alert = false;
}

function register() {
  alert.isFlipped = true;
  alert.loginzIndex = 1;
  setTimeout(() => {
    alert.isFlipped2 = false;
    alert.registerzIndex = 2;
  }, 1000);
}

function back() {
  setTimeout(() => {
    alert.isFlipped = false;
    alert.loginzIndex = 2;
  }, 1000);
  alert.isFlipped2 = true;
  alert.registerzIndex = 1;
}

</script>

<style lang="scss" scoped>
#index {
  display: flex;
  justify-content: center;
  align-items: center;

  #head {
    position: absolute;
    top: 3%;
  }

  @font-face {
    font-family: 'jam font';
    src: url('@/ttf/jamfont.ttf') format('truetype');
    font-weight: normal;
    font-style: normal;
  }

  h2 {
    text-align: center;
    font-family: '也字工厂果酱体', serif;
    font-size: 50px;
    color: rgba(0, 0, 0, 0.5);
    margin-bottom: 20px;
  }

  img {
    max-width: 75%;
    height: auto;
  }

  button {
    padding: 10px 15px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    color: white;
  }

  #login,
  #register {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 800px;
    height: 500px;
    border-radius: 15px;
    display: flex;
    perspective: 1000px;
    transform-style: preserve-3d;

    .login,
    .register {
      display: flex;
      flex: 1;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      background: rgba(255, 255, 255, 0.1);
      backdrop-filter: blur(10px);
      width: 100%;
      height: 100%;

      .form-group {
        margin-bottom: 15px;

        label {
          display: block;
          margin-bottom: 5px;
        }

        input[type="text"],
        input[type="password"] {
          width: 100%;
          padding: 10px;
          border: 1px solid #ddd;
          border-radius: 4px;
        }

        img {
          cursor: pointer;
        }
      }

      .button-group {
        display: flex;
        justify-content: space-between;
        align-items: center;

        button {
          background-color: #5cb85c;
        }

        button:hover {
          background-color: #4cae4c;
        }

        button[type="button"] {
          background-color: #f0ad4e;
        }

        button[type="button"]:hover {
          background-color: #ec971f;
        }
      }

      #Captcha {
        margin-top: 10px;
        border-radius: 5px;
      }
    }

    .login-img,
    .register-img {
      flex: 1;
      display: flex;
      align-items: center;
      justify-content: center;
      background: rgba(255, 255, 255, 0.05);
      backdrop-filter: blur(10px);
      position: relative;
    }
  }

  .alert {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    padding: 20px;
    background: rgba(255, 255, 255, 0.1);
    backdrop-filter: blur(50px);
    border-radius: 15px;
    text-align: center;
    z-index: 999999;

    h1 {
      font-family: 'jam font', serif;
      font-size: 50px;
      margin: 10px;
      color: #3838e5;
    }

    button {
      margin-top: 10px;
      background-color: #5cb85c;

      &:hover {
        background-color: #4cae4c;
      }
    }
  }
}

#index .login {
  transform-origin: right;
  transition: 1s;
  right: 0;
}

#index .login-img {
  transform-origin: left;
  transition: 1s;
  left: 0;
}

#index .register {
  transform-origin: left;
  transition: 1s;
  left: 0;
}

#index .register-img {
  transform-origin: right;
  transition: 1s;
  right: 0;
}

#index .loginflip {
  transition: 1s;
  transform: rotateY(90deg);
}

#index .loginimgflip {
  transition: 1s;
  transform: rotateY(-90deg);
}

#index .registerflip {
  transition: 1s;
  transform: rotateY(-90deg);
}

#index .registerimgflip {
  transition: 1s;
  transform: rotateY(90deg);
}

</style>
