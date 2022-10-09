package jialiang_ding.reggie.util;

public class BaseContextUtil {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }
    public static Long getCurrentId(){
        return threadLocal.get();
    }
    private static final ThreadLocal<Long> userId = new ThreadLocal<>();


    public static void setCurrentUser(Long userId) {
        BaseContextUtil.userId.set(userId);
    }

    public static Long  getCurrentUserId() {
        return userId.get();
    }


}
