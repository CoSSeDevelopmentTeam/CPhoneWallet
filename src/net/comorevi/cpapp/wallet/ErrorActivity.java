package net.comorevi.cpapp.wallet;

import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.ModalResponse;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.widget.activity.Activity;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;
import net.comorevi.cphone.cphone.widget.activity.base.ModalActivity;

public class ErrorActivity extends ModalActivity {

    private Bundle bundle;
    private String eMessage;
    private Activity activity;

    public ErrorActivity(ApplicationManifest manifest, String emessage, Activity activity) {
        super(manifest);
        this.eMessage = emessage;
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle bundle) {
        this.bundle = bundle;
        this.setTitle(bundle.getString("title_error"));
        this.setContent("message: \n - " + this.eMessage);
        this.setButton1Text(bundle.getString("button_error1"));
        this.setButton2Text(bundle.getString("button_error2"));
    }

    @Override
    public ReturnType onStop(Response response) {
        ModalResponse mResponse = (ModalResponse) response;
        if (mResponse.isButton1Clicked()) {
            switch (activity.getClass().getName()) {
                case "net.comorevi.cpapp.wallet.GiveMoneyActivity":
                    new GiveMoneyActivity(getManifest()).start(mResponse.getPlayer(), bundle.getStrings());
                    return ReturnType.TYPE_CONTINUE;
                case "net.comorevi.cpapp.wallet.PayMoneyActivity":
                    new PayMoneyActivity(getManifest()).start(mResponse.getPlayer(), bundle.getStrings());
                    return ReturnType.TYPE_CONTINUE;
                case "net.comorevi.cpapp.wallet.SeeMoneyActivity":
                    new SeeMoneyActivity(getManifest()).start(mResponse.getPlayer(), bundle.getStrings());
                    return ReturnType.TYPE_CONTINUE;
                case "net.comorevi.cpapp.wallet.SetMoneyActivity":
                    new SetMoneyActivity(getManifest()).start(mResponse.getPlayer(), bundle.getStrings());
                    return ReturnType.TYPE_CONTINUE;
                case "net.comorevi.cpapp.wallet.TakeMoneyActivity":
                    new TakeMoneyActivity(getManifest()).start(mResponse.getPlayer(), bundle.getStrings());
                    return ReturnType.TYPE_CONTINUE;
            }
        } else if (mResponse.isButton2Clicked()) {
            return ReturnType.TYPE_END;
        }
        return ReturnType.TYPE_END;
    }
}
