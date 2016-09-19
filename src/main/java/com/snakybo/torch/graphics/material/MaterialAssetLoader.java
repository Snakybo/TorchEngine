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

package com.snakybo.torch.graphics.material;

import com.snakybo.torch.graphics.texture.Texture2D;
import com.snakybo.torch.util.color.Color;
import com.snakybo.torch.util.debug.Logger;
import com.snakybo.torch.util.debug.LoggerInternal;
import com.snakybo.torch.util.xml.XMLParser;
import com.snakybo.torch.util.xml.parsers.MaterialParser;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.nio.file.NoSuchFileException;
import java.util.Map;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class MaterialAssetLoader
{
	private MaterialAssetLoader()
	{
		throw new AssertionError();
	}
	
	public static Material load(String path)
	{
		LoggerInternal.log("Begin loading of material: " + path);
		
		if(MaterialAsset.all.containsKey(path))
		{
			LoggerInternal.log("Material has already been loaded");
			return new Material(MaterialAsset.all.get(path));
		}
		
		try
		{
			MaterialParser.MaterialData materialData = (MaterialParser.MaterialData)XMLParser.decode(path);
			
			Material material = new Material(materialData.shader);
			
			for(Map.Entry<String, Object> prop : materialData.values.entrySet())
			{
				if(prop.getValue().getClass().equals(Vector2f.class))
				{
					material.setVector2f(prop.getKey(), (Vector2f)prop.getValue());
				}
				else if(prop.getValue().getClass().equals(Vector3f.class))
				{
					material.setVector3f(prop.getKey(), (Vector3f)prop.getValue());
				}
				else if(prop.getValue().getClass().equals(Vector4f.class))
				{
					material.setVector4f(prop.getKey(), (Vector4f)prop.getValue());
				}
				else if(prop.getValue().getClass().equals(Color.class))
				{
					material.setColor(prop.getKey(), (Color)prop.getValue());
				}
				else if(prop.getValue().getClass().equals(Float.class))
				{
					material.setFloat(prop.getKey(), (float)prop.getValue());
				}
				else if(prop.getValue().getClass().equals(Integer.class))
				{
					material.setInt(prop.getKey(), (int)prop.getValue());
				}
				else if(prop.getValue().getClass().equals(Texture2D.class))
				{
					material.setTexture(prop.getKey(), (Texture2D)prop.getValue());
				}
				else
				{
					Logger.logError("Unknown material property: " + prop.getKey() + " - " + prop.getValue().getClass());
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
