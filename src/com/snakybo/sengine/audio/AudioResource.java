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

/**
 * @author Snakybo
 * @since 1.0
 */
final class AudioResource extends Resource
{
	private final IntBuffer buffer;
	
	private float duration;
	
	private int numSamples;
	private int sampleRate;
	
	/**
	 * Create a new {@link AudioResource}, it will automatically import the specified {@code clip}
	 * @param clip - The clip to import
	 */
	AudioResource(String fileName, float duration, int numSamples, int sampleRate, int format, ByteBuffer pcm)
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
