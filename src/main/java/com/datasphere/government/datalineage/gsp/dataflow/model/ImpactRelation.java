
package com.datasphere.government.datalineage.gsp.dataflow.model;

public class ImpactRelation extends AbstractRelation
{

	@Override
	public RelationType getRelationType( )
	{
		return RelationType.impact;
	}
}
