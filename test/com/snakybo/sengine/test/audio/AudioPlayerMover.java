package com.snakybo.sengine.test.audio;

import org.joml.Vector3f;

import com.snakybo.sengine.object.Component;

/**
 * @author Snakybo
 * @since 1.0
 */
public class AudioPlayerMover extends Component
{
	private Vector3f position;
	
	@Override
	protected void start()
	{
		position = getTransform().getPosition();
		position.z = 1;
		position.x = -4;
	}
	
	@Override
	protected void update()
	{
		position.x += 0.025f;
		
		if(position.x >= 4)
		{
			position.x = -4;
		}
	}
}
