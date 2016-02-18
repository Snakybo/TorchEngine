package com.snakybo.sengine.audio;

import static org.lwjgl.openal.AL10.AL_FORMAT_MONO16;
import static org.lwjgl.openal.AL10.alBufferData;
import static org.lwjgl.openal.AL10.alDeleteBuffers;
import static org.lwjgl.openal.AL10.alGenBuffers;
import static org.lwjgl.openal.ALUtil.checkALError;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBVorbisInfo;

import com.snakybo.sengine.debug.Logger;
import com.snakybo.sengine.importer.AudioClipImporter;
import com.snakybo.sengine.importer.AudioClipImporter.AudioClipData;
import com.snakybo.sengine.io.FileUtils;
import com.snakybo.sengine.util.IDestroyable;

/**
 * @author Kevin
 * @since Feb 18, 2016
 */
public final class AudioClip implements IDestroyable
{
	private final IntBuffer buffer;
	
	private float duration;
	
	private int samples;
	
	/**
	 * Create a new audio clip
	 * @param clip - The file to load
	 */
	public AudioClip(String clip)
	{
		buffer = BufferUtils.createIntBuffer(1);
		
		if(!FileUtils.getFileExtension("./res/" + clip).equals("ogg"))
		{
			Logger.logException(new UnsupportedOperationException("Currently only .ogg sound files are supported"), this);
			return;
		}
		
		STBVorbisInfo info = STBVorbisInfo.malloc();		
		
		AudioClipData data = AudioClipImporter.importClip(clip, 32, info);
		duration = data.duration;
		samples = data.samples;
			
		alGenBuffers(buffer);
		checkALError();
			
		alBufferData(buffer.get(0), AL_FORMAT_MONO16, data.pcm, info.sample_rate());
		checkALError();
			
		info.free();
	}
	
	@Override
	public void destroy()
	{
		alDeleteBuffers(buffer);
	}
	
	/**
	 * @return The duration of the audio clip
	 */
	public final float getDuration()
	{
		return duration;
	}
	
	/**
	 * @return The number of samples the audio clip has
	 */
	public final int getNumSamples()
	{
		return samples;
	}
	
	/**
	 * @return The ID of this audio clip
	 */
	final int getBufferId()
	{
		return buffer.get(0);
	}
}
