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

package com.snakybo.torch.util.xml.parsers;

import com.snakybo.torch.util.debug.Logger;
import com.snakybo.torch.util.debug.LoggerInternal;
import com.snakybo.torch.util.xml.XMLParserUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class MaterialParser
{
	private static final String VERSION = "1";
	
	public static final class MaterialData
	{
		public final Map<String, Object> values;
		
		public final String shader;
		
		public MaterialData(String shader, Map<String, Object> values)
		{
			this.shader = shader;
			this.values = values;
		}
	}
	
	private MaterialParser()
	{
		throw new AssertionError();
	}
	
	public static MaterialData decode(Element element)
	{
		String version = element.getAttribute("version");
		
		LoggerInternal.log("Material data version: " + version);
		if(!version.equals(VERSION))
		{
			// TODO: Material data version upgrade tool
			Logger.logError("Unable to load scene data, invalid version (expected: " + VERSION + " got:" + version + ")");
			return null;
		}
		
		String shader = element.getAttribute("shader");
		
		NodeList properties = element.getElementsByTagName("properties").item(0).getChildNodes();
		Map<String, Object> values = new HashMap<>();
		
		LoggerInternal.log("Begin decoding material properties");
		for(int i = 0; i < properties.getLength(); i++)
		{
			Node node = properties.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element property = (Element)node;

				String name = property.getAttribute("name");
				Object value = XMLParserUtils.decodeObject(property.getAttribute("type"), property.getTextContent());
				LoggerInternal.log("Property=" + name + " Value="  + value);
				
				values.put(name, value);
			}
		}
		
		LoggerInternal.log("Successfully decoded material data");
		return new MaterialData(shader, values);
	}
}
