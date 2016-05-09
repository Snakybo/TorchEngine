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

package com.snakybo.torch.camera;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.snakybo.torch.input.cursor.Cursor;
import com.snakybo.torch.input.cursor.CursorLockMode;
import com.snakybo.torch.input.mouse.Mouse;
import com.snakybo.torch.object.Component;
import com.snakybo.torch.window.Window;

/**
 * @author Snakybo
 * @since 1.0
 */
public class CameraFreeLook extends Component
{
	private float sensitivity;
	
	public CameraFreeLook()
	{
		this(0.5f);
	}
	
	public CameraFreeLook(float sensitivity)
	{
		this.sensitivity = sensitivity;
	}
	
	@Override
	protected void start()
	{
		Mouse.setCursorPosition(Window.getCenter());
		
		Cursor.setLockMode(CursorLockMode.Locked);
		Cursor.setVisible(false);
	}
	
	@Override
	protected void destroy()
	{
		Cursor.setLockMode(CursorLockMode.None);
		Cursor.setVisible(true);
	}
	
	@Override
	protected void update()
	{
		Vector2f delta = Mouse.getCursorPositionDelta();
		
		if(delta.x != 0)
		{
			rotate(new Vector3f(0, 1, 0), delta.x);
		}
		
		if(delta.y != 0)
		{
			rotate(getTransform().getRight(), delta.y);
		}
	}
	
	private final void rotate(Vector3f axis, float amount)
	{
		getTransform().rotate(axis, -amount * sensitivity);
	}
}
