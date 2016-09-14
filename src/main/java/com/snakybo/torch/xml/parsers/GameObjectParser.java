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
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class GameObjectParser
{
	public static class GameObjectData
	{
		public final String name;
		
		public final Vector3f position;
		public final Quaternionf rotation;
		public final Vector3f scale;
		
		public final List<ComponentParser.ComponentData> componentData;
		
		public GameObjectData(String name, Vector3f position, Quaternionf rotation, Vector3f scale, List<ComponentParser.ComponentData> componentData)
		{
			this.name = name;
			this.position = position;
			this.rotation = rotation;
			this.scale = scale;
			this.componentData = componentData;
		}
	}
	
	private GameObjectParser()
	{
		throw new AssertionError();
	}
	
	public static GameObjectData decode(Element element)
	{
		String name = element.getAttribute("name");
		LoggerInternal.log("GameObject name: " + name);
		
		Element positionElement = (Element)element.getElementsByTagName("position").item(0);
		Vector3f position = (Vector3f)XMLParserUtils.decodeObject("vector3", positionElement.getTextContent());
		
		Element rotationElement = (Element)element.getElementsByTagName("rotation").item(0);
		Quaternionf rotation = (Quaternionf)XMLParserUtils.decodeObject("quaternion", rotationElement.getTextContent());
		
		Element scaleElement = (Element)element.getElementsByTagName("scale").item(0);
		Vector3f scale = (Vector3f)XMLParserUtils.decodeObject("vector3", scaleElement.getTextContent());
		
		NodeList components = element.getElementsByTagName("components").item(0).getChildNodes();
		List<ComponentParser.ComponentData> componentData = new ArrayList<>();
		
		LoggerInternal.log("Begin decoding Component data");
		for(int i = 0; i < components.getLength(); i++)
		{
			Node node = components.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element component = (Element)node;
				componentData.add(ComponentParser.decode(component));
			}
		}
		
		LoggerInternal.log("Successfully decoded GameObject data");
		return new GameObjectData(name, position, rotation, scale, componentData);
	}
}
