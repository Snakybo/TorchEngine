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

package com.snakybo.torch.object;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.io.Serializable;

/**
 * <p>
 * Holds transform data for {@link GameObject}s.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public final class Transform implements Serializable
{
	GameObject gameObject;
	
	private Vector3f position;
	private Quaternionf rotation;
	private Vector3f scale;
	
	/**
	 * Create a new {@code Transform}.
	 */
	public Transform()
	{
		position = new Vector3f();
		rotation = new Quaternionf();
		scale = new Vector3f(1, 1, 1);
	}
	
	@Override
	public final String toString()
	{
		String position = "Position(" + this.position.x +", " + this.position.y + ", " + this.position.z + ")";
		String rotation = "Rotation(" + this.rotation.x +", " + this.rotation.y + ", " + this.rotation.z + ", " + this.rotation.w + ")";
		String scale = "Scale(" + this.scale.x + ", " + this.scale.y + ", " + this.scale.z + ")";
		
		return gameObject + ": " + position + " " + rotation + " " + scale;
	}
	
	/**
	 * <p>
	 * Translate the transform.
	 * </p>
	 *
	 * @param direction The direction to translate in.
	 */
	public final void translate(Vector3f direction)
	{
		position.sub(direction);
	}
	
	/**
	 * <p>
	 * Rotate the transform.
	 * </p>
	 *
	 * @param axis The axis to rotate on.
	 * @param angle The angle to rotate by.
	 */
	public final void rotate(Vector3f axis, double angle)
	{
		rotate(axis, (float)angle);
	}
	
	/**
	 * <p>
	 * Rotate the transform.
	 * </p>
	 *
	 * @param axis The axis to rotate on.
	 * @param angle The angle to rotate by.
	 */
	public final void rotate(Vector3f axis, float angle)
	{
		rotation.rotateAxis(angle, axis);
	}
	
	/**
	 * <p>
	 * Look at a position.
	 * </p>
	 *
	 * @param position The position in world space to look at.
	 */
	public final void lookAt(Vector3f position)
	{
		lookAt(position, new Vector3f(0, 1, 0));
	}
	
	/**
	 * <p>
	 * Look at a position.
	 * </p>
	 *
	 * @param position The position in world space to look at.
	 * @param up The up direction.
	 */
	public final void lookAt(Vector3f position, Vector3f up)
	{
		Vector3f direction = new Vector3f(position);
		direction.sub(position);
		direction.normalize();
		
		rotation.lookRotate(direction, up);
	}
	
	/**
	 * <p>
	 * Set the position of the {@code Transform} in world-space.
	 * </p>
	 *
	 * @param position The new position.
	 */
	public final void setPosition(Vector3f position)
	{
		this.position = position;
	}
	
	/**
	 * <p>
	 * Set the rotation of the {@code Transform} in world-space.
	 * </p>
	 *
	 * @param rotation The new rotation.
	 */
	public final void setRotation(Quaternionf rotation)
	{
		this.rotation = rotation;
	}
	
	/**
	 * <p>
	 * Set the local scale of the {@code Transform}.
	 * </p>
	 *
	 * @param scale The new scale.
	 */
	public final void setScale(Vector3f scale)
	{
		this.scale = scale;
	}
	
	/**
	 * <p>
	 * Get the transformation matrix.
	 * </p>
	 *
	 * @return The transformation matrix.
	 */
	public final Matrix4f getTransformation()
	{
		return new Matrix4f().translate(-position.x, -position.y, -position.z).scale(scale).rotate(rotation);
	}
	
	/**
	 * <p>
	 * Get the attached {@code GameObject}.
	 * </p>
	 *
	 * @return The attached {@code GameObject}
	 */
	public final GameObject getGameObject()
	{
		return gameObject;
	}
	
	/**
	 * <p>
	 * Get the position in world-space.
	 * </p>
	 *
	 * @return The position in world-space.
	 */
	public final Vector3f getPosition()
	{
		return new Vector3f(position);
	}
	
	/**
	 * <p>
	 * Get the rotation in world-space.
	 * </p>
	 *
	 * @return The rotation world space.
	 */
	public final Quaternionf getRotation()
	{
		return new Quaternionf(rotation);
	}
	
	/**
	 * <p>
	 * Get the scale of the {@code Transform}.
	 * </p>
	 *
	 * @return The scale.
	 */
	public final Vector3f getScale()
	{
		return new Vector3f(scale);
	}
	
	/**
	 * <p>
	 * Get the forward direction.
	 * </p>
	 *
	 * @return The forward direction.
	 */
	public final Vector3f forward()
	{
		return rotation.positiveZ(new Vector3f()).negate();
	}
	/**
	 * <p>
	 * Get the backward direction.
	 * </p>
	 *
	 * @return The backward direction.
	 */
	public final Vector3f backward()
	{
		return rotation.positiveZ(new Vector3f());
	}
	
	/**
	 * <p>
	 * Get the left direction.
	 * </p>
	 *
	 * @return The left direction.
	 */
	public final Vector3f left()
	{
		return rotation.positiveX(new Vector3f()).negate();
	}
	
	/**
	 * <p>
	 * Get the right direction.
	 * </p>
	 *
	 * @return The right direction.
	 */
	public final Vector3f right()
	{
		return rotation.positiveX(new Vector3f());
	}
	
	/**
	 * <p>
	 * Get the up direction.
	 * </p>
	 *
	 * @return The up direction.
	 */
	public final Vector3f up()
	{
		return rotation.positiveY(new Vector3f());
	}
	
	/**
	 * <p>
	 * Get the down direction.
	 * </p>
	 *
	 * @return The down direction.
	 */
	public final Vector3f down()
	{
		return rotation.positiveY(new Vector3f()).negate();
	}
}
