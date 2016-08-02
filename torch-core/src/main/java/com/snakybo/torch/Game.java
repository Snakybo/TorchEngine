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

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Game
{
	private static String name = "TorchEngine Game";
	
	private static int targetFrameRate = 60;
	
	public static void setName(String name)
	{
		Game.name = name;
	}
	
	/**
	 * Start the game.
	 */
	public static void start()
	{
		Engine.start();
	}
	
	/**
	 * Quit the game.
	 */
	public static void quit()
	{
		Engine.stop();
	}
	
	/**
	 * Set the target frame rate of the game.
	 * @param targetFrameRate The new target frame rate.
	 */
	public static void setTargetFrameRate(int targetFrameRate)
	{
		if(targetFrameRate <= 0)
		{
			throw new IllegalArgumentException("Target frame rate must be above 0");
		}
		
		Game.targetFrameRate = targetFrameRate;
	}
	
	/**
	 * Get the name of the game.
	 * @return The name of the game.
	 */
	public static String getName()
	{
		return name;
	}
	
	/**
	 * Get the target frame rate specified.
	 * @return The target frame rate.
	 */
	public static int getTargetFrameRate()
	{
		return targetFrameRate;
	}
}
