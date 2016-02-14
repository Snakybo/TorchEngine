package com.snakybo.sengine.input.mouse;

/**
 * @author Kevin
 * @since Feb 14, 2016
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
}
