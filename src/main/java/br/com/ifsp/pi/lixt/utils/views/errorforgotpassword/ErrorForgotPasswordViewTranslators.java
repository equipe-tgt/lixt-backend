package br.com.ifsp.pi.lixt.utils.views.errorforgotpassword;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ErrorForgotPasswordViewTranslators {

    public static Map<String, String> toPortuguese() {
        Map<String, String> params = new HashMap<>();

        params.put("$TITLE", "Erro ao redefinir senha!");
        params.put("$RESPONSE", "Possivelmente o link expirou, entre novamente no aplicativo e solicite a redefinição de senha.");

        return params;
    }

    public static Map<String, String> toEnglish() {
        Map<String, String> params = new HashMap<>();

        params.put("$TITLE", "Error resetting password!");
        params.put("$RESPONSE", "Possibly the link has expired, log back into the application and request password reset.");

        return params;
    }

}
