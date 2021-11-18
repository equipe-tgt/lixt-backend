package br.com.ifsp.pi.lixt.utils.views.formnewpassword;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class FormNewPasswordViewTranslators {

    public static Map<String, String> toPortuguese() {
        Map<String, String> params = new HashMap<>();

        params.put("$TITLE", "Definir nova senha");
        params.put("$PASSWORD-FIELD", "Nova senha*");
        params.put("$CONFIRM-PASSWORD-FIELD", "Confirme a senha*");
        params.put("$SUBMIT", "Enviar");
        params.put("$ERROR-MESSAGE-FIELDS-DID-NOT-MATCH", "**As senhas não são iguais");
        params.put("$ERROR-MESSAGE-INVALID-LENGTH", "**A senha deve ter no mínimo 8 caracteres e no máximo 20");

        return params;
    }

    public static Map<String, String> toEnglish() {
        Map<String, String> params = new HashMap<>();

        params.put("$TITLE", "Set New Password");
        params.put("$PASSWORD-FIELD", "Create Password*");
        params.put("$CONFIRM-PASSWORD-FIELD", "Confirm Password*");
        params.put("$SUBMIT", "Submit");
        params.put("$ERROR-MESSAGE-FIELDS-DID-NOT-MATCH", "**Passwords are not same");
        params.put("$ERROR-MESSAGE-INVALID-LENGTH", "**Password length must be at least 8 characters and not exceed 20 characters");

        return params;
    }

}
