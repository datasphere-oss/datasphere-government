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

import com.datasphere.government.mdm.catalog.CatalogController;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.util.List;

public class MetadataPredicate {

  /**
   * Specify default list query options
   *
   * @param nameContains The characters contained in the column name
   * @param searchDateBy Date search criteria (creation date / modification date)
   * @param from Search start date, yyyy-MM-ddThh:mm:ss.SSSZ
   * @param to Search end date, yyyy-MM-ddThh:mm:ss.SSSZ
   * @return
   */
  public static Predicate searchList(Metadata.SourceType sourceType, String catalogId, List<String> subCatalogIds, String nameContains,
                                     String searchDateBy, DateTime from, DateTime to) {

    BooleanBuilder builder = new BooleanBuilder();
    QMetadata qMetadata = QMetadata.metadata;

    if(sourceType != null) {
      builder.and(qMetadata.sourceType.eq(sourceType));
    }

    if(catalogId != null) {
      if(CatalogController.EMPTY_CATALOG.equals(catalogId)) {
        builder.and(qMetadata.catalogs.isEmpty());
      } else {
        if(CollectionUtils.isNotEmpty(subCatalogIds)) {
          subCatalogIds.add(catalogId);
          builder.and(qMetadata.catalogs.any().id.in(subCatalogIds));
        } else {
          builder.and(qMetadata.catalogs.any().id.eq(catalogId));
        }
      }
    }

    if(from != null && to != null) {
      if(StringUtils.isNotEmpty(searchDateBy) && "CREATED".equalsIgnoreCase(searchDateBy)) {
        builder = builder.and(qMetadata.createdTime.between(from, to));
      } else {
        builder = builder.and(qMetadata.modifiedTime.between(from, to));
      }
    }

    if(StringUtils.isNotEmpty(nameContains)) {
      builder = builder.and(qMetadata.name.containsIgnoreCase(nameContains));
    }

    return builder;
  }

  /**
   * Metadata name duplicate check
   *
   * @param name
   * @return
   */
  public static Predicate searchDuplicatedName(String name) {

    BooleanBuilder builder = new BooleanBuilder();
    QMetadata qMetadata = QMetadata.metadata;

    builder = builder.and(qMetadata.name.eq(name));

    return builder;
  }

  /**
   * Metadata names duplicate check
   *
   * @param names
   * @return
   */
  public static Predicate searchDuplicatedNames(List<String> names) {
    BooleanBuilder builder = new BooleanBuilder();
    QMetadata qMetadata = QMetadata.metadata;

    if(names != null && names.size() > 0){
      builder = builder.and(qMetadata.name.in(names));
    }

    return builder;
  }

}
