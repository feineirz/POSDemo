/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GLOBAL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author feinz
 */
public class Validator {
    
    public static class InputValidation {
        
         public static class ValidationResult {
            public boolean result;
            public String message;
        }
         
        public static final String LETTER_UCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String LETTER_LCASE = LETTER_UCASE.toLowerCase();
        public static final String LETTER_DIGITS = "0123456789";
        public static final String LETTER_SYMBOLS = "!@#$^&*()-=_+[]{},.<>";
        
        public static final int VALIDATE_USERNAME_MIN_LENGTH = 4;
        public static final int VALIDATE_USERNAME_MAX_LENGTH = 16;
        public static final String VALIDATE_USERNAME_VALID_CHARACTERS = LETTER_UCASE + LETTER_LCASE;
        
        public static final int VALIDATE_PASSWORD_MIN_LENGTH = 4;
        public static final int VALIDATE_PASSWORD_MAX_LENGTH = 16;
        public static final String VALIDATE_PASSWORD_VALID_CHARACTERS = LETTER_UCASE + LETTER_LCASE + LETTER_DIGITS + LETTER_SYMBOLS;
        
        public static final int VALIDATE_CATEGORYNAME_MIN_LENGTH = 2;
        public static final int VALIDATE_CATEGORYNAME_MAX_LENGTH = 16;
        
        public static final int VALIDATE_PRODUCTCODE_MIN_LENGTH = 3;
        public static final int VALIDATE_PRODUCTCODE_MAX_LENGTH = 100;
        
        public static final int VALIDATE_PRODUCTNAME_MIN_LENGTH = 3;
        public static final int VALIDATE_PRODUCTNAME_MAX_LENGTH = 200;
        
        // Common validation        
        public static ValidationResult validateStringLength(String content, int min, int max, String errMessage) {
            
            ValidationResult vr = new ValidationResult();
            if (content.length() < min || content.length() > max) {
                vr.result = false;
                vr.message = errMessage;
                return vr;
            }
            
            vr.result = true;
            vr.message = "PASSED";
            return vr;
            
        }
        
        public static ValidationResult validateStringIsInteger(String content, String errMessage) {
            
            ValidationResult vr = new ValidationResult();
            try{
                Integer.parseInt(content);
                vr.result = true;
                vr.message = "PASSED";
                return vr;
            }catch (Exception ex) {
                vr.result = false;
                vr.message = errMessage;
                return vr;
            }
                
        }
        
        public static ValidationResult validateStringIsDouble(String content, String errMessage) {
            
            ValidationResult vr = new ValidationResult();
            try{
                Double.parseDouble(content);
                vr.result = true;
                vr.message = "PASSED";
                return vr;
            }catch (Exception ex) {
                vr.result = false;
                vr.message = errMessage;
                return vr;
            }
                
        }
        
        public static ValidationResult validateForbiddenCharacter(String content, String validCharacters, String errMessage) {
            
            ValidationResult vr = new ValidationResult();
            for (char c : content.toCharArray()) {
                if (validCharacters.indexOf(c) < 0) {
                    vr.result =false;
                    vr.message = errMessage;
                    return vr;
                }
            }
            
            vr.result = true;
                vr.message = "PASSED";
                return vr;
        }
        
        public static ValidationResult validateEmail(String email) {
            
            ValidationResult vr = new ValidationResult();
            
            String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"; 
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            
            if (!matcher.matches()) {
                vr.result = false ;
                vr.message = "Invalid Email address format!";
                return vr;
            }
            
            vr.result = true ;
            vr.message = "PASSED";
            return vr;
        }
        
        public static ValidationResult validateCost(String cost) {
            
            ValidationResult vr = new ValidationResult();
            String errMessage;
            
            if (cost.trim().equals("")) {
                vr.result = false ;
                vr.message = "Cost cannot be EMPTY!";
                return vr;
            }
            
            errMessage = "Invalid 'Cost' format!";
            vr = validateStringIsDouble(cost, errMessage);
            if (!vr.result) return vr;
            
            vr.result = true ;
            vr.message = "PASSED";
            return vr;
            
        }
        
        public static ValidationResult validatePrice(String price) {
            
            ValidationResult vr = new ValidationResult();
            String errMessage;
            
            if (price.trim().equals("")) {
                vr.result = false ;
                vr.message = "Price cannot be EMPTY!";
                return vr;
            }
            
            errMessage = "Invalid 'Price' format!";
            vr = validateStringIsDouble(price, errMessage);
            if (!vr.result) return vr;
            
            vr.result = true ;
            vr.message = "PASSED";
            return vr;
            
        }
        
        // Specific validation
        
        public static ValidationResult validateUsername(String username) {
            
            ValidationResult vr = new ValidationResult();
            String errMessage;            
            
            errMessage = "Username must be "+VALIDATE_USERNAME_MIN_LENGTH+"-"+VALIDATE_USERNAME_MAX_LENGTH+" characters";
            vr = validateStringLength(username, VALIDATE_USERNAME_MIN_LENGTH, VALIDATE_USERNAME_MAX_LENGTH, errMessage);
            if (!vr.result) return vr;
            
            errMessage = "Username contains forbidden character(s)";
            vr = validateForbiddenCharacter(username, VALIDATE_USERNAME_VALID_CHARACTERS, errMessage);
            if (!vr.result) return vr;            
            
            vr.message = "PASSED";
            vr.result = true ;
            return vr;            
        }
        
        public static ValidationResult validatePassword(String password) {
            
            ValidationResult vr = new ValidationResult();
            String errMessage;            
            
            errMessage = "Password must be "+VALIDATE_PASSWORD_MIN_LENGTH+"-"+VALIDATE_PASSWORD_MAX_LENGTH+" characters";
            vr = validateStringLength(password, VALIDATE_PASSWORD_MIN_LENGTH, VALIDATE_PASSWORD_MAX_LENGTH, errMessage);
            if (!vr.result) return vr;

            errMessage = "Password contains forbidden character(s)";
            vr = validateForbiddenCharacter(password, VALIDATE_PASSWORD_VALID_CHARACTERS, errMessage);
            if (!vr.result) return vr;
            
            vr.message = "PASSED";
            vr.result = true ;
            return vr;            
        }
        
        // Product Section
        public static ValidationResult validateCategoryName(String categoryName) {            
            
            ValidationResult vr = new ValidationResult();
            String errMessage;            
            
            errMessage = "Category name must be "+VALIDATE_CATEGORYNAME_MIN_LENGTH+"-"+VALIDATE_CATEGORYNAME_MAX_LENGTH+" characters";
            vr = validateStringLength(categoryName, VALIDATE_PASSWORD_MIN_LENGTH, VALIDATE_PASSWORD_MAX_LENGTH, errMessage);
            if (!vr.result) return vr;
            
            vr.message = "PASSED";
            vr.result = true ;
            return vr;            
        }
        
        public static ValidationResult validateProductCode(String productCode) {            
            
            ValidationResult vr = new ValidationResult();
            String errMessage;            
            
            errMessage = "Product code must be "+VALIDATE_PRODUCTCODE_MIN_LENGTH+"-"+VALIDATE_PRODUCTNAME_MAX_LENGTH+" characters";
            vr = validateStringLength(productCode, VALIDATE_PRODUCTCODE_MIN_LENGTH, VALIDATE_PRODUCTNAME_MAX_LENGTH, errMessage);
            if (!vr.result) return vr;
            
            vr.message = "PASSED";
            vr.result = true ;
            return vr;            
        }
        
        public static ValidationResult validateProductName(String productName) {            
            
            ValidationResult vr = new ValidationResult();
            String errMessage;            
            
            errMessage = "Product name must be "+VALIDATE_PRODUCTNAME_MIN_LENGTH+"-"+VALIDATE_PRODUCTNAME_MAX_LENGTH+" characters";
            vr = validateStringLength(productName, VALIDATE_PRODUCTNAME_MIN_LENGTH, VALIDATE_PRODUCTNAME_MAX_LENGTH, errMessage);
            if (!vr.result) return vr;
            
            vr.message = "PASSED";
            vr.result = true ;
            return vr;            
        }
        
    }
    
}
