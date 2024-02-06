package umc.IRECIPE_Server.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAllergy is a Querydsl query type for Allergy
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAllergy extends EntityPathBase<Allergy> {

    private static final long serialVersionUID = -990961355L;

    public static final QAllergy allergy = new QAllergy("allergy");

    public final umc.IRECIPE_Server.common.QBaseEntity _super = new umc.IRECIPE_Server.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QAllergy(String variable) {
        super(Allergy.class, forVariable(variable));
    }

    public QAllergy(Path<? extends Allergy> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAllergy(PathMetadata metadata) {
        super(Allergy.class, metadata);
    }

}

