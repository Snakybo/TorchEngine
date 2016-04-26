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

package com.snakybo.torch.audio;

import static org.lwjgl.openal.AL10.AL_ORIENTATION;
import static org.lwjgl.openal.AL10.AL_POSITION;
import static org.lwjgl.openal.AL10.AL_VELOCITY;
import static org.lwjgl.openal.AL10.alListenerfv;
import static org.lwjgl.openal.ALUtil.checkALError;

import java.nio.FloatBuffer;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.object.Component;
import com.snakybo.torch.util.time.Time;

/**
 * @author Snakybo
 * @since 1.0
 */
public class AudioListener extends Component
{	
	private static AudioListener instance;
	
	private Vector3f previousPosition;
	
	public AudioListener()
	{
		if(instance != null)
		{
			Logger.logWarning("There already is an audio listener in the scene", this);
			return;
		}
		
		instance = this;
	}
	
	@Override
	protected void start()
	{
		previousPosition = new Vector3f(getTransform().getPosition());
	}
	
	@Override
	protected void postUpdate()
	{
		setPosition();
		setVelocity();
		setOrientation();
		
		previousPosition = getTransform().getPosition();
	}
	
	@Override
	protected void destroy()
	{
		if(instance == this)
		{
			instance = null;
		}
	}
	
	/**
	 * Set the position of the listener
	 */
	private void setPosition()
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(3);		
		Vector3f position = getTransform().getPosition();
		
		alListenerfv(AL_POSITION, position.get(buffer));
		checkALError();
	}
	
	/**
	 * Set the velocity of the listener
	 */
	private void setVelocity()
	{
		Vector3f currentPosition = getTransform().getPosition();
		Vector3f velocity = currentPosition.sub(previousPosition, new Vector3f()).div(Time.getDeltaTime());
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(3);
		
		alListenerfv(AL_VELOCITY, velocity.get(buffer));
		checkALError();
		
		previousPosition.set(currentPosition);
	}
	
	/**
	 * Set the orientation of the listener
	 */
	private void setOrientation()
	{
		FloatBuffer buffer = (FloatBuffer)BufferUtils.createFloatBuffer(6).put(new float[] {0, 0, -1, 0, 1, 0}).rewind();
		
		alListenerfv(AL_ORIENTATION, buffer);
		checkALError();
		
		// TODO: Proper AudioListener orientation
		/*FloatBuffer buffer = BufferUtils.createFloatBuffer(6);		
		
		Vector3f forward = getTransform().getForward();
		Vector3f up = getTransform().getUp();
		
		forward.get(buffer);
		up.get(buffer);
		
		alListenerfv(AL_ORIENTATION, buffer);
		checkALError();*/
	}
	
	/**
	 * @return Whether or not an {@link AudioListener} is present in the game scene
	 */
	public static boolean isPresent()
	{
		return instance != null;
	}
}
