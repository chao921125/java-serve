// AI生成
package com.cc.app.service.impl.sys;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 审批条件表达式求值器
 * 支持简单表达式：amount > 5000, type == 'URGENT', amount > 1000 && type == 'URGENT'
 */
@Component
public class ApprovalConditionEvaluator {

    private static final Pattern COMPARE_PATTERN = Pattern.compile(
            "(\\w+)\\s*(>=|<=|==|!=|>|<)\\s*('([^']*)'|\"([^\"]*)\"|([0-9]+\\.?[0-9]*))"
    );

    /**
     * 求值条件表达式
     *
     * @param expression 条件表达式，如 "amount > 5000 && type == 'URGENT'"
     * @param formData   表单数据 JSON 字符串
     * @return 条件是否满足
     */
    public boolean evaluate(String expression, String formData) {
        if (expression == null || expression.isBlank()) {
            return true;
        }
        JSONObject data = parseFormData(formData);

        // 处理 OR (||)
        if (expression.contains("||")) {
            String[] orParts = expression.split("\\|\\|");
            for (String part : orParts) {
                if (evaluateAnd(part.trim(), data)) {
                    return true;
                }
            }
            return false;
        }

        return evaluateAnd(expression, data);
    }

    /**
     * 求值 AND (&&) 表达式
     */
    private boolean evaluateAnd(String expression, JSONObject data) {
        String[] andParts = expression.split("&&");
        for (String part : andParts) {
            if (!evaluateComparison(part.trim(), data)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 求值单个比较表达式
     */
    private boolean evaluateComparison(String expr, JSONObject data) {
        Matcher matcher = COMPARE_PATTERN.matcher(expr);
        if (!matcher.matches()) {
            return true;
        }

        String field = matcher.group(1);
        String operator = matcher.group(2);
        String stringValue = matcher.group(4) != null ? matcher.group(4) :
                matcher.group(5) != null ? matcher.group(5) : null;
        String numericValue = matcher.group(6);

        Object fieldValue = data.get(field);

        if (numericValue != null) {
            double actual = toDouble(fieldValue);
            double expected = Double.parseDouble(numericValue);
            return compareNumeric(actual, operator, expected);
        } else if (stringValue != null) {
            String actual = fieldValue != null ? fieldValue.toString() : "";
            return compareString(actual, operator, stringValue);
        }
        return true;
    }

    private boolean compareNumeric(double actual, String operator, double expected) {
        return switch (operator) {
            case ">" -> actual > expected;
            case "<" -> actual < expected;
            case ">=" -> actual >= expected;
            case "<=" -> actual <= expected;
            case "==" -> actual == expected;
            case "!=" -> actual != expected;
            default -> true;
        };
    }

    private boolean compareString(String actual, String operator, String expected) {
        return switch (operator) {
            case "==" -> actual.equals(expected);
            case "!=" -> !actual.equals(expected);
            default -> true;
        };
    }

    private double toDouble(Object value) {
        if (value == null) return 0;
        if (value instanceof Number) return ((Number) value).doubleValue();
        try {
            return Double.parseDouble(value.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private JSONObject parseFormData(String formData) {
        if (formData == null || formData.isBlank()) {
            return new JSONObject();
        }
        try {
            return JSON.parseObject(formData);
        } catch (Exception e) {
            return new JSONObject();
        }
    }
}