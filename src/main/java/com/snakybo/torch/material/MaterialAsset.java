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

import com.snakybo.torch.asset.Asset;
import com.snakybo.torch.asset.AssetData;
import com.snakybo.torch.asset.Assets;
import com.snakybo.torch.shader.Shader;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Snakybo
 * @since 1.0
 */
final class MaterialAsset extends AssetData
{
	static Map<String, MaterialAsset> all = new HashMap<>();
	
	Map<String, Object> values;
	
	Shader shader;
	
	MaterialAsset(String name, String shader)
	{
		super(name);
		
		this.shader = Assets.load(Shader.class, shader);
		this.values = new HashMap<>();
		
		if(name != null && !name.isEmpty())
		{
			all.put(name, this);
		}
	}
	
	@Override
	public final void destroy()
	{
		if(name != null && !name.isEmpty())
		{
			all.remove(name);
		}
		
		for(Object value : values.values())
		{
			if(value instanceof Asset)
			{
				((Asset)value).destroy();
			}
		}
		
		
		shader.destroy();
	}
}
