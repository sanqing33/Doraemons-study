<template>
  <div id="index">
    <img id="head" alt="Hello Doraemon" src="../assets/Doraemon.png">

    <div id="login">
      <div :class="{ loginflip: alert.isFlipped }" class="login">
        <div style="width: 70%">
          <div style="font-size: 40px;transform:translateY(-20px);font-weight: bold">登录</div>
          <div style="margin-top: 40px">
            <div>
              <el-icon style="transform: translate(10px,4px);z-index: 1">
                <User/>
              </el-icon>
              <input v-model="login.username" placeholder="用户名/邮箱" style="width: 90%" type="text">
            </div>
            <div>
              <el-icon style="transform: translate(10px,4px);z-index: 1">
                <Lock/>
              </el-icon>
              <input v-model="login.password" placeholder="密码" style="margin-top: 10px;width: 90%" type="password">
            </div>
            <div>
              <el-icon style="transform: translate(10px,4px);z-index: 1">
                <Check/>
              </el-icon>
              <input v-model="login.captcha" placeholder="验证码" style="margin-top: 10px;width: 90%" type="text">
            </div>
            <div style="margin-top: 15px;">
              <canvas ref="captchaCanvas" height="50" style="border-radius: 8px" width="150" @click="refreshCaptcha">
              </canvas>
            </div>
          </div>
          <div style="margin-top: 25px">
            <el-button plain style="width: 60px;transform: translateX(-20px)" type="success" @click="submitFormLogin">
              登录
            </el-button>
            <el-button plain style="width: 60px;transform: translateX(20px)" type="warning" @click="toregister">
              注册
            </el-button>
          </div>
          <el-row style="margin-top: 10px">
            <el-col :span="18" style="text-align: right">
              <el-link>忘记密码？</el-link>
            </el-col>
          </el-row>
        </div>
      </div>
      <div :class="{ loginimgflip: alert.isFlipped }" class="login-img">
        <img alt="Login Image" src="../assets/Doraemon/login.png">
      </div>
    </div>

    <div v-if="registerRender" id="register">
      <div :class="{ registerimgflip: alert.isFlipped2 }" class="register-img">
        <img alt="Register Image" src="../assets/Doraemon/register.png">
      </div>
      <div :class="{ registerflip: alert.isFlipped2 }" class="register">
        <div style="width: 70%">
          <div style="font-size: 40px;transform:translateY(-20px);font-weight: bold">注册</div>
          <div style="margin-top: 20px">
            <div>
              <el-icon style="transform: translate(10px,4px);z-index: 1">
                <User/>
              </el-icon>
              <input v-model="register.username" placeholder="用户名" style="width: 90%" type="text">
            </div>
            <div>
              <el-icon style="transform: translate(10px,4px);z-index: 1">
                <Message/>
              </el-icon>
              <input v-model="register.email" placeholder="邮箱(用于登录或找回密码)" style="margin-top: 10px;width: 90%" type="text">
            </div>
            <div>
              <el-icon style="transform: translate(10px,4px);z-index: 1">
                <Lock/>
              </el-icon>
              <input v-model="register.password" placeholder="密码" style="margin-top: 10px;width: 90%" type="password">
            </div>
            <div>
              <el-icon style="transform: translate(10px,4px);z-index: 1">
                <Lock/>
              </el-icon>
              <input v-model="register.confirmPassword" placeholder="确认密码" style="margin-top: 10px;width: 90%" type="password">
            </div>
            <div style="margin-top: 40px">
              <el-button plain style="width: 60px;transform: translateX(-20px)" type="success" @click="submitFormRegister">
                注册
              </el-button>
              <el-button plain style="width: 60px;transform: translateX(20px)" type="warning" @click="back">
                返回
              </el-button>
            </div>
          </div>
        </div>
      </div>

      <div v-if="alert.input_alert || alert.error_alert || alert.login_alert || alert.exist_alert || alert.register_alert" class="alert">
        <h1 v-if="alert.input_alert">请输入<br>{{ alert.message }}</h1>
        <h1 v-else-if="alert.error_alert">{{ alert.message }}错误<br>请重新输入</h1>
        <h1 v-else-if="alert.login_alert">登录成功<br>欢迎用户{{ alert.message }}</h1>
        <h1 v-else-if="alert.exist_alert">用户名或邮箱已存在</h1>
        <h1 v-else-if="alert.register_alert">注册成功</h1>
        <el-button plain style="width: 60px" type="danger" @click="close">确定</el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import {onMounted, reactive, ref} from 'vue';
import {Check, Lock, Message, User} from '@element-plus/icons-vue'
import router from "@/router/index.js";
import {post} from "@/apis/index.js";

const login = {
  username: '',
  password: '',
  captcha: '',
};

const register = {
  username: '',
  email: '',
  password: '',
  confirmPassword: '',
};

const user = {
  name: '三青',
  username: 'sqing',
  password: 'sqing',
};

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

const alert = reactive({
  input_alert: false,
  error_alert: false,
  login_alert: false,
  exist_alert: false,
  register_alert: false,
  message: '',
  isFlipped: false,
  isFlipped2: true,
})

function submitFormLogin() {
  if (login.username === '') {
    alert.message = '用户名/邮箱';
    alert.input_alert = true;
  } else if (login.password === '') {
    alert.message = '密码';
    alert.input_alert = true;
  } else if (login.captcha === '') {
    alert.message = '验证码';
    alert.input_alert = true;
  } else {
    if (login.captcha.toLowerCase() === captchaValue.toLowerCase()) {
      post('/api/auth/login', {
        username: login.username,
        password: login.password
      }, (name) => {
        alert.message = name;
        alert.login_alert = true;
        setTimeout(() => {
          localStorage.setItem('username', name);
          router.push({path: '/'});
        }, 2000);
      })
    } else {
      alert.message = '验证码';
      alert.error_alert = true;
    }
  }
}

function submitFormRegister() {
  if (register.username === '') {
    alert.message = '用户名';
    alert.input_alert = true;
  } else if (register.password === '') {
    alert.message = '密码';
    alert.input_alert = true;
  } else if (register.confirmPassword === '') {
    alert.message = '密码';
    alert.input_alert = true;
  } else if (register.password === register.confirmPassword) {
    //向后端发送表单
    post('/api/auth/register', {
      username: register.username,
      email: register.email,
      password: register.password
    }, () => {
      alert.register_alert = true;
      back();
    }, () => {
      alert.exist_alert = true;
    })
  }
}

function close() {
  alert.input_alert = false;
  alert.error_alert = false;
  alert.login_alert = false;
  alert.exist_alert = false;
  alert.register_alert = false;
}

//渲染注册组件
let registerRender = ref(false);

function toregister() {
  alert.isFlipped = true;
  registerRender.value = true;
  setTimeout(() => {
    alert.isFlipped2 = false;
  }, 1000);
}

function back() {
  alert.isFlipped2 = true;
  setTimeout(() => {
    alert.isFlipped = false;
    registerRender.value = false
  }, 1000);
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

  input {
    padding: 8px 25px;
    width: 100%;
    flex-grow: 1;
    border-radius: 5px;
    height: var(--el-input-inner-height);
    line-height: var(--el-input-inner-height);
    outline: 0;
    border: none;
    box-sizing: border-box;
    font-size: 12px;
    transform: translateX(-10px);
  }

  img {
    max-width: 75%;
    height: auto;
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
    text-align: center;

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
      color: #333333;
    }

  }
}

#index {
  .login {
    transform-origin: right;
    transition: 1s;
    right: 0;
  }

  .login-img {
    transform-origin: left;
    transition: 1s;
    left: 0;
  }

  .register {
    transform-origin: left;
    transition: 1s;
    left: 0;
  }

  .register-img {
    transform-origin: right;
    transition: 1s;
    right: 0;
  }

  .loginflip {
    transition: 1s;
    transform: rotateY(90deg);
  }

  .loginimgflip {
    transition: 1s;
    transform: rotateY(-90deg);
  }

  .registerflip {
    transition: 1s;
    transform: rotateY(-90deg);
  }

  .registerimgflip {
    transition: 1s;
    transform: rotateY(90deg);
  }
}
</style>
