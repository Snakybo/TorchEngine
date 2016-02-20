package com.snakybo.sengine.audio;

import static org.lwjgl.openal.AL10.AL_BUFFER;
import static org.lwjgl.openal.AL10.alBufferData;
import static org.lwjgl.openal.AL10.alDeleteBuffers;
import static org.lwjgl.openal.AL10.alGenBuffers;
import static org.lwjgl.openal.AL10.alSourcei;
import static org.lwjgl.openal.ALUtil.checkALError;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import com.snakybo.sengine.asset.Asset;
import com.snakybo.sengine.importer.AudioAssetImporter;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class AudioAsset extends Asset
{
	private final IntBuffer buffer;
	
	private final String name;
	
	private float duration;
	
	private int numSamples;
	private int sampleRate;
	
	/**
	 * Create a new {@link AudioAsset}, it will automatically import the specified {@code clip}
	 * @param clip - The clip to import
	 */
	public AudioAsset(String clip)
	{
		buffer = BufferUtils.createIntBuffer(1);
		name = clip;
		
		AudioAssetImporter.beginImport(clip, 32);
		
		duration = AudioAssetImporter.getDuration();
		numSamples = AudioAssetImporter.getNumSamples();
		sampleRate = AudioAssetImporter.getSampleRate();
		
		alGenBuffers(buffer);
		checkALError();
			
		alBufferData(buffer.get(0), AudioAssetImporter.getFormat(), AudioAssetImporter.getPCM(), sampleRate);
		checkALError();
		
		AudioAssetImporter.endImport();
	}
	
	@Override
	public void destroy()
	{
		alDeleteBuffers(buffer);
	}
	
	/**
	 * Bind the {@link AudioAsset} to a source
	 * @param source - The source to bind to
	 */
	public void bind(int source)
	{
		alSourcei(source, AL_BUFFER, buffer.get(0));
		checkALError();
	}
	
	/**
	 * @return The name of the {@link AudioAsset}
	 */
	public final String getName()
	{
		return name;
	}
	
	/**
	 * @return The duration of the imported {@link AudioAsset}
	 */
	public final float getDuration()
	{
		return duration;
	}
	
	/**
	 * @return The number of samples the imported {@link AudioAsset} has
	 */
	public final int getNumSamples()
	{
		return numSamples;
	}
	
	/**
	 * @return The sample rate of the imported {@link AudioAsset}
	 */
	public final int getSampleRate()
	{
		return sampleRate;
	}
}
