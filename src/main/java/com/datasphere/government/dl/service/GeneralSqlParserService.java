package com.datasphere.government.dl.service;

import gudusoft.gsqlparser.EDbVendor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datasphere.government.common.BaseService;
import com.datasphere.government.dl.common.DBConstant;
import com.datasphere.government.dl.common.JsonWrapper;
import com.datasphere.government.dl.test.DemoData;
import com.datasphere.government.gsp.dlineage.ParseLineage;
import com.datasphere.government.gsp.dlineage.entity.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GeneralSqlParserService extends BaseService {
    private static boolean firstNode = false;

    @Autowired
    PostgresService postgresService;

    public JSONArray getAll() {
        JSONArray jsonArray = parseLineageParseResult(DemoData.test2, "oracle");
        return jsonArray;
    }

    public JSONArray getByFileName(String dbType, String fileName) {
        JSONArray jsonArray = parseLineageParseResult(readFile(dbType, fileName), dbType);
        return jsonArray;
    }

    public static void main(String[] args) {
        GeneralSqlParserService a = new GeneralSqlParserService();
        System.out.println(a.getByFileName("Hive", "hive2.sql"));
    }

    public JSONArray getByProName(String proname) {
        Map<String, String> map  = postgresService.listStoredProcedureByName(proname);
//        String prefix = "CREATE FUNCTION "+proname+"(subtotal real) RETURNS real AS $$  \n";
        //1043 20 {ids,userid}
//        String suffix = "$$ LANGUAGE plpgsql;";
        JSONArray jsonArray = parseLineageParseResult(map.get("prosrc").toString(), this.dbTypePG);
        return jsonArray;
    }

    public JSONArray parseLineageParseResult(String prosrc, String dbType) {
        if ("".equals(prosrc)) return new JSONArray();
        ConnectEntity connectEntity = new ConnectEntity();
        connectEntity.setSchemaName(this.schemaNamePG);
        connectEntity.setUrl(this.jdbcUrlPG);
        connectEntity.setUserName(this.usernamePG);
        connectEntity.setPassword(this.passwordPG);

        List<LineageParseResult> lprList;
        if (DBConstant.POSTGRESQL.equals(dbType)) {
            lprList = dataLinkage(prosrc, EDbVendor.dbvpostgresql, connectEntity);
        } else if(DBConstant.ORACLE.equals(dbType)) {
            lprList = dataLinkage(prosrc, EDbVendor.dbvoracle, connectEntity);
        } else if(DBConstant.MYSQL.equals(dbType)) {
            lprList = dataLinkage(prosrc, EDbVendor.dbvmysql, connectEntity);
        } else if(DBConstant.HIVE.equals(dbType)) {
            lprList = dataLinkage(prosrc, EDbVendor.dbvhive, connectEntity);
        } else {
            lprList = new ArrayList<>();
        }
//        else if(DBConstant.SQL_SERVER.equals(dbType)) {
//            lprList = dataLinkage(prosrc, EDbVendor.dbvmssql, connectEntity);
//        } else if(DBConstant.DB2.equals(dbType)) {
//            lprList = dataLinkage(prosrc, EDbVendor.dbvdb2, connectEntity);
//        } else if(DBConstant.GREENPLUM.equals(dbType)) {
//            lprList = dataLinkage(prosrc, EDbVendor.dbvgreenplum, connectEntity);
//        } else if(DBConstant.HIVE.equals(dbType)) {
//            lprList = dataLinkage(prosrc, EDbVendor.dbvhive, connectEntity);
//        }
        if (lprList == null || lprList.size() == 0) return null;
        JSONArray jsonArray = new JSONArray();
        for (LineageParseResult lpr:lprList) {
            if (lpr == null) continue;
            JSONObject obj = new JSONObject();
            JSONArray nodesArray = new JSONArray();
            JSONArray edgesArray = new JSONArray();
            MegrezLineageDto megrezLineageDto = lpr.getMegrezLineageDto();
            List<MegrezLineageEdge> edgeList = null;
            if (megrezLineageDto != null) edgeList = megrezLineageDto.getEdges();
            if (edgeList != null) {
                for (MegrezLineageEdge edge : edgeList) {
                    List<String> sourceList = edge.getSources();
                    if (sourceList.size() != 0) nodesArray = listSplit2Array(nodesArray, sourceList);
                    firstNode = true;
                    List<String> targetList = edge.getTargets();
                    if (targetList.size() != 0) nodesArray = listSplit2Array(nodesArray, targetList);
                    firstNode = false;
                    edgesArray = toEdge(edgesArray, sourceList, targetList);
                }
            }
            if (nodesArray.length() != 0) obj.put("nodes", nodesArray);
            if (edgesArray.length() != 0) obj.put("edges", edgesArray);
            if (obj.length() != 0) jsonArray.put(obj);
        }
        return jsonArray;
    }

    /**
     * 使用BufferedReader类读文本文件
     */
    public String readFile(String dbType, String fileName) {
        StringBuilder sqls = new StringBuilder();
//        String fullPath = this.getClass().getResource(File.separator+"uploadFiles"+File.separator).getPath()+fileName;
        String fullPath = null;
        if (DBConstant.MYSQL.equals(dbType)) {
            fullPath = "uploadFiles"+File.separator+"mysql"+File.separator+fileName;//文件的相对路径Relatively Path
        } else if (DBConstant.ORACLE.equals(dbType)) {
            fullPath = "uploadFiles"+File.separator+"oracle"+File.separator+fileName;
        } else if (DBConstant.POSTGRESQL.equals(dbType)) {
            fullPath = "uploadFiles"+File.separator+"postgresql"+File.separator+fileName;
        } else if (DBConstant.GREENPLUM.equals(dbType)) {
            fullPath = "uploadFiles"+File.separator+"greenplum"+File.separator+fileName;
        }  else if (DBConstant.HIVE.equals(dbType)) {
            fullPath = "uploadFiles"+File.separator+"hive"+File.separator+fileName;
        } else {
            fullPath = "uploadFiles"+File.separator+"others"+File.separator+fileName;
        }
        String line;
        try {
            BufferedReader in = new BufferedReader(new FileReader(fullPath));
            line = in.readLine();
            while (line != null) {
                sqls.append(line+" \n");
                line = in.readLine();
            }
            in.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            sqls = new StringBuilder();
        }
        return sqls.toString();
    }

    private JSONArray toEdge(JSONArray arr, List<String> sourceList, List<String> targetList) {
        boolean firstAdd = false;
        JSONObject obj;
        JSONObject obj2;
        JSONObject obj3;
        JSONObject obj4;
        for (String source:sourceList) {
            String[] strArr = source.split("\\.");
            String db_name = strArr[0];
            if (db_name.equals("null")) db_name = "default";
            obj = new JSONObject();
            obj2 = new JSONObject();
            String table_name;
            String field_name;
            if (strArr.length == 3) {
                table_name = strArr[1];
                field_name = strArr[2];
                obj.put("target", table_name);// 库-表
                obj2.put("source", table_name);// source表-字段
            } else {
                String schema_name = strArr[1];
                table_name = strArr[2];
                field_name = strArr[3];
                obj.put("target", schema_name+"."+table_name);// 库-表
                obj2.put("source", schema_name+"."+table_name);// source表-字段
            }
            obj.put("source", db_name);
            obj.put("value", 0.08);
            obj2.put("target", field_name);
            obj2.put("value", 0.06);
            if(!firstAdd && arr.length() == 0) {
                arr.put(obj);
                firstAdd = true;
            } else {
                if (!arr.toString().contains(obj.toString())) arr.put(obj);
            }
            if (arr.length() != 0 && !arr.toString().contains(obj2.toString())) arr.put(obj2);
            for (String target:targetList) {
                String[] strArr2 = target.split("\\.");
//                String db_name2 = strArr2[0];
                String table_name2;
                String field_name2;
                obj3 = new JSONObject();
                obj4 = new JSONObject();
                if (strArr2.length == 3) {
                    table_name2 = strArr2[1];
                    field_name2 = strArr2[2];
                    obj3.put("target", table_name2);// 库-表
                    obj4.put("source", table_name2);// source表-字段
                    obj4.put("target", table_name2+"."+field_name2);
                } else {
                    String schema_name2 = strArr2[1];
                    table_name2 = strArr2[2];
                    field_name2 = strArr2[3];
                    obj3.put("target", schema_name2+"."+table_name2);// 库-表
                    obj4.put("source", schema_name2+"."+table_name2);// source表-字段
                    obj4.put("target", schema_name2+"."+table_name2+"."+field_name2);
                }
                //source字段-target表
                obj3.put("source", field_name);
//                obj3.put("target", table_name2);
                obj3.put("value", 0.08);
                //target表-target表.字段
//                obj4.put("source", table_name2);
                obj4.put("value", 0.06);
                if (arr.length() != 0 && !arr.toString().contains(obj3.toString())) arr.put(obj3);
                if (arr.length() != 0 && !arr.toString().contains(obj4.toString())) arr.put(obj4);
            }
        }
        return arr;
    }

    private JSONArray listSplit2Array(JSONArray arr, List<String> list) {
        for (String s:list) {
            String[] strArr = s.split("\\.");
            String db_name = strArr[0];
            if (db_name.equals("null")) db_name = "default";
            String table_name;
            String field_name;
            if (strArr.length == 3) {
                table_name = strArr[1];
                field_name = strArr[2];
                arr = putObj(arr, db_name);
                arr = putObj(arr, table_name);
                arr = putObj(arr, field_name);
                if (firstNode) {
                    String table_field = table_name+"."+field_name;
                    arr = putObj(arr, table_field);
                }
            } else {
                String schema_name = strArr[1];
                table_name = strArr[2];
                field_name = strArr[3];
                arr = putObj(arr, db_name);
                arr = putObj(arr, schema_name+"."+table_name);
                arr = putObj(arr, field_name);
                if (firstNode) {
                    String schema_table_field = schema_name+"."+table_name+"."+field_name;
                    arr = putObj(arr, schema_table_field);
                }
            }
        }
        return arr;
    }

    private JSONArray putObj(JSONArray arr, String value) {
        JSONObject obj;
        if (!arr.toString().contains("\""+value+"\"")) {
            obj = new JSONObject();
            obj.put("name", value);
            arr.put(obj);
        }
        return arr;
    }

    public List<LineageParseResult> dataLinkage(String sqls, EDbVendor dbType, ConnectEntity connectEntity) {
        ParseLineage parseLineage = new ParseLineage();
        return parseLineage.parseSqlsLineage(sqls,dbType,connectEntity);
    }

    class SecurityAccess {
        public void disopen() {

        }
    }
}
