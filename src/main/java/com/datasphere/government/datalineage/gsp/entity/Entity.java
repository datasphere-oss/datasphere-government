package com.datasphere.government.gsp.datalineage.entity;

import lombok.Data;

/**
 * Created by jeq 5.
 */
@Data
public class Entity {
    private String database;
    private String table;
    /**
     * 实体类型
     */
    private String type;
    private String writeType;

    public Entity(String database, String table, String type, String writeType) {
        this.database = database;
        this.table = table;
        this.type = type;
        this.writeType = writeType;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWriteType() {
        return writeType;
    }

    public void setWriteType(String writeType) {
        this.writeType = writeType;
    }
}
