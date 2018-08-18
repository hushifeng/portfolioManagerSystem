<template>
<div class="page-body">
  <div class="page-header">
    <h1 class="page-title">Reports</h1>
    <el-breadcrumb>
      <el-breadcrumb-item :to="{path: '/'}">Home</el-breadcrumb-item>
      <el-breadcrumb-item >Reports</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
    <section class="chart-container">
        <el-row>
            <el-col :span="20">
                <div id="BestFundManagerChart" style="width:80%; height:450px;"></div>
            </el-col>
            <el-col :span="20">
                <div id="BestPortfolioChart" style="width:80%; height:450px;"></div>
            </el-col>
        </el-row>
    </section>
</div>
</template>

<script>
import echarts from "echarts";
import axios from "axios";

export default {
  data() {
    return {
      BestFundManagerChart: null,
      BestPortfolioChart: null
    };
  },

  methods: {
    /**
     * draw best fund manager
     * @param fundmanagerName
     * @param totalMoney
     */
    drawBestFundManager() {
      var arr = [];
      var total = [];

      axios({
        method: 'get',
        baseURL: '/api',
        url: 'api/report/getBestFM'
      }).then(res => {
        console.log('hello');
        console.log(res.bestFMs);
        for (var i = 0; i < res.bestFMs.length; i++) {
          console.log(res.bestFMs[i].soeid);
          arr.push(res.bestFMs[i].soeid);
          total.push(res.bestFMs[i].totalMoney);
          console.log(arr);
          console.log("totalMoney"+total);
          this.BestFundManagerChart = echarts.init(document.getElementById("BestFundManagerChart"));
          this.BestFundManagerChart.setOption({
            title: { text: "Best FundManager" },
            tooltip: {},
            toolbox: {
              show : true,
              feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
              }
            },
            calculable : true,
            xAxis: {
              type: 'category',
              data : arr

            },
            yAxis: {},
            series: [
              {
                name: "TotalMoney",
                type: "bar",
                barWidth : 50,
                data : total
              }
            ]
          });
        }
      })
    },

    /**
     * draw best portfolio
     */
    drawBestPortfolioChart() {
      var arr = [];
      var profit = [];

      axios({
        method: 'get',
        baseURL: '/api',
        url: 'api/report/getBestPortfolio'
      }).then(res => {
        console.log('hello');
        console.log(res);
        console.log(res.bestPortfolios);
        for (var i = 0; i < res.bestPortfolios.length; i++) {
          // console.log(res.bestPortfolios[i].fundmanagerID);
          // console.log(res.bestPortfolios[i].portfolioName);
          arr.push(res.bestPortfolios[i].fmId + '-' + res.bestPortfolios[i].ptfId);
          // console.log(res.bestPortfolios[i].Profit);
          profit.push(res.bestPortfolios[i].profit);
          // console.log(arr);
          // console.log(profit);
          this.BestFundManagerChart = echarts.init(document.getElementById("BestPortfolioChart"));
          this.BestFundManagerChart.setOption({
            title: { text: "Best Portfolio" },
            tooltip: {},
            toolbox: {
              show : true,
              feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
              }
            },
            calculable : true,
            xAxis: {
              type: 'category',
              data : arr,
              axisLabel: {
                interval:0,
                rotate:20
              }
            },
            yAxis: {
              type: 'value',
              axisLabel: {
                formatter: '{value}%',
                textStyle:{fontSize:'10'}
              }
            },
            series: [
              {
                name: "Profit",
                type: "bar",
                barWidth : 50,
                itemStyle:{
                  normal:{
                    color: '#FFB6C1'
                  }
                },
                data : profit
              }
            ]
          });
        }
      })
    },
    drawCharts() {
      this.drawBestFundManager();
      this.drawBestPortfolioChart();
    }
  },

  mounted: function() {
    this.drawCharts();
  },
  updated: function() {
    this.drawCharts();
  }
};
</script>

<style scoped>
.chart-container {
  width: 100%;
}
/*.chart div {
        height: 400px;
        float: left;
    }*/

.el-col {
  padding: 30px 20px;
}
</style>
