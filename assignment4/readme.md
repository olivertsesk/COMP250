<html>
<head>
</head>
<body>
<h1>COMP 250 Winter 2015 Assignment 4 Instructions</h1>
<p>Version 1.0 19 March 2015</p>
<p><strong>DUE SATURDAY 11 APRIL 11pm</strong></p>
<h2>Background</h2>
<p><span lang="EN-US">In this assignment, you will implement some basic functionalities of a search engine. You will be provided with a file called "vertices.csv" and "edges.csv". The data in both files capture a portion of the McGill web network. The "vertices.csv" contains 100+ webpage links with the corresponding text of each page. The "edges.csv" file contains the links between the pages. For this assignment you will do the following:</span></p>
<ol>
<li>Create a Graph structure using the data from the "vertices.csv" and "edges.csv". To make things simpler, the edges MUST be directed edges (ie. an edge: (v1,v2) corresponds to an edge that points from vertex v1 to vertex v2.</li>
<li>Implement a search function on your graph to look for specific keywords. You will have to implement both Breadth-First Search and Depth-First Search algorithms.</li>
<li>Upon completing a search request, you will have to print the number of occurrences of your keyword for every page in your graph.</li>
</ol>
<p>That's it!</p>
<p><span style="text-decoration: underline;">To help you test your code, we've included a sample test suite that you can apply to your code. We will use similar methods and different values to test your submissions. Make sure your code passes all the tests in the test suite before the submission deadline.</span></p>
<p></p>
<h2>Requirements</h2>
<p>You may be able to make the project work on other environments, but we will support the following cross-platform configuration:</p>
<ul>
<li>Eclipse Luna</li>
<li>Java 7</li>
</ul>
<h2>Instructions</h2>
<ol>
<li>Download the assignment 4 package and import it into Eclipse (File -&gt; Import -&gt; General -&gt; Existing Projects into Workspace). The code skeleton will have several errors in red. The errors will disappear as you fill in the code.&nbsp;</li>
<li>Read the academic integrity statement at the bottom of this page and paste it into the header of the file Search.java intended for this purpose.</li>
<li>Complete the code provided as part of the assignment package. All further instructions are in the source code comments.</li>
<li>Submit your answer as a single file called Search<tt>.java</tt>&nbsp;that contains the academic integrity statement and all your solution. Do not submit a zip file, class files, or any other kind of artifact besides that single file. The file you submit should at the very least compile without any errors.</li>
</ol>
<p>Upload a single solution file per group by&nbsp;<strong>Saturday 11 April 11:00pm</strong>. Assignments submitted between 11:01pm and 7:00am the next day will receive a -40% absolute penalty. It will not be possible to submit assignments after Sunday 12 April 7am. Clear and fixed deadlines ensure fairness and speedier feedback.</p>
<h2>Important Tips for Managing Your Time</h2>
<ul>
<li>Poor performance in assignments is generally the result of bad time management as opposed to incomprehension of the material. Plan your time carefully.</li>
<li>Import the file and make sure everything works right away. If there is a problem you will have plenty of time to ask for help and calmly solve the issue. Do not wait to discover that you have a configuration problem close to the deadline.</li>
<li>Work incrementally and test your solution often. Start with the more basic functions. Do not try to do a so-called "big bang integration" where everything is supposed to come together nicely in the end (because it probably won't).</li>
<li>Bugs are normal and learning to solve them is part of this course. However, make sure to plan for them by allocating the necessary time. Do not expect that everything will work as it should right away.</li>
<li>We have set up the submission site to allow resubmissions. You can and should submit early, as soon as you have something reasonable. You will be able to upload new versions of your work until the deadline. However, if you have something pretty good submitted on time, don't override it after the deadline as you will instantly lose 40%.</li>
<li>If you are doing this assignment with a partner, we recommend trying to work on the solution together at the same time, using a technique called&nbsp;<a href="https://en.wikipedia.org/wiki/Pair_programming">pair programming</a>.</li>
</ul>
<h2>Academic Integrity Statement</h2>
<p>Paste this statement in the header of your code. We will not grade your work without it. If you have any doubt about the validity of the content of your submission, contact an instructor or TA before submitting it.</p>

<h2>Grading Scheme</h2>
<p>Your solution will be run through a suite of JUnit tests. Your grade will be based 80% on correctness (absence of bugs during testing, respect of the requirements), and 20% on general quality and understandability of the code (good names, indentation and layout, comments to clarify anything otherwise obscure).</p>
</body>
</html>
