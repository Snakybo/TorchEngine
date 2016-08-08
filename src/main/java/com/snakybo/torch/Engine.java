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

import com.snakybo.torch.component.camera.Camera;
import com.snakybo.torch.cursor.CursorController;
import com.snakybo.torch.debug.Debug;
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
import static org.lwjgl.glfw.GLFW.glfwWaitEvents;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Engine
{
	static boolean running;
	
	private static boolean initialized;
	
	public static void initialize()
	{
		if(!initialized)
		{
			LoggerInternal.log("Initializing", "Engine");
			LoggerInternal.log("Engine version: " + EngineInfo.VERSION_STRING, "Engine");
			
			if(Debug.LOG_LIBRARY_INFO)
			{
				LoggerInternal.log("Version: " + Version.getVersion(), "LWJGL");
			}
			
			GLFW.create();
			MonitorController.create();
			
			// Create an empty scene
			new Scene();
			
			initialized = true;
		}
	}
	
	/**
	 * Start the engine, called by {@link Game#start()}.
	 */
	static void start()
	{
		if(!running)
		{
			LoggerInternal.log("Starting", "Engine");
			
			running = true;
			
			mainLoop();
		}
	}
	
	/**
	 * Stop the engine, called by {@link Game#quit()}.
	 */
	static void stop()
	{
		if(running)
		{
			LoggerInternal.log("Stopping", "Engine");
			
			running = false;
		}
	}
	
	/**
	 * The main loop of the engine.
	 */
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
			running = false;
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
				e.printStackTrace();
			}
			
			now = glfwGetTime();
		}
	}
	
	/**
	 * Destroy all engine systems.
	 */
	private static void destroy()
	{
		Scene.getCurrentScene().getAllGameObjects().forEach(GameObjectNotifier::destroy);

		JoystickController.destroy();
		MonitorController.destroy();
	}
}
