package com.datasphere.government.datalineage.gsp;

import com.datasphere.government.datalineage.gsp.Connection.GreenplumUtils;
import com.datasphere.government.datalineage.gsp.Connection.MysqlUtils;
import com.datasphere.government.datalineage.gsp.Connection.OracleUtils;
import com.datasphere.government.datalineage.gsp.entity.*;
import com.datasphere.government.datalineage.gsp.entity.xmls.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import gudusoft.gsqlparser.EDbVendor;
import gudusoft.gsqlparser.TGSqlParser;
import gudusoft.gsqlparser.TStatementList;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class ParseLineage {
    private static final Logger logger = LoggerFactory.getLogger(ParseLineage.class);

    public  void getStoreFunctionInDb(String sql,EDbVendor dbType,ConnectEntity connectEntity) {
        // 正则 必须以 空格开头，紧接着中间字符串一次到多个，紧接着没有空格，紧接着是左括号，中间一些东西，右括号
        List<String> matchedKeys = RegexUtil.regexSelectList(sql, "\\s{1}\\S+\\s{0}\\((.*?)\\)");
        for (String functionString : matchedKeys) {
            String functionName = functionString.split("\\(")[0];
            String functionParmms = functionString.split("\\(")[1];
            String params = functionParmms.substring(0, functionParmms.length() - 1);

            String sqlInDb = "";
            if(dbType == EDbVendor.dbvgreenplum){
                sqlInDb = GreenplumUtils.getStoreFunctionInDb(functionName,  connectEntity);
            } else if(dbType == EDbVendor.dbvoracle){
                sqlInDb = OracleUtils.getStoreFunctionInDb(functionName,  connectEntity);
            } else if(dbType == EDbVendor.dbvmysql){
                sqlInDb = MysqlUtils.getStoreFunctionInDb(functionName,  connectEntity);
            }
            parseSqlsLineage( sqlInDb, dbType, connectEntity);
        }
    }

    /**
     * sql 去除注释
     * @param source
     * @return
     */
    private String removeAnnotation(String source) {
        String[] lines = source.split("\n");
        StringBuilder resultBuilder = new StringBuilder();

        //包含 "--" 的为注释，去除
        for (String line : lines) {
            if (StringUtils.isNoneEmpty(line) && !line.trim().startsWith("--")) {
                resultBuilder.append(line).append("\n");
            }
        }
        return resultBuilder.toString();
    }

    public List<LineageParseResult> parseSqlsLineage(String sqlInDb,EDbVendor dbType,ConnectEntity connectEntity) {
        sqlInDb = removeAnnotation(sqlInDb);
        TGSqlParser sqlParser = new TGSqlParser(dbType);
        sqlParser.sqltext = sqlInDb;
        sqlParser.parse();
        /*int rs = sqlParser.checkSyntax();
        if(rs != 0) {
            System.err.println("!!!语法错误："+sqlParser.getErrormessage());
            return null;
        }
        System.out.println("语法正确！");*/
        TStatementList stList = sqlParser.sqlstatements;
        logger.info("共有条"+stList.size()+"sql语句!");
        System.out.println("1.共有条"+stList.size()+"sql语句");
        List<LineageParseResult> lineageParseResultList = new ArrayList<>();
        LineageParseResult lineageParseResult = new LineageParseResult();
        for(int i=0; i<stList.size(); i++){
            String sql = stList.get(i).toString();
            if (sql.contains("BEGIN") && !sql.contains("END")) {
                sql = sql.replaceAll("BEGIN", "");
            } else if(!sql.contains("BEGIN") && sql.contains("END;")) {
                sql = sql.replaceAll("END;", "");
            }
            System.out.println("-->SQL"+(i+1)+":\r\n"+sql);
            lineageParseResult = parseLineage(sql,dbType, connectEntity);
            lineageParseResultList.add(lineageParseResult);
        }
        return lineageParseResultList;
    }

    public LineageParseResult parseLineage(String sql,EDbVendor dbType,ConnectEntity connectEntity) {
        DataFlowAnalyzer dataFlowAnalyzer = new DataFlowAnalyzer( sql,dbType,true );
        StringBuffer errorBuffer = new StringBuffer();
        String result = dataFlowAnalyzer.generateDataFlow( errorBuffer );
        if ( result != null ) {
            System.out.println( "2.解析结果==>\r\n"+result );
            // 没有血缘信息的
            if(result.equals("<dlineage/>")) return null;
            return transformXMLtoObject(result, sql, dbType, connectEntity);
        }
        return null;
    }

    /**
     * 解析xml,构造血缘对象
     * <dlineage>
     * 	 <relation id="3" type="dataflow">
     * 	    <target coordinate="[1,27],[1,31]" column="NAME" id="5" parent_id="1" parent_name="TABLEA"/>
     * 	    <source coordinate="[1,27],[1,31]" column="NAME" id="1" parent_id="2" parent_name="B"/>
     * 	 </relation>
     * 	 <relation id="4" type="dataflow">
     * 	    <target coordinate="[1,32],[1,35]" column="AGE" id="6" parent_id="1" parent_name="TABLEA"/>
     * 	    <source coordinate="[1,32],[1,35]" column="AGE" id="2" parent_id="2" parent_name="B"/>
     * 	 </relation>
     * 	 <table name="TABLEA" id="1" type="table" coordinate="[1,13],[1,19]">
     * 	    <column name="NAME" id="5" coordinate="[1,27],[1,31]"/>
     * 	    <column name="AGE" id="6" coordinate="[1,32],[1,35]"/>
     * 	 </table>
     * 	 <table name="B" id="2" type="table" coordinate="[1,42],[1,43]">
     * 	    <column name="NAME" id="1" coordinate="[1,27],[1,31]"/>
     * 	    <column name="AGE" id="2" coordinate="[1,32],[1,35]"/>
     * 	 </table>
     * </dlineage>
     */
    public LineageParseResult transformXMLtoObject(String result,String sql ,EDbVendor dbType,ConnectEntity connectEntity) {
        result = result.replaceAll("dlineage","DlineageEntity");
        result = result.replaceAll("parent_id","parentId");
        result = result.replaceAll("parent_name","parentName");
        System.out.println( "3.1 replace to UPPER： \r\n"+result );
//        http://www.coin163.com/it/6547353498740415747/Xsream
        XStream xstream = new XStream(new DomDriver());
        xstream.autodetectAnnotations(true);
        xstream.processAnnotations(DlineageEntity.class);
        xstream.addPermission(AnyTypePermission.ANY);
//        Class<?>[] classes = new Class[] { DlineageEntity.class };
//        XStream xstream = new XStream();
//        XStream.setupDefaultSecurity(xstream);
//        xstream.allowTypes(classes);
        DlineageEntity dlineageEntity = null;
        try {
            dlineageEntity = (DlineageEntity) xstream.fromXML(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println( "3.2 转换XML为Object：\r\n"+dlineageEntity.toString());
        return transformtoMySelfLineage(dlineageEntity, sql, dbType, connectEntity);
    }

    /**
     * 补全SQL
     * @param dlineageEntity
     * @param sql
     * @param dbType
     */
    private void completionSql(DlineageEntity dlineageEntity, String sql, EDbVendor dbType,ConnectEntity connectEntity) {
        List<Table> tables = dlineageEntity.getTable();

        List<String> tableIds = new ArrayList<>();
        if (tableIds == null) return;
        for( Table table : tables){
            tableIds.add(table.getId());
        }

        for( Table table : tables){
            String tableName = table.getName();
            String tableCoordinate = table.getCoordinate();
            tableCoordinate = tableCoordinate.replaceAll("\\[","");
            tableCoordinate = tableCoordinate.replaceAll("\\]","");
            String[] tableIndex = tableCoordinate.split(",");

            List<Column> columns = table.getColumn();
            for( Column column : columns){
                String columnName = column.getName();
                String columnId = column.getId();
                if(columnName.equals("*")){
                    String columnsInDb = getTableNames(tableName, dbType, connectEntity);
                    String columnCoordinate = column.getCoordinate();
                    columnCoordinate = columnCoordinate.replaceAll("\\[","");
                    columnCoordinate = columnCoordinate.replaceAll("\\]","");
                    String[] columnIndex = columnCoordinate.split(",");
                    int columnIndexInt = Integer.parseInt(columnIndex[1]);
                    String indexString = sql.substring(columnIndexInt-1, columnIndexInt);
                    StringBuilder sb = new StringBuilder(sql);//构造一个StringBuilder对象
                    if(tableIds.contains(columnId)) {
                        columnIndexInt = columnIndexInt-1;
                        columnsInDb = " "+columnsInDb+" ";
                        sb.replace(columnIndexInt,columnIndexInt+1,columnsInDb);
                    } else {
                        int tableIndexInt = Integer.parseInt(tableIndex[3]);
                        columnsInDb = " ("+columnsInDb+") ";
                        sb.insert(tableIndexInt, columnsInDb);
                    }
                    sql = sb.toString();
                    System.out.println("替换*号后的sql:"+sql);
                    parseLineage(sql,dbType,connectEntity);
                }
            }
        }
    }

    /**
     * 转换成自己需要的血缘格式
     * @param dlineageEntity
     */
    private LineageParseResult transformtoMySelfLineage(DlineageEntity dlineageEntity,String sql,EDbVendor dbType,ConnectEntity connectEntity) {
        completionSql(dlineageEntity, sql, dbType, connectEntity);
        String dbName = connectEntity.getDbName();

        LineageParseResult lineageParseResult = new LineageParseResult();
        MegrezLineageDto megrezLineageDto = new MegrezLineageDto();
        Set<String> inputSet = new HashSet<>();
        Set<String> outputSet = new HashSet<>();
        List<String> fieldList = new ArrayList<>();
        List<Relation> relations = dlineageEntity.getRelation();
        List<MegrezLineageEdge> edges = new ArrayList<>();
        if(relations != null){
            for (Relation relation: relations){
                List<Target> targets = relation.getTarget();
                List<Source> sources = relation.getSource();

                MegrezLineageEdge edge = new MegrezLineageEdge();

                List<String> sourcesList = new ArrayList<>();/**边source顶点ID*/
                List<String> targetsList = new ArrayList<>();/**边sink顶点ID*/
                for(Source source : sources) {
                    String inputColumn = source.getColumn();
                    String inPutTableName = source.getParentName();
                    inputSet.add(inPutTableName);
                    sourcesList.add(dbName+"."+inPutTableName+"."+inputColumn);
                    fieldList.add(dbName+"."+inPutTableName+"."+inputColumn);
                }
                for(Target target : targets) {
                    String targetColumn = target.getColumn();
                    String outPutTableName = target.getParentName();
                    outputSet.add(outPutTableName);
                    targetsList.add(dbName+"."+outPutTableName+"."+targetColumn);
                    fieldList.add(dbName+"."+outPutTableName+"."+targetColumn);
                }
                edge.setSources(sourcesList);
                edge.setTargets(targetsList);
                edges.add(edge);
            }
            megrezLineageDto.setEdges(edges);
            lineageParseResult.setMegrezLineageDto(megrezLineageDto);
            lineageParseResult.setFieldList(fieldList);
            lineageParseResult.setInputList(hashSetToList(inputSet,dbName));
            lineageParseResult.setOutputList(hashSetToList(outputSet,dbName));
            System.err.println("4 转换成自己需要的血缘格式：\r\n"+lineageParseResult);
        }
        return lineageParseResult;
    }

    /**
     * 将Set集合转换成list，因为不能强转
     * @param oldSet
     * @return
     */
    public List<Entity> hashSetToList(Set<String> oldSet,String dbName)  {
        List<Entity>  list = new ArrayList<>();
        for(String table : oldSet ){
            Entity entity = new Entity(dbName,table,null,null);
            list.add(entity);
        }
        return list;
    }

    public  String getTableNames(String TableName, EDbVendor dbType,ConnectEntity connectEntity) {
        String columnsInDb = "";
        if(dbType == EDbVendor.dbvgreenplum) {
            columnsInDb = GreenplumUtils.getTableNames(TableName, connectEntity);
        } else if(dbType == EDbVendor.dbvoracle) {
            columnsInDb = OracleUtils.getTableNames(TableName, connectEntity);
        } else if (dbType == EDbVendor.dbvmysql) {
            columnsInDb = MysqlUtils.getTableNames(TableName, connectEntity);
        } else if (dbType == EDbVendor.dbvpostgresql) {
            columnsInDb = GreenplumUtils.getTableNames(TableName, connectEntity);
        } else {
            columnsInDb = GreenplumUtils.getTableNames(TableName, connectEntity);
        }
        return columnsInDb;
    }

    class SecurityAccess {
        public void disopen() {

        }
    }
}
