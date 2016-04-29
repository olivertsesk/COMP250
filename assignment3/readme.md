<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
</head>
<body>
<h1>COMP 250 Winter 2015 Assignment 3 Instructions</h1>
<p>Version 1.0 26 January 2015</p>
<p><strong>DUE FRIDAY 20 MARCH 11pm</strong></p>
<h2>Background</h2>
<p>In this assignment you will implement a small calculator that can process simple mathematical expressions that involve only (as input): positive integers, parentheses, and the four operators: addition, subtraction, multiplication, and (floating-point) division. For example, the following expressions are supported:</p>
<pre>(12-2)*2/(3+2)
1+1*4
(1)
(12)-(45-5)
1/3
</pre>
<p>but the following are not supported:</p>
<pre>-1-2
2.5*4
</pre>
<p>For this assignment, the graphical portion of the code is implemented for you. Your task is to supply the code responsible for processing the expression and evaluating it, and for detecting errors in invalid expressions.</p>
<p>Evaluating mathematical expressions may seem like a daunting task at first, but it is relatively simple if you choose the right data structures (which we have seen in class) and are familiar with two key concepts: tokenization and reverse Polish notation.</p>
<h3>Tokenization</h3>
<p>Tokenization is the process of breaking up a sequence of characters into units that are meaningful for a certain task (in our case, computing mathematical expressions). For instance, in the expression <code>123+456</code>, it is not convenient to consider the digits individually. Instead, we would prefer to work with a sequence of three units: <code>[123][+][456]</code> (here square brackets are used as token delimiters). As part of your task, you will write a tokenizer that does three things:</p>
<ul>
<li>Ignore ("swallow") all white spaces;</li>
<li>Flag an error if any invalid character is discovered (an invalid character is any character except a white space, digit, parenthesis, or operator).</li>
<li>Package the string into a stream of tokens.</li>
</ul>
<p>Example of tokenization:</p>
<pre>(123+34)/(3*4*5) -&gt; [(][123][+][34][)][/][(][3][*][4][*][5][)]
</pre>
<h3>Reverse Polish Notation</h3>
<p>Mathematical expressions such as <code>(1+2)/3</code> are in <a href="https://en.wikipedia.org/wiki/Infix_notation">infix notation</a>, that is, the operator is placed between operands. It turns out that evaluating operations directly in infix notation is unnecessarily complex. A better way is to convert infix expressions into <a href="https://en.wikipedia.org/wiki/Reverse_Polish_notation">Reverse Polish Notation (RPN)</a>, where operators follow their operands. In the following examples, the first line is an expression in infix notation and the line below is the same expression in RPN. Note that in RPN parentheses are superfluous and not used.</p>
<pre>[1][+][2]
[1][2][+]

[1][+][1][*][4]
[1][1][4][*][+]

[(][12][-][2][)][*][2][/][(][3][+][2][)]
[12][2][-][2][*][3][2][+][/]
</pre>
<p>As part of the assignment, one of your task is to convert the original expression in infix notation into its RPN equivalent. To do this, you can pretty much directly implement the famous <a href="https://en.wikipedia.org/wiki/Shunting-yard_algorithm">Shunting-yard algorithm</a>. If you use the previous (Wikipedia) reference, simply omit the steps that involve function calls since function calls are not supported by our calculator. As usual, our four operators are left-associative and multiplication and division have precedence over addition and subtraction.</p>
<p>Once you have an expression in RPN, evaluating it is trivial using a Stack (figuring out how to do this is part of the assignment).</p>
<h3>Error Handling</h3>
<p>As part of the assignment, we ask you to detect problems in the input string and to flag the first problematic character detected using method <code>flagError</code>. However, different parts of the computation are "better" at detecting different types of errors, so don't try to detect errors all at once (for example at the beginning), instead, consider the following tips:</p>
<ul>
<li>The Tokenizer is the best place to detect invalid characters.</li>
<li>The misuse of parentheses (for example as in <code>(1)(2)</code>) is easy to detect once you have a fully tokenized expression NOT in RPN.</li>
<li>Unbalanced parentheses are easy to detect as part of the Shunting-yard algorithm.</li>
<li>Illegal combination of operators (such as <code>1++2</code> are easy to detect in the evaluator.</li>
</ul>
<h2>Requirements</h2>
<p>You may be able to make the project work on other environments, but we will support the following cross-platform configuration:</p>
<ul>
<li>Eclipse Luna</li>
<li>Java 7</li>
</ul>
<h2>Instructions</h2>
<ol>
<li>Download the assignment 3 package and import it into Eclipse (File -&gt; Import -&gt; General -&gt; Existing Projects into Workspace). The code skeleton should build without errors. Run the main method to make sure everything works. You should see the calculator appear but (obviously) it's not going to work as it should.</li>
<li>Read the academic integrity statement at the bottom of this page and paste it into the header of file Assignment3.java intended for this purpose.</li>
<li>Complete the code provided as part of the assignment package. All further instructions and hints are in the source code comments.</li>
<li>Submit your answer as a single file called <tt>Assignment3.java</tt> that contains the academic integrity statement and all your solution. Do not submit a zip file, class files, or any other kind of artifact besides that single file. The file you submit should at the very least compile without any errors.</li>
</ol>
<p>Upload a single solution file per group by <strong>Friday 20 Mar 11:00pm</strong>. Assignments submitted between 11:01pm and 7:00am the next day will receive a -40% absolute penalty. It will not be possible to submit assignments after Saturday 21 Mar 7am. Clear and fixed deadlines ensure fairness and speedier feedback.</p>
<h2>Recommended Sequence</h2>
<p>There is a natural dependency order in the pieces you need to implement for this assignment and you are strongly encouraged to follow this order:</p>
<ol>
<li>Implement the Token hierarchy and the Tokenizer. Test the tokenizer thoroughly.</li>
<li>Implement the <code>processExpression</code> method and test that it produces correct RPN expressions.</li>
<li>Implement the <code>evaluateExpression</code> method.</li>
</ol>
<p>If you feel overwhelmed at first, implement a first version that assumes perfectly well-formed expressions. You should be able to add the error checking incrementally after the fact.</p>
<h2>Important Tips for Managing Your Time</h2>
<ul>
<li>Poor performance in assignments is generally the result of bad time management as opposed to incomprehension of the material. Plan your time carefully.</li>
<li>Import the file and make sure everything works right away. If there is a problem you will have plenty of time to ask for help and calmly solve the issue. Do not wait to discover that you have a configuration problem close to the deadline.</li>
<li>Work incrementally and test your solution often. Start with the more basic functions. Do not try to do a so-called "big bang integration" where everything is supposed to come together nicely in the end (because it probably won't).</li>
<li>Bugs are normal and learning to solve them is part of this course. However, make sure to plan for them by allocating the necessary time. Do not expect that everything will work as it should right away.</li>
<li>We have set up the submission site to allow resubmissions. You can and should submit early, as soon as you have something reasonable. You will be able to upload new versions of your work until the deadline. However, if you have something pretty good submitted on time, don't override it after the deadline as you will instantly lose 40%.</li>
<li>If you are doing this assignment with a partner, we recommend trying to work on the solution together at the same time, using a technique called <a href="https://en.wikipedia.org/wiki/Pair_programming">pair programming</a>.</li>
</ul>
<h2>Academic Integrity Statement</h2>
<p>Paste this statement in the header of your code. We will not grade your work without it. If you have any doubt about the validity of the content of your submission, contact an instructor or TA before submitting it.</p>

<h2>Grading Scheme</h2>
<p>Your grade will be based 80% on correctness (absence of bugs during testing, respect of the requirements), and 20% on general quality and understandability of the code (good names, indentation and layout, comments to clarify anything otherwise obscure).</p>
</body>
</html>
