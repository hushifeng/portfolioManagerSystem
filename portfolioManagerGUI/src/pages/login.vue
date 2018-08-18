<!--<template>
<div class="fullscreen">
  <div class="login-box">
    <div style="text-align: center">
      <img src="../assets/logo.png" alt="" class="logo">
    </div>
    <p class="text-tips">你好，欢迎登录</p>
    <form action="" class="login-form">
      <div class="m-list-group">
        <div class="m-list-group-item">
          <input type="text" placeholder="Username" class="m-input" v-model="username">
        </div>
        <div class="m-list-group-item">
          <input type="password" placeholder="Password" class="m-input" v-model="password">
        </div>
      </div>
      <p class="text-tips">免密码，点击登录按钮进入</p>
      <button class="m-btn sub select-none" @click.prevent="handleLogin" v-loading="isLoging">登录</button>
    </form>
    <div style="margin-top: 50px"></div>
    <p class="text-tips">
      <i class="fa fa-meetup" style="color: #29ABE2"></i>&nbsp;
      <span class="footer-text">{{appName}} &nbsp;<el-tag size="mini">{{version}}</el-tag> <br>©make by <a href="https://www.github.com/mengdu" target="_blank">{{author}}</a>
  </span>
    </p>
  </div>
</div>
</template>-->
<template>
<div class="fullscreen">
  <div class="login-box">
    <div style="text-align: center">
      <img src="../assets/logo.png" alt="" class="logo">
    </div>
    <p class="text-tips">Hello, welcome to login.</p>
    <form action="" class="login-form">
      <div class="m-list-group">
        <div class="m-list-group-item">
          <input type="text" placeholder="Soeid" class="m-input" v-model="username">
        </div>
        <!-- <div class="m-list-group-item">
          <input type="password" placeholder="Password" class="m-input" v-model="password">
        </div> -->
      </div>
      <p class="text-tips">Please click button to sign in</p>
      <button class="m-btn sub select-none" @click.prevent="handleLogin" v-loading="isLoging">Sign in</button>
    </form>
    <div style="margin-top: 50px"></div>
    <p class="text-tips">
      <i class="fa fa-meetup" style="color: #29ABE2"></i>&nbsp;
      <span class="footer-text">{{appName}} &nbsp;<el-tag size="mini">{{version}}</el-tag> <br>©make by <a href="https://github.com/hushifeng/portfolioManager" target="_blank">{{author}}</a>
  </span>
    </p>
  </div>
</div>
</template>
<script>
import { mapActions } from "vuex";
import axios from 'axios';
const isAdmin = 0;//全局变量
export default {
  isAdmin,
  name: "login",
  data() {
    return {
      soeid:"",
      username: "hushifeng",
      // password: '123456',
      isLoging: false,
      author: "Java Team 5",
      version: "V1.0",
      appName: "Portfolio Manager System"
    };
  },
  methods: {
    ...mapActions(["login"]),
    handleLogin() {
      if (!this.username) {
        return this.$message.warning("Soeid can not be empty!");
      }
      console.log(this.username.toString);
      if (this.username.length == 9 && this.username.valueOf() == "hushifeng") {
        this.isLoging = true;
        this.GLOBAL.isAdmin = 1;
        this.login({
          username: this.username
          // password: this.password
        }).then(res => {
          this.$message.success("Sign in successful!");
          this.$router.push({ name: "home" });
          this.isLoging = false;
        });
      } else {
        console.log(this.username);
        //调用查询fund manager的soeid的接口
        let para = {soeid:this.username.valueOf()};
        console.log(para);
        axios({
        method: "get",
        baseURL: "/api",
        url: "api/FM/loginFundManager",
        params: para
      }).then(res => {
        console.log("login in"+res);
       if(res==true){
         this.isLoging = true;
          this.GLOBAL.isAdmin = 0;
          this.login({
            username: this.username
          }).then(res => {
            this.$message.success("Sign in successful!");
            this.$router.push({ name: "home" });
            this.isLoging = false;
          });
       }else{
         return this.$message.warning("Soeid Error!!!");
       }
      });
        // if (this.username.valueOf() == "hsf") {
        //   this.isLoging = true;
        //   this.GLOBAL.isAdmin = 0;
        //   this.login({
        //     username: this.username
        //   }).then(res => {
        //     this.$message.success("Sign in successful!");
        //     this.$router.push({ name: "home" });
        //     this.isLoging = false;
        //   });
        // } else {
        //   return this.$message.warning("Soeid Error!!!");
        // }
      }
    }
  }
};
</script>
<style type="text/css">
.m-list-group {
  border-radius: 3px;
  padding: 0;
  margin: 0 0 20px;
}
.m-list-group .m-list-group-item {
  position: relative;
  display: block;
  padding: 6px 10px;
  margin-bottom: -1px;
  background-color: #fff;
  border: 1px solid #e7ecee;
}
.m-list-group .m-list-group-item:first-child {
  border-top-left-radius: 3px;
  border-top-right-radius: 3px;
}
.m-list-group .m-list-group-item:last-child {
  border-bottom-left-radius: 3px;
  border-bottom-right-radius: 3px;
}
.fullscreen {
  position: absolute;
  width: 100%;
  height: 100%;
  background: #f4f5f5;
  display: flex;
  justify-content: center;
  align-items: center;
}
.login-box {
  position: relative;
  width: 330px;
  margin: 0 auto;
  padding: 0px 15px;
}
.login-box .logo {
  max-width: 40%;
  margin-bottom: 30px;
}
.login-box .text-tips {
  text-align: center;
  color: #909db7;
}
.login-box .m-input {
  width: 100%;
  padding: 10px;
  border: none;
  outline: none;
  box-sizing: border-box;
}
.login-box .m-btn {
  font-size: 18px;
  width: 100%;
  color: #fff;
  background-color: #36c1fa;
  display: inline-block;
  padding: 10px 12px;
  margin-bottom: 5px;
  line-height: 1.42857143;
  text-align: center;
  cursor: pointer;
  outline: none;
  border-radius: 2px;
  border: 1px solid #36c1fa;
  box-sizing: border-box;
  text-decoration: none;
}
.login-box .m-btn.m-btn-text {
  background: #fff;
  color: #36c1fa;
}
.login-box .m-btn:hover {
  background-color: #2db7f5;
}
.login-box .m-btn.m-btn-text:hover {
  background-color: #f4f5f5;
}
.login-box .m-btn:active {
  opacity: 0.8;
}
@media (max-width: 768px) {
  .login-box {
    width: auto;
  }
}
</style>