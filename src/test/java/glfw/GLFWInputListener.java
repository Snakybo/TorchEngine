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

package glfw;

import com.snakybo.torch.input.keyboard.Key;
import com.snakybo.torch.input.keyboard.Keyboard;
import com.snakybo.torch.input.mouse.Mouse;
import com.snakybo.torch.input.mouse.MouseButton;
import com.snakybo.torch.object.Component;
import com.snakybo.torch.util.debug.Logger;

/**
 * @author Snakybo
 * @since 1.0
 */
public class GLFWInputListener extends Component
{
	protected void onUpdate()
	{
		if(Keyboard.onDown(Key.W))
		{
			Logger.log("Pressed W");
		}
		
		if(Keyboard.onUp(Key.W))
		{
			Logger.log("Released W");
		}
		
		if(Keyboard.onDown(Key.A))
		{
			Logger.log("Pressed A");
		}
		
		if(Keyboard.onUp(Key.A))
		{
			Logger.log("Released A");
		}
		
		if(Keyboard.onDown(Key.S))
		{
			Logger.log("Pressed S");
		}
		
		if(Keyboard.onUp(Key.S))
		{
			Logger.log("Released S");
		}
		
		if(Keyboard.onDown(Key.D))
		{
			Logger.log("Pressed D");
		}
		
		if(Keyboard.onUp(Key.D))
		{
			Logger.log("Released D");
		}
		
		if(Mouse.onDown(MouseButton.LEFT))
		{
			Logger.log("Pressed left mouse button");
		}
		
		if(Mouse.onUp(MouseButton.LEFT))
		{
			Logger.log("Released left mouse button");
		}
		
		if(Mouse.getScrollDelta().x != 0 || Mouse.getScrollDelta().y != 0)
		{
			Logger.log("Mouse scroll delta: " + Mouse.getScrollDelta());
		}
	}
}
