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

package com.snakybo.torch.util;

import com.snakybo.torch.asset.Assets;
import com.snakybo.torch.color.Color;
import com.snakybo.torch.color.Color32;
import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.debug.LoggerInternal;
import com.snakybo.torch.util.tuple.Tuple3;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.URI;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class ParserUtil
{
	private ParserUtil()
	{
		throw new AssertionError();
	}
	
	public static Document getDocument(URI uri) throws NoSuchFileException
	{
		File file = new File(uri);
		
		if(!file.exists())
		{
			throw new NoSuchFileException("File not found: " + uri);
		}
		
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(file);
			
			document.getDocumentElement().normalize();
			return document;
		}
		catch(Exception e)
		{
			Logger.logError(e.getMessage(), e);
		}
		
		return null;
	}
	
	public static boolean isCorrectFile(Document document, String requiredRootName)
	{
		return document.getDocumentElement().getNodeName().equals(requiredRootName);
	}
	
	public static boolean parseBoolean(Element element)
	{
		LoggerInternal.log("Parsing boolean value: " + element.getTextContent());
		return Boolean.parseBoolean(element.getTextContent());
	}
	
	public static int parseInt(Element element)
	{
		LoggerInternal.log("Parsing integer valu: " + element.getTextContent());
		
		if(element.getTextContent().startsWith("0x"))
		{
			return Integer.parseInt(element.getTextContent().substring(2), 16);
		}
		
		return Integer.parseInt(element.getTextContent());
	}
	
	public static float parseFloat(Element element)
	{
		LoggerInternal.log("Parsing float value: " + element.getTextContent());
		return Float.parseFloat(element.getTextContent());
	}
	
	public static double parseDouble(Element element)
	{
		LoggerInternal.log("Parsing double value: " + element.getTextContent());
		return Double.parseDouble(element.getTextContent());
	}
	
	public static Enum<?> parseEnum(Element element)
	{
		LoggerInternal.log("Parsing enum value: " + element.getTextContent());
		
		String value = element.getTextContent();
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
	
	public static Color parseColor(Element element)
	{
		LoggerInternal.log("Parsing Color");
		Vector4f value = parseVector4(element);
		
		return new Color(value.x, value.y, value.z, value.w);
	}
	
	public static Vector2f parseVector2(Element element)
	{
		LoggerInternal.log("Parsing Vector2");
		
		float x = 0;
		float y = 0;
		
		x = parseFloat((Element)element.getElementsByTagName("x").item(0));
		y = parseFloat((Element)element.getElementsByTagName("y").item(0));
		
		return new Vector2f(x, y);
	}
	
	public static Vector3f parseVector3(Element element)
	{
		LoggerInternal.log("Parsing Vector3");
		
		float x = 0;
		float y = 0;
		float z = 0;
		
		x = parseFloat((Element)element.getElementsByTagName("x").item(0));
		y = parseFloat((Element)element.getElementsByTagName("y").item(0));
		z = parseFloat((Element)element.getElementsByTagName("z").item(0));
		
		return new Vector3f(x, y, z);
	}
	
	public static Vector4f parseVector4(Element element)
	{
		LoggerInternal.log("Parsing Vector4");
		
		float x = 0;
		float y = 0;
		float z = 0;
		float w = 0;
		
		x = parseFloat((Element)element.getElementsByTagName("x").item(0));
		y = parseFloat((Element)element.getElementsByTagName("y").item(0));
		z = parseFloat((Element)element.getElementsByTagName("z").item(0));
		w = parseFloat((Element)element.getElementsByTagName("w").item(0));
		
		return new Vector4f(x, y, z, w);
	}
	
	public static Quaternionf parseQuaternion(Element element)
	{
		LoggerInternal.log("Parsing Quaternion");
		
		float x = 0;
		float y = 0;
		float z = 0;
		float w = 1;
		
		x = parseFloat((Element)element.getElementsByTagName("x").item(0));
		y = parseFloat((Element)element.getElementsByTagName("y").item(0));
		z = parseFloat((Element)element.getElementsByTagName("z").item(0));
		w = parseFloat((Element)element.getElementsByTagName("w").item(0));
			
		if(element.getElementsByTagName("w").getLength() == 0)
		{
			w = 1;
		}
		
		return new Quaternionf(x, y, z, w);
	}
	
	public static Set<Tuple3<String, String, Object>> parseFieldList(NodeList nodeList)
	{
		LoggerInternal.log("Parsing field nodes");
		Set<Tuple3<String, String, Object>> result = new HashSet<>();
		
		for(int i = 0; i < nodeList.getLength(); i++)
		{
			Node node = nodeList.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element element = (Element)node;
				result.add(parseField(element));
			}
		}
		
		return result;
	}
	
	public static Object[] parseParameterList(NodeList nodeList)
	{
		LoggerInternal.log("Parsing parameter nodes");
		List<Object> result = new ArrayList<>();
		
		for(int i = 0; i < nodeList.getLength(); i++)
		{
			Node node = nodeList.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element element = (Element)node;
				result.add(ParserUtil.parseParameter(element));
			}
		}
		
		return result.toArray(new Object[result.size()]);
	}
	
	public static Tuple3<String, String, Object> parseField(Element element)
	{
		String key = element.getAttribute("name");
		String type = element.getAttribute("type");
		Object value = parseParameter(element);
		
		return new Tuple3<>(key, type, value);
	}
	
	public static Object parseParameter(Element element)
	{
		String type = element.getAttribute("type");
		
		if(type.isEmpty())
		{
			throw new IllegalArgumentException("Specified element does not have a \"type\" attribute: " + element.getNodeName());
		}
		
		switch(type)
		{
		case "boolean":
			return parseBoolean(element);
		case "int":
			return parseInt(element);
		case "float":
			return parseFloat(element);
		case "double":
			return parseDouble(element);
		case "enum":
			return parseEnum(element);
		case "color":
			return parseColor(element);
		case "vector2":
			return parseVector2(element);
		case "vector3":
			return parseVector3(element);
		case "vector4":
			return parseVector4(element);
		case "quaternion":
			return parseQuaternion(element);
		case "material":
		case "texture":
		case "mesh":
			return Assets.load(element.getTextContent());
		default:
			Logger.logError("Unknown parameter type: " + type);
			return null;
		}
	}
	
	public static <T> T parseParameter(Class<T> clazz, Element element)
	{
		Object result = parseParameter(element);
		
		if(result != null)
		{
			return clazz.cast(result);
		}
		
		return null;
	}
}
