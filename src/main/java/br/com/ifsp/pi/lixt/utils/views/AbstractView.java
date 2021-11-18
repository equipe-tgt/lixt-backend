package br.com.ifsp.pi.lixt.utils.views;

import java.util.Map;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractView {
	
	protected static String translate(String view, Map<String, String> params) {
		for(String key : params.keySet())
			view = view.replace(key, params.get(key));
		
		return view;
	}

}
