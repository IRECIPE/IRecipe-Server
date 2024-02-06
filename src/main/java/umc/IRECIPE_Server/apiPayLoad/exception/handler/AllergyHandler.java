package umc.IRECIPE_Server.apiPayLoad.exception.handler;

import umc.IRECIPE_Server.apiPayLoad.code.BaseErrorCode;
import umc.IRECIPE_Server.apiPayLoad.exception.GeneralException;

public class AllergyHandler extends GeneralException{
    public AllergyHandler(BaseErrorCode code) {
        super(code);
    }
}