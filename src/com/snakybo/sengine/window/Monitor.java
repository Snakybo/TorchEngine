package com.snakybo.sengine.window;

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
	public static List<Monitor> getMonitors()
	{
		PointerBuffer monitors = glfwGetMonitors();
		List<Monitor> result = new ArrayList<Monitor>();
		
		for(int i = 0; i < monitors.limit(); i++)
		{
			long id = monitors.get(i);			
			result.add(new Monitor(id));
		}
		
		return result;
	}
}
