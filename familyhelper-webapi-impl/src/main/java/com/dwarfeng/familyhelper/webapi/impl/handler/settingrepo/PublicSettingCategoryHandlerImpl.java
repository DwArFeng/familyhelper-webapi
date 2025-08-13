package com.dwarfeng.familyhelper.webapi.impl.handler.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.handler.settingrepo.PublicSettingCategoryHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.stereotype.Component;

@Component
public class PublicSettingCategoryHandlerImpl implements PublicSettingCategoryHandler {

    private final ExpressionParser expressionParser;
    private final ParserContext parserContext;

    @Value("${settingrepo.public_setting_category.spel}")
    private String spel;

    public PublicSettingCategoryHandlerImpl(ExpressionParser expressionParser, ParserContext parserContext) {
        this.expressionParser = expressionParser;
        this.parserContext = parserContext;
    }

    @Override
    public String parsePublicSettingCategory(String settingCategory) {
        return expressionParser.parseExpression(spel, parserContext).getValue(settingCategory, String.class);
    }

    @Override
    public String toString() {
        return "PublicSettingCategoryHandlerImpl{" +
                "expressionParser=" + expressionParser +
                ", parserContext=" + parserContext +
                ", spel='" + spel + '\'' +
                '}';
    }
}
