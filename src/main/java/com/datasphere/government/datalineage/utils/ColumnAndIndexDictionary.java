package com.datasphere.government.datalineage.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ColumnAndIndexDictionary {
    public static Map<String, Integer> map = new HashMap<>();

    /**
     * 根据列索引查询对应的列类型
     * @param colIndex
     * @return
     */
    public static String postgresDictionary(ResultSet results, Integer colIndex) {
        String columnTypeName = "";
        switch (colIndex) {
            case -7:
            case 1:
            case 91: {
                columnTypeName = "date";
                break;
            }
            case 1111:
            case 7: {
                columnTypeName = "float4";
                break;
            }
            case 8:
            case 5: {
                columnTypeName = "int2";
                break;
            }
            case -5:
            case 2: {
                columnTypeName = "numeric";
                break;
            }
            case 12:
            case 92: {
                columnTypeName = "time";
                break;
            }
            case 93:
            case 2009: {
                columnTypeName = "xml";
                break;
            }
            case 4: {
                columnTypeName = "int4";
                break;
            }
            case 2012: {
                columnTypeName = "refcursor";
                break;
            }
            default: {
                try {
                    columnTypeName = results.getString(colIndex);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return columnTypeName;
    }

//1   a     abstime     1111
//2   b     aclitem     1111
//3   c     box     1111
//4   d     cid     1111
//5   e     cidr     1111
//6   f     circle     1111
//7   g     gtsvector     1111
//8   h     int2vector     1111
//9   i     interval     1111
//10   j     json     1111
//11   k     jsonb     1111
//12   l     line     1111
//13   m     lseg     1111
//14   n     macaddr     1111
//15   o     oid     -5
//16   p     oidvector     1111
//17   q     path     1111
//18   r     point     1111
//19   s     polygon     1111
//20   t     refcursor     2012
//21   u     regclass     1111
//22   v     regconfig     1111
//23   w     regdictionary     1111
//24   x     regnamespace     1111
//25   y     regoper     1111
//26   z     regoperator     1111
//1   a     regproc     1111
//2   b     regprocedure     1111
//3   c     regrole     1111
//4   d     regtype     1111
//5   e     reltime     1111
//6   f     smgr     1111
//7   g     tid     1111
//8   h     tinterval     1111
//9   i     tsquery     1111
//10   j     tsrange     1111
//11   k     tsvector     1111
//12   l     tstzrange     1111
//13   m     txid_snapshot     1111
//14   n     uuid     1111
//15   o     xid     1111
//1   a     bit     -7
//2   b     bool     -7
//3   c     char     1
//4   d     bpchar     1
//5   e     date     91
//6   f     daterange     1111
//7   g     float4     7
//8   h     float8     8
//9   i     int2     5
//10  j     int8     -5
//1   a     numeric     2
//2   b     text     12
//3   c     time     92
//4   d     timestamp     93
//5   e     timestamptz     93
//6   f     uuid     1111
//7   g     varchar     12
//8   h     varbit     1111
//9   i     xml     2009
//1   a     int4     4
//2   b     int4range     1111
//3   c     json     1111
//4   d     money     8
//5   e     name     12
//6   f     unknown     1111

}
