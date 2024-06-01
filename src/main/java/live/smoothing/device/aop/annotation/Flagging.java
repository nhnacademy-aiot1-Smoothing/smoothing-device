package live.smoothing.device.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Flagging {
    Class<?>[] value() default {};
    Class<?> type() default Object.class;
    boolean isFlag() default true;
}
