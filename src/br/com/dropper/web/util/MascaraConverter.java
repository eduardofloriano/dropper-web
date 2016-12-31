package br.com.dropper.web.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("mascaraConverter")
public class MascaraConverter implements Converter{
    
	
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
             return value;
    }
    @Override
    public String getAsString(FacesContext context, UIComponent component,Object value) {

    	String valor = (String) value;
    	if(valor.length() > 30){
    		
    		String arrNomeArquivo[] = valor.split("\\.");
    		String prefixo = arrNomeArquivo[0].substring(0, 15);
    		String sufixo = arrNomeArquivo[1];
    		
    		return prefixo + "..." + sufixo;
    	}else{
    		return valor;
    	}
        
    }
}
