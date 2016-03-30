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

package com.snakybo.sengine.importer;

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

import com.snakybo.sengine.audio.AudioAsset;
import com.snakybo.sengine.debug.Logger;
import com.snakybo.sengine.debug.LoggerInternal;
import com.snakybo.sengine.io.FileUtils;
import com.snakybo.sengine.util.ALUtil;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class AudioAssetImporter
{
	private static STBVorbisInfo vorbisInfo;
	private static long decoder;
	
	private AudioAssetImporter()
	{
		throw new AssertionError();
	}
	
	/**
	 * Begin importing an {@link AudioAsset}, make sure to call {@link #endImport()} when you're done importing.
	 * @param asset - The asset to load
	 * @param bufferSize - The buffer size of the {@link AudioAsset}
	 */
	public static void beginImport(String asset, int bufferSize)
	{
		if(!canImport(asset))
		{
			return;
		}
		
		if(vorbisInfo != null)
		{
			Logger.logWarning("Another audio asset has not finished importing", "AudioAssetImporter");
		}
		
		LoggerInternal.log("Beginning import of audio asset: " + asset, "AudioAssetImporter");
		
		try
		{
			ByteBuffer vorbis = ALUtil.ioResourceToByteBuffer(asset, bufferSize);			
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
			Logger.logException(e, "AudioAssetImporter");
		}
	}
	
	/**
	 * Stop importing an {@link AudioAsset},
	 * this will free up any memory used by {@link STBVorbisInfo} and it will close the decoder
	 */
	public static void endImport()
	{
		stb_vorbis_close(decoder);
		
		vorbisInfo.free();
		vorbisInfo = null;
	}
	
	/**
	 * Check if the specified {@code asset} is valid for importation
	 * @param asset - The asset to import
	 * @return Whether or not the asset can be imported
	 */
	private static boolean canImport(String asset)
	{
		String extension = FileUtils.getFileExtension(asset);
		
		if(extension.equals("ogg"))
		{
			return true;
		}
		
		Logger.logException(new UnsupportedOperationException("Unable to import: " + asset + ", currently only .ogg sound files are supported"), "AudioAssetImporter");
		return false;
	}
	
	/**
	 * @return The PCM data of the imported asset
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
	 * @return The duration in seconds of the imported asset
	 */
	public static float getDuration()
	{
		return stb_vorbis_stream_length_in_seconds(decoder);
	}
	
	/**
	 * @return The number of samples the imported asset has
	 */
	public static int getNumSamples()
	{
		return stb_vorbis_stream_length_in_samples(decoder);
	}
	
	/**
	 * @return The sample rate of the imported asset
	 */
	public static int getSampleRate()
	{
		return vorbisInfo.sample_rate();
	}
	
	/**
	 * @return The amount of channels the imported asset has
	 */
	public static int getNumChannels()
	{
		return vorbisInfo.channels();
	}
	
	/**
	 * @return The OpenAL format of the imported asset,
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
			Logger.logException(new UnsupportedOperationException("Unsupported number of channels: " + channels), "AudioAssetImporter");
		}
		
		return AL_FORMAT_MONO16;
	}
}
