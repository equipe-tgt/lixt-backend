package br.com.ifsp.pi.lixt.utils.views.errorforgotpassword;

public abstract class ErrorForgotPasswordViewHtml {

    private ErrorForgotPasswordViewHtml() {}

    public static String getView() {
        return "<!DOCTYPE html>\r\n"
        		+ "<html\r\n"
        		+ "  xmlns=\"http://www.w3.org/1999/xhtml\"\r\n"
        		+ "  xmlns:v=\"urn:schemas-microsoft-com:vml\"\r\n"
        		+ "  xmlns:o=\"urn:schemas-microsoft-com:office:office\"\r\n"
        		+ ">\r\n"
        		+ "  <head>\r\n"
        		+ "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n"
        		+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\r\n"
        		+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\r\n"
        		+ "    <meta name=\"format-detection\" content=\"date=no\" />\r\n"
        		+ "    <meta name=\"format-detection\" content=\"telephone=no\" />\r\n"
        		+ "    <title>Lixt</title>\r\n"
        		+ "\r\n"
        		+ "    <link\r\n"
        		+ "      href=\"https://fonts.googleapis.com/css2?family=Archivo:wght@400;700&family=Poppins&display=swap\"\r\n"
        		+ "      rel=\"stylesheet\"\r\n"
        		+ "    />\r\n"
        		+ "\r\n"
        		+ "    <style>\r\n"
        		+ "      body {\r\n"
        		+ "        background-color: #eaeaea;\r\n"
        		+ "        width: 100%;\r\n"
        		+ "        margin: 0;\r\n"
        		+ "        font-family: \"Poppins\", sans-serif;\r\n"
        		+ "        text-align: center;\r\n"
        		+ "      }\r\n"
        		+ "\r\n"
        		+ "      h1 {\r\n"
        		+ "        font-weight: 600;\r\n"
        		+ "      }\r\n"
        		+ "\r\n"
        		+ "      body > div:first-child {\r\n"
        		+ "        background-color: #fff;\r\n"
        		+ "        width: 90%;\r\n"
        		+ "        min-width: 300px;\r\n"
        		+ "        margin: 25px auto;\r\n"
        		+ "        border-radius: 5px;\r\n"
        		+ "        box-shadow: 10px 10px 5px -6px rgba(0, 0, 0, 0.26);\r\n"
        		+ "      }\r\n"
        		+ "\r\n"
        		+ "      .header {\r\n"
        		+ "        border-radius: 5px 5px 0 0;\r\n"
        		+ "        background-color: #07b4d2;\r\n"
        		+ "        width: 100%;\r\n"
        		+ "        padding: 5px 0;\r\n"
        		+ "        color: white;\r\n"
        		+ "      }\r\n"
        		+ "\r\n"
        		+ "      .body {\r\n"
        		+ "        padding-bottom: 15px;\r\n"
        		+ "      }\r\n"
        		+ "    </style>\r\n"
        		+ "  </head>\r\n"
        		+ "\r\n"
        		+ "  <body>\r\n"
        		+ "    <div id=\"active-account\">\r\n"
        		+ "      <div class=\"header\">\r\n"
        		+ "        <h1>$TITLE</h1>\r\n"
        		+ "      </div>\r\n"
        		+ "\r\n"
        		+ "      <div class=\"body\">\r\n"
        		+ "        <p>$RESPONSE</p>\r\n"
        		+ "      </div>\r\n"
        		+ "    </div>\r\n"
        		+ "  </body>\r\n"
        		+ "</html>\r\n";
    }

}
