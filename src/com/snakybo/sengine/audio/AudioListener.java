package com.snakybo.sengine.audio;

import java.nio.FloatBuffer;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import static org.lwjgl.openal.AL10.*;

import com.snakybo.sengine.debug.Logger;
import com.snakybo.sengine.object.Component;
import com.snakybo.sengine.util.time.Time;

/**
 * @author Snakybo
 * @since 1.0
 */
public class AudioListener extends Component
{	
	static AudioListener instance;
	
	private FloatBuffer positionBuffer;
	private FloatBuffer velocityBuffer;
	private FloatBuffer orientationBuffer;
	
	private Vector3f lastPosition;
	
	public AudioListener()
	{
		if(instance != null)
		{
			Logger.logWarning("There's already an audio listener in the scene", this);
			return;
		}
		
		instance = this;
	}
	
	@Override
	protected void start()
	{
		lastPosition = getTransform().getPosition();		
		positionBuffer = BufferUtils.createFloatBuffer(3);
		velocityBuffer = BufferUtils.createFloatBuffer(3);
		orientationBuffer = BufferUtils.createFloatBuffer(6);
	}
	
	@Override
	protected void postUpdate()
	{
		// Listener position
		Vector3f position = getTransform().getPosition();
		
		positionBuffer.clear();
		positionBuffer.put(new float[] { position.x, position.y, position.z });
		positionBuffer.rewind();
		
		// Listener velocity
		float dt = Time.getDeltaTime();
		Vector3f velocity = getTransform().getPosition().sub(lastPosition, new Vector3f()).sub(dt, dt, dt);
		
		velocityBuffer.clear();
		velocityBuffer.put(new float[] { velocity.x, velocity.y, velocity.z });
		velocityBuffer.rewind();
		
		// Listener orientation
		//Vector3f forward = getTransform().getForward();
		Vector3f forward = getTransform().getBack();
		Vector3f up = getTransform().getUp();
		
		orientationBuffer.clear();
		orientationBuffer.put(new float[] { forward.x, forward.y, forward.z, up.x, up.y, up.z });
		//orientationBuffer.put(new float[] { 0, 0, 1, 0, 1, 0 });
		orientationBuffer.rewind();
		
		lastPosition = getTransform().getPosition();
		
		// Set listener values
		alListenerfv(AL_POSITION, positionBuffer);
		alListenerfv(AL_VELOCITY, velocityBuffer);
		alListenerfv(AL_ORIENTATION, orientationBuffer);
	}
	
	@Override
	protected void destroy()
	{
		if(instance == this)
		{
			instance = null;
		}
	}
}
