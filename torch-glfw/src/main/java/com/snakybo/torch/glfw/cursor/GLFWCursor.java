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

package com.snakybo.torch.glfw.cursor;

import static org.lwjgl.glfw.GLFW.GLFW_ARROW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CROSSHAIR_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;
import static org.lwjgl.glfw.GLFW.GLFW_HAND_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_HRESIZE_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_IBEAM_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_VRESIZE_CURSOR;
import static org.lwjgl.glfw.GLFW.glfwCreateCursor;
import static org.lwjgl.glfw.GLFW.glfwCreateStandardCursor;
import static org.lwjgl.glfw.GLFW.glfwGetInputMode;
import static org.lwjgl.glfw.GLFW.glfwSetCursor;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWImage;

import com.snakybo.torch.bitmap.Bitmap;
import com.snakybo.torch.cursor.CursorLockMode;
import com.snakybo.torch.cursor.CursorShape;
import com.snakybo.torch.cursor.ICursor;
import com.snakybo.torch.debug.LoggerInternal;
import com.snakybo.torch.window.Window;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class GLFWCursor implements ICursor
{	
	CursorLockMode lockMode = CursorLockMode.None;
	
	@Override
	public final boolean isVisible()
	{
		return glfwGetInputMode(Window.getNativeId(), GLFW_CURSOR) == GLFW_CURSOR_NORMAL ? true : false;
	}

	@Override
	public final void setLockMode(CursorLockMode lockMode)
	{
		this.lockMode = lockMode;
	}
	
	@Override
	public final void setVisible(boolean visible)
	{
		glfwSetInputMode(Window.getNativeId(), GLFW_CURSOR, visible ? GLFW_CURSOR_NORMAL : GLFW_CURSOR_DISABLED);
	}
	
	@Override
	public final void setShapeArrow()
	{
		setShape(glfwCreateStandardCursor(GLFW_ARROW_CURSOR));
	}
	
	@Override
	public final void setShapeIBeam()
	{
		setShape(glfwCreateStandardCursor(GLFW_IBEAM_CURSOR));
	}
	
	@Override
	public final void setShapeCrosshair()
	{
		setShape(glfwCreateStandardCursor(GLFW_CROSSHAIR_CURSOR));
	}
	
	@Override
	public final void setShapeHand()
	{
		setShape(glfwCreateStandardCursor(GLFW_HAND_CURSOR));
	}
	
	@Override
	public final void setShapeHResize()
	{
		setShape(glfwCreateStandardCursor(GLFW_HRESIZE_CURSOR));
	}
	
	@Override
	public final void setShapeVResize()
	{
		setShape(glfwCreateStandardCursor(GLFW_VRESIZE_CURSOR));
	}
	
	@Override
	public final void setShape(Bitmap bitmap, Vector2f hot)
	{
		if(CursorShape.hasCursor(bitmap, hot))
		{
			setShape(CursorShape.getCursor(bitmap, hot));
			
			LoggerInternal.log("Loaded existing cursor shape", this);
		}
		else
		{
			GLFWImage image = GLFWImage.malloc().set(bitmap.getWidth(), bitmap.getHeight(), bitmap.getByteByffer());
			
			long cursor = glfwCreateCursor(image, Math.round(hot.x), Math.round(hot.y));
			CursorShape.addCursor(bitmap, hot, cursor);
			
			setShape(cursor);
			
			LoggerInternal.log("Created new cursor shape", this);
		}
	}
	
	@Override
	public final CursorLockMode getLockMode()
	{
		return lockMode;
	}

	private final void setShape(long shape)
	{
		glfwSetCursor(Window.getNativeId(), shape);
	}
}
