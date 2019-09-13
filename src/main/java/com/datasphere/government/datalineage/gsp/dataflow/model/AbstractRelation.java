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

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRelation implements Relation
{

	public static int RELATION_ID = 0;

	private int id;

	protected RelationElement<?> target;
	protected List<RelationElement<?>> sources = new ArrayList<RelationElement<?>>( );

	public AbstractRelation( )
	{
		id = ++RELATION_ID;
	}

	public int getId( )
	{
		return id;
	}

	public RelationElement<?> getTarget( )
	{
		return target;
	}

	public void setTarget( RelationElement<?> target )
	{
		this.target = target;
	}

	public RelationElement<?>[] getSources( )
	{
		return sources.toArray( new RelationElement<?>[0] );
	}

	public void addSource( RelationElement<?> source )
	{
		if ( source != null && !sources.contains( source ) )
		{
			sources.add( source );
		}
	}
}
