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

package com.datasphere.government.datalineage.gsp.dataflow.model;

public class ResultColumnRelationElement implements
		RelationElement<ResultColumn>
{

	private ResultColumn column;

	public ResultColumnRelationElement( ResultColumn column )
	{
		this.column = column;
	}

	@Override
	public ResultColumn getElement( )
	{
		return column;
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ( ( column == null ) ? 0 : column.hashCode( ) );
		return result;
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass( ) != obj.getClass( ) )
			return false;
		ResultColumnRelationElement other = (ResultColumnRelationElement) obj;
		if ( column == null )
		{
			if ( other.column != null )
				return false;
		}
		else if ( !column.equals( other.column ) )
			return false;
		return true;
	}

}
