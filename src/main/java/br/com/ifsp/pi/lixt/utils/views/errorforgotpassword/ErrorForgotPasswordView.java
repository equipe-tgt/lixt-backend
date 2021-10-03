package br.com.ifsp.pi.lixt.utils.views.errorforgotpassword;


import br.com.ifsp.pi.lixt.utils.mail.templates.Languages;

public abstract class ErrorForgotPasswordView {

    private ErrorForgotPasswordView() {}

    public static String getView(Languages language) {
        return formatBodyMessageHtml(language);
    }

    private static String formatBodyMessageHtml(Languages language) {
        String view = ErrorForgotPasswordViewHtml.getView();

        switch(language) {

            case PORTUGUESE:

                for(String key : ErrorForgotPasswordViewTranslators.translateToPortuguese().keySet())
                    view = view.replace(key, ErrorForgotPasswordViewTranslators.translateToPortuguese().get(key));

                return view;

            case ENGLISH:
            default:
                for(String key : ErrorForgotPasswordViewTranslators.translateToEnglish().keySet())
                    view = view.replace(key, ErrorForgotPasswordViewTranslators.translateToEnglish().get(key));

                return view;
        }
    }

}
