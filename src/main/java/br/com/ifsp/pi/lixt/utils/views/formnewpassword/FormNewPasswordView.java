package br.com.ifsp.pi.lixt.utils.views.formnewpassword;

import br.com.ifsp.pi.lixt.utils.mail.templates.Languages;
import br.com.ifsp.pi.lixt.utils.views.AbstractView;

public abstract class FormNewPasswordView extends AbstractView {

    private FormNewPasswordView() {}

    public static String getView(Languages language, String token, String baseUrl) {
        return formatBodyMessageHtml(language, token, baseUrl);
    }

    private static String formatBodyMessageHtml(Languages language, String token, String baseUrl) {
        String view = FormNewPasswordViewHtml.getView();

        view = view.replace("$TOKEN", token);
        view = view.replace("$BASE_URL", baseUrl);
        view = view.replace("$LANGUAGE", language.getDescription());

        switch(language) {

            case PORTUGUESE:
            	return translate(view, FormNewPasswordViewTranslators.toPortuguese());

            case ENGLISH:
            default:
            	return translate(view, FormNewPasswordViewTranslators.toEnglish());
        }
    }
}
