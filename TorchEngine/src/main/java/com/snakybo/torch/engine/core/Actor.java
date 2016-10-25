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

package com.snakybo.torch.engine.core;

import com.snakybo.torch.engine.component.ActorComponent;
import com.snakybo.torch.engine.component.SceneComponent;
import com.snakybo.torch.engine.math.Quaternion;
import com.snakybo.torch.engine.math.Vector3;
import com.snakybo.torch.engine.serialization.SerializedField;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Snakybo
 * @since 1.0
 */
public class Actor extends ObjectBase
{
	@SerializedField(category = "Actor")
	private ArrayList<String> tags = new ArrayList<>();
	
	private Set<ActorComponent> components;
	private Set<Actor> children;
	
	private ActorComponentUpdater actorComponentUpdater;
	private SceneComponent rootComponent;
	private Actor parent;
	private World world;
	
	public Actor(String name, World world)
	{
		super(name);
		
		this.world = world;
		
		actorComponentUpdater = new ActorComponentUpdater();
		
		components = new HashSet<>();
		children = new HashSet<>();
	}
	
	public void onSpawned()
	{
	}
	
	public void onDestroyed()
	{
	}
	
	public void start()
	{
	}
	
	public void stop()
	{
	}
	
	public void update()
	{
	}
	
	public final void destroy()
	{
		world.destroyActor(this);
		
		for(Actor child : children)
		{
			child.destroy();
		}
		
		for(ActorComponent component : components)
		{
			component.destroy();
		}
	}
	
	public final <T extends ActorComponent> T addComponent(Class<T> clazz)
	{
		try
		{
			Constructor<T> constructor = clazz.getConstructor(Actor.class);
			T component = constructor.newInstance(this);
			
			components.add(component);
			
			if(rootComponent == null && SceneComponent.class.isAssignableFrom(clazz))
			{
				rootComponent = (SceneComponent)component;
			}
			
			actorComponentUpdater.register(component);
			return component;
		}
		catch(NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public final void addTag(String tag)
	{
		tags.add(tag);
	}
	
	public final void removeTag(String tag)
	{
		tags.remove(tag);
	}
	
	public final void removeAllTags()
	{
		tags.clear();
	}
	
	public final boolean hasTag(String tag)
	{
		return tags.contains(tag);
	}
	
	public final void setParent(Actor actor)
	{
		if(parent != null)
		{
			parent.children.remove(this);
		}
		
		parent = actor;
		
		if(parent != null)
		{
			parent.children.add(this);
		}
	}
	
	public final void setRootComponent(SceneComponent component)
	{
		if(components.contains(component))
		{
			SceneComponent oldRoot = rootComponent;
			rootComponent = component;
			
			rootComponent.setParent(null);
			oldRoot.setParent(rootComponent);
		}
	}
	
	public final void setPosition(Vector3 position)
	{
		if(rootComponent == null)
		{
			addComponent(SceneComponent.class);
		}
		
		rootComponent.setPosition(position);
	}
	
	public final void setRotation(Quaternion rotation)
	{
		if(rootComponent == null)
		{
			addComponent(SceneComponent.class);
		}
		
		rootComponent.setRotation(rotation);
	}
	
	public final void setScale(Vector3 scale)
	{
		if(rootComponent == null)
		{
			addComponent(SceneComponent.class);
		}
		
		rootComponent.setScale(scale);
	}
	
	public final String[] getTags()
	{
		return tags.toArray(new String[tags.size()]);
	}
	
	public final ActorComponent[] getComponents()
	{
		return components.toArray(new ActorComponent[components.size()]);
	}
	
	public final Actor[] getChildren()
	{
		return children.toArray(new Actor[children.size()]);
	}
	
	public final <T extends ActorComponent> T[] getComponents(Class<T> clazz)
	{
		Set<ActorComponent> result = new HashSet<>();
		
		for(ActorComponent component : components)
		{
			if(component.getClass().equals(clazz))
			{
				result.add(component);
			}
		}
		
		return (T[])result.toArray(new Object[result.size()]);
	}
	
	public final <T extends ActorComponent> T getComponent(Class<T> clazz)
	{
		for(ActorComponent component : components)
		{
			if(component.getClass().equals(clazz))
			{
				return clazz.cast(component);
			}
		}
		
		return null;
	}
	
	public final SceneComponent getRootComponent()
	{
		if(rootComponent == null)
		{
			addComponent(SceneComponent.class);
		}
		
		return rootComponent;
	}
	
	public final Actor getParent()
	{
		return parent;
	}
	
	public final Vector3 getPosition()
	{
		return getRootComponent().transform.position;
	}
	
	public final Quaternion getRotation()
	{
		return getRootComponent().transform.rotation;
	}
	
	public final Vector3 getScale()
	{
		return getRootComponent().transform.scale;
	}
	
	public final ActorComponentUpdater getActorComponentUpdater()
	{
		return actorComponentUpdater;
	}
	
	public final World getWorld()
	{
		return world;
	}
}
