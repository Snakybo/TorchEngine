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

package com.snakybo.torch.input.keyboad;

/**
 * @author Snakybo
 * @since 1.0
 */
public enum Key
{
	SPACE         (0x20),
	APOSTROPHE    (0x27),
	COMMA         (0x2C),
	MINUS         (0x2D),
	PERIOD        (0x2E),
	SLASH         (0x2F),
	NUM_0         (0x30),
	NUM_1         (0x31),
	NUM_2         (0x32),
	NUM_3         (0x33),
	NUM_4         (0x34),
	NUM_5         (0x35),
	NUM_6         (0x36),
	NUM_7         (0x37),
	NUM_8         (0x38),
	NUM_9         (0x39),
	SEMICOLON     (0x3B),
	EQUAL         (0x3D),
	A             (0x41),
	B             (0x42),
	C             (0x43),
	D             (0x44),
	E             (0x45),
	F             (0x46),
	G             (0x47),
	H             (0x48),
	I             (0x49),
	J             (0x4A),
	K             (0x4B),
	L             (0x4C),
	M             (0x4D),
	N             (0x4E),
	O             (0x4F),
	P             (0x50),
	Q             (0x51),
	R             (0x52),
	S             (0x53),
	T             (0x54),
	U             (0x55),
	V             (0x56),
	W             (0x57),
	X             (0x58),
	Y             (0x59),
	Z             (0x5A),
	LEFT_BRACKET  (0x5B),
	BACKSLASH     (0x5C),
	RIGHT_BRACKET (0x5D),
	GRAVE_ACCENT  (0x60),
	WORLD_1       (0xA1),
	WORLD_2       (0xA2),
	ESCAPE        (0x100),
	ENTER         (0x101),
	TAB           (0x102),
	BACKSPACE     (0x103),
	INSERT        (0x104),
	DELETE        (0x105),
	RIGHT         (0x106),
	LEFT          (0x107),
	DOWN          (0x108),
	UP            (0x109),
	PAGE_UP       (0x10A),
	PAGE_DOWN     (0x10B),
	HOME          (0x10C),
	END           (0x10D),
	CAPS_LOCK     (0x118),
	SCROLL_LOCK   (0x119),
	NUM_LOCK      (0x11A),
	PRINT_SCREEN  (0x11B),
	PAUSE         (0x11C),
	F1            (0x122),
	F2            (0x123),
	F3            (0x124),
	F4            (0x125),
	F5            (0x126),
	F6            (0x127),
	F7            (0x128),
	F8            (0x129),
	F9            (0x12A),
	F10           (0x12B),
	F11           (0x12C),
	F12           (0x12D),
	F13           (0x12E),
	F14           (0x12F),
	F15           (0x130),
	F16           (0x131),
	F17           (0x132),
	F18           (0x133),
	F19           (0x134),
	F20           (0x135),
	F21           (0x136),
	F22           (0x137),
	F23           (0x138),
	F24           (0x139),
	F25           (0x13A),
	KP_0          (0x140),
	KP_1          (0x141),
	KP_2          (0x142),
	KP_3          (0x143),
	KP_4          (0x144),
	KP_5          (0x145),
	KP_6          (0x146),
	KP_7          (0x147),
	KP_8          (0x148),
	KP_9          (0x149),
	KP_DECIMAL    (0x14A),
	KP_DIVIDE     (0x14B),
	KP_MULTIPLY   (0x14C),
	KP_SUBTRACT   (0x14D),
	KP_ADD        (0x14E),
	KP_ENTER      (0x14F),
	KP_EQUAL      (0x150),
	LEFT_SHIFT    (0x154),
	LEFT_CONTROL  (0x155),
	LEFT_ALT      (0x156),
	LEFT_SUPER    (0x157),
	RIGHT_SHIFT   (0x158),
	RIGHT_CONTROL (0x159),
	RIGHT_ALT     (0x15A),
	RIGHT_SUPER   (0x15B),
	MENU          (0x15C);
	
	final int id;
	
	private Key(int key)
	{
		this.id = key;
	}
	
	@Override
	public final String toString()
	{
		switch(this)
		{
		case SPACE:
			return "Space";
		case APOSTROPHE:
			return "Apostrophe";
		case COMMA:
			return "Comma";
		case MINUS:
			return "Minus";
		case PERIOD:
			return "Period";
		case SLASH:
			return "Slash";
		case NUM_0:
			return "0";
		case NUM_1:
			return "1";
		case NUM_2:
			return "2";
		case NUM_3:
			return "3";
		case NUM_4:
			return "4";
		case NUM_5:
			return "5";
		case NUM_6:
			return "6";
		case NUM_7:
			return "7";
		case NUM_8:
			return "8";
		case NUM_9:
			return "9";
		case SEMICOLON:
			return "Semicolon";
		case EQUAL:
			return "Equal";
		case A:
			return "A";
		case B:
			return "B";
		case C:
			return "C";
		case D:
			return "D";
		case E:
			return "E";
		case F:
			return "F";
		case G:
			return "G";
		case H:
			return "H";
		case I:
			return "I";
		case J:
			return "J";
		case K:
			return "K";
		case L:
			return "L";
		case M:
			return "M";
		case N:
			return "N";
		case O:
			return "O";
		case P:
			return "P";
		case Q:
			return "Q";
		case R:
			return "R";
		case S:
			return "S";
		case T:
			return "T";
		case U:
			return "U";
		case V:
			return "V";
		case W:
			return "W";
		case X:
			return "X";
		case Y:
			return "Y";
		case Z:
			return "Z";
		case BACKSLASH:
			return "Backslash";
		case BACKSPACE:
			return "Backspace";
		case CAPS_LOCK:
			return "Caps Lock";
		case DELETE:
			return "Delete";
		case DOWN:
			return "Down";
		case END:
			return "End";
		case ENTER:
			return "Enter";
		case ESCAPE:
			return "Escape";
		case F1:
			return "F1";
		case F10:
			return "F10";
		case F11:
			return "F11";
		case F12:
			return "F12";
		case F13:
			return "F13";
		case F14:
			return "F14";
		case F15:
			return "F15";
		case F16:
			return "F16";
		case F17:
			return "F17";
		case F18:
			return "F18";
		case F19:
			return "F19";
		case F2:
			return "F2";
		case F20:
			return "F20";
		case F21:
			return "F21";
		case F22:
			return "F22";
		case F23:
			return "F23";
		case F24:
			return "F24";
		case F25:
			return "F25";
		case F3:
			return "F3";
		case F4:
			return "F4";
		case F5:
			return "F5";
		case F6:
			return "F6";
		case F7:
			return "F7";
		case F8:
			return "F8";
		case F9:
			return "F9";
		case GRAVE_ACCENT:
			return "Grave Accent";
		case HOME:
			return "Home";
		case INSERT:
			return "Insert";
		case KP_0:
			return "Numpad 0";
		case KP_1:
			return "Numpad 1";
		case KP_2:
			return "Numpad 2";
		case KP_3:
			return "Numpad 3";
		case KP_4:
			return "Numpad 4";
		case KP_5:
			return "Numpad 5";
		case KP_6:
			return "Numpad 6";
		case KP_7:
			return "Numpad 7";
		case KP_8:
			return "Numpad 8";
		case KP_9:
			return "Numpad 9";
		case KP_ADD:
			return "Numpad Add";
		case KP_DECIMAL:
			return "Numpad Decimal";
		case KP_DIVIDE:
			return "Numpad Divide";
		case KP_ENTER:
			return "Numpad Enter";
		case KP_EQUAL:
			return "Numpad Equal";
		case KP_MULTIPLY:
			return "Numpad Multiply";
		case KP_SUBTRACT:
			return "Numpad Subtract";
		case LEFT:
			return "Left Arrow";
		case LEFT_ALT:
			return "Left Alt";
		case LEFT_BRACKET:
			return "Left Bracket";
		case LEFT_CONTROL:
			return "Left Control";
		case LEFT_SHIFT:
			return "Left Shift";
		case LEFT_SUPER:
			return "Left Super";
		case MENU:
			return "Menu";
		case NUM_LOCK:
			return "Num Lock";
		case PAGE_DOWN:
			return "Page Down";
		case PAGE_UP:
			return "Page Up";
		case PAUSE:
			return "Pause";
		case PRINT_SCREEN:
			return "Print Screen";
		case RIGHT:
			return "Right Arrow";
		case RIGHT_ALT:
			return "Right Alt";
		case RIGHT_BRACKET:
			return "Right Bracket";
		case RIGHT_CONTROL:
			return "Right Control";
		case RIGHT_SHIFT:
			return "Right Shift";
		case RIGHT_SUPER:
			return "Right Super";
		case SCROLL_LOCK:
			return "Scroll Lock";
		case TAB:
			return "Tab";
		case UP:
			return "Up Arrow";
		case WORLD_1:
			return "World 1";
		case WORLD_2:
			return "World 2";
		default:
			return "Unknown";
		}
	}
}
