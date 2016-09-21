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

package com.snakybo.torch.graphics.shader;

import com.snakybo.torch.asset.Asset;
import com.snakybo.torch.graphics.material.Material;
import com.snakybo.torch.object.Component;

/**
 * <p>
 * The shader class is a direct link to the low-level GLSL shaders.
 * </p>
 *
 * <p>
 * A shader is usually attached to a {@link Material}, however it is possible to manually
 * use a shader in the {@code Component#onRenderObject()} method.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public final class Shader extends Asset
{
	ShaderAsset asset;
	
	Shader(ShaderAsset asset)
	{
		this.asset = asset;
		this.asset.addUsage();
	}
	
	Shader(String path)
	{
		asset = new ShaderAsset(path);
		asset.init();
	}
	
	@Override
	public final void finalize() throws Throwable
	{
		try
		{
			destroy();
		}
		finally
		{
			super.finalize();
		}
	}
	
	@Override
	public final void destroy()
	{
		if(asset != null)
		{
			asset.removeUsage();
			asset = null;
		}
	}
	
	/**
	 * <p>
	 * Get the name of the shader.
	 * </p>
	 *
	 * @return The name of the shader.
	 */
	public final String getName()
	{
		return asset.getName();
	}
	
	/**
	 * <p>
	 * Load a shader.
	 * </p>
	 *
	 * @param path The path/name of the shader.
	 * @return The loaded shader.
	 */
	public static Shader load(String path)
	{
		if(ShaderAsset.all.containsKey(path))
		{
			return new Shader(ShaderAsset.all.get(path));
		}
		
		return new Shader(path);
	}
}
