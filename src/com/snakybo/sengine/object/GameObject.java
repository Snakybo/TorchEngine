package com.snakybo.sengine.object;

import java.util.HashSet;
import java.util.Set;

import com.snakybo.sengine.scene.SceneUtilities;

/**
 * @author Kevin
 * @since Feb 13, 2016
 */
public final class GameObject extends Object
{	
	Set<Component> components;
	Set<Component> frameQueue;
	
	public GameObject(String name)
	{
		super(name);
		
		// Register the GameObject
		SceneUtilities.register(this);
		
		components = new HashSet<Component>();
		frameQueue = new HashSet<Component>();
	}
	
	/**
	 * Add a component to the GameObject
	 * @param component - The component to add
	 */
	public void addComponent(Component component)
	{
		components.add(component);
	}
	
	/**
	 * Attempt to retrieve a component of {@code type} from the GameObject
	 * @param type - The type of component to retrieve
	 * @return The component, or {@code null} if no component of {@code type} was found
	 */
	public <T extends Component> T getComponent(Class<T> type)
	{
		for(Component component : components)
		{
			if(component.getClass().equals(type))
			{
				return type.cast(component);
			}
		}
		
		return null;
	}
	
	/**
	 * Attempt to retrieve all components of {@code type} from the GameObject
	 * @param type - The type of component to retrieve
	 * @return The components, it's empty if no components of {@code type} are found
	 */
	public <T extends Component> Iterable<T> getComponents(Class<T> type)
	{
		Set<T> result = new HashSet<T>();
		
		for(Component component : components)
		{
			if(component.getClass().equals(type))
			{
				result.add(type.cast(component));
			}
		}
		
		return result;
	}
}
