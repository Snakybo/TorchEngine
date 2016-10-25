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

import com.snakybo.torch.engine.math.Quaternion;
import com.snakybo.torch.engine.math.Vector3;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Snakybo
 * @since 1.0
 */
public class World
{
	private static World currentWorld;
	
	private ActorRegister actorRegister;
	private ActorUpdater actorUpdater;
	
	private String name;
	
	public World(String name)
	{
		this.name = name;
		
		actorRegister = new ActorRegister();
		actorUpdater = new ActorUpdater(actorRegister);
	}
	
	public final <T extends Actor> T spawnActor(Class<T> clazz)
	{
		String name = clazz.getSimpleName() + "1";
		int nameIndex = 1;
		
		while(getActorByName(name) != null)
		{
			nameIndex++;
			name = clazz.getSimpleName() + nameIndex;
		}
		
		return spawnActor(clazz, name);
	}
	
	public final <T extends Actor> T spawnActor(Class<T> clazz, String name)
	{
		return spawnActor(clazz, name, new Vector3(), new Quaternion(), new Vector3(1));
	}
	
	public final <T extends Actor> T spawnActor(Class<T> clazz, String name, Vector3 position, Quaternion rotation, Vector3 scale)
	{
		try
		{
			Constructor<T> constructor = clazz.getConstructor(String.class, World.class);
			T actor = constructor.newInstance(name, this);
			
			actor.getRootComponent().setPosition(position);
			actor.getRootComponent().setRotation(rotation);
			actor.getRootComponent().setScale(scale);
			
			actorRegister.add(actor);
			
			return actor;
		}
		catch(NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public final boolean destroyActor(Actor actor)
	{
		if(isBeingDestroyed(actor))
		{
			return false;
		}
		
		actorRegister.remove(actor);
		
		return true;
	}
	
	public final Actor[] getActorsByName(String name)
	{
		Set<Actor> result = new HashSet<>();
		
		for(Actor actor : actorRegister.getAll())
		{
			if(actor.getName().equals(name))
			{
				result.add(actor);
			}
		}
		
		return result.toArray(new Actor[result.size()]);
	}
	
	public final Actor getActorByName(String name)
	{
		for(Actor actor : actorRegister.getAll())
		{
			if(actor.getName().equals(name))
			{
				return actor;
			}
		}
		
		return null;
	}
	
	public final boolean isBeingDestroyed(Actor actor)
	{
		return actorRegister.isBeingDestroyed(actor);
	}
	
	public final ActorUpdater getActorUpdater()
	{
		return actorUpdater;
	}
	
	public static void setCurrentWorld(World world)
	{
		currentWorld = world;
	}
	
	public static World getCurrentWorld()
	{
		return currentWorld;
	}
}
