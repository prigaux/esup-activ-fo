package org.esupportail.activfo.web.validators;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.imageio.ImageIO;

import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.esupportail.commons.beans.AbstractI18nAwareBean;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;

public class ValidatorPhoto extends AbstractI18nAwareBean implements Validator {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Logger logger = new LoggerImpl(getClass());
	
		/**
	 * @throws IOException 
	 * 
	 */

	public void validate(FacesContext context, UIComponent componentToValidate,Object object) throws ValidatorException{
		
		 UploadedFile uploadedFile = (UploadedFile)object;
		 InputStream streamFile = null;
		 BufferedImage img = null;
		 
		 if (uploadedFile!=null){
			 try {
				streamFile = uploadedFile.getInputStream() ;
				img = ImageIO.read(streamFile);
				if (img==null)throw new ValidatorException(getFacesErrorMessage("VALIDATOR.FILEPHOTO.INAVLID"));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
	}
	
}