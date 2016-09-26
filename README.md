# wp-xml-parser
A tool that extracts info from a WordPress xml export file and structures it.

#Instructions
1. Navigate to your WordPress site's WP Admin and export all content. You are left with an .xml file.
2. Open the Java project in your IDE. I used IntelliJ. Create an instance of the WPArchive class, 
and make sure to pass the path of your .xml file as parameter in the constructor.
3. Now you have a neat data structure that contains your WordPress posts and the comments.

#Data Structure
The three classes in the implementation are WPArchive, Post and Comment.
##WPArchive 
WPArchive is the library, a collection of all posts.

##Post
A post has an author, date and content as well as a list of comments.

##Comment
A comment has an author, content and a date.

#Properties and methods
##getArchive()
The archive is an ArrayList of "Post" objects, so all methods of the ArrayList are accessible.
