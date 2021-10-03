package br.com.ifsp.pi.lixt.utils.views.activeaccount;

import br.com.ifsp.pi.lixt.utils.mail.templates.Languages;
import br.com.ifsp.pi.lixt.utils.views.AbstractView;

public abstract class ActiveAccountView extends AbstractView {
	
	private ActiveAccountView() {}
	
	public static String getView(Languages language) {
		return formatBodyMessageHtml(language);
	}

	private static String formatBodyMessageHtml(Languages language) {
		String view = ActiveAccountViewHtml.getView();
		
		switch(language) {

			case PORTUGUESE:
				return translate(view, ActiveAccountViewTranslators.toPortuguese());
			
			case ENGLISH:
			default:
				return translate(view, ActiveAccountViewTranslators.toEnglish());
		}
	}
	
}
