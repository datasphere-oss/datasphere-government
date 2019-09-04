package com.datasphere.government.gsp.datalineage.entity.xmls;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;

/**
 * Created by jeq 27.
 */
@Data
@XStreamAlias("column")
public class Column {

    @XStreamAsAttribute
    private String name;
    @XStreamAsAttribute
    private String id;
    @XStreamAsAttribute
    private String coordinate;

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

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }
}
