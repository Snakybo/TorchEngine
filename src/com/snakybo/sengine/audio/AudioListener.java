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
	
	private Vector3f lastPosition;
	
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
		lastPosition = getTransform().getPosition();
	}
	
	@Override
	protected void postUpdate()
	{
		setPosition();
//		setVelocity();
//		setOrientation();
		
		lastPosition = getTransform().getPosition();
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
		// TODO: AudioListener velocity
		FloatBuffer buffer = BufferUtils.createFloatBuffer(3);
		
		float dt = Time.getDeltaTime();
		Vector3f position = getTransform().getPosition();
		Vector3f velocity = position.sub(lastPosition, new Vector3f());
		velocity.sub(dt, dt, dt);
		
		alListenerfv(AL_VELOCITY, velocity.get(buffer));
		checkALError();
	}
	
	/**
	 * Set the orientation of the listener
	 */
	private void setOrientation()
	{
		// TODO: AudioListener orientation
		FloatBuffer buffer = BufferUtils.createFloatBuffer(6);		
		
		Vector3f forward = getTransform().getForward();
		Vector3f up = getTransform().getUp();
		
		forward.get(buffer);
		up.get(buffer);
		
		alListenerfv(AL_ORIENTATION, buffer);
		checkALError();
	}
}
