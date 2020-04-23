package io.github.manuelarte.spring.queryparameter.config;

import io.github.manuelarte.spring.queryparameter.operators.EqualsOperator;
import io.github.manuelarte.spring.queryparameter.operators.GreaterThanOperator;
import io.github.manuelarte.spring.queryparameter.operators.GreaterThanOrEqualsOperator;
import io.github.manuelarte.spring.queryparameter.operators.InOperator;
import io.github.manuelarte.spring.queryparameter.operators.LowerThanOperator;
import io.github.manuelarte.spring.queryparameter.operators.LowerThanOrEqualsOperator;
import io.github.manuelarte.spring.queryparameter.operators.NotAbstractMiddleOperatorOperator;
import io.github.manuelarte.spring.queryparameter.operators.NotInOperator;
import io.github.manuelarte.spring.queryparameter.operators.Operator;
import java.util.ArrayList;
import java.util.List;
import lombok.val;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OperatorConfig {

  @Bean
  @ConditionalOnMissingBean
  public List<Operator<?>> operators() {
    final List<Operator<?>> operators = new ArrayList<>();
    val equalsOperator = new EqualsOperator();
    val notEqualsOperator = new NotAbstractMiddleOperatorOperator(equalsOperator);

    val lowerThanOrEqualsOperator = new LowerThanOrEqualsOperator();
    val notLowerThanOrEqualsOperator = new NotAbstractMiddleOperatorOperator(lowerThanOrEqualsOperator);

    val lowerThanOperator = new LowerThanOperator();
    val notLowerThanOperator = new NotAbstractMiddleOperatorOperator(lowerThanOperator);

    val greaterThanOrEqualsOperator = new GreaterThanOrEqualsOperator();
    val notGreaterThanOrEqualsOperator = new NotAbstractMiddleOperatorOperator(greaterThanOrEqualsOperator);

    val greaterThanOperator = new GreaterThanOperator();
    val notGreaterThanOperator = new NotAbstractMiddleOperatorOperator(greaterThanOperator);

    val inOperator = new InOperator();
    val notInOperator = new NotInOperator(inOperator);

    operators.add(equalsOperator);
    operators.add(notEqualsOperator);

    operators.add(lowerThanOrEqualsOperator);
    operators.add(notLowerThanOrEqualsOperator);

    operators.add(lowerThanOperator);
    operators.add(notLowerThanOperator);

    operators.add(greaterThanOrEqualsOperator);
    operators.add(notGreaterThanOrEqualsOperator);

    operators.add(greaterThanOperator);
    operators.add(notGreaterThanOperator);

    operators.add(inOperator);
    operators.add(notInOperator);

    return operators;
  }

}
