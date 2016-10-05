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

import com.snakybo.torch.asset2.Asset2;
import com.snakybo.torch.asset2.AssetData2;
import com.snakybo.torch.util.FileUtils;
import com.snakybo.torch.util.debug.Logger;
import com.snakybo.torch.util.debug.LoggerInternal;
import com.snakybo.torch.util.xml.XMLParser;
import com.snakybo.torch.util.xml.parsers.TextureParser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.NoSuchFileException;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

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
		
		try
		{
			TextureParser.TextureData textureData = (TextureParser.TextureData)XMLParser.decode(path + ".dat");
			Texture texture = null;
			
			if(textureData.target.equals(Texture2D.class))
			{
				texture = new Texture2D(path);
			}
			else
			{
				Logger.logError("Unsupported texture target: " + textureData.target);
			}
			
			texture.setFilterMode(textureData.filterMode);
			texture.setWrapMode(textureData.wrapMode);
			texture.setAnisoLevel(textureData.anisoLevel);
			
			return texture;
		}
		catch(NoSuchFileException e)
		{
			Logger.logError(e.getMessage(), e);
		}
		
		return null;
	}
}
