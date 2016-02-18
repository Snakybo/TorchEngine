package com.snakybo.sengine.util;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Color32
{
	private int r;
	private int g;
	private int b;
	private int a;
	
	/**
	 * Create a new color
	 */
	public Color32()
	{
		this(0, 0, 0);
	}
	
	/**
	 * Create a new color
	 * @param r - The red component
	 * @param g - The green component
	 * @param b - The blue component
	 */
	public Color32(int r, int g, int b)
	{
		this(r, g, b, 255);
	}
	
	/**
	 * Create a new color
	 * @param r - The red component
	 * @param g - The green component
	 * @param b - The blue component
	 * @param a - The alpha component
	 */
	public Color32(int r, int g, int b, int a)
	{
		setRed(r);
		setGreen(g);
		setBlue(b);
		setAlpha(a);
	}
	
	@Override
	public String toString()
	{
		return "(" + r + ", " + g + ", " + b + ", " + a + ")";
	}
	
	/**
	 * Set the red component of the color [0-255]
	 * @param r - The red component
	 */
	public final void setRed(int r)
	{
		this.r = MathUtils.clamp(r, 0, 1);
	}
	
	/**
	 * Set the green component of the color [0-255]
	 * @param g - The green component
	 */
	public final void setGreen(int g)
	{
		this.g = MathUtils.clamp(g, 0, 1);
	}
	
	/**
	 * Set the blue component of the color [0-255]
	 * @param b - The blue component
	 */
	public final void setBlue(int b)
	{
		this.b = MathUtils.clamp(b, 0, 1);
	}
	
	/**
	 * Set the alpha component of the color [0-255]
	 * @param a - The alpha component
	 */
	public final void setAlpha(int a)
	{
		this.a = MathUtils.clamp(a, 0, 1);
	}
	
	/**
	 * Convert the 32-bit color into a color
	 */
	public final Color32 toColor()
	{
		return new Color32(r / 255, g / 255, b / 255, a / 255);
	}
	
	/**
	 * @return The red component of the color
	 */
	public final int getRed()
	{
		return r;
	}
	
	/**
	 * @return The green component of the color
	 */
	public final int getGreen()
	{
		return g;
	}
	
	/**
	 * @return The blue component of the color
	 */
	public final int getBlue()
	{
		return b;
	}

	/**
	 * @return The alpha component of the color
	 */
	public final int getAlpha()
	{
		return a;
	}
	
	/**
	 * Convert the RGB values of the color to an integer
	 */
	public final int getRGB()
	{
		int red = (r << 16) & 0x00FF0000;
		int green = (g << 8) & 0x0000FF00;
		int blue = b & 0x000000FF;
		
		return 0xFF000000 | red | green | blue;
	}
	
	/**
	 * Convert the RGBA values of the color to an integer
	 */
	public final int getRGBA()
	{
		int alpha = (a << 24) & 0xFF000000;
		int red = (r << 16) & 0x00FF0000;
		int green = (g << 8) & 0x0000FF00;
		int blue = b & 0x000000FF;
		
		return alpha | red | green | blue;
	}
}
