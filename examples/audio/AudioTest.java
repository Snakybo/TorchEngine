package audio;

import com.snakybo.sengine.Game;
import com.snakybo.sengine.audio.AudioClip;
import com.snakybo.sengine.audio.AudioListener;
import com.snakybo.sengine.audio.AudioPlayer;
import com.snakybo.sengine.object.GameObject;

/**
 * @author Snakybo
 * @since 1.0
 */
public class AudioTest extends Game
{	
	public AudioTest()
	{
		super("Audio Test");
	}
	
	@Override
	public void createScene()
	{
		GameObject listener = new GameObject("Listener");
		listener.addComponent(new AudioListener());
		
		GameObject source = new GameObject("Source");
		source.addComponent(new AudioPlayer(new AudioClip("car.ogg"), true));
		source.addComponent(new AudioPlayerMover());
	}
	
	public static void main(String[] args)
	{
		new AudioTest();
	}
}
