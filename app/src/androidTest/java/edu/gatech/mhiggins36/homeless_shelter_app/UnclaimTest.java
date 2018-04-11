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
 * Created by marcu on 4/11/2018.
 */

@RunWith (AndroidJUnit4.class)
public class UnclaimTest {

    @Rule
    public ActivityTestRule<ShelterInfoActivity> mActivityRule =
            new ActivityTestRule<ShelterInfoActivity>(ShelterInfoActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent(InstrumentationRegistry.getTargetContext(), ShelterInfoActivity.class);
                    intent.putExtra("Shelter", new Shelter(21, "Your House",
                            150, 150, "", (float)0.0,(float)0.0,
                            "","",""));
                    return intent;
                }
            };

    @Test
    public void checkUnclaim() {
        ShelterManager.createShelterMap(mActivityRule.getActivity().getApplicationContext());
        edu.gatech.mhiggins36.homeless_shelter_app.Controllers.UserManager.
                checkLogin(mActivityRule.getActivity().getApplicationContext(),
                        "marcus@gatech.edu", "pass");
        onView(withId(R.id.cancelBed)).perform(click());
        onView(withId(R.id.claimStatus)).check(matches(withText("clear unsuccessful")));
        onView(withId(R.id.numBeds)).perform(typeText("3"), closeSoftKeyboard());
        onView(withId(R.id.bedClaim)).perform(click());
        onView(withId(R.id.bedClaim)).perform(click());
        onView(withId(R.id.cancelBed)).perform(click());
        onView(withId(R.id.cancelBed)).perform(click());
        onView(withId(R.id.claimStatus)).check(matches(withText("clear successful")));
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
