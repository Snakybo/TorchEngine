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
import org.lwjgl.stb.STBVorbisInfo;

import com.snakybo.sengine.debug.Logger;
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
	
	public static void beginImport(String asset, int bufferSize)
	{
		if(!canImport(asset))
		{
			return;
		}
		
		if(vorbisInfo != null)
		{
			Logger.logWarning("Another Audio Asset has not finished importing", "AudioAssetImporter");
		}
		
		Logger.log("Beginning import of audio asset: " + asset, "AudioAssetImporter");
		
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
	
	public static void endImport()
	{
		stb_vorbis_close(decoder);
		
		vorbisInfo.free();
		vorbisInfo = null;
	}
	
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
	
	public static ByteBuffer getPCM()
	{
		int channels = getNumChannels();
		int numSamples = getNumSamples();
		
		ByteBuffer pcm = BufferUtils.createByteBuffer(numSamples * 2);		
		stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm, numSamples);
		
		return pcm;
	}
	
	public static float getDuration()
	{
		return stb_vorbis_stream_length_in_seconds(decoder);
	}
	
	public static int getNumSamples()
	{
		return stb_vorbis_stream_length_in_samples(decoder);
	}
	
	public static int getSampleRate()
	{
		return vorbisInfo.sample_rate();
	}
	
	public static int getNumChannels()
	{
		return vorbisInfo.channels();
	}
	
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
