/*
 * Copyright 2019, Huahuidata, Inc.
 * DataSphere is licensed under the Mulan PSL v1.
 * You can use this software according to the terms and conditions of the Mulan PSL v1.
 * You may obtain a copy of Mulan PSL v1 at:
 * http://license.coscl.org.cn/MulanPSL
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 * PURPOSE.
 * See the Mulan PSL v1 for more details.
 */

package com.datasphere.government.mdm.controller;

import com.google.common.collect.Maps;

import com.querydsl.core.types.Predicate;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

import com.datasphere.government.mdm.ColumnDictionaryRepository;
import com.datasphere.government.mdm.MetadataColumnProjections;
import com.datasphere.government.mdm.MetadataColumnRepository;
import com.datasphere.server.util.ProjectionUtils;

/**
 * Created by aladin on 2019. 7. 22..
 */
@RepositoryRestController
public class ColumnDictionaryController {

  private static Logger LOGGER = LoggerFactory.getLogger(ColumnDictionaryController.class);

  @Autowired
  ColumnDictionaryRepository columnDictionaryRepository;

  @Autowired
  MetadataColumnRepository metadataColumnRepository;

  @Autowired
  ProjectionFactory projectionFactory;

  @Autowired
  PagedResourcesAssembler pagedResourcesAssembler;

  MetadataColumnProjections metadataColumnProjections = new MetadataColumnProjections();

  public ColumnDictionaryController() {
  }

  /**
   * Column Dictionary View the list.
   *
   * @param nameContains
   * @param searchDateBy
   * @param from
   * @param to
   * @param pageable
   * @param resourceAssembler
   * @return
   */
  @RequestMapping(value = "/dictionaries", method = RequestMethod.GET)
  public ResponseEntity<?> findColumnDictionaries(
                                           @RequestParam(value = "nameContains", required = false) String nameContains,
                                           @RequestParam(value = "logicalNameContains", required = false) String logicalNameContains,
                                           @RequestParam(value = "searchDateBy", required = false) String searchDateBy,
                                           @RequestParam(value = "from", required = false)
                                           @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) DateTime from,
                                           @RequestParam(value = "to", required = false)
                                           @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) DateTime to,
                                           Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {

    // Get Predicate
    Predicate searchPredicated = ColumnDictionaryPredicate.searchList(nameContains, logicalNameContains, searchDateBy, from, to);

    // Default sort condition settings
    if (pageable.getSort() == null || !pageable.getSort().iterator().hasNext()) {
      pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                                 new Sort(Sort.Direction.ASC, "logicalName", "name"));
    }

    // Find by predicated
    Page<ColumnDictionary> columnDictionaries = columnDictionaryRepository.findAll(searchPredicated, pageable);

    return ResponseEntity.ok(this.pagedResourcesAssembler.toResource(columnDictionaries, resourceAssembler));
  }

  @RequestMapping(path = "/dictionaries/name/{value}/duplicated", method = RequestMethod.GET)
  public ResponseEntity<?> checkDuplicatedValue(@PathVariable("value") String value) {

    Map<String, Boolean> duplicatedResult = Maps.newHashMap();
    Predicate duplicatedPredicate = ColumnDictionaryPredicate.searchDuplicatedLogicalName(value);
    Boolean duplicated = columnDictionaryRepository.exists(duplicatedPredicate);

    if (duplicated) {
      duplicatedResult.put("duplicated", true);
    } else {
      duplicatedResult.put("duplicated", false);
    }

    return ResponseEntity.ok(duplicatedResult);

  }

  @RequestMapping(value = "/dictionaries/{dictionaryId}/columns", method = RequestMethod.GET)
  public ResponseEntity<?> findLinkedMedadataColumns(@PathVariable("dictionaryId") String dictionaryId,
                                                    @RequestParam(value = "projection", required = false, defaultValue = "default") String projection,
                                                    Pageable pageable) {

    if(columnDictionaryRepository.findById(dictionaryId) == null) {
      throw new ResourceNotFoundException(dictionaryId);
    }


    if (pageable.getSort() == null || !pageable.getSort().iterator().hasNext()) {
      pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                                 new Sort(Sort.Direction.ASC, "physicalName"));
    }

    Page<MetadataColumn> columns = metadataColumnRepository.linkedMetadataColumns(dictionaryId, pageable);


    return ResponseEntity.ok(this.pagedResourcesAssembler.toResource(
        ProjectionUtils.toPageResource(projectionFactory,
                                       metadataColumnProjections.getProjectionByName(projection),
                                       columns)
    ));

  }

}
