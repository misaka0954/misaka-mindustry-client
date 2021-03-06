package uwu.utils;

import arc.scene.ui.Dialog;
import mindustry.Vars;
import mindustry.ui.dialogs.BaseDialog;
import uwu.utils.conveyors.ConveyorChecker;
import uwu.utils.finder.BlockFinder;
import uwu.utils.history.HistoryDialog;

import java.util.Date;

public class MenuCaller {
    static int x = 0;
    static int y = 0;
    static Long lastTapTime = 0L;
    static int t = 0;
    public static boolean historyEnable = false;

    public static void tap(int tx, int ty) {
        if (tx != x || ty != y || new Date().getTime() - lastTapTime > 500) {
            lastTapTime = new Date().getTime();
            x = tx;
            y = ty;
            t = 1;
            return;
        }
        t++;
        if (t < 3) {
            return;
        }
        if(historyEnable){
            new HistoryDialog(Vars.world.tile(tx,ty));
            return;
        }
        showMenuDialog();
    }
    public static void showMenuDialog() {
        Dialog d = new Dialog("Menu");
        d.center().button("Conveyor checker", ConveyorChecker::new).size(400f, 50f).center().row();
//        if(net.server() || player.admin){
//        d.button("Bans Log", BanLogDialog::new).size(400f, 50f).row();
//        }
        d.center().button("Enable History", () -> {
            historyEnable = true;
            d.hide();
        }).size(400f, 50f).center().row();
        d.center().button("Find Block", () -> {
            new BlockFinder();
            d.hide();
        }).size(400f, 50f).center().row();
        d.center().button("Close", d::hide).center();
        d.closeOnBack();
        d.show();
    }
}
