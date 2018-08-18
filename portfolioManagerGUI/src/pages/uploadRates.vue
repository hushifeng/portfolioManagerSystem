<template>

<div class="page-body">
  <div class="page-header">
    <h1 class="page-title">Upload Rates</h1>
    <el-breadcrumb>
      <el-breadcrumb-item :to="{path: '/'}">Home</el-breadcrumb-item>
      <el-breadcrumb-item >uploadRates</el-breadcrumb-item>
    </el-breadcrumb>
  </div>

  <section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<!-- <el-form-item>
					<el-input v-model="filters.term" placeholder="Term"></el-input>
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
		<el-table :data="usersCurrentPage"
    stripe highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
			<!-- <el-table-column type="selection" width="55">
			</el-table-column> -->
			<el-table-column type="index" width="60">
			</el-table-column>
			<!-- <el-table-column prop="rateId" label="RateId" width="120" sortable>
			</el-table-column> -->	
			<el-table-column prop="term_currency" label="Term" width="120" sortable>
			</el-table-column>
			<el-table-column prop="rate" label="Rate" width="120" sortable>
			</el-table-column>
      <el-table-column prop="date" label="Date" width="120" sortable>
			</el-table-column>
      <el-table-column prop="base_currency" label="Base" width="120" sortable>
			</el-table-column>
			<el-table-column label="Operator" width="250">
				<template slot-scope="scope">
					<el-button size="small" @click="handleEdit(scope.$index, scope.row)">Edit</el-button>
					<!-- <el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">Delete</el-button> -->
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<!-- <el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">Large Delete</el-button> -->
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

		<el-dialog title="ADD" :visible.sync="dialogVisibleAdd" width="40%">
  			<el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
				<!-- <el-form-item label="RateId" prop="rateId">
					<el-input v-model="addForm.rateId" auto-complete="off"></el-input>
				</el-form-item> -->
				
        <el-form-item label="Term" prop="term_currency">
					<el-input v-model="addForm.term_currency" auto-complete="off"></el-input>
				</el-form-item>
        <el-form-item label="Rate" prop="rate">
					<el-input v-model="addForm.rate" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="Date" prop="date">
					<el-date-picker type="date" placeholder="Select Date" v-model="addForm.date"></el-date-picker>
				</el-form-item>
        <el-form-item label="Base" prop="base_currency">
					<el-input v-model="addForm.base_currency" auto-complete="off"></el-input>
				</el-form-item>
			</el-form>
  		<span slot="footer" class="dialog-footer">
    	<el-button @click="dialogVisibleAdd = false">Cancel</el-button>
    <el-button type="primary" @click.native="addSubmit" :loading="addLoading">Ok</el-button>
  </span>
</el-dialog>
<el-dialog title="EDIT" :visible.sync="dialogVisibleEdit" width="40%">
  <el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">

				<!-- <el-form-item label="RateId" prop="rateId">
					<el-input v-model="editForm.rateId" auto-complete="off"></el-input>
				</el-form-item> -->
				
        <el-form-item label="Term" prop="term_currency">
					<el-input v-model="editForm.term_currency" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="Rate" prop="rate">
					<el-input v-model="editForm.rate" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="Date" prop="date">
					<el-date-picker type="date" placeholder="Select Date" v-model="editForm.date"></el-date-picker>
				</el-form-item>
        <el-form-item label="Base" prop="base_currency">
					<el-input v-model="editForm.base_currency" auto-complete="off"></el-input>
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
        term: ""
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
        term: [
          { required: true, message: "Please input term", trigger: "blur" }
        ]
      },
      //编辑界面数据
      editForm: {
        term_currency: "",
        rate: "",
        date: "",
        base_currency: ""
      },

      addFormVisible: false, //新增界面是否显示
      addLoading: false,
      addFormRules: {
        term: [
          { required: true, message: "Please input term", trigger: "blur" }
        ]
      },
      //新增界面数据
      addForm: {
        // rateId: "",
        term_currency: "",
        rate: "",
        date: "",
        base_currency: ""
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
        term: this.filters.term
      };
      this.listLoading = true;

      axios({
        method: "get",
        baseURL: "/api",
        url: "api/ratesData/getRatesDataAll"
      }).then(res => {
        console.log(res);
        this.users = res.ratesDataList;
        this.usersCurrentPage = this.users.slice((this.currentPage-1)*this.pagesize,this.currentPage*this.pagesize);
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
          let para = {date:row.date,
                        BC:row.BC,
                        TC:row.TC};
            axios({
              method: "delete",
              baseURL: "/api",
              url: "api/ratesData/deleteRatesDataByDateAndBaseAndTerm",
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
    },
    //编辑
    editSubmit: function() {
      this.$refs.editForm.validate(valid => {
        if (valid) {
          this.$confirm("Are you sure to submit？", "NOTICE", {}).then(() => {
            this.editLoading = true;
            let para = Object.assign({}, this.editForm);
            axios({
              method: "post",
              baseURL: "/api",
              url: "api/ratesData/updateRatesDataByDateAndBaseAndTerm",
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
            let para = Object.assign({}, this.addForm);
            para.date =
              !para.date || para.date == ""
                ? ""
                : util.formatDate.format(new Date(para.date), "yyyy-MM-dd");
            axios({
              method: "post",
              baseURL: "/api",
              url: "api/ratesData/uploadRatesData",
              params: para
            }).then(res => {
              console.log("huhuhuh"+res);
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
      var term_currencys = this.sels.map(item => item.term_currency).toString();
      var dates = this.sels.map(item => item.date).toString();
      var base_currencys = this.sels.map(item => item.base_currency).toString();
      this.$confirm("Are you sure to delete choosed？", "NOTICE", {
        type: "warning"
      })
        .then(() => {
          this.listLoading = true;
            let para = {term_currencys:term_currencys,dates:dates,base_currencys:base_currencys};
            axios({
              method: "post",
              baseURL: "/api",
              url: "api/ratesData/deleteMultiRatesDatas",
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