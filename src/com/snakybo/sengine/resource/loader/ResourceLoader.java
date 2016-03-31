package com.snakybo.sengine.resource.loader;

public abstract class ResourceLoader
{
	/**
	 * Begin importing a resource, make sure to call {@link #endImport()} when you're done importing.
	 * @param resource - The resource to load
	 * @param objects - Any additional parameters
	 */
	public abstract void beginImport(String resource, Object... objects);
	
	/**
	 * Stop the import of a resource, this will clean up any used resources
	 */
	public abstract void endImport();
	
	/**
	 * Check if the specified {@code resource} is valid for importation
	 * @param resource - The resource to import
	 * @return Whether or not the resource can be imported
	 */
	public abstract boolean canImport(String resource);
}
