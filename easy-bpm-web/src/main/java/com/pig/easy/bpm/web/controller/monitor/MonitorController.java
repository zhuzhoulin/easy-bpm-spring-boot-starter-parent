package com.pig.easy.bpm.web.controller.monitor;

import com.pig.easy.bpm.auth.core.controller.BaseController;
import com.pig.easy.bpm.common.monitor.server.Server;
import com.pig.easy.bpm.common.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/1/27 16:14
 */
@RestController
@Api(tags = "监控管理", value = "监控管理")
@RequestMapping("/monitor/server")
public class MonitorController  extends BaseController {

    @ApiOperation(value = "查询服务器信息", notes = "查询服务器信息", produces = "application/json")
    @PostMapping("/getServerInfo")
    public JsonResult getServerInfo()  throws Exception {
        Server server = new Server();
        server.copyTo();
        return JsonResult.success(server);
    }
}
