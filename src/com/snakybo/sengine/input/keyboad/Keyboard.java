package com.snakybo.sengine.input.keyboad;

/**
 * @author Kevin
 * @since Feb 13, 2016
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
