package com.snakybo.sengine.asset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.snakybo.sengine.debug.Logger;
import com.snakybo.sengine.io.File;
import com.snakybo.sengine.object.Object;

/**
 * @author Kevin
 * @since Feb 13, 2016
 */
public abstract class Asset extends Object
{
	public Asset()
	{
		super("Asset");
		setName(getClass().getSimpleName());
	}
	
	/**
	 * Load an asset from a file
	 * @param asset - The type of the asset
	 * @param path - The path to the asset data
	 * @return An asset with the data read from the specified path
	 */
	public static <T extends Asset> T load(Class<T> asset, String path)
	{
		Logger.log("Loading asset at: " + path, "Asset");
				
		if(!File.exists(path + ".asset"))
		{
			Logger.logError("No asset found at: " + path, "Asset");
			return null;
		}
		
		try
		{
			Asset result = asset.newInstance();			
			AssetParser.setFields(result, AssetParser.getFields(path + ".asset"));
			
			return asset.cast(result);
		}
		catch(InstantiationException | IllegalAccessException e)
		{
			Logger.logException(e, "AssetParser");
		}
		
		return null;
	}
	
	/**
	 * Write an asset's contents to the disk
	 * @param asset - The asset to save
	 * @param path - The path to save the asset to
	 */
	public static void save(Asset asset, String path)
	{
		Logger.log("Saving asset: " + asset + " at: " + path, "Asset");
		
		List<String> lines = new ArrayList<String>();
		
		// Add fields
		lines.add("fields:");		
		Map<String, String> fields = AssetParser.getFields(asset);
		for(Map.Entry<String, String> field : fields.entrySet())
		{
			lines.add("    " + field.getKey() + ": " + field.getValue());
		}
		
		// Write the data
		File.writeLines(path + ".asset", lines);
	}
}
