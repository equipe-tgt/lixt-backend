package br.com.ifsp.pi.lixt.utils.views.formnewpassword;

import br.com.ifsp.pi.lixt.utils.mail.templates.Languages;
import br.com.ifsp.pi.lixt.utils.views.activeaccount.ActiveAccountViewTranslators;

public abstract class FormNewPasswordView {

    private FormNewPasswordView() {}

    public static String getView(Languages language) {
        return formatBodyMessageHtml(language);
    }

    private static String formatBodyMessageHtml(Languages language) {
        String view = FormNewPasswordViewHtml.getView();

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
