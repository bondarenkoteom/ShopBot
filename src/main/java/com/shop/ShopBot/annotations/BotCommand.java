package com.shop.ShopBot.annotations;

import com.shop.ShopBot.constant.MessageType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BotCommand {
    String command();
    MessageType type();
    String redirect() default "";
}
