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

package com.snakybo.torch.mesh;

import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.debug.LoggerInternal;
import com.snakybo.torch.mesh.obj.OBJMesh;
import com.snakybo.torch.util.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class MeshAssetLoader
{
	public static Mesh load(String path)
	{
		LoggerInternal.log("Begin loading of mesh: " + path);
		
		if(MeshAsset.all.containsKey(path))
		{
			LoggerInternal.log("Mesh has already been loaded");
			return new Mesh(MeshAsset.all.get(path));
		}
		
		try
		{
			LoggerInternal.log("Begin parsing of mesh data file: " + path);
			String extension = FileUtils.getExtension(path);
			
			List<String> lines = Files.readAllLines(Paths.get(FileUtils.toURI(path)));
			IMeshLoader loader = null;
			
			switch(extension)
			{
			case "obj":
				loader = new OBJMesh(lines);
				break;
			}
			
			return loader.toModel(new Mesh(path));
		}
		catch(IOException e)
		{
			Logger.logError(e.toString(), e);
		}
		
		return null;
	}
}
