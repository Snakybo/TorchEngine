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

package com.snakybo.torch.window;

import static org.lwjgl.glfw.GLFW.glfwGetMonitorName;
import static org.lwjgl.glfw.GLFW.glfwGetMonitorPhysicalSize;
import static org.lwjgl.glfw.GLFW.glfwGetMonitors;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoModes;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWVidMode.Buffer;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Monitor
{
	long id;
	
	private List<DisplayMode> displayModes;
	
	private Monitor(long id)
	{
		this.id = id;
		
		Buffer modes = glfwGetVideoModes(id);
		displayModes = new ArrayList<DisplayMode>();
		
		for(int i = 0; i < modes.limit(); i++)
		{
			displayModes.add(new DisplayMode(id, modes.get(i)));
		}
	}
	
	@Override
	public String toString()
	{
		return getName();
	}
	
	/**
	 * @return Whether or not this is the primary {@link Monitor}
	 */
	public final boolean isPrimaryMonitor()
	{
		return id == glfwGetPrimaryMonitor();
	}
	
	/**
	 * @return All {@link DisplayMode}s this {@link Monitor} supports
	 */
	public final DisplayMode[] getDisplayModes()
	{
		return displayModes.toArray(new DisplayMode[displayModes.size()]);
	}	
	
	/**
	 * @return The native {@link DisplayMode} of this {@link Monitor}
	 */
	public final DisplayMode getNativeDisplayMode()
	{
		for(DisplayMode dm : displayModes)
		{
			if(dm.isNativeDisplayMode())
			{
				return dm;
			}
		}
		
		return null;
	}
	
	/**
	 * @return The name of this {@link Monitor}
	 */
	public final String getName()
	{
		return glfwGetMonitorName(id);
	}
	
	/**
	 * @return The physical size of this {@link Monitor}
	 */
	public final Vector2f getPhysicalSize()
	{
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		
		glfwGetMonitorPhysicalSize(id, width, height);
		
		return new Vector2f(width.get(), height.get());
	}
	
	/**
	 * @return The primary {@link Monitor}
	 */
	public static Monitor getPrimaryMonitor()
	{
		return new Monitor(glfwGetPrimaryMonitor());
	}
	
	/**
	 * @return All detected {@link Monitor}s
	 */
	public static Monitor[] getMonitors()
	{
		PointerBuffer monitors = glfwGetMonitors();
		List<Monitor> result = new ArrayList<Monitor>();
		
		for(int i = 0; i < monitors.limit(); i++)
		{
			long id = monitors.get(i);			
			result.add(new Monitor(id));
		}
		
		return result.toArray(new Monitor[result.size()]);
	}
}
