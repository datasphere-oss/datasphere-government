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

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

public class ColumnDictionaryPredicate {

  /**
   * Specify default list query options
   *
   * @param nameContains The characters contained in the column name
   * @param logicalNameContains Characters contained in logical names
   * @param searchDateBy Date search criteria (creation date / modification date)
   * @param from Search start date, yyyy-MM-ddThh:mm:ss.SSSZ
   * @param to Search end date, yyyy-MM-ddThh:mm:ss.SSSZ
   * @return
   */
  public static Predicate searchList(String nameContains, String logicalNameContains,
                                     String searchDateBy, DateTime from, DateTime to) {

    BooleanBuilder builder = new BooleanBuilder();
    QColumnDictionary dictionary = QColumnDictionary.columnDictionary;

    if(from != null && to != null) {
      if(StringUtils.isNotEmpty(searchDateBy) && "CREATED".equalsIgnoreCase(searchDateBy)) {
        builder = builder.and(dictionary.createdTime.between(from, to));
      } else {
        builder = builder.and(dictionary.modifiedTime.between(from, to));
      }
    }

    if(StringUtils.isNotEmpty(nameContains)) {
      builder = builder.and(dictionary.name.containsIgnoreCase(nameContains));
    }

    if(StringUtils.isNotEmpty(logicalNameContains)) {
      builder = builder.and(dictionary.logicalName.containsIgnoreCase(logicalNameContains));
    }

    return builder;
  }

  public static Predicate searchListInCodeTable(CodeTable codeTable, String nameContains, String logicalNameContains,
                                     String searchDateBy, DateTime from, DateTime to) {
    BooleanBuilder builder = new BooleanBuilder();
    QColumnDictionary dictionary = QColumnDictionary.columnDictionary;

    builder.and(dictionary.codeTable.eq(codeTable));

    return builder.and(searchList(nameContains, logicalNameContains, searchDateBy, from, to));

  }

  /**
   * ColumnDictionary Duplicate lookup conditions
   *
   * @param name
   * @return
   */
  public static Predicate searchDuplicatedName(String name) {

    BooleanBuilder builder = new BooleanBuilder();
    QColumnDictionary dictionary = QColumnDictionary.columnDictionary;

    builder = builder.and(dictionary.name.eq(name));

    return builder;
  }

  /**
   * ColumnDictionary Logical Duplicate lookup conditions
   *
   * @param logicalName
   * @return
   */
  public static Predicate searchDuplicatedLogicalName(String logicalName) {

    BooleanBuilder builder = new BooleanBuilder();
    QColumnDictionary dictionary = QColumnDictionary.columnDictionary;

    builder = builder.and(dictionary.logicalName.eq(logicalName));

    return builder;
  }

}
