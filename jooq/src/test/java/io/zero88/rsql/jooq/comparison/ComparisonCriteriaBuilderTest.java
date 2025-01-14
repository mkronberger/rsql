package io.zero88.rsql.jooq.comparison;

import java.util.Arrays;
import java.util.Collections;

import io.zero88.rsql.jooq.criteria.comparison.LikeBuilder;
import io.zero88.rsql.jooq.criteria.comparison.NotLikeBuilder;
import org.jooq.Condition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.zero88.rsql.jooq.criteria.JooqCriteriaBuilder;
import io.zero88.rsql.jooq.criteria.JooqCriteriaBuilderFactory;
import io.zero88.rsql.jooq.criteria.comparison.BetweenBuilder;
import io.zero88.rsql.jooq.criteria.comparison.EqualBuilder;
import io.zero88.rsql.jooq.criteria.comparison.ExistsBuilder;
import io.zero88.rsql.jooq.criteria.comparison.GreaterThanBuilder;
import io.zero88.rsql.jooq.criteria.comparison.GreaterThanOrEqualBuilder;
import io.zero88.rsql.jooq.criteria.comparison.InBuilder;
import io.zero88.rsql.jooq.criteria.comparison.LessThanBuilder;
import io.zero88.rsql.jooq.criteria.comparison.LessThanOrEqualBuilder;
import io.zero88.rsql.jooq.criteria.comparison.NonExistsBuilder;
import io.zero88.rsql.jooq.criteria.comparison.NotEqualBuilder;
import io.zero88.rsql.jooq.criteria.comparison.NotInBuilder;
import io.zero88.rsql.jooq.criteria.comparison.NullableBuilder;
import io.github.zero88.utils.Strings;
import io.zero88.rsql.jooq.Tables;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.RSQLOperators;

public class ComparisonCriteriaBuilderTest {

    @Test
    public void test_equal_node() {
        final ComparisonNode node = new ComparisonNode(RSQLOperators.EQUAL, Tables.ALL_DATA_TYPE.F_PERIOD.getName(),
                                                       Collections.singletonList("abc"));
        final JooqCriteriaBuilder builder = JooqCriteriaBuilderFactory.DEFAULT.create(node);
        Assertions.assertTrue(builder instanceof EqualBuilder);
        final Condition condition = builder.build(Tables.ALL_DATA_TYPE);
        Assertions.assertEquals("\"ALL_DATA_TYPE\".\"F_PERIOD\" = 'abc'", condition.toString());
    }

    @Test
    public void test_not_equal_node() {
        final ComparisonNode node = new ComparisonNode(RSQLOperators.NOT_EQUAL, Tables.ALL_DATA_TYPE.F_PERIOD.getName(),
                                                       Collections.singletonList("abc"));
        final JooqCriteriaBuilder builder = JooqCriteriaBuilderFactory.DEFAULT.create(node);
        Assertions.assertTrue(builder instanceof NotEqualBuilder);
        final Condition condition = builder.build(Tables.ALL_DATA_TYPE);
        Assertions.assertEquals("\"ALL_DATA_TYPE\".\"F_PERIOD\" <> 'abc'", condition.toString());
    }

    @Test
    public void test_greater_than_node() {
        final ComparisonNode node = new ComparisonNode(RSQLOperators.GREATER_THAN,
                                                       Tables.ALL_DATA_TYPE.F_REAL.getName(),
                                                       Collections.singletonList("5.5"));
        final JooqCriteriaBuilder builder = JooqCriteriaBuilderFactory.DEFAULT.create(node);
        Assertions.assertTrue(builder instanceof GreaterThanBuilder);
        final Condition condition = builder.build(Tables.ALL_DATA_TYPE);
        Assertions.assertEquals("\"ALL_DATA_TYPE\".\"F_REAL\" > 5.5", condition.toString());
    }

    @Test
    public void test_greater_than_or_equal_node() {
        final ComparisonNode node = new ComparisonNode(RSQLOperators.GREATER_THAN_OR_EQUAL,
                                                       Tables.ALL_DATA_TYPE.F_INT.getName(),
                                                       Collections.singletonList("5"));
        final JooqCriteriaBuilder builder = JooqCriteriaBuilderFactory.DEFAULT.create(node);
        Assertions.assertTrue(builder instanceof GreaterThanOrEqualBuilder);
        final Condition condition = builder.build(Tables.ALL_DATA_TYPE);
        Assertions.assertEquals("\"ALL_DATA_TYPE\".\"F_INT\" >= 5", condition.toString());
    }

    @Test
    public void test_less_than_node() {
        final ComparisonNode node = new ComparisonNode(RSQLOperators.LESS_THAN, Tables.ALL_DATA_TYPE.F_DOUBLE.getName(),
                                                       Collections.singletonList("23.54326"));
        final JooqCriteriaBuilder builder = JooqCriteriaBuilderFactory.DEFAULT.create(node);
        Assertions.assertTrue(builder instanceof LessThanBuilder);
        final Condition condition = builder.build(Tables.ALL_DATA_TYPE);
        Assertions.assertEquals("\"ALL_DATA_TYPE\".\"F_DOUBLE\" < 23.54326", condition.toString());
    }

    @Test
    public void test_less_than_or_equal_node() {
        final ComparisonNode node = new ComparisonNode(RSQLOperators.LESS_THAN_OR_EQUAL,
                                                       Tables.ALL_DATA_TYPE.F_DECIMAL.getName(),
                                                       Collections.singletonList("55.24681579"));
        final JooqCriteriaBuilder builder = JooqCriteriaBuilderFactory.DEFAULT.create(node);
        Assertions.assertTrue(builder instanceof LessThanOrEqualBuilder);
        final Condition condition = builder.build(Tables.ALL_DATA_TYPE);
        Assertions.assertEquals("\"ALL_DATA_TYPE\".\"F_DECIMAL\" <= 55.24681579", condition.toString());
    }

    @Test
    public void test_in_node() {
        final ComparisonNode node = new ComparisonNode(RSQLOperators.IN, Tables.ALL_DATA_TYPE.ID.getName(),
                                                       Arrays.asList("5", "7", "10"));
        final JooqCriteriaBuilder builder = JooqCriteriaBuilderFactory.DEFAULT.create(node);
        Assertions.assertTrue(builder instanceof InBuilder);
        final Condition condition = builder.build(Tables.ALL_DATA_TYPE);
        Assertions.assertEquals("\"ALL_DATA_TYPE\".\"ID\" in ( 5, 7, 10 )",
                            Strings.optimizeMultipleSpace(condition.toString()));
    }

    @Test
    public void test_not_in_node() {
        final ComparisonNode node = new ComparisonNode(RSQLOperators.NOT_IN, Tables.ALL_DATA_TYPE.ID.getName(),
                                                       Arrays.asList("5", "7", "10"));
        final JooqCriteriaBuilder builder = JooqCriteriaBuilderFactory.DEFAULT.create(node);
        Assertions.assertTrue(builder instanceof NotInBuilder);
        final Condition condition = builder.build(Tables.ALL_DATA_TYPE);
        Assertions.assertEquals("\"ALL_DATA_TYPE\".\"ID\" not in ( 5, 7, 10 )",
                            Strings.optimizeMultipleSpace(condition.toString()));
    }

    @Test
    public void test_between_node() {
        final ComparisonNode node = new ComparisonNode(BetweenBuilder.OPERATOR, Tables.ALL_DATA_TYPE.F_DATE.getName(),
                                                       Arrays.asList("2020-04-05T08:00:00", "2020-04-08T08:00:00"));
        final JooqCriteriaBuilder builder = JooqCriteriaBuilderFactory.DEFAULT.create(node);
        Assertions.assertTrue(builder instanceof BetweenBuilder);
        final Condition condition = builder.build(Tables.ALL_DATA_TYPE);
        System.out.println(node.toString());
        System.out.println(condition.toString());
        Assertions.assertEquals(
            "\"ALL_DATA_TYPE\".\"F_DATE\" between timestamp '2020-04-05 08:00:00.0' and timestamp " +
            "'2020-04-08 08:00:00.0'", Strings.optimizeMultipleSpace(condition.toString()));
    }

    @Test
    public void test_exists_node() {
        final ComparisonNode node = new ComparisonNode(ExistsBuilder.OPERATOR, Tables.ALL_DATA_TYPE.F_PERIOD.getName(),
                                                       Collections.singletonList("t"));
        final JooqCriteriaBuilder builder = JooqCriteriaBuilderFactory.DEFAULT.create(node);
        Assertions.assertTrue(builder instanceof ExistsBuilder);
        final Condition condition = builder.build(Tables.ALL_DATA_TYPE);
        System.out.println(node.toString());
        System.out.println(condition.toString());
        Assertions.assertEquals("\"ALL_DATA_TYPE\".\"F_PERIOD\" is not null",
                            Strings.optimizeMultipleSpace(condition.toString()));
    }

    @Test
    public void test_non_exists_node() {
        final ComparisonNode node = new ComparisonNode(NonExistsBuilder.OPERATOR,
                                                       Tables.ALL_DATA_TYPE.F_DURATION.getName(),
                                                       Collections.singletonList("t"));
        final JooqCriteriaBuilder builder = JooqCriteriaBuilderFactory.DEFAULT.create(node);
        Assertions.assertTrue(builder instanceof NonExistsBuilder);
        final Condition condition = builder.build(Tables.ALL_DATA_TYPE);
        Assertions.assertEquals("\"ALL_DATA_TYPE\".\"F_DURATION\" is null",
                            Strings.optimizeMultipleSpace(condition.toString()));
    }

    @Test
    public void test_nullable_node() {
        final ComparisonNode node = new ComparisonNode(NullableBuilder.OPERATOR,
                                                       Tables.ALL_DATA_TYPE.F_VALUE_JSON.getName(),
                                                       Collections.singletonList("t"));
        final JooqCriteriaBuilder builder = JooqCriteriaBuilderFactory.DEFAULT.create(node);
        Assertions.assertTrue(builder instanceof NullableBuilder);
        final Condition condition = builder.build(Tables.ALL_DATA_TYPE);
        Assertions.assertEquals("( \"ALL_DATA_TYPE\".\"F_VALUE_JSON\" is null or \"ALL_DATA_TYPE\"" +
                            ".\"F_VALUE_JSON\" = 't' )", Strings.optimizeMultipleSpace(condition.toString()));
    }

    @Test
    public void test_like_node() {
        final ComparisonNode node = new ComparisonNode(LikeBuilder.OPERATOR,
                Tables.ALL_DATA_TYPE.F_STR.getName(),
                Collections.singletonList("test"));
        final JooqCriteriaBuilder builder = JooqCriteriaBuilderFactory.DEFAULT.create(node);
        Assertions.assertTrue(builder instanceof LikeBuilder);
        final Condition condition = builder.build(Tables.ALL_DATA_TYPE);
        Assertions.assertEquals("\"ALL_DATA_TYPE\".\"F_STR\" like 'test'", Strings.optimizeMultipleSpace(condition.toString()));
    }

    @Test
    public void test_notLike_node() {
        final ComparisonNode node = new ComparisonNode(NotLikeBuilder.OPERATOR,
                Tables.ALL_DATA_TYPE.F_STR.getName(),
                Collections.singletonList("test%"));
        final JooqCriteriaBuilder builder = JooqCriteriaBuilderFactory.DEFAULT.create(node);
        Assertions.assertTrue(builder instanceof NotLikeBuilder);
        final Condition condition = builder.build(Tables.ALL_DATA_TYPE);
        Assertions.assertEquals("\"ALL_DATA_TYPE\".\"F_STR\" not like 'test%'", Strings.optimizeMultipleSpace(condition.toString()));
    }

}
