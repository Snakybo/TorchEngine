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

import static org.lwjgl.glfw.GLFW.glfwGetMonitorName;
import static org.lwjgl.glfw.GLFW.glfwGetMonitorPhysicalSize;
import static org.lwjgl.glfw.GLFW.glfwGetMonitors;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetVideoModes;

import java.nio.IntBuffer;
import java.util.HashSet;
import java.util.Set;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWVidMode.Buffer;

import com.snakybo.torch.window.WindowMode;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class GLFWMonitor
{	
	private static Set<GLFWMonitor> monitors;
	
	private Set<GLFWWindowMode> windowModes;
	
	private long monitorId;
	
	static
	{
		PointerBuffer monitorPointers = glfwGetMonitors();
		monitors = new HashSet<GLFWMonitor>();
		
		for(int i = 0; i < monitorPointers.limit(); i++)
		{
			long id = monitorPointers.get(i);			
			monitors.add(new GLFWMonitor(id));
		}
	}
	
	private GLFWMonitor(long monitorId)
	{
		this.monitorId = monitorId;
		
		Buffer modes = glfwGetVideoModes(monitorId);
		windowModes = new HashSet<GLFWWindowMode>();
		
		for(int i = 0; i < modes.limit(); i++)
		{
			GLFWVidMode vm = modes.get();
			GLFWWindowMode wm =new GLFWWindowMode(
					monitorId,
					vm.width(),
					vm.height(),
					vm.redBits(),
					vm.refreshRate());
			
			windowModes.add(wm);
		}
	}
	
	@Override
	public String toString()
	{
		return getName();
	}
	
	public final boolean isPrimaryMonitor()
	{
		return monitorId == glfwGetPrimaryMonitor();
	}
	
	public final WindowMode[] getWindowModes()
	{
		return windowModes.toArray(new WindowMode[windowModes.size()]);
	}	
	
	public final GLFWWindowMode getNativeWindowMode()
	{
		GLFWVidMode nvm = glfwGetVideoMode(monitorId);
		
		for(GLFWWindowMode wm : windowModes)
		{
			if(wm.getWidth() == nvm.width()
					&& wm.getHeight() == nvm.height()
					&& wm.getBitsPerPixel() == nvm.redBits()
					&& wm.getFrequency() == nvm.refreshRate())
			{
				return wm;
			}
		}
		
		return null;
	}
	
	public final Vector2f getPhysicalSize()
	{
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		
		glfwGetMonitorPhysicalSize(monitorId, width, height);
		
		return new Vector2f(width.get(), height.get());
	}

	public final String getName()
	{
		return glfwGetMonitorName(monitorId);
	}
	
	public final long getNativeId()
	{
		return monitorId;
	}
	
	public static GLFWMonitor getPrimaryMonitor()
	{
		for(GLFWMonitor monitor : monitors)
		{
			if(monitor.isPrimaryMonitor())
			{
				return monitor;
			}
		}
		
		return null;
	}
	
	public static GLFWMonitor[] getMonitors()
	{
		return monitors.toArray(new GLFWMonitor[monitors.size()]);
	}
}