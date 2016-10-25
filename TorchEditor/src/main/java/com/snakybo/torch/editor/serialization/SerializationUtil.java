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

package com.snakybo.torch.editor.serialization;

import com.snakybo.torch.engine.core.ObjectBase;
import com.snakybo.torch.engine.serialization.SerializedField;
import com.snakybo.torch.engine.serialization.SerializedType;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class SerializationUtil
{
	private SerializationUtil()
	{
		throw new AssertionError();
	}
	
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
	
	public static boolean isSerializable(Class<?> clazz)
	{
		return clazz.isAnnotationPresent(SerializedType.class) || ObjectBase.class.isAssignableFrom(clazz);
	}
	
	public static Field[] findSerializable(Class<?> clazz)
	{
		Set<Field> fields = new HashSet<>();
		
		while(clazz.getSuperclass() != null)
		{
			Field[] declaredFields = clazz.getDeclaredFields();
			
			for(Field field : declaredFields)
			{
				if(isValidField(field))
				{
					fields.add(field);
				}
			}
			
			clazz = clazz.getSuperclass();
		}
		
		return fields.toArray(new Field[fields.size()]);
	}
	
	private static boolean isValidField(Field field)
	{
		int modifiers = field.getModifiers();
		
		// Cannot be static
		if(Modifier.isStatic(modifiers))
		{
			return false;
		}
		
		// Cannot be final or abstract
		if(Modifier.isFinal(modifiers) || Modifier.isAbstract(modifiers))
		{
			return false;
		}
		
		// Must have the SerializedField annotation or be public
		if(!field.isAnnotationPresent(SerializedField.class) && !Modifier.isPublic(modifiers))
		{
			return false;
		}
		
		return true;
	}
}
