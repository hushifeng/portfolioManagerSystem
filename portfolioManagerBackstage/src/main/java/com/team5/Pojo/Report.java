package com.team5.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Report {

  String fmId;
  int ptfId;
  double profit;


  public int getPtfId() {
    return ptfId;
  }

  public void setPtfId(int ptfId) {
    this.ptfId = ptfId;
  }


  public Report(int ptfId, double profit) {
    this.ptfId = ptfId;
    this.profit = profit;
  }

}
