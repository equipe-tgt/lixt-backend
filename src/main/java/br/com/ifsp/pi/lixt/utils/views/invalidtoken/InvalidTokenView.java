package br.com.ifsp.pi.lixt.utils.views.invalidtoken;

import br.com.ifsp.pi.lixt.utils.mail.templates.Languages;

public abstract class InvalidTokenView {
	
	private InvalidTokenView() {}
	
	public static String getView(Languages language) {
		return formatBodyMessageHtml(language);
	}

	private static String formatBodyMessageHtml(Languages language) {
		String view = InvalidTokenViewHtml.getView();
		
		switch(language) {

			case PORTUGUESE:

				for(String key : InvalidTokenViewTranslators.translateToPortuguese().keySet())
					view = view.replace(key, InvalidTokenViewTranslators.translateToPortuguese().get(key));
				
				return view;
			
			case ENGLISH:
			default:

				for(String key : InvalidTokenViewTranslators.translateToEnglish().keySet())
					view = view.replace(key, InvalidTokenViewTranslators.translateToEnglish().get(key));
				
				return view;
		}
	}
	
}
