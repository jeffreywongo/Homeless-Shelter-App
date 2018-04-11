package edu.gatech.mhiggins36.homeless_shelter_app;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;

import org.junit.Rule;
import org.junit.Test;

import android.support.test.rule.ActivityTestRule;

import edu.gatech.mhiggins36.homeless_shelter_app.activities.LoginActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class CheckCredentialsTest {

    /** this line is preferred way to hook up to activity */
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent(InstrumentationRegistry.getTargetContext(), LoginActivity.class);
                    return intent;
                }
            };


    @Test
    public void checkCredentialsPass() {

        //put in proper username
        onView(withId(R.id.usernameField)).perform(typeText("devin@gmail.com"), closeSoftKeyboard());
        //put in proper pass
        onView(withId(R.id.passwordField)).perform(typeText("pass"), closeSoftKeyboard());
        //now click the sign in button
        onView(withId(R.id.signIn)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //do it twice bcs Volley
        onView(withId(R.id.signIn)).perform(click());
        //check that login was successful
        onView(withId(R.id.nameSearch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void checkCredentialsFail() {
        //put in proper username
        onView(withId(R.id.usernameField)).perform(typeText("devin@gmail.com"), closeSoftKeyboard());
        //put in wrong pass
        onView(withId(R.id.passwordField)).perform(typeText("andsl"), closeSoftKeyboard());
        //now click the sign in button
        onView(withId(R.id.signIn)).perform(click());
        //check that login was unsuccessful
        onView(withId(R.id.incorrectCred)).check(matches(isDisplayed()));
        //clear username field
        onView(withId(R.id.usernameField)).perform(clearText(), closeSoftKeyboard());
        //clear pass field
        onView(withId(R.id.passwordField)).perform(clearText(), closeSoftKeyboard());

        //put in proper username
        onView(withId(R.id.usernameField)).perform(typeText("devin@gmail.com"), closeSoftKeyboard());
        //put in empty pass
        onView(withId(R.id.passwordField)).perform(typeText(""), closeSoftKeyboard());
        //now click the sign in button
        onView(withId(R.id.signIn)).perform(click());
        //check that login was unsuccessful
        onView(withId(R.id.incorrectCred)).check(matches(isDisplayed()));
        //clear username field
        onView(withId(R.id.usernameField)).perform(clearText(), closeSoftKeyboard());
        //clear pass field
        onView(withId(R.id.passwordField)).perform(clearText(), closeSoftKeyboard());

        //put in wrong username
        onView(withId(R.id.usernameField)).perform(typeText("sdalkfjds@gmail.com"), closeSoftKeyboard());
        //put in proper pass
        onView(withId(R.id.passwordField)).perform(typeText("pass"), closeSoftKeyboard());
        //now click the sign in button
        onView(withId(R.id.signIn)).perform(click());
        //check that login was unsuccessful
        onView(withId(R.id.incorrectCred)).check(matches(isDisplayed()));
        //clear username field
        onView(withId(R.id.usernameField)).perform(clearText(), closeSoftKeyboard());
        //clear pass field
        onView(withId(R.id.passwordField)).perform(clearText(), closeSoftKeyboard());

        //put in empty username
        onView(withId(R.id.usernameField)).perform(typeText(""), closeSoftKeyboard());
        //put in proper pass
        onView(withId(R.id.passwordField)).perform(typeText("pass"), closeSoftKeyboard());
        //now click the sign in button
        onView(withId(R.id.signIn)).perform(click());
        //check that login was unsuccessful
        onView(withId(R.id.incorrectCred)).check(matches(isDisplayed()));
        //clear username field
        onView(withId(R.id.usernameField)).perform(clearText(), closeSoftKeyboard());
        //clear pass field
        onView(withId(R.id.passwordField)).perform(clearText(), closeSoftKeyboard());

        //put in wrong username
        onView(withId(R.id.usernameField)).perform(typeText("asfsd@gmail.com"), closeSoftKeyboard());
        //put in empty pass
        onView(withId(R.id.passwordField)).perform(typeText(""), closeSoftKeyboard());
        //now click the sign in button
        onView(withId(R.id.signIn)).perform(click());
        //check that login was unsuccessful
        onView(withId(R.id.incorrectCred)).check(matches(isDisplayed()));
        //clear username field
        onView(withId(R.id.usernameField)).perform(clearText(), closeSoftKeyboard());
        //clear pass field
        onView(withId(R.id.passwordField)).perform(clearText(), closeSoftKeyboard());

        //put in wrong username
        onView(withId(R.id.usernameField)).perform(typeText("asdfd@gmail.com"), closeSoftKeyboard());
        //put in wrong pass
        onView(withId(R.id.passwordField)).perform(typeText("asdfdsa"), closeSoftKeyboard());
        //now click the sign in button
        onView(withId(R.id.signIn)).perform(click());
        //check that login was unsuccessful
        onView(withId(R.id.incorrectCred)).check(matches(isDisplayed()));
        //clear username field
        onView(withId(R.id.usernameField)).perform(clearText(), closeSoftKeyboard());
        //clear pass field
        onView(withId(R.id.passwordField)).perform(clearText(), closeSoftKeyboard());
    }
}
