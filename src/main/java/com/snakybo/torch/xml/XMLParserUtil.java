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

package com.snakybo.torch.xml;

import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.debug.LoggerInternal;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class XMLParserUtil
{
	private XMLParserUtil()
	{
		throw new AssertionError();
	}
	
	public static int parseInt(String value)
	{
		LoggerInternal.log("Parsing integer value from XML: " + value);
		return Integer.parseInt(value);
	}
	
	public static float parseFloat(String value)
	{
		LoggerInternal.log("Parsing float value from XML: " + value);
		return Float.parseFloat(value);
	}
	
	public static double parseDouble(String value)
	{
		LoggerInternal.log("Parsing double value from XML: " + value);
		return Double.parseDouble(value);
	}
	
	public static Enum<?> parseEnum(String value)
	{
		LoggerInternal.log("Parsing enum value from XML: " + value);
		
		String k = value.substring(0, value.indexOf(':'));
		String v = value.substring(value.indexOf(':') + 1);
		
		try
		{
			Enum<?> e = Enum.valueOf((Class<Enum>)Class.forName(k), v);
			return e;
		}
		catch(ClassNotFoundException e)
		{
			Logger.logError(e.getMessage(), e);
		}
		
		return null;
	}
	
	public static Vector3f parseVector3(Node node)
	{
		LoggerInternal.log("Parsing Vector3 from XML");
		
		float x = 0;
		float y = 0;
		float z = 0;
		
		if(node.getNodeType() == Node.ELEMENT_NODE)
		{
			Element element = (Element)node;
			
			x = parseFloat(element.getElementsByTagName("x").item(0).getTextContent());
			y = parseFloat(element.getElementsByTagName("y").item(0).getTextContent());
			z = parseFloat(element.getElementsByTagName("z").item(0).getTextContent());
		}
		
		return new Vector3f(x, y, z);
	}
	
	public static Quaternionf parseQuaternion(Node node)
	{
		LoggerInternal.log("Parsing Quaternion from XML");
		
		float x = 0;
		float y = 0;
		float z = 0;
		float w = 1;
		
		if(node.getNodeType() == Node.ELEMENT_NODE)
		{
			Element element = (Element)node;
			
			x = parseFloat(element.getElementsByTagName("x").item(0).getTextContent());
			y = parseFloat(element.getElementsByTagName("y").item(0).getTextContent());
			z = parseFloat(element.getElementsByTagName("z").item(0).getTextContent());
			w = parseFloat(element.getElementsByTagName("w").item(0).getTextContent());
			
			if(element.getElementsByTagName("w").getLength() == 0)
			{
				w = 1;
			}
		}
		
		return new Quaternionf(x, y, z, w);
	}
}
