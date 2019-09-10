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

package com.datasphere.government.datalineage.gsp.dataflow.model.xml;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "dlineage")
public class dataflow
{

	@ElementList(entry = "relation", inline = true, required = false)
	private List<relation> relations;

	@ElementList(entry = "table", inline = true, required = false)
	private List<table> tables;

	@ElementList(entry = "view", inline = true, required = false)
	private List<table> views;

	@ElementList(entry = "resultset", inline = true, required = false)
	private List<table> resultsets;

	public List<relation> getRelations( )
	{
		return relations;
	}

	public void setRelations( List<relation> relations )
	{
		this.relations = relations;
	}

	public List<table> getTables( )
	{
		return tables;
	}

	public void setTables( List<table> tables )
	{
		this.tables = tables;
	}

	public List<table> getViews( )
	{
		return views;
	}

	public void setViews( List<table> views )
	{
		this.views = views;
	}

	public List<table> getResultsets( )
	{
		return resultsets;
	}

	public void setResultsets( List<table> resultsets )
	{
		this.resultsets = resultsets;
	}

}