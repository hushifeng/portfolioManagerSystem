<template>

<div class="page-body">
  <div class="page-header">
    <h1 class="page-title">Portfolios</h1>
    <el-breadcrumb>
      <el-breadcrumb-item :to="{path: '/'}">Home</el-breadcrumb-item>
      <el-breadcrumb-item >Portfolios</el-breadcrumb-item>
    </el-breadcrumb>
  </div>

  <section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.id" placeholder="PortfolioId"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="searchById">Search</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleAdd">Add</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="usersCurrentPage"
    stripe highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
			<el-table-column type="selection" width="55">
			</el-table-column>
			<el-table-column type="index" width="60">
			</el-table-column>
			<el-table-column prop="id" label="Id" width="120" sortable>
			</el-table-column>
			<el-table-column prop="name" label="Name" width="120" sortable>
			</el-table-column>
			<!-- <el-table-column prop="fund_manager_id" label="FundManagerId" width="150" sortable>
			</el-table-column> -->
			<el-table-column prop="initial_cash" label="Initcash" width="120" sortable>
			</el-table-column>
      <el-table-column prop="cash" label="Cash" width="120" sortable>
			</el-table-column>
           
			<el-table-column label="Operator" width="250">
				<template slot-scope="scope">
					<el-button size="small" @click="handleEdit(scope.$index, scope.row)">Edit</el-button>
					<el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">Delete</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">Large Delete</el-button>
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

		<el-dialog title="ADD" :visible.sync="dialogVisibleAdd" width="30%">
  			<el-form :model="addForm" label-width="120px" :rules="addFormRules" ref="addForm">
				<el-form-item label="FundManager_Id" prop="fund_manager_id">
					<el-input v-model="addForm.fund_manager_id" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="Name" prop="name">
					<el-input v-model="addForm.name" auto-complete="off"></el-input>
				</el-form-item>
                <!-- <el-form-item label="FundManagerId" prop="fund_manager_id">
					<el-input v-model="addForm.fund_manager_id" auto-complete="off"></el-input>
				</el-form-item> -->
                <el-form-item label="Initcash" prop="initial_cash">
					<el-input v-model="addForm.initial_cash" auto-complete="off"></el-input>
				</el-form-item>
                
			</el-form>
  		<span slot="footer" class="dialog-footer">
    	<el-button @click="dialogVisibleAdd = false">Cancel</el-button>
    <el-button type="primary" @click.native="addSubmit" :loading="addLoading">Ok</el-button>
  </span>
</el-dialog>
<el-dialog title="EDIT" :visible.sync="dialogVisibleEdit" width="30%">
  <el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">

				<!-- <el-form-item label="Id" prop="id">
					<el-input v-model="editForm.id" auto-complete="off"></el-input>
				</el-form-item> -->
				<el-form-item label="Name" prop="name">
					<el-input v-model="editForm.name" auto-complete="off"></el-input>
				</el-form-item>
                <!-- <el-form-item label="FundManagerId" prop="fund_manager_id">
					<el-input v-model="editForm.fund_manager_id" auto-complete="off"></el-input>
				</el-form-item> -->
				<el-form-item label="Initcash" prop="initial_cash">
					<el-input v-model="editForm.initial_cash" auto-complete="off"></el-input>
				</el-form-item>
			</el-form>
  <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisibleEdit= false">Cancel</el-button>
    <el-button type="primary" @click.native="editSubmit" :loading="editLoading">Ok</el-button>
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
        id: ""
      },
      users: [],
      usersCurrentPage:[],
      total: 0,
      // page: 1,
      listLoading: false,
      sels: [], //列表选中列

      editFormVisible: false, //编辑界面是否显示
      editLoading: false,
      editFormRules: {
        name: [
          { required: true, message: "Please input name", trigger: "blur" }
        ]
      },
      //编辑界面数据
      editForm: {
        // id: 0,
        // id: "",
        name:"",
        fund_manager_id:"",
        initial_cash:""
      },

      addFormVisible: false, //新增界面是否显示
      addLoading: false,
      addFormRules: {
        name: [
          { required: true, message: "Please input name", trigger: "blur" }
        ]
      },
      //新增界面数据
      addForm: {
        // id: "",
        name:"",
        fund_manager_id:"",
        initial_cash:""
      }
    };
  },
  methods: {
    //处理分页
    // handleSizeChange(val) {
    //     console.log(`每页 ${val} 条`);
    //   },
    //   handleCurrentChange(val) {
    //     console.log(`当前页: ${val}`);
    //   },
    
    handleSizeChange: function(size) {
      this.pagesize = size;
      this.getUsers();
    },
    handleCurrentChange: function(currentPage) {
      this.currentPage = currentPage;
      this.getUsers();
    },
    //search
    searchById: function() {
      let para = {
        // page: this.page,
        id: this.filters.id
      };
      console.log(para.id);
      this.listLoading = true;
      // console.log("111111111"+para.fund_manager_id);
      axios({
        method: "get",
        baseURL: "/api",
        url: "api/portfolio/queryPortfolioById",
        params: para
      }).then(res => {
        console.log("jjjjjjjjj"+res);
        // this.users = res
        // this.usersCurrentPage = [res.id,res.name,res.cash];
        this.users = res;
        console.log(res);
        // this.usersCurrentPage.name = res.name;
        // this.usersCurrentPage.cash = res.cash;
        this.usersCurrentPage = this.users.slice((this.currentPage-1)*this.pagesize,this.currentPage*this.pagesize);
        this.listLoading = false;
    });
    },
    //获取用户列表
    getUsers() {
      // let para = {
      //   // page: this.page,
      //   fund_manager_id: this.filters.fund_manager_id
      // };
      this.listLoading = true;

      axios({
        method: "get",
        baseURL: "/api",
        url: "api/portfolio/queryPortfolioAll",
        // data:para
      }).then(res => {
        console.log(res);
        console.log(res.total);
        this.users = res.portfolios;
        this.usersCurrentPage = this.users.slice((this.currentPage-1)*this.pagesize,this.currentPage*this.pagesize);
        // this.usersCurrentPage = this.users.slice((this.currentPage-1)*this.pagesize,this.currentPage*this.pagesize);
        this.listLoading = false;
      });

      // getUserListPage(para).then((res) => {
      // 	this.total = res.data.total;
      // 	this.users = res.data.users;
      // 	this.listLoading = false;
      // });
    },
    //删除
    handleDel: function(index, row) {
      this.$confirm("Are you sure to delete?", "NOTICE", {
        type: "warning"
      })
        .then(() => {
          // this.listLoading = true;
          // //NProgress.start();
          // let para = { id: row.id };
          // removeUser(para).then(res => {
          //   this.listLoading = false;
          //   //NProgress.done();
          //   this.$message({
          //     message: "delete success",
          //     type: "success"
          //   });
          //   this.getUsers();
          // });
          this.listLoading = true;
            //NProgress.start();
            let para = {portfolio_id:row.id};
            axios({
              method: "get",
              baseURL: "/api",
              url: "api/portfolio/deletePortfolio",
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
      this.dialogVisibleEdit = true;
      this.editFormVisible = true;
      this.editForm = Object.assign({}, row);
    },
    //显示新增界面
    handleAdd: function() {
      this.dialogVisibleAdd = true;
      //   this.addFormVisible = true;
      //   console.log(this.addFormVisible);
      //   this.addForm = {
      //     symbol: "",
      //     prices: "",
      //     date: ""
      //   };
    },
    //编辑
    editSubmit: function() {
      this.$refs.editForm.validate(valid => {
        if (valid) {
          this.$confirm("Are you sure to submit？", "NOTICE", {}).then(() => {
            // this.editLoading = true;
            // //NProgress.start();
            // let para = Object.assign({}, this.editForm);
            // para.birth =
            //   !para.birth || para.birth == ""
            //     ? ""
            //     : util.formatDate.format(new Date(para.birth), "yyyy-MM-dd");
            // editUser(para).then(res => {
            //   this.editLoading = false;
            //   //NProgress.done();
            //   this.$message({
            //     message: "submit success",
            //     type: "success"
            //   });
            //   this.$refs["editForm"].resetFields();
            //   this.editFormVisible = false;
            //   this.getUsers();
            // });
             this.editLoading = true;
             let para = Object.assign({}, this.editForm);
            axios({
              method: "get",
              baseURL: "/api",
              url: "api/portfolio/updatePortfolio",
              params: para
            }).then(res => {
              console.log(res);
              if(res==true){
                this.$message({
                message: "update success",
                type: "success"
              });
              }else{
                 this.$message({
                message: "update failed",
                type: "error"
              });
              }
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
            // this.addLoading = true;
            // //NProgress.start();
            // let para = Object.assign({}, this.addForm);
            // para.birth =
            //   !para.birth || para.birth == ""
            //     ? ""
            //     : util.formatDate.format(new Date(para.birth), "yyyy-MM-dd");
            // addUser(para).then(res => {
            //   this.addLoading = false;
            //   //NProgress.done();
            //   this.$message({
            //     message: "submit success",
            //     type: "success"
            //   });
            //   this.$refs["addForm"].resetFields();
            //   this.addFormVisible = false;
            //   this.getUsers();
            // });
             this.addLoading = true;
            let para = Object.assign({}, this.addForm);
            axios({
              method: "get",
              baseURL: "/api",
              url: "api/portfolio/createPortfolio",
              params: para
            }).then(res => {
              console.log("hhhh"+res);
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
      var ids = this.sels.map(item => item.id).toString();
      this.$confirm("Are you sure to delete choosed？", "NOTICE", {
        type: "warning"
      })
        .then(() => {
          this.listLoading = true;
          //NProgress.start();
          let para = { ids: ids };
          // batchRemoveUser(para).then(res => {
          //   this.listLoading = false;
          //   //NProgress.done();
          //   this.$message({
          //     message: "delete success",
          //     type: "success"
          //   });
          //   this.getUsers();
          // });
          axios({
              method: "get",
              baseURL: "/api",
              url: "api/portfolio/deletePortfolios",
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
    }
  },
  mounted() {
    this.getUsers();
  }
};
</script>

<style scoped>
</style>