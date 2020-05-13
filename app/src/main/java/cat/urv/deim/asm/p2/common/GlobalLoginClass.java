package cat.urv.deim.asm.p2.common;

import android.app.Application;

public class GlobalLoginClass extends Application {
    protected static boolean loginCorrect = false;

    public static boolean isLoginCorrect() {
        return loginCorrect;
    }
    public static void setLoginCorrect(boolean loginCorrect) {
        GlobalLoginClass.loginCorrect = loginCorrect;
    }
}
