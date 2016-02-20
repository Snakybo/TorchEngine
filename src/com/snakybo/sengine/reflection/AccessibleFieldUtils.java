package com.snakybo.sengine.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import com.snakybo.sengine.annotations.AccessibleField;
import com.snakybo.sengine.debug.Logger;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class AccessibleFieldUtils
{
	private AccessibleFieldUtils()
	{
		throw new AssertionError();
	}
	
	/**
	 * Set the value of an {@link AccessibleField}
	 * @param object - The object of the field
	 * @param field - The field
	 * @param value - The value of the field as string
	 */
	public static void set(Object object, Field field, String value)
	{
		Class<?> t = field.getType();
		
		try
		{
			if(t.equals(int.class))
			{
				field.setInt(object, Integer.parseInt(value));
			}
			else if(t.equals(boolean.class))
			{
				field.setBoolean(object, Boolean.parseBoolean(value));
			}
			else if(t.equals(String.class))
			{
				field.set(object, value);
			}
			else if(t.equals(float.class))
			{
				field.setFloat(object, Float.parseFloat(value));
			}
			else if(t.equals(short.class))
			{
				field.setShort(object, Short.parseShort(value));
			}
			else if(t.equals(long.class))
			{
				field.setLong(object, Long.parseLong(value));
			}
			else if(t.equals(char.class))
			{
				field.setChar(object, value.charAt(0));
			}
			else if(t.equals(byte.class))
			{
				field.setByte(object, Byte.parseByte(value));
			}
			else if(t.equals(double.class))
			{
				field.setDouble(object, Double.parseDouble(value));
			}
		}
		catch(IllegalArgumentException | IllegalAccessException e)
		{
			Logger.logException(e, "AccessibleFieldUtils");
		}
	}
	
	/**
	 * Get all {@link AccessibleField}s from an object
	 * @param object - The object
	 * @return All accessible fields
	 */
	public static List<Field> getAll(Object object)
	{
		List<Field> result = new ArrayList<Field>();
		
		for(Field field : object.getClass().getDeclaredFields())
		{
			if(!isAccessible(field))
			{
				continue;
			}
			
			result.add(field);
		}
		
		return result;
	}
	
	/**
	 * Get an {@link AccessibleField} with the specified {@code name}
	 * @param object - The object of the field
	 * @param name - The name of the field
	 * @return The field, or null if no field was found with the specified name
	 */
	public static Field get(Object object, String name)
	{
		try
		{
			Field result = object.getClass().getDeclaredField(name);
			
			if(isAccessible(result))
			{
				return result;
			}
		}
		catch(NoSuchFieldException | SecurityException e)
		{
			Logger.logException(e, "FieldUtilities");
		}
		
		return null;
	}
	
	/**
	 * Get the value of an {@link AccessibleField}
	 * @param object - The object of the field
	 * @param field - The field to get the value of
	 * @return The value of the field as a string
	 */
	public static Object getValue(Object object, Field field)
	{
		Object result = null;
		
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		
		try
		{
			result = field.get(object);			
		}
		catch(IllegalArgumentException | IllegalAccessException e)
		{
			Logger.logException(e, "AccessibleFieldUtils");
		}
		
		field.setAccessible(accessible);
		return result;
	}
	
	/**
	 * Checks if a field is accessible 
	 * @param field - The field to check
	 * @return Whether or not the field is accessible
	 */
	public static boolean isAccessible(Field field)
	{
		int modifiers = field.getModifiers();
		
		// A field cannot be static
		if(Modifier.isStatic(modifiers))
		{
			return false;
		}
		
		// A field must have the AccessibleField annotation or be public
		if(!field.isAnnotationPresent(AccessibleField.class) && !Modifier.isPublic(modifiers))
		{
			return false;
		}
		
		// A field must have a certain type
		if(!isTypeValid(field.getType()))
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * Check if the type of an accessible field is valid
	 * @param type - The type of the field
	 * @return Whether or not the type of the field is valid
	 */
	private static boolean isTypeValid(Class<?> type)
	{
		final Class<?>[] allowed = new Class<?>[]
		{ 
			int.class, boolean.class,
			String.class, float.class,
			short.class, long.class,
			char.class, byte.class,
			double.class
		};
		
		for(Class<?> t : allowed)
		{
			if(type.equals(t))
			{
				return true;
			}
		}
		
		return false;
	}
}
