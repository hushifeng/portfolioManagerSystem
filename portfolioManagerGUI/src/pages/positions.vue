<template>

<div class="page-body">
  <div class="page-header">
    <h1 class="page-title">Positions</h1>
    <el-breadcrumb>
      <el-breadcrumb-item :to="{path: '/'}">Home</el-breadcrumb-item>
      <el-breadcrumb-item >Positions</el-breadcrumb-item>
    </el-breadcrumb>
  </div>

  <section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.portfolio_id" placeholder="Portfolio Id"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="searchByPortfolioId">Search</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleAdd">Buy</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="usersCurrentPage"
    stripe highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
			<el-table-column type="selection" width="55">
			</el-table-column>
			<el-table-column type="index" width="50">
			</el-table-column>
			<el-table-column prop="id" label="Id" width="50" sortable>
			</el-table-column>
			<el-table-column prop="symbol" label="Symbol" width="120" sortable>
			</el-table-column>
			<el-table-column prop="quantity" label="Quantity" width="110" sortable>
			</el-table-column>
			<el-table-column prop="price" label="Price" width="90" sortable>
			</el-table-column>
            <el-table-column prop="portfolio_id" label="PortfolioId" width="120" sortable>
			</el-table-column>
       <el-table-column prop="base_currency" label="BaseCurrency" width="140" sortable>
			</el-table-column>
            <el-table-column prop="term_currency" label="TermCurrency" width="140" sortable>
			</el-table-column>
            <el-table-column prop="date" label="Date" width="120" sortable>
			</el-table-column>
			<el-table-column label="Operator" width="250">
				<template slot-scope="scope">
					<el-button size="small" @click="handleEdit(scope.$index, scope.row)">Sell</el-button>
					<el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">SellAll</el-button>
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

		<el-dialog title="BUY" :visible.sync="dialogVisibleAdd" width="40%">
  			<el-form :model="addForm" label-width="120px" :rules="addFormRules" ref="addForm">
				<!-- <el-form-item label="Id" prop="id">
					<el-input v-model="addForm.id" auto-complete="off"></el-input>
				</el-form-item> -->
				<el-form-item label="Symbol" prop="symbol">
					<el-input v-model="addForm.symbol" auto-complete="off"></el-input>
				</el-form-item>
                <el-form-item label="Quantity" prop="quantity">
					<el-input v-model="addForm.quantity" auto-complete="off"></el-input>
				</el-form-item>
                <!-- <el-form-item label="Price" prop="price">
					<el-input v-model="addForm.price" auto-complete="off"></el-input>
				</el-form-item> -->
                <el-form-item label="PortfolioId" prop="portfolio_id">
					<el-input v-model="addForm.portfolio_id" auto-complete="off"></el-input>
				</el-form-item>
        
        <el-form-item label="BaseCurrency" prop="base_currency">
					<el-input v-model="addForm.base_currency" auto-complete="off"></el-input>
				</el-form-item>
        <el-form-item label="TermCurrency" prop="term_currency">
					<el-input v-model="addForm.term_currency" auto-complete="off"></el-input>
				</el-form-item>
                
				<el-form-item label="Date" prop="date">
					<el-date-picker type="date" placeholder="Select Date" v-model="addForm.date"></el-date-picker>
				</el-form-item>
			</el-form>
  		<span slot="footer" class="dialog-footer">
    	<el-button @click="dialogVisibleAdd = false">Cancel</el-button>
    <el-button type="primary" @click.native="addSubmit" :loading="addLoading">Ok</el-button>
  </span>
</el-dialog>
<el-dialog title="SELL" :visible.sync="dialogVisibleEdit" width="30%">
  <el-form :model="editForm" label-width="100px" :rules="editFormRules" ref="editForm">

				<!-- <el-form-item label="Id" prop="id">
					<el-input v-model="editForm.id" auto-complete="off"></el-input>
				</el-form-item> -->
				<!-- <el-form-item label="Symbol" prop="symbol">
					<el-input v-model="editForm.symbol" auto-complete="off"></el-input>
				</el-form-item> -->
                <el-form-item label="Quantity" prop="quantity">
					<el-input v-model="editForm.quantity" auto-complete="off"></el-input>
				</el-form-item>
				<!-- <el-form-item label="Price" prop="price">
					<el-input v-model="editForm.price" auto-complete="off"></el-input>
				</el-form-item> -->
                <!-- <el-form-item label="PortfolioId" prop="portfolio_id">
					<el-input v-model="editForm.portfolio_id" auto-complete="off"></el-input>
				</el-form-item>
        <el-form-item label="BaseCurrency" prop="base_currency">
					<el-input v-model="editForm.base_currency" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="TermCurrency" prop="term_currency">
					<el-input v-model="editForm.term_currency" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="Date" prop="date">
					<el-date-picker type="date" placeholder="Select Date" v-model="editForm.date"></el-date-picker>
				</el-form-item> -->
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
        portfolio_id: ""
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
        quantity: [{ required: true, message: "Please input quantity", trigger: "blur" }]
      },
      //编辑界面数据
      editForm: {
        // id: 0,
        id: "",
        symbol: "",
        quantity: "",
        price: "",
        portfolio_id: "",
        base_currency:"",
        term_currency: "",
        date: ""
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
        // id: "",
        symbol: "",
        quantity: "",
        // price: "",
        portfolio_id: "",
        base_currency:"",
        term_currency: "",
        date: ""
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
    searchByPortfolioId: function() {
      let para = {
        // page: this.page,
        portfolio_id: this.filters.portfolio_id
      };
      // console.log(para.id);
      this.listLoading = true;
      // console.log("111111111"+para.fund_manager_id);
      axios({
        method: "get",
        baseURL: "/api",
        url: "api/position/queryPosition",
        params: para
      }).then(res => {
        console.log("jjjjjjjjj"+res);
        // this.users = res
        // this.usersCurrentPage = [res.id,res.name,res.cash];
        this.users = res.positions;
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
      //   page: this.page,
      //   id: this.filters.Id
      // };
      this.listLoading = true;

      axios({
        method: "get",
        baseURL: "/api",
        url: "api/position/queryPositionAll"
      }).then(res => {
        console.log(res);
        console.log(res.total);
        this.total = res.total;
        this.users = res.positions;
        this.usersCurrentPage = this.users.slice((this.currentPage-1)*this.pagesize,this.currentPage*this.pagesize);
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
            let para = {position_id:row.id};
            console.log(para);
            axios({
              method: "get",
              baseURL: "/api",
              url: "api/position/deletePosition",
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
            this.editLoading = true;
            let para = Object.assign({}, this.editForm);
            para.date =
              !para.date || para.date == ""
                ? ""
                : util.formatDate.format(new Date(para.date), "yyyy-MM-dd");
            axios({
              method: "get",
              baseURL: "/api",
              url: "api/position/updatePosition",
              params: para
            }).then(res => {
              console.log(res);
              if(res==true){
                this.$message({
                message: "Sell success",
                type: "success"
              });
              }else{
                 this.$message({
                message: "Sell failed",
                type: "error"
              });
              }
              this.editLoading = false;
              this.listLoading = false;
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
              method: "get",
              baseURL: "/api",
              url: "api/position/createPosition",
              params: para
            }).then(res => {
              console.log(res);
              if(res==true){
                this.$message({
                message: "Buy success",
                type: "success"
              });
              }else{
                 this.$message({
                message: "Buy failed",
                type: "error"
              });
              }
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
              url: "api/position/deletePositions",
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