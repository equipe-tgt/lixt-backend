package br.com.ifsp.pi.lixt.utils.mail.templates.dto;

import br.com.ifsp.pi.lixt.utils.mail.MailDto;

public abstract class ForgetPasswordTemplate {
	
	private ForgetPasswordTemplate() {}
	
	public static MailDto getTemplate() {
		
		return MailDto.builder()
				.title("Lixt - Alteração de Senha")
				.msgHTML("<!DOCTYPE html>\r\n" + 
						"<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\r\n" + 
						"\r\n" + 
						"    <head>\r\n" + 
						"        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n" + 
						"        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
						"        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n" + 
						"        <meta name=\"format-detection\" content=\"date=no\"> \r\n" + 
						"        <meta name=\"format-detection\" content=\"telephone=no\">\r\n" + 
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
						"                font-size: 14px !important;\r\n" + 
						"                line-height: 1.5rem !important;\r\n" + 
						"                color: #32264D !important;\r\n" + 
						"            }\r\n" + 
						"\r\n" + 
						"            #reset-password {\r\n" + 
						"                margin: 1rem !important;\r\n" + 
						"                padding-top: 1rem !important;\r\n" + 
						"            }\r\n" + 
						"\r\n" + 
						"            #reset-password .header {\r\n" + 
						"                background-color: #D3D3D3;\r\n" + 
						"                padding: 1rem 0 !important;\r\n" + 
						"                \r\n" + 
						"            }\r\n" + 
						"\r\n" + 
						"            #reset-password .body {\r\n" + 
						"                padding: 1rem !important;\r\n" + 
						"                background: white !important;\r\n" + 
						"            }\r\n" + 
						"\r\n" + 
						"            #reset-password .body .greeting {\r\n" + 
						"                margin-bottom: 4px !important;\r\n" + 
						"                font-size: 16px !important;\r\n" + 
						"                font-weight: 600 !important;\r\n" + 
						"            }\r\n" + 
						"\r\n" + 
						"            #reset-password .footer {\r\n" + 
						"                padding: 1rem !important;\r\n" + 
						"            }\r\n" + 
						"\r\n" + 
						"            #reset-password .footer p {\r\n" + 
						"                font-family: Helvetica, Arial, sans-serif !important;\r\n" + 
						"                font-size: 12px !important;\r\n" + 
						"                line-height: 16px !important;\r\n" + 
						"                color: #aaaaaa !important;\r\n" + 
						"            }\r\n" + 
						"\r\n" + 
						"        </style>\r\n" + 
						"    </head>\r\n" + 
						"\r\n" + 
						"    <body>\r\n" + 
						"        <div id=\"reset-password\">\r\n" + 
						"\r\n" + 
						"            <div class=\"header\">\r\n" + 
						"                <h1><center>Senha Resetada com Sucesso!</center></h1>\r\n" + 
						"            </div>\r\n" + 
						"\r\n" + 
						"            <div class=\"body\">\r\n" + 
						"                <p class=\"greeting\">Olá, $USERNAME!</p>\r\n" + 
						"                <p>Sua nova senha de acesso do Lixt é: <b>$PASSWORD</b>.</p>\r\n" + 
						"                <p>É altamente recomendado que a altere após acessar a plataforma para melhor proteger sua conta.</p>\r\n" + 
						"                <p>Bons planejamentos e boas compras!</p>\r\n" + 
						"            </div>\r\n" + 
						"\r\n" + 
						"            <div class=\"footer\">\r\n" + 
						"                <p>Lixt</p>\r\n" + 
						"                <p>aplication@lixt.com</p>\r\n" + 
						"            </div>\r\n" + 
						"        </div>\r\n" + 
						"    </body>\r\n" + 
						"\r\n" + 
						"</html>\r\n" + 
						"")
				.build();
	}

}
