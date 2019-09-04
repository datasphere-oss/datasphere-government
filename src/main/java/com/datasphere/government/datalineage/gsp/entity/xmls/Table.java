package com.datasphere.government.gsp.datalineage.entity.xmls;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.List;

/**
 * Created by jeq 27.
 */
@Data
@XStreamAlias("table")
public class Table {

    @XStreamAsAttribute
    private String name;
    @XStreamAsAttribute
    private String id;
    @XStreamAsAttribute
    private String type;
    @XStreamAsAttribute
    private String coordinate;

    @XStreamImplicit(itemFieldName = "column")
    private List<Column> column;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public List<Column> getColumn() {
        return column;
    }

    public void setColumn(List<Column> column) {
        this.column = column;
    }
}
