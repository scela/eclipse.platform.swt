/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.tests.junit;


import junit.framework.*;
import junit.textui.*;

/**
 * Automated Test Suite for class org.eclipse.swt.dnd.DragSourceAdapter
 *
 * @see org.eclipse.swt.dnd.DragSourceAdapter
 */
public class Test_org_eclipse_swt_dnd_DragSourceAdapter extends SwtTestCase {

public Test_org_eclipse_swt_dnd_DragSourceAdapter(String name) {
	super(name);
}

public static void main(String[] args) {
	TestRunner.run(suite());
}

public void test_Constructor() {
	warnUnimpl("Test test_Constructor not written");
}

public void test_dragFinishedLorg_eclipse_swt_dnd_DragSourceEvent() {
	warnUnimpl("Test test_dragFinishedLorg_eclipse_swt_dnd_DragSourceEvent not written");
}

public void test_dragSetDataLorg_eclipse_swt_dnd_DragSourceEvent() {
	warnUnimpl("Test test_dragSetDataLorg_eclipse_swt_dnd_DragSourceEvent not written");
}

public void test_dragStartLorg_eclipse_swt_dnd_DragSourceEvent() {
	warnUnimpl("Test test_dragStartLorg_eclipse_swt_dnd_DragSourceEvent not written");
}


public static Test suite() {
	TestSuite suite = new TestSuite();
	java.util.Vector<String> methodNames = methodNames();
	java.util.Enumeration<String> e = methodNames.elements();
	while (e.hasMoreElements()) {
		suite.addTest(new Test_org_eclipse_swt_dnd_DragSourceAdapter(e.nextElement()));
	}
	return suite;
}
public static java.util.Vector<String> methodNames() {
	java.util.Vector<String> methodNames = new java.util.Vector<String>();
	methodNames.addElement("test_Constructor");
	methodNames.addElement("test_dragFinishedLorg_eclipse_swt_dnd_DragSourceEvent");
	methodNames.addElement("test_dragSetDataLorg_eclipse_swt_dnd_DragSourceEvent");
	methodNames.addElement("test_dragStartLorg_eclipse_swt_dnd_DragSourceEvent");
	return methodNames;
}
@Override
protected void runTest() throws Throwable {
	if (getName().equals("test_Constructor")) test_Constructor();
	else if (getName().equals("test_dragFinishedLorg_eclipse_swt_dnd_DragSourceEvent")) test_dragFinishedLorg_eclipse_swt_dnd_DragSourceEvent();
	else if (getName().equals("test_dragSetDataLorg_eclipse_swt_dnd_DragSourceEvent")) test_dragSetDataLorg_eclipse_swt_dnd_DragSourceEvent();
	else if (getName().equals("test_dragStartLorg_eclipse_swt_dnd_DragSourceEvent")) test_dragStartLorg_eclipse_swt_dnd_DragSourceEvent();
}
}
