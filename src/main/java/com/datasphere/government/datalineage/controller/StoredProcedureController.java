/*
 * Copyright 2019, Huahuidata, Inc.
 * DataSphere is licensed under the Mulan PSL v1.
 * You can use this software according to the terms and conditions of the Mulan PSL v1.
 * You may obtain a copy of Mulan PSL v1 at:
 * http://license.coscl.org.cn/MulanPSL
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 * PURPOSE.
 * See the Mulan PSL v1 for more details.
 */

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
    public Map<String, Object> listAllStoredProcedure(@RequestBody ConnectEntity connectEntity) {
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
