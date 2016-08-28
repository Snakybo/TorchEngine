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

import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.debug.LoggerInternal;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class SceneParser
{
	private static final String VERSION = "1";
	
	public static final class SceneData
	{
		public final List<GameObjectParser.GameObjectData> gameObjectData;
		
		public SceneData(List<GameObjectParser.GameObjectData> gameObjectData)
		{
			this.gameObjectData = gameObjectData;
		}
	}
	
	private SceneParser()
	{
		throw new AssertionError();
	}
	
	public static SceneData decode(Element element)
	{
		String version = element.getAttribute("version");
		
		LoggerInternal.log("Scene data version: " + version);
		if(!version.equals(VERSION))
		{
			// TODO: Scene data version upgrade tool
			Logger.logError("Unable to load scene data, invalid version (expected: " + VERSION + " got:" + version + ")");
			return null;
		}
		
		NodeList gameObjects = element.getElementsByTagName("game_objects").item(0).getChildNodes();
		List<GameObjectParser.GameObjectData> gameObjectData = new ArrayList<>();
		
		LoggerInternal.log("Begin decoding GameObject data");
		for(int i = 0; i < gameObjects.getLength(); i++)
		{
			Node node = gameObjects.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element gameObject = (Element)node;
				gameObjectData.add(GameObjectParser.decode(gameObject));
			}
		}
		
		LoggerInternal.log("Successfully decoded scene data");
		return new SceneData(gameObjectData);
	}
}
