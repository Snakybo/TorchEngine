package com.snakybo.sengine.test;

import com.snakybo.sengine.Game;

/**
 * @author Kevin
 * @since Feb 14, 2016
 */
public class TestGame extends Game
{
	@Override
	public void createScene()
	{
		
	}

	@Override
	public String getName()
	{
		return "Test Game";
	}
	
	public static void main(String[] args)
	{
		new TestGame();
	}
}
