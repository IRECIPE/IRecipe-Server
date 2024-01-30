package umc.IRECIPE_Server.apiPayLoad.exception.handler;

import umc.IRECIPE_Server.apiPayLoad.code.BaseErrorCode;
import umc.IRECIPE_Server.apiPayLoad.exception.GeneralException;

public class IngredientHandler extends GeneralException {

    public IngredientHandler(BaseErrorCode code) {
        super(code);
    }
}
