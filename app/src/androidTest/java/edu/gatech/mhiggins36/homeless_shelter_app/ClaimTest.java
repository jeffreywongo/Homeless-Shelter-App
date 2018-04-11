package edu.gatech.mhiggins36.homeless_shelter_app;

import android.content.Intent;
import android.os.SystemClock;
import android.os.UserManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.test.ActivityInstrumentationTestCase2;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import edu.gatech.mhiggins36.homeless_shelter_app.Controllers.ShelterManager;
import edu.gatech.mhiggins36.homeless_shelter_app.R;
import edu.gatech.mhiggins36.homeless_shelter_app.activities.ShelterInfoActivity;
import edu.gatech.mhiggins36.homeless_shelter_app.models.Shelter;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by robertwaters on 3/15/16.
 */
@RunWith(AndroidJUnit4.class)
public class ClaimTest {


    /** this line is preferred way to hook up to activity */
    @Rule
    public ActivityTestRule<ShelterInfoActivity> mActivityRule =
            new ActivityTestRule<ShelterInfoActivity>(ShelterInfoActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent(InstrumentationRegistry.getTargetContext(), ShelterInfoActivity.class);
                    //put eden village for testing purposes
                    intent.putExtra("Shelter", new Shelter(3,
                            "Eden Village", 112,
                            112, "", (float)0.0,(float)0.0,"","",""));
                    return intent;
                }
            };


    @Test
    public void checkClaim() {
        //logs me in and sets me as the current user
        ShelterManager.createShelterMap(mActivityRule.getActivity().getApplicationContext());
        edu.gatech.mhiggins36.homeless_shelter_app.
                Controllers.UserManager.checkLogin(mActivityRule.getActivity().getApplicationContext(),
                "devin@gmail.com", "pass");


        //put 1 bed to be claimed
        onView(withId(R.id.numBeds)).perform(typeText("1"), closeSoftKeyboard());

        //now click the bed claim button
        onView(withId(R.id.bedClaim)).perform(click());
        //check that claim was unsuccessful
        onView(withId(R.id.claimStatus)).check(matches(withText("claim unsuccessful")));

        //now click the bed claim button
        onView(withId(R.id.bedClaim)).perform(click());
        //check that claim was successful
        onView(withId(R.id.claimStatus)).check(matches(withText("claim successful")));
    }


}
