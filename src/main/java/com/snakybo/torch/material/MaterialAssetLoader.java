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

package com.snakybo.torch.material;

import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.debug.LoggerInternal;
import com.snakybo.torch.xml.MaterialParser;
import com.snakybo.torch.xml.XMLParser;

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
			
			for(Map.Entry<String, Object> value : materialData.values.entrySet())
			{
				material.set(value.getKey(), value.getValue());
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
