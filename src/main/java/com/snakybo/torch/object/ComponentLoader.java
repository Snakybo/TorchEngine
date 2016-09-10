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

package com.snakybo.torch.object;

import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.util.serialized.SerializationUtils;
import com.snakybo.torch.xml.parsers.ComponentParser;

import java.lang.reflect.Field;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class ComponentLoader
{
	private ComponentLoader()
	{
		throw new AssertionError();
	}
	
	public static void load(GameObject gameObject, ComponentParser.ComponentData componentData)
	{
		try
		{
			if(componentData.type.getConstructors().length > 1 || componentData.type.getConstructor() == null)
			{
				throw new RuntimeException("Components should not have a constructor (" + componentData.type.getName() + ")");
			}
		} catch(NoSuchMethodException e)
		{
			Logger.logError(e.getMessage(), e);
			return;
		}
		
		Component component = gameObject.addComponentInternal(componentData.type);
		
		for(ComponentParser.ComponentFieldData fieldData : componentData.fieldData)
		{
			Field field = SerializationUtils.get(component, fieldData.name);
			
			switch(fieldData.type)
			{
			case "byte":
				SerializationUtils.set(component, field, (byte)fieldData.value);
				break;
			case "short":
				SerializationUtils.set(component, field, (short)fieldData.value);
				break;
			case "int":
				SerializationUtils.set(component, field, (int)fieldData.value);
				break;
			case "float":
				SerializationUtils.set(component, field, (float)fieldData.value);
				break;
			case "long":
				SerializationUtils.set(component, field, (long)fieldData.value);
				break;
			case "double":
				SerializationUtils.set(component, field, (double)fieldData.value);
				break;
			case "char":
				SerializationUtils.set(component, field, (char)fieldData.value);
				break;
			case "boolean":
				SerializationUtils.set(component, field, (boolean)fieldData.value);
				break;
			default:
				SerializationUtils.set(component, field, fieldData.value);
				break;
			}
		}
		
		component.onCreate();
	}
}
