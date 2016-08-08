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

package com.snakybo.torch.object;

import com.snakybo.torch.Engine;
import com.snakybo.torch.Game;
import com.snakybo.torch.monitor.DisplayMode;
import com.snakybo.torch.monitor.Monitor;
import com.snakybo.torch.window.Window;
import com.snakybo.torch.window.WindowMode;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Kevin Krol
 * @since 1.0
 */
public class ComponentTest
{
	private int called = 0;
	
	private class CallbackComponent extends Component
	{
		@Override
		protected void start()
		{
			addComponent(new StopComponent());
			removeComponent(this);

			called++;
		}
		
		@Override
		protected void update()
		{
			called++;
		}
		
		@Override
		protected void postUpdate()
		{
			called++;
		}

		@Override
		protected void destroy()
		{
			called++;
		}
	}
	
	private class StopComponent extends Component
	{
		@Override
		protected void start()
		{
			Game.quit();
		}
	}
	
	@Test
	public void testCallbacks()
	{
		Engine.initialize();

		Window.create(new DisplayMode(Monitor.getPrimaryMonitor(), 1280, 720), WindowMode.Windowed);
		Window.setVSyncEnabled(false);
		
		GameObject go = new GameObject();
		go.addComponent(new CallbackComponent());
		
		Game.setName("JUnit Test");
		Game.start();
	}
	
	@After
	public void confirmCallbacks()
	{
		assertEquals(4, called);
	}
}
