<html>
<head>
</head>
<body>
<h1>COMP 250 Winter 2015 Assignment 2 Instructions</h1>
<p>Version 1.0 06 February 2015</p>
<p><strong>DUE WEDNESDAY 25 FEBRUARY 11pm</strong></p>
<h2>Background</h2>
<p><span lang="EN-US">You have been assigned the responsibility of implementing a basic command-line interface for an Electronic Medical Records (EMR) system for one of the departments inside The Royal Victoria Hospital in Montreal. (The department is left unnamed for legal and security purposes).</span></p>
<p>The current electronic system in this department is an Excel workbook to save patient records, patient medical notes, visits, examinations, and other information. The Excel sheets contain tens of thousands of records and are a nightmare to manage. They are very large and crash very often. Moreover, the secretaries use them as the system interface to patient data. Although the staff has been able to cross-reference information from several sheets, the whole system is becoming very difficult to administer and update using Excel. They need a better solution.</p>
<p>In this assignment, you will create the building blocks of a new command line EMR system that will extract patient and doctor information (demo info) from the existing sheets and allow the secretaries manage, update, and modify existing records. The data will still be saved to the excel sheets, however, the secretaries would not need to deal directly with them. They will only use your command-line EMR interface. More specifically, the interface you create will extract patient information from sheets into objects in your program and allow you to do the following:</p>
<ol>
<li><span lang="EN-US"></span><span lang="EN-US">Add a new patient to the EMR system</span></li>
<li><span lang="EN-US"></span><span lang="EN-US">Add a new Doctor to the EMR system</span></li>
<li><span lang="EN-US"></span><span lang="EN-US">Record new patient visit to the department</span></li>
<li><span lang="EN-US"></span><span lang="EN-US">Edit patient information</span></li>
<li><span lang="EN-US"></span><span lang="EN-US">Display list of all Patient IDs</span></li>
<li><span lang="EN-US"></span><span lang="EN-US">Display list of all Doctor IDs</span></li>
<li><span lang="EN-US"></span><span lang="EN-US">Print a Doctor's record</span></li>
<li><span lang="EN-US"></span><span lang="EN-US">Print a Patient's record</span></li>
<li><span lang="EN-US"></span>Exit and save modifications</li>
</ol>
<p>Moreover, you will implement some of the above functionalities using algorithms that run in different Big-O time.</p>
<p>We&rsquo;ve simplified the process a tiny bit by providing you with the Excel sheets in .csv format (You don&rsquo;t have to write special code to handle excel files. You only need to read/write the csv files and load/save the data) &nbsp;</p>
<h2>Requirements</h2>
<p>You may be able to make the project work on other environments, but we will support the following cross-platform configuration:</p>
<ul>
<li>Eclipse Luna</li>
<li>Java 7</li>
</ul>
<h2>Instructions</h2>
<ol>
<li>Download the assignment 2 package and import it into Eclipse (File -&gt; Import -&gt; General -&gt; Existing Projects into Workspace). The code skeleton should build without errors. Run the main method to make sure everything works.</li>
<li>Read the academic integrity statement at the bottom of this page and paste it into the header of file Assignment1.java intended for this purpose.</li>
<li>Complete the code provided as part of the assignment package. All further instructions are in the source code comments.</li>
<li>Submit your answer as a single file called&nbsp;<tt>EMR.java</tt>&nbsp;that contains the academic integrity statement and all your solution. Do not submit a zip file, class files, or any other kind of artifact besides that single file. The file you submit should at the very least compile without any errors.</li>
</ol>
<p>Upload a single solution file per group by&nbsp;<strong>Wednesday 25 Feb 11:00pm</strong>. Assignments submitted between 11:01pm and 7:00am the next day will receive a -40% absolute penalty. It will not be possible to submit assignments after Thursday 26 Feb 7am. Clear and fixed deadlines ensure fairness and speedier feedback.</p>
<h2>Important Tips for Managing Your Time</h2>
<ul>
<li>Poor performance in assignments is generally the result of bad time management as opposed to incomprehension of the material. Plan your time carefully.</li>
<li>Import the file and make sure everything works right away. If there is a problem you will have plenty of time to ask for help and calmly solve the issue. Do not wait to discover that you have a configuration problem close to the deadline.</li>
<li>Work incrementally and test your solution often. Start with the more basic functions. Do not try to do a so-called "big bang integration" where everything is supposed to come together nicely in the end (because it probably won't).</li>
<li>Bugs are normal and learning to solve them is part of this course. However, make sure to plan for them by allocating the necessary time. Do not expect that everything will work as it should right away.</li>
<li>We have set up the submission site to allow resubmissions. You can and should submit early, as soon as you have something reasonable. You will be able to upload new versions of your work until the deadline. However, if you have something pretty good submitted on time, don't override it after the deadline as you will instantly lose 40%.</li>
<li>If you are doing this assignment with a partner, we recommend trying to work on the solution together at the same time, using a technique called&nbsp;<a href="https://en.wikipedia.org/wiki/Pair_programming">pair programming</a>.</li>
</ul>

<h2>Grading Scheme</h2>
<p>Your solution will be run through a suite of JUnit tests designed by the TAs. Your grade will be based 80% on correctness (absence of bugs during testing, respect of the requirements), and 20% on general quality and understandability of the code (good names, indentation and layout, comments to clarify anything otherwise obscure).</p>
<h2>Contact Information</h2>
<p>You can post clarification and background questions to the Assignment 2 discussion forum. The coordinator in charge of collecting all the submissions for this assignment is&nbsp;<a href="mailto:faizy.ahsan@mail.mcgill.ca" target="_blank">Faizy Ahsan</a>. He can be contacted for any questions regarding the submission process except about the submission deadline, which is fixed.</p>
</body>
</html>
