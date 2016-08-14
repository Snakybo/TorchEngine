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

package opengl;

import com.snakybo.torch.Engine;
import com.snakybo.torch.Game;
import com.snakybo.torch.asset.AssetLoader;
import com.snakybo.torch.component.mesh.MeshRenderer;
import com.snakybo.torch.material.Material;
import com.snakybo.torch.mesh.Mesh;
import com.snakybo.torch.monitor.DisplayMode;
import com.snakybo.torch.monitor.Monitor;
import com.snakybo.torch.object.GameObject;
import com.snakybo.torch.scene.Scene;
import com.snakybo.torch.window.Window;
import com.snakybo.torch.window.WindowMode;
import com.snakybo.torch.scene.SceneLoader;
import org.joml.Vector3f;

/**
 * @author Kevin
 * @since 1.0
 */
public class OpenGLTest
{
	private static void createScene()
	{
		Scene scene = SceneLoader.load("test.scene");
		scene.makeCurrent();
		
		Material mat = AssetLoader.load(Material.class, "test.mtl");
		GameObject go = new GameObject();
		go.addComponent(new MeshRenderer(AssetLoader.load(Mesh.class, "cube.obj"), mat));
		go.getTransform().setLocalScale(new Vector3f(0.25f));
		go.getTransform().setLocalPosition(new Vector3f(2, 0, 0));
		go.getTransform().rotate(new Vector3f(0, 1, 0), 45f);
		
		//Logger.log(scene.getGameObjectByName("wtf").getTransform().toString());
		
//		Material material = new Material("unlit.glsl");
//		material.setTexture("diffuse", Texture2D.load("grassblock.png"));

//		GameObject box = new GameObject();
//		box.addComponent(new MeshRenderer(Mesh.load("cube.obj"), material));
////		box.addComponent(new Component()
////		{
////			@Override
////			protected void update()
////			{
////				getTransform().rotate(new Vector3f(0, 1, 0), 1f * Time.getDeltaTime());
////			}
////		});
//		box.getTransform().setLocalScale(new Vector3f(0.25f));
//		box.getTransform().setLocalPosition(new Vector3f(0, 0, -1f));
//		box.getTransform().rotate(new Vector3f(0, 1, 0), 45f);
	}
	
	public static void main(String[] args)
	{
		Engine.initialize();
		
		Window.create(new DisplayMode(Monitor.getPrimaryMonitor(), 1280, 720), WindowMode.Windowed);
		//Window.create(Monitor.getPrimaryMonitor().getNativeDisplayMode(), WindowMode.Fullscreen);
		Window.setVSyncEnabled(true);
		
		createScene();
		
		Game.setName("OpenGL Test");
		Game.start();
	}
}
