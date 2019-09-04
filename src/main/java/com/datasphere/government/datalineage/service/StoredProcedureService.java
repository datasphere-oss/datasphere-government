package com.datasphere.government.dl.service;

import com.datasphere.government.common.BaseService;
import com.datasphere.government.dl.common.DBConstant;
import com.datasphere.government.gsp.datalineage.entity.ConnectEntity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoredProcedureService extends BaseService {
    @Autowired MysqlService mysqlService;
    @Autowired OracleService oracleService;
    @Autowired PostgresService postgresService;
    @Autowired HiveService hiveService;

    public List<String> listAllStoredProcedure(ConnectEntity connectEntity) {
        List<String> result = null;
        if (DBConstant.MYSQL.equals(connectEntity.getDbType())) {
            result = mysqlService.listAllStoredProcedure();
        } else if (DBConstant.ORACLE.equals(connectEntity.getDbType())) {
            result = oracleService.listAllStoredProcedure();
        } else if (DBConstant.POSTGRESQL.equals(connectEntity.getDbType())) {
            result = postgresService.listAllStoredProcedure();
        } else if (DBConstant.GREENPLUM.equals(connectEntity.getDbType())) {
            result = postgresService.listAllStoredProcedure();
        } else if (DBConstant.HIVE.equals(connectEntity.getDbType())) {
            result = hiveService.listAllStoredProcedure();
        }
        return result;
    }

    class SecurityAccess {
        public void disopen() {

        }
    }
}
