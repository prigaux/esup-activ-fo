package org.esupportail.activfo.web.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.esupportail.commons.beans.AbstractI18nAwareBean;

public class ValidatorPassword extends AbstractI18nAwareBean implements Validator{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8849185735359561457L;

	public void validate(FacesContext context, UIComponent componentToValidate,Object value) throws ValidatorException {
		
			String PASSWORD_MIXED_CASE = "1";
			String PASSWORD_MIN_LENGTH = "7";
			String PASSWORD_MAX_LENGTH = "30";
			String PASSWORD_NUMERIC = "1";
			String PASSWORD_SPECIAL = "1";
			String PASSWORD_STRENGTH = "20";
			String[][] interdit={
					{"¡","\u00A1"},{"¢","\u00A2"},{"£","\u00A3"},{"¤","\u00A4"},
					{"¥","\u00A5"},{"¦","\u00A6"},{"§","\u00A7"},{"¨","\u00A8"},
					{"©","\u00A9"},{"ª","\u00AA"},{"«","\u00AB"},{"¬","\u00AC"},
					{" ","espace"},{"®","\u00AE"},{"¯","\u00AF"},{"°","\u00B0"},
					{"±","\u00B1"},{"²","\u00B2"},{"³","\u00B3"},{"´","\u00B4"},
					{"µ","\u00B5"},{"¶","\u00B6"},{"·","\u00B7"},{"¸","\u00B8"},
					{"¹","\u00B9"},{"º","\u00BA"},{"»","\u00BB"},{"¼","\u00BC"},
					{"½","\u00BD"},{"¾","\u00BE"},{"¿","\u00BF"},{"À","\u00C0"},
					{"Á","\u00C1"},{"Â","\u00C2"},{"Ã","\u00C3"},{"Ä","\u00C4"},
					{"Å","\u00C5"},{"Æ","\u00C6"},{"Ç","\u00C7"},{"È","\u00C8"},
					{"É","\u00C9"},{"Ê","\u00CA"},{"Ë","\u00CB"},{"Ì","\u00CC"},
					{"Í","\u00CD"},{"Î","\u00CE"},{"Ï","\u00CF"},{"Ð","\u00D0"},
					{"Ñ","\u00D1"},{"Ò","\u00D2"},{"Ó","\u00D3"},{"Ô","\u00D4"},
					{"Õ","\u00D5"},{"Ö","\u00D6"},{"×","\u00D7"},{"Ø","\u00D8"},
					{"Ù","\u00D9"},{"Ú","\u00DA"},{"Û","\u00DB"},{"Ü","\u00DC"},
					{"Ý","\u00DD"},{"Þ","\u00DE"},{"ß","\u00DF"},{"à","\u00E0"},
					{"á","\u00E1"},{"â","\u00E2"},{"ã","\u00E3"},{"ä","\u00E4"},
					{"å","\u00E5"},{"æ","\u00E6"},{"ç","\u00E7"},{"è","\u00E8"},
					{"é","\u00E9"},{"ê","\u00EA"},{"ë","\u00EB"},{"ì","\u00EC"},
					{"í","\u00ED"},{"î","\u00EE"},{"ï","\u00EF"},{"ð","\u00F0"},
					{"ñ","\u00F1"},{"ò","\u00F2"},{"ó","\u00F3"},{"ô","\u00F4"},
					{"õ","\u00F5"},{"ö","\u00F6"},{"÷","\u00F7"},{"ø","\u00F8"},
					{"ù","\u00F9"},{"ú","\u00FA"},{"û","\u00FB"},{"ü","\u00FC"},
					{"ý","\u00FD"},{"þ","\u00FE"},{"ÿ","\u00FF"}
			};
			
			if (value instanceof String) {
				String passwd = (String) value;
				
				int upper = 0, lower = 0, numbers = 0, special = 0, length = 0, special2=0;
				int strength = 0, intScore = 0;
				String strVerdict = "none", strLog = "";
				Pattern p;
				Matcher m;
				if (passwd == null)
					throw new ValidatorException(getFacesErrorMessage("VALIDATOR.PASSWORD.NULL"));
				// PASSWORD LENGTH
				length = passwd.length();
				if (length < 5) // length 4 or less
					{
					intScore = (intScore + 3);
					strLog = strLog + "3 points for length (" + length + ")\n";
				} else if (length > 4 && passwd.length() < 8) // length between 5 and 7
					{
					intScore = (intScore + 6);
					strLog = strLog + "6 points for length (" + length + ")\n";
				} else if (
					length > 7 && passwd.length() < 16) // length between 8 and 15
					{
					intScore = (intScore + 12);
					strLog = strLog + "12 points for length (" + length + ")\n";
				} else if (length > 15) // length 16 or more
					{
					intScore = (intScore + 18);
					strLog = strLog + "18 point for length (" + length + ")\n";
				}
				// LETTERS 
				p = Pattern.compile(".??[a-z]");
				m = p.matcher(passwd);
				while (m.find()) // [verified] at least one lower case letter
					{
					lower += 1;
				}
				if (lower > 0) {
					intScore = (intScore + 1);
					strLog = strLog + "1 point for a lower case character\n";
				}
				p = Pattern.compile(".??[A-Z]");
				m = p.matcher(passwd);
				while (m.find()) // [verified] at least one upper case letter
					{
					upper += 1;
				}
				if (upper > 0) {
					intScore = (intScore + 5);
					strLog = strLog + "5 point for an upper case character\n";
				}
				// NUMBERS
				p = Pattern.compile(".??[0-9]");
				m = p.matcher(passwd);
				while (m.find()) // [verified] at least one number
					{
					numbers += 1;
				}
				if (numbers > 0) {
					intScore = (intScore + 5);
					strLog = strLog + "5 points for a number\n";
					if (numbers > 1) {
						intScore = (intScore + 2);
						strLog = strLog + "2 points for at least two numbers\n";
						if (numbers > 2) {
							intScore = (intScore + 3);
							strLog = strLog + "3 points for at least three numbers\n";
						}
					}
				}
				
				for (int i=0;i<interdit.length;i++) {
					p=Pattern.compile(interdit[i][0]);
					m=p.matcher(passwd);
					if(m.find()) {
						throw new ValidatorException(getFacesErrorMessage("VALIDATOR.PASSWORD.INTERDITCARACTERS",interdit[i][1]));
					}
				}
				
				
				// SPECIAL CHAR
				p = Pattern.compile(".??[:,!,@,#,$,%,^,&,*,?,_,~]");
				m = p.matcher(passwd);
				while (m.find()) // [verified] at least one special character
					{
					special += 1;
				}
				if (special > 0) {
					intScore = (intScore + 5);
					strLog = strLog + "5 points for a special character\n";
					if (special > 1) {
						intScore += (intScore + 5);
						strLog =
							strLog + "5 points for at least two special characters\n";
					}
				}
				// COMBOS
				if (upper > 0 && lower > 0) // [verified] both upper and lower case
					{
					intScore = (intScore + 2);
					strLog = strLog + "2 combo points for upper and lower letters\n";
				}
				if ((upper > 0 || lower > 0)
					&& numbers > 0) // [verified] both letters and numbers
					{
					intScore = (intScore + 2);
					strLog = strLog + "2 combo points for letters and numbers\n";
				}
				if ((upper > 0 || lower > 0)
					&& numbers > 0
					&& special > 0) // [verified] letters, numbers, and special characters
					{
					intScore = (intScore + 2);
					strLog =
						strLog
							+ "2 combo points for letters, numbers and special chars\n";
				}
				if (upper > 0 && lower > 0 && numbers > 0 && special > 0)			// [verified] upper, lower, numbers, and special characters
					{
					intScore = (intScore + 2);
					strLog =
						strLog
							+ "2 combo points for upper and lower case letters, numbers and special chars\n";
				}
				if (intScore < 16) {
					strVerdict = "very weak";
				} else if (intScore > 15 && intScore < 25) {
					strVerdict = "weak";
				} else if (intScore > 24 && intScore < 35) {
					strVerdict = "mediocre";
				} else if (intScore > 34 && intScore < 45) {
					strVerdict = "strong";
				} else {
					strVerdict = "very strong";
				}
				System.out.println(strVerdict + " - " + intScore + "\n" + strLog);
				// Does it meet the password policy?
					int min = Integer.parseInt(PASSWORD_MIN_LENGTH);
					if (length < min)
						throw new ValidatorException(getFacesErrorMessage("VALIDATOR.PASSWORD.TOOSHORT"));


					int max = Integer.parseInt(PASSWORD_MAX_LENGTH);
					if (length > max)
						throw new ValidatorException(getFacesErrorMessage("VALIDATOR.PASSWORD.TOOLONG"));


					int num = Integer.parseInt(PASSWORD_NUMERIC);
					
					int score=0;
					boolean notEnoughNumbers=true;
					if (numbers >= num) {
						notEnoughNumbers=false;
						score++;
					}

					int mix = Integer.parseInt(PASSWORD_MIXED_CASE);
					boolean notEnoughMixed=true;
					if (upper >= mix && lower >= mix) {
						notEnoughMixed=false;
						score++;
					}
					
					int spec = Integer.parseInt(PASSWORD_SPECIAL);
					boolean notEnoughSpecial=true;
					if (special >= spec) {
						notEnoughSpecial=false;
						score++;
					}
					
					if (score<2) throw new ValidatorException(getFacesErrorMessage("VALIDATOR.PASSWORD.NOTENOUGHDIFFERENTCHARS"));
		
					
					int str = Integer.parseInt(PASSWORD_STRENGTH);
					if (intScore < str)
						throw new ValidatorException(getFacesErrorMessage("VALIDATOR.PASSWORD.NOTENOUGHSTRONG"));
					
			}
			else throw new ValidatorException(getFacesErrorMessage("VALIDATOR.NOTSTRING"));
		}
}