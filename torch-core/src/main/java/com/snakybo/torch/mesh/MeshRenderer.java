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

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import com.snakybo.torch.model.Model;
import com.snakybo.torch.object.Component;

/**
 * @author Snakybo
 * @since 1.0
 */
public class MeshRenderer extends Component
{
	private static final int POSITION_VBO = 0;
	private static final int TEXCOORD_VBO = 1;
	private static final int NORMAL_VBO = 2;
	private static final int INDEX_VBO = 3;
	private static final int LAST_VBO = INDEX_VBO;
	
	private Material material;
	private Model model;
	
	private IntBuffer vao;
	private IntBuffer vbo;
	
	public MeshRenderer(Model model, Material material)
	{
		this.model = model;
		this.material = material;
	}
	
	@Override
	protected void start()
	{
		material.setGameObject(getGameObject());
		
		vao = BufferUtils.createIntBuffer(1);
		vbo = BufferUtils.createIntBuffer(LAST_VBO + 1);
		
		glGenVertexArrays(vao);
		glBindVertexArray(vao.get(0));
		
		glGenBuffers(vbo);
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo.get(POSITION_VBO));		
		glBufferData(GL_ARRAY_BUFFER, model.getVertexBuffer(), GL_STATIC_DRAW);
		glVertexAttribPointer(POSITION_VBO, 3, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo.get(TEXCOORD_VBO));
		glBufferData(GL_ARRAY_BUFFER, model.getTexCoordBuffer(), GL_STATIC_DRAW);
		glVertexAttribPointer(TEXCOORD_VBO, 2, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo.get(NORMAL_VBO));
		glBufferData(GL_ARRAY_BUFFER, model.getNormalBuffer(), GL_STATIC_DRAW);
		glVertexAttribPointer(NORMAL_VBO, 3, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo.get(INDEX_VBO));
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, model.getIndexBuffer(), GL_STATIC_DRAW);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}
	
	@Override
	protected void renderObject()
	{
		material.bind();
		material.update();
		
		glBindVertexArray(vao.get(0));
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		
		glDrawElements(GL_TRIANGLES, model.getNumIndices(), GL_UNSIGNED_INT, 0);
		
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(0);
		
		glBindVertexArray(0);
		
		material.unbind();
	}
	
	@Override
	protected void destroy()
	{
		glDisableVertexAttribArray(0);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDeleteBuffers(vbo);
		
		glBindVertexArray(0);
		glDeleteVertexArrays(vao);
		
		material.destroy();
	}
}
