package com.team5.Pojo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by ellen on 2018/8/12.
 */
/*@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor*/
public class Administrator implements Serializable {

  private String soeid;
  private String name;

  public String getSoeid() {
    return soeid;
  }

  public void setSoeid(String soeid) {
    this.soeid = soeid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Administrator(String soeid, String name) {
    this.soeid = soeid;
    this.name = name;
  }

  public Administrator() {
  }
}
