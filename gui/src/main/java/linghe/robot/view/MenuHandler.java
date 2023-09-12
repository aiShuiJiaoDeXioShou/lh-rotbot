package linghe.robot.view;

import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.callback.CefContextMenuParams;
import org.cef.callback.CefMenuModel;
import org.cef.handler.CefContextMenuHandlerAdapter;

public class MenuHandler  extends CefContextMenuHandlerAdapter {
    private final static int MENU_ID_INJECTION=10000;

    private final static int MENU_ID_ADDTEXT=10001;

    @Override
    public void onBeforeContextMenu(CefBrowser browser, CefFrame frame, CefContextMenuParams params, CefMenuModel model) {
        //清除菜单项
        model.clear();

        //剪切、复制、粘贴
        model.addItem(CefMenuModel.MenuId.MENU_ID_COPY, "复制");
        model.addItem(CefMenuModel.MenuId.MENU_ID_CUT, "剪切");
        model.addItem(CefMenuModel.MenuId.MENU_ID_PASTE, "粘贴");
        model.addSeparator();

        model.addItem(CefMenuModel.MenuId.MENU_ID_BACK, "返回");
        model.setEnabled(CefMenuModel.MenuId.MENU_ID_BACK, browser.canGoBack());

        model.addItem(CefMenuModel.MenuId.MENU_ID_FORWARD, "前进");
        model.setEnabled(CefMenuModel.MenuId.MENU_ID_FORWARD, browser.canGoForward());

        model.addItem(CefMenuModel.MenuId.MENU_ID_RELOAD, "刷新");

        model.addSeparator();
        //创建子菜单
        CefMenuModel cmodel=model.addSubMenu(MENU_ID_INJECTION, "脚本注入");

        cmodel.addItem(MENU_ID_ADDTEXT, "添加一段文本");
    }

    @Override
    public boolean onContextMenuCommand(CefBrowser browser, CefFrame frame, CefContextMenuParams params, int commandId, int eventFlags) {
        switch(commandId) {
            case CefMenuModel.MenuId.MENU_ID_RELOAD:
                browser.reload();
                return true;
            case MENU_ID_ADDTEXT:
                browser.executeJavaScript("document.body.innerHTML+='<div>添加一段文本</div>';", browser.getURL(), 0);
                return true;
        }
        return false;
    }
}
