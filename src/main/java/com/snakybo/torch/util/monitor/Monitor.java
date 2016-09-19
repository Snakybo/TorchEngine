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

package com.snakybo.torch.util.monitor;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWVidMode;

import java.nio.IntBuffer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.glfwGetMonitorName;
import static org.lwjgl.glfw.GLFW.glfwGetMonitorPhysicalSize;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetVideoModes;

/**
 * <p>
 * Container class for all available monitors.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public final class Monitor
{
	static List<Monitor> monitors;
	
	private Set<DisplayMode> displayModes;
	
	private long monitorId;
	
	Monitor(long monitorId)
	{
		this.monitorId = monitorId;
		
		GLFWVidMode.Buffer modes = glfwGetVideoModes(monitorId);
		displayModes = new HashSet<>();
		
		for(int i = 0; i < modes.limit(); i++)
		{
			GLFWVidMode vm = modes.get();
			DisplayMode wm = new DisplayMode(
					this,
					vm.width(),
					vm.height(),
					vm.redBits(),
					vm.refreshRate());
			
			displayModes.add(wm);
		}
	}
	
	@Override
	public String toString()
	{
		return getName();
	}
	
	/**
	 * <p>
	 * Is this the primary monitor?
	 * </p>
	 *
	 * @return <code>true</code> if this is the primary monitor.
	 */
	public final boolean isPrimaryMonitor()
	{
		return monitorId == glfwGetPrimaryMonitor();
	}
	
	/**
	 * <p>
	 * Get all available window modes.
	 * </p>
	 *
	 * @return All available window modes.
	 */
	public final DisplayMode[] getAllDisplayModes()
	{
		return displayModes.toArray(new DisplayMode[displayModes.size()]);
	}
	
	/**
	 * <p>
	 * Get the native window mode of the monitor.
	 * </p>
	 *
	 * @return The native window mode.
	 */
	public final DisplayMode getNativeDisplayMode()
	{
		GLFWVidMode nvm = glfwGetVideoMode(monitorId);
		
		for(DisplayMode mode : displayModes)
		{
			if(mode.getWidth() == nvm.width()
					&& mode.getHeight() == nvm.height()
					&& mode.getBitsPerPixel() == nvm.redBits()
					&& mode.getFrequency() == nvm.refreshRate())
			{
				return mode;
			}
		}
		
		return null;
	}
	
	/**
	 * <p>
	 * Get the physical size of the monitor.
	 * </p>
	 *
	 * @return The physical size.
	 */
	public final Vector2f getPhysicalSize()
	{
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		
		glfwGetMonitorPhysicalSize(monitorId, width, height);
		
		return new Vector2f(width.get(), height.get());
	}
	
	/**
	 * <p>
	 * Get the name of the monitor.
	 * </p>
	 *
	 * @return The name.
	 */
	public final String getName()
	{
		return glfwGetMonitorName(monitorId);
	}
	
	/**
	 * <p>
	 * Get the native ID of the monitor.
	 * </p>
	 *
	 * @return The native ID.
	 */
	public final long getNativeId()
	{
		return monitorId;
	}
	
	/**
	 * <p>
	 * Get the system's primary monitor.
	 * </p>
	 *
	 * @return The primary monitor.
	 */
	public static Monitor getPrimaryMonitor()
	{
		for(Monitor monitor : monitors)
		{
			if(monitor.isPrimaryMonitor())
			{
				return monitor;
			}
		}
		
		return null;
	}
	
	/**
	 * <p>
	 * Get all monitors.
	 * </p>
	 *
	 * @return All monitors.
	 */
	public static Monitor[] getMonitors()
	{
		return monitors.toArray(new Monitor[monitors.size()]);
	}
}
