package appiumrunner.unesc.net.appiumrunner.helpers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodInvoker {
    public static Object invoke(Object object, String methodName, Class parameterType, Object params) {
        Method method = null;
        try {
            method = parameterType == null ? object.getClass().getDeclaredMethod(methodName)
                    : object.getClass().getDeclaredMethod(methodName, parameterType);
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            return params == null ? method.invoke(object) : method.invoke(object, params);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object invoke(Object object, String methodName) {
        return invoke(object, methodName, null, null);
    }
}
