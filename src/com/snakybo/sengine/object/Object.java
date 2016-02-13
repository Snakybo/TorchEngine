package com.snakybo.sengine.object;

import com.snakybo.sengine.debug.Logger;

/**
 * @author Kevin
 * @since Feb 13, 2016
 */
public class Object
{
	private String name;
	
	public Object(String name)
	{
		setName(name);
	}
	
	/**
	 * @see #getName()
	 */
	@Override
	public String toString()
	{
		return getName();
	}
	
	/**
	 * Set the name of the object
	 * @param name - The new name
	 */
	public void setName(String name)
	{
		if(name == null || name.isEmpty())
		{
			Logger.logError("Invalid name for object", this);
			return;
		}
		
		this.name = name;
	}
	
	/**
	 * @return The name of the object
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Destroy an object, can be used on any {@link GameObject} or {@link Component}
	 * @param object The object to destroy
	 * @see GameObject
	 * @see Component
	 */
	public static void destroy(Object object)
	{
		if(object instanceof GameObject)
		{
			GameObjectUtilities.destroy((GameObject)object);
		}
		else if(object instanceof Component)
		{
			Component c = (Component)object;			
			GameObjectUtilities.removeComponent(c.gameObject, c);
		}
	}
}