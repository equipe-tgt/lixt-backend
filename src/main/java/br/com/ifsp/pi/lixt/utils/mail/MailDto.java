package br.com.ifsp.pi.lixt.utils.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.ifsp.pi.lixt.utils.validators.ValidatorData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MailDto {
	
	private String msgHTML; 
	private String title;
	private List<String> recipientsTO;
	private List<String> recipientsCC;
	private List<String> recipientsBCC;
	private Date dateSent;
	private List<File> files;
	
	public MailDto setRecipientTo(String... destinary) {
		
		recipientsTO = new ArrayList<>();
		
		for(String s : destinary) {
			recipientsTO.add(s);
		}
		
		return this;
	}
	
	public boolean validateBodyEmail() {
		return ValidatorData.validateString(msgHTML) && ValidatorData.validateString(title);
	}

}
