package com.asiainfo.lucene.core.criteria;

import java.lang.reflect.Array;
import java.util.List;










public final class SqlExpression
{
  public SqlExpression() {}
  
  public static void builder(Criteria.Criterion criterion, StringBuilder sb)
  {
    whereClause(criterion, sb);
  }
  




  private static void whereClause(Criteria.Criterion criterion, StringBuilder sb)
  {
    if ((criterion.getComparison().toString().equalsIgnoreCase(Criteria.IN.toString())) || (criterion.getComparison().toString().equalsIgnoreCase(Criteria.NOT_IN.toString())))
    {
      doIn(criterion, sb);
    }
    else if ((criterion.getComparison().toString().equalsIgnoreCase(Criteria.LIKE.toString())) || (criterion.getComparison().toString().equalsIgnoreCase(Criteria.NOT_LIKE.toString())))
    {
      doLike(criterion, sb);
    }
    else {
      Object val = criterion.getValue();
      if (val == null) {
        val = "";
      }
      sb.append(criterion.getColumn() + criterion.getComparison().toString() + val);
    }
  }
  




  private static void doIn(Criteria.Criterion criterion, StringBuilder sb)
  {
    sb.append(criterion.getColumn());
    sb.append(criterion.getComparison().toString());
    sb.append(" ( ");
    Object value = criterion.getValue();
    if ((criterion.getValue() instanceof List)) {
      List valueList = (List)value;
      for (int i = 0; i < valueList.size(); i++) {
        if ((valueList.get(i) instanceof String)) {
          sb.append("'");
          sb.append(valueList.get(i));
          sb.append("'");
        }
        else {
          sb.append(valueList.get(i));
        }
        if (i != valueList.size() - 1) {
          sb.append(",");
        }
      }
    } else {
      for (int i = 0; i < Array.getLength(value); i++) {
        if ((Array.get(value, i) instanceof String)) {
          sb.append("'");
          sb.append(Array.get(value, i));
          sb.append("'");
        }
        else {
          sb.append(Array.get(value, i));
        }
        if (i != Array.getLength(value) - 1) {
          sb.append(",");
        }
      }
    }
    sb.append(" ) ");
  }
  


  private static void doLike(Criteria.Criterion criterion, StringBuilder sb)
  {
    sb.append(criterion.getColumn());
    sb.append(criterion.getComparison().toString());
    
    sb.append(criterion.getValue());
  }
}

