/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.widgets;


import org.eclipse.swt.internal.win32.*;
import org.eclipse.swt.*;

/**
 * Instances of this class represent the system task bar.
 * 
 * <dl>
 * <dt><b>Styles:</b></dt>
 * <dd>(none)</dd>
 * <dt><b>Events:</b></dt>
 * <dd>(none)</dd>
 * </dl>
 * 
 * @see Display#getSystemTaskBar
 * @see <a href="http://www.eclipse.org/swt/">Sample code and further information</a>
 * 
 * @since 3.6
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
public class TaskBar extends Widget {
	int itemCount;
	TaskBarItem [] items = new TaskBarItem [4];
	int /*long*/ mTaskbarList3;
	
	static final byte [] CLSID_TaskbarList = new byte [16]; 
	static final byte [] IID_ITaskbarList3 = new byte [16];
	static {
		OS.IIDFromString ("{56FDF344-FD6D-11d0-958A-006097C9A090}\0".toCharArray (), CLSID_TaskbarList); //$NON-NLS-1$
		OS.IIDFromString ("{EA1AFB91-9E28-4B86-90E9-9E9F8A5EEFAF}\0".toCharArray (), IID_ITaskbarList3); //$NON-NLS-1$	
	}

TaskBar (Display display, int style) {
	if (display == null) display = Display.getCurrent ();
	if (display == null) display = Display.getDefault ();
	if (!display.isValidThread ()) {
		error (SWT.ERROR_THREAD_INVALID_ACCESS);
	}
	this.display = display;
	createHandle ();
	reskinWidget ();
}

void createHandle () {
	int /*long*/[] ppv = new int /*long*/ [1];
	int hr = OS.CoCreateInstance (CLSID_TaskbarList, 0, OS.CLSCTX_INPROC_SERVER, IID_ITaskbarList3, ppv);
	if (hr != OS.S_OK) error (SWT.ERROR_NO_HANDLES);
	mTaskbarList3 = ppv [0];
}

void createItem (TaskBarItem item, int index) {
	if (index == -1) index = itemCount;
	if (!(0 <= index && index <= itemCount)) error (SWT.ERROR_INVALID_RANGE);
	if (itemCount == items.length) {
		TaskBarItem [] newItems = new TaskBarItem [items.length + 4];
		System.arraycopy (items, 0, newItems, 0, items.length);
		items = newItems;
	}
	System.arraycopy (items, index, items, index + 1, itemCount++ - index);
	items [index] = item;
}

void createItems () {
	Shell [] shells = display.getShells ();
	for (int i = 0; i < shells.length; i++) {
		getItem (shells[i]);
	}
}

void destroyItem (TaskBarItem item) {
	int index = 0;
	while (index < itemCount) {
		if (items [index] == item) break;
		index++;
	}
	if (index == itemCount) return;
	System.arraycopy (items, index + 1, items, index, --itemCount - index);
	items [itemCount] = null;
}

/**
 * Returns the item at the given, zero-relative index in the
 * receiver. Throws an exception if the index is out of range.
 *
 * @param index the index of the item to return
 * @return the item at the given index
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_INVALID_RANGE - if the index is not between 0 and the number of elements in the list minus 1 (inclusive)</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public TaskBarItem getItem (int index) {
	checkWidget ();
	createItems ();
	if (!(0 <= index && index < itemCount)) error (SWT.ERROR_INVALID_RANGE);
	return items [index];
}

/**
 * Returns the number of items contained in the receiver.
 *
 * @return the number of items
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public int getItemCount () {
	checkWidget ();
	createItems ();
	return itemCount;
}

/**
 * Returns the task bar item for the given <code>shell</code> or the task bar
 * item for the application if the <code>shell</code> argument is <code>null</code>.
 * If the requested item is not supported by the platform it returns <code>null</code>.
 * 
 * @param shell the shell for which the task bar item is requested, or null to request the application item
 * @return the task bar item at the given shell or the application
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public TaskBarItem getItem (Shell shell) {
	checkWidget ();
	for (int i = 0; i < items.length; i++) {
		if (items [i] != null && items [i].shell == shell) {
			return items [i];
		}
	}
	// Windows only supports shell item
	TaskBarItem item = null;
	if (shell != null) {
		item = new TaskBarItem (this, SWT.NONE);
		item.setShell (shell);
	}
	return item;
}

/**
 * Returns an array of <code>TaskBarItem</code>s which are the items
 * in the receiver. 
 * <p>
 * Note: This is not the actual structure used by the receiver
 * to maintain its list of items, so modifying the array will
 * not affect the receiver. 
 * </p>
 *
 * @return the items in the receiver
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public TaskBarItem [] getItems () {
	checkWidget ();
	createItems ();
	TaskBarItem [] result = new TaskBarItem [itemCount];
	System.arraycopy (items, 0, result, 0, result.length);
	return result;
}

void releaseChildren (boolean destroy) {
	if (items != null) {
		for (int i=0; i<items.length; i++) {
			TaskBarItem item = items [i];
			if (item != null && !item.isDisposed ()) {
				item.release (false);
			}
		}
		items = null;
	}
	super.releaseChildren (destroy);
}

void releaseParent () {
	super.releaseParent ();
	if (display.taskBar == this) display.taskBar = null;
}

void releaseWidget () {
	super.releaseWidget ();
	if (mTaskbarList3 != 0) {
		/* Release() */
		OS.VtblCall (2, mTaskbarList3);
		mTaskbarList3 = 0;
	}
}

void reskinChildren (int flags) {	
	if (items != null) {
		for (int i=0; i<items.length; i++) {
			TaskBarItem item = items [i];
			if (item != null) item.reskin (flags);
		}
	}
	super.reskinChildren (flags);
}

}