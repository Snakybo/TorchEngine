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

import com.snakybo.torch.util.debug.Logger;
import com.snakybo.torch.util.debug.LoggerInternal;
import com.snakybo.torch.util.reflection.SerializedFieldUtils;
import com.snakybo.torch.util.xml.parsers.ComponentParser;

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
	
	public static Component load(GameObject gameObject, ComponentParser.ComponentData componentData)
	{
		try
		{
			if(componentData.type.getConstructors().length > 1 || componentData.type.getConstructor() == null)
			{
				throw new RuntimeException("Components should not have a constructor (" + componentData.type.getName() + ")");
			}
		}
		catch(NoSuchMethodException e)
		{
			Logger.logError(e.getMessage(), e);
			return null;
		}
		
		LoggerInternal.log("Creating component: " + componentData.type);
		Component component = gameObject.addComponentInternal(componentData.type);
		
		for(ComponentParser.ComponentFieldData fieldData : componentData.fieldData)
		{
			Field field = null;
			
			try
			{
				field = SerializedFieldUtils.get(component, fieldData.name);
			}
			catch(NoSuchFieldException | SecurityException e)
			{
				Logger.logError("Unable to find field \"" + fieldData.name + "\" on " + gameObject + ":" + component);
				continue;
			}
			
			switch(fieldData.type)
			{
			case "byte":
				SerializedFieldUtils.set(component, field, (byte)fieldData.value);
				break;
			case "short":
				SerializedFieldUtils.set(component, field, (short)fieldData.value);
				break;
			case "int":
				SerializedFieldUtils.set(component, field, (int)fieldData.value);
				break;
			case "float":
				SerializedFieldUtils.set(component, field, (float)fieldData.value);
				break;
			case "long":
				SerializedFieldUtils.set(component, field, (long)fieldData.value);
				break;
			case "double":
				SerializedFieldUtils.set(component, field, (double)fieldData.value);
				break;
			case "char":
				SerializedFieldUtils.set(component, field, (char)fieldData.value);
				break;
			case "boolean":
				SerializedFieldUtils.set(component, field, (boolean)fieldData.value);
				break;
			default:
				SerializedFieldUtils.set(component, field, fieldData.value);
				break;
			}
		}
		
		return component;
	}
}
