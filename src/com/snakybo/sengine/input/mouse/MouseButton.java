package com.snakybo.sengine.input.mouse;

/**
 * @author Snakybo
 * @since 1.0
 */
public enum MouseButton
{
	BUTTON_1      (0x0),
	BUTTON_2      (0x1),
	BUTTON_3      (0x2),
	BUTTON_4      (0x3),
	BUTTON_5      (0x4),
	BUTTON_6      (0x5),
	BUTTON_7      (0x6),
	BUTTON_8      (0x7),
	LEFT          (BUTTON_1),
	RIGHT         (BUTTON_2),
	MIDDLE        (BUTTON_3);
	
	final int id;
	
	private MouseButton(MouseButton mouseButton)
	{
		this(mouseButton.id);
	}
	
	private MouseButton(int id)
	{
		this.id = id;
	}
	
	@Override
	public final String toString()
	{
		switch(this)
		{
		case BUTTON_1:
		case LEFT:
			return "Mouse Button 1";
		case BUTTON_2:
		case RIGHT:
			return "Mouse Button 2";
		case BUTTON_3:
		case MIDDLE:
			return "Mouse Button 3";
		case BUTTON_4:
			return "Mouse Button 4";
		case BUTTON_5:
			return "Mouse Button 5";
		case BUTTON_6:
			return "Mouse Button 6";
		case BUTTON_7:
			return "Mouse Button 7";
		case BUTTON_8:
			return "Mouse Button 8";
		default:
			return "Unknown";
		}
	}
}
