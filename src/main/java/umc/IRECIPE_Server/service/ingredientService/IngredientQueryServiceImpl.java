package umc.IRECIPE_Server.service.ingredientService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.IRECIPE_Server.apiPayLoad.code.status.ErrorStatus;
import umc.IRECIPE_Server.apiPayLoad.exception.handler.IngredientHandler;
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
    public Page<Ingredient> getIngredientList(Long memberId, Integer page) {
        Member member = memberRepository.findById(memberId).get();
        System.out.println("Fetching ingredient list for member: " + member.getName() + ", id: " + member.getId());

        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<Ingredient> ingredientPage = ingredientRepository.findAllByMember(member, pageRequest);

        // 추가된 로깅
        System.out.println("Fetched " + ingredientPage.getContent().size() + " ingredients for member " + member.getName());

        return ingredientPage;
    }
}
