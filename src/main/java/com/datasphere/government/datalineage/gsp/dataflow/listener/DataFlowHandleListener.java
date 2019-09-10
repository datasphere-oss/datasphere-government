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

package com.datasphere.government.datalineage.gsp.dataflow.listener;

import java.io.File;

public interface DataFlowHandleListener
{

	public void startAnalyze(File file, long lengthOrCount, boolean isCount);

	public void startParse(File file, long length, int index);

	public void endParse();

	public void startAnalyzeDataFlow(int totalCount);

	public void startAnalyzeStatment(int index);

	public void endAnalyzeStatment(int index);

	public void endAnalyzeDataFlow();

	public void startOutputDataFlowXML();

	public void endOutputDataFlowXML(long length);

	public void endAnalyze();

	public boolean isCanceled();

}
