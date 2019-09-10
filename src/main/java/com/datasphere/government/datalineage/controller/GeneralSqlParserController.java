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

import com.alibaba.fastjson.JSON;
import com.datasphere.government.common.BaseController;
import com.datasphere.government.datalineage.service.GeneralSqlParserService;


import io.reactivex.Single;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 解析-数据血缘
 */
public class GeneralSqlParserController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(GeneralSqlParserController.class);

    @Autowired
    GeneralSqlParserService generalSqlParserService;

    @Get("/parser")
    public Single<Object> getAll() {//@Body ConnectEntity connectEntity
        return Single.fromCallable(() -> {
            try {
                JSONArray jsonArray = generalSqlParserService.getAll();
                System.err.println(jsonArray);
                if (jsonArray == null) return Single.just(new JSONArray());
                return Single.just(JSON.parseArray(jsonArray.toString()));
            } catch (Exception e) {
                e.printStackTrace();
                return Single.just(new JSONArray());
            }
        });
    }

    /**
     * 获取多个血缘关系
     * @param fileName
     * @return
     */
    @Post(value = "/parserMoreByFileName", produces = "text/json;charset=utf-8")
    public Single<Object> getMoreByFileName(@Parameter String dbType, @Parameter String fileName) {//@Body ConnectEntity connectEntity,
        return Single.fromCallable(() -> {
            try {
                if (StringUtils.isBlank(dbType) || StringUtils.isBlank(fileName)) {
                    log.error("入参不能为空！dbType="+dbType+",fileName="+fileName);
                    return Single.just(new JSONArray());
                }
                JSONArray jsonArray = generalSqlParserService.getByFileName(dbType, fileName);
                if (jsonArray == null) {
                    System.err.println("本次解析出[0]个血缘关系！");
                } else {
                    System.err.println("本次解析出["+jsonArray.length()+"]个血缘关系！");
                }
                if (jsonArray == null) return Single.just(new JSONArray());
                // @TODO 给上海工行解析的数据血缘限制为一个
                JSONArray aaa = new JSONArray();
                aaa.put(jsonArray.get(0));
                return Single.just(JSON.parseArray(aaa.toString()));
            } catch (Exception e) {
                e.printStackTrace();
                return Single.just(new JSONArray());
            }
        });
    }

    /**
     * 3-根据存储过程名称查询血缘关系
     * @param proname
     * @return
     */
    @Post("/parserByProName")
    public Single<Object> getByProName(@Parameter String proname) {
        return Single.fromCallable(() -> {
            try {
                JSONArray jsonArray = generalSqlParserService.getByProName(proname);
                System.err.println("本次解析出["+jsonArray.length()+"]个血缘关系！");
                if (jsonArray == null) return Single.just(new JSONArray());
                return Single.just(JSON.parseArray(jsonArray.toString()));
            } catch (Exception e) {
                e.printStackTrace();
                return Single.just(new JSONArray());
            }
        });
    }

    class SecurityAccess {
        public void disopen() {

        }
    }
}
