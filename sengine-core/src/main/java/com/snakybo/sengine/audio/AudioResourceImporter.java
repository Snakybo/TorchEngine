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

import com.snakybo.sengine.audio.AudioClip.AudioClipResource;
import com.snakybo.sengine.debug.Logger;
import com.snakybo.sengine.debug.LoggerInternal;
import com.snakybo.sengine.io.FileUtils;
import com.snakybo.sengine.resource.ResourceImporter;
import com.snakybo.sengine.util.ALUtil;

/**
 * @author Snakybo
 * @since 1.0
 */
final class AudioResourceImporter extends ResourceImporter<AudioClipResource>
{
	private final String fileName;
	private final int bufferSize;
	
	AudioResourceImporter(String fileName, int bufferSize)
	{
		this.fileName = fileName;
		this.bufferSize = bufferSize;
	}
	
	@Override
	protected AudioClipResource importResource()
	{
		if(!isFileValid(fileName))
		{
			return null;
		}
		
		STBVorbisInfo vorbisInfo;
		ByteBuffer pcm;
		
		long decoder;
		
		LoggerInternal.log("Beginning import of audio: " + fileName, this);
		
		try
		{
			ByteBuffer vorbis = ALUtil.ioResourceToByteBuffer(fileName, bufferSize);			
			IntBuffer error = BufferUtils.createIntBuffer(1);
			
			vorbisInfo = STBVorbisInfo.malloc();
			decoder = stb_vorbis_open_memory(vorbis, error, null);
			
			if(decoder == NULL)
			{
				Logger.logException(new RuntimeException("Unable to open OGG Vorbis clip: " + ALUtil.getALErrorString(error.get(0))), this);
				
				stb_vorbis_close(decoder);
				vorbisInfo.free();
				
				return null;
			}
			
			stb_vorbis_get_info(decoder, vorbisInfo);
		}
		catch(IOException e)
		{
			Logger.logException(e, this);
			return null;
		}
		
		float duration = stb_vorbis_stream_length_in_seconds(decoder);
		int numSamples = stb_vorbis_stream_length_in_samples(decoder);
		int sampleRate = vorbisInfo.sample_rate();		
		int format = vorbisInfo.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16;
		
		pcm = BufferUtils.createByteBuffer(numSamples * 2);		
		stb_vorbis_get_samples_short_interleaved(decoder, vorbisInfo.channels(), pcm, numSamples);
		
		return new AudioClipResource(fileName, duration, numSamples, sampleRate, format, pcm);
	}
	
	private final boolean isFileValid(String fileName)
	{
		String extension = FileUtils.getFileExtension(fileName);
		
		if(extension.equals("ogg"))
		{
			return true;
		}
		
		Logger.logException(new UnsupportedOperationException("Unable to import: " + fileName + ", currently only .ogg sound files are supported"), this);
		return false;
	}
}
