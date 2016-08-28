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
import com.snakybo.torch.xml.XMLParserUtils;
import org.w3c.dom.Element;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class TextureParser
{
	private static final String VERSION = "1";
	
	public static final class TextureData
	{
		public final int type;
		public final int filters;
		public final float anisoLevel;
		public final int format;
		public final int internalFormat;
		public final boolean clamp;
		
		public TextureData(int type, int filters, float anisoLevel, int format, int internalFormat, boolean clamp)
		{
			this.type = type;
			this.filters = filters;
			this.anisoLevel = anisoLevel;
			this.format = format;
			this.internalFormat = internalFormat;
			this.clamp = clamp;
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
		
		Element parameters = (Element)element.getElementsByTagName("parameters").item(0);
		
		Element typeElement = (Element)parameters.getElementsByTagName("type").item(0);
		int type = (int)XMLParserUtils.decodeObject(typeElement.getAttribute("type"), typeElement.getTextContent());
		
		Element filtersElement = (Element)parameters.getElementsByTagName("filters").item(0);
		int filters = (int)XMLParserUtils.decodeObject(filtersElement.getAttribute("type"), filtersElement.getTextContent());
		
		Element anisoLevelElement = (Element)parameters.getElementsByTagName("anisoLevel").item(0);
		float anisoLevel = (float)XMLParserUtils.decodeObject(anisoLevelElement.getAttribute("type"), anisoLevelElement.getTextContent());
		
		Element formatElement = (Element)parameters.getElementsByTagName("format").item(0);
		int format = (int)XMLParserUtils.decodeObject(formatElement.getAttribute("type"), formatElement.getTextContent());
		
		Element internalFormatElement = (Element)parameters.getElementsByTagName("internalFormat").item(0);
		int internalFormat = (int)XMLParserUtils.decodeObject(internalFormatElement.getAttribute("type"), internalFormatElement.getTextContent());
		
		Element clampElement = (Element)parameters.getElementsByTagName("clamp").item(0);
		boolean clamp = (boolean)XMLParserUtils.decodeObject(clampElement.getAttribute("type"), clampElement.getTextContent());
		
		LoggerInternal.log("Successfully decoded texture data");
		return new TextureData(type, filters, anisoLevel, format, internalFormat, clamp);
	}
}
