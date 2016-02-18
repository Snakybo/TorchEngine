package com.snakybo.sengine.test;

import com.snakybo.sengine.Game;

/**
 * @author Snakybo
 * @since 1.0
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
