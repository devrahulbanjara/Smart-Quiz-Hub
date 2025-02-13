-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Feb 12, 2025 at 12:30 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `SmartQuizHub`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin_details`
--

CREATE TABLE `admin_details` (
  `ID` int(11) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin_details`
--

INSERT INTO `admin_details` (`ID`, `Name`, `Email`, `Password`) VALUES
(2, 'Prakash Sharma', 'prakash.sharma@example.com', 'Prakash@1234'),
(3, 'Sita Rai', 'sita.rai@example.com', 'Sita@5678'),
(4, 'Rajesh Thapa', 'rajesh.thapa@example.com', 'Rajesh@91011'),
(5, 'Anita Koirala', 'anita.koirala@example.com', 'Anita@1213'),
(6, 'Ramesh Gurung', 'ramesh.gurung@example.com', 'Ramesh@1415');

-- --------------------------------------------------------

--
-- Table structure for table `competitor_scores`
--

CREATE TABLE `competitor_scores` (
  `competitor_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `level` varchar(50) NOT NULL,
  `score1` int(11) DEFAULT NULL,
  `score2` int(11) DEFAULT NULL,
  `score3` int(11) DEFAULT NULL,
  `score4` int(11) DEFAULT NULL,
  `score5` int(11) DEFAULT NULL,
  `overall_score` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `competitor_scores`
--

INSERT INTO `competitor_scores` (`competitor_id`, `name`, `level`, `score1`, `score2`, `score3`, `score4`, `score5`, `overall_score`) VALUES
(18, 'Anil Kumar', 'Beginner', 3, 4, 2, 5, 3, 54),
(19, 'Sita Rai', 'Intermediate', 5, 4, 4, 5, 4, 84),
(20, 'Ramesh Shrestha', 'Advanced', 5, 5, 5, 5, 5, 100),
(21, 'Pooja Sharma', 'Beginner', 2, 3, 3, 4, 3, 34),
(23, 'Maya Gurung', 'Advanced', 5, 4, 5, 5, 5, 96),
(24, 'Suman Bhandari', 'Beginner', 1, 1, 2, 2, 2, 18),
(26, 'Arun Paudel', 'Advanced', 5, 5, 5, 5, 4, 94),
(27, 'Nita Acharya', 'Beginner', 4, 3, 3, 3, 4, 56),
(29, 'Rashmi Bhattarai', 'Advanced', 5, 5, 5, 4, 5, 94),
(30, 'Bikash Koirala', 'Beginner', 3, 3, 4, 3, 3, 60),
(32, 'Rajeev Joshi', 'Advanced', 5, 5, 5, 5, 4, 96),
(33, 'Anju Khatri', 'Beginner', 2, 2, 2, 2, 3, 38),
(35, 'Nirajan K.C.', 'Advanced', 5, 5, 5, 5, 5, 100),
(36, 'Gita Rathi', 'Beginner', 3, 3, 4, 3, 2, 52),
(38, 'Ravindra Yadav', 'Advanced', 5, 4, 5, 5, 5, 96),
(39, 'Bina Joshi', 'Beginner', 3, 3, 4, 3, 3, 56),
(41, 'Nirisha Tiwari', 'Advanced', 5, 4, 5, 5, 4, 94),
(42, 'Pashupati Gurung', 'Beginner', 4, 4, 3, 3, 4, 72),
(44, 'Pradeep Dhakal', 'Advanced', 5, 5, 5, 4, 5, 94),
(45, 'Prasanna Baral', 'Beginner', 3, 4, 2, 3, 4, 60);

-- --------------------------------------------------------

--
-- Table structure for table `player_details`
--

CREATE TABLE `player_details` (
  `ID` int(11) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `competition_level` varchar(50) NOT NULL,
  `age` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `player_details`
--

INSERT INTO `player_details` (`ID`, `Name`, `Email`, `Password`, `competition_level`, `age`) VALUES
(18, 'Anil Kumar', 'anil.kumar123@example.com', 'Anil@1234', 'Beginner', 23),
(19, 'Sita Rai', 'sita.rai456@example.com', 'Sita@5678', 'Intermediate', 25),
(20, 'Ramesh Shrestha', 'ramesh.shrestha789@example.com', 'Ramesh@9876', 'Advanced', 28),
(21, 'Pooja Sharma', 'pooja.sharma321@example.com', 'Pooja@1357', 'Beginner', 21),
(22, 'Hari Prasad', 'hari.prasad654@example.com', 'Hari@2468', 'Intermediate', 30),
(23, 'Maya Gurung', 'maya.gurung987@example.com', 'Maya@2468', 'Advanced', 27),
(24, 'Suman Bhandari', 'suman.bhandari135@example.com', 'Suman@1234', 'Beginner', 24),
(25, 'Pramila Thapa', 'pramila.thapa852@example.com', 'Pramila@3521', 'Intermediate', 26),
(26, 'Arun Paudel', 'arun.paudel963@example.com', 'Arun@7410', 'Advanced', 29),
(27, 'Nita Acharya', 'nita.acharya741@example.com', 'Nita@2589', 'Beginner', 22),
(28, 'Manoj Pandey', 'manoj.pandey258@example.com', 'Manoj@5468', 'Intermediate', 31),
(29, 'Rashmi Bhattarai', 'rashmi.bhattarai369@example.com', 'Rashmi@9876', 'Advanced', 32),
(30, 'Bikash Koirala', 'bikash.koirala456@example.com', 'Bikash@1234', 'Beginner', 24),
(31, 'Shreeja Limbu', 'shreeja.limbu741@example.com', 'Shreeja@2589', 'Intermediate', 23),
(32, 'Rajeev Joshi', 'rajeeve.joshi963@example.com', 'Rajeev@3690', 'Advanced', 27),
(33, 'Anju Khatri', 'anju.khatri741@example.com', 'Anju@5793', 'Beginner', 21),
(34, 'Dinesh Lama', 'dinesh.lama258@example.com', 'Dinesh@8324', 'Intermediate', 29),
(35, 'Nirajan K.C.', 'nirajan.kc654@example.com', 'Nirajan@3245', 'Advanced', 28),
(36, 'Gita Rathi', 'gita.rathi852@example.com', 'Gita@1029', 'Beginner', 25),
(37, 'Laxmi Shrestha', 'laxmi.shrestha963@example.com', 'Laxmi@1009', 'Intermediate', 22),
(38, 'Ravindra Yadav', 'ravindra.yadav741@example.com', 'Ravindra@5846', 'Advanced', 31),
(39, 'Bina Joshi', 'bina.joshi258@example.com', 'Bina@4672', 'Beginner', 23),
(40, 'Suraj Gautam', 'suraj.gautam369@example.com', 'Suraj@1324', 'Intermediate', 27),
(41, 'Nirisha Tiwari', 'nirisha.tiwari456@example.com', 'Nirisha@7853', 'Advanced', 29),
(42, 'Pashupati Gurung', 'pashupati.gurung258@example.com', 'Pashupati@9423', 'Beginner', 25),
(43, 'Rekha Pandit', 'rekha.pandit369@example.com', 'Rekha@1248', 'Intermediate', 26),
(44, 'Pradeep Dhakal', 'pradeep.dhakal741@example.com', 'Pradeep@8901', 'Advanced', 30),
(45, 'Prasanna Baral', 'prasanna.baral852@example.com', 'Prasanna@7654', 'Beginner', 21),
(46, 'Samita Kunwar', 'samita.kunwar963@example.com', 'Samita@3568', 'Intermediate', 27),
(48, 'Rahul Dev Banjara', 'r@gmail.com', 'rahulbanjara', 'Advanced', 21);

-- --------------------------------------------------------

--
-- Table structure for table `quiz_questions`
--

CREATE TABLE `quiz_questions` (
  `question_id` int(11) NOT NULL,
  `question_text` varchar(255) NOT NULL,
  `option_1` varchar(255) NOT NULL,
  `option_2` varchar(255) NOT NULL,
  `option_3` varchar(255) NOT NULL,
  `option_4` varchar(255) NOT NULL,
  `correct_option` int(11) NOT NULL,
  `level` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `quiz_questions`
--

INSERT INTO `quiz_questions` (`question_id`, `question_text`, `option_1`, `option_2`, `option_3`, `option_4`, `correct_option`, `level`) VALUES
(2, 'What is the default value of a boolean variable in Java?', 'true', 'false', 'null', '0', 2, 'Beginner'),
(3, 'Which of the following is the correct way to declare an array in Java?', 'int[] arr;', 'int arr[];', 'int arr[] = new int[5];', 'int[5] arr;', 2, 'Beginner'),
(4, 'What is the size of a byte variable in Java?', '8 bits', '16 bits', '32 bits', '64 bits', 1, 'Beginner'),
(5, 'Which of the following is not a primitive data type in Java?', 'int', 'char', 'String', 'double', 3, 'Beginner'),
(6, 'Which keyword is used to define a class in Java?', 'class', 'def', 'function', 'object', 1, 'Beginner'),
(7, 'How do you create an instance of a class in Java?', 'new ClassName();', 'ClassName();', 'new ClassName;', 'ClassName.new();', 1, 'Beginner'),
(8, 'Which method is used to start a thread in Java?', 'run()', 'start()', 'execute()', 'begin()', 2, 'Beginner'),
(9, 'What is the default value of an integer variable in Java?', '0', '1', 'null', 'undefined', 1, 'Beginner'),
(10, 'Which of the following is used to handle exceptions in Java?', 'throw', 'throws', 'catch', 'try-catch', 4, 'Beginner'),
(11, 'What is the purpose of the static keyword in Java?', 'To define a class', 'To create objects', 'To create methods that belong to the class', 'To allocate memory', 3, 'Intermediate'),
(12, 'Which method is used to compare two strings in Java?', 'compareTo()', 'equals()', 'compare()', 'isEqual()', 1, 'Intermediate'),
(13, 'What is the output of the following code: System.out.println(2 + 3 + \"4\");', '234', '23', '5', '2345', 1, 'Intermediate'),
(14, 'What is the superclass of every class in Java?', 'Object', 'Super', 'Class', 'Base', 1, 'Intermediate'),
(15, 'What does the keyword final mean in Java?', 'Cannot be inherited', 'Cannot be overridden', 'Value cannot be changed', 'All of the above', 4, 'Intermediate'),
(16, 'What is the default value of a reference variable in Java?', 'null', '0', 'undefined', '\"\"', 1, 'Intermediate'),
(17, 'Which collection class is best to use when you need to store unique values in Java?', 'ArrayList', 'HashSet', 'LinkedList', 'TreeSet', 2, 'Intermediate'),
(18, 'What does the \"super\" keyword refer to in Java?', 'Parent class', 'Current class', 'This object', 'Static method', 1, 'Intermediate'),
(19, 'What is the output of the following code: System.out.println(\"Hello\".length());', '5', 'Hello', '6', 'Error', 1, 'Intermediate'),
(20, 'Which method is used to get the length of an array in Java?', 'size()', 'length()', 'getLength()', 'count()', 2, 'Intermediate'),
(21, 'What is the difference between \"==\" and \"equals()\" in Java?', '== compares reference, equals() compares values', '== compares values, equals() compares reference', 'Both compare values', 'Both compare references', 1, 'Advanced'),
(22, 'Which of the following is true about Java exceptions?', 'All exceptions must be handled', 'Only runtime exceptions need to be handled', 'All exceptions are checked exceptions', 'Runtime exceptions are unchecked', 4, 'Advanced'),
(23, 'What is the purpose of the transient keyword in Java?', 'To prevent serialization of a variable', 'To create an object', 'To define a constant', 'To override methods', 1, 'Advanced'),
(24, 'What is the result of calling the method System.gc() in Java?', 'Request the JVM to perform garbage collection', 'Immediately collects garbage', 'Stops the JVM', 'Nothing', 1, 'Advanced'),
(25, 'Which interface does the Collection class implement in Java?', 'Set', 'List', 'Iterable', 'Comparable', 3, 'Advanced'),
(26, 'Which method in Java is used to perform deep cloning of an object?', 'clone()', 'copy()', 'deepClone()', 'cloneDeep()', 1, 'Advanced'),
(27, 'What is the return type of the method hashCode() in Java?', 'int', 'String', 'Object', 'long', 1, 'Advanced'),
(28, 'What is the purpose of the synchronized keyword in Java?', 'To allow multiple threads to access a method simultaneously', 'To ensure that only one thread accesses a method at a time', 'To pause a thread', 'To create a thread-safe class', 2, 'Advanced'),
(29, 'What is the difference between ArrayList and LinkedList in Java?', 'ArrayList is faster for insertions, LinkedList for access', 'LinkedList is faster for insertions, ArrayList for access', 'Both are equally fast', 'None', 2, 'Advanced'),
(31, 'Kukhra paila ki anda ?', 'Kukhra', 'Anda', 'Maccha', 'Kamlesh', 4, 'Advanced'),
(44, 'What is the default value of a byte variable in Java?', '0', '1', 'null', 'undefined', 1, 'Beginner'),
(45, 'Which of the following is used to define a package in Java?', 'import', 'package', 'include', 'namespace', 2, 'Beginner'),
(46, 'Which method is used to get the character at a specific index in a string?', 'charAt()', 'getChar()', 'indexOf()', 'charAtIndex()', 1, 'Beginner'),
(47, 'Which of the following is not a keyword in Java?', 'interface', 'static', 'void', 'main', 4, 'Beginner'),
(48, 'Which operator is used to concatenate strings in Java?', '+', '-', '*', '/', 1, 'Beginner'),
(49, 'What does the \"this\" keyword refer to in Java?', 'Current instance', 'Current method', 'Super class', 'Main method', 1, 'Beginner'),
(50, 'How do you declare a constant in Java?', 'final int x;', 'const int x;', 'int const x;', 'constant int x;', 1, 'Beginner'),
(51, 'Which method is used to remove an element from an ArrayList in Java?', 'remove()', 'delete()', 'pop()', 'erase()', 1, 'Beginner'),
(52, 'Which of the following is used to handle exceptions in Java?', 'catch', 'throw', 'finally', 'try-catch', 4, 'Beginner'),
(53, 'What is the size of a short variable in Java?', '16 bits', '8 bits', '32 bits', '64 bits', 1, 'Beginner'),
(54, 'Which of the following is a valid comment in Java?', '// comment', '/* comment */', '# comment', 'All of the above', 4, 'Beginner'),
(55, 'Which method is used to create a thread in Java?', 'Thread()', 'start()', 'run()', 'execute()', 2, 'Beginner'),
(56, 'What is the default value of a boolean variable in Java?', 'true', 'false', 'null', 'undefined', 2, 'Beginner'),
(57, 'What is the purpose of the break statement in Java?', 'Exit from the loop', 'Exit from the method', 'Exit from the class', 'Exit from the program', 1, 'Beginner'),
(58, 'test', 'test', 'test', 'test', 'test', 1, 'Beginner'),
(59, 'Which of the following is used to create an object of a class in Java?', 'new', 'create', 'instantiate', 'object', 1, 'Beginner'),
(60, 'Which method is used to compare two integers in Java?', 'compare()', 'equals()', 'compareTo()', '==', 1, 'Beginner'),
(61, 'Which of the following is not a type of loop in Java?', 'for', 'foreach', 'loop', 'while', 3, 'Beginner'),
(62, 'What is the default value of a float variable in Java?', '0.0', '0', 'null', 'undefined', 1, 'Beginner'),
(63, 'Which of the following is a reference type in Java?', 'String', 'int', 'boolean', 'float', 1, 'Beginner'),
(64, 'What is the purpose of the static keyword in Java?', 'To define a class', 'To create objects', 'To create methods that belong to the class', 'To allocate memory', 3, 'Intermediate'),
(65, 'Which method is used to compare two strings in Java?', 'compareTo()', 'equals()', 'compare()', 'isEqual()', 1, 'Intermediate'),
(66, 'What is the output of the following code: System.out.println(2 + 3);', '234', '23', '5', '2345', 3, 'Intermediate'),
(67, 'What is the superclass of every class in Java?', 'Object', 'Super', 'Class', 'Base', 1, 'Intermediate'),
(68, 'What does the keyword final mean in Java?', 'Cannot be inherited', 'Cannot be overridden', 'Value cannot be changed', 'All of the above', 4, 'Intermediate'),
(69, 'What is the default value of a reference variable in Java?', 'null', '0', 'undefined', '\"\"', 1, 'Intermediate'),
(70, 'Which collection class is best to use when you need unique values in Java?', 'ArrayList', 'HashSet', 'LinkedList', 'TreeSet', 2, 'Intermediate'),
(71, 'What does the \"super\" keyword refer to in Java?', 'Parent class', 'Current class', 'This object', 'Static method', 1, 'Intermediate'),
(72, 'What is the output of the following code: System.out.println(\"Hello\".length());', '5', 'Hello', 'Error', '6', 1, 'Intermediate'),
(73, 'Which method is used to get the length of an array in Java?', 'size()', 'length()', 'getLength()', 'count()', 2, 'Intermediate'),
(74, 'What is the difference between \"==\" and \"equals()\" in Java?', '== compares reference, equals() compares values', '== compares values, equals() compares reference', 'Both compare values', 'Both compare references', 1, 'Intermediate'),
(75, 'Which of the following is true about Java exceptions?', 'All exceptions must be handled', 'Only runtime exceptions need to be handled', 'All exceptions are checked exceptions', 'Runtime exceptions are unchecked', 4, 'Intermediate'),
(76, 'What is the purpose of the transient keyword in Java?', 'To prevent serialization of a variable', 'To create an object', 'To define a constant', 'To override methods', 1, 'Intermediate'),
(77, 'What is the result of calling the method System.gc()?', 'Request the JVM to perform garbage collection', 'Immediately collects garbage', 'Stops the JVM', 'Nothing', 1, 'Intermediate'),
(78, 'Which interface does the Collection class implement in Java?', 'Set', 'List', 'Iterable', 'Comparable', 3, 'Intermediate'),
(79, 'Which method in Java is used to perform deep cloning?', 'clone()', 'copy()', 'deepClone()', 'cloneDeep()', 1, 'Intermediate'),
(80, 'What is the return type of the hashCode() method in Java?', 'int', 'String', 'Object', 'long', 1, 'Intermediate'),
(81, 'What is the purpose of the synchronized keyword in Java?', 'To allow multiple threads to access a method simultaneously', 'To ensure that only one thread accesses a method at a time', 'To pause a thread', 'To create a thread-safe class', 2, 'Intermediate'),
(82, 'What is the difference between ArrayList and LinkedList in Java?', 'ArrayList is faster for insertions, LinkedList for lookups', 'LinkedList is faster for insertions, ArrayList for lookups', 'Both are equally fast', 'None', 2, 'Intermediate'),
(83, 'What is the purpose of the keyword \"final\" in Java?', 'To prevent method overriding', 'To prevent inheritance', 'To make variables immutable', 'All of the above', 4, 'Intermediate'),
(84, 'What is the purpose of the synchronized keyword in Java?', 'To allow multiple threads to access a method simultaneously', 'To ensure that only one thread accesses a method at a time', 'To pause a thread', 'To create a thread-safe class', 2, 'Advanced'),
(85, 'What is the difference between ArrayList and LinkedList?', 'ArrayList is faster for insertions, LinkedList for lookups', 'LinkedList is faster for insertions, ArrayList for lookups', 'Both are equally fast', 'None', 2, 'Advanced'),
(86, 'What does the transient keyword do in Java?', 'Prevents serialization of a variable', 'Prevents method overriding', 'Prevents garbage collection', 'Prevents thread synchronization', 1, 'Advanced'),
(87, 'Which method is used to perform deep cloning in Java?', 'clone()', 'copy()', 'deepClone()', 'cloneDeep()', 1, 'Advanced'),
(88, 'Which interface does the Collection class implement in Java?', 'Set', 'List', 'Iterable', 'Comparable', 3, 'Advanced'),
(89, 'What is the return type of the hashCode() method in Java?', 'int', 'String', 'Object', 'long', 1, 'Advanced'),
(90, 'What is the purpose of the System.gc() method in Java?', 'Request the JVM to perform garbage collection', 'Immediately collects garbage', 'Stops the JVM', 'Nothing', 1, 'Advanced'),
(91, 'What does the \"super\" keyword refer to in Java?', 'Parent class', 'Current class', 'This object', 'Static method', 1, 'Advanced'),
(92, 'What is the default value of a reference variable in Java?', 'null', '0', 'undefined', '\"\"', 1, 'Advanced'),
(93, 'Which method is used to get the length of an array in Java?', 'size()', 'length()', 'getLength()', 'count()', 2, 'Advanced'),
(94, 'Which collection class is best to use when you need unique values in Java?', 'ArrayList', 'HashSet', 'LinkedList', 'TreeSet', 2, 'Advanced'),
(95, 'What is the output of calling the hashCode() method on two identical objects?', 'Same integer value', 'Different integer values', 'Zero', 'Throws exception', 1, 'Advanced'),
(96, 'How do you create a custom exception in Java?', 'By extending Exception', 'By implementing Throwable', 'By creating an interface', 'By using the throws keyword', 1, 'Advanced'),
(97, 'Which method is used to start a thread in Java?', 'start()', 'run()', 'init()', 'begin()', 1, 'Advanced'),
(98, 'What does the volatile keyword do in Java?', 'Indicates that a variable can be changed by multiple threads', 'Prevents a variable from being changed', 'Makes a variable constant', 'Specifies a variable is private', 1, 'Advanced'),
(99, 'What is the purpose of the java.nio package?', 'For file and I/O operations', 'For database connections', 'For UI design', 'For networking operations', 1, 'Advanced'),
(100, 'Which class is used to implement a stack in Java?', 'ArrayList', 'HashMap', 'Stack', 'LinkedList', 3, 'Advanced'),
(101, 'What is the purpose of the synchronized block in Java?', 'To make methods thread-safe', 'To prevent race conditions in multi-threading', 'To increase performance', 'To prevent deadlocks', 2, 'Advanced'),
(102, 'What is the result of calling the equals() method on a null object in Java?', 'NullPointerException', 'False', 'True', 'RuntimeException', 1, 'Advanced');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin_details`
--
ALTER TABLE `admin_details`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `competitor_scores`
--
ALTER TABLE `competitor_scores`
  ADD PRIMARY KEY (`competitor_id`);

--
-- Indexes for table `player_details`
--
ALTER TABLE `player_details`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `quiz_questions`
--
ALTER TABLE `quiz_questions`
  ADD PRIMARY KEY (`question_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin_details`
--
ALTER TABLE `admin_details`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `competitor_scores`
--
ALTER TABLE `competitor_scores`
  MODIFY `competitor_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT for table `player_details`
--
ALTER TABLE `player_details`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT for table `quiz_questions`
--
ALTER TABLE `quiz_questions`
  MODIFY `question_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=108;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `competitor_scores`
--
ALTER TABLE `competitor_scores`
  ADD CONSTRAINT `competitor_scores_ibfk_1` FOREIGN KEY (`competitor_id`) REFERENCES `player_details` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
