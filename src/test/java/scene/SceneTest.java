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

package scene;

import com.snakybo.sengine.Game;
import com.snakybo.sengine.debug.Logger;
import com.snakybo.sengine.input.keyboad.Key;
import com.snakybo.sengine.input.keyboad.Keyboard;
import com.snakybo.sengine.object.Component;
import com.snakybo.sengine.object.GameObject;
import com.snakybo.sengine.scene.Scene;
import com.snakybo.sengine.scene.SceneManager;

/**
 * @author Snakybo
 * @since 1.0
 */
public class SceneTest extends Game
{
	public SceneTest()
	{
		super("Scene Test");
	}
	
	@Override
	protected void onCreate()
	{
		GameObject go = new GameObject();
		
		go.addComponent(new Component()
		{
			@Override
			protected void start()
			{
				Logger.log("Loaded scene: " + SceneManager.getCurrentScene().getName());
			}
			
			@Override
			protected void update()
			{
				if(Keyboard.onKeyDown(Key.SPACE))
				{
					SceneManager.load(new Scene("Scene 2"));
				}
			}
		});
		
		go.addComponent(new Component()
		{
			@Override
			protected void update()
			{
				Logger.log("Update!");
			}
		});
	}
}
