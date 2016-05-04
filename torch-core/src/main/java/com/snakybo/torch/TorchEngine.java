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

package com.snakybo.torch;

import org.lwjgl.Version;

import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.debug.LoggerInternal;
import com.snakybo.torch.input.cursor.CursorInternal;
import com.snakybo.torch.input.joystick.JoystickInternal;
import com.snakybo.torch.input.keyboad.KeyboardInternal;
import com.snakybo.torch.input.mouse.MouseInternal;
import com.snakybo.torch.renderer.OpenGLRenderer;
import com.snakybo.torch.scene.SceneInternal;
import com.snakybo.torch.time.TimeInternal;
import com.snakybo.torch.window.WindowInternal;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class TorchEngine
{
	/**
	 * The major version of the engine.
	 */
	public static final int VERSION_MAJOR = 1;
	
	/**
	 * The minor version of the engine.
	 */
	public static final int VERSION_MINOR = 0;
	
	/**
	 * The patch version of the engine.
	 */
	public static final int VERSION_PATCH = 0;
	
	/**
	 * The version of the engine as a String, in the format {@link #VERSION_MAJOR}.{@link #VERSION_MINOR}.{@link #VERSION_PATCH}.
	 */
	public static final String VERSION_STRING = VERSION_MAJOR + "." + VERSION_MINOR + "." + VERSION_PATCH;
	
	private TorchGame game;	
	private boolean running;
	
	/**
	 * Create an instance of the engine, called by {@link TorchGame#TorchGame(String)}.
	 * @param game The instance of the game to play.
	 */
	TorchEngine(TorchGame game)
	{
		LoggerInternal.log("Starting", this);
		LoggerInternal.log("Engine version: " + VERSION_STRING, this);
		
		logLWJGLInfo();
		
		// Initialize engine systems
		WindowInternal.createWindowed(1280, 720);
		OpenGLRenderer.create();
		
		this.game = game;
		
		start();
	}
	
	/**
	 * Start the engine, called by {@link TorchGame#start()}.
	 */
	private final void start()
	{
		if(!running)
		{
			running = true;
			
			game.onCreate();
			mainLoop();
		}
	}
	
	/**
	 * Stop the engine, called by {@link TorchGame#quit()}.
	 */
	public final void stop()
	{
		if(running)
		{
			LoggerInternal.log("Stopping", this);
			
			running = false;
		}
	}
	
	/**
	 * The main loop of the engine.
	 */
	private final void mainLoop()
	{
		double unprocessedTime = 0.0;
		
		while(running)
		{
			boolean shouldRender = false;
			
			TimeInternal.update();
			unprocessedTime += TimeInternal.getPassedTime();
			
			// Load the new scene if the user scheduled a scene load via SceneManager.load
			SceneInternal.loadScene();
			
			// Construct the frame queue
			// Might have to be moved to updateCycle()
			SceneInternal.constructFrameQueue();
			
			while(unprocessedTime > TimeInternal.getFrameTime())
			{
				if(WindowInternal.isCloseRequested())
				{
					stop();
				}
				
				shouldRender = true;
				unprocessedTime -= TimeInternal.getFrameTime();
				
				updateCycle();
			}
			
			if(shouldRender)
			{
				renderCycle();
				
				WindowInternal.update();
				TimeInternal.updateFrameCount();
			}
			else
			{
				try
				{
					Thread.sleep(1);
				}
				catch(InterruptedException e)
				{
					Logger.logException(e, this);
				}
			}
		}
		
		destroy();
	}
	
	/**
	 * Run a single update cycle.
	 */
	private final void updateCycle()
	{
		SceneInternal.runUpdateCycle();
		
		// Update input
		KeyboardInternal.update();
		MouseInternal.update();
		CursorInternal.update();
		JoystickInternal.update();
	}
	
	/**
	 * Run a single render cycle.
	 */
	private final void renderCycle()
	{
		SceneInternal.runRenderCycle();
	}
	
	/**
	 * Destroy all engine systems.
	 */
	private final void destroy()
	{
		WindowInternal.destroy();
	}
	
	/**
	 * Log information about LWJGL.
	 */
	private final void logLWJGLInfo()
	{
		LoggerInternal.log("Version: " + Version.getVersion(), "LWJGL");
	}
}
