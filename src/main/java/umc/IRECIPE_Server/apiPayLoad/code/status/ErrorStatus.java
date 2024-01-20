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

    // 멤버 관려 에러
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

    //커뮤니티 관련 에러
    POST_NOT_FOUND(HttpStatus.BAD_REQUEST, "POST4003", "게시글을 찾을 수 없습니다."),

    //지역 관련 에러
    REGION_NOT_FOUND(HttpStatus.BAD_REQUEST, "REGION4001", "그런 지역은 없는뎁쇼"),

    //스토어 관련 에러
    STORE_NOT_FOUND(HttpStatus.BAD_REQUEST, "STORE4001", "그런 가게는 없는뎁쇼");

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