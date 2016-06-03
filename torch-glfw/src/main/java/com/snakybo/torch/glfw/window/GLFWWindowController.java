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

package com.snakybo.torch.glfw.window;

import static org.lwjgl.glfw.GLFW.GLFW_CONNECTED;
import static org.lwjgl.glfw.GLFW.GLFW_DISCONNECTED;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetMonitorCallback;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;

import org.lwjgl.glfw.GLFWMonitorCallback;

import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.window.IWindow;
import com.snakybo.torch.window.IWindowController;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class GLFWWindowController implements IWindowController
{
	private class MonitorCallback extends GLFWMonitorCallback
	{
		@Override
		public void invoke(long monitorId, int event)
		{
			if(event == GLFW_CONNECTED)
			{
				for(GLFWMonitor monitor : GLFWMonitor.monitors)
				{
					if(monitor.getNativeId() == monitorId)
					{
						throw new RuntimeException("Unable to add monitor, another monitor with ID " + monitorId + " is already connected.");
					}
					
					Logger.log("Monitor added");
					GLFWMonitor.monitors.add(new GLFWMonitor(monitorId));
				}
			}
			else if(event == GLFW_DISCONNECTED)
			{
				GLFWMonitor target = null;
				
				for(GLFWMonitor monitor : GLFWMonitor.monitors)
				{
					if(monitor.getNativeId() == monitorId)
					{
						target = monitor;
						break;
					}
				}
				
				if(target == null)
				{
					throw new RuntimeException("Unable to remove monitor, no monitor with ID " + monitorId + " is present.");
				}
				Logger.log("Monitor removed");
				GLFWMonitor.monitors.remove(target);
			}
		}		
	}
	
	private GLFWWindow window;
	
	public GLFWWindowController()
	{
		window = new GLFWWindow();
	}
	
	@Override
	public void create()
	{
		glfwSetMonitorCallback(new MonitorCallback());
	}
	
	@Override
	public final void update()
	{
		glfwSwapBuffers(window.getNativeId());
		glfwPollEvents();
	}

	@Override
	public final void destroy()
	{
		window.destroy();
		
		glfwSetMonitorCallback(null).free();
	}
	
	@Override
	public IWindow get()
	{
		return window;
	}
}
