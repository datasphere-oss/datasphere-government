package com.datasphere.government.gsp.datalineage.entity.xmls;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.List;

@Data
@XStreamAlias("DlineageEntity") //对应dlineage元素
public class DlineageEntity {
    // 对应relation
    @XStreamImplicit(itemFieldName = "relation")
    private List<Relation> relation;

    // 对应table
    @XStreamImplicit(itemFieldName = "table")
    private List<Table> table;

    // 对应resultset
    @XStreamImplicit(itemFieldName = "resultset")
    private List<Resultset> resultset;


    public List<Relation> getRelation() {
        return relation;
    }

    public void setRelation(List<Relation> relation) {
        this.relation = relation;
    }

    public List<Table> getTable() {
        return table;
    }

    public void setTable(List<Table> table) {
        this.table = table;
    }

    public List<Resultset> getResultset() {
        return resultset;
    }

    public void setResultset(List<Resultset> resultset) {
        this.resultset = resultset;
    }
}
