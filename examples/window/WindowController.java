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

package window;

import com.snakybo.sengine.debug.Logger;
import com.snakybo.sengine.input.keyboad.Key;
import com.snakybo.sengine.input.keyboad.Keyboard;
import com.snakybo.sengine.object.Component;
import com.snakybo.sengine.window.DisplayMode;
import com.snakybo.sengine.window.Monitor;
import com.snakybo.sengine.window.Window;

/**
 * @author Snakybo
 * @since 1.0
 */
class WindowController extends Component
{
	@Override
	protected void start()
	{
		Logger.log("Press F for exclusive fullscreen");
		Logger.log("Press G for borderless fullscreen");
		Logger.log("Press H for windowed");
		
		Logger.log("Press V to log all monitors");
		Logger.log("Press B to log all available display modes on the primary monitor");
	}
	
	@Override
	protected void update()
	{		
		if(Keyboard.onKeyDown(Key.F))
		{
			Window.setFullscreen(true, false);
		}
		else if(Keyboard.onKeyDown(Key.G))
		{
			Window.setFullscreen(true, true);
		}
		else if(Keyboard.onKeyDown(Key.H))
		{
			Window.setFullscreen(false, false);
		}
		else if(Keyboard.onKeyDown(Key.V))
		{
			Monitor[] monitors = Monitor.getMonitors();
			
			for(Monitor monitor : monitors)
			{
				Logger.log(monitor, this);
			}
		}
		else if(Keyboard.onKeyDown(Key.B))
		{
			DisplayMode[] displayModes = Monitor.getPrimaryMonitor().getDisplayModes();
			
			for(DisplayMode displayMode : displayModes)
			{
				Logger.log(displayMode, this);
			}
		}
	}
}
