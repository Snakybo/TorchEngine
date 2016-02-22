package com.snakybo.sengine.audio;

import static org.lwjgl.openal.AL10.AL_ORIENTATION;
import static org.lwjgl.openal.AL10.AL_POSITION;
import static org.lwjgl.openal.AL10.AL_VELOCITY;
import static org.lwjgl.openal.AL10.alListenerfv;
import static org.lwjgl.openal.ALUtil.checkALError;

import java.nio.FloatBuffer;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import com.snakybo.sengine.debug.Logger;
import com.snakybo.sengine.object.Component;
import com.snakybo.sengine.util.time.Time;

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
