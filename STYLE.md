# Style guide #

> Consistency is key

### Functions

A function is literally that; a function. Each function of the program should be divided in a function that is as short as possible.
The benefit of this is that each function gets a name, so it is easy to read what the code does, and the code is clearly structured.

### Names

Class names are camelcase. Class instances and other variables start with a lowercase, if it consists of multiple words each following
word is written with a capital letter, this is also the case for function names. Function names should be short, and define clearly what
the functions does. This should be a verb or verb phrases.

### Comments

Comments should be limited. Only reserverd for parts where it is not exactly clear what the code does. Comments are concise.
Avoid 

### Writing styles

#### White space

Indents are four spaces.
All arithmetics should have spaces surrounding them so like this 4 + 4 and not like 4+4 this. Except for
-1 or x += 2

#### Line length

Lines should not surpass 80 characters.

#### Curly braces / Parentheses

I write curly braces and parentheses 

>likeThis () {
>
>}

and not 

>likeThis()
>
>{
>
>}


### Class member ordering

>class Order {
>
> // fields
> 
> // constructor
> 
> // methods
> 
>}
