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

package com.snakybo.torch.monitor;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWMonitorCallback;

import java.util.HashSet;

import static org.lwjgl.glfw.GLFW.GLFW_CONNECTED;
import static org.lwjgl.glfw.GLFW.GLFW_DISCONNECTED;
import static org.lwjgl.glfw.GLFW.glfwGetMonitors;
import static org.lwjgl.glfw.GLFW.glfwSetMonitorCallback;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class MonitorController
{
	private static class MonitorCallback extends GLFWMonitorCallback
	{
		@Override
		public void invoke(long monitorId, int event)
		{
			if(event == GLFW_CONNECTED)
			{
				for(Monitor monitor : Monitor.monitors)
				{
					if(monitor.getNativeId() == monitorId)
					{
						throw new RuntimeException("Unable to add monitor, another monitor with ID " + monitorId + " is already connected.");
					}
					
					Monitor.monitors.add(new Monitor(monitorId));
				}
			}
			else if(event == GLFW_DISCONNECTED)
			{
				Monitor target = null;
				
				for(Monitor monitor : Monitor.monitors)
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
				
				Monitor.monitors.remove(target);
			}
		}
	}
	
	public static void create()
	{
		PointerBuffer monitorPointers = glfwGetMonitors();
		Monitor.monitors = new HashSet<>();
		
		for(int i = 0; i < monitorPointers.limit(); i++)
		{
			long id = monitorPointers.get(i);
			Monitor.monitors.add(new Monitor(id));
		}
		
		glfwSetMonitorCallback(new MonitorCallback());
	}
	
	public static void destroy()
	{
		glfwSetMonitorCallback(null).free();
	}
}
