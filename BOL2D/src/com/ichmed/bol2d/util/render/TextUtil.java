package com.ichmed.bol2d.util.render;

public class TextUtil
{
	public static void drawText(String text, String font, float startX, float startY, float size)
	{
		float posX = 0, posY = 0;
		for (int i = 0; i < text.length(); i++)
		{
			char c = text.toCharArray()[i];
			if (c == '\n')
			{
				posX = 0;
				posY -= size * 1.3f;
			} else
			{
				String name = "letter_" + font + "_" + getNameForChar(c);
				// pos += getWidthForChar(c);
				RenderUtil.drawTexturedRect(startX + posX * size, startY + posY, size, size, name);
				posX += getWidthForChar(c);
			}
		}

	}

	public static float getWidthForChar(char c)
	{
		switch (c)
		{
		case 'I':
			return 0.3f;
		case 'l':
			return 0.3f;
		case 'i':
			return 0.3f;
		case 'j':
			return 0.3f;
		case ' ':
			return 0.2f;
		default:
			return 0.5f;
		}
	}

	public static String getNameForChar(char c)
	{
		switch (c)
		{
		case '?':
			return "questionmark";
		case '!':
			return "exclamationmark";
		case ':':
			return "colon";
		case ';':
			return "semicolon";
		case '\\':
			return "backslash";
		case '/':
			return "forwardslash";
		case '^':
			return "circumflex";
		case '_':
			return "underscore";
		case '.':
			return "point";
		case ',':
			return "comma";
		case '-':
			return "dash";
		case '+':
			return "plus";
		case '#':
			return "pound";
		case '*':
			return "asterisk";
		case '\"':
			return "doublequotes";
		case '\'':
			return "quote";
		case '§':
			return "section";
		case '$':
			return "dollar";
		case '%':
			return "percent";
		case '&':
			return "ampersand";
		case '(':
			return "bracket_A_open";
		case ')':
			return "bracket_A_closed";
		case '{':
			return "bracket_B_open";
		case '}':
			return "bracket_B_closed";
		case '[':
			return "bracket_C_open";
		case ']':
			return "bracket_C_closed";
		case '<':
			return "bracket_D_open";
		case '>':
			return "bracket_D_closed";
		case '|':
			return "pipe";
		case '=':
			return "equals";
		case ' ':
			return "space";
		case '~':
			return "tilde";
		case '@':
			return "at";
		default:
			return "" + c;
		}
	}

}
