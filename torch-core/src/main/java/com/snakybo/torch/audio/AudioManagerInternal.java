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

package com.snakybo.torch.audio;

import static org.lwjgl.openal.AL10.AL_EXTENSIONS;
import static org.lwjgl.openal.AL10.AL_RENDERER;
import static org.lwjgl.openal.AL10.AL_VENDOR;
import static org.lwjgl.openal.AL10.AL_VERSION;
import static org.lwjgl.openal.AL10.alGetString;

import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALContext;
import org.lwjgl.openal.ALDevice;

import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.debug.LoggerInternal;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class AudioManagerInternal
{
	private static ALContext context;
	
	private AudioManagerInternal()
	{
		throw new AssertionError();
	}	
	
	public static void create()
	{
		LoggerInternal.log("Initializing OpenAL", "AudioManager");
		
		ALDevice device = ALDevice.create(null);
		if(device == null)
		{
			Logger.logException(new RuntimeException("Unable to create the default audio output device"), "AudioManager");
			return;
		}
		
		ALCCapabilities caps = device.getCapabilities();
		if(!caps.OpenALC10)
		{
			Logger.logException(new RuntimeException("The default audio output device does not support OpenALC10"), "AudioManager");
			return;
		}
		
		context = ALContext.create(device);
		
		logOpenALInfo();
	}
	
	public static void destroy()
	{
		LoggerInternal.log("Terminating OpenAL", "AudioManager");
		
		context.destroy();
		context.getDevice().destroy();
	}
	
	private static void logOpenALInfo()
	{
		LoggerInternal.log("Vendor: " + alGetString(AL_VENDOR), "OpenAL");
		LoggerInternal.log("Renderer: " + alGetString(AL_RENDERER), "OpenAL");
		LoggerInternal.log("Version: " + alGetString(AL_VERSION), "OpenAL");		
		LoggerInternal.log("Extensions: " + alGetString(AL_EXTENSIONS), "OpenAL");
	}
}
