package az.maqa.project.model.response;

public enum ErrorMessages {

	 	MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields"),
	    RECORD_ALREADY_EXISTS("Record already exists"),
	    INTERNAL_SERVER_ERROR("Internal server error"),
	    NO_RECORD_FOUND("Record with provided id is not found"),
	    AUTHENTICATION_FAILED("Authentication failed"),
	    COULD_NOT_UPDATE_RECORD("Could not update record"),
	    COULD_NOT_DELETE_RECORD("Could not delete record"),
	    EMAIL_ADDRESS_NOT_VERIFIED("Email address could not be verified");
	
	
//	    	CODE_MISSING_REQUIRED_FIELD(101),
//	    	CODE_RECORD_ALREADY_EXISTS(102),
//	CODE_INTERNAL_SERVER_ERROR(103),
//	CODE_NO_RECORD_FOUND(104),
//	CODE_AUTHENTICATION_FAILED(105),
//	CODE_COULD_NOT_UPDATE_RECORD(106),
//	CODE_COULD_NOT_DELETE_RECORD(107),
//	CODE_EMAIL_ADDRESS_NOT_VERIFIED(108);
	    

	    private String errorMessage;
	  //  private Integer code;

	    ErrorMessages(String errorMessage) {
	        this.errorMessage = errorMessage;
	    }


		/**
	     * @return the errorMessage
	     */
	    public String getErrorMessage() {
	        return errorMessage;
	    }

	    /**
	     * @param errorMessage the errorMessage to set
	     */
	    public void setErrorMessage(String errorMessage) {
	        this.errorMessage = errorMessage;
	    }
	
}
