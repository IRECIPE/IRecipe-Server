package umc.IRECIPE_Server.apiPayLoad.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import umc.IRECIPE_Server.apiPayLoad.code.BaseErrorCode;
import umc.IRECIPE_Server.apiPayLoad.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // 멤버 관련 에러
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "사용자가 없습니다."),
    NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4002", "닉네임은 필수 입니다."),
    NICKNAME_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4003", "이미 존재하는 닉네임입니다."),
    ADDRESS_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4004", "주소는 필수 입니다."),
    MEMBER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4005", "이미 존재하는 회원입니다."),
    GENDER_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4006", "성별은 필수 입니다."),
    NAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4007", "이름은 필수 입니다."),

    // 예시,,,
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "ARTICLE4001", "게시글이 없습니다."),

    //for test
    TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "어라라"),
    NEW_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4002", "왐마"),

    //카테고리 관련 에러
    FOOD_CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "FOOD4001", "음식 카테고리가 없습니다"),

    //레시피 카테고리 관련 에
    COOKING_CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "COOKING4002", "요리 종류를 정해야 합니다"),
    DIFFICULTY_CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "DIFFICULTY4002", "난이도를 정해야 합니다"),

    //레시피 글쓰기 관련 에러
    TITLE_NOT_EXIST(HttpStatus.BAD_REQUEST, "POST4001", "제목은 필수 입니다."),
    SUBTITLE_NOT_EXIST(HttpStatus.BAD_REQUEST, "POST4002", "소제목은 필수 입니다."),
    CONTENT_NOT_EXIST(HttpStatus.BAD_REQUEST, "POST4003", "내용은 필수 입니다."),

    //재료 관련 에러
    INGREDIENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "INGREDIENT4001", "재료를 찾을 수 없습니다."),
    INGREDIENT_TYPE_NOT_FOUND(HttpStatus.BAD_REQUEST, "INGREDIENT4002", "재료 타입이 맞지 않습니다."),

    //커뮤니티 관련 에러
    POST_NOT_FOUND(HttpStatus.BAD_REQUEST, "POST4011", "게시글을 찾을 수 없습니다."),
    POST_REVIEW_NOT_FOUND(HttpStatus.BAD_REQUEST, "POST4012", "게시글 후기를 찾을 수 없습니다."),
    LIKE_FOUND(HttpStatus.BAD_REQUEST, "POST4014", "이미 관심에 추가한 게시글입니다."),
    LIKE_NOT_FOUND(HttpStatus.BAD_REQUEST, "POST4015", "이미 관심에 추가되어 있지 않습니다."),
    POST_QNA_NOT_FOUND(HttpStatus.BAD_REQUEST, "POST4013", "게시글 QnA를 찾을 수 없습니다"),


    //알러지 관련 에러
    ALLERGY_NOT_FOUND(HttpStatus.BAD_REQUEST, "ALLERGY4001", "알러지를 찾을 수 없습니다."),

    //페이지 관련 에러
    MEMBER_DONT_HAVE_POSTS(HttpStatus.BAD_REQUEST, "PAGE4003", "게시물이 존재하지 않습니다."),
    NO_MORE_PAGE(HttpStatus.BAD_REQUEST, "PAGE4002", "더이상 페이지가 존재하지 않습니다."),
    INVALID_PAGE(HttpStatus.BAD_REQUEST, "PAGE4001", "유효하지 않은 페이지입니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}