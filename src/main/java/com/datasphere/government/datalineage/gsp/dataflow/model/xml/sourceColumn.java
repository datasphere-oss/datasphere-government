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
import org.simpleframework.xml.Element;

@Element(name = "source")
public class sourceColumn
{

	@Attribute(required = false)
	private String coordinate;

	@Attribute(required = false)
	private String column;

	@Attribute(required = false)
	private String id;

	@Attribute(required = false)
	private String parent_id;

	@Attribute(required = false)
	private String parent_name;

	@Attribute(required = false)
	private String value;

	@Attribute(required = false)
	private String source_name;

	@Attribute(required = false)
	private String source_id;

	public String getCoordinate( )
	{
		return coordinate;
	}

	public void setCoordinate( String coordinate )
	{
		this.coordinate = coordinate;
	}

	public String getColumn( )
	{
		return column;
	}

	public void setColumn( String column )
	{
		this.column = column;
	}

	public String getId( )
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}

	public String getParent_id( )
	{
		return parent_id;
	}

	public void setParent_id( String parent_id )
	{
		this.parent_id = parent_id;
	}

	public String getParent_name( )
	{
		return parent_name;
	}

	public void setParent_name( String parent_name )
	{
		this.parent_name = parent_name;
	}

	public String getValue( )
	{
		return value;
	}

	public void setValue( String value )
	{
		this.value = value;
	}

	public String getSource_name( )
	{
		return source_name;
	}

	public void setSource_name( String source_name )
	{
		this.source_name = source_name;
	}

	public String getSource_id( )
	{
		return source_id;
	}

	public void setSource_id( String source_id )
	{
		this.source_id = source_id;
	}

}
