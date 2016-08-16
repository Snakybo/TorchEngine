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

import com.snakybo.torch.component.MeshRenderer;
import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.input.keyboard.Key;
import com.snakybo.torch.input.keyboard.Keyboard;
import com.snakybo.torch.object.Component;
import com.snakybo.torch.scene.Scene;
import com.snakybo.torch.serialized.SerializedField;
import com.snakybo.torch.texture.Texture2D;
import com.snakybo.torch.time.Time;
import org.joml.Vector3f;

/**
 * @author Snakybo
 * @since 1.0
 */
public class LightController extends Component
{
	@SerializedField private Texture2D diffuse;
	@SerializedField private Texture2D specular;
	
	private MeshRenderer meshRenderer;
	
	@Override
	protected void onUpdate()
	{
		if(Keyboard.isDown(Key.W))
		{
			getTransform().translate(new Vector3f(0, 0, 1).mul(Time.getDeltaTime()));
		}
		
		if(Keyboard.isDown(Key.A))
		{
			getTransform().translate(new Vector3f(-1, 0, 0).mul(Time.getDeltaTime()));
		}
		
		if(Keyboard.isDown(Key.S))
		{
			getTransform().translate(new Vector3f(0, 0, -1).mul(Time.getDeltaTime()));
		}
		
		if(Keyboard.isDown(Key.D))
		{
			getTransform().translate(new Vector3f(1, 0, 0).mul(Time.getDeltaTime()));
		}
		
		if(meshRenderer == null)
		{
			meshRenderer = Scene.getCurrentScene().getGameObjectByName("Grassblock").getComponent(MeshRenderer.class);
			
			if(meshRenderer == null)
			{
				Logger.logError("Unable to retrieve MeshRenderer");
			}
			else
			{
				meshRenderer.getMaterial().setTexture("material.diffuse", diffuse);
				meshRenderer.getMaterial().setTexture("material.specular", specular);
			}
		}
		
		if(meshRenderer != null)
		{
			meshRenderer.getMaterial().setVector3f("lightPosition", getTransform().getPosition());
		}
		
		
//		float r = (float)Math.sin(Time.getCurrentTime() * 2.0f);
//		float g = (float)Math.sin(Time.getCurrentTime() * 0.7f);
//		float b = (float)Math.sin(Time.getCurrentTime() * 1.3f);
//
//		Vector3f diffuseColor = new Vector3f(r, g, b).mul(0.5f);
//		Vector3f ambientColor = diffuseColor.mul(0.2f);
//
//		meshRenderer.getMaterial().setVector3f("light.ambient", ambientColor);
//		meshRenderer.getMaterial().setVector3f("light.diffuse", diffuseColor);
	}
}
