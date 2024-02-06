package umc.IRECIPE_Server.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -526138811L;

    public static final QMember member = new QMember("member1");

    public final umc.IRECIPE_Server.common.QBaseEntity _super = new umc.IRECIPE_Server.common.QBaseEntity(this);

    public final BooleanPath activity = createBoolean("activity");

    public final EnumPath<umc.IRECIPE_Server.common.enums.Age> age = createEnum("age", umc.IRECIPE_Server.common.enums.Age.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final ListPath<DislikedFood, QDislikedFood> dislikedFoodList = this.<DislikedFood, QDislikedFood>createList("dislikedFoodList", DislikedFood.class, QDislikedFood.class, PathInits.DIRECT2);

    public final BooleanPath event = createBoolean("event");

    public final EnumPath<umc.IRECIPE_Server.common.enums.Gender> gender = createEnum("gender", umc.IRECIPE_Server.common.enums.Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath important = createBoolean("important");

    public final ListPath<Ingredient, QIngredient> ingredientList = this.<Ingredient, QIngredient>createList("ingredientList", Ingredient.class, QIngredient.class, PathInits.DIRECT2);

    public final ListPath<MemberAllergy, QMemberAllergy> memberAllergyList = this.<MemberAllergy, QMemberAllergy>createList("memberAllergyList", MemberAllergy.class, QMemberAllergy.class, PathInits.DIRECT2);

    public final ListPath<MemberLikes, QMemberLikes> memberLikesList = this.<MemberLikes, QMemberLikes>createList("memberLikesList", MemberLikes.class, QMemberLikes.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath personalId = createString("personalId");

    public final StringPath profileImage = createString("profileImage");

    public final ListPath<Qna, QQna> qnaList = this.<Qna, QQna>createList("qnaList", Qna.class, QQna.class, PathInits.DIRECT2);

    public final ListPath<StoredRecipe, QStoredRecipe> recipeList = this.<StoredRecipe, QStoredRecipe>createList("recipeList", StoredRecipe.class, QStoredRecipe.class, PathInits.DIRECT2);

    public final ListPath<Review, QReview> reviewList = this.<Review, QReview>createList("reviewList", Review.class, QReview.class, PathInits.DIRECT2);

    public final EnumPath<umc.IRECIPE_Server.common.enums.Role> role = createEnum("role", umc.IRECIPE_Server.common.enums.Role.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

