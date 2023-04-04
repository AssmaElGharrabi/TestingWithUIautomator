package com.example.testingwithuiautomator;


import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest4 {

    private static final String BASIC_SAMPLE_PACKAGE = "ma.project.android.uiautomator";
    private static final int LAUNCH_TIMEOUT = 5000;
    private UiDevice device;
    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialiser l'instance UiDevice
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        // Démarrer à partir de l'écran d'accueil
        device.pressHome();
        // Attendre le lanceur
        final String launcherPackage = device.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);
        // Lancer l'application
        Context context = ApplicationProvider.getApplicationContext();
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        // Effacer toutes les instances précédentes
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        // Attendre que l'application apparaisse
        device.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }
    @Test
    public void test() throws UiObjectNotFoundException {
        UiObject number1 = device.findObject(new UiSelector().resourceId("ma.project.android.uiautomator:id/number1").className("android.widget.EditText"));
        number1.setText(5+"");
        UiObject number2 = device.findObject(new UiSelector().resourceId("ma.project.android.uiautomator:id/number2").className("android.widget.EditText"));
        number2.setText(7+"");
        UiObject plus = device.findObject(new UiSelector().text("+").className("android.widget.Button"));
        // Simuler un clic de l'utilisateur sur le bouton OK, si il existe.
        if(plus.exists() && plus.isEnabled()) {
            plus.click();
        }
        // Vérifier le résultat = 12
        UiObject2 result = device.findObject(By.res(BASIC_SAMPLE_PACKAGE, "result"));
        assertEquals("12", result.getText());
    }

}
