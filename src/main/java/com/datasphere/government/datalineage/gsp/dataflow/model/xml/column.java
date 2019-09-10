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

import org.simpleframework.xml.Attribute;

import com.datasphere.government.gsp.datalineage.util.Pair;

public class column
{

	@Attribute(required = false)
	private String name;

	@Attribute(required = false)
	private String id;

	@Attribute(required = false)
	private String coordinate;

	public String getCoordinate( )
	{
		return coordinate;
	}

	public Pair<Integer, Integer> getStartPos( )
	{
		return PositionUtil.getStartPos( coordinate );
	}

	public Pair<Integer, Integer> getEndPos( )
	{
		return PositionUtil.getEndPos( coordinate );
	}

	public void setCoordinate( String coordinate )
	{
		this.coordinate = coordinate;
	}

	public String getName( )
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getId( )
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}

}
