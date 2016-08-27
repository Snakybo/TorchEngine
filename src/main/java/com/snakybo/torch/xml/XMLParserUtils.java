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

import com.snakybo.torch.asset.Assets;
import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.graphics.color.Color;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class XMLParserUtils
{
	private XMLParserUtils()
	{
		throw new AssertionError();
	}
	
	public static Document getDocument(URI uri)
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(uri));
			
			document.getDocumentElement().normalize();
			return document;
		}
		catch(IOException | ParserConfigurationException | SAXException e)
		{
			Logger.logError("Error while decoding XML: " + e.getMessage(), e);
		}
		
		return null;
	}
	
	public static Class<?> decodeClass(String className)
	{
		try
		{
			return Class.forName(className);
		}
		catch(ClassNotFoundException e)
		{
			Logger.logError("Error while decoding XML: " + e.getMessage(), e);
		}
		
		return null;
	}
	
	public static Object decodeObject(String type, String value)
	{
		switch(type)
		{
		case "boolean":
			return Boolean.parseBoolean(value);
		case "byte":
			return Byte.parseByte(value);
		case "int":
			return Integer.parseInt(value);
		case "float":
			return Float.parseFloat(value);
		case "double":
			return Double.parseDouble(value);
		case "long":
			return Long.parseLong(value);
		case "enum":
			return decodeEnum(value);
		case "vector2":
			return decodeVector2(value);
		case "vector3":
			return decodeVector3(value);
		case "vector4":
			return decodeVector4(value);
		case "quaternion":
			return decodeQuaternion(value);
		case "color":
			return decodeColor(value);
		case "asset":
			return Assets.load(value);
		default:
			throw new IllegalArgumentException("Unable to parse XML: Unknown object type: " + type + ", value is: " + value);
		}
	}
	
	private static Enum<?> decodeEnum(String value)
	{
		String k = value.substring(0, value.indexOf(':'));
		int v = Integer.parseInt(value.substring(value.indexOf(':') + 1));
		
		try
		{
			Class<Enum> clazz = (Class<Enum>)Class.forName(k);
			Enum<?>[] values = clazz.getEnumConstants();
			
			return values[v];
		}
		catch(ClassNotFoundException e)
		{
			Logger.logError(e.getMessage(), e);
		}
		
		return null;
	}
	
	private static Vector2f decodeVector2(String value)
	{
		String[] parts = value.split(", ");
		
		float x = Float.parseFloat(parts[0]);
		float y = Float.parseFloat(parts[1]);
		
		return new Vector2f(x, y);
	}
	
	private static Vector3f decodeVector3(String value)
	{
		String[] parts = value.split(", ");
		
		float x = Float.parseFloat(parts[0]);
		float y = Float.parseFloat(parts[1]);
		float z = Float.parseFloat(parts[2]);
		
		return new Vector3f(x, y, z);
	}
	
	private static Vector4f decodeVector4(String value)
	{
		String[] parts = value.split(", ");
		
		float x = Float.parseFloat(parts[0]);
		float y = Float.parseFloat(parts[1]);
		float z = Float.parseFloat(parts[2]);
		float w = Float.parseFloat(parts[3]);
		
		return new Vector4f(x, y, z, w);
	}
	
	private static Quaternionf decodeQuaternion(String value)
	{
		String[] parts = value.split(", ");
		
		float x = Float.parseFloat(parts[0]);
		float y = Float.parseFloat(parts[1]);
		float z = Float.parseFloat(parts[2]);
		float w = Float.parseFloat(parts[3]);
		
		return new Quaternionf(x, y, z, w);
	}
	
	private static Color decodeColor(String value)
	{
		String[] parts = value.split(", ");
		
		float r = Float.parseFloat(parts[0]);
		float g = Float.parseFloat(parts[1]);
		float b = Float.parseFloat(parts[2]);
		float a = Float.parseFloat(parts[3]);
		
		return new Color(r, g, b, a);
	}
}
