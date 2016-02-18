package com.snakybo.sengine.asset;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.snakybo.sengine.io.File;
import com.snakybo.sengine.reflection.FieldUtils;

/**
 * @author Snakybo
 * @since 1.0
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
			Field field = FieldUtils.getAccessibleField(asset, target.getKey());
			
			if(field != null)
			{
				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				
				FieldUtils.setAccessibleFieldValue(asset, field, target.getValue());
				
				field.setAccessible(accessible);
			}
		}
	}
	
	public static Map<String, String> getFields(Asset asset)
	{
		Map<String, String> result = new HashMap<String, String>();
		List<Field> fields = FieldUtils.getAccessibleFields(asset);
		
		for(Field field : fields)
		{
			String name = field.getName();
			Object value = FieldUtils.getValue(asset, field);
			
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
