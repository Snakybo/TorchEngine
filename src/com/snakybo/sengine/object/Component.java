package com.snakybo.sengine.object;

/**
 * @author Kevin
 * @since Feb 13, 2016
 */
public abstract class Component extends Object
{	
	GameObject gameObject;
	
	public Component()
	{
		super("Component");
		setName(getClass().getSimpleName());
	}
	
	/**
	 * Called once when the object was first created, just before {@link #update()} is called
	 */
	protected void start()
	{
	}
	
	/**
	 * Called every frame
	 */
	protected void update()
	{
	}
	
	/**
	 * Called every frame, after {@link #update()} has been called on every component in the scene
	 */
	protected void postUpdate()
	{
	}
	
	/**
	 * Called when a camera starts rendering the object
	 */
	protected void preRenderObject()
	{
	}
	
	/**
	 * Called when a camera renders the object
	 */
	protected void renderObject()
	{
	}
	
	/**
	 * Called when a camera has finished rendering the object
	 */
	protected void postRenderObject()
	{
	}
	
	/**
	 * Called when the component or parent {@link GameObject} has been destroyed by {@link #destroy(Object)}
	 */
	protected void destroy()
	{
	}
	
	/**
	 * @return The parent {@link GameObject}
	 */
	public GameObject getGameObject()
	{
		return gameObject;
	}
}
