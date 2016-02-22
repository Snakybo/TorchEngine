package window;

import com.snakybo.sengine.Game;
import com.snakybo.sengine.object.GameObject;

/**
 * @author Snakybo
 * @since 1.0
 */
public class WindowTest extends Game
{
	public WindowTest()
	{
		super("Window Test");
	}

	@Override
	public void createScene()
	{
		GameObject controller = new GameObject("Window Controller");
		controller.addComponent(new WindowController());
	}
	
	public static void main(String[] args)
	{
		new WindowTest();
	}
}
