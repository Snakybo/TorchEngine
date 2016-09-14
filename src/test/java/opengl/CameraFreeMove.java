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

package opengl;

import com.snakybo.torch.annotation.SerializedField;
import com.snakybo.torch.input.keyboard.Key;
import com.snakybo.torch.input.keyboard.Keyboard;
import com.snakybo.torch.object.Component;
import com.snakybo.torch.util.time.Time;
import org.joml.Vector3f;

/**
 * @author Snakybo
 * @since 1.0
 */
public class CameraFreeMove extends Component
{
	@SerializedField private Key forward = Key.W;
	@SerializedField private Key left = Key.A;
	@SerializedField private Key back = Key.S;
	@SerializedField private Key right = Key.D;
	@SerializedField private Key up = Key.E;
	@SerializedField private Key down = Key.Q;
	
	@SerializedField private float speed = 2.5f;
	
	@Override
	protected void onUpdate()
	{
		if(Keyboard.isDown(forward))
		{
			move(getTransform().forward());
		}
		
		if(Keyboard.isDown(back))
		{
			move(getTransform().backward());
		}
		
		if(Keyboard.isDown(left))
		{
			move(getTransform().left());
		}
		
		if(Keyboard.isDown(right))
		{
			move(getTransform().right());
		}
		
		if(Keyboard.isDown(up))
		{
			move(getTransform().up());
		}
		
		if(Keyboard.isDown(down))
		{
			move(getTransform().down());
		}
	}
	
	private void move(Vector3f direction)
	{
		getTransform().translate(direction.mul(speed * Time.getDeltaTime()));
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
