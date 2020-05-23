package cat.urv.deim.asm.p2.common;

import android.app.Application;

public class GlobalLoginClass extends Application {
    protected static boolean loginCorrect = false;  //Variable to know if the user is anonymous or registered
    protected static boolean showBackToMenu = false;    //Variable to know if it's our first login

    public static boolean isLoginCorrect() {
        return loginCorrect;
    }

    public static void setLoginCorrect(boolean loginCorrect) {
        GlobalLoginClass.loginCorrect = loginCorrect;
    }

    public static boolean isShowBackToMenu() {
        return showBackToMenu;
    }

    public static void setShowBackToMenu(boolean showBackToMenu) {
        GlobalLoginClass.showBackToMenu = showBackToMenu;
    }
}
