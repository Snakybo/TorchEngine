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

import java.lang.Object;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class GameObjectLoader
{
	private GameObjectLoader()
	{
		throw new AssertionError();
	}
	
	public static Iterable<GameObject> parseGameObjectList(NodeList nodeList)
	{
		List<GameObject> result = new ArrayList<>();
		LoggerInternal.log("Parsing GameObject nodes");
		
		for(int i = 0; i < nodeList.getLength(); i++)
		{
			Node node = nodeList.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element element = (Element)node;
				String name = element.getElementsByTagName("name").item(0).getTextContent();
				name = !name.isEmpty() ? name : "GameObject";
				
				LoggerInternal.log("Parsing GameObject node: " + name);
				
				GameObject gameObject = new GameObject(name);
				parseTransform(gameObject, element);
				
				NodeList componentNodeList = element.getElementsByTagName("component");
				
				if(componentNodeList.getLength() > 0)
				{
					ComponentLoader.loadComponents(gameObject, componentNodeList);
				}
				
				result.add(gameObject);
			}
		}
		
		return result;
	}
	
	private static void parseTransform(GameObject gameObject, Element element)
	{
		Element position = (Element)element.getElementsByTagName("position").item(0);
		Element rotation = (Element)element.getElementsByTagName("rotation").item(0);
		Element scale = (Element)element.getElementsByTagName("scale").item(0);
		
		gameObject.getTransform().setLocalPosition(ParserUtil.parseVector3(position));
		gameObject.getTransform().setLocalRotation(ParserUtil.parseQuaternion(rotation));
		gameObject.getTransform().setLocalScale(ParserUtil.parseVector3(scale));
	}
}