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

package com.snakybo.torch.scene;

import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.debug.LoggerInternal;
import com.snakybo.torch.object.GameObjectLoader;
import com.snakybo.torch.util.FileUtils;
import com.snakybo.torch.util.ParserUtil;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.nio.file.NoSuchFileException;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class SceneLoader
{
	private SceneLoader()
	{
		throw new AssertionError();
	}
	
	public static Scene load(String path)
	{
		LoggerInternal.log("Begin loading of scene: " + path);
		
		try
		{
			LoggerInternal.log("Begin parsing of scene data file: " + path);
			Document document = ParserUtil.getDocument(FileUtils.toURI(path));
			
			if(!ParserUtil.isCorrectFile(document, "scene"))
			{
				throw new IllegalArgumentException("Specified file (" + path + ") is not a scene");
			}
			
			Scene oldScene = Scene.getCurrentScene();
			Scene scene = new Scene();
			scene.makeCurrent();
			
			NodeList gameObjectsNodeList = document.getElementsByTagName("game_object");
			GameObjectLoader.parseGameObjectList(gameObjectsNodeList);
			
			if(oldScene != null)
			{
				oldScene.makeCurrent();
			}
			
			return scene;
		}
		catch(NoSuchFileException e)
		{
			Logger.logError(e.getMessage(), e);
		}
		
		return null;
	}
}