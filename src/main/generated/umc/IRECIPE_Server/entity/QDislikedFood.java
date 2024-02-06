package umc.IRECIPE_Server.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDislikedFood is a Querydsl query type for DislikedFood
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDislikedFood extends EntityPathBase<DislikedFood> {

    private static final long serialVersionUID = -951686072L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDislikedFood dislikedFood = new QDislikedFood("dislikedFood");

    public final umc.IRECIPE_Server.common.QBaseEntity _super = new umc.IRECIPE_Server.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QDislikedFood(String variable) {
        this(DislikedFood.class, forVariable(variable), INITS);
    }

    public QDislikedFood(Path<? extends DislikedFood> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDislikedFood(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDislikedFood(PathMetadata metadata, PathInits inits) {
        this(DislikedFood.class, metadata, inits);
    }

    public QDislikedFood(Class<? extends DislikedFood> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

