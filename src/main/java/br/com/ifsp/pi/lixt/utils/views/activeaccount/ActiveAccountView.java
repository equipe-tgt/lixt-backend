package br.com.ifsp.pi.lixt.utils.views.activeaccount;

import br.com.ifsp.pi.lixt.utils.mail.templates.Languages;

public abstract class ActiveAccountView {
	
	private ActiveAccountView() {}
	
	public static String getView(Languages language) {
		return formatBodyMessageHtml(language);
	}

	private static String formatBodyMessageHtml(Languages language) {
		String view = ActiveAccountViewHtml.getView();
		
		switch(language) {

			case PORTUGUESE:
				
				for(String key : ActiveAccountViewTranslators.toPortuguese().keySet())
					view = view.replace(key, ActiveAccountViewTranslators.toPortuguese().get(key));
				
				return view;
			
			case ENGLISH:
			default:
				for(String key : ActiveAccountViewTranslators.toEnglish().keySet())
					view = view.replace(key, ActiveAccountViewTranslators.toEnglish().get(key));
				
				return view;
		}
	}
	
}
