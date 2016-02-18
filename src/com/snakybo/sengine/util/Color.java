package com.snakybo.sengine.util;

/**
 * @author Kevin
 * @since Feb 18, 2016
 */
public final class Color
{
	private float r;
	private float g;
	private float b;
	private float a;
	
	/**
	 * Create a new color
	 */
	public Color()
	{
		this(0, 0, 0);
	}
	
	/**
	 * Create a new color
	 * @param r - The red component
	 * @param g - The green component
	 * @param b - The blue component
	 */
	public Color(float r, float g, float b)
	{
		this(r, g, b, 1);
	}
	
	/**
	 * Create a new color
	 * @param r - The red component
	 * @param g - The green component
	 * @param b - The blue component
	 * @param a - The alpha component
	 */
	public Color(float r, float g, float b, float a)
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
	 * Set the red component of the color [0-1]
	 * @param r - The red component
	 */
	public final void setRed(float r)
	{
		this.r = MathUtils.clamp(r, 0, 1);
	}
	
	/**
	 * Set the green component of the color [0-1]
	 * @param g - The green component
	 */
	public final void setGreen(float g)
	{
		this.g = MathUtils.clamp(g, 0, 1);
	}
	
	/**
	 * Set the blue component of the color [0-1]
	 * @param b - The blue component
	 */
	public final void setBlue(float b)
	{
		this.b = MathUtils.clamp(b, 0, 1);
	}
	
	/**
	 * Set the alpha component of the color [0-1]
	 * @param a - The alpha component
	 */
	public final void setAlpha(float a)
	{
		this.a = MathUtils.clamp(a, 0, 1);
	}
	
	/**
	 * Convert the color to a 32-bit color
	 */
	public final Color32 toColor32()
	{
		int red = (int)Math.ceil(r * 255);
		int green = (int)Math.ceil(r * 255);
		int blue = (int)Math.ceil(r * 255);
		int alpha = (int)Math.ceil(r * 255);
		
		return new Color32(red, green, blue, alpha);
	}
	
	/**
	 * @return The red component of the color
	 */
	public final float getRed()
	{
		return r;
	}
	
	/**
	 * @return The green component of the color
	 */
	public final float getGreen()
	{
		return g;
	}
	
	/**
	 * @return The blue component of the color
	 */
	public final float getBlue()
	{
		return b;
	}

	/**
	 * @return The alpha component of the color
	 */
	public final float getAlpha()
	{
		return a;
	}
	
	/**
	 * Convert the RGB values of the color to an integer
	 */
	public final int getRGB()
	{
		return toColor32().getRGB();
	}
	
	/**
	 * Convert the RGBA values of the color to an integer
	 */
	public final int getRGBA()
	{
		return toColor32().getRGBA();
	}
}
