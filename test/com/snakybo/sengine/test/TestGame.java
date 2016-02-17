package com.snakybo.sengine.test;

import com.snakybo.sengine.Game;

/**
 * @author Kevin
 * @since Feb 14, 2016
 */
public class TestGame extends Game
{
	public TestGame()
	{
		super("Test Game");
	}

	@Override
	public void createScene()
	{
	}
	
	public static void main(String[] args)
	{
		new TestGame();
	}
}
