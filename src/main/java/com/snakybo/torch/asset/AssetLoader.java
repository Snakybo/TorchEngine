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

package com.snakybo.torch.asset;

import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.material.MaterialAssetLoader;
import com.snakybo.torch.mesh.MeshAssetLoader;
import com.snakybo.torch.texture.TextureAssetLoader;
import com.snakybo.torch.util.FileUtils;

import java.nio.file.NoSuchFileException;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class AssetLoader
{
	private AssetLoader()
	{
		throw new AssertionError();
	}
	
	public static Asset load(String file)
	{
		try
		{
			String path = getPath(file);
			String ext = FileUtils.getExtension(path);
			
			// TODO: Maybe shaders should be loaded via here too
			
			switch(ext)
			{
			case "png":
			case "jpg":
				return TextureAssetLoader.load(path);
			case "obj":
				return MeshAssetLoader.load(path);
			case "mtl":
				return MaterialAssetLoader.load(path);
			default:
				Logger.logError("Unknown asset type: " + ext);
				return null;
			}
		}
		catch(NoSuchFileException e)
		{
			Logger.logError(e.getMessage(), e);
		}
		
		return null;
	}
	
	public static <T extends Asset> T load(Class<T> clazz, String file)
	{
		Asset result = load(file);
		
		if(result != null)
		{
			return clazz.cast(result);
		}
		
		return null;
	}
	
	private static String getPath(String path) throws NoSuchFileException
	{
		String target = path;
		
		try
		{
			FileUtils.toURI(target);
		}
		catch(NoSuchFileException e)
		{
			target = "torch_internal/" + target;
			
			try
			{
				FileUtils.toURI(target);
			}
			catch(NoSuchFileException ex)
			{
				throw new NoSuchFileException("No file found at " + path + " or " + target);
			}
		}
		
		return target;
	}
}
