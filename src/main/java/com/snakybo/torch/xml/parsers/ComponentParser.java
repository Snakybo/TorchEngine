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

package com.snakybo.torch.xml.parsers;

import com.snakybo.torch.util.debug.LoggerInternal;
import com.snakybo.torch.xml.XMLParserUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class ComponentParser
{
	public static class ComponentFieldData
	{
		public final String name;
		public final String type;
		public final Object value;
		
		public ComponentFieldData(String name, String type, Object value)
		{
			this.name = name;
			this.type = type;
			this.value = value;
		}
	}
	
	public static class ComponentData
	{
		public final Class<?> type;
		public final Set<ComponentFieldData> fieldData;
		
		public ComponentData(Class<?> type, Set<ComponentFieldData> fieldData)
		{
			this.type = type;
			this.fieldData = fieldData;
		}
	}
	
	private ComponentParser()
	{
		throw new AssertionError();
	}
	
	public static ComponentData decode(Element element)
	{
		Class<?> type = (Class<?>)XMLParserUtils.decodeObject("class", element.getAttribute("type"));
		LoggerInternal.log("Component type: " + type);
		
		NodeList fields = element.getElementsByTagName("fields").item(0).getChildNodes();
		Set<ComponentFieldData> fieldData = new HashSet<>();
		
		LoggerInternal.log("Begin decoding Component field data");
		for(int i = 0; i < fields.getLength(); i++)
		{
			Node node = fields.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element field = (Element)node;
				
				String name = field.getNodeName();
				String fieldType = field.getAttribute("type");
				Object value = XMLParserUtils.decodeObject(fieldType, field.getTextContent());
				LoggerInternal.log("Name=" + name + " Type=" + fieldType + " Value=" + value + " RawValue=" + field.getTextContent());
				
				fieldData.add(new ComponentFieldData(name, fieldType, value));
			}
		}
		
		LoggerInternal.log("Successfully decoded Component data");
		return new ComponentData(type, fieldData);
	}
}
