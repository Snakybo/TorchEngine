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
	
	public static boolean isVisible()
	{
		return WindowModule.getInstance().getCursor().isVisible();
	}

	public static void setLockMode(CursorLockMode cursorLockMode)
	{
		WindowModule.getInstance().getCursor().setLockMode(cursorLockMode);
	}
	
	public static void setVisible(boolean visible)
	{
		WindowModule.getInstance().getCursor().setVisible(visible);
	}
	
	public static void setShapeArrow()
	{
		WindowModule.getInstance().getCursor().setShapeArrow();
	}
	
	public static void setShapeIBeam()
	{
		WindowModule.getInstance().getCursor().setShapeIBeam();
	}
	
	public static void setShapeCrosshair()
	{
		WindowModule.getInstance().getCursor().setShapeCrosshair();
	}
	
	public static void setShapeHand()
	{
		WindowModule.getInstance().getCursor().setShapeHand();
	}
	
	public static void setShapeHResize()
	{
		WindowModule.getInstance().getCursor().setShapeHResize();
	}
	
	public static void setShapeVResize()
	{
		WindowModule.getInstance().getCursor().setShapeVResize();
	}
	
	public static void setShape(Bitmap bitmap, Vector2f hot)
	{
		WindowModule.getInstance().getCursor().setShape(bitmap, hot);
	}
	
	public static CursorLockMode getLockMode()
	{
		return WindowModule.getInstance().getCursor().getLockMode();
	}
}
