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

package com.snakybo.torch.util;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A collection of reflection utilities.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public final class ReflectionUtil
{
	/**
	 * <p>
	 * Get the type of all provided {@code objects}.
	 * </p>
	 *
	 * <p>
	 * This method will automatically sanitize primitive types by calling {@link #sanitizePrimitives(Class)}.
	 * </p>
	 *
	 * @param objects The objects to get the type of.
	 * @return The type of all objects.
	 */
	public static Class<?>[] getObjectTypes(Iterable<Object> objects)
	{
		List<Object> objectsArrayList  = new ArrayList<>();
		
		for(Object obj : objects)
		{
			objectsArrayList.add(obj);
		}
		
		Object[] objectsArray = objectsArrayList.toArray(new Object[objectsArrayList.size()]);
		return getObjectTypes(objectsArray);
	}
	
	/**
	 * <p>
	 * Get the type of all provided {@code objects}.
	 * </p>
	 *
	 * <p>
	 * This method will automatically sanitize primitive types by calling {@link #sanitizePrimitives(Class)}.
	 * </p>
	 *
	 * @param objects The objects to get the type of.
	 * @return The type of all objects.
	 */
	public static Class<?>[] getObjectTypes(Object... objects)
	{
		List<Class<?>> result = new ArrayList<>();
		
		for(Object obj : objects)
		{
			Class<?> clazz = obj.getClass();
			result.add(sanitizePrimitives(clazz));
		}
		
		return result.toArray(new Class<?>[result.size()]);
	}
	
	/**
	 * <p>
	 * This method checks if the input {@code clazz} can be converted to a primitive type.
	 * </p>
	 *
	 * <p>
	 * For example, if the input {@code clazz} is an {@code Integer}, it will convert it to an {@code int}.
	 * </p>
	 *
	 * @param clazz The class to sanitize.
	 * @return The primitive version of the input class if it exists.
	 */
	public static Class<?> sanitizePrimitives(Class<?> clazz)
	{
		if(clazz == Byte.class)
		{
			return byte.class;
		}
		
		if(clazz == Short.class)
		{
			return short.class;
		}
		
		if(clazz == Integer.class)
		{
			return int.class;
		}
		
		if(clazz == Long.class)
		{
			return long.class;
		}
		
		if(clazz == Float.class)
		{
			return float.class;
		}
		
		if(clazz == Double.class)
		{
			return double.class;
		}
		
		if(clazz == Character.class)
		{
			return char.class;
		}
		
		if(clazz == Boolean.class)
		{
			return boolean.class;
		}
		
		return clazz;
	}
}
