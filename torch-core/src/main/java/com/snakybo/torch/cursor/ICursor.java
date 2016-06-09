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

/**
 * @author Snakybo
 * @since 1.0
 */
public interface ICursor
{
	/**
	 * Is the cursor currently visible?
	 * @return Whether or not the cursor is currently visible.
	 */
	boolean isVisible();
	
	/**
	 * Set the lock mode of the cursor.
	 * @param cursorLockMode The new {@link CursorLockMode}.
	 */
	void setLockMode(CursorLockMode cursorLockMode);
	
	/**
	 * Hide or show the cursor.
	 * @param visible Whether or not the cursor should be visible.
	 */
	void setVisible(boolean visible);
	
	/**
	 * Set the cursor's shape to the system's arrow cursor.
	 */
	void setShapeArrow();
	
	/**
	 * Set the cursor's shape to the system's I beam cursor.
	 */
	void setShapeIBeam();
	
	/**
	 * Set the cursor's shape to the system's crosshair cursor.
	 */
	void setShapeCrosshair();
	
	/**
	 * Set the cursor's shape to the system's hand cursor.
	 */
	void setShapeHand();
	
	/**
	 * Set the cursor's shape to the system's horizontal resize cursor.
	 */
	void setShapeHResize();
	
	/**
	 * Set the cursor's shape to the system's vertical resize cursor.
	 */
	void setShapeVResize();
	
	/**
	 * Set the cursor's shape.
	 * @param bitmap The cursor shape.
	 * @param hot The hot point of the shape.
	 */
	void setShape(Bitmap bitmap, Vector2f hot);
	
	/**
	 * Get the current cursor lock mode.
	 * @return The current cursor lock mode.
	 */
	CursorLockMode getLockMode();
}
