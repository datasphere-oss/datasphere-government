package com.datasphere.government.datalineage.gsp.entity;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;


@Data
public class ParseResult {

    /**
     * 输入列表
     */
    private List<Entity> inputList;

    /**
     * 输出列表
     */
    private List<Entity> outputList;


    public ParseResult() {
        this.inputList = new LinkedList<>();
        this.outputList = new LinkedList<>();
    }

    public List<Entity> getInputList() {
        return inputList;
    }

    public void setInputList(List<Entity> inputList) {
        this.inputList = inputList;
    }

    public List<Entity> getOutputList() {
        return outputList;
    }

    public void setOutputList(List<Entity> outputList) {
        this.outputList = outputList;
    }
}
