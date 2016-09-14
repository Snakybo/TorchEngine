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

import com.snakybo.torch.graphics.material.MaterialAssetLoader;
import com.snakybo.torch.graphics.mesh.MeshAssetLoader;
import com.snakybo.torch.graphics.shader.Shader;
import com.snakybo.torch.graphics.texture.TextureAssetLoader;
import com.snakybo.torch.util.FileUtils;
import com.snakybo.torch.util.debug.Logger;

import java.nio.file.NoSuchFileException;

/**
 * <p>
 * The {@link Asset} loader. Attempts to load assets based on their file extension.
 * </p>
 *
 * @see Asset
 *
 * @author Snakybo
 * @since 1.0
 */
public final class Assets
{
	private Assets()
	{
		throw new AssertionError();
	}
	
	/**
	 * <p>
	 * Load an {@link Asset} by file name.
	 * </p>
	 *
	 * @param file The file to load.
	 * @return The loaded asset if it exists and has a known type. Returns {@code null} otherwise.
	 */
	public static Asset load(String file)
	{
		try
		{
			String path = getPath(file);
			String ext = FileUtils.getExtension(path);
			
			switch(ext)
			{
			case "png":
			case "jpg":
				return TextureAssetLoader.load(path);
			case "obj":
				return MeshAssetLoader.load(path);
			case "mtl":
				return MaterialAssetLoader.load(path);
			case "glsl":
				return Shader.load(path);
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
	
	/**
	 * Load an {@link Asset} by file name.
	 * @param clazz The class to cast the resulting {@code Asset} to.
	 * @param file The file to load.
	 * @param <T> The class to cast the resulting {@code Asset} to.
	 * @return The loaded asset if it exists and has a known type. Returns {@code null} otherwise.
	 * @see #load(String)
	 */
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
			target = "torch_default/" + target;
			
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
