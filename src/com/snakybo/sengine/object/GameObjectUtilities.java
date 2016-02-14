package com.snakybo.sengine.object;

import com.snakybo.sengine.scene.SceneUtilities;

/**
 * @author Kevin
 * @since Feb 13, 2016
 */
public final class GameObjectUtilities
{
	private GameObjectUtilities()
	{
		throw new AssertionError();
	}
	
	/**
	 * Construct the frame queue of a {@link GameObject},
	 * the frame queue are the components which can receive callbacks this frame
	 * @param gameObject - The {@link GameObject} to construct the frame queue for
	 */
	public static void constructFrameQueue(GameObject gameObject)
	{
		gameObject.frameQueue.clear();
		gameObject.frameQueue.addAll(gameObject.components);
	}
	
	/**
	 * Call {@link Component#start()} on all components in the frame queue of a {@link GameObject} if neccecary
	 * @param gameObject - The target {@link GameObject}
	 * @see Component#start()
	 */
	public static void start(GameObject gameObject)
	{
		// Call start() on components if neccecary
		for(Component component : gameObject.frameQueue)
		{
			if(component.gameObject == null)
			{
				// Set the name of the component to include the GameObject's name
				component.setName(gameObject.getName() + ":" + component.getName());
				
				component.gameObject = gameObject;
				component.start();
			}
		}
	}
	
	/**
	 * Call {@link Component#update()} on all components in the frame queue of a {@link GameObject}
	 * @param gameObject - The target {@link GameObject}
	 * @see Component#update()
	 */
	public static void update(GameObject gameObject)
	{		
		// Call update() on components
		for(Component component : gameObject.frameQueue)
		{
			component.update();
		}
	}
	
	/**
	 * Call {@link Component#postUpdate()} on all components in the frame queue of a {@link GameObject}
	 * @param gameObject - The target {@link GameObject}
	 * @see Component#postUpdate()
	 */
	public static void postUpdate(GameObject gameObject)
	{
		// Call postUpdate() on components
		for(Component component : gameObject.frameQueue)
		{
			component.postUpdate();
		}
	}
	
	/**
	 * Call {@link Component#preRenderObject()} on all components in the frame queue of a {@link GameObject}
	 * @param gameObject - The target {@link GameObject}
	 * @see Component#preRenderObject()
	 */
	public static void preRenderObject(GameObject gameObject)
	{
		// Call preRender() on components
		for(Component component : gameObject.frameQueue)
		{
			component.preRenderObject();
		}
	}
	
	/**
	 * Call {@link Component#renderObject()} on all components in the frame queue of a {@link GameObject}
	 * @param gameObject - The target {@link GameObject}
	 * @see Component#renderObject()
	 */
	public static void renderObject(GameObject gameObject)
	{
		// Call render() on components
		for(Component component : gameObject.frameQueue)
		{
			component.renderObject();
		}
	}
	
	/**
	 * Call {@link Component#postRenderObject()} on all components in the frame queue of a {@link GameObject}
	 * @param gameObject - The target {@link GameObject}
	 * @see Component#postRenderObject()
	 */
	public static void postRenderObject(GameObject gameObject)
	{
		// Call postRender() on components
		for(Component component : gameObject.frameQueue)
		{
			component.postRenderObject();
		}
	}
	
	/**
	 * Call {@link Component#destroy()} on all components in the frame queue of a {@link GameObject},
	 * and call {@link SceneUtilities#unregister(GameObject)}
	 * @param gameObject - The target {@link GameObject}
	 * @see Component#destroy()
	 */
	public static void destroy(GameObject gameObject)
	{
		// Let the components know they're being destroyed
		for(Component component : gameObject.frameQueue)
		{
			removeComponent(gameObject, component);
		}
		
		// Unregister the GameObject
		SceneUtilities.unregister(gameObject);
	}
	
	/**
	 * Remove a single {@link Component} from a {@link GameObject}, it will also call {@link Component#destroy()} on the component
	 * @param gameObject - The parent {@link GameObject}
	 * @param component - The {@link Component} to remove
	 * @see Component#destroy()
	 */
	public static void removeComponent(GameObject gameObject, Component component)
	{
		if(gameObject.components.remove(component))
		{
			// Call destroy() on the component, but only if it's in the frame queue
			if(gameObject.frameQueue.contains(component))
			{
				component.destroy();
			}
			
			// Remove the GameObject as parent of the component
			component.gameObject = null;
			
			// Restore the original name of the component
			String[] name = component.getName().split(":");
			component.setName(name[1]);
		}
	}
}
