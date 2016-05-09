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

package com.snakybo.torch.bitmap;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.imageio.ImageIO;

import com.snakybo.torch.debug.LoggerInternal;
import com.snakybo.torch.resource.IResourceLoader;
import com.snakybo.torch.resource.ResourceLoaderData;
import com.snakybo.torch.util.FileUtils;

/**
 * @author Snakybo
 * @since 1.0
 */
@ResourceLoaderData(types={"jpg", "png"})
public final class BitmapResourceLoader implements IResourceLoader
{
	@Override
	public final Object load(URI path)
	{
		LoggerInternal.log("Loading Bitmap: " + FileUtils.getName(path), this);
		
		try
		{
			return new Bitmap(ImageIO.read(new File(path)));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}	
}
