/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.datasphere.government.mdm;

import com.google.common.collect.Lists;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.datasphere.server.common.domain.AbstractHistoryEntity;
import com.datasphere.server.common.domain.DSSDomain;

@Entity
@Table(name = "mdm_code_table")
public class CodeTable extends AbstractHistoryEntity implements DSSDomain<String> {

  /**
   * ID
   */
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "id")
  String id;

  /**
   * The name of code table
   */
  @Column(name = "table_name")
  @NotBlank
  @Size(max = 150)
  String name;

  /**
   * The description of code table
   */
  @Column(name = "table_desc", length = 1000)
  @Size(max = 900)
  String description;

  /**
   * About data source fields
   */
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "table_id", referencedColumnName = "id")
  @BatchSize(size = 50)
  List<CodeValuePair> codes;

  /**
   * 数据字典
   */
  @OneToMany(mappedBy = "codeTable", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
  @OrderBy("logicalName ASC")
  @BatchSize(size = 50)
  List<ColumnDictionary> dictionaries;

  @OneToMany(mappedBy = "codeTable", cascade = {CascadeType.MERGE})
  private List<MetadataColumn> columns;

  @PreRemove
  private void removeCodeTableFromChilds(){

    dictionaries.forEach(dictionary -> {
      if(dictionary.getCodeTable().id.equals(this.id)){
        dictionary.setCodeTable(null);
      }
    });

    columns.forEach(metadataColumn -> {
      if(metadataColumn.getCodeTable().id.equals(this.id)){
        metadataColumn.setCodeTable(null);
      }
    });
  }

  public CodeTable() {
  }

  @JsonIgnore
  public Map<Long, CodeValuePair> getCodeMap() {
    return codes.stream()
                .collect(Collectors.toMap(CodeValuePair::getId, code -> code));
  }
  // 添加代码
  public void addCode(CodeValuePair codeValuePair) {
    if(this.codes == null) {
      this.codes = Lists.newArrayList();
    }

    this.codes.add(codeValuePair);
  }
  // 删除代码
  public void removeCode(CodeValuePair codeValuePair) {
    if(this.codes == null) {
      return;
    }

    this.codes.remove(codeValuePair);
  }
  // 添加元数据列
  public void addColumn(MetadataColumn column) {
    if(this.columns == null) {
      this.columns = Lists.newArrayList();
    }

    this.columns.add(column);
  }
  //删除元数据列
  public void removeColumn(MetadataColumn column) {
    if(this.columns == null) {
      return;
    }

    this.columns.remove(column);
  }

  @Override
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<CodeValuePair> getCodes() {
    return codes;
  }

  public void setCodes(List<CodeValuePair> codes) {
    this.codes = codes;
  }
  //获取字典
  public List<ColumnDictionary> getDictionaries() {
    return dictionaries;
  }
  // 设置字典
  public void setDictionaries(List<ColumnDictionary> dictionaries) {
    this.dictionaries = dictionaries;
  }

  public List<MetadataColumn> getColumns() {
    return columns;
  }

  public void setColumns(List<MetadataColumn> columns) {
    this.columns = columns;
  }
}
