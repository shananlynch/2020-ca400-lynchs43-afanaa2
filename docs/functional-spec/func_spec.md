Mona a first Programming language.
=======
**Functional Specification**
-------
##### **Authors**
* Ala Al Din Afana (16395986)
* Shanan Lynch     ()

*Date created: 21 November 2019*

__
# Table of Contents
* [1. Introduction](#1-introduction)
  * [1.1 Overview](#11-overview)
  * [1.3 Glossary](#13-glossary)
















# 1. Introduction
## 1.1 overview
For our fourth year project we will develop a First programming Language using Java cc. Our programming Language will be accessible through a web app, we plan on developing a debugger for our language that will allow the user to set breakpoints , resume step-over , step-into and step-out.
The design of our web-app is influenced by https://repl.it/languages/python3. 
The goal of our project is to provide a Novice with a programming language that will allow him to learn programming concepts and by learning it being able to transition to a more advanced language.
Our project will break down the syntax and semantics of python java and c++ and combine them into an easy to access, easy to use programming language. In 1999 Guido van Rossum defined his goals for python.
* an easy and intuitive language just as powerful as those of the major competitors;
* open source, so anyone can contribute to its development;
* code that is as understandable as plain English;
* suitable for everyday tasks, allowing for short development times. 


He was able to accomplish these goals and take python to the top of the rankings. Python is a great programming languages but its simplicity and plain english style hinders the ability of a programmer to transition to other languages, Python has eliminated many programming concepts that are used in desirable programming such as java and c++.
In java you must declare the type of data this is called static typing (int  x=1 ) on the other hand Python code is dynamically typed, meaning that you don't need to declare the type of variable this is known as duck typing 
"If it walks like a duck and it quacks like a duck, then it must be a duck"  (a=1).
This should not be an issue if a software engineer sticks to python for the rest of his life but as we learnt from first year a good software engineer is flexible and must have a love for learning and curiosity. with the market moving towards full stack development developers must be flexible and have a deep understanding of several programming languages. 
  
Principles of a programing language. 
According to (C.A.R.Hoare paper “HINTS ON PROGRAMMING LANGUAGE DESIGN” from Stanford University )
A programming language is regarded as a  tool to aid the programmer , Meaning it should give assistance in the most difficult aspects of his Art , Namely program design,  documentation and debugging. These three factors will be taken massively into our first programing language implementation and will be discussed in more details.

Program design. 
The first and very difficult aspect of design is to decide what the program is doing and format this to a clear and precise and acceptable specification. Often just as difficult as deciding on how to do it, and how to divide tasks into subtasks and to specify the purpose of each part and define clearly, precise, and efficient interface between them .
A good programming language should assist in establishing and enforcing the programing conventions and disciplines which will ensure harmonious co-operation of the parts of a large program when they are developed separately and finally put together. The programming language Java is a very good example. It assists the programmer in the design process by enforcing strict programming conventions. Java provides classes and functions to separate blocks of code. It has strict syntax and allows an object oriented design approach. Object Oriented Programing application is much easier and it also helps keep the system modular, flexible and extensible.

Program documentation.
Documentation is a very important factor in developing a good programing language. The purpose of documentation is to explain to a human reader how a program works, so it can be successfully adapted after it goes into service. Often perceived that documentation is a process that is added to a program after its developed seems to be wrong in principle and counter productive in practice. Instead documentation should be regarded as a necessary part of the process of design and coding. 
Our programming language will encourage and assist the programmer to write clear defined easy to understand code. In a sense our programing languages aims to be self documenting code. It will have a pleasant display and style of writing.
We aim for readability as much as writability.

Program Debugging.
Program debugging can often be the most tiresome, expensive and unpredictable phase of a program development. Particularly when adding subsections of a program together. The best way to reduce these problems is by successful initial design of the program, and by careful documentation. Even the best designed and documented programs run into issues and error. A good programming language will assist a programmer in debugging their code. We aim to develop a system that will reduce as far as possible the scope for coding errors. We will also develop a debugger that will allow the programmer to run their code step by step. This will be discussed in more detail later on.

The target of Our programming language is simplicity in the design of the language. A complicated language design has many side effects , as we complicate the design we cannot evaluate the consequences of our design decisions. But the main beneficiary of simplicity is the target user of the language.
While our goal is simplicity we have to take into account that our intentions are to provide a programming platform that will introduce a novice to programming. 

Designing a language that has simple syntax for example python can negatively impact the programmer transitioning into a more complex designed language. 
It can be argued that a novice who learns to program in c++ will have little difficulty coming into java or any other language.
It is also unreasonable for a novice programmer to learn languages that are designed for commercial use by experienced commercial programmers.
The development environments that they use often designed for commercial programming and contain features beyond a novices comprehension.

<img style="margin: 0 auto;" src="Images/diffrence.JPG">
 
