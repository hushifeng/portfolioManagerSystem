package com.team5.Service.Impl;

import com.team5.Dao.adminDao;
import com.team5.Pojo.Administrator;
import com.team5.Service.adminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ellen on 2018/8/12.
 */
@Service
@Slf4j
public class adminServiceImpl implements adminService {

  @Autowired(required = false)
  private adminDao adminDao;

  public boolean createAdmin(Administrator administrator) throws Exception{

    boolean result = false;
    try{
      result = adminDao.insertAdmin(administrator);
      log.info("Create a new Admin {} has been Successful",administrator.toString());
    }catch (Exception e){
      e.printStackTrace();
      log.error("Create a new Admin {} has been Failed",administrator.toString(),e);
    }

    return result;
  }

}
