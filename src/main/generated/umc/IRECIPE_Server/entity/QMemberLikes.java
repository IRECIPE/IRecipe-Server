package umc.IRECIPE_Server.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberLikes is a Querydsl query type for MemberLikes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberLikes extends EntityPathBase<MemberLikes> {

    private static final long serialVersionUID = -1820052361L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberLikes memberLikes = new QMemberLikes("memberLikes");

    public final umc.IRECIPE_Server.common.QBaseEntity _super = new umc.IRECIPE_Server.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final QPost post;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMemberLikes(String variable) {
        this(MemberLikes.class, forVariable(variable), INITS);
    }

    public QMemberLikes(Path<? extends MemberLikes> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberLikes(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberLikes(PathMetadata metadata, PathInits inits) {
        this(MemberLikes.class, metadata, inits);
    }

    public QMemberLikes(Class<? extends MemberLikes> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
    }

}

