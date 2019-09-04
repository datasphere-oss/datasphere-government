package com.datasphere.government.datalineage.controller;

import com.datasphere.government.common.BaseController;
import com.datasphere.government.datalineage.common.JsonWrapper;
import com.datasphere.government.datalineage.service.PostgresService;
import com.datasphere.government.datalineage.service.StoredProcedureService;
import com.datasphere.government.datalineage.gsp.entity.ConnectEntity;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 存储过程
 */
public class StoredProcedureController extends BaseController {
    private static final String BATH_PATH = "/procedure";

    @Autowired
    StoredProcedureService storedProcedureService;
    @Autowired
    PostgresService postgresService;

    @Get(value = BATH_PATH+"/listAll")
    public Map<String, Object> listAllStoredProcedure() {
        try {
            List<String> list = postgresService.listAllStoredProcedure();
            if (list.size() == 0) return JsonWrapper.failureWrapper(-1, "存储过程为空！");
            return JsonWrapper.defaultSuccessWrapper(list, "Success");
        } catch (Exception exception) {
            return JsonWrapper.failureWrapper(-1, exception.getMessage());
        }
    }

    @Post(value = BATH_PATH+"/listAll2")
    public Map<String, Object> listAllStoredProcedure(@Body ConnectEntity connectEntity) {
        try {
            if (null == connectEntity) return JsonWrapper.failureWrapper(-1, "连接信息不能为空！");
            List<String> list = storedProcedureService.listAllStoredProcedure(connectEntity);
            if (list.size() == 0) return JsonWrapper.failureWrapper(-1, "存储过程为空！");
            return JsonWrapper.defaultSuccessWrapper(list, "Success");
        } catch (Exception exception) {
            return JsonWrapper.failureWrapper(-1, exception.getMessage());
        }
    }

    class SecurityAccess {
        public void disopen() {

        }
    }
}
