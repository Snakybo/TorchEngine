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

package cursor;

import com.snakybo.torch.TorchGame;
import com.snakybo.torch.bitmap.Bitmap;
import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.input.cursor.Cursor;
import com.snakybo.torch.input.mouse.Mouse;
import com.snakybo.torch.input.mouse.MouseButton;
import com.snakybo.torch.object.Component;
import com.snakybo.torch.object.GameObject;

/**
 * @author Snakybo
 * @since 1.0
 */
public class CursorExample extends TorchGame
{
	private static class CursorManager extends Component
	{
		private final int NUM_CURSORS = 5;
		
		private Bitmap custom;
		private int currentCursor = 0;
		
		@Override
		protected void start()
		{
			custom = new Bitmap("examples/cursor/custom.png");
			
			setStandardCursor();
			
			Logger.log("Press left mouse button to cycle between standard cursors");
			Logger.log("Press right mouse button to set a custom cursor");
		}
		
		@Override
		protected void update()
		{
			if(Mouse.onMouseDown(MouseButton.LEFT))
			{
				if(++currentCursor >= NUM_CURSORS)
				{
					currentCursor = 0;
				}
				
				setStandardCursor();
			}
			else if(Mouse.onMouseDown(MouseButton.RIGHT))
			{
				Cursor.setShape(custom);
				currentCursor = 0;
			}
		}
		
		private void setStandardCursor()
		{
			switch(currentCursor)
			{
			case 0:
				Cursor.setShapeArrow();
				break;
			case 1:
				Cursor.setShapeIBeam();
				break;
			case 2:
				Cursor.setShapeCrosshair();
				break;
			case 3:
				Cursor.setShapeHand();
				break;
			case 4:
				Cursor.setShapeHResize();
				break;
			case 5:
				Cursor.setShapeVResize();
				break;
			}
		}
	}

	public CursorExample()
	{
		super("Cursor Example");
	}
	
	@Override
	protected void onCreate() {
		GameObject cursorManager = new GameObject("Cursor Manager");
		cursorManager.addComponent(new CursorManager());
	}
}