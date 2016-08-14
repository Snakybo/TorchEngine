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

package com.snakybo.torch.parser;

import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.debug.LoggerInternal;
import com.snakybo.torch.texture.Texture;
import com.snakybo.torch.texture.Texture2D;
import com.snakybo.torch.util.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.nio.file.NoSuchFileException;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class TextureParser
{
	private TextureParser()
	{
		throw new AssertionError();
	}
	
	public static Texture parseTexture(String file)
	{
		try
		{
			LoggerInternal.log("Parsing texture file: " + file);
			Document document = ParserUtil.getDocument(FileUtils.toURI(file + ".dat"));
			
			if(!document.getDocumentElement().getNodeName().equals("texture"))
			{
				throw new IllegalArgumentException("Specified file is not a texture file");
			}
			
			NodeList parameterNodeList = document.getDocumentElement().getElementsByTagName("parameter");
			
			try
			{
				String source = document.getDocumentElement().getElementsByTagName("source").item(0).getTextContent();
				Class<?> clazz = Class.forName(document.getDocumentElement().getElementsByTagName("type").item(0).getTextContent());
				Object[] parameters = ParserUtil.parseParameterList(parameterNodeList);
				
				if(clazz == Texture2D.class)
				{
					return Texture2D.load(source, (int)parameters[0], (int)parameters[1], (int)parameters[2], (int)parameters[3], (boolean)parameters[4]);
				}
			}
			catch(ClassNotFoundException e)
			{
				Logger.logError(e.getMessage(), e);
			}
		}
		catch(NoSuchFileException e)
		{
			Logger.logError(e.getMessage(), e);
		}
		
		return null;
	}
}
