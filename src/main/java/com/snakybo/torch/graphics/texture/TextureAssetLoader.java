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

package com.snakybo.torch.graphics.texture;

import com.snakybo.torch.util.FileUtils;
import com.snakybo.torch.util.debug.Logger;
import com.snakybo.torch.util.debug.LoggerInternal;
import com.snakybo.torch.xml.XMLParser;
import com.snakybo.torch.xml.parsers.TextureParser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.NoSuchFileException;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class TextureAssetLoader
{
	private TextureAssetLoader()
	{
		throw new AssertionError();
	}
	
	public static Texture load(String path)
	{
		LoggerInternal.log("Begin loading of texture: " + path);
		
		if(TextureAsset.all.containsKey(path))
		{
			LoggerInternal.log("Texture has already been loaded");
			return new Texture(TextureAsset.all.get(path));
		}
		
		try
		{
			TextureParser.TextureData textureData = (TextureParser.TextureData)XMLParser.decode(path + ".dat");
			
			return new Texture(
					path,
					getBufferedImage(path),
					textureData.type,
					textureData.filters,
					textureData.anisoLevel,
					textureData.format,
					textureData.internalFormat,
					textureData.clamp);
		}
		catch(NoSuchFileException e)
		{
			Logger.logError(e.getMessage(), e);
		}
		
		return null;
	}
	
	private static BufferedImage getBufferedImage(String path) throws NoSuchFileException
	{
		try
		{
			URI uri = FileUtils.toURI(path);
			return ImageIO.read(new File(uri));
		}
		catch(IOException e)
		{
			Logger.logError(e.toString(), e);
		}
		
		throw new NoSuchFileException("No file found at: " + path);
	}
}
