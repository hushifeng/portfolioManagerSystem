<template>
<div class="app-footer-box">
  <!-- <img src="../assets/logo.png" alt="logo" class="footer-logo"> -->
  <i class="fa fa-meetup" style="color: #29ABE2"></i>&nbsp;
  <span class="footer-text">{{appName}} ©make by <a href="https://github.com/hushifeng" target="_blank">{{author}}</a>
  &nbsp;Version：<el-tag size="mini">{{version}}</el-tag>
  </span>
</div>
</template>
<script type="text/javascript">
export default {
  data () {
    return {
      // author: window.APP_INFO.author,
      // version: window.APP_INFO.version,
      // appName: window.APP_INFO.appName
      author: 'Supreve',
      version: '1.0',
      appName: 'Portfolio Manager System'
    }
  }
}
</script>
<style type="text/css">
  .app-footer-box{
    height: 100%;
    display: flex;
    text-align: center;
    justify-content: center;
    align-items: center;
  }
  .app-footer-box .footer-logo{
    width: 24px;
    margin-right: 10px;
    vertical-align: middle;
  }
  .app-footer-box .footer-text{
    color: #8b949e;
  }
</style>
