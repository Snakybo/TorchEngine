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

package com.snakybo.torch.graphics.camera;

import com.snakybo.torch.asset.Assets;
import com.snakybo.torch.component.Camera;
import com.snakybo.torch.graphics.material.Material;
import com.snakybo.torch.graphics.material.MaterialInternal;
import com.snakybo.torch.graphics.mesh.Mesh;
import com.snakybo.torch.graphics.renderer.MeshRendererInternal;
import com.snakybo.torch.graphics.shader.ShaderInternal;
import com.snakybo.torch.graphics.texture.Cubemap;
import com.snakybo.torch.graphics.texture.Texture;
import com.snakybo.torch.object.Transform;
import com.snakybo.torch.util.debug.Logger;
import javafx.scene.input.ScrollEvent;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_LESS;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Skybox
{
	private static final float[] VERTICES = {
			-1.0f,  1.0f, -1.0f,
			-1.0f, -1.0f, -1.0f,
		 	 1.0f, -1.0f, -1.0f,
			 1.0f, -1.0f, -1.0f,
			 1.0f,  1.0f, -1.0f,
			-1.0f,  1.0f, -1.0f,
			
			-1.0f, -1.0f,  1.0f,
			-1.0f, -1.0f, -1.0f,
			-1.0f,  1.0f, -1.0f,
			-1.0f,  1.0f, -1.0f,
			-1.0f,  1.0f,  1.0f,
			-1.0f, -1.0f,  1.0f,
			
			 1.0f, -1.0f, -1.0f,
			 1.0f, -1.0f,  1.0f,
			 1.0f,  1.0f,  1.0f,
			 1.0f,  1.0f,  1.0f,
			 1.0f,  1.0f, -1.0f,
			 1.0f, -1.0f, -1.0f,
			
			-1.0f, -1.0f,  1.0f,
			-1.0f,  1.0f,  1.0f,
			 1.0f,  1.0f,  1.0f,
			 1.0f,  1.0f,  1.0f,
			 1.0f, -1.0f,  1.0f,
			-1.0f, -1.0f,  1.0f,
			
			-1.0f,  1.0f, -1.0f,
			 1.0f,  1.0f, -1.0f,
			 1.0f,  1.0f,  1.0f,
			 1.0f,  1.0f,  1.0f,
			-1.0f,  1.0f,  1.0f,
			-1.0f,  1.0f, -1.0f,
			
			-1.0f, -1.0f, -1.0f,
			-1.0f, -1.0f,  1.0f,
			 1.0f, -1.0f, -1.0f,
			 1.0f, -1.0f, -1.0f,
			-1.0f, -1.0f,  1.0f,
			 1.0f, -1.0f,  1.0f
	};
	
	private static IntBuffer vao;
	private static IntBuffer vbo;
	
	private static Material material;
	
	static
	{
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(VERTICES.length);
		
		for(int i = 0; i < VERTICES.length; i += 3)
		{
			vertexBuffer.put(VERTICES[i]);
			vertexBuffer.put(VERTICES[i + 1]);
			vertexBuffer.put(VERTICES[i + 2]);
		}
		
		vertexBuffer.flip();
		
		vao = BufferUtils.createIntBuffer(1);
		vbo = BufferUtils.createIntBuffer(1);
		
		glGenVertexArrays(vao);
		glGenBuffers(vbo);
		
		glBindVertexArray(vao.get(0));
		glBindBuffer(GL_ARRAY_BUFFER, vbo.get(0));
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glBindVertexArray(0);
		
		
		material = new Material("torch_internal/skybox.glsl");
	}
	
	private Skybox()
	{
		throw new AssertionError();
	}
	
	public static void render()
	{
		glDepthFunc(GL_LEQUAL);
		
		ShaderInternal.bind(material.getShader());
		
		Matrix4f viewMatrix = Camera.getCurrentCamera().getViewMatrix();
		viewMatrix = new Matrix4f(new Matrix3f(viewMatrix));
		
		material.setMatrix4f("_view", viewMatrix);
		material.setMatrix4f("_projection", Camera.getCurrentCamera().getProjection());
		
		MaterialInternal.update(material);
		
		glBindVertexArray(vao.get(0));
		glDrawArrays(GL_TRIANGLES, 0, 36);
		glBindVertexArray(0);
		
		ShaderInternal.unbind();
		
		glDepthFunc(GL_LESS);
	}
	
	public static void setTexture(Cubemap texture)
	{
		material.setTexture("skybox", texture);
	}
	
	public static Cubemap getTexture()
	{
		return (Cubemap)material.getTexture("skybox");
	}
}
