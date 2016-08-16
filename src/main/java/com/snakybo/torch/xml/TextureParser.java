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

import com.snakybo.torch.texture.Texture;
import org.w3c.dom.Element;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class TextureParser
{
	public static final class TextureData
	{
		public final Class<?> type;
		
		public final int filters;
		public final float anisoLevel;
		public final int format;
		public final int internalFormat;
		public final boolean clamp;
		
		public TextureData(Class<?> type, int filters, float anisoLevel, int format, int internalFormat, boolean clamp)
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
	
	static TextureData decode(Element element)
	{
		Class<?> type = XMLParserUtils.decodeClass(element.getAttribute("type"));
		
		Element parameters = (Element)element.getElementsByTagName("parameters").item(0);
		
		Element filtersElement = (Element)parameters.getElementsByTagName("filters").item(0);
		int filters = (int)XMLParserUtils.decodeObject(filtersElement.getAttribute("type"), filtersElement.getTextContent());
		
		Element anisioLevelElement = (Element)parameters.getElementsByTagName("anisioLevel").item(0);
		float anisioLevel = (float)XMLParserUtils.decodeObject(anisioLevelElement.getAttribute("type"), anisioLevelElement.getTextContent());
		
		Element formatElement = (Element)parameters.getElementsByTagName("format").item(0);
		int format = (int)XMLParserUtils.decodeObject(formatElement.getAttribute("type"), formatElement.getTextContent());
		
		Element internalFormatElement = (Element)parameters.getElementsByTagName("internalFormat").item(0);
		int internalFormat = (int)XMLParserUtils.decodeObject(internalFormatElement.getAttribute("type"), internalFormatElement.getTextContent());
		
		Element clampElement = (Element)parameters.getElementsByTagName("clamp").item(0);
		boolean clamp = (boolean)XMLParserUtils.decodeObject(clampElement.getAttribute("type"), clampElement.getTextContent());
		
		return new TextureData(type, filters, anisioLevel, format, internalFormat, clamp);
	}
}