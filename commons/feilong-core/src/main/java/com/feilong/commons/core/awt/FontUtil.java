/*
 * Copyright (C) 2008 feilong (venusdrogon@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.feilong.commons.core.awt;

import java.awt.Font;

import com.feilong.commons.core.enumeration.FontType;

/**
 * 字体类.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2010-1-21 上午10:08:47
 * @since 1.0.0
 * @deprecated 将来可能会更改名称或者写法
 */
@Deprecated
public final class FontUtil{

	/** 9 正常 雅黑 . */
	public final static Font	YAHEI_PLAIN_9	= getFont_YaHei_Plain(9);

	/** 12正常 雅黑. */
	public final static Font	YAHEI_PLAIN_12	= getFont_YaHei_Plain(12);

	/** 16 正常 楷体字体. */
	public final static Font	KAITI_PLAIN_16	= getFont(FontType.KAITI, 16);

	/** 12 正常 宋体字体. */
	public final static Font	SONG_PLAIN_12	= getFont(FontType.SONGTI, 12);

	/** 9 正常 宋体字体. */
	public final static Font	SONG_PLAIN_9	= getFont(FontType.SONGTI, 9);

	/**
	 * 获得courier_New字体(默认 Font.PLAIN)
	 * 
	 * @param size
	 *            the size
	 * @return the font_courier_ new_ plain
	 */
	public final static Font getFont_courier_New_Plain(int size){
		return getFont(FontType.COURIER_NEW, size);
	}

	/**
	 * 获得verdana字体(默认 Font.PLAIN)
	 * 
	 * @param size
	 *            the size
	 * @return the font_verdana_ plain
	 */
	public final static Font getFont_verdana_Plain(int size){
		return getFont(FontType.VERDANA, size);
	}

	/**
	 * 获得微软雅黑字体(默认 Font.PLAIN)
	 * 
	 * @param size
	 *            the size
	 * @return the font_ ya hei_ plain
	 */
	public final static Font getFont_YaHei_Plain(int size){
		return getFont(FontType.YAHEI, size);
	}

	/**
	 * 获得字体(默认 Font.PLAIN)
	 * 
	 * @param fontName
	 *            字体名称。可以是字体外观名称或字体系列名称，并可表示此 GraphicsEnvironment 中找到的逻辑字体或物理字体。 <li>逻辑字体的系列名称有：Dialog、DialogInput、Monospaced、Serif 或
	 *            SansSerif。</li> 如果 name 为 null，则将新 Font 的 逻辑字体名称（由 getName() 返回）设置为 "Default"。
	 * @param size
	 *            the size
	 * @return 字体
	 */
	public final static Font getFont(String fontName,int size){
		// 普通样式常量
		return getFont(fontName, Font.PLAIN, size);
	}

	/**
	 * 获得字体.
	 * 
	 * @param fontName
	 *            字体名称。可以是字体外观名称或字体系列名称，并可表示此 GraphicsEnvironment 中找到的逻辑字体或物理字体。 <li>逻辑字体的系列名称有：Dialog、DialogInput、Monospaced、Serif 或
	 *            SansSerif。</li> 如果 name 为 null，则将新 Font 的 逻辑字体名称（由 getName() 返回）设置为 "Default"。
	 * @param fontStyle
	 *            字体样式 粗体BOLD 斜体ITALIC 普通样式常量PLAIN <li>Font 的样式常量。样式参数是整数位掩码，可以为 PLAIN，或 BOLD 和 ITALIC 的按位或（例如，ITALIC 或 BOLD|ITALIC）。</li>
	 *            <li>如果样式参数不符合任何一个期望的整数位掩码，则将样式设置为 PLAIN。</li>
	 * @param fontSize
	 *            字体磅值大小 9pt=12px
	 * @return 字体
	 */
	public final static Font getFont(String fontName,int fontStyle,int fontSize){
		return new Font(fontName, fontStyle, fontSize);
	}
}