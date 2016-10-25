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

import com.snakybo.torch.engine.debug.Debug;
import com.snakybo.torch.engine.math.Quaternion;
import com.snakybo.torch.engine.math.Vector2;
import com.snakybo.torch.engine.math.Vector3;
import com.snakybo.torch.engine.math.Vector4;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Snakybo
 * @since 1.0
 */
public class SerializedProperty implements Iterable<SerializedProperty>
{
	private interface ISerializedProperty
	{
		void applyChanges();
		
		boolean setNewValue(Object v);
		
		Object getValue();
	}
	
	private class SerializedPropertyObject implements ISerializedProperty
	{
		private final Field field;
		
		public SerializedPropertyObject(Field field)
		{
			this.field = field;
			this.field.setAccessible(true);
		}
		
		@Override
		public final void applyChanges()
		{
			try
			{
				field.set(targetObject, value);
			}
			catch(IllegalAccessException e)
			{
				// Try to set the field's accessibility to true
				field.setAccessible(true);
				
				try
				{
					field.set(targetObject, value);
				}
				catch(IllegalAccessException ex)
				{
					Debug.logException(ex);
				}
			}
		}
		
		@Override
		public boolean setNewValue(Object v)
		{
			Class<?> clazz = SerializationUtil.sanitizePrimitives(v.getClass());
			
			if(getType().isAssignableFrom(clazz))
			{
				value = v;
				return true;
			}
			
			return false;
		}
		
		@Override
		public final Object getValue()
		{
			try
			{
				return field.get(targetObject);
			}
			catch(IllegalAccessException e)
			{
				// Try to set the field's accessibility to true
				field.setAccessible(true);
				
				try
				{
					return field.get(targetObject);
				}
				catch(IllegalAccessException ex)
				{
					Debug.logException(ex);
				}
			}
			
			return null;
		}
	}
	
	private final SerializedObject serializedObject;
	private final ISerializedProperty internalProperty;
	private final Object targetObject;
	
	private Map<String, SerializedProperty> childProperties;
	private Object value;
	
	private boolean changed;
	
	SerializedProperty(SerializedObject serializedObject, Object targetObject, Field field)
	{
		this.serializedObject = serializedObject;
		this.internalProperty = new SerializedPropertyObject(field);
		this.targetObject = targetObject;
		this.childProperties = new HashMap<>();
		
		update();
	}
	
	@Override
	public final String toString()
	{
		return getName();
	}
	
	@Override
	public final int hashCode()
	{
		return 51 ^ targetObject.hashCode();
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o == this)
		{
			return true;
		}
		
		if(o == null || o.getClass() != getClass())
		{
			return false;
		}
		
		SerializedProperty sp = (SerializedProperty)o;
		return sp.targetObject.equals(targetObject);
	}
	
	@Override
	public final Iterator<SerializedProperty> iterator()
	{
		return childProperties.values().iterator();
	}
	
	void update()
	{
		Object newValue = internalProperty.getValue();
		
		if(newValue != value)
		{
			value = newValue;
			changed = false;
			
			findChildProperties();
		}
		else
		{
			childProperties.values().forEach(SerializedProperty::update);
		}
	}
	
	void applyChanges()
	{
		if(changed)
		{
			internalProperty.applyChanges();
		}
	}
	
	public final SerializedProperty findChildProperty(String name)
	{
		if(name == null || !hasChildProperties())
		{
			return null;
		}
		
		return childProperties.get(name);
	}
	
	public final boolean hasChildProperties()
	{
		return childProperties.size() > 0;
	}
	
	private void findChildProperties()
	{
		childProperties.clear();
		
		if(SerializationUtil.isSerializable(getType()))
		{
			Field[] fields = SerializationUtil.findSerializable(getType());
			
			for(Field field : fields)
			{
				SerializedProperty property = new SerializedProperty(serializedObject, value, field);
				childProperties.put(field.getName(), property);
			}
		}
	}
	
	public final void setByteValue(byte b)
	{
		if(!getType().isAssignableFrom(byte.class))
		{
			Debug.logError("SerializedProperty is not a byte");
			return;
		}
		
		setObjectValue(b);
	}
	
	public final void setBooleanValue(boolean b)
	{
		if(!getType().isAssignableFrom(boolean.class))
		{
			Debug.logError("SerializedProperty is not a boolean");
			return;
		}
		
		setObjectValue(b);
	}
	
	public final void setIntValue(int i)
	{
		if(!getType().isAssignableFrom(int.class))
		{
			Debug.logError("SerializedProperty is not an int");
			return;
		}
		
		setObjectValue(i);
	}
	
	public final void setShortValue(short s)
	{
		if(!getType().isAssignableFrom(short.class))
		{
			Debug.logError("SerializedProperty is not a short");
			return;
		}
		
		setObjectValue(s);
	}
	
	public final void setFloatValue(float f)
	{
		if(!getType().isAssignableFrom(float.class))
		{
			Debug.logError("SerializedProperty is not a float");
			return;
		}
		
		setObjectValue(f);
	}
	
	public final void setLongValue(long l)
	{
		if(!getType().isAssignableFrom(long.class))
		{
			Debug.logError("SerializedProperty is not a long");
			return;
		}
		
		setObjectValue(l);
	}
	
	public final void setDoubleValue(double d)
	{
		if(!getType().isAssignableFrom(double.class))
		{
			Debug.logError("SerializedProperty is not a double");
			return;
		}
		
		setObjectValue(d);
	}
	
	public final void setStringValue(String s)
	{
		if(!getType().isAssignableFrom(String.class))
		{
			Debug.logError("SerializedProperty is not a string");
			return;
		}
		
		setObjectValue(s);
	}
	
	public final void setVector2Value(Vector2 v)
	{
		if(!getType().isAssignableFrom(Vector2.class))
		{
			Debug.logError("SerializedProperty is not a vector 2");
			return;
		}
		
		setObjectValue(v);
	}
	
	public final void setVector3Value(Vector3 v)
	{
		if(!getType().isAssignableFrom(Vector3.class))
		{
			Debug.logError("SerializedProperty is not a vector 3");
			return;
		}
		
		setObjectValue(v);
	}
	
	public final void setVector4Value(Vector4 v)
	{
		if(!getType().isAssignableFrom(Vector4.class))
		{
			Debug.logError("SerializedProperty is not a vector 4");
			return;
		}
		
		setObjectValue(v);
	}
	
	public final void getQuaternionValue(Quaternion q)
	{
		if(!getType().isAssignableFrom(Quaternion.class))
		{
			Debug.logError("SerializedProperty is not a quaternion");
			return;
		}
		
		setObjectValue(q);
	}
	
	public final void setObjectReferenceValue(Object object)
	{
		setObjectValue(object);
	}
	
	private void setObjectValue(Object v)
	{
		if(internalProperty.setNewValue(v))
		{
			changed = true;
		}
		else
		{
			Debug.logWarning("Unable to set value of SerializedProperty");
		}
	}
	
	public final byte getByteValue()
	{
		if(getType().isAssignableFrom(byte.class))
		{
			return (byte)value;
		}
		
		throw new IllegalStateException("SerializedProperty is not a byte");
	}
	
	public final boolean getBooleanValue()
	{
		if(getType().isAssignableFrom(boolean.class))
		{
			return (boolean)value;
		}
		
		throw new IllegalStateException("SerializedProperty is not a boolean");
	}
	
	public final int getIntValue()
	{
		if(getType().isAssignableFrom(int.class))
		{
			return (int)value;
		}
		
		throw new IllegalStateException("SerializedProperty is not an int");
	}
	
	public final short getShortValue()
	{
		if(getType().isAssignableFrom(short.class))
		{
			return (short)value;
		}
		
		throw new IllegalStateException("SerializedProperty is not a short");
	}
	
	public final float getFloatValue()
	{
		if(getType().isAssignableFrom(float.class))
		{
			return (float)value;
		}
		
		throw new IllegalStateException("SerializedProperty is not a float");
	}
	
	public final long getLongValue()
	{
		if(getType().isAssignableFrom(long.class))
		{
			return (long)value;
		}
		
		throw new IllegalStateException("SerializedProperty is not a long");
	}
	
	public final double getDoubleValue()
	{
		if(getType().isAssignableFrom(double.class))
		{
			return (double)value;
		}
		
		throw new IllegalStateException("SerializedProperty is not a double");
	}
	
	public final String getStringValue()
	{
		if(getType().isAssignableFrom(String.class))
		{
			return (String)value;
		}
		
		throw new IllegalStateException("SerializedProperty is not a string");
	}
	
	public final Vector2 getVector2Value()
	{
		if(getType().isAssignableFrom(Vector2.class))
		{
			return (Vector2)value;
		}
		
		throw new IllegalStateException("SerializedProperty is not a vector2");
	}
	
	public final Vector3 getVector3Value()
	{
		if(getType().isAssignableFrom(Vector3.class))
		{
			return (Vector3)value;
		}
		
		throw new IllegalStateException("SerializedProperty is not a vector3");
	}
	
	public final Vector4 getVector4Value()
	{
		if(getType().isAssignableFrom(Vector4.class))
		{
			return (Vector4)value;
		}
		
		throw new IllegalStateException("SerializedProperty is not a vector4");
	}
	
	public final Quaternion getQuaternionValue()
	{
		if(getType().isAssignableFrom(Quaternion.class))
		{
			return (Quaternion)value;
		}
		
		throw new IllegalStateException("SerializedProperty is not a quaternion");
	}
	
	public final Object getObjectReferenceValue()
	{
		return value;
	}
	
	public final String getName()
	{
		return ((SerializedPropertyObject)internalProperty).field.getName();
	}
	
	public final Class<?> getType()
	{
		Field field = ((SerializedPropertyObject)internalProperty).field;
		return field.getType();
	}
	
	public final SerializedObject getSerializedObject()
	{
		return serializedObject;
	}
}
