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

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class SerializedObject implements Iterable<SerializedProperty>
{
	private final Object targetObject;
	
	private Map<String, SerializedProperty> properties;
	
	public SerializedObject(Object targetObject)
	{
		this.targetObject = targetObject;
		
		properties = new HashMap<>();
		
		Field[] fields = SerializationUtil.findSerializable(targetObject.getClass());
		
		for(Field field : fields)
		{
			// We don't support arrays currently
			if(field.getType().isArray())
			{
				continue;
			}
			
			SerializedProperty property = new SerializedProperty(this, targetObject, field);
			properties.put(field.getName(), property);
		}
	}
	
	@Override
	public final String toString()
	{
		return targetObject.toString();
	}
	
	@Override
	public final int hashCode()
	{
		return 13 ^ targetObject.hashCode();
	}
	
	@Override
	public final boolean equals(Object o)
	{
		if(o == this)
		{
			return true;
		}
		
		if(o == null || o.getClass() != getClass())
		{
			return false;
		}
		
		SerializedObject so = (SerializedObject)o;
		return so.targetObject.equals(targetObject);
	}
	
	@Override
	public final Iterator<SerializedProperty> iterator()
	{
		return properties.values().iterator();
	}
	
	public final SerializedProperty findProperty(String name)
	{
		if(name == null)
		{
			return null;
		}
		
		return properties.get(name);
	}
	
	public final void update()
	{
		properties.values().forEach(SerializedProperty::update);
	}
	
	public final void applyChanges()
	{
		properties.values().forEach(SerializedProperty::applyChanges);
	}
	
	public final Object getTargetObject()
	{
		return targetObject;
	}
}
