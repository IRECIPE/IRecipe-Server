package umc.IRECIPE_Server.apiPayLoad.exception.handler;

import umc.IRECIPE_Server.apiPayLoad.code.BaseErrorCode;
import umc.IRECIPE_Server.apiPayLoad.exception.GeneralException;

public class MemberHandler extends GeneralException {
    public MemberHandler(BaseErrorCode code) {
        super(code);
    }
}
