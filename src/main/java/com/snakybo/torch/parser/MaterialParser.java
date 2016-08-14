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

import com.snakybo.torch.color.Color;
import com.snakybo.torch.color.Color32;
import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.debug.LoggerInternal;
import com.snakybo.torch.material.Material;
import com.snakybo.torch.texture.Texture;
import com.snakybo.torch.util.FileUtils;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.nio.file.NoSuchFileException;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class MaterialParser
{
	private MaterialParser()
	{
		throw new AssertionError();
	}
	
	public static Material parseMaterial(String file)
	{
		try
		{
			LoggerInternal.log("Parsing material file: " + file);
			Document document = ParserUtil.getDocument(FileUtils.toURI(file + ".mtl.dat"));
			
			if(!document.getDocumentElement().getNodeName().equals("material"))
			{
				throw new IllegalArgumentException("Specified file is not a material file");
			}
						
			String shader = document.getDocumentElement().getElementsByTagName("shader").item(0).getTextContent();
			
			Material material = new Material(shader);
			
			NodeList propertyNodeList = document.getDocumentElement().getElementsByTagName("property");
			
			for(int i = 0; i < propertyNodeList.getLength(); i++)
			{
				Node node = propertyNodeList.item(i);
				
				if(node.getNodeType() == Node.ELEMENT_NODE)
				{
					Element element = (Element)node;
					String name = element.getAttribute("name");
					Object property = ParserUtil.parseParameter(element);
					
					switch(element.getAttribute("type"))
					{
					case "texture":
						material.setTexture(name, (Texture)property);
						break;
					case "color":
						material.setColor(name, (Color)property);
						break;
					case "color32":
						material.setColor(name, (Color32)property);
						break;
					case "int":
						material.setInt(name, (int)property);
						break;
					case "float":
						material.setFloat(name, (float)property);
						break;
					case "vector2":
						material.setVector2f(name, (Vector2f)property);
						break;
					case "vector3":
						material.setVector3f(name, (Vector3f)property);
						break;
					case "vector4":
						material.setVector4f(name, (Vector4f)property);
						break;
					default:
						Logger.logError("Invalid material type: " + element.getAttribute("type"));
						break;
					}
				}
			}
			
			return material;
		}
		catch(NoSuchFileException e)
		{
			Logger.logError(e.getMessage(), e);
		}
		
		return null;
	}
}
