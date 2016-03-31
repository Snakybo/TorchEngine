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

package com.snakybo.sengine.resource.loader;

import static org.lwjgl.openal.AL10.AL_FORMAT_MONO16;
import static org.lwjgl.openal.AL10.AL_FORMAT_STEREO16;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_close;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_get_info;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_get_samples_short_interleaved;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_open_memory;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_stream_length_in_samples;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_stream_length_in_seconds;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.stb.STBVorbisInfo;

import com.snakybo.sengine.debug.Logger;
import com.snakybo.sengine.debug.LoggerInternal;
import com.snakybo.sengine.io.FileUtils;
import com.snakybo.sengine.util.ALUtil;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class AudioResourceLoader extends ResourceLoader
{
	private static STBVorbisInfo vorbisInfo;
	private static long decoder;
	
	@Override
	public void beginImport(String resource, Object... objects)
	{
		if(!canImport(resource))
		{
			return;
		}
		
		if(vorbisInfo != null)
		{
			Logger.logWarning("Another audio resource has not finished importing", "AudioResourceImporter");
		}
		
		LoggerInternal.log("Beginning import of audio resource: " + resource, "AudioResourceImporter");
		
		try
		{
			ByteBuffer vorbis = ALUtil.ioResourceToByteBuffer(resource, (int)objects[0]);			
			IntBuffer error = BufferUtils.createIntBuffer(1);
			
			vorbisInfo = STBVorbisInfo.malloc();
			decoder = stb_vorbis_open_memory(vorbis, error, null);
			
			if(decoder == NULL)
			{
				Logger.logException(new RuntimeException("Unable to open OGG Vorbis clip: " + ALUtil.getALErrorString(error.get(0))), "AudioClip");
				endImport();
				return;
			}
			
			stb_vorbis_get_info(decoder, vorbisInfo);
		}
		catch(IOException e)
		{
			Logger.logException(e, "AudioResourceImporter");
		}
	}
	
	@Override
	public void endImport()
	{
		stb_vorbis_close(decoder);
		
		vorbisInfo.free();
		vorbisInfo = null;
	}
	
	@Override
	public boolean canImport(String resource)
	{
		String extension = FileUtils.getFileExtension(resource);
		
		if(extension.equals("ogg"))
		{
			return true;
		}
		
		Logger.logException(new UnsupportedOperationException("Unable to import: " + resource + ", currently only .ogg sound files are supported"), "AudioResourceImporter");
		return false;
	}
	
	/**
	 * @return The PCM data of the imported resource
	 */
	public static ByteBuffer getPCM()
	{
		int channels = getNumChannels();
		int numSamples = getNumSamples();
		
		ByteBuffer pcm = BufferUtils.createByteBuffer(numSamples * 2);		
		stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm, numSamples);
		
		return pcm;
	}
	
	/**
	 * @return The duration in seconds of the imported resource
	 */
	public static float getDuration()
	{
		return stb_vorbis_stream_length_in_seconds(decoder);
	}
	
	/**
	 * @return The number of samples the imported resource has
	 */
	public static int getNumSamples()
	{
		return stb_vorbis_stream_length_in_samples(decoder);
	}
	
	/**
	 * @return The sample rate of the imported resource
	 */
	public static int getSampleRate()
	{
		return vorbisInfo.sample_rate();
	}
	
	/**
	 * @return The amount of channels the imported resource has
	 */
	public static int getNumChannels()
	{
		return vorbisInfo.channels();
	}
	
	/**
	 * @return The OpenAL format of the imported resource,
	 * {@link AL10#AL_FORMAT_MONO16} or {@link AL10#AL_FORMAT_STEREO16}
	 */
	public static int getFormat()
	{
		int channels = getNumChannels();
		
		switch(channels)
		{
		case 1:
			return AL_FORMAT_MONO16;
		case 2:
			return AL_FORMAT_STEREO16;
		default:
			Logger.logException(new UnsupportedOperationException("Unsupported number of channels: " + channels), "AudioResourceImporter");
		}
		
		return AL_FORMAT_MONO16;
	}
}
