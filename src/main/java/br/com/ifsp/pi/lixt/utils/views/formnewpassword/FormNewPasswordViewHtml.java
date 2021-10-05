package br.com.ifsp.pi.lixt.utils.views.formnewpassword;

public abstract class FormNewPasswordViewHtml {

    private FormNewPasswordViewHtml() {}

    public static String getView() {
        return "<!DOCTYPE html>\r\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\r\n" +
                "\r\n" +
                "    <head>\r\n" +
                "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" +
                "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n" +
                "        <meta name=\"format-detection\" content=\"date=no\"> \r\n" +
                "        <meta name=\"format-detection\" content=\"telephone=no\">\r\n" +
                "        <title>Lixt</title>\r\n" +
                "\r\n" +
                "        <link href=\"https://fonts.googleapis.com/css2?family=Archivo:wght@400;700&family=Poppins&display=swap\" rel=\"stylesheet\">\r\n" +
                "\r\n" +
                "        <style type=\"text/css\">\r\n" +
                "\r\n" +
                "            * {\r\n" +
                "                margin: 0 !important;\r\n" +
                "                padding: 0 !important;\r\n" +
                "                box-sizing: border-box !important;\r\n" +
                "            }\r\n" +
                "\r\n" +
                "            body {\r\n" +
                "                margin: 0;\r\n" +
                "                background-color: #F0F0F7 !important;\r\n" +
                "            }\r\n" +
                "\r\n" +
                "            h1 {\r\n" +
                "                font-family: Helvetica, Arial, sans-serif !important;\r\n" +
                "                font-weight: 800 !important;\r\n" +
                "                font-size: 2rem !important;\r\n" +
                "                line-height: 2.25rem !important;\r\n" +
                "                color: #32264D !important;\r\n" +
                "\r\n" +
                "                order: inherit;\r\n" +
                "            }\r\n" +
                "\r\n" +
                "            p {\r\n" +
                "                font-family: Helvetica, Arial, sans-serif !important;\r\n" +
                "                font-style: normal !important;\r\n" +
                "                font-weight: normal !important;\r\n" +
                "                font-size: 16px !important;\r\n" +
                "                line-height: 1.5rem !important;\r\n" +
                "                color: #32264D !important;\r\n" +
                "            }\r\n" +
                "\r\n" +
                "            #new-password {\r\n" +
                "                margin: 1rem !important;\r\n" +
                "                padding-top: 1rem !important;\r\n" +
                "            }\r\n" +
                "\r\n" +
                "            #new-password .header {\r\n" +
                "                background-color: #D3D3D3;\r\n" +
                "                padding: 1rem 0 !important;\r\n" +
                "                \r\n" +
                "            }\r\n" +
                "\r\n" +
                "            #new-password .body {\r\n" +
                "                padding: 1rem !important;\r\n" +
                "                background: white !important;\r\n" +
                "            }\r\n" +
                "\r\n" +
                "        </style>\r\n" +
                "    </head>\r\n" +
                "\r\n" +
                "    <body>\r\n" +
                "        <div id=\"new-password\">\r\n" +
                "\r\n" +
                "            <div class=\"header\">\r\n" +
                "                <h1><center>$TITLE</center></h1>\r\n" +
                "            </div>\r\n" +
                "\r\n" +
                "            <div class=\"body\">\r\n" +
                "\r\n" +
                "            <label for=\"password\">$PASSWORD-FIELD</label>\r\n" +
                "            <input id=\"pswd1\" type=\"password\" name=\"password\" >\r\n" +
                "            <br/><br/>\r\n" +
                "\r\n" +
                "            <label for=\"password-confirm\">$CONFIRM-PASSWORD-FIELD</label>\r\n" +
                "            <input id=\"pswd2\" type=\"password\" name=\"password-confirm\" >\r\n" +
                "            <br/><br/>" +
                "\r\n" +
                "            <button onClick=\"validateForm()\">$SUBMIT</button>" +
                "\r\n" +
                "            </div>\r\n" +
                "            \r\n" +
                "        </div>\r\n" +
                "        <script>\r\n" +
                "            function validateForm() {\r\n" +
                "                var pw1 = document.getElementById(\"pswd1\").value;\r\n" +
                "                var pw2 = document.getElementById(\"pswd2\").value;\r\n" +
                "\r\n" +
                "                if(pw1 != pw2) {\r\n" +
                "                    alert(\"$ERROR-MESSAGE-FIELDS-DID-NOT-MATCH\");\r\n" +
                "                    return false;\r\n" +
                "                }\r\n" +
                "                if(pw1.length < 8 || pw1.length > 20) {\r\n" +
                "                    alert(\"$ERROR-MESSAGE-INVALID-LENGTH\");\r\n" +
                "                    return false;\r\n" +
                "                } else {\r\n" +
                "                    sendForm(pw1);\r\n" +
                "                    return false;" +
                "                }\r\n" +
                "             }\r\n" +
                "\r\n" +
                "             function sendForm(passwordValue) {\r\n" +
                "                 var form;\r\n" +
                "                 form = document.createElement(\"form\");\r\n" +
                "                 form.method = \"POST\";\r\n" +
                "                 form.action = \"$BASE_URL/auth/form/update-password?token=$TOKEN&language=$LANGUAGE\"\r\n" +
                "                 const hiddenField = document.createElement('input');\r\n" +
                "                 hiddenField.type = 'hidden';\r\n" +
                "                 hiddenField.name = 'newPassword';\r\n" +
                "                 hiddenField.value = passwordValue;\r\n" +
                "                 form.appendChild(hiddenField);\r\n" +
                "\r\n" +
                "                 document.body.appendChild(form);\r\n" +
                "                 form.submit();\r\n" +
                "             }\r\n" +
                "        </script>\r\n" +
                "    </body>\r\n" +
                "\r\n" +
                "</html>\r\n" +
                "";
    }
}
