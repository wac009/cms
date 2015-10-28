package com.cc.common.checkcode;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.GradientBackgroundGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.ImageCaptcha;
import com.octo.captcha.image.ImageCaptchaFactory;
import com.octo.captcha.image.gimpy.GimpyFactory;

@SuppressWarnings({ "unchecked", "unused","rawtypes" })
public class CaptchaEngineEx extends ListImageCaptchaEngine {
	private static final Logger		log					= LoggerFactory.getLogger(CaptchaEngineEx.class);
	private static final Integer	MIN_WORD_LENGTH		= new Integer(4);
	private static final Integer	MAX_WORD_LENGTH		= new Integer(5);
	private static final Integer	IMAGE_WIDTH			= new Integer(100);
	private static final Integer	IMAGE_HEIGHT		= new Integer(34);
	private static final Integer	MIN_FONT_SIZE		= new Integer(18);
	private static final Integer	MAX_FONT_SIZE		= new Integer(19);
	private static final String		NUMERIC_CHARS		= "234578";
	private static final String		UPPER_ASCII_CHARS	= "ABDEFHJKLMNPRSTXYZ";
	private static final String		LOWER_ASCII_CHARS	= "abdefhjkmnprstxyz";
	private static CaptchaEngineEx	instance			= new CaptchaEngineEx();
	private ArrayList				textPasterList;
	private ArrayList				backgroundGeneratorList;
	private ArrayList				fontGeneratorList;
	ImageCaptcha					imageCaptcha		= null;

	private CaptchaEngineEx() {}

	public static CaptchaEngineEx getInstance() {
		return instance;
	}

	@Override
	protected void buildInitialFactories() {
		try {
			textPasterList = new ArrayList();
			backgroundGeneratorList = new ArrayList();
			fontGeneratorList = new ArrayList();
			// 文字显示的个数
			textPasterList.add(new RandomTextPaster(MIN_WORD_LENGTH, MAX_WORD_LENGTH, Color.black));
			// 图片的大小
			backgroundGeneratorList.add(new GradientBackgroundGenerator(IMAGE_WIDTH, IMAGE_HEIGHT, new Color(Integer.parseInt("#DCDCDC".substring(1),
					16)), new Color(Integer.parseInt("#DCDCDC".substring(1), 16))));
			// 文字的大小
			fontGeneratorList.add(new RandomFontGenerator(MIN_FONT_SIZE, MAX_FONT_SIZE));// to easy to read
			// 随机生成的字符
			WordGenerator words = new RandomWordGenerator(NUMERIC_CHARS + LOWER_ASCII_CHARS);
			for (Iterator fontIter = fontGeneratorList.iterator(); fontIter.hasNext();) {
				FontGenerator font = (FontGenerator) fontIter.next();
				for (Iterator backIter = backgroundGeneratorList.iterator(); backIter.hasNext();) {
					BackgroundGenerator back = (BackgroundGenerator) backIter.next();
					for (Iterator textIter = textPasterList.iterator(); textIter.hasNext();) {
						TextPaster parser = (TextPaster) textIter.next();
						WordToImage word2image = new ComposedWordToImage(font, back, parser);
						ImageCaptchaFactory factory = new GimpyFactory(words, word2image);
						addFactory(factory);
					}
				}
			}
		} catch (Exception ex) {
			log.error("产生校验码异常 ＝ " + ex);
		}
	}
}
