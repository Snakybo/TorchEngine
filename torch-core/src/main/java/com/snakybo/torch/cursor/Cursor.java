// The MIT License(MIT)
//
// Copyright(c) 2016 Kevin Krol
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package com.snakybo.torch.cursor;

import org.joml.Vector2f;

import com.snakybo.torch.bitmap.Bitmap;
import com.snakybo.torch.module.Module;
import com.snakybo.torch.module.WindowModule;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Cursor
{
	private Cursor()
	{
		throw new AssertionError();
	}
	
	/**
	 * Is the cursor currently visible?
	 * @return Whether or not the cursor is currently visible.
	 */
	public static boolean isVisible()
	{
		return Module.getModule(WindowModule.class).getCursor().isVisible();
	}
	
	/**
	 * Set the lock mode of the cursor.
	 * @param cursorLockMode The new {@link CursorLockMode}.
	 */
	public static void setLockMode(CursorLockMode cursorLockMode)
	{
		Module.getModule(WindowModule.class).getCursor().setLockMode(cursorLockMode);
	}
	
	/**
	 * Hide or show the cursor.
	 * @param visible Whether or not the cursor should be visible.
	 */
	public static void setVisible(boolean visible)
	{
		Module.getModule(WindowModule.class).getCursor().setVisible(visible);
	}
	
	/**
	 * Set the cursor's shape to the system's arrow cursor.
	 */
	public static void setShapeArrow()
	{
		Module.getModule(WindowModule.class).getCursor().setShapeArrow();
	}
	
	/**
	 * Set the cursor's shape to the system's I beam cursor.
	 */
	public static void setShapeIBeam()
	{
		Module.getModule(WindowModule.class).getCursor().setShapeIBeam();
	}
	
	/**
	 * Set the cursor's shape to the system's crosshair cursor.
	 */
	public static void setShapeCrosshair()
	{
		Module.getModule(WindowModule.class).getCursor().setShapeCrosshair();
	}
	
	/**
	 * Set the cursor's shape to the system's hand cursor.
	 */
	public static void setShapeHand()
	{
		Module.getModule(WindowModule.class).getCursor().setShapeHand();
	}
	
	/**
	 * Set the cursor's shape to the system's horizontal resize cursor.
	 */
	public static void setShapeHResize()
	{
		Module.getModule(WindowModule.class).getCursor().setShapeHResize();
	}
	
	/**
	 * Set the cursor's shape to the system's vertical resize cursor.
	 */
	public static void setShapeVResize()
	{
		Module.getModule(WindowModule.class).getCursor().setShapeVResize();
	}
	
	/**
	 * Set the cursor's shape.
	 * @param bitmap The cursor shape.
	 * @param hot The hot point of the shape.
	 */
	public static void setShape(Bitmap bitmap, Vector2f hot)
	{
		Module.getModule(WindowModule.class).getCursor().setShape(bitmap, hot);
	}
	
	/**
	 * Get the current cursor lock mode.
	 * @return The current cursor lock mode.
	 */
	public static CursorLockMode getLockMode()
	{
		return Module.getModule(WindowModule.class).getCursor().getLockMode();
	}
}
