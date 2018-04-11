package edu.gatech.mhiggins36.homeless_shelter_app;

import android.content.Intent;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.TextView;

import edu.gatech.mhiggins36.homeless_shelter_app.Controllers.ShelterManager;
import edu.gatech.mhiggins36.homeless_shelter_app.activities.ShelterInfoActivity;
import edu.gatech.mhiggins36.homeless_shelter_app.models.Shelter;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

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
        ShelterManager.getInstance(mActivityRule.getActivity().getApplicationContext()).createShelterMap();
        edu.gatech.mhiggins36.homeless_shelter_app.
                Controllers.UserManager.getInstance(InstrumentationRegistry.getTargetContext()).checkLogin("devin@gmail.com", "pass");


        //put 1 bed to be claimed
        onView(withId(R.id.numBeds)).perform(typeText("not num"), closeSoftKeyboard());

        //now click the bed claim button
        onView(withId(R.id.bedClaim)).perform(click());
        //check that claim was unsuccessful
        onView(withId(R.id.claimStatus)).check(matches(withText("claim unsuccessful")));

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

    /**
     * Original source from Espresso library, modified to handle spanned fields
     *
     * Returns a matcher that matches a descendant of {@link TextView} that is
     * displaying the string associated with the given resource id.
     *
     * @param resourceId
     *            the string resource the text view is expected to hold.
     */
    public static Matcher<View> withText(final String resourceId) {

        return new BoundedMatcher<View, TextView>(TextView.class) {
            private String expectedText = null;

            @Override
            public void describeTo(Description description) {
                description.appendText("with string from resource id: ");
                description.appendValue(resourceId);
                if (null != this.expectedText) {
                    description.appendText(" value: ");
                    description.appendText(this.expectedText);
                }
            }

            @Override
            public boolean matchesSafely(TextView textView) {
                if (null == this.expectedText) {
                    try {
                        this.expectedText = textView.getText().toString();
                    } catch (Resources.NotFoundException ignored) {
                        /*
                         * view could be from a context unaware of the resource
                         * id.
                         */
                    }
                }
                if (null != this.expectedText) {
                    return this.expectedText.equals(textView.getText()
                            .toString());
                } else {
                    return false;
                }
            }
        };
    }

}
