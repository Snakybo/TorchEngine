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

import com.snakybo.torch.graphics.texture.TextureFilterMode;
import com.snakybo.torch.graphics.texture.TextureWrapMode;
import com.snakybo.torch.util.debug.Logger;
import com.snakybo.torch.util.debug.LoggerInternal;
import com.snakybo.torch.util.xml.XMLParserUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class TextureParser
{
	private static final String VERSION = "1";
	
	public static final class TextureData
	{
		public final Class<?> target;
		public final TextureFilterMode filterMode;
		public final TextureWrapMode wrapMode;
		public final float anisoLevel;
		
		public TextureData(Class<?> target, TextureFilterMode filterMode, TextureWrapMode wrapMode, float anisoLevel)
		{
			this.target = target;
			this.filterMode = filterMode;
			this.wrapMode = wrapMode;
			this.anisoLevel = anisoLevel;
		}
	}
	
	private TextureParser()
	{
		throw new AssertionError();
	}
	
	public static TextureData decode(Element element)
	{
		String version = element.getAttribute("version");
		
		LoggerInternal.log("Texture data version: " + version);
		if(!version.equals(VERSION))
		{
			// TODO: Texture data version upgrade tool
			Logger.logError("Unable to load texture data, invalid version (expected: " + VERSION + " got:" + version + ")");
			return null;
		}
		
		Element parameters = getElement(element, "parameters");
		
		if(parameters != null)
		{
			Class<?> target = (Class<?>)decode(parameters, "target");
			TextureFilterMode textureFilterMode = (TextureFilterMode)decode(parameters, "filterMode");
			TextureWrapMode textureWrapMode = (TextureWrapMode)decode(parameters, "wrapMode");
			
			Object anisoLevelObject = decode(parameters, "anisoLevel");
			float anisoLevel = anisoLevelObject == null ? -1 : (float)anisoLevelObject;
			
			LoggerInternal.log("Successfully decoded texture data");
			return new TextureData(target, textureFilterMode, textureWrapMode, anisoLevel);
		}
		
		return null;
	}
	
	private static Object decode(Element parent, String name)
	{
		Element element = getElement(parent, name);
		
		if(element != null)
		{
			String type = element.getAttribute("type");
			String value = element.getTextContent();
			
			return XMLParserUtils.decodeObject(type, value);
		}
		
		return null;
	}
	
	private static Element getElement(Element parent, String name)
	{
		NodeList nodeList = parent.getElementsByTagName(name);
		
		if(nodeList.getLength() > 0)
		{
			Node node = nodeList.item(0);
			
			if(node.getNodeType() == Node.ELEMENT_NODE)
			{
				return (Element)node;
			}
		}
		
		return null;
	}
}
