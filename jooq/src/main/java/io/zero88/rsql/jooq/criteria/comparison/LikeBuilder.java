package io.zero88.rsql.jooq.criteria.comparison;

import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;

import io.zero88.rsql.jooq.JooqArgumentParser;
import io.zero88.rsql.jooq.criteria.JooqComparisonCriteriaBuilder;
import io.zero88.rsql.parser.ast.BuiltinComparisonOperator;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import lombok.NonNull;

//TODO implement it
public final class LikeBuilder extends JooqComparisonCriteriaBuilder {

    public static final ComparisonOperator OPERATOR = BuiltinComparisonOperator.LIKE;

    protected LikeBuilder(@NonNull ComparisonNode node) {
        super(node);
    }

    @Override
    protected @NonNull Condition compare(@NonNull Field field, @NonNull List<String> arguments,
                                         @NonNull JooqArgumentParser parser) {
      return  field.like((String) parser.parse(field, arguments.get(0)));
    }

}
