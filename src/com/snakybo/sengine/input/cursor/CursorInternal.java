package com.snakybo.sengine.input.cursor;

import org.joml.Vector2f;

import com.snakybo.sengine.input.keyboad.Key;
import com.snakybo.sengine.input.keyboad.Keyboard;
import com.snakybo.sengine.input.mouse.Mouse;
import com.snakybo.sengine.window.Window;

/**
 * @author Snakybo
 * @since 1.0
 */
public class CursorInternal
{
	private CursorInternal()
	{
		throw new AssertionError();
	}
	
	/**
	 * Unused
	 */
	public static void create()
	{
	}
	
	/**
	 * Unused
	 */
	public static void destroy()
	{
	}
	
	/**
	 * Update the cursor. Applies the {@link CursorLockMode} set by {@link Cursor#setLockMode(CursorLockMode)}
	 */
	public static void update()
	{
		// If shift+escape is pressed, always unlock the cursor
		if(Keyboard.isKeyDown(Key.LEFT_SHIFT) && Keyboard.onKeyDown(Key.ESCAPE))
		{
			Cursor.setLockMode(CursorLockMode.None);
			Cursor.setVisible(true);
		}
		
		// Handle the cursor's behaviour
		switch(Cursor.lockMode)
		{
		case Locked:
			handleLockedCursor();
			break;
		case Confined:
			handleConfinedCursor();
			break;
		default:
			break;
		}
	}
	
	/**
	 * Behaviour for {@link CursorLockMode#Locked}
	 */
	private static void handleLockedCursor()
	{
		Vector2f center = Window.getCenter();
		Mouse.setCursorPosition((int)center.x, (int)center.y);
	}
	
	/**
	 * Behaviour for {@link CursorLockMode#Confined}
	 */
	private static void handleConfinedCursor()
	{
		Vector2f cursorPosition = Mouse.getCursorPosition();
		
		if(cursorPosition.x < 0)
		{
			cursorPosition.x = 0;
		}
		else if(cursorPosition.x > Window.getWidth())
		{
			cursorPosition.x = Window.getWidth();
		}
		
		if(cursorPosition.y < 0)
		{
			cursorPosition.y = 0;
		}
		else if(cursorPosition.y > Window.getHeight())
		{
			cursorPosition.y = Window.getHeight();
		}
		
		Mouse.setCursorPosition(cursorPosition);	
	}
}
