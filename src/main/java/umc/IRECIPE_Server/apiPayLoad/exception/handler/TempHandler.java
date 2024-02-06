package umc.IRECIPE_Server.apiPayLoad.exception.handler;

import umc.IRECIPE_Server.apiPayLoad.code.BaseErrorCode;
import umc.IRECIPE_Server.apiPayLoad.exception.GeneralException;

public class TempHandler extends GeneralException {
    public TempHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
