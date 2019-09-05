package com.datasphere.government.datalineage.service;

import com.datasphere.government.common.BaseService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class FileService extends BaseService{
    Map<String, List<String>> map = new HashMap<>();

    public Map<String,List<String>> listAll(String filePath) {
        List<String> db = new ArrayList<>();
//        String filePath = "uploadFiles" + File.separator+"mysql"+File.separator;//文件的相对路径Relatively Path
        File dir = new File(filePath);
        File[] fileList = dir.listFiles();
        if (fileList == null) ;
        for (File file : fileList) {
            if (file.isDirectory()) {
                listAll(file.getAbsolutePath());
            }
            if (file.isFile()) {
                if(!file.getName().equals(".DS_Store")) db.add(file.getName());
                if (file.getAbsolutePath().contains("/mysql/")) map.put("MySQL", db);
                if (file.getAbsolutePath().contains("/oracle/")) map.put("Oracle", db);
                if (file.getAbsolutePath().contains("/postgresql/")) map.put("PostgreSQL", db);
                if (file.getAbsolutePath().contains("/greenplum/")) map.put("Greenplum", db);
                if (file.getAbsolutePath().contains("/hive/")) map.put("Hive", db);
                if (file.getAbsolutePath().contains("/others/")) map.put("其他", db);
            }
        }
        return map;
    }

    class SecurityAccess {
        public void disopen() {

        }
    }
}
