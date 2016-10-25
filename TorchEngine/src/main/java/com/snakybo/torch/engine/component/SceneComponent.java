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

package com.snakybo.torch.engine.component;

import com.snakybo.torch.engine.core.Actor;
import com.snakybo.torch.engine.core.Transform;
import com.snakybo.torch.engine.math.Quaternion;
import com.snakybo.torch.engine.math.Vector3;
import com.snakybo.torch.engine.serialization.SerializedField;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Snakybo
 * @since 1.0
 */
public class SceneComponent extends ActorComponent
{
	@SerializedField(category = "Transform")
	public final Transform transform;
	
	private Set<SceneComponent> children;
	
	private SceneComponent parent;
	
	public SceneComponent(Actor actor)
	{
		super(actor);
		
		children = new HashSet<>();
		transform = new Transform();
	}
	
	protected void onChildAdded(SceneComponent child)
	{
	}
	
	protected void onChildRemoved(SceneComponent child)
	{
	}
	
	public final boolean isRootComponent()
	{
		return actor.getRootComponent() == this;
	}
	
	public final void setPosition(Vector3 position)
	{
		transform.position.set(position);
	}
	
	public final void setRotation(Quaternion rotation)
	{
		transform.rotation.set(rotation);
	}
	
	public final void setScale(Vector3 scale)
	{
		transform.scale.set(scale);
	}
	
	public final void setParent(SceneComponent component)
	{
		if(component == null || component.actor == actor)
		{
			if(parent != null)
			{
				parent.children.remove(this);
				parent.onChildRemoved(this);
			}
			
			parent = component;
			
			if(parent != null)
			{
				parent.children.add(this);
				parent.onChildAdded(this);
			}
		}
	}
	
	public final SceneComponent getParent()
	{
		return parent;
	}
	
	public final SceneComponent[] getChildrenRecursive()
	{
		Set<SceneComponent> result = getChildrenRecursiveImpl();
		return result.toArray(new SceneComponent[result.size()]);
	}
	
	public final SceneComponent[] getChildren()
	{
		return children.toArray(new SceneComponent[children.size()]);
	}
	
	private Set<SceneComponent> getChildrenRecursiveImpl()
	{
		Set<SceneComponent> result = new HashSet<>();
		result.add(this);
		
		for(SceneComponent child : children)
		{
			result.addAll(child.getChildrenRecursiveImpl());
		}
		
		return result;
	}
}
