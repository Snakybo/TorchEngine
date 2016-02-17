package com.snakybo.sengine.asset;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.snakybo.sengine.io.File;
import com.snakybo.sengine.reflection.FieldUtilities;

/**
 * @author Kevin
 * @since Feb 13, 2016
 */
public final class AssetParser
{
	private AssetParser()
	{
		throw new AssertionError();
	}
	
	public static void setFields(Asset asset, Map<String, String> fields)
	{
		for(Map.Entry<String, String> target : fields.entrySet())
		{
			Field field = FieldUtilities.getAccessibleField(asset, target.getKey());
			
			if(field != null)
			{
				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				
				FieldUtilities.setAccessibleFieldValue(asset, field, target.getValue());
				
				field.setAccessible(accessible);
			}
		}
	}
	
	public static Map<String, String> getFields(Asset asset)
	{
		Map<String, String> result = new HashMap<String, String>();
		List<Field> fields = FieldUtilities.getAccessibleFields(asset);
		
		for(Field field : fields)
		{
			String name = field.getName();
			Object value = FieldUtilities.getValue(asset, field);
			
			result.put(name, value == null ? "" : value.toString());
		}
		
		return result;
	}
	
	public static Map<String, String> getFields(String file)
	{
		Map<String, String> fields = new HashMap<String, String>();
		List<String> lines = File.readLines(file);
		
		// Get all field data
		int fieldsStart = lines.indexOf("fields:");		
		for(int i = fieldsStart; i < lines.size(); i++)
		{
			String line = lines.get(i);
			
			if(!line.startsWith("    "))
			{
				continue;
			}
			
			String[] fieldData = line.split(":");
			fields.put(fieldData[0].trim(), fieldData[1].trim());
		}
		
		return fields;
	}
}
