package com.snakybo.sengine.audio;

import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALContext;
import org.lwjgl.openal.ALDevice;

import com.snakybo.sengine.debug.Logger;

/**
 * @author Kevin
 * @since Feb 18, 2016
 */
public final class AudioManagerInternal
{
	private AudioManagerInternal()
	{
		throw new AssertionError();
	}
	
	private static ALContext context;
	
	/**
	 * Create the audio manager
	 */
	public static void create()
	{
		Logger.log("Initializing OpenAL", "AudioManager");
		
		ALDevice device = ALDevice.create(null);
		if(device == null)
		{
			Logger.logException(new RuntimeException("Unable to create the default audio output device"), "AudioManager");
			return;
		}
		
		ALCCapabilities caps = device.getCapabilities();
		if(!caps.OpenALC10)
		{
			Logger.logException(new RuntimeException("The default audio output device does not support OpenALC10"), "AudioManager");
			return;
		}
		
		context = ALContext.create(device);
	}
	
	/**
	 * Destroy the audio manager
	 */
	public static void destroy()
	{
		Logger.log("Terminating OpenAL", "AudioManager");
		
		for(AudioChannel audioChannel : AudioManager.audioChannels)
		{
			audioChannel.destroy();
		}
		
		context.destroy();
		context.getDevice().destroy();
	}
}
