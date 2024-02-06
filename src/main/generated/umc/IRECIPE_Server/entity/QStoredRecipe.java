package umc.IRECIPE_Server.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStoredRecipe is a Querydsl query type for StoredRecipe
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStoredRecipe extends EntityPathBase<StoredRecipe> {

    private static final long serialVersionUID = 383150716L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStoredRecipe storedRecipe = new QStoredRecipe("storedRecipe");

    public final StringPath body = createString("body");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public QStoredRecipe(String variable) {
        this(StoredRecipe.class, forVariable(variable), INITS);
    }

    public QStoredRecipe(Path<? extends StoredRecipe> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStoredRecipe(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStoredRecipe(PathMetadata metadata, PathInits inits) {
        this(StoredRecipe.class, metadata, inits);
    }

    public QStoredRecipe(Class<? extends StoredRecipe> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

