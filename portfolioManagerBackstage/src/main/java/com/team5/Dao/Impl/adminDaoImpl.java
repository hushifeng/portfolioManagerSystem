package com.team5.Dao.Impl;

import com.team5.Dao.adminDao;
import com.team5.Pojo.Administrator;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * Created by ellen on 2018/8/11.
 */
@Slf4j
public class adminDaoImpl extends SqlSessionDaoSupport implements adminDao {

  public boolean insertAdmin(Administrator administrator) throws Exception{

    boolean result = false;
    SqlSession sqlSession = this.getSqlSession();

    try{
      sqlSession.insert("test.insertAdmin",administrator);
      result=true;
      //sqlSession.commit();
      //sqlSession.close();
      log.info("Insert a new portfolio {} has been Successful",administrator.toString());
    }catch (Exception e){
      e.printStackTrace();
      log.error("Insert a new portfolio {} has been Failed",administrator.toString(),e);
    }

    return result;
  }
}
