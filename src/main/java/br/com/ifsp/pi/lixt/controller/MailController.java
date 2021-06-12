package br.com.ifsp.pi.lixt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifsp.pi.lixt.utils.mail.MailDto;
import br.com.ifsp.pi.lixt.utils.mail.SenderMail;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {
	
	private final SenderMail senderMail;
	
	@GetMapping
	public String helloWorld() {
		return "Hello World";
	}
	
	@PostMapping("/send")
	public void sendMail(@RequestBody(required = false) MailDto mailDto) {
		this.senderMail.sendEmail(mailDto);
	}

}
