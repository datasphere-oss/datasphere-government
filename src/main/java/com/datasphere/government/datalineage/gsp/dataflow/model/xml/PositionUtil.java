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

import com.datasphere.government.datalineage.gsp.util.Pair;

public class PositionUtil
{

	public static Pair<Integer, Integer> getStartPos(String coordinate )
	{
		if ( coordinate != null )
		{
			String[] splits = coordinate.replace( "[", "" )
					.replace( "]", "" )
					.split( "," );
			if ( splits.length == 4 )
			{
				return new Pair<Integer, Integer>( Integer.parseInt( splits[0].trim( ) ),
						Integer.parseInt( splits[1].trim( ) ) );
			}
		}
		return null;
	}

	public static Pair<Integer, Integer> getEndPos( String coordinate )
	{
		if ( coordinate != null )
		{
			String[] splits = coordinate.replace( "[", "" )
					.replace( "]", "" )
					.split( "," );
			if ( splits.length == 4 )
			{
				return new Pair<Integer, Integer>( Integer.parseInt( splits[2].trim( ) ),
						Integer.parseInt( splits[3].trim( ) ) );
			}
		}
		return null;
	}
}
