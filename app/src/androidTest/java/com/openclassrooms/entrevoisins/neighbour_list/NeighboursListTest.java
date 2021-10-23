
package com.openclassrooms.entrevoisins.neighbour_list;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import com.openclassrooms.entrevoisins.R;

import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static final int ITEMS_COUNT = 12;


    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        ListNeighbourActivity activity = mActivityRule.getActivity();
        assertThat(activity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.

        onView(ViewMatchers.withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(RecyclerViewItemCountAssertion.withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(RecyclerViewItemCountAssertion.withItemCount(ITEMS_COUNT-1));
    }
    /** When we click on an avatar, the details activity is launched **/
    @Test
    public void onAvatarClikedDetailsActivityIsLaunched(){
        // We click on The Avatar on Position 1 (2nd in the list)
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new ClickOnAvatarAction()));
    onView(withId(R.id.detailsActivity)).check(matches(isDisplayed()));

       onView(withId(R.id.detailsTextViewUserNameWhite)).check(matches(withText("Jack")));

    }

    @Test
    public void checkIfFavoriteTabContainsOnlyFavoriteNeighbours(){
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new ClickOnAvatarAction()));
        onView(withId(R.id.detailsTextViewUserNameWhite)).check(matches(withText("Jack")));

        onView(ViewMatchers.withId(R.id.floatingActionButtonAddToFavorite))
                .perform(click());
        pressBack();
        onView(withText("Favorites"))
                .perform(click());
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(RecyclerViewItemCountAssertion.withItemCount(1));
        onView(withId(R.id.item_list_name)).check(matches(withText("Jack")));


    }

}