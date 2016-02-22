package com.snakybo.sengine.audio;

import static org.lwjgl.openal.AL10.AL_EXTENSIONS;
import static org.lwjgl.openal.AL10.AL_RENDERER;
import static org.lwjgl.openal.AL10.AL_VENDOR;
import static org.lwjgl.openal.AL10.AL_VERSION;
import static org.lwjgl.openal.AL10.alGetString;

import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALContext;
import org.lwjgl.openal.ALDevice;

import com.snakybo.sengine.debug.Logger;
import com.snakybo.sengine.debug.LoggerInternal;

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
		LoggerInternal.log("Initializing OpenAL", "AudioManager");
		
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
		LoggerInternal.log("Terminating OpenAL", "AudioManager");
		
		context.destroy();
		context.getDevice().destroy();
	}
	
	/**
	 * Log information about OpenAL
	 */
	private static void logOpenALInfo()
	{
		LoggerInternal.log("Vendor: " + alGetString(AL_VENDOR), "OpenAL");
		LoggerInternal.log("Renderer: " + alGetString(AL_RENDERER), "OpenAL");
		LoggerInternal.log("Version: " + alGetString(AL_VERSION), "OpenAL");		
		LoggerInternal.log("Extensions: " + alGetString(AL_EXTENSIONS), "OpenAL");
	}
}
