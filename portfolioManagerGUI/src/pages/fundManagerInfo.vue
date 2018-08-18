<template>

<div class="page-body">
  <div class="page-header">
    <h1 class="page-title">Fund Manager Info</h1>
    <el-breadcrumb>
      <el-breadcrumb-item :to="{path: '/'}">Home</el-breadcrumb-item>
      <el-breadcrumb-item >funManagerInfo</el-breadcrumb-item>
    </el-breadcrumb>
  </div>

   <section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<!-- <el-form-item>
					<el-input v-model="filters.soeid" placeholder="Soeid"></el-input>
				</el-form-item> -->
				<!-- <el-form-item>
					<el-button type="primary" v-on:click="getUsers">Search</el-button>
				</el-form-item> -->
				<el-form-item>
					<el-button type="primary" @click="handleAdd">Add</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table @selection-change="selsChange" :data="usersCurrentPage" stripe highlight-current-row v-loading="listLoading"  style="width: 100%;">
			<!-- <el-table-column type="selection" width="55">
			</el-table-column> -->
			<el-table-column type="index" width="60">
			</el-table-column>
			<el-table-column prop="soeid" label="Soeid" width="120" sortable>
			</el-table-column>
			<el-table-column prop="name" label="Name" width="120" sortable>
			</el-table-column>
			
			<!-- <el-table-column id="time"  label="SysDate" width="120" sortable>
				getDate
			</el-table-column> -->
			<el-table-column label="Operator" width="250">
				<template slot-scope="scope">
					<el-button size="small" @click="handleEdit(scope.$index, scope.row)">Edit</el-button>
					<el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">Delete</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<!-- <el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">Large Delete</el-button> -->
			<!-- <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="1" :total="users.length" style="float:right;">

			</el-pagination> -->
      <el-pagination
    @size-change="handleSizeChange"
    @current-change="handleCurrentChange"
    :current-page="currentPage"
    :page-sizes="[1, 2, 5, 10]"
    :page-size="pagesize"
    layout="total, sizes, prev, pager, next, jumper"
    :total="users.length"
    style="float:right;">
</el-pagination>

			
		</el-col>
	<!--弹窗 -->
		<el-dialog title="ADD" :visible.sync="dialogVisibleAdd" width="30%">
  			<el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
				<el-form-item label="Soeid" prop="soeid">
					<el-input v-model="addForm.soeid" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="Name" prop="name">
					<el-input v-model="addForm.name" auto-complete="off"></el-input>
				</el-form-item>
			</el-form>
  		<span slot="footer" class="dialog-footer">
    	<el-button @click="dialogVisibleAdd = false">Cancel</el-button>
    <el-button type="primary" @click.native="addSubmit" :loading="addLoading">Ok</el-button>
  </span>
</el-dialog>
<el-dialog title="EDIT" :visible.sync="dialogVisibleEdit" width="30%">
  <el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">

				<el-form-item label="Soeid" prop="soeid">
					<el-input v-model="editForm.soeid" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="Name" prop="name">
					<el-input v-model="editForm.name" auto-complete="off"></el-input>
				</el-form-item>
			</el-form>
  <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisibleEdit= false">Cancel</el-button>
    <el-button type="primary" @click.native="editSubmit" :loading="editLoading" >Ok</el-button>
  </span>
</el-dialog>
	</section>
</div>
</template>
<script>
import util from "../common/util";
//import NProgress from 'nprogress'
import axios from "axios";

import {
  getUserListPage,
  removeUser,
  batchRemoveUser,
  editUser,
  addUser
} from "../common/api";

export default {
  data() {
    return {
      currentPage: 1,
      pagesize: 2,

      dialogVisibleAdd: false,
      dialogVisibleEdit: false,

      filters: {
        soeid: ""
      },
      users: [],
      usersCurrentPage: [],
      total: 0,
      // page: 1,
      listLoading: false,
      sels: [], //列表选中列

      // editFormVisible: false, //编辑界面是否显示
      editLoading: false,
      editFormRules: {
        soeid: [
          { required: true, message: "Please input soeid", trigger: "blur" }
        ]
      },
      //编辑界面数据
      editForm: {
        id: 0,
        soeid: "",
        name: ""
      },

      // addFormVisible: false, //新增界面是否显示
      addLoading: false,
      addFormRules: {
        soeid: [
          { required: true, message: "Please input soeid", trigger: "blur" }
        ]
      },
      //新增界面数据
      addForm: {
        soeid: "",
        name: ""
      }
    };
  },
  methods: {
    handleSizeChange: function(size) {
      this.pagesize = size;
      this.getUsers();
    },
    handleCurrentChange: function(currentPage) {
      this.currentPage = currentPage;
      this.getUsers();
    },

    //获取用户列表
    getUsers() {
      let para = {
        page: this.page,
        soeid: this.filters.soeid
      };
      this.listLoading = true;

      axios({
        method: "get",
        baseURL: "/api",
        url: "api/FM/queryAllFundManager"
      }).then(res => {
        console.log("" + res);
        this.total = res.total;
        this.users = res.FundManagerList;

        this.usersCurrentPage = this.users.slice(
          (this.currentPage - 1) * this.pagesize,
          this.currentPage * this.pagesize
        );
        this.listLoading = false;
      });
    },
    //删除
    handleDel: function(index, row) {
      this.$confirm("Are you sure to delete?", "NOTICE", {
        type: "warning"
      })
        .then(() => {
          this.listLoading = true;
            //NProgress.start();
            let para = {soeid:row.soeid,
                        name:row.name};
            axios({
              method: "get",
              baseURL: "/api",
              url: "api/FM/deleteFundManager",
              params: para
            }).then(res => {
              console.log(res);
              // this.addLoading = false;
              this.listLoading = false;
              // this.dialogVisibleAdd = false;
              console.log("going to");
              // this.addFormVisible = false;
              this.getUsers();
            });
        })
        .catch(() => {});
    },
    //显示编辑界面
    handleEdit: function(index, row) {
      console.log("hhhhhhhhhhhhh");
      this.dialogVisibleEdit = true;
      // this.editFormVisible = true;
      this.editForm = Object.assign({}, row);
      console.log(this.editForm);
      console.log(row);
    },
    //显示新增界面
    handleAdd: function() {
      this.dialogVisibleAdd = true;
    },
    //编辑
    editSubmit: function() {
      this.$refs.editForm.validate(valid => {
        if (valid) {
          this.$confirm("Are you sure to submit？", "NOTICE", {}).then(() => {
            this.editLoading = true;
             let para = Object.assign({}, this.editForm);
            axios({
              method: "get",
              baseURL: "/api",
              url: "api/FM/updateFundManager",
              params: para
            }).then(res => {
              console.log(res);
              // this.addLoading = false;
               this.editLoading = false;
              // this.listLoading = false;
              this.dialogVisibleEdit = false;
              console.log("going to");
              // this.addFormVisible = false;
              this.getUsers();
            });
          });
        }
      });
    },
    //新增
    addSubmit: function() {
      this.$refs.addForm.validate(valid => {
        if (valid) {
          this.$confirm("Are you sure to submit？", "NOTICE", {}).then(() => {
            this.addLoading = true;
            //NProgress.start();
            let para = Object.assign({}, this.addForm);
            axios({
              method: "get",
              baseURL: "/api",
              url: "api/FM/insertFundManager",
              params: para
            }).then(res => {
              console.log(res);
              this.addLoading = false;
              this.listLoading = false;
              this.dialogVisibleAdd = false;
              console.log("going to");
              // this.addFormVisible = false;
              this.getUsers();
            });
          });
        }
      });
    },
    selsChange: function(sels) {
      this.sels = sels;
    },
    //批量删除
    batchRemove: function() {
      var soeids = this.sels.map(item => item.soeid).toString();
      console.log(soeids);
      this.$confirm("Are you sure to delete choosed？", "NOTICE", {
        type: "warning"
      })
        .then(() => {
          this.listLoading = true;
            //NProgress.start();
            let para = {soeids:soeids};
            axios({
              method: "get",
              baseURL: "/api",
              url: "api/FM/deleteManyFM",
              params: para
            }).then(res => {
              Console.log('HHWLOL')
              console.log(res);
              // this.addLoading = false;
              this.listLoading = false;
              // this.dialogVisibleAdd = false;
              console.log("going to");
              // this.addFormVisible = false;
              this.getUsers();
            });
        });
       
    }
  },
  mounted() {
    this.getUsers();
  }
};
</script>

<style scoped>
</style>