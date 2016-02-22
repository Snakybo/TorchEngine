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
public class WindowController extends Component
{
	@Override
	protected void start()
	{
		Logger.log("Press F for exclusive fullscreen", this);
		Logger.log("Press G for borderless fullscreen", this);
		Logger.log("Press H for windowed", this);
		
		Logger.log("Press V to log all monitors", this);
		Logger.log("Press B to log all available display modes on the primary monitor", this);
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
