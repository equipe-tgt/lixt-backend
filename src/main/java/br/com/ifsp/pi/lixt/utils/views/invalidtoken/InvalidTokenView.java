package br.com.ifsp.pi.lixt.utils.views.invalidtoken;

import br.com.ifsp.pi.lixt.utils.mail.templates.Languages;
import br.com.ifsp.pi.lixt.utils.views.AbstractView;

public abstract class InvalidTokenView extends AbstractView {
	
	private InvalidTokenView() {}
	
	public static String getView(Languages language) {
		return formatBodyMessageHtml(language);
	}

	private static String formatBodyMessageHtml(Languages language) {
		String view = InvalidTokenViewHtml.getView();
		
		switch(language) {

			case PORTUGUESE:
				return translate(view, InvalidTokenViewTranslators.toPortuguese());
			
			case ENGLISH:
			default:
				return translate(view, InvalidTokenViewTranslators.toEnglish());
		}
	}
	
}
