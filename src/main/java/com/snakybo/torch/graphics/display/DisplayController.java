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

package com.snakybo.torch.graphics.display;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWMonitorCallback;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.GLFW_CONNECTED;
import static org.lwjgl.glfw.GLFW.GLFW_DISCONNECTED;
import static org.lwjgl.glfw.GLFW.glfwGetMonitors;
import static org.lwjgl.glfw.GLFW.glfwSetMonitorCallback;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class DisplayController
{
	private static class DisplayCallback extends GLFWMonitorCallback
	{
		@Override
		public void invoke(long displayId, int event)
		{
			if(event == GLFW_CONNECTED)
			{
				for(Display display : Display.displays)
				{
					if(display.getNativeId() == displayId)
					{
						throw new RuntimeException("Unable to add display, another display with ID " + displayId + " is already connected.");
					}
					
					Display.displays.add(new Display(displayId));
				}
			}
			else if(event == GLFW_DISCONNECTED)
			{
				Display target = null;
				
				for(Display display : Display.displays)
				{
					if(display.getNativeId() == displayId)
					{
						target = display;
						break;
					}
				}
				
				if(target == null)
				{
					throw new RuntimeException("Unable to remove display, no display with ID " + displayId + " is present.");
				}
				
				Display.displays.remove(target);
			}
		}
	}
	
	public static void create()
	{
		PointerBuffer displayPointers = glfwGetMonitors();
		Display.displays = new ArrayList<>();
		
		for(int i = 0; i < displayPointers.limit(); i++)
		{
			long id = displayPointers.get(i);
			Display.displays.add(new Display(id));
		}
		
		glfwSetMonitorCallback(new DisplayCallback());
	}
	
	public static void destroy()
	{
		glfwSetMonitorCallback(null).free();
	}
}
