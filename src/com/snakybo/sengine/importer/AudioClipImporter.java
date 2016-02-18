package com.snakybo.sengine.importer;

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
import com.snakybo.sengine.util.ALUtil;

/**
 * @author Kevin
 * @since Feb 18, 2016
 */
public final class AudioClipImporter
{
	public static class AudioClipData
	{
		public ByteBuffer pcm;
		
		public float duration;
		
		public int samples;
	}
	
	private AudioClipImporter()
	{
		throw new AssertionError();
	}
	
	/**
	 * Import an audio clip
	 * @param fileName - The file name
	 * @param bufferSize - The buffer size
	 * @param info - The STB Vortis information
	 * @return The data of the audio clip
	 */
	public static AudioClipData importClip(String fileName, int bufferSize, STBVorbisInfo info)
	{
		Logger.log("Importing audio asset: " + fileName, "AudioClipImporter");
		
		try
		{
			ByteBuffer vorbis = ALUtil.ioResourceToByteBuffer("./res/" + fileName, bufferSize);
			
			IntBuffer error = BufferUtils.createIntBuffer(1);
			long decoder = stb_vorbis_open_memory(vorbis, error, null);
			
			if(decoder == NULL)
			{
				Logger.logException(new RuntimeException("Unable to open OGG Vorbis clip: " + ALUtil.getALErrorString(error.get(0))), "AudioClip");
				return null;
			}
			
			return createAudioClip(decoder, info);
		}
		catch(IOException e)
		{
			Logger.logException(e, "AudioClip");
		}
		
		return null;
	}
	
	/**
	 * Create AudioClipData from a decoder
	 * @param decoder - The decoder
	 * @param info - The STB Vorbis information
	 * @return The audio clip data
	 */
	private static AudioClipData createAudioClip(long decoder, STBVorbisInfo info)
	{
		AudioClipData data = new AudioClipData();
		
		stb_vorbis_get_info(decoder, info);
		
		int channels = info.channels();
		
		data.samples = stb_vorbis_stream_length_in_samples(decoder);
		data.duration = stb_vorbis_stream_length_in_seconds(decoder);		
		data.pcm = BufferUtils.createByteBuffer(data.samples * 2);
		
		stb_vorbis_get_samples_short_interleaved(decoder, channels, data.pcm, data.samples);
		stb_vorbis_close(decoder);
		
		return data;
	}
}
