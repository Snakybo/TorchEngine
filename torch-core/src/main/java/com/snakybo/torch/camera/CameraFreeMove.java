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

import org.joml.Vector3f;

import com.snakybo.torch.input.keyboad.Key;
import com.snakybo.torch.input.keyboad.Keyboard;
import com.snakybo.torch.object.Component;

/**
 * @author Snakybo
 * @since 1.0
 */
public class CameraFreeMove extends Component
{
	private Key forward;
	private Key left;
	private Key back;
	private Key right;
	
	private float speed;
	
	/**
	 * Create a new free move component.
	 */
	public CameraFreeMove()
	{
		this(0.13f);
	}
	
	/**
	 * Create a new free move component.
	 * @param forward The key to move forward.
	 * @param left The key to move to the left.
	 * @param back The key to move backwards.
	 * @param right The key to move to the right.
	 */
	public CameraFreeMove(Key forward, Key left, Key back, Key right)
	{
		this(0.13f, forward, left, back, right);
	}
	
	/**
	 * Create a new free move component.
	 * @param speed The movement speed.
	 */
	public CameraFreeMove(float speed)
	{
		this(speed, Key.W, Key.A, Key.S, Key.D);
	}
	
	/**
	 * Create a new free move component.
	 * @param speed The movement speed.
	 * @param forward The key to move forward.
	 * @param left The key to move to the left.
	 * @param back The key to move backwards.
	 * @param right The key to move to the right.
	 */
	public CameraFreeMove(float speed, Key forward, Key left, Key back, Key right)
	{
		this.speed = speed;
		
		this.forward = forward;
		this.left = left;
		this.back = back;
		this.right = right;
	}
	
	@Override
	protected void update()
	{
		Vector3f direction = new Vector3f();
		
		if(Keyboard.isKeyDown(forward))
		{
			direction.z = -1;
		}
		else if(Keyboard.isKeyDown(back))
		{
			direction.z = 1;
		}
		
		if(Keyboard.isKeyDown(left))
		{
			direction.x = -1;
		}
		else if(Keyboard.isKeyDown(right))
		{
			direction.x = 1;
		}
		
		getTransform().translate(direction.mul(speed));
	}
	
	/**
	 * Set the key to move forward.
	 * @param forward The new forward key.
	 */
	public final void setForward(Key forward)
	{
		this.forward = forward;
	}
	
	/**
	 * Set the key to move to the left.
	 * @param left The new left key.
	 */
	public final void setLeft(Key left)
	{
		this.left = left;
	}
	
	/**
	 * Set the key to move backwards.
	 * @param back The new backwards key.
	 */
	public final void setBack(Key back)
	{
		this.back = back;
	}
	
	/**
	 * Set the key to move to the right.
	 * @param right The new right key.
	 */
	public final void setRight(Key right)
	{
		this.right = right;
	}
	
	/**
	 * Set the movement speed.
	 * @param speed The new movement speed.
	 */
	public final void setSpeed(float speed)
	{
		this.speed = speed;
	}
	
	/**
	 * Get the key to move forward.
	 * @return The forward key.
	 */
	public final Key getForward()
	{
		return forward;
	}
	
	/**
	 * Get the key to move to the left.
	 * @return The left key.
	 */
	public final Key getLeft()
	{
		return left;
	}
	
	/**
	 * Get the key to move backwards.
	 * @return The backwards key.
	 */
	public final Key getBack()
	{
		return back;
	}
	
	/**
	 * Get the key to move to the right.
	 * @return The right key;
	 */
	public final Key getRight()
	{
		return right;
	}
	
	/**
	 * Get the movement speed.
	 * @return The movement speed.
	 */
	public final float getSpeed()
	{
		return speed;
	}
}
