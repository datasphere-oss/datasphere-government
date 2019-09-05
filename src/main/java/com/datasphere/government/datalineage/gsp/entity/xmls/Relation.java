package com.datasphere.government.datalineage.gsp.entity.xmls;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.List;


@Data
@XStreamAlias("relation")  // 对应relation
public class Relation {
    // 对应 id 属性
    @XStreamAsAttribute
    private String id;
    // 对应 type 属性
    @XStreamAsAttribute
    private String type;

    // 对应 target 子元素
    @XStreamImplicit(itemFieldName = "target")
    private List<Target> target ;
    // 对应 source 子元素
    @XStreamImplicit(itemFieldName = "source")
    private List<Source> source ;

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

    public List<Target> getTarget() {
        return target;
    }

    public void setTarget(List<Target> target) {
        this.target = target;
    }

    public List<Source> getSource() {
        return source;
    }

    public void setSource(List<Source> source) {
        this.source = source;
    }
}
