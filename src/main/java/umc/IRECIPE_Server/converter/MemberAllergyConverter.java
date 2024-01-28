package umc.IRECIPE_Server.converter;

import umc.IRECIPE_Server.entity.Allergy;
import umc.IRECIPE_Server.entity.MemberAllergy;
import java.util.List;
import java.util.stream.Collectors;

public class MemberAllergyConverter {
    public static List<MemberAllergy> toMemberAllergyList(List<Allergy> allergyList){

        return allergyList.stream()
                .map(allergy ->
                        MemberAllergy.builder()
                                .allergy(allergy)
                                .build()
                ).collect(Collectors.toList());
    }
}
