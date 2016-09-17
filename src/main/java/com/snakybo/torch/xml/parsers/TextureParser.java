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

import com.snakybo.torch.graphics.texture.TextureFilterMode;
import com.snakybo.torch.graphics.texture.TextureWrapMode;
import com.snakybo.torch.util.debug.Logger;
import com.snakybo.torch.util.debug.LoggerInternal;
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
		
		Element parameters = (Element)element.getElementsByTagName("parameters").item(0);
		
		Element targetElement = (Element)parameters.getElementsByTagName("target").item(0);
		Class<?> target = (Class<?>)XMLParserUtils.decodeObject(targetElement.getAttribute("type"), targetElement.getTextContent());
		
		Element filterModeElement = (Element)parameters.getElementsByTagName("filterMode").item(0);
		TextureFilterMode filterMode = (TextureFilterMode)XMLParserUtils.decodeObject(filterModeElement.getAttribute("type"), filterModeElement.getTextContent());
		
		Element wrapModeElement = (Element)parameters.getElementsByTagName("wrapMode").item(0);
		TextureWrapMode wrapMode = (TextureWrapMode)XMLParserUtils.decodeObject(wrapModeElement.getAttribute("type"), wrapModeElement.getTextContent());
		
		Element anisoLevelElement = (Element)parameters.getElementsByTagName("anisoLevel").item(0);
		float anisoLevel = (float)XMLParserUtils.decodeObject(anisoLevelElement.getAttribute("type"), anisoLevelElement.getTextContent());
		
		LoggerInternal.log("Successfully decoded texture data");
		return new TextureData(target, filterMode, wrapMode, anisoLevel);
	}
}
