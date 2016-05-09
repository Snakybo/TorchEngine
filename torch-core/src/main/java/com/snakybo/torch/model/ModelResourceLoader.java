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

package com.snakybo.torch.model;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.debug.LoggerInternal;
import com.snakybo.torch.model.obj.OBJModel;
import com.snakybo.torch.resource.IResourceLoader;
import com.snakybo.torch.resource.ResourceLoaderData;
import com.snakybo.torch.util.FileUtils;

/**
 * @author Kevin
 *
 */
@ResourceLoaderData(types={"obj"})
public final class ModelResourceLoader implements IResourceLoader
{	
	@Override
	public final Object load(URI path)
	{
		try
		{
			LoggerInternal.log("Importing " + FileUtils.getName(path), this);
			
			String extension = FileUtils.getExtension(path);
			
			List<String> lines = Files.readAllLines(Paths.get(path));
			IModelLoader loader = null;
			
			switch(extension)
			{
			case "obj":
				loader = new OBJModel(lines);
				break;
			}
			
			return loader.toModel();
		}
		catch(IOException e)
		{
			Logger.logException(e, this);
		}
		
		return null;
	}
}
