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

package com.snakybo.torch.engine.graphics.glfw.vulkan;

import com.snakybo.torch.engine.Application;
import com.snakybo.torch.engine.TorchEngine;
import com.snakybo.torch.engine.debug.Debug;
import com.snakybo.torch.engine.debug.logging.LogType;
import org.lwjgl.PointerBuffer;
import org.lwjgl.vulkan.VkApplicationInfo;
import org.lwjgl.vulkan.VkDevice;
import org.lwjgl.vulkan.VkDeviceCreateInfo;
import org.lwjgl.vulkan.VkDeviceQueueCreateInfo;
import org.lwjgl.vulkan.VkInstance;
import org.lwjgl.vulkan.VkInstanceCreateInfo;
import org.lwjgl.vulkan.VkPhysicalDevice;
import org.lwjgl.vulkan.VkPhysicalDeviceProperties;
import org.lwjgl.vulkan.VkQueueFamilyProperties;
import org.lwjgl.vulkan.VkSurfaceFormatKHR;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

import static com.snakybo.torch.engine.graphics.glfw.vulkan.VulkanUtil.checkError;
import static com.snakybo.torch.engine.graphics.glfw.vulkan.VulkanUtil.deviceTypeToString;
import static com.snakybo.torch.engine.graphics.glfw.vulkan.VulkanUtil.getApiVersion;
import static org.lwjgl.glfw.GLFW.GLFW_CLIENT_API;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_NO_API;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFWVulkan.glfwCreateWindowSurface;
import static org.lwjgl.glfw.GLFWVulkan.glfwGetRequiredInstanceExtensions;
import static org.lwjgl.glfw.GLFWVulkan.glfwVulkanSupported;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memAllocFloat;
import static org.lwjgl.system.MemoryUtil.memAllocInt;
import static org.lwjgl.system.MemoryUtil.memAllocLong;
import static org.lwjgl.system.MemoryUtil.memAllocPointer;
import static org.lwjgl.system.MemoryUtil.memFree;
import static org.lwjgl.system.MemoryUtil.memUTF8;
import static org.lwjgl.vulkan.EXTDebugReport.VK_EXT_DEBUG_REPORT_EXTENSION_NAME;
import static org.lwjgl.vulkan.KHRSurface.vkDestroySurfaceKHR;
import static org.lwjgl.vulkan.KHRSurface.vkGetPhysicalDeviceSurfaceFormatsKHR;
import static org.lwjgl.vulkan.VK10.VK_API_VERSION_1_0;
import static org.lwjgl.vulkan.VK10.VK_FORMAT_B8G8R8A8_UNORM;
import static org.lwjgl.vulkan.VK10.VK_FORMAT_UNDEFINED;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_APPLICATION_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.vkCreateDevice;
import static org.lwjgl.vulkan.VK10.vkCreateInstance;
import static org.lwjgl.vulkan.VK10.vkDestroyDevice;
import static org.lwjgl.vulkan.VK10.vkDestroyInstance;
import static org.lwjgl.vulkan.VK10.vkEnumeratePhysicalDevices;
import static org.lwjgl.vulkan.VK10.vkGetPhysicalDeviceProperties;
import static org.lwjgl.vulkan.VK10.vkGetPhysicalDeviceQueueFamilyProperties;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class VulkanBackend
{
	static VkQueueFamilyProperties.Buffer vkQueueFamilyProperties;
	static VkPhysicalDevice vkPhysicalDevice;
	static VkInstance vkInstance;
	static VkDevice vkDevice;
	
	static long window;
	static long surface;
	
	static int colorFormat;
	static int colorSpace;
	
	private static VulkanSwapChain swapChain;
	
	private VulkanBackend()
	{
		throw new AssertionError();
	}
	
	public static void initialize()
	{
		if(vkInstance != null)
		{
			throw new IllegalStateException("Vulkan Backend has already been initialized");
		}
		
		Debug.getLogHandler().log(LogType.Internal, "Initializing Vulkan Backend");
		
		if(!glfwInit())
		{
			throw new AssertionError("Unable to initialize GLFW");
		}
		
		if(!glfwVulkanSupported())
		{
			throw new AssertionError("No Vulkan loader found");
		}
		
		createVkInstance();
		createVkPhysicalDevice();
		createVkDevice();
		
		createWindowAndSurface();
		
		swapChain = new VulkanSwapChain();
		
		Debug.getLogHandler().log(LogType.Internal, "Initialized Vulkan Backend");
	}
	
	public static void destroy()
	{
		Debug.getLogHandler().log(LogType.Internal, "Terminating Vulkan Backend");
		
		vkDestroyDevice(vkDevice, null);
		
		vkQueueFamilyProperties.free();
		
		vkDestroySurfaceKHR(vkInstance, surface, null);
		glfwDestroyWindow(window);
		
		vkDestroyInstance(vkInstance, null);
		glfwTerminate();
		
		Debug.getLogHandler().log(LogType.Internal, "Terminated Vulkan Backend");
	}
	
	private static void createVkInstance()
	{
		// Get required extensions
		PointerBuffer requiredInstanceExtensions = glfwGetRequiredInstanceExtensions();
		
		if(requiredInstanceExtensions == null)
		{
			throw new AssertionError("Unable to retrieve required extensions");
		}
		
		PointerBuffer enabledExtensionNames = memAllocPointer(requiredInstanceExtensions.remaining() + 1);
		enabledExtensionNames.put(requiredInstanceExtensions);
		enabledExtensionNames.put(memUTF8(VK_EXT_DEBUG_REPORT_EXTENSION_NAME));
		enabledExtensionNames.flip();
		
		// Vulkan application info
		VkApplicationInfo applicationInfo = VkApplicationInfo.calloc()
				.sType(VK_STRUCTURE_TYPE_APPLICATION_INFO)
				.pNext(NULL)
				.pApplicationName(memUTF8(Application.getName()))
				.pEngineName(memUTF8("TorchEngine"))
				.engineVersion(TorchEngine.VERSION32)
				.apiVersion(VK_API_VERSION_1_0);
		
		// Vulkan instance create info
		// TODO: Enable layers
		// TODO: Enable extensions
		VkInstanceCreateInfo instanceCreateInfo = VkInstanceCreateInfo.calloc()
				.sType(VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO)
				.pNext(NULL)
				.flags(0)
				.pApplicationInfo(applicationInfo)
				.ppEnabledLayerNames(null)
				.ppEnabledExtensionNames(enabledExtensionNames);
		
		// Create Vulkan instance
		PointerBuffer instance = memAllocPointer(1);
		VulkanUtil.checkError(vkCreateInstance(instanceCreateInfo, null, instance));
		
		vkInstance = new VkInstance(instance.get(0), instanceCreateInfo);
		
		// Clean up all allocated resources
		memFree(instance);
		memFree(applicationInfo.pApplicationName());
		memFree(applicationInfo.pEngineName());
		memFree(enabledExtensionNames);
		
		instanceCreateInfo.free();
		applicationInfo.free();
	}
	
	private static void createVkPhysicalDevice()
	{
		// Get number of physical devices present
		IntBuffer physicalDeviceCount = memAllocInt(1);
		checkError(vkEnumeratePhysicalDevices(vkInstance, physicalDeviceCount, null));
		
		// Check if there is at least 1 physical device present that has Vulkan support
		if(physicalDeviceCount.get(0) == 0)
		{
			throw new AssertionError("No physical device with Vulkan support present");
		}
		
		// Get all physical devices and physical device properties
		// TODO: Get all available physical devices
		PointerBuffer physicalDevices = memAllocPointer(physicalDeviceCount.get(0));
		checkError(vkEnumeratePhysicalDevices(vkInstance, physicalDeviceCount, physicalDevices));
		
		Debug.getLogHandler().log(LogType.Internal, "Physical devices with Vulkan support (" + physicalDeviceCount.get(0) + "):");
		vkPhysicalDevice = new VkPhysicalDevice(physicalDevices.get(0), vkInstance);
		
		// Get physical device properties
		VkPhysicalDeviceProperties properties = VkPhysicalDeviceProperties.calloc();
		vkGetPhysicalDeviceProperties(vkPhysicalDevice, properties);
		
		// Log physical device properties
		Debug.getLogHandler().log(LogType.Internal, "\t" + properties.deviceNameString());
		Debug.getLogHandler().log(LogType.Internal, "\t\tDriver version:\t" + properties.driverVersion());
		Debug.getLogHandler().log(LogType.Internal, "\t\tDevice type:\t" + deviceTypeToString(properties.deviceType()));
		Debug.getLogHandler().log(LogType.Internal, "\t\tAPI version:\t" + getApiVersion(properties.apiVersion()));
		
		// Get physical device queue family properties
		IntBuffer queueFamilyCount = memAllocInt(1);
		vkGetPhysicalDeviceQueueFamilyProperties(vkPhysicalDevice, queueFamilyCount, null);
		
		vkQueueFamilyProperties = VkQueueFamilyProperties.calloc(queueFamilyCount.get(0));
		vkGetPhysicalDeviceQueueFamilyProperties(vkPhysicalDevice, queueFamilyCount, vkQueueFamilyProperties);
		
		// Clean up all allocated resources
		memFree(physicalDeviceCount);
		memFree(physicalDevices);
		memFree(queueFamilyCount);
		
		properties.free();
	}
	
	private static void createVkDevice()
	{
		VkDeviceQueueCreateInfo.Buffer deviceQueueCreateInfo = VkDeviceQueueCreateInfo.calloc(1)
				.sType(VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO)
				.pNext(NULL)
				.flags(0)
				.queueFamilyIndex(0)
				.pQueuePriorities((FloatBuffer)memAllocFloat(1).put(0f).flip());
		
		// TODO: Enable layers
		// TODO: Enable extensions
		// TODO: Enable features
		VkDeviceCreateInfo deviceCreateInfo = VkDeviceCreateInfo.calloc()
				.sType(VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO)
				.pNext(NULL)
				.ppEnabledExtensionNames(null)
				.ppEnabledLayerNames(null)
				.pEnabledFeatures(null)
				.pQueueCreateInfos(deviceQueueCreateInfo);
		
		PointerBuffer device = memAllocPointer(1);
		checkError(vkCreateDevice(vkPhysicalDevice, deviceCreateInfo, null, device));
		
		vkDevice = new VkDevice(device.get(0), vkPhysicalDevice, deviceCreateInfo);
		
		// Clean up all allocated resources
		memFree(device);
	}
	
	private static void createWindowAndSurface()
	{
		// Create window
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_CLIENT_API, GLFW_NO_API);
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		
		// TODO: Set window title to game name
		window = glfwCreateWindow(800, 600, Application.getName(), NULL, NULL);
		
		if(window == NULL)
		{
			throw new AssertionError("Unable to create GLFW window");
		}
		
		// Create surface
		LongBuffer surfaceBuffer = memAllocLong(1);
		checkError(glfwCreateWindowSurface(vkInstance, window, null, surfaceBuffer));
		surface = surfaceBuffer.get(0);
		
		// Retrieve color format and color space
		IntBuffer surfaceFormatCount = memAllocInt(1);
		checkError(vkGetPhysicalDeviceSurfaceFormatsKHR(vkPhysicalDevice, surface, surfaceFormatCount, null));
		
		VkSurfaceFormatKHR.Buffer surfaceFormats = VkSurfaceFormatKHR.calloc(surfaceFormatCount.get(0));
		checkError(vkGetPhysicalDeviceSurfaceFormatsKHR(vkPhysicalDevice, surface, surfaceFormatCount, surfaceFormats));
		
		if(surfaceFormatCount.get(0) == 1 && surfaceFormats.get(0).format() == VK_FORMAT_UNDEFINED)
		{
			colorFormat = VK_FORMAT_B8G8R8A8_UNORM;
		}
		else
		{
			if(surfaceFormatCount.get(0) < 1)
			{
				throw new AssertionError("No available surface formats found");
			}
			
			colorFormat = surfaceFormats.get(0).format();
		}
		
		colorSpace = surfaceFormats.get(0).colorSpace();
		
		// Clean up all allocated resources
		memFree(surfaceBuffer);
		memFree(surfaceFormatCount);
		
		surfaceFormats.free();
	}
}
