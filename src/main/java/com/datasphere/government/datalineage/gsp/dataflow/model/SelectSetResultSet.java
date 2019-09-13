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

import gudusoft.gsqlparser.ESetOperatorType;
import gudusoft.gsqlparser.nodes.TResultColumnList;
import gudusoft.gsqlparser.stmt.TSelectSqlStatement;

public class SelectSetResultSet extends ResultSet
{

	private TSelectSqlStatement selectObject;

	public SelectSetResultSet( TSelectSqlStatement select )
	{
		super( select, false );
		this.selectObject = select;
	}

	public ESetOperatorType getSetOperatorType( )
	{
		return selectObject.getSetOperatorType( );
	}

	public TResultColumnList getResultColumnObject( )
	{
		if ( selectObject.getLeftStmt( ) != null
				&& selectObject.getLeftStmt( ).getResultColumnList( ) != null )
		{
			return selectObject.getLeftStmt( ).getResultColumnList( );
		}
		else if ( selectObject.getRightStmt( ) != null
				&& selectObject.getRightStmt( ).getResultColumnList( ) != null )
		{
			return selectObject.getRightStmt( ).getResultColumnList( );
		}
		return selectObject.getResultColumnList( );
	}

}
