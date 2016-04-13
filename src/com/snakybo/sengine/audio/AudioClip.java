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
import static org.lwjgl.openal.AL10.alBufferData;
import static org.lwjgl.openal.AL10.alDeleteBuffers;
import static org.lwjgl.openal.AL10.alGenBuffers;
import static org.lwjgl.openal.AL10.alSourcei;
import static org.lwjgl.openal.ALUtil.checkALError;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import com.snakybo.sengine.resource.Resource;
import com.snakybo.sengine.resource.ResourceDatabase;
import com.snakybo.sengine.util.IDestroyable;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class AudioClip implements IDestroyable
{
	static class AudioClipResource extends Resource
	{
		final IntBuffer buffer;
		
		float duration;
		
		int numSamples;
		int sampleRate;
		
		/**
		 * Create a new {@link AudioClipResource}, it will automatically import the specified {@code clip}
		 * @param clip - The clip to import
		 */
		AudioClipResource(String fileName, float duration, int numSamples, int sampleRate, int format, ByteBuffer pcm)
		{
			super(fileName);
			
			this.buffer = BufferUtils.createIntBuffer(1);
			this.duration = duration;
			this.numSamples = numSamples;
			this.sampleRate = sampleRate;
			
			alGenBuffers(buffer);
			checkALError();
				
			alBufferData(buffer.get(0), format, pcm, sampleRate);
			checkALError();
		}
		
		@Override
		public void destroy()
		{
			alDeleteBuffers(buffer);
		}
	}
	
	private final AudioClipResource resource;
	
	/**
	 * Create a new {@link AudioClip}. If the specified {@code clip} has already been imported,
	 * it will reuse the imported {@link AudioClipResource}
	 * @param clip - The clip to load
	 */
	public AudioClip(String clip)
	{
		clip = "./res/" + clip;		
		resource = ResourceDatabase.load(AudioClipResource.class, clip, this, new AudioResourceImporter(clip, 32));
	}
	
	@Override
	protected void finalize() throws Throwable
	{
		try
		{
			destroy();
		}
		finally
		{
			super.finalize();
		}
	}
	
	@Override
	public void destroy()
	{
		ResourceDatabase.unlink(resource.getName(), this);
	}
	
	/**
	 * Bind the {@link AudioClip} to a source
	 * @param source - The source to bind to
	 */
	final void bind(int source)
	{
		alSourcei(source, AL_BUFFER, resource.buffer.get(0));
		checkALError();
	}
	
	/**
	 * @return The duration in seconds
	 */
	public final float getDuration()
	{
		return resource.duration;
	}
	
	/**
	 * @return The number of samples
	 */
	public final int getNumSamples()
	{
		return resource.numSamples;
	}
	
	/**
	 * @return The sample rate
	 */
	public final int getSampleRate()
	{
		return resource.sampleRate;
	}
}
