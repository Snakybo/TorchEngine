package com.snakybo.sengine.model;

/**
 * @author Kevin
 * @since Feb 15, 2016
 */
public interface IModelLoader
{
	/**
	 * Convert the loaded data to a model format the engine can understand
	 */
	Model toEngineModel();
}
