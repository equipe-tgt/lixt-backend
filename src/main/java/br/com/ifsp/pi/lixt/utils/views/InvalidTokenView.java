package br.com.ifsp.pi.lixt.utils.views;

public abstract class InvalidTokenView {
	
	private InvalidTokenView() {}
	
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
				"\r\n" + 
				"        <link href=\"https://fonts.googleapis.com/css2?family=Archivo:wght@400;700&family=Poppins&display=swap\" rel=\"stylesheet\">\r\n" + 
				"\r\n" + 
				"    </head>\r\n" + 
				"\r\n" + 
				"    <body>\r\n" + 
				"        <h1>TOKEN INVÁLIDO</h1>\r\n" + 
				"    </body>\r\n" + 
				"\r\n" + 
				"</html>\r\n" + 
				"";
	}

}
