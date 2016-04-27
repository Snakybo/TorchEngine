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

package rendering;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.snakybo.torch.TorchGame;
import com.snakybo.torch.camera.Camera;
import com.snakybo.torch.camera.CameraClearFlags;
import com.snakybo.torch.mesh.Material;
import com.snakybo.torch.mesh.Mesh;
import com.snakybo.torch.mesh.MeshRenderer;
import com.snakybo.torch.object.Component;
import com.snakybo.torch.object.GameObject;
import com.snakybo.torch.renderer.Skybox;
import com.snakybo.torch.texture.Texture;
import com.snakybo.torch.window.Window;

/**
 * @author Snakybo
 * @since 1.0
 */
public class RendererTest extends TorchGame
{
	public RendererTest()
	{
		super("Renderer Test");
	}
	
	@Override
	protected void onCreate()
	{
		GameObject camera = new GameObject("Camera");
		camera.addComponent(new Camera(new Matrix4f().perspective((float)Math.toRadians(90f), Window.getAspectRatio(), 0.01f, 1000), CameraClearFlags.SolidColor));
		camera.addComponent(new Component()
		{
			@Override
			protected void update()
			{
				getTransform().rotate(new Vector3f(0, 1, 0), 0.003f);
			}
		});
		
		Material material = new Material("./src/test/resources/shaders/test.glsl");
		material.setTexture("diffuse", new Texture("./src/test/resources/grassblock.png"));
		
		GameObject box = new GameObject();
		box.addComponent(new Mesh("./src/test/resources/cube.obj"));
		box.addComponent(new MeshRenderer(material));
		box.addComponent(new Component()
		{
			@Override
			protected void update()
			{
				getTransform().rotate(new Vector3f(0, 1, 0), 0.01f);
			}
		});
		box.getTransform().getLocalScale().set(0.25f);
		box.getTransform().getPosition().set(0, 0, -1f);
		box.getTransform().rotate(new Vector3f(0, 1, 0), 45f);
		
		GameObject skybox = new GameObject();
		skybox.addComponent(new Skybox(new Texture("./src/test/resources/skybox.png")));
	}
	
	public static void main(String[] args)
	{
		new RendererTest();
	}
}
