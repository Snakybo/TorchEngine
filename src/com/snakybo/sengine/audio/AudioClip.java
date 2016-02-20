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
