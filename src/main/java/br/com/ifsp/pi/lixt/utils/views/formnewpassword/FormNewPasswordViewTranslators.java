package br.com.ifsp.pi.lixt.utils.views.formnewpassword;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class FormNewPasswordViewTranslators {

    public static Map<String, String> translateToPortuguese() {
        Map<String, String> params = new HashMap<>();

        params.put("$TITLE", "Definir nova senha");
        params.put("$PASSWORD-FIELD", "Nova senha*");
        params.put("$CONFIRM-PASSWORD-FIELD", "Confirme a senha*");
        params.put("$SUBMIT", "Enviar");
        params.put("$ERROR-MESSAGE-FIELDS-DID-NOT-MATCH", "**As senhas não são iguais");
        params.put("$ERROR-MESSAGE-INVALID-LENGTH", "**A senha deve ter no mínimo 8 caracteres e no máximo 20");
        params.put("$SUCESS-MASSAGE-SEND", "Senha criada com sucesso!");

        return params;
    }

    public static Map<String, String> translateToEnglish() {
        Map<String, String> params = new HashMap<>();

        params.put("$TITLE", "Set New Password");
        params.put("$PASSWORD-FIELD", "Create Password*");
        params.put("$CONFIRM-PASSWORD-FIELD", "Confirm Password*");
        params.put("$SUBMIT", "Submit");
        params.put("$ERROR-MESSAGE-FIELDS-DID-NOT-MATCH", "**Passwords are not same");
        params.put("$ERROR-MESSAGE-INVALID-LENGTH", "**Password length must be at least 8 characters and not exceed 20 characters");
        params.put("$SUCESS-MASSAGE-SEND", "Password created successfully!");

        return params;
    }

}
