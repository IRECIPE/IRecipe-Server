package umc.IRECIPE_Server.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberAllergy is a Querydsl query type for MemberAllergy
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberAllergy extends EntityPathBase<MemberAllergy> {

    private static final long serialVersionUID = -2104422021L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberAllergy memberAllergy = new QMemberAllergy("memberAllergy");

    public final umc.IRECIPE_Server.common.QBaseEntity _super = new umc.IRECIPE_Server.common.QBaseEntity(this);

    public final QAllergy allergy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMemberAllergy(String variable) {
        this(MemberAllergy.class, forVariable(variable), INITS);
    }

    public QMemberAllergy(Path<? extends MemberAllergy> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberAllergy(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberAllergy(PathMetadata metadata, PathInits inits) {
        this(MemberAllergy.class, metadata, inits);
    }

    public QMemberAllergy(Class<? extends MemberAllergy> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.allergy = inits.isInitialized("allergy") ? new QAllergy(forProperty("allergy")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

