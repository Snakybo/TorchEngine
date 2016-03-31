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

import com.snakybo.sengine.resource.ResourceDatabase;
import com.snakybo.sengine.util.IDestroyable;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class AudioClip implements IDestroyable
{
	private final AudioResource resource;
	
	/**
	 * Create a new {@link AudioClip}. If the specified {@code clip} has already been imported,
	 * it will reuse the imported {@link AudioResource}
	 * @param clip - The clip to load
	 */
	public AudioClip(String clip)
	{
		clip = "./res/" + clip;
		
		if(ResourceDatabase.hasResource(clip))
		{
			resource = (AudioResource)ResourceDatabase.link(clip, this);
		}
		else
		{
			resource = new AudioResource(clip);
			ResourceDatabase.register(clip, resource, this);
		}
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
		resource.bind(source);
	}
	
	/**
	 * @return The duration in seconds
	 */
	public final float getDuration()
	{
		return resource.getDuration();
	}
	
	/**
	 * @return The number of samples
	 */
	public final int getNumSamples()
	{
		return resource.getNumSamples();
	}
	
	/**
	 * @return The sample rate
	 */
	public final int getSampleRate()
	{
		return resource.getSampleRate();
	}
}
