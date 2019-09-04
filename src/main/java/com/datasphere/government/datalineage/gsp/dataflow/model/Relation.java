
package com.datasphere.government.datalineage.gsp.dataflow.model;

public interface Relation
{

	RelationElement<?> getTarget();

	RelationElement<?>[] getSources();

	RelationType getRelationType();
}
