package com.training.service.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhangliujie
 * @Description
 * @date 2019/9/10.
 */

@Api(value = "Data控制器")
@Controller
@RequestMapping("/data")
public class SwaggerTest {

    @ApiOperation(value = "根据数据id查询质检信息", httpMethod = "GET", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = ResponseEntity.class)
    @ResponseBody
    @RequestMapping(value = "/{dataId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ReturnMsg> getInfo(@PathVariable("dataId") String dataId) {
        Assert.notNull(dataId);
        ReturnMsg msg = new ReturnMsg();
        msg.setCode(200);
        msg.setData("查询到的数据:" + dataId);
        msg.setMsg("查询成功");
        return ResponseEntity.ok(msg);
    }

}
