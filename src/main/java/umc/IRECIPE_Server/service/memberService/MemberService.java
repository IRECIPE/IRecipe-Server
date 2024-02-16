package umc.IRECIPE_Server.service.memberService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import umc.IRECIPE_Server.apiPayLoad.code.status.ErrorStatus;
import umc.IRECIPE_Server.apiPayLoad.exception.handler.AllergyHandler;
import umc.IRECIPE_Server.apiPayLoad.exception.handler.MemberHandler;
import umc.IRECIPE_Server.apiPayLoad.exception.handler.PostHandler;
import umc.IRECIPE_Server.common.enums.Status;
import umc.IRECIPE_Server.converter.MemberAllergyConverter;
import umc.IRECIPE_Server.converter.MemberConverter;
import umc.IRECIPE_Server.dto.request.MemberLoginRequestDTO;
import umc.IRECIPE_Server.dto.request.MemberRequestDTO;
import umc.IRECIPE_Server.dto.response.MemberResponseDTO;
import umc.IRECIPE_Server.entity.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface MemberService {


    Boolean findMemberByNickname(String Nickname);
    Boolean findMemberByPersonalId(String id);

    Member findMember(String personalId);

    void checkNickname(String nickname);

    MemberAllergy findMemberAllergy(Long memberId, Long allergyId);

    Member joinMember(MemberRequestDTO.JoinDto request, String url);

    Member signup(MemberRequestDTO.JoinDto request, String url);

    Member updateProfileById(MultipartFile file, String personalId) throws IOException;

    Member updateMemberById(MemberRequestDTO.fixMemberInfoDto request, String personalId);

    void deleteMemberAllergy(Long memberId, Long allergyId);

    Member login(MemberLoginRequestDTO.JoinLoginDto request);

    Page<Post> getWrittenPostList(String personalId, Integer page);

    List<Post> getLikedPostList(String personalId, Integer page);

    Member refresh(Member member);

    void deleteMember(String personalId);

}
