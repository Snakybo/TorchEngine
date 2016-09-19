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

import com.snakybo.torch.component.Camera;
import com.snakybo.torch.component.MeshRenderer;
import com.snakybo.torch.graphics.color.Color;
import com.snakybo.torch.graphics.gizmo.Gizmos;
import com.snakybo.torch.graphics.texture.TextureFilterMode;
import com.snakybo.torch.input.keyboard.Key;
import com.snakybo.torch.input.keyboard.Keyboard;
import com.snakybo.torch.object.Component;
import com.snakybo.torch.object.Transform;
import com.snakybo.torch.scene.Scene;
import com.snakybo.torch.util.debug.Logger;
import org.joml.Vector3f;

/**
 * @author Snakybo
 * @since 1.0
 */
public class LightController extends Component
{
	private MeshRenderer meshRenderer;
	
	protected void onUpdate()
	{
		if(meshRenderer == null)
		{
			meshRenderer = Scene.getGameObjectByName("Grassblock").getComponent(MeshRenderer.class);
			
			if(meshRenderer == null)
			{
				Logger.logError("Unable to retrieve MeshRenderer");
			}
		}
		
		if(meshRenderer != null)
		{
			meshRenderer.getMaterial().setVector3f("directionalLight.direction", getTransform().forward());
			
			Transform camera = Camera.getInstance().getTransform();
			
			meshRenderer.getMaterial().setVector3f("spotLight.position", camera.getPosition());
			meshRenderer.getMaterial().setVector3f("spotLight.direction", camera.forward());
			meshRenderer.getMaterial().setFloat("spotLight.cutOff", (float)Math.toRadians(10.0));
			meshRenderer.getMaterial().setFloat("spotLight.outerCutOff", (float)Math.toRadians(15.0));
		}
		
		if(Keyboard.onDown(Key.F))
		{
			meshRenderer.getMaterial().getTexture("mainTexture").setFilterMode(TextureFilterMode.Point);
		}
		else if(Keyboard.onDown(Key.G))
		{
			meshRenderer.getMaterial().getTexture("mainTexture").setFilterMode(TextureFilterMode.Bilinear);
		}
		else if(Keyboard.onDown(Key.H))
		{
			meshRenderer.getMaterial().getTexture("mainTexture").setFilterMode(TextureFilterMode.Trilinear);
		}
	}
	
	protected void onRenderGizmos()
	{
		Gizmos.setColor(new Color(1, 0, 0));
		Gizmos.drawSphere(new Vector3f(2f, -1.2f, 3.0f), 0.1f);
		
		Gizmos.setColor(new Color(0, 1, 0));
		Gizmos.drawSphere(new Vector3f(0, 5, 0), 0.1f);
		
		Gizmos.setColor(new Color(0, 0, 1));
		Gizmos.drawSphere(new Vector3f(2, 0, -1), 0.1f);
		
		Gizmos.setColor(new Color(1, 1, 0));
		Gizmos.drawSphere(new Vector3f(-2.3f, 0.7f, 2.8f), 0.1f);
	}
}
