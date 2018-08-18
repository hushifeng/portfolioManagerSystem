package com.team5.Controller;

import com.team5.Pojo.FundManager;
import com.team5.Service.Impl.fmServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by ellen on 2018/8/12.
 */

@Controller
@RequestMapping("api/FM/")
public class fmController {
    @Autowired
    private fmServiceImpl fmServiceImpl;

    @RequestMapping(value = "loginFundManager", method = {RequestMethod.GET})
    @ResponseBody
    public boolean loginFundManager(@RequestParam String soeid) throws Exception {
        if (fmServiceImpl.queryFM(soeid) != null){
            return true;
        }
        return false;
    }

    @RequestMapping(value = "insertFundManager", method = {RequestMethod.GET})
    @ResponseBody
    public boolean insertFundManager(@RequestParam String soeid, @RequestParam String name) throws Exception {
        return  fmServiceImpl.insertFM(new FundManager(soeid, name));
    }

    @RequestMapping(value = "updateFundManager", method = {RequestMethod.GET})
    @ResponseBody
    public boolean updateFundManager(@RequestParam String soeid, @RequestParam String name) throws Exception {
        return fmServiceImpl.updateFM(new FundManager(soeid, name));
    }

    @RequestMapping(value = "deleteFundManager", method = {RequestMethod.GET})
    @ResponseBody
    public boolean deleteFundManager(@RequestParam String soeid, @RequestParam String name) throws Exception {
        return fmServiceImpl.deleteFM(new FundManager(soeid, name));
    }

    @RequestMapping(value = "deleteManyFundManager", method = {RequestMethod.GET})
    @ResponseBody
    public boolean deleteManyFundManager(@RequestParam List<String> fundManagerIDList) throws Exception {
        return fmServiceImpl.deleteManyFM(fundManagerIDList);
    }

    @RequestMapping(value = "queryFundManager", method = {RequestMethod.GET})
    @ResponseBody
    public FundManager queryFundManager(@RequestParam String soeid) throws Exception {
        return fmServiceImpl.queryFM(soeid);
    }

    @RequestMapping(value = "queryAllFundManager", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryAllFundManager() throws Exception {
        return fmServiceImpl.queryAllFM();
    }
}
