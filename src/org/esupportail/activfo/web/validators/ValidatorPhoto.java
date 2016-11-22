package org.esupportail.activfo.web.validators;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.stream.ImageInputStream;

import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.esupportail.commons.beans.AbstractI18nAwareBean;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

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
			 String name=uploadedFile.getName();
			 String regex="([^\\s]+(\\.(?i)(jpg|jpeg))$)";			 
			 if (name!=null) {
				if (!name.matches(regex)) {
					throw new ValidatorException(getFacesErrorMessage("VALIDATOR.PHOTO.FORMAT.INAVLID"));
				}
			 }	 			 
			 
			 try{
				 try {
					streamFile = uploadedFile.getInputStream() ;
					img = ImageIO.read(streamFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			 }
			 catch (IllegalArgumentException e) {				 
				 img=getbyteGrayImage(uploadedFile);
				 
			 }
			 if (img==null)throw new ValidatorException(getFacesErrorMessage("VALIDATOR.FILEPHOTO.INAVLID"));
			 
		 }
	}
	
	public BufferedImage getbyteGrayImage(UploadedFile uploadedFile) {
		BufferedImage img = null;
		try {					  
			InputStream streamFile = uploadedFile.getInputStream();								
			ImageInputStream inputStream = ImageIO.createImageInputStream(streamFile);
			Iterator<ImageReader> iter = ImageIO.getImageReaders(inputStream);					        
	        while (iter.hasNext()) {
	            ImageReader reader = null;
	            try {
	                reader = (ImageReader)iter.next();
	                ImageReadParam param = reader.getDefaultReadParam();
	                reader.setInput(inputStream, true, true);
	                Iterator<ImageTypeSpecifier> imageTypes = reader.getImageTypes(0);
	                while (imageTypes.hasNext()) {
	                    ImageTypeSpecifier imageTypeSpecifier = imageTypes.next();
	                    int bufferedImageType = imageTypeSpecifier.getBufferedImageType();
	                    if (bufferedImageType == BufferedImage.TYPE_BYTE_GRAY) {
	                        param.setDestinationType(imageTypeSpecifier);
	                        break;
	                    }
	                }
	                
	                img = reader.read(0, param);
	                if (null != img)   	{
	                	System.out.println("image lue");
	                	break;}
	               
	            } catch (Exception e1) {
	            	e1.printStackTrace();
	            } finally {
	                if (null != reader) reader.dispose();               
	            }
	        }
				
	} catch (IOException e3) {
		// TODO Auto-generated catch block
		e3.printStackTrace();
	}			
		return img;
	}
	
}