# Mini Moneybox Solution

## Part A - Fix current bugs

### Bug 1 - Layout does not look as expected

*- Added missing constraints to match expected layout below*

![](/images/correct_layout.png)

### Bug 2 - Validation is incorrect
If the input entered by the user is correct then they should see a toast saying “Input is valid!”.  However if it is not correct we should show an error on the field that is incorrect.  Below is the following validation logic:

- Email is not optional and should match EMAIL_REGEX
- Password is not optional and should match PASSWORD_REGEX
- Name is optional, but if it contains any value it should match NAME_REGEX

*- Replaced incorrect field validation logic with helper methods that check all the fields or the individual fields depending on the situation*

### Bug 3 - Animation is looping incorrectly

- The animation should start from frame **0** to **109** when the user first starts the activity.  See below for animation.
![](/images/firstpig.gif)
- When the first stage of the animation has finished it should then loop from frame **131** to **158** continuously.  See below for animation.<br/>
![](/images/secondpig.gif)

*- Added AnimatorUpdateListener to listen to frame changes up to the second part of the animation then set a loop on the remaining frames to match expected functionality*

## Part B - Add 2 new screens

### Screen 2 - User accounts screen
This screen should be shown after the user has successfully logged in and should show have the following functionality:
- Display "Hello {name} **only** if they provided it on previous screen"
- Show the **'TotalPlanValue'** of a user.
- Show the accounts the user holds, e.g. ISA, GIA, LISA, Pension.
- Show all of those account's **'PlanValue'**.
- Show all of those account's **'Moneybox'** total.

*- Added login functionality using the provided api, achieved using Retrofit2 to create api calls using Java interfaces and Okhttp3 logging interceptor to debug api calls.*

*- Used MVP architecture for login functionality, needed a simple way to abstract validation and network logic from activity*

*- Added User Accounts functionality using the provided api*

*- Used MVVM architecture for User Accounts functionality as I wanted to bind the data returned from the api with the views that would show the data. This also solved a problem I was having with losing data after configuration changes*

### Screen 3 - Individual account screen
If a user selects one of those accounts, they should then be taken to this screen.  This screen should have the following functionality:
- Show the **'Name'** of the account.
- Show the account's **'PlanValue'**.
- Show the accounts **'Moneybox'** total.
- Allow a user to add to a fixed value (e.g. £10) to their moneybox total.

*- Again used MVVM architecture to bind data to views, any changes to the stored Moneybox value would be reflected in the view*

A prototype wireframe of all 3 screens is provided as a guideline. You are free to change any elements of the screen and provide additional information if you wish.

*- Added loading spinner for login and user accounts screen for an improved UX*

*- Fixed touch shadow on login button*

![](/images/wireframe.png)

Please feel free to refactor the LoginActivity and use any libraries/helper methods to make your life easier.

*- Upgraded to Androidx in order to be able to use the latest Android support libraries*

*- Added parameterised tests for LoginValidationUtil*
