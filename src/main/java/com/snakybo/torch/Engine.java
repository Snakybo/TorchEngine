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

import com.snakybo.torch.component.Camera;
import com.snakybo.torch.cursor.CursorController;
import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.debug.LoggerInternal;
import com.snakybo.torch.glfw.GLFW;
import com.snakybo.torch.input.joystick.JoystickController;
import com.snakybo.torch.input.keyboard.KeyboardController;
import com.snakybo.torch.input.mouse.MouseController;
import com.snakybo.torch.monitor.MonitorController;
import com.snakybo.torch.object.GameObjectNotifier;
import com.snakybo.torch.queue.QueueProcessor;
import com.snakybo.torch.scene.Scene;
import com.snakybo.torch.time.Time;
import com.snakybo.torch.time.TimeInternal;
import com.snakybo.torch.window.Window;
import org.lwjgl.Version;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

/**
 * <p>
 * Main class of the engine, the core update and render loop is handled by this,
 * as well as creating and destroying dependencies.
 * </p>
 *
 * <p>
 * Aside from {@link #initialize()}, you do not need any further interaction with this class.
 * Instead use {@link Game} for starting, pausing and stopping the game.
 * </p>
 *
 * @see Game
 *
 * @author Snakybo
 * @since 1.0
 */
public final class Engine
{
	static boolean running;
	
	private static boolean initialized;
	
	/**
	 * Initialize the engine, this must be the first interaction your game has with the engine.
	 */
	public static void initialize()
	{
		if(!initialized)
		{
			LoggerInternal.log("Initializing");
			LoggerInternal.log("Engine version: " + EngineInfo.VERSION_STRING);
			LoggerInternal.log("LWJGL Version: " + Version.getVersion());
			
			GLFW.create();
			MonitorController.create();
			
			initialized = true;
		}
	}
	
	/**
	 * Start the engine, called by {@link Game#start()}.
	 */
	static void start()
	{
		if(!running && initialized)
		{
			LoggerInternal.log("Starting");
			
			running = true;
			
			mainLoop();
		}
		
		if(!initialized)
		{
			Logger.logError("You must initialize the engine by calling Engine.initialize() before starting the game");
		}
	}
	
	/**
	 * Stop the engine, called by {@link Game#quit()}.
	 */
	static void stop()
	{
		if(running)
		{
			LoggerInternal.log("Stopping");
			
			running = false;
		}
	}
	
	private static void mainLoop()
	{
		while(running)
		{
			frame();
		}

		QueueProcessor.process();
		destroy();
	}

	private static void frame()
	{
		if(Window.isCloseRequested())
		{
			stop();
		}

		TimeInternal.updateDeltaTime();

		update();
		updateInput();
		
		render();
	}
	
	private static void updateInput()
	{
		KeyboardController.update();
		MouseController.update();
		JoystickController.update();
		CursorController.update();
	}
	
	private static void update()
	{
		Scene.getCurrentScene().getAllGameObjects().forEach(GameObjectNotifier::update);
		Scene.getCurrentScene().getAllGameObjects().forEach(GameObjectNotifier::postUpdate);
		
		QueueProcessor.process();
	}
	
	private static void render()
	{
		Camera.getCameras().forEach(Camera::render);
		
		Window.update();
		
		if(!Window.isVSyncEnabled())
		{
			sync();
		}
		
		TimeInternal.updateFrameCount();
	}
	
	private static void sync()
	{
		double last = Time.getLastTime();
		double now = glfwGetTime();
		
		float targetTime = 1f / Game.getTargetFrameRate();
		
		while(now - last < targetTime)
		{
			Thread.yield();
			
			try
			{
				Thread.sleep(1);
			}
			catch(InterruptedException e)
			{
				Logger.logError(e.toString(), e);
			}
			
			now = glfwGetTime();
		}
	}
	
	private static void destroy()
	{
		LoggerInternal.log("Cleaning up");
		
		Scene.getCurrentScene().getAllGameObjects().forEach(GameObjectNotifier::destroy);

		JoystickController.destroy();
		MonitorController.destroy();
		
		LoggerInternal.log("Terminating");
		System.exit(0);
	}
}
