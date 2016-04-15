// The MIT License(MIT)
//
// Copyright(c) 2016 Kevin Krol
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

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
	 * @param object The object of the field
	 * @param field The field
	 * @param value The value of the field
	 */
	public static void set(Object object, Field field, boolean value)
	{
		Class<?> t = field.getType();		
		if(t.equals(boolean.class))
		{
			try
			{
				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				field.setBoolean(object, value);
				field.setAccessible(accessible);
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				Logger.logException(e, "AccessibleFieldUtils");
			}
		}
		else
		{
			Logger.logError("boolean is not equal to " + t, "AcessibleFieldUtils");
		}
	}
	
	/**
	 * Set the value of an {@link AccessibleField}
	 * @param object The object of the field
	 * @param field The field
	 * @param value The value of the field
	 */
	public static void set(Object object, Field field, byte value)
	{
		Class<?> t = field.getType();		
		if(t.equals(byte.class))
		{
			try
			{
				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				field.setByte(object, value);
				field.setAccessible(accessible);
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				Logger.logException(e, "AccessibleFieldUtils");
			}
		}
		else
		{
			Logger.logError("byte is not equal to " + t, "AcessibleFieldUtils");
		}
	}
	
	/**
	 * Set the value of an {@link AccessibleField}
	 * @param object The object of the field
	 * @param field The field
	 * @param value The value of the field
	 */
	public static void set(Object object, Field field, short value)
	{
		Class<?> t = field.getType();		
		if(t.equals(short.class))
		{
			try
			{
				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				field.setShort(object, value);
				field.setAccessible(accessible);
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				Logger.logException(e, "AccessibleFieldUtils");
			}
		}
		else
		{
			Logger.logError("short is not equal to " + t, "AcessibleFieldUtils");
		}
	}
	
	/**
	 * Set the value of an {@link AccessibleField}
	 * @param object The object of the field
	 * @param field The field
	 * @param value The value of the field
	 */
	public static void set(Object object, Field field, int value)
	{
		Class<?> t = field.getType();		
		if(t.equals(int.class))
		{
			try
			{
				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				field.setInt(object, value);
				field.setAccessible(accessible);
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				Logger.logException(e, "AccessibleFieldUtils");
			}
		}
		else
		{
			Logger.logError("int is not equal to " + t, "AcessibleFieldUtils");
		}
	}
	
	/**
	 * Set the value of an {@link AccessibleField}
	 * @param object The object of the field
	 * @param field The field
	 * @param value The value of the field
	 */
	public static void set(Object object, Field field, float value)
	{
		Class<?> t = field.getType();		
		if(t.equals(float.class))
		{
			try
			{
				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				field.setFloat(object, value);
				field.setAccessible(accessible);
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				Logger.logException(e, "AccessibleFieldUtils");
			}
		}
		else
		{
			Logger.logError("float is not equal to " + t, "AcessibleFieldUtils");
		}
	}
	
	/**
	 * Set the value of an {@link AccessibleField}
	 * @param object The object of the field
	 * @param field The field
	 * @param value The value of the field
	 */
	public static void set(Object object, Field field, double value)
	{
		Class<?> t = field.getType();		
		if(t.equals(double.class))
		{
			try
			{
				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				field.setDouble(object, value);
				field.setAccessible(accessible);
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				Logger.logException(e, "AccessibleFieldUtils");
			}
		}
		else
		{
			Logger.logError("double is not equal to " + t, "AcessibleFieldUtils");
		}
	}
	
	/**
	 * Set the value of an {@link AccessibleField}
	 * @param object The object of the field
	 * @param field The field
	 * @param value The value of the field
	 */
	public static void set(Object object, Field field, long value)
	{
		Class<?> t = field.getType();		
		if(t.equals(long.class))
		{
			try
			{
				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				field.setLong(object, value);
				field.setAccessible(accessible);
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				Logger.logException(e, "AccessibleFieldUtils");
			}
		}
		else
		{
			Logger.logError("long is not equal to " + t, "AcessibleFieldUtils");
		}
	}
	
	/**
	 * Set the value of an {@link AccessibleField}
	 * @param object The object of the field
	 * @param field The field
	 * @param value The value of the field
	 */
	public static void set(Object object, Field field, char value)
	{
		Class<?> t = field.getType();		
		if(t.equals(char.class))
		{
			try
			{
				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				field.setChar(object, value);
				field.setAccessible(accessible);
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				Logger.logException(e, "AccessibleFieldUtils");
			}
		}
		else
		{
			Logger.logError("char is not equal to " + t, "AcessibleFieldUtils");
		}
	}
	
	/**
	 * Set the value of an {@link AccessibleField}
	 * @param object The object of the field
	 * @param field The field
	 * @param value The value of the field
	 */
	public static void set(Object object, Field field, String value)
	{
		Class<?> t = field.getType();
		if(t.equals(String.class) || value == null)
		{
			try
			{
				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				field.set(object, value);
				field.setAccessible(accessible);
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				Logger.logException(e, "AccessibleFieldUtils");
			}
		}
		else
		{
			Logger.logError(value.getClass() + " is not equal to " + t, "AcessibleFieldUtils");
		}
	}
	
	/**
	 * Set the value of an {@link AccessibleField}
	 * @param object The object of the field
	 * @param field The field
	 * @param value The value of the field
	 */
	public static void set(Object object, Field field, Object value)
	{
		Class<?> t = field.getType();
		if(t.equals(value.getClass()) || value == null)
		{
			try
			{
				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				field.set(object, value);
				field.setAccessible(accessible);
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				Logger.logException(e, "AccessibleFieldUtils");
			}
		}
		else
		{
			Logger.logError(value.getClass() + " is not equal to " + t, "AcessibleFieldUtils");
		}
	}
	
	/**
	 * Get all {@link AccessibleField}s from an object
	 * @param object The object
	 * @return All accessible fields
	 */
	public static Field[] getAll(Object object)
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
		
		return result.toArray(new Field[result.size()]);
	}
	
	/**
	 * Get an {@link AccessibleField} with the specified {@code name}
	 * @param object The object of the field
	 * @param name The name of the field
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
	 * @param object The object of the field
	 * @param field The field to get the value of
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
	 * @param field The field to check
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
		
		return true;
	}
}
