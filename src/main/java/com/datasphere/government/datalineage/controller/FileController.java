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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.datasphere.government.common.BaseController;
import com.datasphere.government.datalineage.common.DBConstant;
import com.datasphere.government.datalineage.common.JsonWrapper;
import com.datasphere.government.datalineage.service.FileService;

import java.io.IOException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * 文件
 */
public class FileController extends BaseController{
    private static final Logger log = LoggerFactory.getLogger(FileController.class);
    private static final String BATH_PATH = "/file";
    @Autowired
    FileService fileService;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @Post(value = BATH_PATH+"/upload", produces = "text/json;charset=utf-8", consumes = MediaType.MULTIPART_FORM_DATA)
    public HttpResponse<Object> uploadCompleted(CompletedFileUpload file, @Parameter String dbType) {
        try {
            String fileName = file.getFilename();
            String fileRelativePath = null;
            if (DBConstant.MYSQL.equals(dbType)) {
                fileRelativePath = "uploadFiles"+File.separator+"mysql"+File.separator+fileName;//文件的相对路径Relatively Path
            } else if (DBConstant.ORACLE.equals(dbType)) {
                fileRelativePath = "uploadFiles"+File.separator+"oracle"+File.separator+fileName;//文件的相对路径Relatively Path
            } else if (DBConstant.POSTGRESQL.equals(dbType)) {
                fileRelativePath = "uploadFiles"+File.separator+"postgresql"+File.separator+fileName;//文件的相对路径Relatively Path
            } else if (DBConstant.HIVE.equals(dbType)) {
                fileRelativePath = "uploadFiles"+File.separator+"hive"+File.separator+fileName;//文件的相对路径Relatively Path
            } else if (DBConstant.GREENPLUM.equals(dbType)) {
                fileRelativePath = "uploadFiles"+File.separator+"greenplum"+File.separator+fileName;//文件的相对路径Relatively Path
            } else {
                return HttpResponse.badRequest(JsonWrapper.failureWrapper(-1, "Upload Failed：暂不支持"+dbType+"数据库！"));
            }
            File dir = new File(fileRelativePath);
            if (dir.exists()) dir.delete();
            Path path = Paths.get(dir.getAbsolutePath());//根据相对路径获取绝对路径
            Files.write(path, file.getBytes());// 写入文件
            return HttpResponse.ok(JsonWrapper.defaultSuccessWrapper(dir.getName(), "Success"));
        } catch (IOException e) {
            e.printStackTrace();
            return HttpResponse.badRequest(JsonWrapper.failureWrapper(-1, "Upload Failed："+e.getMessage()));
        }
    }

    /**
     * 查询所有已上传文件
     * @return
     */
    @Get(value = BATH_PATH+"/listAll")
    public Map<String, Object> listAllFiles() {
        try {
            String filePath = "uploadFiles" + File.separator;//文件的相对路径Relatively Path
            Map<String, List<String>> map = fileService.listAll(filePath);
            if (map.size() == 0) return JsonWrapper.failureWrapper(-1, "暂无文件");
            return JsonWrapper.defaultSuccessWrapper(map, "Success");
        } catch (Exception exception) {
            return JsonWrapper.failureWrapper(-1, exception.getMessage());
        }
    }

    class SecurityAccess {
        public void disopen() {

        }
    }
}
