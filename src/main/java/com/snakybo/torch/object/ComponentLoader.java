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
import com.snakybo.torch.debug.LoggerInternal;
import com.snakybo.torch.serialized.SerializationUtils;
import com.snakybo.torch.util.ParserUtil;
import com.snakybo.torch.util.tuple.Tuple3;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Set;

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
	
	public static void loadComponents(GameObject gameObject, NodeList nodeList)
	{
		LoggerInternal.log("Begin parsing of Component nodes for GameObject node: " + gameObject);
		
		for(int i = 0; i < nodeList.getLength(); i++)
		{
			Node node = nodeList.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element element = (Element)node;
				String componentClassString = element.getElementsByTagName("class").item(0).getTextContent();
				
				LoggerInternal.log("Parsing Component node: " + componentClassString);
				
				Component component = createComponent(gameObject, componentClassString);
				
				NodeList fieldNodeList = element.getElementsByTagName("field");
				
				if(fieldNodeList.getLength() > 0)
				{
					setFields(component, fieldNodeList);
				}
			}
		}
	}
	
	private static Component createComponent(GameObject gameObject, String clazz)
	{
		try
		{
			Class<?> componentClass = Class.forName(clazz);
			
			if(componentClass.getConstructors().length > 1 || componentClass.getConstructor() == null)
			{
				throw new RuntimeException("Components should not have a constructor (" + componentClass.getName() + ")");
			}
			
			return gameObject.addComponentInternal(componentClass);
		}
		catch(ReflectiveOperationException e)
		{
			Logger.logError(e.getMessage(), e);
		}
		
		return null;
	}
	
	private static void setFields(Component component, NodeList nodeList)
	{
		Set<Tuple3<String, String, java.lang.Object>> fields = ParserUtil.parseFieldList(nodeList);
		
		for(Tuple3<String, String, java.lang.Object> field : fields)
		{
			switch(field.v2)
			{
			case "byte":
				SerializationUtils.set(component, SerializationUtils.get(component, field.v1), (byte) field.v3);
				break;
			case "short":
				SerializationUtils.set(component, SerializationUtils.get(component, field.v1), (short) field.v3);
				break;
			case "int":
				SerializationUtils.set(component, SerializationUtils.get(component, field.v1), (int) field.v3);
				break;
			case "float":
				SerializationUtils.set(component, SerializationUtils.get(component, field.v1), (float) field.v3);
				break;
			case "long":
				SerializationUtils.set(component, SerializationUtils.get(component, field.v1), (long) field.v3);
				break;
			case "double":
				SerializationUtils.set(component, SerializationUtils.get(component, field.v1), (double) field.v3);
				break;
			case "char":
				SerializationUtils.set(component, SerializationUtils.get(component, field.v1), (char) field.v3);
				break;
			case "boolean":
				SerializationUtils.set(component, SerializationUtils.get(component, field.v1), (boolean) field.v3);
				break;
			default:
				SerializationUtils.set(component, SerializationUtils.get(component, field.v1), field.v3);
				break;
			}
		}
	}
}
