package com.team5;

import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;

import com.team5.Dao.Impl.PositionDaoImpl;
import com.team5.Pojo.Position;
/*import org.junit.Before;
import org.junit.Test;*/
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mac on 2018/8/11.
 */
public class positionDaoImplTest {


    private static ApplicationContext applicationContext;

    @BeforeAll
    public void setUp() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    @Test
    public void testCreatePosition() throws Exception {

        Position position  = new Position("GOOG",11,70,
                                            1,"kk","USD","2018-08-10");
        PositionDaoImpl PositionDaoImpl = (PositionDaoImpl) applicationContext.getBean("positionDao");
        boolean result = PositionDaoImpl.createPosition(position);
        System.out.println(result+":"+position.toString());
    }


    @Test
    public void testDeletePosition() throws Exception {
        PositionDaoImpl positionDaoImpl = (PositionDaoImpl) applicationContext.getBean("positionDao");
        boolean result = positionDaoImpl.deletePosition(2);
    }

/*    @Test
    public void testUpdatePosition() throws Exception {
        Position position  = new Position("GOOG",112221,70,
                1,"kk","USD","2018-08-10");
        position.setId(9);
        PositionDaoImpl positionDaoImpl = (PositionDaoImpl) applicationContext.getBean("positionDao");
        boolean result = positionDaoImpl.updatePosition(position);
    }*/


    @Test
    public void testQueryPosition() throws Exception {
        PositionDaoImpl positionDaoImpl = (PositionDaoImpl) applicationContext.getBean("positionDao");
        List<Position> result = positionDaoImpl.queryPosition(1);
        System.out.println(result);
    }

    @Test
    public void testDeletePositions() throws Exception {
        PositionDaoImpl positionDaoImpl = (PositionDaoImpl) applicationContext.getBean("positionDao");
        List<Integer> list = new ArrayList<Integer>(){{add(3);add(4);}};
        boolean result = positionDaoImpl.deletePositions(list);
    }

    @Test
    public void testChangePriceToTerm(){
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        PositionDaoImpl positionDaoImpl = (PositionDaoImpl) applicationContext.getBean("positionDao");
        System.out.println(positionDaoImpl.changePriceToTerm("GDP","2018-08-14"));

    }

    @Test
    public void testQueryTotalPrice() throws Exception{
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        PositionDaoImpl positionDaoImpl = (PositionDaoImpl) applicationContext.getBean("positionDao");
        System.out.println(positionDaoImpl.queryTotalPrice(11,"2018-08-17"));

    }

}