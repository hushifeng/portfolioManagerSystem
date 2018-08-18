package com.team5.Service;

import com.team5.Pojo.Report;
import com.team5.Pojo.bestFMVo;
import java.util.List;
import java.util.Map;

/**
 * Created by ellen on 2018/8/13.
 */
public interface getReportService {
  public List<bestFMVo> getBestFM() throws Exception;
  public List<Report> getBestPortfolio() throws Exception;
}
