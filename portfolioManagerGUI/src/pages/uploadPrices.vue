<template>

<div class="page-body">
  <div class="page-header">
    <h1 class="page-title">Upload Prices</h1>
    <el-breadcrumb>
      <el-breadcrumb-item :to="{path: '/'}">Home</el-breadcrumb-item>
      <el-breadcrumb-item >uploadPrices</el-breadcrumb-item>
    </el-breadcrumb>
  </div>

  <section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<!-- <el-form-item>
					<el-input v-model="filters.symbol" placeholder="Symbol"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="getUsers">Search</el-button>
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
			<el-table-column prop="symbol" label="Symbol" width="120" sortable>
			</el-table-column>
			<el-table-column prop="price" label="Price" width="120" sortable>
			</el-table-column>
			<el-table-column prop="date" label="Date" width="120" sortable>
			</el-table-column>
      <el-table-column prop="base_currency" label="Base_currency" width="150" sortable>
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
			<el-pagination
    @size-change="handleSizeChange"
    @current-change="handleCurrentChange"
    :current-page="currentPage"
    :page-sizes="[1, 2, 5, 10]"
    :page-size="pagesize"
    layout="total, sizes, prev, pager, next, jumper"
    :total="pricesDataList.length"
    style="float:right;">
</el-pagination>

			
		</el-col>


		<el-dialog title="ADD" :visible.sync="dialogVisibleAdd" width="40%">
  			<el-form :model="addForm" label-width="120px" :rules="addFormRules" ref="addForm">
				<el-form-item label="Symbol" prop="symbol">
					<el-input v-model="addForm.symbol" auto-complete="off"></el-input>
				</el-form-item>
        <el-form-item label="Date" prop="date">
					<el-date-picker type="date" placeholder="Select Date" v-model="addForm.date"></el-date-picker>
				</el-form-item>
				<el-form-item label="Price" prop="price">
					<el-input v-model="addForm.price" auto-complete="off"></el-input>
				</el-form-item>
        <el-form-item label="Base_currency" prop="base_currency">
					<el-input v-model="addForm.base_currency" auto-complete="off"></el-input>
				</el-form-item>
			</el-form>
  		<span slot="footer" class="dialog-footer">
    	<el-button @click="dialogVisibleAdd = false">Cancel</el-button>
    <el-button type="primary" @click.native="addSubmit" :loading="addLoading">Ok</el-button>
  </span>
</el-dialog>
<el-dialog title="EDIT" :visible.sync="dialogVisibleEdit" width="40%">
  <el-form :model="editForm" label-width="120px" :rules="editFormRules" ref="editForm">

				<el-form-item label="Symbol" prop="symbol">
					<el-input v-model="editForm.symbol" auto-complete="off"></el-input>
				</el-form-item>
        <el-form-item label="Date" prop="date">
					<el-date-picker type="date" placeholder="Select Date" v-model="editForm.date"></el-date-picker>
				</el-form-item>
				<el-form-item label="Price" prop="price">
					<el-input v-model="editForm.price" auto-complete="off"></el-input>
				</el-form-item>	
        <el-form-item label="Base_currency" prop="base_currency">
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
        symbol: ""
      },
      pricesDataList: [],
      usersCurrentPage:[],
      total: 0,
      // page: 1,
      listLoading: false,
      sels: [], //列表选中列

      editFormVisible: false, //编辑界面是否显示
      editLoading: false,
      editFormRules: {
        symbol: [
          { required: true, message: "Please input symbol", trigger: "blur" }
        ]
      },
      //编辑界面数据
      editForm: {
        symbol: "",
        date: "",
        price: "",        
        base_currency:""
      },

      addFormVisible: false, //新增界面是否显示
      addLoading: false,
      addFormRules: {
        symbol: [
          { required: true, message: "Please input symbol", trigger: "blur" }
        ]
      },
      //新增界面数据
      addForm: {
        symbol: "",
        date: "",
        price: "",        
        base_currency:""
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
        symbol: this.filters.symbol
      };
      this.listLoading = true;

      axios({
        method: "get",
        baseURL: "/api",
        url: "api/pricesData/getPricesDataAll"
      }).then(res => {
        console.log(res);
        console.log(res.total);
        this.pricesDataList = res.pricesDataList;
        this.usersCurrentPage = this.pricesDataList.slice((this.currentPage-1)*this.pagesize,this.currentPage*this.pagesize);
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
           let para = {symbol:row.symbol};
            axios({
              method: "delete",
              baseURL: "/api",
              url: "api/pricesData/deletePricesDataBySymbol",
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
            para.date =
              !para.date || para.date == ""
                ? ""
                : util.formatDate.format(new Date(para.date), "yyyy-MM-dd");
            axios({
              method: "POST",
              baseURL: "/api",
              url: "api/pricesData/updatePricesData",
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
            para.date =
              !para.date || para.date == ""
                ? ""
                : util.formatDate.format(new Date(para.date), "yyyy-MM-dd");
            axios({
              method: "post",
              baseURL: "/api",
              url: "api/pricesData/uploadPricesData",
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
     var symbols = this.sels.map(item => item.symbol).toString();
      console.log(symbols);
      this.$confirm("Are you sure to delete choosed？", "NOTICE", {
        type: "warning"
      })
        .then(() => {
           this.listLoading = true;
            //NProgress.start();
            let para = {symbols:symbols};
            axios({
              method: "delete",
              baseURL: "/api",
              url: "api/pricesData/deleteMultiPricesDataBySymbols",
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