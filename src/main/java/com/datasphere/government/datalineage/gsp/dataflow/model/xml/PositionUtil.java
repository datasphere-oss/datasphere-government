
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
