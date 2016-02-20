package com.snakybo.sengine.audio;

import org.lwjgl.openal.ALContext;
import org.lwjgl.openal.ALDevice;
import static org.lwjgl.openal.AL10.*;

import org.lwjgl.openal.ALCCapabilities;

import com.snakybo.sengine.debug.Logger;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class AudioManagerInternal
{
	private static ALContext context;
	
	private AudioManagerInternal()
	{
		throw new AssertionError();
	}	
	
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
		
		logOpenALInfo();
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
	
	/**
	 * Log information about OpenAL
	 */
	private static void logOpenALInfo()
	{
		Logger.log("Vendor: " + alGetString(AL_VENDOR), "OpenAL");
		Logger.log("Renderer: " + alGetString(AL_RENDERER), "OpenAL");
		Logger.log("Version: " + alGetString(AL_VERSION), "OpenAL");		
		Logger.log("Extensions: " + alGetString(AL_EXTENSIONS), "OpenAL");
	}
}
