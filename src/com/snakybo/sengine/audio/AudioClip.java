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

import com.snakybo.sengine.asset.AssetDatabase;
import com.snakybo.sengine.util.IDestroyable;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class AudioClip implements IDestroyable
{
	private final AudioAsset asset;
	
	/**
	 * Create a new {@link AudioClip}. If the specified {@code clip} has already been imported,
	 * it will reuse the imported {@link AudioAsset}
	 * @param clip - The clip to load
	 */
	public AudioClip(String clip)
	{
		clip = "./res/" + clip;
		
		if(AssetDatabase.hasAsset(clip))
		{
			asset = (AudioAsset)AssetDatabase.link(clip, this);
		}
		else
		{
			asset = new AudioAsset(clip);
			AssetDatabase.register(clip, asset, this);
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
		AssetDatabase.unlink(asset.getName(), this);
	}
	
	/**
	 * Bind the {@link AudioClip} to a source
	 * @param source - The source to bind to
	 */
	final void bind(int source)
	{
		asset.bind(source);
	}
	
	/**
	 * @return The duration of the audio clip
	 */
	public final float getDuration()
	{
		return asset.getDuration();
	}
	
	/**
	 * @return The number of samples the audio clip has
	 */
	public final int getNumSamples()
	{
		return asset.getNumSamples();
	}
	
	/**
	 * @return The sample rate of the audio clip
	 */
	public final int getSampleRate()
	{
		return asset.getSampleRate();
	}
}
