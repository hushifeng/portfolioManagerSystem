<template>

<v-menu class="side-menu"
  :collapse="collapse"
  :default-active="defaultActive"
  :menus="menusType"
  router
  :background-color="theme.backgroundColor"
  :text-color="theme.textColor"
  :active-text-color="theme.activeTextColor"
  ></v-menu>
  
<!-- <el-row class="tac">
  <el-col :span="24">
    <el-menu
    class="side-menu"
  :collapse="collapse"
  :default-active="$route.path"
  :background-color="theme.backgroundColor"
  :text-color="theme.textColor"
  :active-text-color="theme.activeTextColor"
      >
      <el-submenu index="1" >
        <template slot="title">
          <i class="el-icon-location"></i>
          <span>导航一</span>
        </template>
        <el-menu-item-group>
          <template slot="title">分组一</template>
          <el-menu-item index="/fundManagerInfo">选项1</el-menu-item>
          <el-menu-item index="/reports">选项2</el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group title="分组2">
          <el-menu-item index="1-3">选项3</el-menu-item>
        </el-menu-item-group>
        <el-submenu index="1-4">
          <template slot="title">选项4</template>
          <el-menu-item index="1-4-1">选项1</el-menu-item>
        </el-submenu>
      </el-submenu>
      <el-menu-item index="2">
        <i class="el-icon-menu"></i>
        <span slot="title">导航二</span>
      </el-menu-item>
      <el-menu-item index="3" disabled>
        <i class="el-icon-document"></i>
        <span slot="title">导航三</span>
      </el-menu-item>
      <el-menu-item index="4" v-if="0<5">
        <i class="el-icon-setting"></i>
        <span slot="title">hushifegn</span>
      </el-menu-item>
    </el-menu>
  </el-col>
</el-row> -->




</template>
<script type="text/javascript">
import VMenu from "./vmenu";
import menus1 from "./menus";
import menus2 from "./menus2";
import {mapActions, mapState} from 'vuex'
export default {
  props: {
    collapse: Boolean,
    theme: Object
  },
  components: {
    ...mapState({
      user: ({user}) => user.user
    }),
    VMenu,
    
  },
  data() {
    return {
      menus1,
      menus2,
      menusType: "menus1",
      defaultActive: "home",
      test: "asdfasdf"
    };
  },
  watch: {
    $route() {
      this.setCurrentRoute();
    }
  },
  methods: {
    setCurrentRoute() {
      // console.log(this.$route)
      this.defaultActive = this.$route.name;
      console.log(this.defaultActive + "hhhhhh");
      // console.log(this.$user.username)
      // console.log(user.username);
      console.log("isAdmin:"+this.GLOBAL.isAdmin);
      if (this.GLOBAL.isAdmin == 1) {
        console.log(this.GLOBAL.isAdmin);
        this.menusType = menus1;
      } else {
        this.menusType = menus2;
      }
    }
  },
  created() {
    this.setCurrentRoute();
    // this.setMenu();
  },
  mounted() {
    console.log("hhhhhhhhhhhhhhh");

  }
};
</script>
<style type="text/css">
.el-menu.side-menu {
  border-right: none;
}
.el-menu .fa {
  font-size: 18px;
  margin-right: 7px;
  display: inline-block;
  width: 23px;
  text-align: center;
}
.el-menu.side-menu .el-menu-item.is-active {
  color: #409eff;
  border-right: solid 2px #36c1fa;
  background: #eef3f5;
}
</style>
