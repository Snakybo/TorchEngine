package com.snakybo.sengine.asset;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class AssetDatabase
{
	private static Map<String, Integer> useCount = new HashMap<String, Integer>();
	private static Map<String, Asset> assets = new HashMap<String, Asset>();
	
	private static Set<Object> sources = new HashSet<Object>();
	
	private AssetDatabase()
	{
		throw new AssertionError();
	}
	
	/**
	 * Register a new asset, doing this will add the {@code asset} to the database
	 * @param assetName - The name of the asset
	 * @param asset - The asset to register
	 * @param source - The source of the registration
	 * @return The {@code asset}
	 */
	public static Asset register(String assetName, Asset asset, Object source)
	{
		if(!useCount.containsKey(assetName))
		{
			useCount.put(assetName, 0);
			assets.put(assetName, asset);
		}
		
		return link(assetName, source);
	}
	
	/**
	 * Link an object to an {@link Asset}, this will increase the usage-counter by 1
	 * @param assetName - The name of the asset
	 * @param source - The object to link to the asset
	 * @return The {@link Asset} registered with the same {@code assetName}
	 */
	public static Asset link(String assetName, Object source)
	{
		if(useCount.containsKey(assetName))
		{
			int usages = useCount.get(assetName);
			useCount.put(assetName, usages + 1);			
			sources.add(source);
			
			return assets.get(assetName);
		}
		
		return null;
	}
	
	/**
	 * Unlink an object from an {@link Asset}, this wil decrease the usage-counter by 1.
	 * If the usage counter becomes 0 as a result of this operation, it will automatically destroy the asset
	 * @param assetName - The name of the asset
	 * @param source - The object to unlink
	 */
	public static void unlink(String assetName, Object source)
	{
		if(sources.contains(source) && useCount.containsKey(assetName))
		{
			int usages = useCount.get(assetName);
			useCount.put(assetName, usages - 1);
			sources.remove(source);
			
			// Delete the asset if neccecary
			if(usages - 1 <= 0)
			{
				Asset asset = assets.get(assetName);
				
				useCount.remove(assetName);
				assets.remove(assetName);
				
				asset.destroy();
			}
		}
	}
	
	/**
	 * Check whether or not an {@link Asset} has already been registered
	 * @param assetName - The name of the {@link Asset}
	 * @return Whether or not the {@link Asset} has been registered
	 */
	public static boolean hasAsset(String assetName)
	{
		if(assets.containsKey(assetName))
		{
			return true;
		}
		
		return false;
	}
}
