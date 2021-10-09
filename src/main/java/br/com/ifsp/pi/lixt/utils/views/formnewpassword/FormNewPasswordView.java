package br.com.ifsp.pi.lixt.utils.views.formnewpassword;

import br.com.ifsp.pi.lixt.utils.mail.templates.Languages;

public abstract class FormNewPasswordView {

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

                for(String key : FormNewPasswordViewTranslators.translateToPortuguese().keySet())
                    view = view.replace(key, FormNewPasswordViewTranslators.translateToPortuguese().get(key));

                return view;

            case ENGLISH:
            default:
                for(String key : FormNewPasswordViewTranslators.translateToEnglish().keySet())
                    view = view.replace(key, FormNewPasswordViewTranslators.translateToEnglish().get(key));

                return view;
        }
    }
}
