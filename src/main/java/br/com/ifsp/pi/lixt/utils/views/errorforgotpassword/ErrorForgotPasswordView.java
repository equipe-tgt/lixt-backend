package br.com.ifsp.pi.lixt.utils.views.errorforgotpassword;

import br.com.ifsp.pi.lixt.utils.mail.templates.Languages;
import br.com.ifsp.pi.lixt.utils.views.AbstractView;

public abstract class ErrorForgotPasswordView extends AbstractView {

    private ErrorForgotPasswordView() {}

    public static String getView(Languages language) {
        return formatBodyMessageHtml(language);
    }

    private static String formatBodyMessageHtml(Languages language) {
        String view = ErrorForgotPasswordViewHtml.getView();

        switch(language) {

            case PORTUGUESE:
            	return translate(view, ErrorForgotPasswordViewTranslators.toPortuguese());

            case ENGLISH:
            default:
            	return translate(view, ErrorForgotPasswordViewTranslators.toEnglish());
        }
    }

}
