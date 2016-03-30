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

package com.snakybo.sengine.input.keyboad;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Keyboard
{
	private static final int LAST = Key.MENU.id;
	
	static boolean[] current = new boolean[LAST];
	static boolean[] last = new boolean[LAST];
	
	private Keyboard()
	{
		throw new AssertionError();
	}
	
	/**
	 * Returns true if the specified key is currently held down
	 * @param key - The key
	 * @return True if the key is currently held down
	 * @see Key
	 */
	public static boolean isKeyDown(Key key)
	{
		return current[key.id];
	}
	
	/**
	 * Returns true the frame when the specified key is pressed 
	 * @param key - The key
	 * @return True if the key was pressed in this frame
	 * @see Key
	 */
	public static boolean onKeyDown(Key key)
	{
		return current[key.id] && !last[key.id];
	}
	
	/**
	 * Returns true the frame when the specified key was released
	 * @param key - The key
	 * @return True if the key was released in this frame
	 * @see Key
	 */
	public static boolean onKeyUp(Key key)
	{
		return !current[key.id] && last[key.id];
	}
}
