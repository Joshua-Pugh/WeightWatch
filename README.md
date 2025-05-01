# CS-360-Mobile-Architect-Programming

# Briefly summarize the requirements and goals of the app you developed. What user needs was this app designed to address?
The app required a database with at least three tables: one to store the daily weight, one to store user logins and passwords, and one to store the goal weight. It also needed a screen for logging into the app, a screen with a grid that displays all the daily weights and the days they were entered, a mechanism by which the user can add a daily weight, a mechanism by which the user can add a goal weight and a mechanism by which the application will notify the user when they reach their goal weight. The app was designed to address users' needs to allow them to track their weight and work towards a goal weight easily.

# What screens and features were necessary to support user needs and produce a user-centered UI for the app? How did your UI designs keep users in mind? Why were your designs successful?
Screens and Features:
Login Screen:
  Allows users to log in if they already have an account.
  Provides an option to create a new account if the user logs in for the first time.
Daily Weight Entry Screen:
  A form where users can input their daily weight.
  A button to submit the weight entry.
Goal Weight Entry Screen:
  A form where users can set their goal weight.
  A button to submit the goal weight.
  A textview to show the current goal weight
  A button to delete the current goal weight
Weight Display Screen:
  A grid that displays all the daily weights, the corresponding dates, and a button to delete the entry from the grid.
Notification System:
  Sends a notification to the user when they reach their goal weight.
My app design was made completely with the user in mind. I did my best to keep this simple and intuitive. I added a navigation bar at the bottom to move through screens in the main activity for ease of use. I believe for the most part my designs were successful overall. I do wish I could have had the time to add a few more features to add to the overall user experience

# How did you approach the process of coding your app? What techniques or strategies did you use? How could those techniques or strategies be applied in the future?
I started by researching and undertanding the requirements of the app and each individual component that would be needed. I used an iterative approach coding little by little and then testing that code for proper functionality. In my future career path these concepts are commonly used and in practice. From unit testing to white and black box testing to the iterative approach using sprints to develop software. 

# How did you test to ensure your code was functional? Why is this process important, and what did it reveal?
I used unit testing and functional testing. I used a few unit tests on the user, goal weight, and daily weight classes to ensure all their methods worked properly. I tested each screen after I was done coding it to ensure it worked properly. Then I made any changes that needed to be made and started the process over again. 

# Consider the full app design and development process from initial planning to finalization. Where did you have to innovate to overcome a challenge?
The coding phase of the process was the most challenging for me. There were many times when things were not working as intended or as I had them coded. I had to do a lot of research on was to implement certain aspects of my app. A good example would be the SMS permissions and sending a message when the user reaches their goal weight. I had to read the documentation for a few deprecated methods and find other ways to implement the functionality I was looking for. This project taught me a ton of things that I will not soon forget!
# In what specific component of your mobile app were you particularly successful in demonstrating your knowledge, skills, and experience?
I honestly learned a lot of skills that I did not have beforehand working on this project. One place I think I did well was in the overall layout of the code and how it was structured. I used singleton classes for the database, dao's, and repository classes. I learned about the MVVM pattern and used that for the other classes within the app. I did my best to use the best practices when it came to writing my code and implementing features within the app.
