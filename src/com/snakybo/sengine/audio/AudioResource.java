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

package com.snakybo.sengine.audio;

import static org.lwjgl.openal.AL10.AL_BUFFER;
import static org.lwjgl.openal.AL10.AL_FORMAT_MONO16;
import static org.lwjgl.openal.AL10.AL_FORMAT_STEREO16;
import static org.lwjgl.openal.AL10.alBufferData;
import static org.lwjgl.openal.AL10.alDeleteBuffers;
import static org.lwjgl.openal.AL10.alGenBuffers;
import static org.lwjgl.openal.AL10.alSourcei;
import static org.lwjgl.openal.ALUtil.checkALError;
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
import org.lwjgl.stb.STBVorbisInfo;

import com.snakybo.sengine.debug.Logger;
import com.snakybo.sengine.debug.LoggerInternal;
import com.snakybo.sengine.io.FileUtils;
import com.snakybo.sengine.resource.RuntimeResource;
import com.snakybo.sengine.util.ALUtil;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class AudioResource extends RuntimeResource
{
	private final IntBuffer buffer;
	
	private final String name;
	
	private STBVorbisInfo vorbisInfo;
	private ByteBuffer pcm;
	
	private long decoder;
	
	private float duration;
	
	private int numSamples;
	private int sampleRate;
	private int format;
	
	/**
	 * Create a new {@link AudioResource}, it will automatically import the specified {@code clip}
	 * @param clip - The clip to import
	 */
	public AudioResource(String clip)
	{
		buffer = BufferUtils.createIntBuffer(1);
		name = clip;
		
		beginImport(clip, 32);
		
		alGenBuffers(buffer);
		checkALError();
			
		alBufferData(buffer.get(0), format, pcm, sampleRate);
		checkALError();
		
		endImport();
	}
	
	@Override
	public void destroy()
	{
		alDeleteBuffers(buffer);
	}
	
	@Override
	protected void beginImport(String resource, Object... objects)
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
			Logger.logException(e, "AudioResource");
		}
		
		duration = stb_vorbis_stream_length_in_seconds(decoder);
		numSamples = stb_vorbis_stream_length_in_samples(decoder);
		sampleRate = vorbisInfo.sample_rate();
		
		format = vorbisInfo.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16;
		
		pcm = BufferUtils.createByteBuffer(numSamples * 2);		
		stb_vorbis_get_samples_short_interleaved(decoder, vorbisInfo.channels(), pcm, numSamples);
	}

	@Override
	protected void endImport()
	{
		stb_vorbis_close(decoder);
		
		vorbisInfo.free();
		vorbisInfo = null;
	}

	@Override
	protected boolean canImport(String resource)
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
	 * Bind the {@link AudioResource} to a source
	 * @param source - The source to bind to
	 */
	public void bind(int source)
	{
		alSourcei(source, AL_BUFFER, buffer.get(0));
		checkALError();
	}
	
	/**
	 * @return The name
	 */
	public final String getName()
	{
		return name;
	}
	
	/**
	 * @return The duration in seconds
	 */
	public final float getDuration()
	{
		return duration;
	}
	
	/**
	 * @return The number of samples
	 */
	public final int getNumSamples()
	{
		return numSamples;
	}
	
	/**
	 * @return The sample rate
	 */
	public final int getSampleRate()
	{
		return sampleRate;
	}
}
