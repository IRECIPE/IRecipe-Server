package umc.IRECIPE_Server.service.ingredientService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.IRECIPE_Server.apiPayLoad.code.status.ErrorStatus;
import umc.IRECIPE_Server.apiPayLoad.exception.GeneralException;
import umc.IRECIPE_Server.apiPayLoad.exception.handler.IngredientHandler;
import umc.IRECIPE_Server.common.enums.Type;
import umc.IRECIPE_Server.entity.Ingredient;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.repository.IngredientRepository;
import umc.IRECIPE_Server.repository.MemberRepository;


import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IngredientQueryServiceImpl implements IngredientQueryService {
    private final IngredientRepository ingredientRepository;
    private final MemberRepository memberRepository;

    @Override
    public Ingredient findOne(Long ingredientId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new IngredientHandler(ErrorStatus.INGREDIENT_NOT_FOUND));
        return ingredient;
    }

    @Override
    @Transactional
    public void delete(Long ingredientId) {
        Ingredient ingredient = findOne(ingredientId);
        ingredientRepository.delete(ingredient);
    }

    @Override
    public Page<Ingredient> getIngredientList(String memberId, Integer page) {
        Member member = memberRepository.findByPersonalId(memberId)
                .orElseThrow(() -> new IngredientHandler(ErrorStatus.MEMBER_NOT_FOUND));

        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<Ingredient> ingredientPage = ingredientRepository.findAllByMember(member, pageRequest);

        return ingredientPage;
    }

    @Override
    public Page<Ingredient> getIngredientListByType(String memberId, Type type, Integer page) {
        Member member = memberRepository.findByPersonalId(memberId)
                .orElseThrow(() -> new IngredientHandler(ErrorStatus.MEMBER_NOT_FOUND));

        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<Ingredient> ingredientPage = ingredientRepository.findAllByMemberAndType(member, type, pageRequest);

        return ingredientPage;
    }

    @Override
    public Page<Ingredient> searchIngredientByName(String memberId, String name, Integer page) {

        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<Ingredient> ingredientPage = ingredientRepository.findAllByMemberAndName(memberId, name, pageRequest);


        return ingredientPage;
    }

    @Override
    public Page<Ingredient> getNearingExpirationIngredientList(String memberId, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<Ingredient> ingredientPage = ingredientRepository.findExpirationListByMember(memberId, pageRequest);
        return ingredientPage;
    }
}