package org.eclipse.swt.examples.controls;

/*
 * (c) Copyright IBM Corp. 2000, 2002.
 * This file is made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
 
import org.eclipse.swt.examples.controlexample.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.part.*;

/**
 * <code>Controls</code> is a simple demonstration
 * of the controls defined by SWT.  It consists of a workbench
 * view and tab folder where each tab in the folder allows the
 * user to interact with a control.
 * 
 * @see ViewPart
 */
public class ControlView extends ViewPart {
	ControlExample instance = null;
	
	/**
	 * Create the example
	 * 
	 * @see ViewPart#createPartControl
	 */
	public void createPartControl(Composite frame) {
		instance = new ControlExample(frame);
	}

	/**
	 * Called when we must grab focus.
	 * 
	 * @see org.eclipse.ui.part.ViewPart#setFocus
	 */
	public void setFocus() {
		instance.setFocus();
	}

	/**
	 * Called when the View is to be disposed
	 */	
	public void dispose() {
		instance.dispose();
		instance = null;
		super.dispose();
	}
}
