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

package com.snakybo.torch;

import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.debug.LoggerInternal;
import com.snakybo.torch.util.SharedLibraryLoader;
import com.snakybo.torch.util.time.TimeInternal;

/**
 * @author Snakybo
 * @since 1.0
 */
public abstract class Game
{
	private static TorchEngine engine;
	private static String name;
	
	private static int targetFrameRate = 60;
	
	/**
	 * Create a game
	 * @param name The name of the game
	 */
	public Game(String name)
	{
		SharedLibraryLoader.load();
		
		Game.name = name;
		Game.engine = new TorchEngine(this);
		
		LoggerInternal.log("Game name: " + name, "Game");
	}
	
	protected abstract void onCreate();
	
	/**
	 * Quit the game
	 */
	public static void quit()
	{
		engine.stop();
	}
	
	/**
	 * Set the target frame rate
	 * @param targetFrameRate The new target frame rate
	 */
	public static void setTargetFrameRate(int targetFrameRate)
	{
		if(targetFrameRate <= 0)
		{
			Logger.logException(new IllegalArgumentException("Target frame rate must be above 0"));
			return;
		}
		
		Game.targetFrameRate = targetFrameRate;
		
		// Update the frame time
		TimeInternal.updateFrameTime();
	}
	
	/**
	 * @return The name of the game
	 */
	public static String getName()
	{
		return name;
	}
	
	/**
	 * @return The target frame rate
	 */
	public static int getTargetFrameRate()
	{
		return targetFrameRate;
	}
}
