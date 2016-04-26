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

package com.snakybo.torch.model.obj;

/**
 * @author Snakybo
 * @since 1.0
 */
final class OBJIndex
{
	int vertex;
	int texCoord;
	int normal;
	
	@Override
	public final boolean equals(Object obj)
	{
		OBJIndex index = (OBJIndex)obj;
		return vertex == index.vertex && texCoord == index.texCoord && normal == index.normal;
	}

	@Override
	public final int hashCode()
	{
		final int BASE = 17;
		final int MULTIPLIER = 31;

		int result = BASE;

		result = MULTIPLIER * result + vertex;
		result = MULTIPLIER * result + texCoord;
		result = MULTIPLIER * result + normal;

		return result;
	}
}
