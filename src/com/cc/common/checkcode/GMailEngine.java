
package com.cc.common.checkcode;

import java.awt.*;
import java.awt.image.*;

import com.octo.captcha.component.image.backgroundgenerator.*;
import com.octo.captcha.component.image.color.*;
import com.octo.captcha.component.image.deformation.*;
import com.octo.captcha.component.image.fontgenerator.*;
import com.octo.captcha.component.image.textpaster.*;
import com.octo.captcha.component.image.textpaster.textdecorator.*;
import com.octo.captcha.component.image.wordtoimage.*;
import com.octo.captcha.component.word.wordgenerator.*;
import com.octo.captcha.engine.image.*;
import com.octo.captcha.image.gimpy.*;

/**
 * 仿照JCaptcha2.0编写GMail验证码样式的图片引擎.
 * 
 * @author wangcheng
 */
public class GMailEngine extends ListImageCaptchaEngine {

	@Override
	protected void buildInitialFactories() {
		int minWordLength = 4;
		int maxWordLength = 4;
		int fontSize = 24;
		int imageWidth = 100;
		int imageHeight = 40;
		// word generator
		// WordGenerator dictionnaryWords = new ComposeDictionaryWordGenerator(new
		// FileDictionary("toddlist"));
		WordGenerator wordGenerator = new RandomWordGenerator("023456789");
		// word2image components
		TextPaster randomPaster = new DecoratedRandomTextPaster(minWordLength, maxWordLength, new RandomListColorGenerator(new Color[] { new Color(23, 170, 27), new Color(220, 34, 11), new Color(23, 67, 172) }), new TextDecorator[] {});
		BackgroundGenerator background = new UniColorBackgroundGenerator(imageWidth, imageHeight, Color.white);
		FontGenerator font = new RandomFontGenerator(fontSize, fontSize, new Font[] { new Font("nyala", Font.BOLD, fontSize), new Font("Bell MT", Font.PLAIN, fontSize), new Font("Credit valley", Font.BOLD, fontSize) });
		ImageDeformation postDef = new ImageDeformationByFilters(new ImageFilter[] {});
		ImageDeformation backDef = new ImageDeformationByFilters(new ImageFilter[] {});
		ImageDeformation textDef = new ImageDeformationByFilters(new ImageFilter[] {});
		WordToImage word2image = new DeformedComposedWordToImage(font, background, randomPaster, backDef, textDef, postDef);
		addFactory(new GimpyFactory(wordGenerator, word2image));
	}
}
