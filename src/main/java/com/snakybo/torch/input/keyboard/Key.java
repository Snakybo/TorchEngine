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

package com.snakybo.torch.input.keyboard;

/**
 * <p>
 * All available keyboard buttons.
 * </p>
 *
 * <p>
 * This is a direct implementation of the GLFW keys.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public enum Key
{
	/** Key. */
	SPACE         (32),
	
	/** ' Key. */
	APOSTROPHE    (39),
	
	/** , Key. */
	COMMA         (44),
	
	/** - Key. */
	MINUS         (45),
	
	/** . Key. */
	PERIOD        (46),
	
	/** / Key. */
	SLASH         (47),
	
	/** 0 Key. */
	NUM_0         (48),
	
	/** 1 Key. */
	NUM_1         (49),
	
	/** 2 Key. */
	NUM_2         (50),
	
	/** 3 Key. */
	NUM_3         (51),
	
	/** 4 Key. */
	NUM_4         (52),
	
	/** 5 Key. */
	NUM_5         (53),
	
	/** 6 Key. */
	NUM_6         (54),
	
	/** 7 Key. */
	NUM_7         (55),
	
	/** 8 Key. */
	NUM_8         (56),

	/** 9 Key. */
	NUM_9         (57),
	
	/** ; Key. */
	SEMICOLON     (59),
	
	/** = Key. */
	EQUAL         (61),
	
	/** A Key. */
	A             (65),
	
	/** B Key. */
	B             (66),
	
	/** C Key. */
	C             (67),
	
	/** D Key. */
	D             (68),
	
	/** E Key. */
	E             (69),
	
	/** F Key. */
	F             (70),
	
	/** G Key. */
	G             (71),
	
	/** H Key. */
	H             (72),
	
	/** I Key. */
	I             (73),
	
	/** J Key. */
	J             (74),
	
	/** K Key. */
	K             (75),
	
	/** L Key. */
	L             (76),
	
	/** M Key. */
	M             (77),
	
	/** N Key. */
	N             (78),
	
	/** O Key. */
	O             (79),
	
	/** P Key. */
	P             (80),
	
	/** Q Key. */
	Q             (81),
	
	/** R Key. */
	R             (82),
	
	/** S Key. */
	S             (83),
	
	/** T Key. */
	T             (84),
	
	/** U Key. */
	U             (85),
	
	/** V Key. */
	V             (86),
	
	/** W Key. */
	W             (87),
	
	/** X Key. */
	X             (88),
	
	/** Y Key. */
	Y             (89),
	
	/** Z Key. */
	Z             (90),
	
	/** [ Key. */
	LEFT_BRACKET  (91),
	
	/** \ Key. */
	BACKSLASH     (92),
	
	/** ] Key. */
	RIGHT_BRACKET (93),
	
	/** ` Key. */
	GRAVE_ACCENT  (96),
	
	/** Key. */
	WORLD_1       (161),
	
	/** Key. */
	WORLD_2       (162),
	
	/** Key. */
	ESCAPE        (256),
	
	/** Key. */
	ENTER         (257),
	
	/** Key. */
	TAB           (258),
	
	/** Key. */
	BACKSPACE     (259),
	
	/** Key. */
	INSERT        (260),
	
	/** Key. */
	DELETE        (261),
	
	/** Key. */
	RIGHT         (262),
	
	/** Key. */
	LEFT          (263),
	
	/** Key. */
	DOWN          (264),
	
	/** Key. */
	UP            (265),
	
	/** Key. */
	PAGE_UP       (266),
	
	/** Key. */
	PAGE_DOWN     (267),
	
	/** Key. */
	HOME          (268),
	
	/** Key. */
	END           (269),
	
	/** Key. */
	CAPS_LOCK     (280),
	
	/** Key. */
	SCROLL_LOCK   (281),
	
	/** Key. */
	NUM_LOCK      (282),
	
	/** Key. */
	PRINT_SCREEN  (283),
	
	/** Key. */
	PAUSE         (284),
	
	/** Key. */
	F1            (290),
	
	/** Key. */
	F2            (291),
	
	/** Key. */
	F3            (292),
	
	/** Key. */
	F4            (293),
	
	/** Key. */
	F5            (294),
	
	/** Key. */
	F6            (295),
	
	/** Key. */
	F7            (296),
	
	/** Key. */
	F8            (297),
	
	/** Key. */
	F9            (298),
	
	/** Key. */
	F10           (299),
	
	/** Key. */
	F11           (300),
	
	/** Key. */
	F12           (301),
	
	/** Key. */
	F13           (302),
	
	/** Key. */
	F14           (303),
	
	/** Key. */
	F15           (304),
	
	/** Key. */
	F16           (305),
	
	/** Key. */
	F17           (306),
	
	/** Key. */
	F18           (307),
	
	/** Key. */
	F19           (308),
	
	/** Key. */
	F20           (309),
	
	/** Key. */
	F21           (310),
	
	/** Key. */
	F22           (311),
	
	/** Key. */
	F23           (312),
	
	/** Key. */
	F24           (313),
	
	/** Key. */
	F25           (314),
	
	/** Keypad 0 Key. */
	KP_0          (320),
	
	/** Keypad 1 Key. */
	KP_1          (321),
	
	/** Keypad 2 Key. */
	KP_2          (322),
	
	/** Keypad 3 Key. */
	KP_3          (323),
	
	/** Keypad 4 Key. */
	KP_4          (324),
	
	/** Keypad 5 Key. */
	KP_5          (325),
	
	/** Keypad 6 Key. */
	KP_6          (326),
	
	/** Keypad 7 Key. */
	KP_7          (327),
	
	/** Keypad 8 Key. */
	KP_8          (328),
	
	/** Keypad 9 Key. */
	KP_9          (329),
	
	/** Keypad . Key. */
	KP_DECIMAL    (330),
	
	/** Keypad / Key. */
	KP_DIVIDE     (331),
	
	/** Keypad * Key. */
	KP_MULTIPLY   (332),
	
	/** Keypad - Key. */
	KP_SUBTRACT   (333),
	
	/** Keypad + Key. */
	KP_ADD        (334),
	
	/** Keypad Key. */
	KP_ENTER      (335),
	
	/** Keypad = Key. */
	KP_EQUAL      (336),
	
	/** Key. */
	LEFT_SHIFT    (340),
	
	/** Key. */
	LEFT_CONTROL  (341),
	
	/** Key. */
	LEFT_ALT      (342),
	
	/** Key. */
	LEFT_SUPER    (343),
	
	/** Key. */
	RIGHT_SHIFT   (344),
	
	/** Key. */
	RIGHT_CONTROL (345),
	
	/** Key. */
	RIGHT_ALT     (346),
	
	/** Key. */
	RIGHT_SUPER   (347),
	
	/** Key. */
	MENU          (348);
	
	public final int id;
	
	Key(int key)
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
